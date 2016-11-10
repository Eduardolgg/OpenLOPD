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

package com.openlopd.web.controllers.privatearea.destinatarios;

import com.elgg.utils.Calendar.ManejadorFechas;
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.lopd.Destinatario;
import com.openlopd.sessionbeans.lopd.DestinatarioFacadeLocal;
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
 * Añade nuevos destinatarios de soportes.
 * @author Eduardo L. García Glez.
 */
public class CAddData implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(CAddData.class);
    DestinatarioFacadeLocal destinatarioFacade = lookupDestinatarioFacadeLocal();

    private String nombre;
    private String descripcion;
    private String fAlta;
    private String fBaja;
    private String observaciones;
    private CSession session;

    /**
     * Constructor por defecto.
     */
    public CAddData() {
        
    }

    /**
     * Obtiene la descripción del destinatario.
     * @return Descripción del destinatario.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del destinatario.
     * @param descripcion Descripción del destinatario.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Establece la fecha de alta del destinatario.
     * @return fecha de alta del destinatario.
     */
    public String getfAlta() {
        return fAlta;
    }

    /**
     * Obtiene la fecha de alta del destinatario.
     * @param fAlta fecha de alta del destinatario.
     */
    public void setfAlta(String fAlta) {
        this.fAlta = fAlta;
    }

    /**
     * Obtiene la fecha de baja del destinatario.
     * @return Fecha de baja del destinatario.
     */
    public String getfBaja() {
        return fBaja;
    }

    /**
     * Establece la fecha de baja del destinatario.
     * @param fBaja Fecha de baja del destinatario.
     */
    public void setfBaja(String fBaja) {
        this.fBaja = fBaja;
    }

    /**
     * Obtiene el nombre del destinatario.
     * @return Nombre del destinatario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del destinatario.
     * @param nombre Nombre del destinatario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene las observaciones sobre el destinatario.
     * @return Observaciones sobre el destinatario.
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * Establece las observaciones sobre el destinatario.
     * @param observaciones Observaciones sobre el destinatario.
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Establece la información de sessión del usuario.
     * @param session Información de la sessión del usuario.
     */
    public void setSession(CSession session) {
        this.session = session;
    }
       
    /**
     * Añade al nuevo destinatario al sistema.
     * @return Identificador único del destinatario en el sistema.
     */
    public String getId() {
        try {
            Destinatario d = new Destinatario(GenKey.newKey(), 
                    new Empresa(session.getAccessInfo().getSubEmpresa().getIdEmpresa()),
                    nombre, descripcion, observaciones,
                    ManejadorFechas.deStringToDateShortTime(fAlta, 
                    session.getAccessInfo().getTimeZone()).getTime(), (new Date()).getTime(),
                    (fBaja != null ? ManejadorFechas.deStringToDateShortTime(fBaja, 
                    session.getAccessInfo().getTimeZone()).getTime() : null), 
                    (fBaja != null ? (new Date()).getTime() : null));
            destinatarioFacade.create(session.getAccessInfo(), d);
            return d.getId();
        } catch (ParseException e) {
            logger.error("en CAddData.getId: Error convirtiendo la fecha fAlta["
                    + fAlta + "] fBaja[" + fBaja + "]. Exception: " + e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            //TODO: Gestionar errores de DataTable Editable
        }
        return "";
    }

    //<editor-fold defaultstate="collapsed" desc="lookup">
    private DestinatarioFacadeLocal lookupDestinatarioFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (DestinatarioFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/DestinatarioFacade!com.openlopd.sessionbeans.lopd.DestinatarioFacadeLocal");
        } catch (NamingException ne) {
            logger.error("lookupDestinatarioFacadeLocal ERROR: {}", ne.getMessage());
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>
}
