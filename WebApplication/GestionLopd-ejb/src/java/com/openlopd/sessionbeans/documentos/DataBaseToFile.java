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

import com.openlopd.entities.documentos.FileDataBase;
import com.jkingii.mail.utils.GenKey;
import java.io.*;
import java.util.Locale;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Envia un fichero de la base de datos al sistema de ficheros.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0
 */
public class DataBaseToFile {
    private static Logger logger = LoggerFactory.getLogger(DataBaseToFile.class);
    private final static ResourceBundle rb = ResourceBundle.getBundle("com.openlopd.config.config", Locale.getDefault());
    
    private File file;
    private String fileName;
    private FileDataBase dbFile;
    public static final String TMP_DIR = rb.getString("tempDir");

    /**
     * Inicializa el objeto a través de un fichero de base de datos.
     * @param dbFile 
     */
    public DataBaseToFile(FileDataBase dbFile) {
        this.dbFile = dbFile;
        this.file = null;
    }

    /**
     * Obtiene el fichero del sistema de ficheros.
     * @return fichero creado en el sistema de ficheros.
     */
    public File getFile() {
        return (file != null ? file : dbFileToFileSystem());
    }

    /**
     * Envía el fichero de base de datos al sistema de ficheros.
     * @return fichero creado en el sistema de ficheros.
     */
    private File dbFileToFileSystem() {
        OutputStream out;
        try {
            fileName = GenKey.newKey() + ".odt";
            out = new FileOutputStream(TMP_DIR + fileName);
            out.write(dbFile.getFile());
            out.close();
            return initFile();
        } catch (FileNotFoundException e) {
            logger.error("Fichero [{}] no encontrado. ", fileName);
            if(logger.isDebugEnabled()) {
                logger.debug("Exception: {}", e.getMessage());
            }
        } catch (IOException e) {
            logger.error("Error enviado fichero al sistema de ficheros.");
            if(logger.isDebugEnabled()) {
                logger.debug("Exception: {}", e.getMessage());
            }
        }
        return null;
    }

    /**
     * Inicializa el fichero.
     * 
     * Después de crear el fichero en el sistema de ficheros hay que crear un
     * objeto tipo FILE que apunte al mismo, para que sea compatible con la 
     * generación de plantillas.
     * 
     * @return File que apunta al fichero creado en el sistema de ficheros.
     */
    private File initFile() {
        file = new File(TMP_DIR + fileName);
        return file;
    }
    
    /**
     * Borra el fichero creado en el sistema de ficheros.
     */
    public void close() {
        if (!file.delete()) {
            logger.error("En close, error borrando el fichero [{}].", file.getName());
        }
    }
}
