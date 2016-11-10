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
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.web.controllers.privatearea.CSession;
import java.io.Serializable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controla la actualización de permisos.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 31 de dic de 2012
 */
public class CUpdatePermiso implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(CUpdatePermiso.class);

    GruposLocal grupos = lookupGruposLocal();
    private CSession session;
    private ColumnasPermisos p;
    private String g;
    private Byte newValue;

    /**
     * Constructor por defecto.
     */
    public CUpdatePermiso() {
        //TODO: Establecer los permisos de acceso para el cambio del permiso.
    }

    /**
     * Establece la información de la sesión del usuario.
     * @param session Información de la sesión del usuario.
     */
    public void setSession(CSession session) {
        this.session = session;
    }

    /**
     * Establece el identificador del permiso a modificar.
     * @param p Identificador del permiso a modificar.
     */
    public void setP(ColumnasPermisos p) {
        this.p = p;
    }

    /**
     * Establece el Nombre del grupo da modificar.
     * @param g Nombre del grupo a modificar.
     */
    public void setG(String g) {
        this.g = g;
    }

    /**
     * Establece el nuevo valor del permiso.
     * @param newValue Nuevo valor del permiso.
     * @see com.openlopd.entities.seguridad.base.BasePermisosGrupos
     */
    public void setNewValue(Byte newValue) {
        this.newValue = newValue;
    }

    /**
     * Ejecuta la operación de actualización del permiso.
     * @return (String) "ok" si el permiso se ha cambiado y "Error" en caso
     * contrario.
     */
    public String update() {
        try {
            grupos.updatePermiso(session.getAccessInfo(), p, g, newValue);
            return "ok";
        } catch (Exception e) {
            String[] errInfo = {p.toString(), g, newValue.toString(), e.getMessage()};
            logger.error("No ha sido posible cambiar el permiso[{}], del grupo"
                    + "[{}] por el nuevo valor [{}]. Exception: [{}]", errInfo);
            return "Error";
        }
    }

    //<editor-fold defaultstate="collapsed" desc="lookup">
    private GruposLocal lookupGruposLocal() {
        try {
            Context c = new InitialContext();
            return (GruposLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/Grupos!com.openlopd.business.seguridad.GruposLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean GruposLocal.");
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>
}
