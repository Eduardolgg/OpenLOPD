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
import com.jkingii.datatables.JsonEntity;
import com.jkingii.datatables.ResponseConfig;
import com.utils.data.DateTimeEntity;
import java.io.Serializable;
import javax.persistence.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Destinatarios de soportes autorizados.
 *
 * @author Eduardo L. García Glez.
 */
@Entity
@Table(name = "destinatarios", schema = "lopd")
@NamedQueries({
    @NamedQuery(name = "Destinatario.findAll", query = ""
    + "SELECT d FROM Destinatario d WHERE d.borrado is null AND d.empresa = :empresa")
})
public class Destinatario extends DateTimeEntity implements Serializable, JsonEntity {

    private static Logger logger = LoggerFactory.getLogger(Destinatario.class);
    private static final long serialVersionUID = 1L;
    @Id
    @Column(length = 37)
    private String id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;
    @Column(name = "observaciones", nullable = true, length = 2000)
    private String observaciones;
    @Column(name = "fecha_alta", nullable = false)
    private Long fechaAlta;
    @Column(name = "fecha_baja", nullable = true)
    private Long fechaBaja;
    @Column(name = "fecha_alta_int", nullable = false)
    private Long fechaAltaInt;
    @Column(name = "fecha_baja_int", nullable = true)
    private Long fechaBajaInt;
    @Column(name = "borrado", nullable = true)
    private Long borrado;
    @Column(name = "borrado_por", nullable = true, length = 25)
    private String borradoPor;
    @Column(name = "active", nullable = true)
    private boolean active;

    public Destinatario() {
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;

    }

    public Destinatario(String id) {
        this.id = id;
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }

    public Destinatario(String id, Empresa empresa, String nombre, String descripcion,
            String observaciones, Long fechaAlta, Long fechaAltaInt,
            Long fechaBaja, Long fechaBajaInt) {
        this.id = id;
        this.empresa = empresa;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.observaciones = observaciones;
        this.fechaAlta = fechaAlta;
        this.fechaAltaInt = fechaAltaInt;
        this.fechaBaja = fechaBaja;
        this.fechaBajaInt = fechaBajaInt;
        active = true;
        borrado = null;
        this.borradoPor = null;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Long fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Long getFechaAltaInt() {
        return fechaAltaInt;
    }

    public void setFechaAltaInt(Long fechaAltaInt) {
        this.fechaAltaInt = fechaAltaInt;
    }

    public Long getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Long fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public Long getFechaBajaInt() {
        return fechaBajaInt;
    }

    public void setFechaBajaInt(Long fechaBajaInt) {
        this.fechaBajaInt = fechaBajaInt;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Destinatario)) {
            return false;
        }
        Destinatario other = (Destinatario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.lopd.Destinatario[ id=" + id + " ]";
    }

    @Override
    public JSONObject toJson(ResponseConfig config) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JSONObject toTableJson(ResponseConfig config) {
        JSONObject js = new JSONObject();

        try {
            js.put("DT_RowId", id);
            js.put("DT_RowClass", (fechaBaja != null ? "gradeA" : "gradeB"));
            js.put("0", nombre);
            js.put("1", descripcion);
            js.put("2", ManejadorFechas.getFechaHora(fechaAlta, config.getTimeZone()));
            js.put("3", (fechaBaja != null ? ManejadorFechas.getFechaHora(fechaBaja, config.getTimeZone()) : ""));
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
            js.put("5", "<a class=\"ui-icon ui-icon-plus\" href=\"./details.jsp?id="
                    + this.id + "\">Details</a>");
            return js;
        } catch (JSONException e) {
            logger.error("Imposible generar el json para tabla editable de [{}] Exception: [{}]",
                    this.toString(), e.getMessage());
            return null;
        }
    }    
}
