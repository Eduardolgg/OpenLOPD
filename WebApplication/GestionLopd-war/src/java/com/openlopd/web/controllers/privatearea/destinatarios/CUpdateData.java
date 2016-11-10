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
import com.openlopd.entities.lopd.Destinatario;
import com.openlopd.sessionbeans.lopd.DestinatarioFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import com.utils.data.ExtractEditID;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Actualiza los datos de destinatarios.
 * @author Eduardo L. García Glez.
 */
public class CUpdateData implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(CUpdateData.class);

    DestinatarioFacadeLocal destinatarioFacade = lookupDestinatarioFacadeLocal();
    private String id;
    private String value;
    private String nombre;
    private String descripcion;
    private String fAlta;
    private String fBaja;
    private String observaciones;
    private CSession session;

    /**
     * Constructor por defecto
     */
    public CUpdateData() {
    }

    /**
     * Establece el ID de la entidad a actualizar.
     * @param id ID de la entidad a actualizar.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * //TODO: Este sistema no se está usando.
     * @param value 
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Establece el nombre del destinatario.
     * @param nombre Nombre del destinatario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
     * 
     * Formato admitido dd/mm/yyyy hh:mm
     * 
     * @param fAlta Fecha de alta.
     */
    public void setfAlta(String fAlta) {
        this.fAlta = fAlta;
    }

    /**
     * Establece la fecha de baja del destinatario.
     * 
     * Formato admitido dd/mm/yyyy hh:mm
     * 
     * @param fBaja Fecha de baja.
     */
    public void setfBaja(String fBaja) {
        this.fBaja = fBaja;
    }

    /**
     * Establece observaciones sobre el destinatario.
     * @param observaciones Observacines sobre el destinatario.
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Establece la sesión de usuario.
     * @param session Información de la sesión del usuario.
     */
    public void setSession(CSession session) {
        this.session = session;
    }

    /**
     * Ejecuta la acción de actualización.
     * @return Id de la entidad actualizada en caso de éxito, o un mensaje
     * en caso de error.
     */
    public String getUpdate() {
        Destinatario d;
        try {//TODO: Terminar con el update
            if (value != null) {
//                p = plantillaFacade.find(id);
//                p.setFechaBaja(ManejadorFechas.deStringToDateShortTime(value, session.getAccessInfo().getTimeZone()).getTime());
//                plantillaFacade.edit(p);
//                return value;
            } else {
                d = destinatarioFacade.find(ExtractEditID.getId(id));
                
                if (!session.getAccessInfo().getSubEmpresa().equals(d.getEmpresa())) {
                    return null;
                }
                
                d.setNombre(nombre);
                d.setDescripcion(descripcion);                
                d.setFechaAlta(ManejadorFechas.deStringToDateShortTime(fAlta, 
                        session.getAccessInfo().getTimeZone()).getTime());
                d.setFechaBaja(fBaja != null && !fBaja.isEmpty() ? 
                        ManejadorFechas.deStringToDateShortTime(fBaja, 
                        session.getAccessInfo().getTimeZone()).getTime() : null);
                d.setFechaBajaInt(fBaja != null && !fBaja.isEmpty() ? new Date().getTime() : null);
                d.setObservaciones(observaciones != null && !observaciones.isEmpty() ? observaciones : d.getObservaciones());
                destinatarioFacade.edit(session.getAccessInfo(), d);
                return d.getId();
            }
        } catch (ParseException e) {
            logger.error("Error convirtiendo la fecha fAlta["
                    + fAlta + "] fBaja[" + fBaja + "]. Exception: " + e.getMessage());
        } catch (Exception e) {
            logger.error("En getUpdate error actualizando");
        }
        return "";
    }

    //<editor-fold defaultstate="collapsed" desc="lookup">
    private DestinatarioFacadeLocal lookupDestinatarioFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (DestinatarioFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/DestinatarioFacade!com.openlopd.sessionbeans.lopd.DestinatarioFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener DestinatarioFacade.");
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>
}
