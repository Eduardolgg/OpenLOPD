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

package com.openlopd.web.controllers.privatearea.incidencias;

import com.elgg.utils.Calendar.ManejadorFechas;
import com.openlopd.entities.documentos.FileDataBase;
import com.openlopd.entities.lopd.Incidencia;
import com.openlopd.sessionbeans.lopd.IncidenciaFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import java.io.Serializable;
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

    private static Logger logger = LoggerFactory.getLogger(CUpdateData.class);
    IncidenciaFacadeLocal incidenciaFacade = lookupIncidenciaFacadeLocal();
    private String id;
    private String fIncidencia;
    private Boolean deteccion;
    private String tipoIncidencia;
    private String notificadoPor;
    private String notificadoA;
    private String efectosDerivados;
    private String medidasCorrectoras;
    private String sistemasAfectados;
    // Propiedades para Medidas de nivel Medio
    private String personaEjecutora;
    private String datosRestaurados;
    private String datosRestauradosManualmente;
    private String protocoloUtilizado;
    private String fileid;
    private String value;
    private String rowId;
    private String columnPosition;
    private String columnId;
    private String columnName;
    private CSession session;

    public CUpdateData() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setfIncidencia(String fIncidencia) {
        this.fIncidencia = fIncidencia;
    }

    public void setDeteccion(Boolean deteccion) {
        this.deteccion = deteccion;
    }

    public void setTipoIncidencia(String tipoIncidencia) {
        this.tipoIncidencia = tipoIncidencia;
    }

    public void setNotificadoPor(String notificadoPor) {
        this.notificadoPor = notificadoPor;
    }

    public void setNotificadoA(String notificadoA) {
        this.notificadoA = notificadoA;
    }

    public void setEfectosDerivados(String efectosDerivados) {
        this.efectosDerivados = efectosDerivados;
    }

    public void setMedidasCorrectoras(String medidasCorrectoras) {
        this.medidasCorrectoras = medidasCorrectoras;
    }

    public void setSistemasAfectados(String sistemasAfectados) {
        this.sistemasAfectados = sistemasAfectados;
    }

    public void setPersonaEjecutora(String personaEjecutora) {
        this.personaEjecutora = personaEjecutora;
    }

    public void setDatosRestaurados(String datosRestaurados) {
        this.datosRestaurados = datosRestaurados;
    }

    public void setDatosRestauradosManualmente(String datosRestauradosManualmente) {
        this.datosRestauradosManualmente = datosRestauradosManualmente;
    }

    public void setProtocoloUtilizado(String protocoloUtilizado) {
        this.protocoloUtilizado = protocoloUtilizado;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public void setColumnPosition(String columnPosition) {
        this.columnPosition = columnPosition;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setSession(CSession session) {
        this.session = session;
    }

    public String getUpdate() {
        Incidencia i;
        try {//TODO: Terminar con el update
            if (value != null) {
//                p = plantillaFacade.find(id);
//                p.setFechaBaja(ManejadorFechas.deStringToDateShortTime(value, session.getAccessInfo().getTimeZone()).getTime());
//                plantillaFacade.edit(p);
//                return value;
            } else {
                i = incidenciaFacade.find(id);

                if (!session.getAccessInfo().getSubEmpresa().equals(i.getEmpresa())) {
                    return null;
                }

                i.setTipoIncidencia(tipoIncidencia);
                i.setFechaIncidencia(ManejadorFechas.deStringToDateShortTime(fIncidencia, 
                        session.getAccessInfo().getTimeZone()).getTime());
                i.setDeteccion(deteccion);
                i.setNotificadoPor(notificadoPor);
                i.setNotificadoA(notificadoA);
                i.setSistemaAfectado(sistemasAfectados);
                i.setEfectosDerivados(efectosDerivados);
                i.setMedidasCorrectoras(medidasCorrectoras);
                i.setPersonaEjecutora(personaEjecutora);
                i.setDatosRestaurados(datosRestaurados);
                i.setDatosRestauradosManualmente(datosRestauradosManualmente);
                i.setProtocoloUtilizado(protocoloUtilizado);
                i.setAutorizacion(fileid != null && !fileid.isEmpty() ? 
                        new FileDataBase(fileid): i.getAutorizacion());                
                incidenciaFacade.edit(session.getAccessInfo(), i);
                return i.getId();
            }        
        } catch (ParseException e) {
            logger.error("en CAddData.getId: Error convirtiendo la fecha fIncidencia["
                    + fIncidencia + "]. Exception: " + e.getMessage());
        } catch (Exception e) {
            logger.error("En getUpdate error actualizando");
        }
        return "";
    }

    private IncidenciaFacadeLocal lookupIncidenciaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (IncidenciaFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/IncidenciaFacade!com.openlopd.sessionbeans.lopd.IncidenciaFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean IncidenciaFacade.");
            throw new RuntimeException(ne);
        }
    }
}
