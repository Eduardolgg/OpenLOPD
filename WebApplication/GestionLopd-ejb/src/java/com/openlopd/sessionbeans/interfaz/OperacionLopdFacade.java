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

package com.openlopd.sessionbeans.interfaz;

import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.interfaz.LinkList;
import com.openlopd.entities.interfaz.OperacionLopd;
import com.openlopd.entities.interfaz.TipoOperacion;
import com.openlopd.entities.seguridad.GestoresEmpresas;
import com.openlopd.entities.seguridad.PermisosGrupos;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.entities.seguridad.utils.primarykey.GenKey;
import com.openlopd.sessionbeans.AbstractFacade;
import com.openlopd.sessionbeans.empresas.EmpresaFacadeLocal;
import com.openlopd.sessionbeans.lopd.FicheroFacadeLocal;
import com.openlopd.sessionbeans.seguridad.GestoresEmpresasFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Eduardo L. García Glez.
 */
@Stateless
public class OperacionLopdFacade extends AbstractFacade<OperacionLopd> implements OperacionLopdFacadeLocal {
    private static ResourceBundle rb = ResourceBundle.getBundle("com.openlopd.config.config", Locale.getDefault());    
    private static Logger logger = LoggerFactory.getLogger(OperacionLopdFacade.class);
    
    @EJB
    private GestoresEmpresasFacadeLocal gestoresEmpresasFacade;
    @EJB
    private EmpresaFacadeLocal empresaFacade;
    @EJB
    private LinkListFacadeLocal linkListFacade;
    @EJB
    private FicheroFacadeLocal ficheroFacade;
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OperacionLopdFacade() {
        super(OperacionLopd.class);
    }
    
    private void rankchange(AccessInfo accessInfo, boolean incrementar) {
        try {
            if (accessInfo.getPermisosUsuario().hasAccess(
                    ColumnasPermisos.GESTION_EMPRESAS, PermisosGrupos.ESCRITURA)) {
                GestoresEmpresas e = gestoresEmpresasFacade
                        .findEmpresaGestionada(accessInfo);
                if (e != null) {
                    if (incrementar) {
                        e.setRank(e.getRank() + 1);
                    } else {
                        e.setRank(e.getRank() - 1);
                    }
                    gestoresEmpresasFacade.edit(e);
                }
            }
        } catch (UnknownColumnException e) {
            logger.error("Imposible la columna Gestion_Empresas debe existir!");
        } catch (Exception e) {
            if (!accessInfo.getEmpresa().getIdEmpresa().equals(
                    rb.getString("empresaID"))) {
                logger.error("ERROR!!!!: la empresa debería tener un gestor. ");
            }
        }
    }

    @Override
    public List<OperacionLopd> findOperations(AccessInfo accessInfo) {
        Query q = em.createNamedQuery("OperacionLopd.findOpsByEmpresa");
        q.setParameter("active", true);
        q.setParameter("nivel", ficheroFacade.getNivel(accessInfo));
        q.setParameter("empresa", accessInfo.getSubEmpresa());
        return getOperationsWithAccess(accessInfo, q.getResultList());
    }

    /**
     * Devuelve el procentage de completado de las operaciones lopd.
     *
     * @param accesInfo infomración de acceso al sistema.
     * @return entero con el porcentaje de completado.
     */
    @Override
    public Integer getPorcentajeCompletado(AccessInfo accessInfo) {
        List<OperacionLopd> listOp = findOperations(accessInfo);
        int realizadas = 0;
        for (OperacionLopd o : listOp) {
            if (o.getEstado()) {
                realizadas++;
            }
        }
        return !listOp.isEmpty() ? realizadas * 100 / listOp.size() : 0;
    }

    /**
     * Obtiene la siguente operación lopd Recomendada.
     *
     * @param accessInfo Información de acceso.
     * @return Operación lopd recomendada.
     */
    @Override
    public OperacionLopd getSiguienteOpRecomendada(AccessInfo accessInfo) {
        Query q = em.createNamedQuery("OperacionLopd.findOpsRecomendada");
        q.setParameter("active", true);
        q.setParameter("nivel", ficheroFacade.getNivel(accessInfo));
        q.setParameter("empresa", accessInfo.getSubEmpresa());
        q.setParameter("estado", true);

        List<OperacionLopd> ol = getOperationsWithAccess(accessInfo, q.getResultList());
        return (ol.isEmpty() ? null : ol.get(0));
    }
    
    /**
     * Actualiza el estado de una operación.
     * @param accessInfo Información de acceso al sistema.
     * @param tipoOperacion Operación a actualizar.
     * @param nuevoValor Nuevo valor de la operación, true realizada, false
     * no realizada.
     * 
     * @return  Operación actualizada.
     */
    @Override
    public void actualizarOperacion(AccessInfo accessInfo, 
            TipoOperacion tipoOperacion, Boolean nuevoValor) {
        Query q = em.createNamedQuery("OperacionLopd.findOpToUpdate");
        q.setParameter("empresa", accessInfo.getSubEmpresa());
        
        for (OperacionLopd o : TipoOperacion.getUpdateList(tipoOperacion, nuevoValor)) {
            q.setParameter("codOperacion", o.getOperacion().getCodOperacion());
            OperacionLopd dbo = (OperacionLopd) q.getSingleResult();
            dbo.setEstado(o.getEstado());
            this.edit(dbo);
        }
        
        this.rankchange(accessInfo, true);
//        return dbo;
    }

    /**
     * Actualiza el listado de operaciones de todas las empresas.
     * @param accessInfo Infomación de acceso.
     */
    @Override
    public void getActualizarListOperaciones(AccessInfo accessInfo) {
        List<Empresa> empresas = empresaFacade.findAll();
        List<LinkList> operacionesDisponibles = linkListFacade.findOperations(ficheroFacade.getNivel(accessInfo));
        for (Empresa e : empresas) {
            getActualizarListOperaciones(accessInfo, e, operacionesDisponibles);
        }
        
        Query q = em.createNamedQuery("OperacionLopd.eliminarOperacionesAntiguas");
        q.executeUpdate();
    }
   
    @Override
    public void getActualizarListOperaciones(AccessInfo accessInfo,
            Empresa empresa) {

        List<LinkList> operacionesDisponibles = linkListFacade.findOperations();
        getActualizarListOperaciones(accessInfo, empresa, operacionesDisponibles);
    }
    
    private void getActualizarListOperaciones(AccessInfo accessInfo,
            Empresa empresa, List<LinkList> operacionesDisponibles) {

        for (LinkList op : operacionesDisponibles) {
            updateOperaciones(empresa, op);
        }
    }
    
    /**
     * Devuelve solo las operaciones para las que se tiene permiso.
     * @param accessInfo Información de acceso del usuario.
     * @param operations Operaciones en las que revisar los permisos.
     * @return Listado de operaciones permitidas para el usuario.
     */
    private List<OperacionLopd> getOperationsWithAccess(AccessInfo accessInfo,
            List<OperacionLopd> operations) {
        List<OperacionLopd> list = new ArrayList<OperacionLopd>();
        for (OperacionLopd o : operations) {
            try {
                if (accessInfo.getPermisosUsuario()
                        .hasAccess(o.getOperacion().getPermiso(), PermisosGrupos.ESCRITURA)) {
                    list.add(o);
                }                
            } catch (UnknownColumnException e) {
                logger.error(e.getMessage());
            }                     
        }
        return list;
    }
    
    /**
     * Busca si la operación existe, si no, la crea.
     * @param empresa Empresa a la que insertar la operación.
     * @param link Link de la operación.
     */
    private void updateOperaciones(Empresa empresa, LinkList link) {
        Query q = em.createNamedQuery("OperacionLopd.findOpByLink");
        
        q.setParameter("empresa", empresa);
        q.setParameter("link", link);
        Boolean existe = ((Long) q.getSingleResult()) > 0;
        
        if (!existe) {
            OperacionLopd o = new OperacionLopd(GenKey.newKey(),link, empresa);
            o.setEstado(link.getCodOperacion().getDefaulValue());
            this.create(o);
        }
    }
}
