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

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 * Tipo de forma de pago admitida.
 * @author Eduardo L. García Glez.
 * @version 0.0.0 13 de mar de 2011
 */
@Entity
@Table (name="tipos_formas_pago")
@NamedQueries({
    @NamedQuery(name = "TipoFormaPago.findAll", query = "SELECT t FROM TipoFormaPago t"),
    @NamedQuery(name = "TipoFormaPago.findActives", query = "SELECT t FROM TipoFormaPago t WHERE t.activa = true"),
    @NamedQuery(name = "TipoFormaPago.findById", query = "SELECT t FROM TipoFormaPago t WHERE t.id = :id")})
public class TipoFormaPago implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Properties">
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Short id;
    @Column(name = "nombre", nullable = false, length = 25)
    private String nombre;
    @Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;
    @Column(name = "activa", nullable = false)
    private Boolean activa;
    
    @OneToMany (cascade = CascadeType.ALL, mappedBy = "tipoFormaPago")
    private List<Factura> facturas;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructores">
    /**
     * Constructor por defecto.
     */
    public TipoFormaPago() {
        
    }

    /**
     * Inicializa al objeto a través de su identificador único.
     * @param id Identificador único del objeto.
     */
    public TipoFormaPago(Short id) {
        this.id = id;
    }

    /**
     * Inicializa el objeto a través de todos sus parámetros.
     * @param id Idenificador único del objeto.
     * @param nombre Nombre de la forma de pago, utilizar el código bundle.
     * @param descripcion Descripción completa de la forma de pago.
     * @param activa Indica si la forma de pago está activa.
     */
    public TipoFormaPago(Short id, String nombre, String descripcion, Boolean activa) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.activa = activa;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Section GetSet">
    /**
     * Obtiene el Id de la entidad.
     * @return Identificador único de la entidad.
     */
    public Short getId() {
        return id;
    }

    /**
     * Establece el identificador único de la entidad.
     *
     * El idntificador de este entidad es autogenerado por lo que es modificado
     * durante la persistencia.
     *
     * @param id Identificador único de la entidad.
     */
    public void setId(Short id) {
        this.id = id;
    }

    /**
     * Obtiene la descripción de la entidad.
     *
     * @return descripción asignada a la entidad.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la entidad.
     * @param descripcion Descripción a asignar a la entidad.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Establece un nombre de la entidad.
     *
     * El nombre debe identificar en muy pocas palabras la forma de pago.
     * Ejemplo de uso:
     * <fmt:message bundle="${StandardBundle}" key="${nombrevar.bundle}" />
     * @return Nombre asignado a la forma de pago.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la entidad.
     *
     * El nombre debe identificar en muy pocas palabras la forma de pago.
     *
     * @param nombre Nombre a asignar a la forma de pago.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene si la forma de pago está activa.
     * @return <code>true</code> si la forma de pago está activa,
     * <code>false</code> en caso contrario.
     */
    public Boolean getActiva() {
        return activa;
    }

    /**
     * Establece si la forma de pago está activa.
     * @param activa <code>true</code> si la forma de pago está activa,
     * <code>false</code> en caso contrario.
     */
    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }    
    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="Default Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoFormaPago)) {
            return false;
        }
        TipoFormaPago other = (TipoFormaPago) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.facturacion.FormaPago[id=" + id + "]";
    }
    // </editor-fold>

}
