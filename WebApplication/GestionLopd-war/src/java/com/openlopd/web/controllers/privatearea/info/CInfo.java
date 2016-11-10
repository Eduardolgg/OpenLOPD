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

package com.openlopd.web.controllers.privatearea.info;

import com.openlopd.business.seguridad.SeguridadLocal;
import com.openlopd.entities.empresas.Persona;
import com.openlopd.sessionbeans.empresas.PersonaFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import com.openlopd.common.localizacion.sessionbeans.LocalidadFacadeLocal;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Obtiene información para las plantillas en las llamadas ajax.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 11 de nov de 2012
 */
public class CInfo implements Serializable {    
    SeguridadLocal seguridad = lookupSeguridadLocal();
    PersonaFacadeLocal personaFacade = lookupPersonaFacadeLocal();
    LocalidadFacadeLocal localidadFacade = lookupLocalidadFacadeLocal();
    
    private static Logger logger = LoggerFactory.getLogger(CInfo.class);
    private String term;
    private String i;
    private CSession session;

    /**
     * Constructor por defecto
     */
    public CInfo() {
    }

    /**
     * Establece la información de la sesión del usuario.
     * @param session Información de la sesión del usuario.
     */
    public void setSession(CSession session) {
        this.session = session;
    }

    /**
     * Información a obtener.
     *
     * Por ejemplo i=personas obtiene información de las personas de la emrpesa.
     *
     * @param i tipo de información a obtener.
     */
    public void setI(String i) {
        this.i = i;
    }

    /**
     * Término de búsqueda.
     *
     * @param term Término de búsqueda.
     */
    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * Obtiene la información a devolver al la solicitud ajax.
     *
     * @return información solicitada en formato json.
     *
     * [ { id:"id", label: "Choice1", value: "value1" }, ... ]
     *
     */
    public String getInfo() {
        switch (i) {
            case "personas":
                return this.getNombrePersona();
            case "localidades":
                return this.getLocalidades();
            case "status":
                return this.getStatus();
            default:
                logger.error("Identificador [{}] no encontrado", i);
                return "error";
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Private">
    /**
     * Listado de personas (JSON).
     *
     * Obtiene un listado de personas filtrado por el valor
     * actual de term.
     *
     * @return JSON con el listado de personas.
     */
    private String getNombrePersona() {
        JSONArray ja = new JSONArray();
        JSONObject js = null;
        
        List<Persona> lp = personaFacade.findAll(session.getAccessInfo(), term);
        try {
            
            for (Persona p : lp) {
                js = new JSONObject();
                js.put("id", p.getId());
                js.put("label", p.getNombreCompleto());
                js.put("value", p.getNombreCompleto());
                ja.put(js);
            }
            //Thread.sleep(5000);
            return ja.toString();
        } catch (Exception e) {
            logger.error("Imposible obtener los nombres de persona para la "
                    + "empresa [{}]", session.getAccessInfo().getSubEmpresa());
        }
        return "[]";
    }
    
    /**
     * Listado de localidades (JSON).
     *
     * Devuelve un listado de localidades filtrados por el valor
     * actual de term.
     *
     * @return JSON con el listado de las localidades.
     */
    private String getLocalidades() {
        JSONArray ja = new JSONArray();
        JSONObject js = null;
        
        List<String> ll = localidadFacade.findAll(term);
        try {
            
            for (String l : ll) {
                js = new JSONObject();
                //js.put("id", l.getId());
                js.put("label", l);
                js.put("value", l);
                ja.put(js);
            }
            //Thread.sleep(5000);
            return ja.toString();
        } catch (Exception e) {
            logger.error("Imposible obtener las provincias");
        }
        return "[]";
    }
    
    private String getStatus() {
        seguridad.updateUserLastAccess(session.getAccessInfo());
        return session.getAccessInfo() != null ? "" : "error";
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="lookup">
    private PersonaFacadeLocal lookupPersonaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (PersonaFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/PersonaFacade!com.openlopd.sessionbeans.empresas.PersonaFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean PersonaFacade");
            throw new RuntimeException(ne);
        }
    }
    
    private LocalidadFacadeLocal lookupLocalidadFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (LocalidadFacadeLocal) c.lookup("java:global/GestionLopd/common-ejb/LocalidadFacade!com.openlopd.common.localizacion.sessionbeans.LocalidadFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean LocalidadFacade");
            throw new RuntimeException(ne);
        }
    }

    private SeguridadLocal lookupSeguridadLocal() {
        try {
            Context c = new InitialContext();
            return (SeguridadLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/Seguridad!com.openlopd.business.seguridad.SeguridadLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean SeguridadFacade.");
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>
}
