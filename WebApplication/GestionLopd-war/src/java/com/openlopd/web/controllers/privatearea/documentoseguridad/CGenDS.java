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

package com.openlopd.web.controllers.privatearea.documentoseguridad;

import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.entities.lopd.DocumentoSeguridad;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.exceptions.SeguridadWriteException;
import com.openlopd.exceptions.SeguridadWriteLimitException;
import com.openlopd.sessionbeans.lopd.DocumentoSeguridadFacadeLocal;
import java.io.Serializable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controlador que permite generar los documentos de seguridad.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 16 de sep de 2012
 */
public class CGenDS implements Serializable {
    DocumentoSeguridadFacadeLocal documentoSeguridadFacade = lookupDocumentoSeguridadFacadeLocal();

    private static Logger logger = LoggerFactory.getLogger(CGenDS.class);

    public CGenDS() {
    }

    public String createNuevoDS(AccessInfo accessInfo) {
        try {
            DocumentoSeguridad ds = documentoSeguridadFacade
                    .cumplimentarDocSeg(accessInfo);
            
            if (ds == null) {
                return "{\"status\": \"ERROR\", \"message\": "
                    + "\"En estos momentos no podemos generar el Documento de "
                        + "seguridad, por favor inténtelo más tarde.\"}";
            }
            
            return "{\"status\": \"OK\", \"message\": "
                    + "\"Documento de seguridad Generado correctamente.\"}";
        } catch (UnknownColumnException e) { 
            logger.error("Imposible!!! la columna debe existir.");
            return "\"status\": \"ERROR\", \"message\": \"Error generando el "
                    + "documento de seguridad.\"";
        } catch (SeguridadWriteException e) { 
            return "\"status\": \"ERROR\", \"message\": \"No tiene permisos para "
                    + "realizar esta operación.\"";
        } catch (SeguridadWriteLimitException e) {
            return "\"status\": \"ERROR\", \"message\": \"Ha excedido el límite "
                    + "de documentos a generar\"";
        }
    }

    private DocumentoSeguridadFacadeLocal lookupDocumentoSeguridadFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (DocumentoSeguridadFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/DocumentoSeguridadFacade!com.openlopd.sessionbeans.lopd.DocumentoSeguridadFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean lookupDocumentoSeguridadFacadeLocal.");
            throw new RuntimeException(ne);
        }
    }
}
