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

package com.openlopd.sessionbeans.documentos;

import com.elgg.utils.lang.Hexadecimal;
import com.elgg.utils.mimetype.MimeType;
import com.openlopd.entities.documentos.FileDataBase;
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.seguridad.Shadow;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.utils.primarykey.GenKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Envía un fichero del sistema de ficheros a la base de datos.
 * @author Eduardo L. García Glez.
 * @version 0.0.0
 */
public class FileToDataBase {
    private File file;
    private FileDataBase fileDataBase;
    private Empresa empresa;
    private Shadow userInfo;
    private ColumnasPermisos permiso;

    /**
     * Inicializa el objeto con los datos requeidos.
     * @param file fichero en el sistema de ficheros.
     * @param empresa empresa propietaria del fichero.
     * @param userInfo usuario que crea el fichero.
     * @param permiso permiso de acceso al fichero.
     */
    public FileToDataBase(File file, Empresa empresa, Shadow userInfo,
            ColumnasPermisos permiso) {
        this.file = file;
        this.empresa = empresa;
        this.userInfo = userInfo;
        this.permiso = permiso;
    }
    
    /**
     * Hase que el fichero sea enviado a la base de datos.
     * 
     * Una vez que el fichero es almacenado en la base de datos
     * es borrado del sistema de ficheros.
     * 
     * @return Entity FileDataBase, con la referencia a la base de datos.
     * @throws FileNotFoundException si no se encuentra el fichero.
     * @throws IOException Si existen problemas de entrada/salida.
     */
    public FileDataBase commit() throws FileNotFoundException, IOException {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] fi = readFile();
            fileDataBase = new FileDataBase(GenKey.newKey(), empresa, 
                    userInfo, new Date().getTime(), file.getName(),
                    new MimeType().factory().getMime(file.getName(), fi), 
                    fi, Hexadecimal.getHex(md5.digest(fi)), file.length(), 
                    permiso);
            file.delete();
            return fileDataBase;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(FileToDataBase.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * Pasa el fichero del sistema de ficheros a un byte[]
     * @return byte[] con el fichero.
     * @throws FileNotFoundException si no se encuentra el fichero en el sistema
     * de ficheros.
     * @throws IOException Si existen problemas de entrada/salida.
     */
    private byte[] readFile() throws FileNotFoundException, IOException {
        FileInputStream fi = new FileInputStream(file);
        //TODO ojo cuando file.length devuelve 0L
        byte[] b = new byte[(int) file.length()];
        if(fi.read(b) < file.length()) 
            throw new IOException("No se ha podido leer todo el fichero.");
        return b;
    }
}
