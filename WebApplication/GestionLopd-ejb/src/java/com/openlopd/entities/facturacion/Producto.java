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

package com.openlopd.entities.facturacion;

import com.jkingii.datatables.JsonEntity;
import com.jkingii.datatables.ResponseConfig;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.json.JSONObject;

/**
 * Descripción del producto en una factura.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 30 de oct de 2012
 */
@Entity
@Table(name = "facturas_productos", schema = "public")
public class Producto implements Serializable, JsonEntity {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "factura_id")
    private Factura factura;
    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;
    @Column(name = "unidades", nullable = false)
    private Long unidades;
    @Column(name = "precio_unidad", precision = 6, scale = 2, nullable = false)
    private BigDecimal precioUnidad;
    @Column(name = "importe", precision = 6, scale = 2, nullable = false)
    private BigDecimal importe;

    public String getId() {
        return id;
    }

    public Producto(String id, Factura factura, String descripcion, Long unidades, BigDecimal precioUnidad, BigDecimal importe) {
        this.id = id;
        this.factura = factura;
        this.descripcion = descripcion;
        this.unidades = unidades;
        this.precioUnidad = precioUnidad;
        this.importe = importe;
    }

    public Producto() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getUnidades() {
        return unidades;
    }

    public void setUnidades(Long unidades) {
        this.unidades = unidades;
    }

    public BigDecimal getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(BigDecimal precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public BigDecimal getImporte() {
        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
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
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.facturacion.Producto[ id=" + id + " ]";
    }

    @Override
    public JSONObject toJson(ResponseConfig config) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JSONObject toTableJson(ResponseConfig config) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JSONObject toTableEditableJson(ResponseConfig config) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
