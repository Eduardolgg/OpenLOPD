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

package com.openlopd.web.controllers.privatearea.personas;

import com.openlopd.business.seguridad.GruposLocal;
import com.openlopd.business.seguridad.SeguridadLocal;
import com.openlopd.entities.empresas.Persona;
import com.openlopd.entities.lopd.Fichero;
import com.openlopd.entities.seguridad.EmpresasGrupos;
import com.openlopd.entities.seguridad.base.BasePermisosGrupos;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.sessionbeans.empresas.PersonaFacadeLocal;
import com.openlopd.sessionbeans.lopd.FicheroFacadeLocal;
import com.openlopd.sessionbeans.lopd.PersonaFicheroFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gestiona la visualización de detalles de la entidad destinatarios.
 *
 * @author Eduardo L. García Glez.
 */
public class CDetails implements Serializable {

    PersonaFicheroFacadeLocal personaFicheroFacade = lookupPersonaFicheroFacadeLocal();
    FicheroFacadeLocal ficheroFacade = lookupFicheroFacadeLocal();
    SeguridadLocal seguridad = lookupSeguridadLocal();
    GruposLocal grupos = lookupGruposLocal();
    PersonaFacadeLocal personaFacade = lookupPersonaFacadeLocal();
    private static Logger logger = LoggerFactory.getLogger(CDetails.class);
    private String id;
    private CSession session;

    public CDetails() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSession(CSession session) {
        this.session = session;
    }

    public Persona getDetails() {
        Persona d = personaFacade.find(id);
        if (session.getAccessInfo().getSubEmpresa().equals(d.getEmpresa())) {
            return d;
        }
        return null;
    }

    public boolean isSystemUser(String idUsuario) {
        return seguridad.isSystemUser(idUsuario);
    }

    public List<EmpresasGrupos> getGruposDisponibles(String idUsuario) {
        return grupos.findAllDisponibles(session.getAccessInfo(), idUsuario);
    }

    public List<EmpresasGrupos> getGruposHabilitados(String idUsuario) {
        return grupos.findAllHabilitados(session.getAccessInfo(), idUsuario);
    }

    public List<Fichero> getFicherosDisponibles() {
        return ficheroFacade.findFicherosDisponiblesPersona(
                session.getAccessInfo(), id);
    }

    public List<Fichero> getFicherosHabilitados() {
        return ficheroFacade.findFicherosHabilitadosPersona(
                session.getAccessInfo(), id);
    }

    /**
     * Comprueba si el usuario tiene acceso como administrador de la aplicación.
     *
     * @return true si tiene acceso, false en caso contrario.
     */
    public boolean isAppAdmin() {
        try {
            return session.getAccessInfo().getPermisosUsuario()
                    .hasAccess(ColumnasPermisos.APP_ADMIN, BasePermisosGrupos.LECTURA);
        } catch (UnknownColumnException ex) {
            logger.error("Imposible!!! el permiso APP_ADMIN debe existir.");
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CDetails other = (CDetails) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Details{" + "id=" + id + '}';
    }

    //<editor-fold defaultstate="collapsed" desc="lookup">
    private PersonaFacadeLocal lookupPersonaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (PersonaFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/PersonaFacade!com.openlopd.sessionbeans.empresas.PersonaFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean PersonaFacade.");
            throw new RuntimeException(ne);
        }
    }

    private GruposLocal lookupGruposLocal() {
        try {
            Context c = new InitialContext();
            return (GruposLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/Grupos!com.openlopd.business.seguridad.GruposLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean lookupGruposLocal.");
            throw new RuntimeException(ne);
        }
    }

    private SeguridadLocal lookupSeguridadLocal() {
        try {
            Context c = new InitialContext();
            return (SeguridadLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/Seguridad!com.openlopd.business.seguridad.SeguridadLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean lookupSeguridadLocal.");
            throw new RuntimeException(ne);
        }
    }

    private FicheroFacadeLocal lookupFicheroFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (FicheroFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/FicheroFacade!com.openlopd.sessionbeans.lopd.FicheroFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean lookupFicheroFacadeLocal.");
            throw new RuntimeException(ne);
        }
    }

    private PersonaFicheroFacadeLocal lookupPersonaFicheroFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (PersonaFicheroFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/PersonaFicheroFacade!com.openlopd.sessionbeans.lopd.PersonaFicheroFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean lookupPersonaFicheroFacadeLocal.");
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>
}
