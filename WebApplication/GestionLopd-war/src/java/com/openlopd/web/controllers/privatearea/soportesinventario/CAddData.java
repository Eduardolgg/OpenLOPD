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
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.lopd.Soporte;
import com.openlopd.entities.lopd.TipoSoporte;
import com.openlopd.entities.lopd.UnidadesInformacion;
import com.openlopd.sessionbeans.lopd.SoporteFacadeLocal;
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
    SoporteFacadeLocal soporteFacade = lookupSoporteFacadeLocal();

    private String descripcion;
    private String fAlta;
    private String fBaja;
    private String etiqueta;
    private Integer capacidad;
    private Long unidadesInfo;
    private String tipo;
    private String sn;
    private String observaciones;
    private CSession session;

    public CAddData() {
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
    
    public void setSession(CSession session) {
        this.session = session;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public void setUnidadesInfo(Long unidadesInfo) {
        this.unidadesInfo = unidadesInfo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getId() {
        try {
            Soporte s = new Soporte();
            s.setId(GenKey.newKey());
            s.setDescripcion(descripcion);
            s.setEmpresa(new Empresa(session.getAccessInfo().getSubEmpresa().getIdEmpresa()));
            s.setFechaAlta(ManejadorFechas.deStringToDateShortTime(fAlta, 
                        session.getAccessInfo().getTimeZone()).getTime());
            s.setFechaAltaInt(new Date().getTime());
            s.setFechaBaja((fBaja != null ? ManejadorFechas.deStringToDateShortTime(fBaja, 
                        session.getAccessInfo().getTimeZone()).getTime() : null));
            s.setFechaBajaInt((fBaja != null ? (new Date()).getTime() : null));
            s.setEtiqueta(etiqueta);
            s.setCapacidad(capacidad);
            s.setUnidades(new UnidadesInformacion(unidadesInfo));
            s.setTipoSoporte(new TipoSoporte(tipo));
            s.setSn(sn);
            s.setObservaciones(observaciones);
            soporteFacade.create(session.getAccessInfo(), s);
            return s.getId();
        } catch (ParseException e) {
            logger.error("en CAddData.getId: Error convirtiendo la fecha fAlta["
                    + fAlta + "] fBaja[" + fBaja + "]. Exception: " + e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            //TODO: Gestionar errores de DataTable Editable
        }
        return "";
    }

    private SoporteFacadeLocal lookupSoporteFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (SoporteFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/SoporteFacade!com.openlopd.sessionbeans.lopd.SoporteFacadeLocal");
        } catch (NamingException ne) {
            logger.error("lookupSoporteFacadeLocal ERROR: {}", ne.getMessage());
            throw new RuntimeException(ne);
        }
    }
}
