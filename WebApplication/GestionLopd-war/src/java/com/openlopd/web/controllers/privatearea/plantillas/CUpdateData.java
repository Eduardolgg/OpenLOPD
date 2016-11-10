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

package com.openlopd.web.controllers.privatearea.plantillas;

import com.elgg.utils.Calendar.ManejadorFechas;
import com.openlopd.entities.documentos.FileDataBase;
import com.openlopd.entities.documentos.Plantilla;
import com.openlopd.sessionbeans.documentos.PlantillaFacadeLocal;
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
    private static Logger logger = LoggerFactory.getLogger(CUpdateData.class);

    PlantillaFacadeLocal plantillaFacade = lookupPlantillaFacadeLocal();
    private String id;
    private String value;
    private String nombre;
    private String descripcion;
    private String version;
    private String fAlta;
    private String fBaja;
    private String fileid;
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

    public void setFileid(String fileid) {
        this.fileid = fileid;
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

    public void setVersion(String version) {
        this.version = version;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUpdate() {
        Plantilla p;
        try {//TODO: Terminar con el update
            if (value != null) {
                p = plantillaFacade.find(id);
                p.setFechaBaja(ManejadorFechas.deStringToDateShortTime(value, 
                        session.getAccessInfo().getTimeZone()).getTime());
                plantillaFacade.edit(session.getAccessInfo(), p);
                return value;
            } else {
                p = plantillaFacade.find(id);
                p.setNombre(nombre);
                p.setDescripcion(descripcion);
                p.setFechaAlta(ManejadorFechas.deStringToDateShortTime(fAlta, 
                        session.getAccessInfo().getTimeZone()).getTime());
                p.setFechaBaja(fBaja != null && !fBaja.isEmpty() ? 
                        ManejadorFechas.deStringToDateShortTime(fBaja, 
                        session.getAccessInfo().getTimeZone()).getTime(): null);
                p.setVersion(version);
                p.setDocumento(new FileDataBase(ExtractEditID.getId(fileid)));
                plantillaFacade.edit(session.getAccessInfo(), p);
                return p.getId();
            }

        } catch (ParseException e) {
            logger.error("Error convirtiendo la fecha fAlta["
                    + fAlta + "] fBaja[" + fBaja + "]. Exception: " + e.getMessage());
        } catch (Exception e) {
            logger.error("En getUpdate error actualizando id[{}]", id);
        }
        return "";
    }

    private PlantillaFacadeLocal lookupPlantillaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (PlantillaFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/PlantillaFacade!com.openlopd.sessionbeans.documentos.PlantillaFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean PlantillaFacade.");
            throw new RuntimeException(ne);
        }
    }
}
