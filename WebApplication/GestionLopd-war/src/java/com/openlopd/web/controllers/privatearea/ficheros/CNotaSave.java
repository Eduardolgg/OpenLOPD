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

package com.openlopd.web.controllers.privatearea.ficheros;

import com.openlopd.agpd.nota.xml.DatosFichero;
import com.openlopd.entities.lopd.Fichero;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.sessionbeans.lopd.FicheroFacadeLocal;
import com.openlopd.sessionbeans.lopd.FicheroPredefinidoFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import java.io.Serializable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Almacena la información del fichero en el sistema.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 26 de nov de 2012
 */
public class CNotaSave extends DatosFichero implements Serializable {
    FicheroPredefinidoFacadeLocal ficheroPredefinidoFacade = lookupFicheroPredefinidoFacadeLocal();

    FicheroFacadeLocal ficheroFacade = lookupFicheroFacadeLocal();
    private static Logger logger = LoggerFactory.getLogger(CNotaSave.class);
    private CSession session;
    private String save;
    private String predefinido;
    private String registrar;
    private Boolean firmar = null;
    private Fichero fichero = null;

    public CNotaSave() {
        super();
    }

    public void setSession(CSession session) {
        this.session = session;
    }

    public void setPredefinido(String predefinido) {
        this.predefinido = predefinido;
    }

    public void setRegistrar(String registrar) {
        this.registrar = registrar;
    }
    
    public boolean getFirmar() {
        return firmar.booleanValue();
    }
    
    public Fichero getFichero() {
        return fichero;
    }
    
    public String getSave() {
        try {
            // firmar indica si se debe utilizar la firma del servidor.            
            firmar = !(session.getAccessInfo().isGestor() &&
                    registrar.equals("1") && !predefinido.equals("1"));
            firmar = false;
            
            this.setEmpresa(this.session.getAccessInfo().getSubEmpresa());
            this.setUserInfo(this.session.getAccessInfo().getUserInfo());
            if (predefinido != null && predefinido.equals("1")) {
                ficheroPredefinidoFacade.create(session.getAccessInfo(), this);
            } else {
                fichero = ficheroFacade.realizarSolicitud(session.getAccessInfo(), this,
                        registrar.equals("1"), firmar);
            }
            return save;
        } catch (UnknownColumnException ex) {
        } catch (Exception ex) {
            logger.error("Imposible registrar el fichero: {}", ex.getMessage());
        }
        return "Error";
    }

    //<editor-fold defaultstate="collapsed" desc="lookup">
    private FicheroFacadeLocal lookupFicheroFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (FicheroFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/FicheroFacade!com.openlopd.sessionbeans.lopd.FicheroFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean FicheroFacade.");
            throw new RuntimeException(ne);
        }
    }
    
    private FicheroPredefinidoFacadeLocal lookupFicheroPredefinidoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (FicheroPredefinidoFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/FicheroPredefinidoFacade!com.openlopd.sessionbeans.lopd.FicheroPredefinidoFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean FicheroPredefinidoFacadeLocal.");
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>
}
