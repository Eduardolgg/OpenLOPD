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

package com.openlopd.entities.lopd;

import com.google.gson.annotations.SerializedName;
import com.jkingii.datatables.JsonEntity;
import com.jkingii.datatables.ResponseConfig;
import java.io.Serializable;
import javax.persistence.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Eduardo L. García Glez.
 */
@Entity
@Table(name = "unidades_informacion", schema = "lopd")
@NamedQueries({
    @NamedQuery(name = "UnidadesInformacion.findByActives", query = ""
        + "SELECT u FROM UnidadesInformacion u WHERE u.active = :active")
})
public class UnidadesInformacion implements Serializable, JsonEntity {
    private static Logger logger = LoggerFactory.getLogger(UnidadesInformacion.class);
    
    private static final long serialVersionUID = 1L;
    @Id
    @SerializedName("DT_RowId")
    private Long id;
    @Column(name = "descripcion", nullable = false, length = 20)
    private String descripcion;
    @Column(name = "active", nullable = false)
    private Boolean active;
    @Column (name = "simbolo", nullable = false, length = 2)
    private String simbolo;
    @Column (name = "tamanio", nullable = false, length = 20)
    private String tamanio;

    public UnidadesInformacion() {
    }

    public UnidadesInformacion(Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UnidadesInformacion other = (UnidadesInformacion) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return descripcion;
    }

    @Override
    public JSONObject toJson(ResponseConfig config) {
        JSONObject js = new JSONObject();
        try {
            js.put("id", id);
            js.put("descripcion", descripcion);
            js.put("tam", tamanio);
            js.put("simbolo", simbolo);
            return js;
        } catch (JSONException e) {
            logger.error("Imposible generar el json de [{}] Exception: ", 
                    this.toString(), e.getMessage());
            return null;
        } 
    }

    @Override
    public JSONObject toTableJson(ResponseConfig config) {
        JSONObject js = new JSONObject();
        try {
            js.put("DT_RowId", id);
//            js.put("DT_RowClass", );
            js.put("0", descripcion);
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
            js.put("1", "<a class=\"table-action-EditData\">Edit</a>");
            js.put("2", "<a href=\"/Details/17\">Details</a>");
            return js;
        } catch (JSONException e) {
            logger.error("Imposible generar el json para tabla editable de [{}] Exception: ", 
                    this.toString(), e.getMessage());
            return null;
        }
    }
}
