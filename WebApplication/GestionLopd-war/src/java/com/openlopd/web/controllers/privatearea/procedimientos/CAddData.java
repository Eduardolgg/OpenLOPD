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

package com.openlopd.web.controllers.privatearea.procedimientos;

import com.openlopd.entities.lopd.ProcedimientoDisponible;
import com.openlopd.entities.lopd.TipoNivelSeguridad;
import com.openlopd.entities.lopd.TipoProcedimiento;
import com.openlopd.entities.lopd.TipoProcedimientoPK;
import com.openlopd.sessionbeans.lopd.ProcedimientoDisponibleFacadeLocal;
import com.openlopd.sessionbeans.lopd.TipoProcedimientoFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import com.jkingii.mail.utils.GenKey;
import java.io.Serializable;
import java.util.logging.Level;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Eduardo L. García Glez.
 */
public class CAddData implements Serializable {
    TipoProcedimientoFacadeLocal tipoProcedimientoFacade = lookupTipoProcedimientoFacadeLocal();
    ProcedimientoDisponibleFacadeLocal procedimientoDisponibleFacade = lookupProcedimientoDisponibleFacadeLocal();
    private static Logger logger = LoggerFactory.getLogger(CAddData.class);
    private String idProtocolo;
    private String tipoDescripcion;
    private String tipoTitulo;
    private String procDescripcion;
    private String protocolo;
    private String nivel;
    private CSession session;

    public CAddData() {
    }

    public void setIdProtocolo(String idProtocolo) {
        this.idProtocolo = idProtocolo;
    }

    public void setTipoDescripcion(String tipoDescripcion) {
        this.tipoDescripcion = tipoDescripcion;
    }

    public void setProcDescripcion(String procDescripcion) {
        this.procDescripcion = procDescripcion;
    }

    public void setTipoTitulo(String tipoTitulo) {
        this.tipoTitulo = tipoTitulo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public void setSession(CSession session) {
        this.session = session;
    }
    
    public String toJson(ProcedimientoDisponible pd) throws JSONException {
        JSONObject js = new JSONObject();        
        js.put("id", pd.getId());
        js.put("descripcion", pd.getDescripcion());
        js.put("nivel", pd.getNivel());
        js.put("procedimiento", pd.getProcedimiento());
        js.put("status", "ok");
        return js.toString();
    }

    public String getId() {
        try {
            TipoProcedimiento tp = new TipoProcedimiento(
                    new TipoProcedimientoPK(idProtocolo,
                    session.getAccessInfo().getSubEmpresa().getIdEmpresa()));
            tp.setDescripcion(tipoDescripcion);
            tp.setUserName(session.getAccessInfo().getUserInfo().getUsuario());
            tp.setTitulo(tipoTitulo);
            
            // TODO: ojo con que las subempresas modifiquen cosas de aquí.
//            TipoProcedimiento tp = tipoProcedimientoFacade.find(new TipoProcedimientoPK(idProtocolo,
//                    session.getAccessInfo().getSubEmpresa().getIdEmpresa()));
//            if (tp != null) {
//                tp.setFechaAlta(new Date().getTime());
//                tp.setDescripcion(tipoDescripcion);
//                tp.setUserName(session.getAccessInfo().getUserInfo().getUsuario());
//                tipoProcedimientoFacade.create(tp);
//            }

            ProcedimientoDisponible pd = new ProcedimientoDisponible(GenKey.newKey(),
                    session.getAccessInfo().getSubEmpresa(),
                    tp, procDescripcion, protocolo, TipoNivelSeguridad.valueOf(nivel));

            procedimientoDisponibleFacade.create(session.getAccessInfo(), pd);
            
            return this.toJson(pd);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "{\"status\": \"Error creando el procedimiento.\"}";
        }
    }

    private ProcedimientoDisponibleFacadeLocal lookupProcedimientoDisponibleFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ProcedimientoDisponibleFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/ProcedimientoDisponibleFacade!com.openlopd.sessionbeans.lopd.ProcedimientoDisponibleFacadeLocal");
        } catch (NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private TipoProcedimientoFacadeLocal lookupTipoProcedimientoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (TipoProcedimientoFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/TipoProcedimientoFacade!com.openlopd.sessionbeans.lopd.TipoProcedimientoFacadeLocal");
        } catch (NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
