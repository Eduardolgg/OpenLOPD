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

package com.openlopd.web.controllers.privatearea.ficheros;

import com.openlopd.entities.lopd.Fichero;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.exceptions.SeguridadReadException;
import com.openlopd.exceptions.SeguridadWriteException;
import com.openlopd.exceptions.SeguridadWriteLimitException;
import com.openlopd.sessionbeans.lopd.FicheroFacadeLocal;
import com.openlopd.web.controllers.privatearea.AbstractWebPageController;
import java.io.Serializable;
import java.util.Objects;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controla los cambios de códigos de inscripción en ficheros.
 * @author Eduardo L. García Glez.
 */
public class CiChange extends AbstractWebPageController implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(CiChange.class);
    FicheroFacadeLocal ficheroFacade = lookupFicheroFacadeLocal();
    private String fichero;
    private String codInscripcion;
    
    public CiChange() {
        super(ColumnasPermisos.FICHEROS);
    }
    
    public void setFichero(String fichero) {
        this.fichero = fichero;
    }

    public void setCodInscripcion(String codInscripcion) {
        this.codInscripcion = codInscripcion;
    }

    public String getUpdate() {
        try {
            Fichero f = ficheroFacade.updateCodInscripcion(session.getAccessInfo(),
                    fichero, codInscripcion);
            return "";
        } catch (SeguridadWriteException | UnknownColumnException | 
                SeguridadReadException | SeguridadWriteLimitException e) {
                    return "El usuario no tiene permisos de Lectura/Escritura";
        } catch (Exception e) {
            return "Error al actualizar el código de inscripción";
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Default methods">
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.fichero);
        hash = 89 * hash + Objects.hashCode(this.codInscripcion);
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
        final CiChange other = (CiChange) obj;
        if (!Objects.equals(this.fichero, other.fichero)) {
            return false;
        }
        if (!Objects.equals(this.codInscripcion, other.codInscripcion)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "CiChange{" + "fichero=" + fichero + ", codInscripcion=" + codInscripcion + '}';
    }
    //</editor-fold>

    private FicheroFacadeLocal lookupFicheroFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (FicheroFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/FicheroFacade!com.openlopd.sessionbeans.lopd.FicheroFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean lookupFicheroFacadeLocal.");
            throw new RuntimeException(ne);
        }
    }
    
}
