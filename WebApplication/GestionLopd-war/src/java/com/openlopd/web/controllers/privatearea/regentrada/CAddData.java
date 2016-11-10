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

import com.elgg.utils.Calendar.ManejadorFechas;
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.empresas.Persona;
import com.openlopd.entities.lopd.RegistroEntradaSoporte;
import com.openlopd.entities.lopd.TipoSoporte;
import com.openlopd.sessionbeans.lopd.RegistroEntradaSoporteFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import com.jkingii.mail.utils.GenKey;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
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

    private static Logger logger = LoggerFactory.getLogger(CAddData.class);
    RegistroEntradaSoporteFacadeLocal registroEntradaSoporteFacade = lookupRegistroEntradaSoporteFacadeLocal();
    private String tipoSoporte;
    private String observaciones;
    private String fEntrada;
    private Integer cantidad;
    private String tipoInformacion;
    private String destinatario;
    private String personaQueEntrega;
    private String modoEnvio;
    private CSession session;

    public CAddData() {
    }

    public void setTipoSoporte(String tipoSoporte) {
        this.tipoSoporte = tipoSoporte;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setfEntrada(String fEntrada) {
        this.fEntrada = fEntrada;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setTipoInformacion(String tipoInformacion) {
        this.tipoInformacion = tipoInformacion;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public void setPersonaQueEntrega(String personaQueEntrega) {
        this.personaQueEntrega = personaQueEntrega;
    }

    public void setModoEnvio(String modoEnvio) {
        this.modoEnvio = modoEnvio;
    }

    public void setSession(CSession session) {
        this.session = session;
    }

    public String getId() {
        try {
            RegistroEntradaSoporte d = new RegistroEntradaSoporte(GenKey.newKey(),
                    new Empresa(session.getAccessInfo().getSubEmpresa().getIdEmpresa()),
                    new TipoSoporte(tipoSoporte), observaciones, Boolean.FALSE,
                    ManejadorFechas.deStringToDateShortTime(fEntrada, 
                        session.getAccessInfo().getTimeZone()).getTime(), cantidad, tipoInformacion,
                    new Persona(destinatario), personaQueEntrega, modoEnvio,
                    new Date().getTime(), null);
            registroEntradaSoporteFacade.create(session.getAccessInfo(), d);
            return d.getId();
        } catch (ParseException e) {
            logger.error("en CAddData.getId: Error convirtiendo la fecha fAlta["
                    + fEntrada + "]. Exception: " + e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            //TODO: Gestionar errores de DataTable Editable
        }
        return "";
    }

    private RegistroEntradaSoporteFacadeLocal lookupRegistroEntradaSoporteFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (RegistroEntradaSoporteFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/RegistroEntradaSoporteFacade!com.openlopd.sessionbeans.lopd.RegistroEntradaSoporteFacadeLocal");
        } catch (NamingException ne) {
            logger.error("lookupRegistroEntradaSoporteFacadeLocal ERROR: {}", ne.getMessage());
            throw new RuntimeException(ne);
        }
    }
}
