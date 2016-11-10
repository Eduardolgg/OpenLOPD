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

import com.elgg.utils.Calendar.ManejadorFechas;
import com.openlopd.entities.empresas.Empresa;
import com.google.gson.annotations.SerializedName;
import com.jkingii.datatables.JsonEntity;
import com.jkingii.datatables.ResponseConfig;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tipos de soportes utilizados por la empresa.
 * @author Eduardo L. García Glez.
 */
@Entity
@Table(name = "tipos_soporte", schema = "lopd")
@NamedQueries({
    @NamedQuery(name = "TipoSoporte.findByActives", query = ""
    + "SELECT t FROM TipoSoporte t "
    + "WHERE t.active = :active AND t.empresa = :empresa AND t.borrado is NULL"),
    @NamedQuery(name = "TipoSoporte.findAlta", query = ""
        + "SELECT t FROM TipoSoporte t "
        + "WHERE t.active = TRUE AND t.empresa = :empresa"
        + "  AND t.borrado is NULL"
        + "  AND t.fechaBaja is NULL")
})
public class TipoSoporte implements Serializable, JsonEntity {

    private static Logger logger = LoggerFactory.getLogger(TipoSoporte.class);
    private static final long serialVersionUID = 1L;
    @Id
    @SerializedName("DT_RowId")
    @Column(length = 37)
    private String id;
    @Column(name = "nombre", nullable = false, length = 20)
    private String nombre;
    @Column(name = "descripcion", nullable = true, length = 255)
    private String descripcion;
    @ManyToOne(optional = false)
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
    @Column(name = "fecha_alta", nullable = false)
    private Long fechaAlta;
    @Column(name = "fecha_baja", nullable = true)
    private Long fechaBaja;
    @Column(name = "borrado", nullable = true)
    private Long borrado;
    @Column(name = "borrado_por", nullable = true, length = 25)
    private String borradoPor;
    @Column(name = "active", nullable = false)
    private Boolean active;

    public TipoSoporte() {
        this.borrado = null;
        this.borradoPor = null;
        this.active = true;
    }

    public TipoSoporte(String id) {
        this.id = id;
        this.borrado = null;
        this.borradoPor = null;
        this.active = true;
    }

    public TipoSoporte(String id, String nombre, String descripcion, 
            Empresa empresa, Long fechaAlta, Long fechaBaja) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.empresa = empresa;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
        this.borrado = null;
        this.borradoPor = null;
        this.active = true;
    }

    /**
     * Indica si la entidad ha sido borrada.
     *
     * @return <code>true</code> si está borrada, <code>false</code> en caso
     * contrario.
     */
    public Long getBorrado() {
        return borrado;
    }

    /**
     * Indica si la entidad ha sido borrada.
     *
     * @param borrado <code>true</code> si está borrada, <code>false</code> en
     * caso contrario.
     */
    public void setBorrado(Long borrado) {
        this.borrado = borrado;
    }

    /**
     * Usuario que borra la entidad.
     *
     * @return Id del usuario que borra la entidad.
     */
    public String getBorradoPor() {
        return borradoPor;
    }

    /**
     * Usuario que borra la entidad.
     *
     * @param borradoPor Id del usuario que borra la entidad.
     */
    public void setBorradoPor(String borradoPor) {
        this.borradoPor = borradoPor;
    }

    /**
     * Obtiene si la plantilla está activa (borrada)
     *
     * Cuando se elimina una plantilla del sistema este bit se desactiva de esta
     * forma puede mantenerse un historial.
     *
     * @return <code>true</code> si la plantilla está activa, <code>false</code>
     * en caso contrario.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Establece si la plantilla está activa (borrada)
     *
     * Cuando se elimina una plantilla del sistema este bit se desactiva de esta
     * forma puede mantenerse un historial.
     *
     * @param active <code>true</code> si la plantilla está activa,
     * <code>false</code> en caso contrario.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Long fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Long getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Long fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TipoSoporte other = (TipoSoporte) obj;
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
            js.put("nombre", nombre);
            js.put("descripcion", descripcion);
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
            js.put("DT_RowClass", (fechaBaja != null ? "GradeA" : "GradeC"));
            js.put("0", nombre);
            js.put("1", descripcion);
            js.put("2", ManejadorFechas.getFechaHora(fechaAlta, config.getTimeZone()));
            js.put("3", (fechaBaja != null ? ManejadorFechas
                    .getFechaHora(fechaBaja, config.getTimeZone()) : ""));
            return js;
        } catch (JSONException e) {
            logger.error("Imposible generar el json para tabla de [{}] Exception: [{}]",
                    this.toString(), e.getMessage());
            return null;
        }
    }

    @Override
    public JSONObject toTableEditableJson(ResponseConfig config) {
        JSONObject js = toTableJson(config);
        try {
            js.put("4", "<a id=\"" + this.id + "\" class=\"table-action-EditData ui-icon ui-icon-pencil\">Edit</a>");
//            js.put("5", "<a class=\"ui-icon ui-icon-plus\" href=\"./details.jsp?id="
//                    + this.id + "\">Details</a>");
            return js;
        } catch (JSONException e) {
            logger.error("Imposible generar el json para tabla editable de [{}] Exception: [{}]",
                    this.toString(), e.getMessage());
            return null;
        }
    }
}
