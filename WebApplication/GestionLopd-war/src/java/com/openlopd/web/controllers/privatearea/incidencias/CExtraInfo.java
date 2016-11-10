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

package com.openlopd.web.controllers.privatearea.incidencias;

import com.openlopd.entities.lopd.Incidencia;
import com.openlopd.sessionbeans.lopd.IncidenciaFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
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

    IncidenciaFacadeLocal incidenciaFacade = lookupIncidenciaFacadeLocal();
    private static Logger logger = LoggerFactory.getLogger(CExtraInfo.class);
    private String id;
    private CSession session;
    private static final ResourceBundle rb = ResourceBundle.getBundle("com.openlopd.config.config", Locale.getDefault());

    public CExtraInfo() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSession(CSession session) {
        this.session = session;
    }

    public String getExtraInfo() {
        Incidencia i;
        try {
            if (id != null && session != null) {

                i = incidenciaFacade.find(id);

                if (!session.getAccessInfo().getSubEmpresa().equals(i.getEmpresa())) {
                    return "";
                }

                JSONObject js = new JSONObject();
                js.put("Detectada", i.getDeteccion() ? "La fecha indicada es la fecha de detección de la incidencia." :
                        "La fecha indicada es la fecha en la que se produjo la incidencia.");
                js.put("Notificante", i.getNotificadoPor());
                js.put("Sistema Afectado", i.getSistemaAfectado());
                js.put("Efectos Derivados", i.getEfectosDerivados());
                js.put("Medidas correctoras", i.getMedidasCorrectoras());
                js.put("Persona que ejecuta la recuperación de datos", i.getPersonaEjecutora());
                js.put("Datos Restaurados", i.getDatosRestaurados());
                js.put("Datos restaudados manualmente", i.getDatosRestauradosManualmente());
                js.put("Protocolo utilizado", i.getProtocoloUtilizado());
                js.put("Tipo de Incidencia", i.getTipoIncidencia());
                js.put("Autorizacion", i.getAutorizacion() != null ? 
                        "<a href=\"" + rb.getString("root") + "/download?file=" 
                        + i.getAutorizacion().getId() + "\">Ver</a>" 
                        : null);
                return js.toString();
            }
        } catch (Exception e) {
            logger.error("En getUpdate error actualizando");
        }
        return "";
    }

    private IncidenciaFacadeLocal lookupIncidenciaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (IncidenciaFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/IncidenciaFacade!com.openlopd.sessionbeans.lopd.IncidenciaFacadeLocal");
        } catch (NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
