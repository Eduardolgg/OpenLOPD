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

package com.openlopd.web.controllers.privatearea.soportesinventario;

import com.openlopd.entities.lopd.Soporte;
import com.openlopd.sessionbeans.lopd.SoporteFacadeLocal;
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
 *
 * @author Eduardo L. García Glez.
 */
public class CJSonTable extends AbstractCDataTable implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(CJSonTable.class); 
    SoporteFacadeLocal soporteFacade = lookupSoporteFacadeLocal();
    private CSession session;
    
    public void setSession(CSession session) {
        this.session = session;
    }

    @Override
    public String getJson() {
        List<Soporte> aTable = soporteFacade.findAllFiltering(this, session.getAccessInfo());
        DTResponse<Soporte> dtResponse = new DTResponse<Soporte>(aTable.size(), aTable.size(),
                this.getsEcho(), null, getResponseConfig(session.getAccessInfo()));
        return getJson(dtResponse.setInterval(aTable, iDisplayStart, iDisplayLength));    
    }

    @Override
    public Boolean isAuthorizedAccess() {
        return true;
    }

    private SoporteFacadeLocal lookupSoporteFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (SoporteFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/SoporteFacade!com.openlopd.sessionbeans.lopd.SoporteFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el bean de sesion Soporte.");
            throw new RuntimeException(ne);
        }
    }    
}
