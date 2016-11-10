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

import com.openlopd.business.seguridad.SeguridadLocal;
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.empresas.EmpresaSede;
import com.openlopd.entities.empresas.Persona;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.web.controllers.privatearea.AbstractWebPageController;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Añade una nueva subEmpresa.
 *
 * @author Eduardo L. García Glez.
 */
public class CAddData extends AbstractWebPageController implements Serializable {

    SeguridadLocal seguridad = lookupSeguridadLocal();
    private static Logger logger = LoggerFactory.getLogger(CAddData.class);
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
    private String gestor;

    public CAddData() {
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

    public void setGestor(String gestor) {
        this.gestor = gestor;
    }

    public String getId() {
        String result = "Error";
        try {

            Empresa e = new Empresa(cif, razonSocial);
            e.setActividad(actividad);
            e.setMailContacto(mailContacto);

            EmpresaSede se = new EmpresaSede(e, true, razonSocial, telefono,
                    movil, fax, null, null, null, null);

            Persona p = new Persona(perContacto, pApellido,
                    sApellido, null, null, null);
            p.setfInicio(new Timestamp(new Date().getTime()));

            if (gestor != null && gestor.equals("ON")) {
                result = seguridad.nuevoGestor(session.getAccessInfo(), e, se, p);
            } else {
                result = seguridad.nuevoContrato(session.getAccessInfo(), e, se, p);
            }

            return result.startsWith("Error") ? result : e.getIdEmpresa();
//        } catfch (ParseException e) {
//            logger.error("en CAddData.getId: Error convirtiendo la fecha fAlta["
//                    + fAlta + "] fBaja[" + fBaja + "]. Exception: " + e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            //TODO: Gestionar errores de DataTable Editable
        }
        return result;
    }

    //<editor-fold defaultstate="collapsed" desc="lookup">
    private SeguridadLocal lookupSeguridadLocal() {
        try {
            Context c = new InitialContext();
            return (SeguridadLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/Seguridad!com.openlopd.business.seguridad.SeguridadLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean SeguridadLocal.");
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>
}
