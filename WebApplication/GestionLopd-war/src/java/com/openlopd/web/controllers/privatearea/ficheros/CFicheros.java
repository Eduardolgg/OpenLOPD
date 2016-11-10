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
import com.openlopd.entities.lopd.FicheroPredefinido;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.exceptions.SeguridadReadException;
import com.openlopd.sessionbeans.lopd.FicheroFacadeLocal;
import com.openlopd.sessionbeans.lopd.FicheroPredefinidoFacadeLocal;
import com.openlopd.web.controllers.privatearea.AbstractWebPageController;
import java.io.Serializable;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contolador de la página ficheros.jsp y firmaPendiente.jsp
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 30 de dic de 2012
 */
public class CFicheros extends AbstractWebPageController implements Serializable {
    FicheroFacadeLocal ficheroFacade = lookupFicheroFacadeLocal();
    FicheroPredefinidoFacadeLocal ficheroPredefinidoFacade = lookupFicheroPredefinidoFacadeLocal();
    private static Logger logger = LoggerFactory.getLogger(CFicheros.class);
    
    public CFicheros() {
        super(ColumnasPermisos.FICHEROS);
    }
    
    public boolean hasAllRegistrationCodes() {
        return ficheroFacade.hasAllRegistrationCodes(session.getAccessInfo());
    }
    
    /**
     * Obtiene un listado de ficheros predefinidos.
     * @return Listado de ficheros predefinidos.
     */
    public List<FicheroPredefinido> getFicherosPredefinidos() {
        try {
            return ficheroPredefinidoFacade.findActives(session.getAccessInfo());
        } catch (UnknownColumnException | SeguridadReadException ex) {            
            return null;
        }
    }
    
    public List<Fichero> getFicherosPendientesFirma() {
        return ficheroFacade.pendientesFirmar(session.getAccessInfo());
    }

    private FicheroPredefinidoFacadeLocal lookupFicheroPredefinidoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (FicheroPredefinidoFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/FicheroPredefinidoFacade!com.openlopd.sessionbeans.lopd.FicheroPredefinidoFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean FicheroPredefinido.");
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
}
