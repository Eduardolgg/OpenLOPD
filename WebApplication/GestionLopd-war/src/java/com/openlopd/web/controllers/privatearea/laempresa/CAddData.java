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

package com.openlopd.web.controllers.privatearea.laempresa;

import com.openlopd.entities.empresas.EmpresaSede;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.sessionbeans.empresas.EmpresaSedeFacadeLocal;
import com.openlopd.web.controllers.privatearea.AbstractWebPageController;
import com.jkingii.mail.utils.GenKey;
import java.io.Serializable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controlador para añadir Sedes de empresas.
 * @author Eduardo L. García Glez.
 */
public class CAddData extends AbstractWebPageController implements Serializable {
    EmpresaSedeFacadeLocal empresaSedeFacade = lookupEmpresaSedeFacadeLocal();
    private static Logger logger = LoggerFactory.getLogger(CAddData.class);

    private String nombreSede;
    private String gestiona;
    private String telefono;
    private String movil;
    private String fax;
    private String direccion;
    private String cp;
    private String localidad;
    private String provincia;
    private String idPersona;
    private String nota;

    public CAddData() {
        super(ColumnasPermisos.APP_ADMIN);       
    }

    //<editor-fold defaultstate="collapsed" desc="GetSet">
    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }
    
    public void setGestiona(String gestiona) {
        this.gestiona = gestiona;
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
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    
    public void setCp(String cp) {
        this.cp = cp;
    }
    
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }
    
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }
    
    public void setNota(String nota) {
        this.nota = nota;
    }
    //</editor-fold>
       
    public String getId() {
        try {
            EmpresaSede se = new EmpresaSede();
            se.setId(GenKey.newKey());
            se.setEmpresa(session.getAccessInfo().getSubEmpresa());
            se.setNombreSede(nombreSede);
            se.setGestionaContratLOPD(gestiona != null &&
                    gestiona.equals("ON") ? true : false);
            se.setTelefono(telefono);
            se.setMovil(movil);
            se.setFax(fax);
            se.setDireccion(direccion);
            se.setCp(cp);
            se.setLocalidad(new Long(localidad));
            se.setProvincia(provincia);
            se.setNota(nota);
            se.setPerContacto(idPersona);
            
            empresaSedeFacade.create(session.getAccessInfo(), se);
            return se.getId();
        } catch (Exception e) {
            logger.error(e.getMessage());
            //TODO: Gestionar errores de DataTable Editable
        }
        return "";
    }

    private EmpresaSedeFacadeLocal lookupEmpresaSedeFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (EmpresaSedeFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/EmpresaSedeFacade!com.openlopd.sessionbeans.empresas.EmpresaSedeFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener EmpresaSedeFacade.");
            throw new RuntimeException(ne);
        }
    }
}
