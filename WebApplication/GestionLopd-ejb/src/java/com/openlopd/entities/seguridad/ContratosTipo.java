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

package com.openlopd.entities.seguridad;

import com.openlopd.entities.seguridad.base.BaseContratosPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 * Gestiona los tipos de contratos por defecto que se pueden realizar desde
 * la web o los gestores.
 * Nota: Los contratos específicos para cada gestor se pondrán en una tabla a
 * parte.
 * @author Eduardo L. García Glez.
 * Fecha 09 de ene de 2011
 * @version 1.0.3
 * Modificado:
 *    26 de enero de 2011 extiende BaseContratosPermisos y se añaden NamedQuerys.
 *    26 de enero de 2011 añadidas nuevas propiedades y comentarios.
 *    31 de enero de 2011 Se modifican los constructores.
 *    31 de enero de 2011 Se añade fecha de inicio.
 *    14 de mar de 2011 Añadida la propiedad Importe.
 */
@Entity
@Table(name = "contratos_tipo")
@NamedQueries({
    @NamedQuery(name = "ContratosTipo.findAll", query = "SELECT c FROM ContratosTipo c"),
    @NamedQuery(name = "ContratosTipo.findById", query = "SELECT c FROM ContratosTipo c WHERE c.id = :id"),
    @NamedQuery(name = "ContratosTipo.findByNombre", query = "SELECT c FROM ContratosTipo c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ContratosTipo.findByDescripcion", query = "SELECT c FROM ContratosTipo c WHERE c.descripcion = :descripcion")})
public class ContratosTipo extends BaseContratosPermisos implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Section Properties">
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Short id;
    @Column (length = 25, nullable = false)
    private String nombre;
    @Column (length = 100, nullable = false)
    private String descripcion;
    @Column (nullable = false)
    private Timestamp fechaInicio;
    @Column(name = "importe", nullable = false, precision = 5, scale = 2)
    private BigDecimal importe;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructores">
    /**
     * Constructor por defecto.
     */
    public ContratosTipo() {
        super();
        this.importe = new BigDecimal(0);
    }

    /**
     * Inicializa el objeto a través de su ID.
     *
     * Este método es ideal para la localización de un contrato específico
     * ya que se identifica de forma inequívoca por su id y no es necesario
     * añadir mayor información en el objeto.
     *
     * @param id Identificador único del tipo de contrato.
     */
    public ContratosTipo(short id) {
        super();
        this.id = id;
    }

    /**
     * Constructor inicializando el nombre del tipo del contrato.
     * @param nombre Nombre del tipo de contrato.
     */
    public ContratosTipo(String nombre) {
        super();
        this.nombre = nombre;
        this.importe = new BigDecimal(0);
    }

    /**
     * Inicializa el objeto con el nombre y la descripción.
     * @param nombre Nombre corto para identificar el contrato.
     * @param descripcion Descripción detallada del contrato.
     */
    public ContratosTipo(String nombre, String descripcion) {
        super();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.importe = new BigDecimal(0);
    }

    /**
     * Inicializa el contrato con todos los parámetros de localización.
     * @param id Identificador único del contrato.
     * @param nombre Nombre corto para identificar el contrato.
     * @param descripcion Descripción detallada del contrato.
     */
    public ContratosTipo(Short id, String nombre, String descripcion) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.importe = new BigDecimal(0);
    }

    /**
     * Inicializa el objeto a través de los permisos
     * @param permisos Permisos a asignar al contrato.
     * @throws UnknownColumnException Se emite esta excepción si se encuentran
     * problemas realacionados con las columnas que almacenan los permisos.
     */
    public ContratosTipo(BaseContratosPermisos permisos) throws UnknownColumnException {
        super(permisos);
    }

    /**
     * Inicializa todos los parámetros de la entidad.
     * @param permisos Permisos a asignar al contrato.
     * @param nombre Nombre corto para identificar el contrato
     * @param descripcion Descripción detallada del contrato.
     * @param importe Importe del contrato.
     * @throws UnknownColumnException Se emite esta excepción si se encuentran
     * problemas realacionados con las columnas que almacenan los permisos.
     */
    public ContratosTipo(BaseContratosPermisos permisos, String nombre, 
            String descripcion, BigDecimal importe) throws UnknownColumnException {
        super(permisos);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.importe = importe;
    }
    // </editor-fold>

    // <editor-fold defaultstate="expanded" desc="Secction GetSet">
    /**
     * Obtiene el identificador único del contrato.
     * @return Identificador único del contrato.
     */
    public Short getId() {
        return id;
    }

    /**
     * Establece el identificador único del contrato.
     *
     * Hay que tener en cuenta que el identificador es autogenerado en el momento
     * de la persistencia de la entidad.
     * @param id Identificador único del contrato.
     */
    public void setId(Short id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del contrato.
     *
     * Se trata de un nombre corto que permita identificar el contrato.
     * para más detalles ver la propiedad <code>descripcion.</code>
     * @return Nombre identificativo del contrato.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del contrato.
     *
     * Se trata de un nombre corto que permita identificar el contrato.
     * para más detalles ver la propiedad <code>descripcion.</code>
     * @param nombre Nombre identificativo del contrato.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene una descripción para el tipo de contrato.
     *
     * Una descripción detallada sobre el tipo de contrato que permita
     * conoser detalles como para que empresas está orientado.
     * @return Descripción del contrato.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece una descripción para el tipo de contrato.
     *
     * Una descripción detallada sobre el tipo de contrato que permita
     * conoser detalles como para que empresas está orientado.
     * @param descripcion Descripción a almacenar en el contrato.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la fecha en la que se añadió el contrato al sistema.
     * @return Fecha en la que se añadió el contrato al sistema.
     */
    public Timestamp getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece la fecha en la que se añadió el contrato al sistema.
     *
     * La fecha se añadirá de forma automática al persistir la entidad.
     * @param fechaInicio Fecha en la que se añadió el contrato al sistema.
     */
    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene el importe del contrato.
     *
     * @return Importe del contrato.
     */
    public BigDecimal getImporte() {
        return importe;
    }

    /**
     * Establece el importe del contrato.
     *
     * @param importe Importe del contrato.
     */
    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Section Persistencia">
    @PrePersist
    protected void prePersist () {
        java.util.Date ahora = new java.util.Date();
        fechaInicio = new Timestamp(ahora.getTime());
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
        if (!(object instanceof ContratosTipo)) {
            return false;
        }
        ContratosTipo other = (ContratosTipo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.seguridad.ContratosTipo[id=" + id + "]";
    }
    // </editor-fold>
}
