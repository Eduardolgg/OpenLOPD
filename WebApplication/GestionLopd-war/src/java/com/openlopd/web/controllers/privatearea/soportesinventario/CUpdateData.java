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

package com.openlopd.web.controllers.privatearea.soportesinventario;

import com.elgg.utils.Calendar.ManejadorFechas;
import com.openlopd.entities.lopd.ModoDestruccion;
import com.openlopd.entities.lopd.Soporte;
import com.openlopd.sessionbeans.lopd.SoporteFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
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
public class CUpdateData implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(CUpdateData.class);

    SoporteFacadeLocal soporteFacade = lookupSoporteFacadeLocal();
    private String id;
    private String value;
    private String rowId;
    private String columnPosition;
    private String columnId;
    private String columnName;
    private CSession session;
    private String descripcion;
    private String fAlta;
    private String fBaja;
    private String modoDestruccion;
    private String etiqueta;
    private String observaciones;
    private String sn;

    public CUpdateData() {
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setColumnPosition(String columnPosition) {
        this.columnPosition = columnPosition;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public void setSession(CSession session) {
        this.session = session;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setfAlta(String fAlta) {
        this.fAlta = fAlta;
    }

    public void setfBaja(String fBaja) {
        this.fBaja = fBaja;
    }

    public void setModoDestruccion(String modoDestruccion) {
        this.modoDestruccion = modoDestruccion;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getUpdate() {
        Soporte s;
        try {//TODO: Terminar con el update
            if (value != null) {
//                p = plantillaFacade.find(id);
//                p.setFechaBaja(ManejadorFechas.deStringToDateShortTime(value, session.getAccessInfo().getTimeZone()).getTime());
//                plantillaFacade.edit(p);
//                return value;
            } else {
                s = soporteFacade.find(id);
                
                if (!session.getAccessInfo().getSubEmpresa().equals(s.getEmpresa())) {
                    return null;
                }
                
                s.setDescripcion(descripcion);
                s.setEtiqueta(etiqueta);
                s.setFechaAlta(ManejadorFechas.deStringToDateShortTime(fAlta, 
                        session.getAccessInfo().getTimeZone()).getTime());
                s.setFechaBaja(fBaja != null && !fBaja.isEmpty() ? ManejadorFechas.deStringToDateShortTime(fBaja, 
                        session.getAccessInfo().getTimeZone()).getTime() : null);
                s.setModoDestruccion(modoDestruccion != null ? new ModoDestruccion(modoDestruccion) : null);
                s.setFechaBajaInt(fBaja != null && !fBaja.isEmpty() ? new Date().getTime() : null);
                s.setSn(sn);
                s.setObservaciones(observaciones != null && !observaciones.isEmpty() ? observaciones : s.getObservaciones());
                soporteFacade.edit(session.getAccessInfo(), s);
                return s.getId();
            }
        } catch (ParseException e) {
            logger.error("Error convirtiendo la fecha fAlta["
                    + fAlta + "] fBaja[" + fBaja + "]. Exception: " + e.getMessage());
        } catch (Exception e) {
            logger.error("En getUpdate error actualizando");
        }
        return "";
    }

    private SoporteFacadeLocal lookupSoporteFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (SoporteFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/SoporteFacade!com.openlopd.sessionbeans.lopd.SoporteFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean SoporteFacade.");
            throw new RuntimeException(ne);
        }
    }
}
