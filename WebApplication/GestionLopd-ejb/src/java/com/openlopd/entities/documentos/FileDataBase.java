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

package com.openlopd.entities.documentos;

import com.elgg.utils.Calendar.ManejadorFechas;
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.seguridad.Shadow;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.jkingii.datatables.JsonEntity;
import com.jkingii.datatables.ResponseConfig;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import javax.persistence.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Almacén de los ficheros delsistema.
 * @author Eduardo L. García Glez.
 * @version 0.0.0
 */
@Entity
@Table (name = "file_data_base", schema = "public")
public class FileDataBase implements Serializable, JsonEntity {
    private static Logger logger = LoggerFactory.getLogger(FileDataBase.class);
        
    //<editor-fold defaultstate="collapsed" desc="Properties">
    @Id
    @Column (name = "id", length = 37)
    private String id;
    @ManyToOne(optional = false)
    private Empresa empresa;
    @ManyToOne(optional = false)
    private Shadow usuario;
    @Column (name = "upload_date", nullable = false)
    private Long uploadDate;
    @Column (name = "filename", nullable = false, length = 255)
    private String filename;
    @Column (name = "mime_type", nullable = false, length = 50)
    private String mimeType;
    @Basic(fetch = FetchType.LAZY, optional = true)
    @Column (name = "file", nullable = false)
    @Lob
    private byte[] file;
    @Column (name = "md5", nullable = false, length = 32)
    private String md5;
    @Column (name = "file_size", nullable = false)
    private Long size;
    @Enumerated(EnumType.ORDINAL)
    @Column (name = "permiso", nullable = true)
    private ColumnasPermisos permiso;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructores">
    /**
     * Constructor por defecto.
     */
    public FileDataBase() {
    }
    
    /**
     * Inicializa la entidad por su id.
     * @param id id único del documento.
     */
    public FileDataBase(String id) {
        this.id = id;
    }
    
    /**
     * Inicializa todos los parámetros del objeto.
     * @param id id único del documento.
     * @param empresa empresa a la que pertenece.
     * @param usuario usuario que realiza la carga del fichero.
     * @param uploadDate fecha de carga.
     * @param filename nombre del fichero.
     * @param mimeType tipo del fichero.
     * @param file fichero
     * @param md5 md5 de verificación del fichero.
     * @param size tamaño del fichero.
     */
     public FileDataBase(String id, Empresa empresa, Shadow usuario, Long uploadDate, 
             String filename, String mimeType, byte[] file, String md5, Long size,
             ColumnasPermisos permiso) {
        this.id = id;
        this.empresa = empresa;
        this.usuario = usuario;
        this.uploadDate = uploadDate;
        this.filename = filename;
        this.mimeType = mimeType;
        this.file = file;
        this.md5 = md5;
        this.size = size;
        this.permiso = permiso;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Section GetSet">
     /**
      * Obtiene la empresa a la que pertenece el fichero.
      * @return Objeto con la empresa a la que pertenece el fichero.
      */
     public Empresa getEmpresa() {
         return empresa;
     }
     
     /**
      * Establece la empresa a la que pertenece el fichero.
      * @param empresa Objeto con la empresa a la que pertenece el fichero.
      */
     public void setEmpresa(Empresa empresa) {
         this.empresa = empresa;
     }
     
     /**
      * Obtiene el fichero en array de bytes.
      * @return fichero en array de bytes.
      */
     public byte[] getFile() {
         return file;
     }
     
     /**
      * Establece el fichero en array de bytes.
      * @param file fichero en array de bytes.
      */
     public void setFile(byte[] file) {
         this.file = file;
     }
     
     /**
      * Obtiene el nombre del fichero.
      * @return Nombre del fichero.
      */
     public String getFilename() {
         return filename;
     }
     
     /**
      * Establece el nombre del fichero.
      * @param filename Nombre del fichero.
      */
     public void setFilename(String filename) {
         this.filename = filename;
     }
     
     /**
      * Obtiene el identificador único del fichero.
      * @return Identificador único del fichero.
      */
     public String getId() {
         return id;
     }
     
     /**
      * Establece el identificador único del fichero.
      * @param id Identificador único del fichero.
      */
     public void setId(String id) {
         this.id = id;
     }
     
     /**
      * Obtiene la suma de verificación.
      * @return Suma de verificación del fichero.
      */
     public String getMd5() {
         return md5;
     }
     
     /**
      * Establece la suma de verificación.
      * @param md5 Suma de verificación del fichero.
      */
     public void setMd5(String md5) {
         this.md5 = md5;
     }
     
     /**
      * Establece el tipo del fichero.
      * @return Tipo del fichero.
      */
     public String getMimeType() {
         return mimeType;
     }
     
     /**
      * Obtiene el tipo del fichero.
      * @param mimeType Tipo del fichero.
      */
     public void setMimeType(String mimeType) {
         this.mimeType = mimeType;
     }
     
     /**
      * Obtiene el tamaño del fichero en bytes.
      * @return Tamaño del fichero en bytes.
      */
     public Long getSize() {
         return size;
     }
     
     /**
      * Establece el tamaño del fichero en bytes.
      * @param size Tamaño del fichero en bytes.
      */
     public void setSize(Long size) {
         this.size = size;
     }
     
     /**
      * Obtiene la fecha de carga del fichero.
      * @return fecha de carga del fichero en milisegundos.
      */
     public Long getUploadDate() {
         return uploadDate;
     }
     
     /**
      * Establece la fecha de carga del fichero.
      * @param uploadDate fecha de carga del fichero en milisegundos.
      */
     public void setUploadDate(Long uploadDate) {
         this.uploadDate = uploadDate;
     }
     
     /**
      * Obtiene el usuario que cargó el fichero.
      * @return Usuario que cargó el fichero.
      */
     public Shadow getUsuario() {
         return usuario;
     }
     
     /**
      * Establece el usuario que cargó el fichero.
      * @param usuario Usuario que cargó el fichero.
      */
     public void setUsuario(Shadow usuario) {
         this.usuario = usuario;
     }

     /**
      * Obtiene el códio de permiso asignado al documento.
      * 
      * Este permiso es que el debe tener el usuario asignado para luego
      * poder acceder en modos de lectura/escritura.
      * 
      * @return Tipo de permiso que aplica sobre el documento.
      */
     public ColumnasPermisos getPermiso() {
         return permiso;
     }

     /**
      * Establece código de permiso del documento.
      * 
      * Este permiso es que el debe tener el usuario asignado para luego
      * poder acceder en modos de lectura/escritura.
      * 
      * @param permiso Tipo de permiso que aplica sobre el documento.
      */
     public void setPermiso(ColumnasPermisos permiso) {
         this.permiso = permiso;
     }
     //</editor-fold>

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FileDataBase other = (FileDataBase) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "FileDataBase{" + "id=" + id + ", empresa=" + empresa.getRazonSocial() + ", usuario=" + usuario.getUsuario() + ", uploadDate=" + uploadDate + ", filename=" + filename + ", mimeType=" + mimeType + ", md5=" + md5 + ", size=" + size + '}';
    }

    @Override
    public JSONObject toJson(ResponseConfig config) {
        JSONObject js = new JSONObject();
        try {
            js.put("id", id);
            js.put("empresa", empresa);
            js.put("usuario", usuario);
            js.put("uploadDate", ManejadorFechas.getFechaHora(uploadDate, 
                    config.getTimeZone()));
            js.put("filename", filename);
            js.put("mimeType", mimeType);
            js.put("md5", md5);
            js.put("size", size);
            return js;
        } catch (JSONException ex) {
            logger.error("Error generando el json para [{}], Exception: {}", 
                    this.toString(), ex);
            return null;
        }
    }

    @Override
    public JSONObject toTableJson(ResponseConfig config) {
        logger.debug("Método sin implementar.");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JSONObject toTableEditableJson(ResponseConfig config) {
        logger.debug("Método sin implementar.");
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
