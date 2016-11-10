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

package com.openlopd.web.controllers.privatearea.admin.grupos;

import com.openlopd.business.seguridad.GruposLocal;
import com.openlopd.business.seguridad.SeguridadLocal;
import com.openlopd.entities.seguridad.EmpresasGrupos;
import com.openlopd.entities.seguridad.PermisosGrupos;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.web.controllers.privatearea.AbstractWebPageController;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controlador de la página admin/grupos/grupos.jsp
 * @author Eduardo L. García Glez.
 */
public class CGrupos extends AbstractWebPageController implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(CGrupos.class);
    private GruposLocal grupos = lookupGruposLocal();
    private SeguridadLocal seguridad = lookupSeguridadLocal();
    private Hashtable<String, PermisosGrupos> permisos;
    
    /**
     * Constructor por defecto.
     */
    public CGrupos() {
        super(ColumnasPermisos.APP_ADMIN);
    }
    
    /**
     * Obtiene el listado de las columnas de permisos accesibles al usuario.
     * @return Listado de columnas de permisos a las que el usuario tiene acceso.
     */
    public List<ColumnasPermisos> getColumnasPermisos() {
        return seguridad.getColumnasPermisos(session.getAccessInfo());
    }
    
    /**
     * Obtiene el listado de grupos de la empresa gestionada.
     * @return Listado de grupos.
     */
    public List<EmpresasGrupos> getGrupos() {
        return grupos.findAll(session.getAccessInfo());
    }
    
    /**
     * Obtiene el valor de un permiso para un grupo.
     * @param grupo Grupo del que obtener el permiso.
     * @param permiso Permiso del que se quiere obtener el valor.
     * @return Valor del permiso solicitado.
     * @throws UnknownColumnException Si el permiso solicitado es desconocido.
     * @see com.openlopd.entities.seguridad.base.ColumnasPermisos
     * @see com.openlopd.entities.seguridad.base.BasePermisosGrupos
     */
    public Byte getPermiso(EmpresasGrupos grupo, ColumnasPermisos permiso) 
            throws UnknownColumnException {
        if (permisos == null) {
            permisos = grupos.getPermisosDeCadaGrupo(session.getAccessInfo());
        }
        return (Byte) permisos.get(grupo.getIdGrupo()).getByName(permiso);
    }

    //<editor-fold defaultstate="collapsed" desc="Lookkup">
    private SeguridadLocal lookupSeguridadLocal() {
        try {
            Context c = new InitialContext();
            return (SeguridadLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/Seguridad!com.openlopd.business.seguridad.SeguridadLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean SeguridadLocal");
            throw new RuntimeException(ne);
        }
    }
    
    private GruposLocal lookupGruposLocal() {
        try {
            Context c = new InitialContext();
            return (GruposLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/Grupos!com.openlopd.business.seguridad.GruposLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean GruposLocal");
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>
    
}
