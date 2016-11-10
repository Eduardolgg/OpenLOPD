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

import com.elgg.utils.Calendar.ManejadorFechas;
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.empresas.Persona;
import com.openlopd.sessionbeans.empresas.PersonaFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import com.jkingii.mail.utils.GenKey;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Eduardo L. García Glez.
 */
public class CAddData implements Serializable {
    PersonaFacadeLocal personaFacade = lookupPersonaFacadeLocal();
    private static Logger logger = LoggerFactory.getLogger(CAddData.class);

    private String nombre;
    private String apellido1;
    private String apellido2;
    private String dni;
    private String fInicio;
    private String fFin;
    private String perfil;
    private String telefono;
    private String email;
//    private String perContacto;
    private String autorizadoSalidaSoportes;
    private String autorizadoEntradaSoportes;
    private String autorizadoCopiaReproduccion;
    private CSession session;

    public CAddData() {
        
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setfInicio(String fInicio) {
        this.fInicio = fInicio;
    }

    public void setfFin(String fFin) {
        this.fFin = fFin;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public void setPerContacto(String perContacto) {
//        this.perContacto = perContacto;
//    }    

    public String getAutorizadoSalidaSoportes() {
        return autorizadoSalidaSoportes;
    }

    public void setAutorizadoSalidaSoportes(String autorizadoSalidaSoportes) {
        this.autorizadoSalidaSoportes = autorizadoSalidaSoportes;
    }

    public String getAutorizadoEntradaSoportes() {
        return autorizadoEntradaSoportes;
    }

    public void setAutorizadoEntradaSoportes(String autorizadoEntradaSoportes) {
        this.autorizadoEntradaSoportes = autorizadoEntradaSoportes;
    }

    public void setAutorizadoCopiaReproduccion(String autorizadoCopiaReproduccion) {
        this.autorizadoCopiaReproduccion = autorizadoCopiaReproduccion;
    }

    public void setSession(CSession session) {
        this.session = session;
    }
       
    public String getId() {        
        try {
            Persona p = new Persona(
                    new Empresa(session.getAccessInfo().getSubEmpresa().getIdEmpresa()), 
                    nombre, apellido1, apellido2, 
                    new Timestamp(ManejadorFechas.deStringToDateShortTime(fInicio, 
                        session.getAccessInfo().getTimeZone()).getTime()),
                    perfil, telefono, email, 
                    /*perContacto != null && perContacto.toUpperCase().equals("ON") ? true : */false);
            p.setDni(dni);
            p.setfFin(fFin != null ? new Timestamp(ManejadorFechas.deStringToDateShortTime(fFin, 
                        session.getAccessInfo().getTimeZone()).getTime()) : null);
            p.setAutorizadoSalidaSoportes(autorizadoSalidaSoportes != null 
                    && autorizadoSalidaSoportes.toUpperCase().equals("ON") ? true : false);
            p.setAutorizadoEntradaSoportes(autorizadoEntradaSoportes != null 
                    && autorizadoEntradaSoportes.toUpperCase().equals("ON") ? true : false);
            p.setAutorizadoCopiaReproduccion(autorizadoCopiaReproduccion != null 
                    && autorizadoCopiaReproduccion.toUpperCase().equals("ON") ? true : false);
            p.setId(GenKey.newKey());
            personaFacade.create(session.getAccessInfo(), p);
            return p.getId();
        } catch (ParseException e) {
            logger.error("en CAddData.getId: Error convirtiendo la fecha fAlta["
                    + fInicio + "] fBaja[" + fFin + "]. Exception: " + e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            //TODO: Gestionar errores de DataTable Editable
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
