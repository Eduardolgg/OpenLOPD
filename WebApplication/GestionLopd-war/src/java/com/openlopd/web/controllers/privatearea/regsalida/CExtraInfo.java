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

package com.openlopd.web.controllers.privatearea.regsalida;

import com.openlopd.entities.lopd.RegistroSalidaSoporte;
import com.openlopd.sessionbeans.lopd.RegistroSalidaSoporteFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import java.io.Serializable;
import java.util.logging.Level;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controla el acceso a la información extra de las tablas.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 de 07 de oct de 2012
 */
public class CExtraInfo implements Serializable {

    RegistroSalidaSoporteFacadeLocal registroSalidaSoporteFacade = lookupRegistroSalidaSoporteFacadeLocal();
    private static Logger logger = LoggerFactory.getLogger(CExtraInfo.class);
    private String id;
    private CSession session;

    public CExtraInfo() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSession(CSession session) {
        this.session = session;
    }

    public String getExtraInfo() {
        RegistroSalidaSoporte rs;
        try {
            if (id != null && session != null) {

                rs = registroSalidaSoporteFacade.find(id);

                if (!session.getAccessInfo().getSubEmpresa().equals(rs.getEmpresa())) {
                    return "";
                }

                JSONObject js = new JSONObject();
                js.put("Modo de envío", rs.getModoEnvio());
                js.put("Persona Autorizada", rs.getPersonaAutorizada().getNombreCompleto() );
                return js.toString();
            }
        } catch (Exception e) {
            logger.error("En getUpdate error actualizando");
        }
        return "";
    }

    private RegistroSalidaSoporteFacadeLocal lookupRegistroSalidaSoporteFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (RegistroSalidaSoporteFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/RegistroSalidaSoporteFacade!com.openlopd.sessionbeans.lopd.RegistroSalidaSoporteFacadeLocal");
        } catch (NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
