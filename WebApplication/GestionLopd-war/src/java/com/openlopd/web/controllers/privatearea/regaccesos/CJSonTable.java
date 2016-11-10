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

import com.openlopd.entities.lopd.RegistroAcceso;
import com.openlopd.sessionbeans.lopd.RegistroAccesoFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import com.jkingii.datatables.AbstractCDataTable;
import com.jkingii.datatables.DTResponse;
import java.io.Serializable;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controla el acceso a los datos de la tabla de Registro de Acceso.
 * @author Eduardo L. García Glez.
 * @version 0.0.0
 */
public class CJSonTable extends AbstractCDataTable implements Serializable {
    RegistroAccesoFacadeLocal registroAccesoFacade = lookupRegistroAccesoFacadeLocal();
    private static Logger logger = LoggerFactory.getLogger(CJSonTable.class);
    
    private CSession session;

    public void setSession(CSession session) {
        this.session = session;
    }

    @Override
    public String getJson() {
        List<RegistroAcceso> aTable = registroAccesoFacade.findAllFiltering(this, session.getAccessInfo());
        DTResponse<RegistroAcceso> dtResponse = new DTResponse<RegistroAcceso>(aTable.size(), aTable.size(),
                this.getsEcho(), null, getResponseConfig(session.getAccessInfo()));
        return getJson(dtResponse.setInterval(aTable, iDisplayStart, iDisplayLength));

    }

    @Override
    public Boolean isAuthorizedAccess() {
        return true;
    }

    private RegistroAccesoFacadeLocal lookupRegistroAccesoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (RegistroAccesoFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/RegistroAccesoFacade!com.openlopd.sessionbeans.lopd.RegistroAccesoFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean lookupRegistroAccesoFacadeLocal.");
            throw new RuntimeException(ne);
        }
    }
}
