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

package com.openlopd.web.controllers.privatearea.personas;

import com.openlopd.entities.empresas.Persona;
import com.openlopd.sessionbeans.empresas.PersonaFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import java.io.Serializable;
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

    PersonaFacadeLocal personaFacade = lookupPersonaFacadeLocal();
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
        Persona p;
        try {
            if (id != null && session != null) {

                p = personaFacade.find(id);

                if (!session.getAccessInfo().getSubEmpresa().equals(p.getEmpresa())) {
                    return "";
                }

                JSONObject js = new JSONObject();
                js.put("Nombre", p.getNombreCompleto());
                js.put("DNI/NIE", p.getDni());
                js.put("Teléfono", p.getTelefono());
                js.put("E-mail", p.getEmail());
                js.put("Está autorizado para el envío de soportes", 
                        p.getAutorizadoSalidaSoportes() != null 
                        && p.getAutorizadoSalidaSoportes() ? "Si" : "No");
                js.put("Está autorizado para recepción de soportes", 
                        p.getAutorizadoEntradaSoportes() != null 
                        && p.getAutorizadoEntradaSoportes() ? "Si" : "No");
                js.put("Está autorizado para la copia/reproducción de documentos", 
                        p.getAutorizadoCopiaReproduccion() != null 
                        && p.getAutorizadoCopiaReproduccion() ? "Si" : "No");
                return js.toString();
            }
        } catch (Exception e) {
            logger.error("En getUpdate error actualizando");
        }
        return "";
    }

    private PersonaFacadeLocal lookupPersonaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (PersonaFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/PersonaFacade!com.openlopd.sessionbeans.empresas.PersonaFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean PersonaFacade.");
            throw new RuntimeException(ne);
        }
    }
}
