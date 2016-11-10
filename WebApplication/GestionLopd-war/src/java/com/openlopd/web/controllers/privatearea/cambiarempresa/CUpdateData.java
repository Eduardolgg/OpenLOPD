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

package com.openlopd.web.controllers.privatearea.cambiarempresa;

import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.empresas.EmpresaSede;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.exceptions.SeguridadWriteException;
import com.openlopd.exceptions.SeguridadWriteLimitException;
import com.openlopd.sessionbeans.empresas.EmpresaFacadeLocal;
import com.openlopd.sessionbeans.empresas.EmpresaSedeFacadeLocal;
import com.openlopd.web.controllers.privatearea.AbstractWebPageController;
import java.io.Serializable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Controla la actualización de datos de subempresas.
 * @author Eduardo L. García Glez.
 * @version 0.0.0
 */
public class CUpdateData extends AbstractWebPageController implements Serializable {
    EmpresaSedeFacadeLocal empresaSedeFacade = lookupEmpresaSedeFacadeLocal();
    EmpresaFacadeLocal empresaFacade = lookupEmpresaFacadeLocal();
    private static Logger logger = LoggerFactory.getLogger(CUpdateData.class);
    
    private String cif;
    private String razonSocial;
    private String actividad;
    private String perContacto;
    private String pApellido;
    private String sApellido;
    private String mailContacto;
    private String telefono;
    private String movil;
    private String fax;

    private String id;
    private String value;
    private String rowId;
    private String columnPosition;
    private String columnId;
    private String columnName;

    public CUpdateData() {        
        super(ColumnasPermisos.GESTION_EMPRESAS);
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public void setPerContacto(String perContacto) {
        this.perContacto = perContacto;
    }

    public void setpApellido(String pApellido) {
        this.pApellido = pApellido;
    }

    public void setsApellido(String sApellido) {
        this.sApellido = sApellido;
    }

    public void setMailContacto(String mailContacto) {
        this.mailContacto = mailContacto;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
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
    
    public void setValue(String value) {
        this.value = value;
    }
    //</editor-fold>
    
    public String getUpdate() {
        try {
            Empresa e = empresaFacade.find(id);
            e.setCif(cif);
            e.setActividad(actividad);
            e.setMailContacto(mailContacto);
            
            EmpresaSede se = e.getSedeLopd();
            se.setTelefono(telefono);
            se.setFax(fax);
            se.setMovil(movil);
            
            empresaFacade.edit(session.getAccessInfo(), e);
            empresaSedeFacade.edit(session.getAccessInfo(), se);
            
            return e.getIdEmpresa();
        } catch (UnknownColumnException | SeguridadWriteException | SeguridadWriteLimitException e) {
            logger.error("En getUpdate error actualizando", this);
        }    
        return "";
    }

    private EmpresaFacadeLocal lookupEmpresaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (EmpresaFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/EmpresaFacade!com.openlopd.sessionbeans.empresas.EmpresaFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean empresaFacade.");
            throw new RuntimeException(ne);
        }
    }

    private EmpresaSedeFacadeLocal lookupEmpresaSedeFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (EmpresaSedeFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/EmpresaSedeFacade!com.openlopd.sessionbeans.empresas.EmpresaSedeFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean empresaSedeFacade.");
            throw new RuntimeException(ne);
        }
    }
   
}
