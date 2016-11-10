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
@Table(name = "hist_contratos_tipo_gestor")
@NamedQueries({
    @NamedQuery(name = "HistContratosTipoGestor.findAll", query = "SELECT h FROM HistContratosTipoGestor h"),
    @NamedQuery(name = "HistContratosTipoGestor.findById", query = "SELECT h FROM HistContratosTipoGestor h WHERE h.id = :id"),
    @NamedQuery(name = "HistContratosTipoGestor.findByCif", query = "SELECT h FROM HistContratosTipoGestor h WHERE h.cif = :cif"),
    @NamedQuery(name = "HistContratosTipoGestor.findByNombre", query = "SELECT h FROM HistContratosTipoGestor h WHERE h.nombre = :nombre"),
    @NamedQuery(name = "HistContratosTipoGestor.findFechaInicio", query = "SELECT h FROM HistContratosTipoGestor h WHERE h.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "HistContratosTipoGestor.findFechaFin", query = "SELECT h FROM HistContratosTipoGestor h WHERE h.fechaFin = :fechaFin"),
    @NamedQuery(name = "HistContratosTipoGestor.findFechaBetween", query = "SELECT h FROM HistContratosTipoGestor h WHERE h.id = :id AND"
    + "(:fecha BETWEEN h.fechaInicio AND h.fechaFin)"),
    @NamedQuery(name = "HistContratosTipoGestor.findByDescripcion", query = "SELECT h FROM HistContratosTipoGestor h WHERE h.descripcion = :descripcion")})
public class HistContratosTipoGestor extends BaseContratosPermisos implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Secction Properties">
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @Column(length = 9, nullable = false)
    private String cif;
    @Column(length = 25, nullable = false)
    private String nombre;
    @Column(length = 100, nullable = true)
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
    public HistContratosTipoGestor() {
        super();
    }

    /**
     * Permite inicializar todas las propiedades, excepto permisos.
     * @param id identificador único asignado al tipo de contrato del gestor.
     * @param cif CIF/NIF del Gestor propietario del tipo de contrato.
     * @param nombre Nombre identificativo del contrato.
     * @param descripcion Descripción detallada del contrato.
     * @param fechaInicio Fecha en la que se dió de alta en el sistema.
     */
    public HistContratosTipoGestor(Long id, String cif, String nombre, String descripcion, Timestamp fechaInicio) {
        super();
        this.id = id;
        this.cif = cif;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
    }

    /**
     * Permite inicializar todo el objeto.
     * @param permisos permisos asociados al contrato.
     * @param id identificador único asignado al tipo de contrato del gestor.
     * @param cif CIF/NIF del Gestor propietario del tipo de contrato.
     * @param nombre Nombre identificativo del contrato.
     * @param descripcion Descripción detallada del contrato.
     * @param fechaInicio Fecha en la que se dió de alta en el sistema.
     * @throws UnknownColumnException Se produce esta excepción si existen problemas
     * con las columnas de permisos.
     */
    public HistContratosTipoGestor(BaseContratosPermisos permisos, Long id, String cif, String nombre, String descripcion, Timestamp fechaInicio) throws UnknownColumnException {
        super(permisos);
        this.id = id;
        this.cif = cif;
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
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único del contrato.
     * @param Identificador único del contrato.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el CIF/NIF de la empresa/autonomo propietaria del tipo de
     * contrato.
     * @return CIF/NIF de la empreas/autonomo.
     */
    public String getCif() {
        return cif;
    }

    /**
     * Establece el CIF/NIF de la empresa/autonomo propietaria del tipo de
     * contrato.
     * @param cif CIF/NIF de la empreas/autonomo.
     */
    public void setCif(String cif) {
        this.cif = cif;
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
        if (!(object instanceof HistContratosTipoGestor)) {
            return false;
        }
        HistContratosTipoGestor other = (HistContratosTipoGestor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.historicos.seguridad.HistContratosTipoGestor[id=" + id + "]";
    }
    // </editor-fold>
}
