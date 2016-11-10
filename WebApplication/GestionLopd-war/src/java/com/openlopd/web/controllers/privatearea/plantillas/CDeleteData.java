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

package com.openlopd.web.controllers.privatearea.plantillas;

import com.openlopd.entities.documentos.Plantilla;
import com.openlopd.sessionbeans.documentos.PlantillaFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import java.io.Serializable;
import java.util.Date;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Borrado de Plantillas.
 * @author Eduardo L. García Glez.
 */
public class CDeleteData implements Serializable {
    PlantillaFacadeLocal plantillaFacade = lookupPlantillaFacadeLocal();
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
        Long now = new Date().getTime();
        try {//TODO: Permisos de borrado, este sistema es inseguro cualquiera con un ID puede borrar.
            Plantilla p = plantillaFacade.find(id);
            if (p.getEmpresa().equals(session.getAccessInfo().getSubEmpresa())) {
                if (p.getFechaBaja() == null) {
                    p.setFechaBaja(now);
                }
                p.setBorrado(now);
                p.setBorradoPor(session.getAccessInfo().getUserInfo().getUsuario());
                plantillaFacade.edit(session.getAccessInfo(), p);
            }            
            return "ok";
        } catch (Exception e) {
            logger.error("Error borrando la fila id{} por empresa:{}", id, 
                    session.getAccessInfo().getEmpresa().getIdEmpresa());
            return "error";
        }
    }

    private PlantillaFacadeLocal lookupPlantillaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (PlantillaFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/PlantillaFacade!com.openlopd.sessionbeans.documentos.PlantillaFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el sessin bean PlantillaFacade.");
            throw new RuntimeException(ne);
        }
    }


}
