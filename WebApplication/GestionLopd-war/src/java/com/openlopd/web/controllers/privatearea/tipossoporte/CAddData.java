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
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.lopd.TipoSoporte;
import com.openlopd.sessionbeans.lopd.TipoSoporteFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import com.jkingii.mail.utils.GenKey;
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
public class CAddData implements Serializable {
    TipoSoporteFacadeLocal tipoSoporteFacade = lookupTipoSoporteFacadeLocal();
    private static Logger logger = LoggerFactory.getLogger(CAddData.class);
    private String nombre;
    private String descripcion;
    private String fAlta;
    private String fBaja;
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSession(CSession session) {
        this.session = session;
    }

    public String getId() {
        try {
            TipoSoporte t = new TipoSoporte(GenKey.newKey(), nombre, descripcion,
                    new Empresa(session.getAccessInfo().getSubEmpresa().getIdEmpresa()),
                    ManejadorFechas.deStringToDateShortTime(fAlta, 
                        session.getAccessInfo().getTimeZone()).getTime(), 
                    (fBaja != null ? ManejadorFechas.deStringToDateShortTime(fBaja, 
                        session.getAccessInfo().getTimeZone()).getTime() : null));
            tipoSoporteFacade.create(session.getAccessInfo(), t);
            return t.getId();
        } catch (ParseException e) {
            logger.error("en CAddData.getId: Error convirtiendo la fecha fAlta["
                    + fAlta + "] fBaja[" + fBaja + "]. Exception: " + e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            //TODO: Gestionar errores de DataTable Editable
        }
        return "";
    }

    private TipoSoporteFacadeLocal lookupTipoSoporteFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (TipoSoporteFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/TipoSoporteFacade!com.openlopd.sessionbeans.lopd.TipoSoporteFacadeLocal");
        } catch (NamingException ne) {            
            logger.error("Imposible acceder al bean de sessión TipoSoporteFacade.");
            throw new RuntimeException(ne);
        }
    }
}
