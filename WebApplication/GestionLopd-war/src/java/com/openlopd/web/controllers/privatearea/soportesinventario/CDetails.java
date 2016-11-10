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
import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Level;
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

    SoporteFacadeLocal soporteFacade = lookupSoporteFacadeLocal();
    private static Logger logger = LoggerFactory.getLogger(CDetails.class);
    private String id;
    private CSession session;
//    private Destinatario destinatario;

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

    public Soporte getDetails() {
        logger.debug("Buscando el soporte con id[{}]", id);
        Soporte s = soporteFacade.find(id);
        if (session.getAccessInfo().getSubEmpresa().equals(s.getEmpresa())) {
            return s;
        }
        return null;
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

    private SoporteFacadeLocal lookupSoporteFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (SoporteFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/SoporteFacade!com.openlopd.sessionbeans.lopd.SoporteFacadeLocal");
        } catch (NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
