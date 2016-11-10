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

package com.openlopd.web.controllers.privatearea.regaccesos;

import com.elgg.utils.Calendar.ManejadorFechas;
import com.openlopd.entities.lopd.RegistroAcceso;
import com.openlopd.entities.lopd.TipoAccesoDocumento;
import com.openlopd.sessionbeans.empresas.PersonaFacadeLocal;
import com.openlopd.sessionbeans.lopd.RegistroAccesoFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import java.io.Serializable;
import java.text.ParseException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controla el añadido de entities de registro de acceso.
 * @author Eduardo L. García Glez.
 */
public class CAddData implements Serializable {
    PersonaFacadeLocal personaFacade = lookupPersonaFacadeLocal();
    RegistroAccesoFacadeLocal registroAccesoFacade = lookupRegistroAccesoFacadeLocal();
    private static Logger logger = LoggerFactory.getLogger(CAddData.class);
    private String usuario;
    private String idPersona;
    private String fechaAcceso;
    private String fichero;
    private String tipoAcceso;
    private String autorizado;
    private CSession session;

    public CAddData() {
    }    

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public void setFechaAcceso(String fechaAcceso) {
        this.fechaAcceso = fechaAcceso;
    }

    public void setFichero(String fichero) {
        this.fichero = fichero;
    }

    public void setTipoAcceso(String tipoAcceso) {
        this.tipoAcceso = tipoAcceso;
    }

    public void setAutorizado(String autorizado) {
        this.autorizado = autorizado;
    }

    public void setSession(CSession session) {
        this.session = session;
    }

    public String getId() {
        try {
            RegistroAcceso r = new RegistroAcceso(personaFacade.find(idPersona),
                    ManejadorFechas.deStringToDateShortTime(fechaAcceso, 
                        session.getAccessInfo().getTimeZone()).getTime(),
                    fichero, TipoAccesoDocumento.valueOf(tipoAcceso), 
                    autorizado != null && autorizado.equals("SI") ? Boolean.TRUE : Boolean.FALSE);
            registroAccesoFacade.create(session.getAccessInfo(), r);
            return r.getId();
        } catch (ParseException e) {
            logger.error("en CAddData.getId: Error convirtiendo la fecha fechaAcceso["
                    + fechaAcceso + "]. Exception: " + e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            //TODO: Gestionar errores de DataTable Editable
        }
        return "";
    }

    private RegistroAccesoFacadeLocal lookupRegistroAccesoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (RegistroAccesoFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/RegistroAccesoFacade!com.openlopd.sessionbeans.lopd.RegistroAccesoFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean lookupRegistroAccesoFacadeLocal.");
            throw new RuntimeException(ne);
        }
    }

    private PersonaFacadeLocal lookupPersonaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (PersonaFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/PersonaFacade!com.openlopd.sessionbeans.empresas.PersonaFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean lookupPersonaFacadeLocal.");
            throw new RuntimeException(ne);
        }
    }

}
