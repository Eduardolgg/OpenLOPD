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

package com.openlopd.web.controllers.privatearea.destinatarios;

import com.openlopd.entities.lopd.Destinatario;
import com.openlopd.sessionbeans.lopd.DestinatarioFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import java.io.Serializable;
import java.util.Date;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controla el borrado de destinatarios.
 * @author Eduardo L. García Glez.
 */
public class CDeleteData implements Serializable {
    DestinatarioFacadeLocal destinatarioFacade = lookupDestinatarioFacadeLocal();
    private static Logger logger = LoggerFactory.getLogger(CDeleteData.class);
    private String id;
    private CSession session;

    /**
     * Constructor por defecto.
     */
    public CDeleteData() {
    }

    /**
     * Establece el Id del entity a eliminar.
     * @param id id del entity a eliminar.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Establece la sessión del usuario.
     * @param session información de la sessión del usuario.
     */
    public void setSession(CSession session) {
        this.session = session;
    }

    /**
     * Ejecuta el proceso de eliminación de la fila indicada.
     * @return <code>ok</code> si la fila es eliminada correctemanete, 
     * <code>error</code> en caso contrario.
     */
    public String getDelete() {
        Long now = new Date().getTime();
        try {
            //TODO: Permisos de borrado, este sistema es inseguro cualquiera con 
            // un ID puede borrar.
            Destinatario d = destinatarioFacade.find(id);
            if (d.getEmpresa().equals(session.getAccessInfo().getSubEmpresa())) {
                if (d.getFechaBaja() == null) {
                    d.setFechaBaja(now);
                    d.setFechaBajaInt(now);
                }
                d.setBorrado(now);
                d.setBorradoPor(session.getAccessInfo().getUserInfo().getUsuario());
                destinatarioFacade.edit(session.getAccessInfo(), d);
            }            
            return "ok";
        } catch (Exception e) {
            logger.error("Error borrando la fila id{} por empresa:{}", id, 
                    session.getAccessInfo().getEmpresa().getIdEmpresa());
            return "error";
        }
    }

    //<editor-fold defaultstate="collapsed" desc="lookup">
    private DestinatarioFacadeLocal lookupDestinatarioFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (DestinatarioFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/DestinatarioFacade!com.openlopd.sessionbeans.lopd.DestinatarioFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el bean de sesion DestinatarioFacade.");
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>
}
