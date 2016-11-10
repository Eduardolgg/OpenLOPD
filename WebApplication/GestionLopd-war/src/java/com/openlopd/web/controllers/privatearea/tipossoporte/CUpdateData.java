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

package com.openlopd.web.controllers.privatearea.tipossoporte;

import com.elgg.utils.Calendar.ManejadorFechas;
import com.openlopd.entities.lopd.TipoSoporte;
import com.openlopd.sessionbeans.lopd.TipoSoporteFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import com.utils.data.ExtractEditID;
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
    TipoSoporteFacadeLocal tipoSoporteFacade = lookupTipoSoporteFacadeLocal();
    private static Logger logger = LoggerFactory.getLogger(CUpdateData.class);

    private String id;
    private String value;
    private String nombre;
    private String descripcion;
    private String fAlta;
    private String fBaja;
    private CSession session;

    public CUpdateData() {
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

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSession(CSession session) {
        this.session = session;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUpdate() {
        TipoSoporte ts;
        try {//TODO: Terminar con el update
            if (value != null) {
//                p = plantillaFacade.find(id);
//                p.setFechaBaja(ManejadorFechas.deStringToDateShortTime(value, session.getAccessInfo().getTimeZone()).getTime());
//                plantillaFacade.edit(p);
//                return value;
            } else {
                ts = tipoSoporteFacade.find(ExtractEditID.getId(id));
                
                if (!session.getAccessInfo().getSubEmpresa().equals(ts.getEmpresa())) {
                    return null;
                }
                
                ts.setNombre(nombre);
                ts.setDescripcion(descripcion);
                ts.setFechaAlta(ManejadorFechas.deStringToDateShortTime(fAlta, 
                        session.getAccessInfo().getTimeZone()).getTime());
                ts.setFechaBaja(fBaja != null && !fBaja.isEmpty() ? ManejadorFechas
                        .deStringToDateShortTime(fBaja, 
                        session.getAccessInfo().getTimeZone()).getTime() : null);
                tipoSoporteFacade.edit(session.getAccessInfo(), ts);
                return ts.getId();
            }
        } catch (ParseException e) {
            logger.error("Error convirtiendo la fecha fAlta["
                    + fAlta + "] fBaja[" + fBaja + "]. Exception: " + e.getMessage());
        } catch (Exception e) {
            logger.error("En getUpdate error actualizando");
        }
        return "";
    }

    private TipoSoporteFacadeLocal lookupTipoSoporteFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (TipoSoporteFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/TipoSoporteFacade!com.openlopd.sessionbeans.lopd.TipoSoporteFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el bean de sesion TipoSoporteFacade.");
            throw new RuntimeException(ne);
        }
    }
}
