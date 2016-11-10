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

import com.openlopd.entities.empresas.Empresa;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Eduardo L. García Glez.
 */
@MappedSuperclass
@XmlRootElement
public class BaseListadosLOPD implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SerializedName("DT_RowId")
    private String id;
    @SerializedName("0")
    @ManyToOne(optional = false)
    private Empresa empresa;
    @SerializedName("1")
    @Column(name = "descripcion", nullable = true, length = 255)
    private String Descripcion;
    @SerializedName("2")
    @Column(name = "observaciones", nullable = true, length = 20000)
    private String Observaciones;
    @SerializedName("3")
    @Column(name = "show_obs_in_docs", nullable = true)
    private Boolean showObsInDocs;
    @SerializedName("4")
    @Column(name = "fecha_alta", nullable = false)
    private Long FechaAlta;
    @SerializedName("5")
    @Column(name = "fecha_baja", nullable = true)
    private Long FechaBaja;
    @Column(name = "fecha_alta_int", nullable = false)
    private Long FechaAltaInt;
    @Column(name = "fecha_baja_int", nullable = true)
    private Long FechaBajaInt;

    /**
     * Constructor por defecto.
     */
    public BaseListadosLOPD() {
    }

    /**
     * Obtiene la clave primaria de la entidad.
     * @return Clave primaria.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece la clave primaria de la entidad.
     * @param id Clave primaria. 
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene una escripción sobre la entidad.
     * 
     * Descripción del usuario sobre la entidad.
     * También se podría proveer de descripciones estándar.
     * 
     * @return Descripción sobre la entidad.
     */
    public String getDescripcion() {
        return Descripcion;
    }

    /**
     * Establece una descripción sobre la entidad.
     * 
     * Descripción del usuario sobre la entidad.
     * También se podría proveer de descripciones estándar.
     * 
     * @param Descripcion Descripción sobre la entidad.
     */
    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    /**
     * Obtiene la fecha de alta de la entidad en el sistema de información
     * de la empresa.
     * 
     * @return Fecha de alta en SI.
     */
    public Long getFechaAlta() {
        return FechaAlta;
    }

    /**
     * Establece la fecha de alta de la entidad en el sistema de información
     * de la empresa.
     * 
     * @param FechaAlta Fecha de alta en SI.
     */
    public void setFechaAlta(Long FechaAlta) {
        this.FechaAlta = FechaAlta;
    }

    /**
     * Obtiene la fecha de alta de la entidad en la base de datos.
     * 
     * @return Fecha de alta en DB.
     */
    public Long getFechaAltaInt() {
        return FechaAltaInt;
    }

    /**
     * Establece la fecha de alta de la entidad en la base de datos.
     * 
     * @param FechaAltaInt Fecha de alta en DB.
     */
    public void setFechaAltaInt(Long FechaAltaInt) {
        this.FechaAltaInt = FechaAltaInt;
    }

    /**
     * Establece la fecha de baja de la entidad en el sistema de 
     * información de la empresa.
     * 
     * @return Fecha de baja en el SI.
     */
    public Long getFechaBaja() {
        return FechaBaja;
    }

    /**
     * Establece la fecha de baja de la entidad en el sistema de 
     * información de la empresa.
     * 
     * @param FechaBaja 
     */
    public void setFechaBaja(Long FechaBaja) {
        this.FechaBaja = FechaBaja;
    }

    /**
     * Obtiene la fecha de baja de la entidad en la base de datos.
     * 
     * @return Fecha de baja en BD.
     */
    public Long getFechaBajaInt() {
        return FechaBajaInt;
    }

    /**
     * Obtiene la fecha de baja de la entidad en la base de datos.
     * 
     * @param FechaBajaInt Fecha de Baja en BD.
     */
    public void setFechaBajaInt(Long FechaBajaInt) {
        this.FechaBajaInt = FechaBajaInt;
    }

    /**
     * Obtiene las observaciones sobre la entidad realizadas por el usuario.
     * 
     * @return Texto de las observaciones.
     */
    public String getObservaciones() {
        return Observaciones;
    }

    /**
     * Establece las observaciones sobre la entidad realizadas por el usuario.
     * 
     * @param Observaciones Texto de las observaciones.
     */
    public void setObservaciones(String Observaciones) {
        this.Observaciones = Observaciones;
    }

    /**
     * Obtiene la empresa a la que pertenece la entidad.
     * @return información de la empresa a la que pertenece la entidad.
     */
    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * Establece la empresa a la que pertenece la entidad.
     * @param empresa información de la empresa a la que pertenece la entidad.
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    /**
     * Obtiene si las observaciones deben mostrarse en los documentos.
     * 
     * Este bit indica si las observaciones realizadas sobre la entidad
     * son aptas para ser incluidas en los documentos generados, este
     * bit se establece a petición del usuario.
     * 
     * @return <code>true</code> si las observaciones deben se incluidas,
     * <code>false</code> en caso contrario.
     */
    public Boolean getShowObsInDocs() {
        return showObsInDocs;
    }

    /**
     * Establece si las observaciones deben mostrarse en los documentos.
     * 
     * Este bit indica si las observaciones realizadas sobre la entidad
     * son aptas para ser incluidas en los documentos generados, este
     * bit se establece a petición del usuario.
     * 
     * @param showObsInDocs <code>true</code> si las observaciones deben se incluidas,
     * <code>false</code> en caso contrario.
     */
    public void setShowObsInDocs(Boolean showObsInDocs) {
        this.showObsInDocs = showObsInDocs;
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
        if (!(object instanceof BaseListadosLOPD)) {
            return false;
        }
        BaseListadosLOPD other = (BaseListadosLOPD) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.temp.BaseListadosLOPD[ id=" + id + " ]";
    }
}
