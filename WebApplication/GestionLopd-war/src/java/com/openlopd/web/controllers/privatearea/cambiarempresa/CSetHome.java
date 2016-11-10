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

package com.openlopd.web.controllers.privatearea.cambiarempresa;

import com.openlopd.business.seguridad.SeguridadLocal;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.web.controllers.privatearea.AbstractWebPageController;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Controlador de la página setHome.jsp
 *
 * Permite cambiar la empresa actual.
 *
 * @author Eduardo L. García Glez.
 */
public class CSetHome extends AbstractWebPageController implements Serializable {
    SeguridadLocal seguridad = lookupSeguridadLocal();

    private String id;

    public CSetHome() {
        super(ColumnasPermisos.GESTION_EMPRESAS);
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean cambiarEmpresa() {
        try {
            seguridad.cambiarEmpresa(session.getAccessInfo(), id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private SeguridadLocal lookupSeguridadLocal() {
        try {
            Context c = new InitialContext();
            return (SeguridadLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/Seguridad!com.openlopd.business.seguridad.SeguridadLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
