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
import com.openlopd.business.seguridad.SeguridadLocal;
import com.openlopd.entities.empresas.Persona;
import com.openlopd.sessionbeans.empresas.PersonaFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
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
public class CUpdateData implements Serializable {
    SeguridadLocal seguridad = lookupSeguridadLocal();

    PersonaFacadeLocal personaFacade = lookupPersonaFacadeLocal();
    private static Logger logger = LoggerFactory.getLogger(CUpdateData.class);
    private String id;
    private String value;
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
    //Para creación de usuarios:
    private String esUsuario;
    private String grupos;
    private String ficheros;
    private CSession session;

    public CUpdateData() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
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

    public void setAutorizadoSalidaSoportes(String autorizadoSalidaSoportes) {
        this.autorizadoSalidaSoportes = autorizadoSalidaSoportes;
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

    public void setEsUsuario(String esUsuario) {
        this.esUsuario = esUsuario;
    }

    public void setGrupos(String grupos) {
        this.grupos = grupos;
    }

    public void setFicheros(String ficheros) {
        this.ficheros = ficheros;
    }

    public String getUpdate() {
        Persona p;
        try {//TODO: Terminar con el update
            if (value != null) {
//                p = plantillaFacade.find(id);
//                p.setFechaBaja(ManejadorFechas.deStringToDateShortTime(value, session.getAccessInfo().getTimeZone()).getTime());
//                plantillaFacade.edit(p);
//                return value;
            } else {
                p = personaFacade.find(id);

                if (!session.getAccessInfo().getSubEmpresa().equals(p.getEmpresa())) {
                    return null;
                }

                p.setNombre(nombre);
                p.setApellido1(apellido1);
                p.setApellido2(apellido2);  
                p.setDni(dni);
                p.setfInicio(new Timestamp(ManejadorFechas.deStringToDateShortTime(fInicio, 
                        session.getAccessInfo().getTimeZone()).getTime()));
                p.setfFin(fFin != null && !fFin.isEmpty() ? new Timestamp(
                        ManejadorFechas.deStringToDateShortTime(fFin, 
                        session.getAccessInfo().getTimeZone()).getTime()) : null);
                p.setPerfil(perfil);
                p.setTelefono(telefono);
                p.setEmail(email);
                p.setUsuario(esUsuario != null && esUsuario.equals("ON") ? email : null);
//                p.setPerContacto(perContacto != null 
//                        && perContacto.toUpperCase().equals("ON") ? true : false);
                p.setAutorizadoSalidaSoportes(autorizadoSalidaSoportes != null 
                        && autorizadoSalidaSoportes.toUpperCase().equals("ON") ? true : false);
                p.setAutorizadoEntradaSoportes(autorizadoEntradaSoportes != null 
                        && autorizadoEntradaSoportes.toUpperCase().equals("ON") ? true : false);
                p.setAutorizadoCopiaReproduccion(autorizadoCopiaReproduccion != null 
                        && autorizadoCopiaReproduccion.toUpperCase().equals("ON") ? true : false);
                personaFacade.edit(session.getAccessInfo(), p, ficheros);
                
                if (esUsuario != null && esUsuario.equals("ON")) {
                    seguridad.activeUser(session.getAccessInfo(), email, grupos);
                } else {
                    seguridad.desactiveUser(session.getAccessInfo(), email);
                }
                
                return "Operación Completada";
            }
        } catch (ParseException e) {
            logger.error("Error convirtiendo la fecha fInicio["
                    + fInicio + "] fFin[" + fFin + "]. Exception: " + e.getMessage());
        } catch (Exception e) {
            logger.error("En getUpdate error actualizando");
        }
        return "Error actualizando";
    }

    private PersonaFacadeLocal lookupPersonaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (PersonaFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/PersonaFacade!com.openlopd.sessionbeans.empresas.PersonaFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session besan lookupPersonaFacadeLocal.");
            throw new RuntimeException(ne);
        }
    }

    private SeguridadLocal lookupSeguridadLocal() {
        try {
            Context c = new InitialContext();
            return (SeguridadLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/Seguridad!com.openlopd.business.seguridad.SeguridadLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session besan lookupSeguridadLocal.");
            throw new RuntimeException(ne);
        }
    }
}
