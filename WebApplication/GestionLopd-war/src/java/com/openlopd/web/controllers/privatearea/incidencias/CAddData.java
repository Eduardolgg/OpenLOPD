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
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.lopd.Incidencia;
import com.openlopd.sessionbeans.lopd.ContadorIncidenciasFacadeLocal;
import com.openlopd.sessionbeans.lopd.IncidenciaFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import com.jkingii.mail.utils.GenKey;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
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
    ContadorIncidenciasFacadeLocal contadorIncidenciasFacade = lookupContadorIncidenciasFacadeLocal();

    private static Logger logger = LoggerFactory.getLogger(CAddData.class);
    IncidenciaFacadeLocal incidenciaFacade = lookupIncidenciaFacadeLocal();
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
    private CSession session;

    public CAddData() {
    }

    //<editor-fold defaultstate="collapsed" desc="Setcion Get/Set">

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

    public void setSession(CSession session) {
        this.session = session;
    }
    //</editor-fold>
    
    public String getId() {
        try {
            Incidencia i = new Incidencia(GenKey.newKey(),
                    new Empresa(session.getAccessInfo().getSubEmpresa().getIdEmpresa()),
                    tipoIncidencia, ManejadorFechas.deStringToDateShortTime(fIncidencia, 
                        session.getAccessInfo().getTimeZone()).getTime(), deteccion,
                    notificadoPor, notificadoA, sistemasAfectados, new Date().getTime());
            String codigo = contadorIncidenciasFacade.nextId(i.getEmpresa().getIdEmpresa());
            i.setCodigo(codigo);
            i.setEfectosDerivados(efectosDerivados);
            i.setMedidasCorrectoras(medidasCorrectoras);
            i.setPersonaEjecutora(personaEjecutora);
            i.setDatosRestaurados(datosRestaurados);
            i.setDatosRestauradosManualmente(datosRestauradosManualmente);
            i.setProtocoloUtilizado(protocoloUtilizado);
            i.setAutorizacion(fileid != null && !fileid.isEmpty() ? new FileDataBase(fileid) : null);
            incidenciaFacade.create(session.getAccessInfo(), i);
            return i.getId();
        } catch (ParseException e) {
            logger.error("en CAddData.getId: Error convirtiendo la fecha fechaIncidencia["
                    + fIncidencia + "]. Exception: " + e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            //TODO: Gestionar errores de DataTable Editable
        }
        return "";
    }

    private IncidenciaFacadeLocal lookupIncidenciaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (IncidenciaFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/IncidenciaFacade!com.openlopd.sessionbeans.lopd.IncidenciaFacadeLocal");
        } catch (NamingException ne) {
            logger.error("lookupIncidenciaFacadeLocal ERROR: {}", ne.getMessage());
            throw new RuntimeException(ne);
        }
    }

    private ContadorIncidenciasFacadeLocal lookupContadorIncidenciasFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ContadorIncidenciasFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/ContadorIncidenciasFacade!com.openlopd.sessionbeans.lopd.ContadorIncidenciasFacadeLocal");
        } catch (NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
