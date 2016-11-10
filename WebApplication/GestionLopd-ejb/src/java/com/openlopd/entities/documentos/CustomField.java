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
 * Campo personalizado para la plantilla.
 * @author Eduardo L. García Glez.
 * @version 0.0.0
 */
@Entity
@Table (name = "custom_fields", schema = "public")
public class CustomField implements Serializable, JsonEntity {
    private static Logger logger = LoggerFactory.getLogger(CustomField.class);
    
    //<editor-fold defaultstate="collapsed" desc="Properties">
    private static final long serialVersionUID = 1L;
    @Id
    @Column (name = "id", length = 37)
    private String id;
    @ManyToOne(optional = false)
    private Empresa empresa;
    @ManyToOne(optional = false)
    private Shadow usuario;
    @Column (name = "field_id", length = 25, nullable = false)
    private String fieldId;
    @Column (name = "field_value", length = 2000, nullable = false)
    private String fieldValue;
    @Column (name = "descripcion", length = 2000, nullable = true)
    private String descripcion;
    @Column (name = "fecha_alta", nullable = false)
    private Long fechaAlta;
    @Column (name = "fecha_baja", nullable = true)
    private Long fechaBaja;
    @Column (name = "active", nullable = false)
    private Boolean active;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Constructor por defecto.
     */
    public CustomField() {
    }
    
    /**
     * Inicializa el objeto a través del identificacor único.
     * @param id Identificador único de la entidad.
     */
    public CustomField(String id) {
        this.id = id;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Section Get/Set">
    /**
     * Obtiene la descripción del campo.
     * @return Descripción descriptiva del campo.
     */
    public String getDescripcion(){
        return descripcion;
    }
    
    /**
     * Establece la descripción del campo.
     * @param descripcion Descripción descriptiva del campo.
     */
    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }
    /**
     * Obtiene si la fila está activa.
     * @return <code>true</code> si la fila se encuentra activa,
     * <code>false</false> en caso contrario.
     */
    public Boolean getActive() {
        return active;
    }
    
    /**
     * Establece la fila como activa.
     * @param active <code>true</code> si la fila se encuentra activa,
     * <code>false</false> en caso contrario.
     */
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    /**
     * Obtiene la empresa a la que pertenece.
     * @return Empresa a la que pertenece.
     */
    public Empresa getEmpresa() {
        return empresa;
    }
    
    /**
     * Establece la empresa a la que pertenece.
     * @param empresa Empresa a la que pertenece.
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    
    /**
     * Obtiene la fecha de alta en el sistema.
     * @return Fecha de alta en el sistema en milisegundos.
     */
    public Long getFechaAlta() {
        return fechaAlta;
    }
    
    /**
     * Establece la fecha de alta en el sistema.
     * @param fechaAlta Fecha de alta en el sistema en milisegundos.
     */
    public void setFechaAlta(Long fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    
    /**
     * Obtiene la fecha de baja en el sistema.
     * @return Fecha de baja en el sistema en milisegundos.
     */
    public Long getFechaBaja() {
        return fechaBaja;
    }
    
    /**
     * Establece la fecha de baja en el sistema.
     * @param fechaBaja Fecha de baja en el sistema en milisegundos.
     */
    public void setFechaBaja(Long fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
    
    /**
     * Obtiene el Identificador del campo de plantilla.
     * @return Identificador único del campo en las plantillas.
     */
    public String getFieldId() {
        return fieldId;
    }
    
    /**
     * Establece el Identificador del campo de plantilla.
     * @param fieldId Identificador único del campo en las plantillas.
     */
    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }
    
    /**
     * Obtiene el valor asociado al campo.
     * @return Valor asociado al campo.
     */
    public String getFieldValue() {
        return fieldValue;
    }
    
    /**
     * Establece el valor asociado al campo.
     * @param fieldValue Valor asociado al campo.
     */
    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
    
    /**
     * Obtiene el identificador único de la entidad.
     * @return Identificador único de la entidad.
     */
    public String getId() {
        return id;
    }
    
    /**
     * Establece el identificador único de la entidad.
     * @param id Identificador único de la entidad.
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Obtiene el usuario que añade la propiedad al sistema.
     * @return usuario que añade la propiedad al sistema.
     */
    public Shadow getUsuario() {
        return usuario;
    }
    
    /**
     * Establece el usuario que añade la propiedad al sistema.
     * @param usuario usuario que añade la propiedad al sistema.
     */
    public void setUsuario(Shadow usuario) {
        this.usuario = usuario;
    }
    //</editor-fold>
   
    //<editor-fold defaultstate="collapsed" desc="Default Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomField)) {
            return false;
        }
        CustomField other = (CustomField) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "com.openlopd.entities.documentos.CustomField[ id=" + id + " ]";
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="JSon">
    @Override
    public JSONObject toJson(ResponseConfig config) {
        logger.debug("Método sin implementar.");
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public JSONObject toTableJson(ResponseConfig config) {
        JSONObject js = new JSONObject();
        DateFormat d = DateFormat.getDateTimeInstance(DateFormat.SHORT,
                DateFormat.SHORT, Locale.getDefault());
        try {
            js.put("DT_RowId", id);
            js.put("DT_RowClass", (fechaBaja != null ? "gradeA" : "gradeB"));
            js.put("0", fieldId);
            js.put("1", fieldValue);
            js.put("2", descripcion);
            js.put("3", ManejadorFechas.getFechaHora(fechaAlta, config.getTimeZone()));
            js.put("4", (fechaBaja != null ? ManejadorFechas.getFechaHora(fechaAlta, null) : ""));
            return js;
        } catch (JSONException e) {
            logger.error("Imposible generar el json para tabla de [{}] Exception: ", 
                    this.toString(), e.getMessage());
            return null;
        }
    }
    
    @Override
    public JSONObject toTableEditableJson(ResponseConfig config) {
        JSONObject js = toTableJson(config);
        try {
            js.put("5", "<a class=\"table-action-EditData\">Edit</a>");
            js.put("6", "<a href=\"/Details/17\">Details</a>");
            return js;
        } catch (JSONException e) {
            logger.error("Imposible generar el json para tabla editable de [{}] Exception: ", 
                    this.toString(), e.getMessage());
            return null;
        }
    }
    //</editor-fold>
    
}
