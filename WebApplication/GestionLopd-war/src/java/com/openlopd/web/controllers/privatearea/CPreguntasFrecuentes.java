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

package com.openlopd.web.controllers.privatearea;

import com.openlopd.entities.interfaz.PreguntaFrecuente;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.sessionbeans.PreguntaFrecuenteFacadeLocal;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Controla la visualización de las preguntas frecuentes.
 * @author Eduardo L. García Glez.
 */
public class CPreguntasFrecuentes extends AbstractWebPageController implements Serializable {
    PreguntaFrecuenteFacadeLocal preguntaFrecuenteFacade = lookupPreguntaFrecuenteFacadeLocal();

    public CPreguntasFrecuentes() {
        super(ColumnasPermisos.PUBLICO);
    }
    
    public List<PreguntaFrecuente> getPreguntasFrecuentes() {
        return preguntaFrecuenteFacade.findAll();
    }

    private PreguntaFrecuenteFacadeLocal lookupPreguntaFrecuenteFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (PreguntaFrecuenteFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/PreguntaFrecuenteFacade!com.openlopd.sessionbeans.PreguntaFrecuenteFacadeLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
