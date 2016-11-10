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

package com.openlopd.entities.historicos.seguridad;

import com.openlopd.entities.seguridad.base.BaseContratosPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 * Gestiona el historico de los permisos de los tipos de contratos de cada una de las empresas.
 * @author Eduardo L. García Glez.
 * Fecha 31 de ene de 2011
 * @version 1.0.0
 */
@Entity
@Table (name = "hist_contratos_tipo")
@NamedQueries({
    @NamedQuery(name = "HistContratosTipo.findAll", query = "SELECT h FROM HistContratosTipo h"),
    @NamedQuery(name = "HistContratosTipo.findById", query = "SELECT h FROM HistContratosTipo h WHERE h.id = :id"),
    @NamedQuery(name = "HistContratosTipo.findByNombre", query = "SELECT h FROM HistContratosTipo h WHERE h.nombre = :nombre"),
    @NamedQuery(name = "HistContratosTipo.findFechaInicio", query = "SELECT h FROM HistContratosTipo h WHERE h.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "HistContratosTipo.findFechaFin", query = "SELECT h FROM HistContratosTipo h WHERE h.fechaFin = :fechaFin"),
    @NamedQuery(name = "HistContratosTipo.findFechaBetween", query = "SELECT h FROM HistContratosTipo h WHERE h.id = :id AND"
    + "(:fecha BETWEEN h.fechaInicio AND h.fechaFin)"),
    @NamedQuery(name = "HistContratosTipo.findByDescripcion", query = "SELECT h FROM HistContratosTipo h WHERE h.descripcion = :descripcion")})
public class HistContratosTipo extends BaseContratosPermisos implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Section Properties">
    private static final long serialVersionUID = 1L;
    @Id
    private Short id;
    @Column(length = 25, nullable = false)
    private String nombre;
    @Column(length = 100, nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private Timestamp fechaInicio;
    @Column(nullable = false)
    private Timestamp fechaFin;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructores">
    /**
     * Constructor por defecto.
     */
    public HistContratosTipo() {
        super();
    }

    /**
     * Permite inicializar todas las propiedades, excepto permisos.
     * @param id identificador único asignado al tipo de contrato del gestor.
     * @param nombre Nombre identificativo del contrato.
     * @param descripcion Descripción detallada del contrato.
     * @param fechaInicio Fecha en la que se dió de alta en el sistema.
     */
    public HistContratosTipo(Short id, String nombre, String descripcion, Timestamp fechaInicio) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
    }

    /**
     * Permite inicializar todo el objeto.
     * @param permisos permisos asociados al contrato.
     * @param id identificador único asignado al tipo de contrato del gestor.
     * @param nombre Nombre identificativo del contrato.
     * @param descripcion Descripción detallada del contrato.
     * @param fechaInicio Fecha en la que se dió de alta en el sistema.
     * @throws UnknownColumnException Se produce esta excepción si existen problemas
     * con las columnas de permisos.
     */
    public HistContratosTipo(BaseContratosPermisos permisos, Short id, String nombre, String descripcion, Timestamp fechaInicio) throws UnknownColumnException {
        super(permisos);
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Section GetSet">
    /**
     * Obtiene el identificador único del contrato.
     * @return Identificador único del contrato.
     */
    public Short getId() {
        return id;
    }

    /**
     * Establece el identificador único del contrato.
     * @param Identificador único del contrato.
     */
    public void setId(Short id) {
        this.id = id;
    }

    /**
     * Obtiene una descripción detalla sobre el contrato.
     * @return Descripción asociada al cotrato.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece una descripción detalla sobre el contrato.
     * @param descripcion Descripción asociada al cotrato.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la Fecha en la que se creó el contrato en el sistema.
     * @return Fecha en la que se introdujo el contrato en el sistema.
     */
    public Timestamp getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece la Fecha en la que se creó el contrato en el sistema.
     * @return Fecha en la que se introdujo el contrato en el sistema.
     */
    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Establece la fecha en la que finaliza el contrato
     *
     * Esta fecha es añadida de forma automática por el sistema al persistir
     * el objeto.
     * @return Fecha fin del contrato.
     */
    public Timestamp getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece la fecha en la que finaliza el contrato
     *
     * Esta fecha es añadida de forma automática por el sistema al persistir
     * el objeto
     * @param fechaFin Fecha fin del contrato.
     */
    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Obtiene el nombre identificativo del contrato.
     * @return Nombre identificativo del contrato.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre identificativo del contrato.
     * @param nombre Nombre identificativo del contrato.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Section Persistencia">
    @PrePersist
    protected void prePersist () {
        java.util.Date ahora = new java.util.Date();
        fechaFin = new Timestamp(ahora.getTime());
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
        if (!(object instanceof HistContratosTipo)) {
            return false;
        }
        HistContratosTipo other = (HistContratosTipo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.historicos.seguridad.HistContratosTipo[id=" + id + "]";
    }
    // </editor-fold>

}
