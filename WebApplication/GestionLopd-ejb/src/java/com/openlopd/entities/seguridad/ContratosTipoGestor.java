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
import javax.persistence.*;

/**
 * Gestiona los tipos de contratos personalizados para cada gestor.
 * Nota: Los contratos específicos para cada gestor se pondrán en una tabla a
 * parte.
 * @author Eduardo L. García Glez.
 * Fecha 09 de ene de 2011
 * @version 1.0.3
 * Modificaciones:
 *    31 de enero de 2011 Se añaden NamedQuerys y se extiene a la clase de permisos
 *    también se añade la documentación y constructores.
 *    31 de enero de 2011 Se añade fecha de inicio.
 *    14 de mar de 2011 Añadida la propiedad Importe.
 */
@Entity
@Table(name = "contratos_tipo_gestor")
@NamedQueries({
    @NamedQuery(name = "ContratosTipoGestor.findAll", query = "SELECT c FROM ContratosTipoGestor c"),
    @NamedQuery(name = "ContratosTipoGestor.findById", query = "SELECT c FROM ContratosTipoGestor c WHERE c.id = :id"),
    @NamedQuery(name = "ContratosTipoGestor.findByCif", query = "SELECT c FROM ContratosTipoGestor c WHERE c.cif = :cif"),
    @NamedQuery(name = "ContratosTipoGestor.findByNombre", query = "SELECT c FROM ContratosTipoGestor c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ContratosTipoGestor.findByDescripcion", query = "SELECT c FROM ContratosTipoGestor c WHERE c.descripcion = :descripcion")})
public class ContratosTipoGestor extends BaseContratosPermisos implements Serializable {
    // <editor-fold defaultstate="expanded" desc="Section Properties">
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Short id;
    @Column (length = 9, nullable = false)
    private String cif;
    @Column (length = 25, nullable = false)
    private String nombre;
    @Column (length = 100, nullable = true)
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
    public ContratosTipoGestor() {
        super();
        this.importe = new BigDecimal(0);
    }

    /**
     * Permite inicializar la entidad a través del cif de la empresa
     * a la que pertenece el tipo de contrato.
     * @param cif CIF/NIF de la empresa/autónomo al que pertenece el tipo de
     * contrato.
     */
    public ContratosTipoGestor(String cif) {
        super();
        this.cif = cif;
        this.importe = new BigDecimal(0);
    }

    /**
     * Inicializa la entidad a través de los parámetros de identificación.
     *
     * Se inicializa la entidad a través del CIF un nombre identificativo y una
     * descripción detallada  sobre a quién o para qué se utiliza el contrato.
     * @param cif CIF/NIF de la empresa/autónomo al que pertenece el tipo de
     * contrato.
     * @param nombre Nombre que permite dentificar el contrato.
     * @param descripcion Descripción que permite conocer a qué está destinado el
     * contrato.
     */
    public ContratosTipoGestor(String cif, String nombre, String descripcion) {
        super();
        this.cif = cif;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.importe = new BigDecimal(0);
    }

    /**
     * Inicializa la entidad por completo.
     * @param permisos Permisos a asignar al contrato.
     * @param cif CIF/NIF de la empresa/autónomo al que pertenece el tipo de
     * contrato.
     * @param nombre Nombre que permite dentificar el contrato.
     * @param descripcion Descripción que permite conocer a qué está destinado el
     * @param importe Importe del contrato.
     * contrato.
     * @throws UnknownColumnException Se emite esta excepción si se encuentran
     * problemas realacionados con las columnas que almacenan los permisos.
     */
    public ContratosTipoGestor(BaseContratosPermisos permisos, String cif, 
            String nombre, String descripcion, BigDecimal importe) throws UnknownColumnException {
        super(permisos);
        this.cif = cif;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.importe = importe;
    }

    /**
     * Inicializa el objeto a través de los permisos
     * @param permisos Permisos a asignar al contrato.
     * @throws UnknownColumnException Se emite esta excepción si se encuentran
     * problemas realacionados con las columnas que almacenan los permisos.
     */
    public ContratosTipoGestor(BaseContratosPermisos permisos) throws UnknownColumnException {
        super(permisos);
        this.importe = new BigDecimal(0);
    }

    // </editor-fold>

    // <editor-fold defaultstate="expanded" desc="Secction GetSet">
    /**
     * Obtiene el id asignado a la entidad.
     *
     * Es importante saber que el id es autogenerado.
     * @return Identificador único de la entidad.
     */
    public Short getId() {
        return id;
    }

    /**
     * Establece el identificador único de la entidad.
     *
     * Es importante saber que el id es autogenerado.
     * @param id Identificador único de la entidad.
     */
    public void setId(Short id) {
        this.id = id;
    }

    /**
     * Obtiene el identificación del gestor al que pertenece el tipo de contrato.
     * 
     * En este método se obtiene el identificador único del gestor que suele ser
     * el CIF en caso de ser una empresa y el NIF cuando se trata de un autónomo.
     * @return Identificador único del gestor.
     */
    public String getCif() {
        return cif;
    }

    /**
     * Establece el identificación del gestor al que pertenece el tipo de contrato.
     *
     * En este método se establece el identificador único del gestor que suele ser
     * el CIF en caso de ser una empresa y el NIF cuando se trata de un autónomo.
     * @param cif Identificador único del gestor.
     */
    public void setCif(String cif) {
        this.cif = cif;
    }

    /**
     * Obtiene el nombre que permite identificar el contrato.
     * @return Nombre que permite identificar el contrato.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre que permite identificar el contrato.
     * @param nombre Nombre que permite identificar el contrato.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece la descripción detallada del contrato.
     * @return Descripción detallada del contrato.
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * Establece la descripción detallada del contrato.
     * @param descripcion Descripción detallada del contrato.
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
        if (!(object instanceof ContratosTipoGestor)) {
            return false;
        }
        ContratosTipoGestor other = (ContratosTipoGestor) object;
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
