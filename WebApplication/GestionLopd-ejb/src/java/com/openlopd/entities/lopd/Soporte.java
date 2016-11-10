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
import com.openlopd.entities.empresas.Persona;
import com.google.gson.annotations.SerializedName;
import com.jkingii.datatables.JsonEntity;
import com.jkingii.datatables.ResponseConfig;
import com.utils.data.DateTimeEntity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Inventario de soportes LOPD
 *
 * @author Eduardo L. García Glez.
 */
@Entity
@Table(name = "soportes", schema = "lopd")
public class Soporte extends DateTimeEntity implements Serializable, JsonEntity {

    private static Logger logger = LoggerFactory.getLogger(Soporte.class);
    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="Properties">
    @Id
    @SerializedName("DT_RowId")
    private String id;
    @SerializedName("0")
    @ManyToOne(optional = false)
    private Empresa empresa;
    @SerializedName("1")
    @Column(name = "descripcion", nullable = true, length = 255)
    private String descripcion;
    @SerializedName("2")
    @Column(name = "observaciones", nullable = true, length = 20000)
    private String observaciones;
    @SerializedName("3")
    @Column(name = "show_obs_in_docs", nullable = true)
    private Boolean showObsInDocs;
    @SerializedName("4")
    @Column(name = "fecha_alta", nullable = false)
    private Long fechaAlta;
    @SerializedName("5")
    @Column(name = "fecha_baja", nullable = true)
    private Long fechaBaja;
    @Column(name = "fecha_alta_int", nullable = false)
    private Long fechaAltaInt;
    @Column(name = "fecha_baja_int", nullable = true)
    private Long fechaBajaInt;
    @Column(name = "etiqueta", nullable = true, length = 255)
    private String etiqueta;
    @Column(name = "sn", nullable = true, length = 50)
    private String sn;//TODO: artículo 92.1 ej, no es posible etiquetar este soporte con los medios de los que dispone la empresa.
    private Integer capacidad;
    @Column(name = "borrado", nullable = true)
    private Long borrado;
    @Column(name = "borrado_por", nullable = true, length = 25)
    private String borradoPor;
    @Column(name = "active", nullable = true)
    private boolean active;
    @ManyToOne
    private UnidadesInformacion unidades;
    @ManyToOne
    private TipoSoporte tipoSoporte;
    @OneToMany
    private List<Fichero> ficheros;
    //    private List<Responsable> responsables;
    @ManyToMany
    private List<Persona> personas;
    @ManyToOne
    private ModoDestruccion modoDestruccion;
    //</editor-fold>

    public Soporte() {
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }

    public Soporte(String id) {
        this.id = id;
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
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

    public List<Fichero> getFicheros() {
        return ficheros;
    }

    public void setFicheros(List<Fichero> ficheros) {
        this.ficheros = ficheros;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ModoDestruccion getModoDestruccion() {
        return modoDestruccion;
    }

    public void setModoDestruccion(ModoDestruccion modoDestruccion) {
        this.modoDestruccion = modoDestruccion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public List<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(List<Persona> personas) {
        this.personas = personas;
    }

    public Boolean getShowObsInDocs() {
        return showObsInDocs;
    }

    public void setShowObsInDocs(Boolean showObsInDocs) {
        this.showObsInDocs = showObsInDocs;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public TipoSoporte getTipoSoporte() {
        return tipoSoporte;
    }

    public void setTipoSoporte(TipoSoporte tipoSoporte) {
        this.tipoSoporte = tipoSoporte;
    }

    public UnidadesInformacion getUnidades() {
        return unidades;
    }

    public void setUnidades(UnidadesInformacion unidades) {
        this.unidades = unidades;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Soporte other = (Soporte) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Soporte{" + "id=" + id + ", empresa=" + empresa + ", descripcion=" + descripcion + ", observaciones=" + observaciones + ", showObsInDocs=" + showObsInDocs + ", fechaAlta=" + fechaAlta + ", fechaBaja=" + fechaBaja + ", fechaAltaInt=" + fechaAltaInt + ", fechaBajaInt=" + fechaBajaInt + ", etiqueta=" + etiqueta + ", sn=" + sn + ", capacidad=" + capacidad + ", unidades=" + unidades + ", tipoSoporte=" + tipoSoporte + ", ficheros=" + ficheros + ", personas=" + personas + ", modoDestruccion=" + modoDestruccion + '}';
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
//            js.put("DT_RowClass", (showObsInDocs ? "gradeA" : "gradeB"));
            js.put("0", descripcion);
//            js.put("1", (Observaciones != null ? Observaciones : ""));
//            js.put("2", (showObsInDocs ? "si" : "no"));
            js.put("1", ManejadorFechas.getFechaHora(fechaAlta, config.getTimeZone()));
            js.put("2", (fechaBaja != null ? ManejadorFechas
                    .getFechaHora(fechaBaja, config.getTimeZone()) : ""));
            js.put("3", (etiqueta != null ? etiqueta : ""));
            js.put("4", capacidad + unidades.getSimbolo());
            js.put("5", tipoSoporte.getNombre());
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
            js.put("6", "<a id=\"" + this.id + "\" class=\"table-action-EditData ui-icon ui-icon-pencil\">Edit</a>");
            js.put("7", "<a class=\"ui-icon ui-icon-plus\" href=\"./details.jsp?id="
                    + this.id + "\">Details</a>");
            return js;
        } catch (JSONException e) {
            logger.error("Imposible generar el json para tabla editable de [{}] Exception: [{}]",
                    this.toString(), e.getMessage());
            return null;
        }
    }
}
