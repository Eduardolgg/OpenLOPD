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

package com.openlopd.web.controllers.privatearea.regentrada;

import com.openlopd.entities.lopd.Incidencia;
import com.openlopd.sessionbeans.lopd.IncidenciaFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Eduardo L. García Glez.
 */
public class CDeleteData implements Serializable {

    IncidenciaFacadeLocal incidenciaFacade = lookupIncidenciaFacadeLocal();
    private String id;
    private CSession session;

    public CDeleteData() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSession(CSession session) {
        this.session = session;
    }

    /**
     * Elimina una fila
     * @return <code>true</code> si la fila es eliminada correctemanete, 
     * <code>false</code> en caso contrario.
     */
    public String getDelete() {
        try {//TODO: Permisos de borrado, este sistema es inseguro cualquiera con un ID puede borrar.
            Incidencia i = incidenciaFacade.find(id);
            incidenciaFacade.remove(session.getAccessInfo(), i);
            return "ok";
        } catch (Exception e) {
//            StandardLogger.error("En getDelete, error borrando la fila", this);
            return e.getMessage();
        }
    }

    private IncidenciaFacadeLocal lookupIncidenciaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (IncidenciaFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/IncidenciaFacade!com.openlopd.sessionbeans.lopd.IncidenciaFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
