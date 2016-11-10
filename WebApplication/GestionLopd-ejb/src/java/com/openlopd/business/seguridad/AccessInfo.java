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

package com.openlopd.business.seguridad;

import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.empresas.Persona;
import com.openlopd.entities.seguridad.ConstantesSeguridad;
import com.openlopd.entities.seguridad.ContratosPermisos;
import com.openlopd.entities.seguridad.PermisosGrupos;
import com.openlopd.entities.seguridad.Shadow;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.sessionbeans.empresas.PersonaFacadeLocal;
import java.beans.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase encargada de mantener la información que es necesario mantener
 * al indentificarse un usuario en el sistema.
 *
 * @author Eduardo L. García Glez.
 * Fecha 01 de feb de 2011
 * @version 1.0.0
 */
public class AccessInfo implements Serializable {
    PersonaFacadeLocal personaFacade = lookupPersonaFacadeLocal();
    private static Logger logger = LoggerFactory.getLogger(AccessInfo.class);

    // <editor-fold defaultstate="collapsed" desc="Section Properties">
    private Shadow userInfo = null;
//    private Persona datosPersonales = null;
    private PermisosGrupos permisosUsuario = null;
    private Empresa empresa;
    private ContratosPermisos permisosEmpresa = null;  // y como se comprueba la validez de un contrato, fecha fin?
    private Empresa subEmpresa;
    private ContratosPermisos permisosSubEmpresa = null;
    private List<PermisosGrupos> permisosGrupos = null;
    private List<ConstantesSeguridad> constantesSeguridad = null;
    private TimeZone timeZone = null;
    private PropertyChangeSupport propertySupport;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructores">
    /**
     * Constructor Predeterminado.
     */
    public AccessInfo() {
    }

    /**
     * Permite inicializar todas las propiedades del objeto.
     * @param userInfo Información del usuario.
     * @param permisosUsuario permisos del usuario, estos son los que permitirán
     * saber si el usuario puede acceder a los distintos módulos
     * @param permisosEmpresa Permisos de acceso de la empresa a la que pertenece
     * el usuario.
     * @param permisosSubEmpresa Permisos de acceso de la empresa que se está
     * gestionando.
     * @param permisosGrupos Permisos de cada uno de los grupos a los que pertenece
     * el usuario.
     */
    public AccessInfo(Shadow userInfo, PermisosGrupos permisosUsuario,
            Empresa empresa,
            ContratosPermisos permisosEmpresa, 
            ContratosPermisos permisosSubEmpresa,
            List<PermisosGrupos> permisosGrupos,
            List<ConstantesSeguridad> constantesSeguridad,
            String timeZoneId) {
        this.userInfo = userInfo;
        this.permisosUsuario = permisosUsuario;
        this.empresa = empresa;
        this.subEmpresa = empresa;
        this.permisosEmpresa = permisosEmpresa;
        this.permisosSubEmpresa = permisosSubEmpresa;
        this.permisosGrupos = permisosGrupos;
        this.constantesSeguridad = constantesSeguridad;
        this.timeZone = TimeZone.getTimeZone(timeZoneId);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Section GetSet">
    /**
     * Identifica si el usuario tiene un acceso válido al sistema.
     *
     * En caso de que la cuenta no esté caducada y que todas las propiedades
     * estén inicializadas el acceso será valido.
     * @return <code>true</code> si el acceso es válido <code>false</code> en
     * caso contrario.
     */
    public boolean isValidAcces() {
        if (userInfo == null)
            return false;
        if (userInfo.getFechaFin().compareTo(new Date()) > 0)
            return false;
        return (permisosUsuario != null && permisosEmpresa != null
                && permisosSubEmpresa != null && permisosGrupos != null
                ? true : false);
    }

    public ContratosPermisos getPermisosEmpresa() {
        return permisosEmpresa;
    }

    public void setPermisosEmpresa(ContratosPermisos permisosEmpresa) {
        this.permisosEmpresa = permisosEmpresa;
    }

    public List<PermisosGrupos> getPermisosGrupos() {
        return permisosGrupos;
    }

    public void setPermisosGrupos(List<PermisosGrupos> permisosGrupos) {
        this.permisosGrupos = permisosGrupos;
    }

    public ContratosPermisos getPermisosSubEmpresa() {
        return permisosSubEmpresa;
    }

    public void setPermisosSubEmpresa(ContratosPermisos permisosSubEmpresa) {
        this.permisosSubEmpresa = permisosSubEmpresa;
    }

    public PermisosGrupos getPermisosUsuario() {
        return permisosUsuario;
    }

    public void setPermisosUsuario(PermisosGrupos permisosUsuario) {
        this.permisosUsuario = permisosUsuario;
    }

    public PropertyChangeSupport getPropertySupport() {
        return propertySupport;
    }

    public void setPropertySupport(PropertyChangeSupport propertySupport) {
        this.propertySupport = propertySupport;
    }

    public Shadow getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Shadow userInfo) {
        this.userInfo = userInfo;
    }

    public List<ConstantesSeguridad> getConstantesSeguridad(){
        return this.constantesSeguridad;
    }

    public void setConstantesSeguridad(List<ConstantesSeguridad> constantes){
        this.constantesSeguridad = constantes;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Empresa getSubEmpresa() {
        return subEmpresa;
    }

    public void setSubEmpresa(Empresa subEmpresa) {
        this.subEmpresa = subEmpresa;
    }
    
    /**
     * Establece la zona horaria del usuario.
     * @param timeZoneId  identificador de la zona horaria.
     * @see TimeZone
     */
    public void setTimeZone(String timeZoneId) {
        this.timeZone = TimeZone.getTimeZone(timeZoneId);
    }
    
    public TimeZone getTimeZone() {
        return this.timeZone;
    } 
    
//    public Persona getDatosPersonales() {
//        if (this.datosPersonales != null) {
//            return datosPersonales;
//        }
//        datosPersonales = personaFacade.findByMail();
//    }
    
    /**
     * Verifica si el usuario forma parte de una empresa gestora.
     * @param accessInfo Información de acceso del usuario.
     * @return 
     */
    public boolean isGestor() {
        try {
            return this.getPermisosEmpresa()
                        .hasAccess(ColumnasPermisos.GESTION_EMPRESAS);
        } catch (UnknownColumnException ex) {
            logger.error("Imposible!!! la columna 'GESTION_EMPRESAS' debe existir.");
        }
        throw new RuntimeException("Imposible!!! la columna 'GESTION_EMPRESAS' debe existir.");
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Extra Methods">
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Default Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userInfo != null ? userInfo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AccessInfo)) {
            return false;
        }
        AccessInfo other = (AccessInfo) object;
        if (!userInfo.equals(other.userInfo) ||
            !permisosUsuario.equals(other.permisosUsuario) ||
            !permisosEmpresa.equals(other.permisosEmpresa) ||
            !permisosGrupos.equals(other.permisosGrupos)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Usuario: " + (userInfo != null ? userInfo.getUsuario(): null);
    }
    // </editor-fold>

    private PersonaFacadeLocal lookupPersonaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (PersonaFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/PersonaFacade!com.openlopd.sessionbeans.empresas.PersonaFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean personaFacade.");
            throw new RuntimeException(ne);
        }
    }
}
