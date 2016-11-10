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

package com.openlopd.web.controllers.privatearea.regaccesos;

import com.openlopd.entities.lopd.Fichero;
import com.openlopd.entities.lopd.RegistroAcceso;
import com.openlopd.entities.lopd.TipoAccesoDocumento;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.sessionbeans.lopd.FicheroFacadeLocal;
import com.openlopd.web.controllers.privatearea.AbstractWebPageController;
import java.io.Serializable;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controla la página regacceso.jsp
 * 
 * @author Eduardo L. García Glez.
 * @version 0.0.0 02 de abr de 2013
 */
public class CRegistroAcceso extends AbstractWebPageController implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(RegistroAcceso.class);
    FicheroFacadeLocal ficheroFacade = lookupFicheroFacadeLocal();

    public CRegistroAcceso() {
        super(ColumnasPermisos.REGISTRO_ACCESOS);
    }
    
    public TipoAccesoDocumento[] getListTipoAccesoDoc() {
        return TipoAccesoDocumento.values();
    }
    
    public List<Fichero> getListFicheros() {
        return ficheroFacade.findActives(session.getAccessInfo().getSubEmpresa());
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
