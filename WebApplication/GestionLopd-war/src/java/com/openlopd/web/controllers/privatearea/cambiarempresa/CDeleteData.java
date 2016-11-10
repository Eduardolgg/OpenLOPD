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
import com.openlopd.web.controllers.privatearea.CSession;
import java.io.Serializable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Elimina una subEmpresa.
 * @author Eduardo L. García Glez.
 */
public class CDeleteData implements Serializable {
    SeguridadLocal seguridad = lookupSeguridadLocal();
    private static Logger logger = LoggerFactory.getLogger(CDeleteData.class);

    private String id;
    private CSession session;

    public CDeleteData() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSession(CSession session) {
        this.session = session;
    }

    /**
     * Elimina una fila
     * @return <code>true</code> si la fila es eliminada correctemanete, 
     * <code>false</code> en caso contrario.
     */
    public String getDelete() {
//        try {
            seguridad.bajaContrato(session.getAccessInfo(), id);
            return "ok";
//        } catch (SeguridadWriteException e) {
//            return "No tiene permisos de escritura para esta operacion.";                 
//        } catch (SeguridadWriteLimitException e) { 
//            return "Ha sobrepasado el límite de escrituras para esta operación.";
//        } catch (UnknownColumnException e) {
//            logger.error("En getDelete, error borrando la fila id[{}]", id);
//            return e.getMessage();
//        }
    }

    private SeguridadLocal lookupSeguridadLocal() {
        try {
            Context c = new InitialContext();
            return (SeguridadLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/Seguridad!com.openlopd.business.seguridad.SeguridadLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session ben Seguridad.");
            throw new RuntimeException(ne);
        }
    }

}
