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

import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.entities.lopd.ProcedimientoDisponible;
import com.openlopd.entities.lopd.ProcedimientoHabilitado;
import com.openlopd.entities.lopd.ProcedimientoHabilitadoPK;
import com.openlopd.sessionbeans.lopd.ProcedimientoHabilitadoFacadeLocal;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Controla la activación/desactivación de procedimientos.
 *
 * @author Eduardo L. García Glez.
 */
public class CManage implements Serializable {

    ProcedimientoHabilitadoFacadeLocal procedimientoHabilitadoFacade = lookupProcedimientoHabilitadoFacadeLocal();
    private AccessInfo accessInfo;
    private int a;
    private String idProc;

    public CManage() {
    }

    public void setAccessInfo(AccessInfo accessInfo) {
        this.accessInfo = accessInfo;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setIdProc(String idProc) {
        this.idProc = idProc;
    }
    
    private String habilitarProcedimiento(ProcedimientoHabilitado p) {
        try {
            procedimientoHabilitadoFacade.edit(accessInfo, p);
            return "{\"status\": \"OK\"}";
        } catch (Exception e) {
            return "{\"status\": \"Error: imposible habilitar.\"}";
        }
    }

    private String deshabilitarProcedimiento(ProcedimientoHabilitado p) {
        try {
            procedimientoHabilitadoFacade.remove(accessInfo, p);
            return "{\"status\": \"OK\"}";
        } catch (Exception e) {
            return "{\"status\": \"Error: imposible deshabilitar.\"}";
        }
    }

    public String getStatus() {
        ProcedimientoHabilitado p = new ProcedimientoHabilitado(
                new ProcedimientoHabilitadoPK(idProc, accessInfo.getSubEmpresa().getIdEmpresa()));
        p.setProcInfo(new ProcedimientoDisponible(idProc));
        if (a == 1) {
            return habilitarProcedimiento(p);
        } else if (a == 0) {
            return deshabilitarProcedimiento(p);
        } else {
            return "{\"status\": \"Error: var a incorrecta.\"}";
        }
    }

    private ProcedimientoHabilitadoFacadeLocal lookupProcedimientoHabilitadoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ProcedimientoHabilitadoFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/ProcedimientoHabilitadoFacade!com.openlopd.sessionbeans.lopd.ProcedimientoHabilitadoFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
