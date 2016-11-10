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

package com.openlopd.web.controllers.privatearea.admin;

import com.openlopd.agpd.nota.tablascomunes.ErrorEnvio;
import com.openlopd.business.files.GenerarDocumentosLocal;
import com.openlopd.documents.CumplimentarPlantilla;
import com.openlopd.entities.lopd.Fichero;
import com.openlopd.entities.seguridad.Shadow;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.sessionbeans.documentos.PlantillaFacadeLocal;
import com.openlopd.sessionbeans.interfaz.OperacionLopdFacadeLocal;
import com.openlopd.sessionbeans.lopd.FicheroFacadeLocal;
import com.openlopd.sessionbeans.seguridad.ShadowFacadeLocal;
import com.openlopd.web.controllers.privatearea.AbstractWebPageController;
import com.jkingii.mail.entities.OutBox;
import com.jkingii.mail.sessionbeans.OutBoxFacadeLocal;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controla el formulario de administración.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 14 de dic de 2012
 */
public class CAdmin extends AbstractWebPageController implements Serializable {
    private GenerarDocumentosLocal generarDocumentos = lookupGenerarDocumentosLocal();
    private OutBoxFacadeLocal outBoxFacade1 = lookupOutBoxFacadeLocal1();
    private ShadowFacadeLocal shadowFacade = lookupShadowFacadeLocal();
    private OutBoxFacadeLocal outBoxFacade = lookupOutBoxFacadeLocal();
    private FicheroFacadeLocal ficheroFacade = lookupFicheroFacadeLocal();
    private PlantillaFacadeLocal plantillaFacade = lookupPlantillaFacadeLocal();
    private OperacionLopdFacadeLocal operacionLopdFacade = lookupOperacionLopdFacadeLocal();
    private static Logger logger = LoggerFactory.getLogger(CAdmin.class);
    private Boolean docGenerado = null;
    
    private String op = "";

    public CAdmin() {
        super(ColumnasPermisos.SYS_ADMIN);
    }

    /**
     * Establece el código de la operación de administración a realizar.
     * @param op Operación de administración del sistema a realizar.
     * @see getRunOp
     */
    public void setOp(String op) {
        this.op = op;
    }

    /**
     * Ejecuta una operación de sistema.
     * @return Mensaje del resultado de la operación.
     */
    public String getRunOp() {
        switch (op) {
            case "actualizarListOperaciones":
                return actualizarListOperaciones();
            case "insertarPlantillas":
                return insertarPlantillas();
            case "cambiarRegistroFicherosAuto":
                return cambiarRegistroFicherosAuto();
            case "cambiarMailingSendStatus":
                return cambiarMailingSendStatus();
            case "generarDocumento":
                return generarDocumento();
            default:
                return "";
        }
    }
    
    private String generarDocumento() {
        try {
            String docId = generarDocumentos.CumplimentarPlantilla(session.getAccessInfo(), "Clausula_e-mail", ColumnasPermisos.SYS_ADMIN).getId();
            docGenerado = Boolean.TRUE;
            return "Documento Generado: " + docId;
        } catch (Exception e) {
            docGenerado = Boolean.FALSE;
            return e.getMessage();
        } 
    }
    
    public Boolean getDocGenerado() {
        return docGenerado;
    }

    /**
     * Actualiza las operaciones LOPD de todo el sistema.
     * @return Mensaje con el resultado de la operación.
     */
    private String actualizarListOperaciones() {
        try {
            operacionLopdFacade.getActualizarListOperaciones(session.getAccessInfo());
            return "Operaciones Actualizadas";
        } catch (Exception e) {
            return e.getMessage();
        }        
    }
    
    private String insertarPlantillas() {
        try {
            plantillaFacade.insertarPlantillasPredefinidas(session.getAccessInfo());
            return "Plantillas Insertadas";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private String cambiarRegistroFicherosAuto() {
        ficheroFacade.setAutoRegistroActivo(!ficheroFacade.isAutoRegistroActivo());
        if(ficheroFacade.isAutoRegistroActivo()) {
            return "Registro de ficheros activado";
        } else {
            return "Registro de ficheros desactivado";
        }
    }
    
    public boolean isAutoRegistroActivo() {
        return ficheroFacade.isAutoRegistroActivo();
    }
    
    
    private String cambiarMailingSendStatus() {
        outBoxFacade.setActive(!outBoxFacade.isActive());
        if(outBoxFacade.isActive()) {
            return "Envío de Mails activado";
        } else {
            return "Envío de Mails desactivado";
        }
    }
    
    public boolean isMailingActivo() {
        return outBoxFacade.isActive();
    }
    
    public int getNumFicherosConError() {
        List<Fichero> ficheros = ficheroFacade.findFicherosConError(session.getAccessInfo());
        return ficheros != null ? ficheros.size() : 0;
    }
    
    public List<Fichero> getFicherosConError() {
        return ficheroFacade.findFicherosConError(session.getAccessInfo());
    }
    
    public String getFileErrorDesc(String id) {
        for (ErrorEnvio e : ErrorEnvio.values()) {
            if (e.getValue().equals(id)) {
                return e.getText();
            }
        }
        return id;
    }
    
    public int getNumFicherosPorNotificar() {
        List<Fichero> ficheros = ficheroFacade.ficherosSinRegistrar();
        return ficheros != null ? ficheros.size() : 0;
    }
    
    public int getNumMailsConError() {
        List<OutBox> mails = outBoxFacade.getErrorMessages();
        return mails != null ? mails.size() : 0;
    }
    
    public int getNumUsuariosOnline() {
        List<Shadow> s = shadowFacade.getOnlineUsers(session.getAccessInfo());
        return s.size();
    }
    
    public List<Shadow> getUsuariosOnline() {
        List<Shadow> s = shadowFacade.getOnlineUsers(session.getAccessInfo());
        int toIndex = s.size() >= 10 ? 10 : s.size();
        return s.subList(0, toIndex);
    }
    
    public List<Shadow> getUltimosUsuariosOnline() {
        List<Shadow> s = shadowFacade.getLastOnlineUsers(session.getAccessInfo());        
        int toIndex = s.size() >= 10 ? 10 : s.size();
        return s.subList(0, toIndex);
    }
    
    public boolean getEstadoTmpDir() {
        return generarDocumentos.existTmpDir();
    }

    //<editor-fold defaultstate="collapsed" desc="Lookup">
    private OperacionLopdFacadeLocal lookupOperacionLopdFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (OperacionLopdFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/OperacionLopdFacade!com.openlopd.sessionbeans.interfaz.OperacionLopdFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible acceder al session bean OperacionLopdFacadeLocal.");
            throw new RuntimeException(ne);
        }
    }

    private PlantillaFacadeLocal lookupPlantillaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (PlantillaFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/PlantillaFacade!com.openlopd.sessionbeans.documentos.PlantillaFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible acceder al session bean PlantillasFacadeLocal.");
            throw new RuntimeException(ne);
        }
    }
    
    private FicheroFacadeLocal lookupFicheroFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (FicheroFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/FicheroFacade!com.openlopd.sessionbeans.lopd.FicheroFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible acceder al session bean ficheroFacadeLocal.");
            throw new RuntimeException(ne);
        }
    }
    
    private OutBoxFacadeLocal lookupOutBoxFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (OutBoxFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/OutBoxFacade!com.jkingii.mail.sessionbeans.OutBoxFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible acceder al session bean outBoxFacadeLocal.");
            throw new RuntimeException(ne);
        }
    }
    
    private ShadowFacadeLocal lookupShadowFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ShadowFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/ShadowFacade!com.openlopd.sessionbeans.seguridad.ShadowFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible acceder al session bean shadowFacade.");
            throw new RuntimeException(ne);
        }
    }
    
    private OutBoxFacadeLocal lookupOutBoxFacadeLocal1() {
        try {
            Context c = new InitialContext();
            return (OutBoxFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/OutBoxFacade!com.jkingii.mail.sessionbeans.OutBoxFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible acceder al session bean outBoxFacade.");
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>

    private GenerarDocumentosLocal lookupGenerarDocumentosLocal() {
        try {
            Context c = new InitialContext();
            return (GenerarDocumentosLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/GenerarDocumentos!com.openlopd.business.files.GenerarDocumentosLocal");
        } catch (NamingException ne) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
