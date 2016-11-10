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

import com.openlopd.entities.empresas.Empresa;
import com.openlopd.sessionbeans.empresas.EmpresaFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import java.io.Serializable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controla la actualización de datos generales de la empresa.
 *
 * @author Eduardo L. García Glez.
 */
public class CUpdate implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(CUpdate.class);
    EmpresaFacadeLocal empresaFacade = lookupEmpresaFacadeLocal();
    private String razonSocial;
    private String nombre;
    private String mailContacto;
    private String respSeguridad;
    private String idPersona;
    private String actividad;
    private CSession session;

    public CUpdate() {
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMailContacto(String mailContacto) {
        this.mailContacto = mailContacto;
    }

    public void setRespSeguridad(String respSeguridad) {
        this.respSeguridad = respSeguridad;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public void setSession(CSession session) {
        this.session = session;
    }

    public String getUpdate() {
        try {
            Empresa empresa = session.getAccessInfo().getSubEmpresa();
            empresa.setActividad(actividad);
            empresa.setMailContacto(mailContacto);
            empresa.setPerContacto(idPersona);
            empresa.setNombre(nombre);
            empresa.setRazonSocial(razonSocial);
            empresaFacade.edit(session.getAccessInfo(), empresa);
            return "Operación realizada";
        } catch (Exception e) {
            return "Error actualizando.";
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
}
