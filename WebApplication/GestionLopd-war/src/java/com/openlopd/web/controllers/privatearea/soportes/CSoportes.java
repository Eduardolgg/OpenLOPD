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

package com.openlopd.web.controllers.privatearea.soportes;

import com.openlopd.sessionbeans.lopd.TipoSoporteFacadeLocal;
import com.openlopd.sessionbeans.lopd.UnidadesInformacionFacadeLocal;
import java.io.Serializable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controla la visualización de soportes.
 * @author Eduardo L. García Glez.
 */
public class CSoportes implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(CSoportes.class);
    
    private UnidadesInformacionFacadeLocal unidadesInformacionFacade = lookupUnidadesInformacionFacadeLocal();
    private TipoSoporteFacadeLocal tipoSoporteFacade = lookupTipoSoporteFacadeLocal();

    
    public CSoportes() {
    }
    
    private TipoSoporteFacadeLocal lookupTipoSoporteFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (TipoSoporteFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/TipoSoporteFacade!com.openlopd.sessionbeans.lopd.TipoSoporteFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible recuperar el bean de session TipoSoporte");
            throw new RuntimeException(ne);
        }
    }

    private UnidadesInformacionFacadeLocal lookupUnidadesInformacionFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (UnidadesInformacionFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/UnidadesInformacionFacade!com.openlopd.sessionbeans.lopd.UnidadesInformacionFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible recuperar el bean de session UnidadesInformacion");
            throw new RuntimeException(ne);
        }
    }
}
