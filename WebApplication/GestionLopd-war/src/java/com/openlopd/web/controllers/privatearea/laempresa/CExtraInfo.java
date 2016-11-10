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

package com.openlopd.web.controllers.privatearea.laempresa;

import com.openlopd.entities.empresas.EmpresaSede;
import com.openlopd.sessionbeans.empresas.EmpresaSedeFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import com.openlopd.web.controllers.privatearea.laempresa.CExtraInfo;
import java.io.Serializable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controla el acceso a la información extra de las tablas.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 de 07 de oct de 2012
 */
public class CExtraInfo implements Serializable {
    EmpresaSedeFacadeLocal empresaSedeFacade = lookupEmpresaSedeFacadeLocal();
    private static Logger logger = LoggerFactory.getLogger(CExtraInfo.class);
    private String id;
    private CSession session;

    public CExtraInfo() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSession(CSession session) {
        this.session = session;
    }

    public String getExtraInfo() {
        EmpresaSede se;
        try {
            if (id != null && session != null) {

                se = empresaSedeFacade.find(id);

                if (!session.getAccessInfo().getSubEmpresa().equals(se.getEmpresa())) {
                    logger.info("Sin acceso a la información extra de esta empresa.");
                    return "";
                }

                JSONObject js = new JSONObject();
                js.put("Teléfono", se.getTelefono());
                js.put("Movil", se.getMovil());
                js.put("fax", se.getFax());
                return js.toString();
            }
        } catch (Exception e) {
            logger.error("En getUpdate error actualizando {}", id);
        }
        return "";
    }

    private EmpresaSedeFacadeLocal lookupEmpresaSedeFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (EmpresaSedeFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/EmpresaSedeFacade!com.openlopd.sessionbeans.empresas.EmpresaSedeFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean EmpresaSedeFacade.");
            throw new RuntimeException(ne);
        }
    }

}
