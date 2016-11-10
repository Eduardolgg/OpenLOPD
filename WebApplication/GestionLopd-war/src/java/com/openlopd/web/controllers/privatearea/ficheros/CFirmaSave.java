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

import com.openlopd.entities.documentos.FileDataBase;
import com.openlopd.entities.lopd.Fichero;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.sessionbeans.documentos.FileDataBaseFacadeLocal;
import com.openlopd.sessionbeans.lopd.FicheroFacadeLocal;
import com.openlopd.web.controllers.privatearea.AbstractWebPageController;
import com.jkingii.mail.utils.GenKey;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Objects;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.axis.encoding.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Eduardo L. García Glez.
 */
public class CFirmaSave extends AbstractWebPageController implements Serializable {
    FileDataBaseFacadeLocal fileDataBaseFacade = lookupFileDataBaseFacadeLocal();
    FicheroFacadeLocal ficheroFacade = lookupFicheroFacadeLocal();
    private static Logger logger = LoggerFactory.getLogger(CFirmaSave.class);
    private String idFichero;
    private String firmaB64;

    public CFirmaSave() {
        super(ColumnasPermisos.FICHEROS);
    }

    public void setIdFichero(String idFichero) {
        this.idFichero = idFichero;
    }

    public void setFirmaB64(String firmaB64) {
        this.firmaB64 = firmaB64;
    }
    
    public String getSave() {
        try {
            Fichero f = ficheroFacade.find(idFichero);
//            f.getSolicitud().setFile(new String(Base64.decode(firmaB64), "ISO-8859-1").getBytes());
            f.setActive(true);
            
            FileDataBase fb = new FileDataBase();
            fb.setEmpresa(session.getAccessInfo().getSubEmpresa());
//            fb.setFile(new String(Base64.decode(firmaB64), "ISO-8859-1").getBytes());
            fb.setFile(new String(Base64.decode(firmaB64)).getBytes());
            
//            fb.setFile(new String(Base64.decode(firmaB64)).getBytes());
            fb.setFilename(f.getSolicitud().getFilename());
            fb.setMd5(f.getSolicitud().getMd5());
            fb.setMimeType(f.getSolicitud().getMimeType());
            fb.setPermiso(f.getSolicitud().getPermiso());
            fb.setSize(new Long(fb.getFile().length));
            fb.setUploadDate(new Date().getTime());
            fb.setUsuario(session.getAccessInfo().getUserInfo());
            fb.setId(GenKey.newKey());
            
            f.setSolicitud(fb);
            
            
            fileDataBaseFacade.create(f.getSolicitud());
            ficheroFacade.edit(f);
            return "ok";
//        } catch (UnsupportedEncodingException ex) {
//            logger.error("Imposible!!! encoding ISO-8859-1 no reconocido,"
//                    + "firma no guardada empresa [{}] documento [{}]",
//                     session.getAccessInfo().getSubEmpresa().getIdEmpresa(), firmaB64);
        } catch (Exception ex) {
            logger.error("Imposible guardar el la firma"
                    + "empresa [{}] documento [{}]", 
                    session.getAccessInfo().getSubEmpresa().getIdEmpresa(), firmaB64);
        }
        return "Error";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.idFichero);
        hash = 97 * hash + Objects.hashCode(this.firmaB64);
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
        final CFirmaSave other = (CFirmaSave) obj;
        if (!Objects.equals(this.idFichero, other.idFichero)) {
            return false;
        }
        if (!Objects.equals(this.firmaB64, other.firmaB64)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CFirmaSave{" + "idFichero=" + idFichero + ", firmaB64=" + firmaB64 + '}';
    }

    private FicheroFacadeLocal lookupFicheroFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (FicheroFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/FicheroFacade!com.openlopd.sessionbeans.lopd.FicheroFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean fichero, se ha perdido la firma,"
                    + "empresa [{}] documento [{}]", session.getAccessInfo().getSubEmpresa().getIdEmpresa(), firmaB64);
            throw new RuntimeException(ne);
        }
    }  

    private FileDataBaseFacadeLocal lookupFileDataBaseFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (FileDataBaseFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/FileDataBaseFacade!com.openlopd.sessionbeans.documentos.FileDataBaseFacadeLocal");
        } catch (NamingException ne) {
            
            logger.error("Imposible obtener el session bean fileDataBase, se ha perdido la firma,"
                    + "empresa [{}] documento [{}]", session.getAccessInfo().getSubEmpresa().getIdEmpresa(), firmaB64);
            throw new RuntimeException(ne);
        }
    }
    
}
