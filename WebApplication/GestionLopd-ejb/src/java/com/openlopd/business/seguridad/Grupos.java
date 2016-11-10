/*
 * OpenLOPD
 * Copyright (C) 2011  Eduardo L. García Glez <eduardo.l.g.g@gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.openlopd.business.seguridad;

import com.openlopd.entities.seguridad.EmpresasGrupos;
import com.openlopd.entities.seguridad.GruposUsuarios;
import com.openlopd.entities.seguridad.PermisosGrupos;
import com.openlopd.entities.seguridad.base.BasePermisosGrupos;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.entities.seguridad.utils.primarykey.GenKey;
import com.openlopd.sessionbeans.seguridad.ContratosTipoFacadeLocal;
import com.openlopd.sessionbeans.seguridad.EmpresasGruposFacadeLocal;
import com.openlopd.sessionbeans.seguridad.GruposUsuariosFacadeLocal;
import com.openlopd.sessionbeans.seguridad.PermisosGruposFacadeLocal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lógica de negocio para la gestión de grupos.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 21 de mar de 2011
 */
@Stateless
public class Grupos implements GruposLocal {

    private static Logger logger = LoggerFactory.getLogger(Grupos.class);
    // <editor-fold defaultstate="collapsed" desc="EJB properties">
    @EJB
    private EmpresasGruposFacadeLocal empresasGruposFacade;
    @EJB
    private PermisosGruposFacadeLocal permisosGruposFacade;
    @EJB
    private GruposUsuariosFacadeLocal gruposUsuariosFacade;
    @EJB
    private ContratosTipoFacadeLocal contratosTipoFacade;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Secction Constructores">
    public Grupos() {
    }
    // </editor-fold>

    /**
     * Crea el grupo de administrador necesario en los nuevos contratos.
     *
     * @param cif CIF/NIF de la empresa/autonomo del que se está realizando el
     * contrao.
     * @param idContratoTipo Tipo del contrato a utilizar.
     * @return El identificador del grupo creado <code>null</code> en caso de
     * error;
     */
    @Override
    public String addAdminGroup(String idEmpresa, String idUsuario, short idContratoTipo) {
        EmpresasGrupos eg;
        PermisosGrupos pg;
        GruposUsuarios gu;

        try {
            eg = empresasGruposFacade.addAdminGroup(idEmpresa);
            pg = new PermisosGrupos();
            pg.importaPermisosDesdeContrato(contratosTipoFacade.find(idContratoTipo),
                    PermisosGrupos.RW);
            pg = permisosGruposFacade.addPermisos(pg, eg.getIdGrupo(), true);
            gu = gruposUsuariosFacade.addUserToGroup(idUsuario, eg.getIdGrupo());
            return eg.getIdGrupo();
        } catch (Exception e) {
            logger.error("Error creando el grupo de administrador para: {}"
                    + " Contrato tipo {}", idEmpresa, idContratoTipo);
            return null;
        }
    }
    
    /**
     * Añade un nuevo grupo al sistema.
     * @param accessInfo Infomación de acceso del usuario.
     * @param grupo nuevo grupo a añadir.
     * @return identificador del nuevo grupo creado.
     */
    @Override
    public String addGroup(AccessInfo accessInfo, EmpresasGrupos grupo) {
        try {            
            
            if (empresasGruposFacade.findByNombre(accessInfo, 
                    grupo.getNombre()) != null) {
                return null;
            }
            
            PermisosGrupos permisos = new PermisosGrupos(accessInfo.getPermisosUsuario(),
                    GenKey.newKey(), true);
            permisosGruposFacade.create(permisos);
            
            grupo.getEmpresasGruposPK().setIdEmpresa(accessInfo.getSubEmpresa()
                    .getIdEmpresa());
            grupo.getEmpresasGruposPK().setidGrupo(permisos.getId());
            empresasGruposFacade.create(grupo);
            return (grupo.getIdGrupo());
        } catch (UnknownColumnException ex) {
            logger.error("Imposible!!! columna de permisos inexistente.");
            return null;
        }
    }

//    public String addGroup(BasePermisosGrupos b){
//        try {
//
//        } catch (Exception e) {
//
//        }
//    }
//
//    public closeGroup(){
//
//    }
    @Override
    public List<EmpresasGrupos> findAll(AccessInfo accessInfo) {
        try {
            if (accessInfo.getPermisosUsuario().hasAccess(
                    ColumnasPermisos.APP_ADMIN, BasePermisosGrupos.LECTURA)) {
                return empresasGruposFacade.findByIdEmpresa(accessInfo.getSubEmpresa().getIdEmpresa());
            }
        } catch (UnknownColumnException e) {
            logger.error("Imposible!!!: columna de permiso de acceso no encontrada.");
        }
        return null;
    }
    
    @Override
    public List<EmpresasGrupos> findAllHabilitados(AccessInfo accessInfo, 
            String idUsuario) {
        try {
            if (accessInfo.getPermisosUsuario().hasAccess(
                    ColumnasPermisos.APP_ADMIN, BasePermisosGrupos.LECTURA)) {
                return empresasGruposFacade.findByIdUsuario(accessInfo,
                        idUsuario);
            }
        } catch (UnknownColumnException e) {
            logger.error("Imposible!!!: columna de permiso de acceso no encontrada.");
        }
        return null;
        
    }
    
    @Override
    public List<EmpresasGrupos> findAllDisponibles(AccessInfo accessInfo, 
            String idUsuario) {
        List<EmpresasGrupos> deshabilitados = new ArrayList<EmpresasGrupos>();
        List<EmpresasGrupos> disponibles = this.findAll(accessInfo);
        List<EmpresasGrupos> habilitados = this.findAllHabilitados(accessInfo, idUsuario);
        
        for(EmpresasGrupos g: disponibles) {
            if (habilitados != null && !habilitados.contains(g)) {
                deshabilitados.add(g);
            }
        }
        return deshabilitados;
    }

    @Override
    public PermisosGrupos findPermisosGrupo(AccessInfo accessInfo, String idGrupo) {
        try {
            if (accessInfo.getPermisosUsuario().hasAccess(
                    ColumnasPermisos.APP_ADMIN, BasePermisosGrupos.LECTURA)) {
                return permisosGruposFacade.find(idGrupo);
            }
        } catch (UnknownColumnException e) {
            logger.error("Imposible!!!: columna de permiso de acceso no encontrada.");
        }
        return null;
    }
    
    @Override
    public Hashtable<String, PermisosGrupos> getPermisosDeCadaGrupo(AccessInfo accessInfo) {
        Hashtable<String, PermisosGrupos> lp = new Hashtable();
        for (EmpresasGrupos e: findAll(accessInfo)) {
            lp.put(e.getIdGrupo(), findPermisosGrupo(accessInfo, e.getIdGrupo()));
        }
        return lp;
    }
      
    /**
     * Actualiza un permiso de acceso de tipo lectura/escritura.
     * @param accessInfo Información de acceso del usuario.
     * @param permiso permiso a actualizar.
     * @param idGrupo grupo al que pertenece el permiso a actualizar.
     * @param newValue nuevo valor del permiso.
     * @return permisos del grupo tras la actualización.
     */
    @Override
    public PermisosGrupos updatePermiso (AccessInfo accessInfo, 
            ColumnasPermisos permiso, String idGrupo, Byte newValue) {
        try {
            if (accessInfo.getPermisosUsuario().hasAccess(ColumnasPermisos.APP_ADMIN, 
                    BasePermisosGrupos.ESCRITURA)) {
                PermisosGrupos p = permisosGruposFacade.findByIdGrupo(accessInfo, 
                        idGrupo, accessInfo.getSubEmpresa().getIdEmpresa());
                p.setByName(permiso, newValue);
                permisosGruposFacade.edit(p);
                return p;
            }
        } catch (UnknownColumnException ex) {
            logger.warn("Solicitado cambiar un permiso inexistente.");
        }
        return null;
    }
}
