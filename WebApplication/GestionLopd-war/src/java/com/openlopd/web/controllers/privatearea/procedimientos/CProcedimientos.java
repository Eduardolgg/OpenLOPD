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
import com.openlopd.entities.lopd.ProcedimientoHabilitado;
import com.openlopd.entities.lopd.TipoProcedimiento;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.sessionbeans.empresas.EmpresaFacadeLocal;
import com.openlopd.sessionbeans.lopd.ProcedimientoDisponibleFacadeLocal;
import com.openlopd.sessionbeans.lopd.ProcedimientoHabilitadoFacadeLocal;
import com.openlopd.sessionbeans.lopd.TipoProcedimientoFacadeLocal;
import com.openlopd.web.controllers.privatearea.AbstractWebPageController;
import java.io.Serializable;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controla la Habilitación/Deshabilitación de procedimientos.
 *
 * @author Eduardo L. García Glez.
 */
public class CProcedimientos extends AbstractWebPageController implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(CProcedimientos.class);
    EmpresaFacadeLocal empresaFacade = lookupEmpresaFacadeLocal();
    TipoProcedimientoFacadeLocal tipoProcedimientoFacade = lookupTipoProcedimientoFacadeLocal();
    ProcedimientoHabilitadoFacadeLocal procedimientoHabilitadoFacade = lookupProcedimientoHabilitadoFacadeLocal();
    ProcedimientoDisponibleFacadeLocal procedimientoDisponibleFacade = lookupProcedimientoDisponibleFacadeLocal();
    private String tipo; // Tipo de procedimiento.
    
    public CProcedimientos() {
        super(ColumnasPermisos.DOCUMENTO_SEGURIDAD);
    }

    //<editor-fold defaultstate="collapsed" desc="Properties Get/Set">
    public TipoProcedimiento getTipoProcedimiento() {
        TipoProcedimiento t = tipoProcedimientoFacade
                .getTipoProcedimientoActivo(session.getAccessInfo().getSubEmpresa(), tipo);
        // TODO: Revisar la seguridad de esto.
        if (logger.isDebugEnabled()) {
            logger.debug("Tipo Proc accedido: [{}]", t);
        }
        return t;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    //</editor-fold>

    public List<ProcedimientoDisponible> getProcedimientosDisponibles() {
        return procedimientoDisponibleFacade.getUnusedProcedures(tipo, session.getAccessInfo());
    }
    
    public List<ProcedimientoHabilitado> getProcedimientosHabilitados() {
        if (logger.isDebugEnabled()) {
            logger.debug("TipoProc  [{}], empresa [{}]", tipo, session.getAccessInfo().getSubEmpresa());
        }
        return procedimientoHabilitadoFacade.getProcedimientosHabilitados(tipo, session.getAccessInfo().getSubEmpresa());
    }
    
    public String getSiguiente() {
        TipoProcedimiento siguiente = tipoProcedimientoFacade
                .getSiguiente(session.getAccessInfo(), tipo);
        return siguiente != null ?
                siguiente.getTipoProcedimientoPK().getId() : null;
    }
    
    public String getAnterior() {
        TipoProcedimiento anterior = tipoProcedimientoFacade
                .getAnterior(session.getAccessInfo(), tipo);
        return anterior != null ?
                anterior.getTipoProcedimientoPK().getId() : null;       
    }

    //<editor-fold defaultstate="collapsed" desc="Lookup Secction">
    private ProcedimientoDisponibleFacadeLocal lookupProcedimientoDisponibleFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ProcedimientoDisponibleFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/ProcedimientoDisponibleFacade!com.openlopd.sessionbeans.lopd.ProcedimientoDisponibleFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el sessionbean ProcedimientoDisponibleFacade.");
            throw new RuntimeException(ne);
        }
    }
    
    private ProcedimientoHabilitadoFacadeLocal lookupProcedimientoHabilitadoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ProcedimientoHabilitadoFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/ProcedimientoHabilitadoFacade!com.openlopd.sessionbeans.lopd.ProcedimientoHabilitadoFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean ProcedimientoHabilitadoFacade.");
            throw new RuntimeException(ne);
        }
    }
    
    private TipoProcedimientoFacadeLocal lookupTipoProcedimientoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (TipoProcedimientoFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/TipoProcedimientoFacade!com.openlopd.sessionbeans.lopd.TipoProcedimientoFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean TipoProcedimientoFacade.");
            throw new RuntimeException(ne);
        }
    }
    
    private EmpresaFacadeLocal lookupEmpresaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (EmpresaFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/EmpresaFacade!com.openlopd.sessionbeans.empresas.EmpresaFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean EmpresaFacade.");
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>
}
