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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 * Gestiona el historico de los permisos de los contratos de cada una de las empresas.
 * @author Eduardo L. García Glez.
 * Fecha 09 de ene de 2011
 * @version 1.0.3
 * Modificaciones:
 *    26 de enero de 2011 Se Hereda de la clase encargada de las columnas de permisos.
 *    26 de enero de 2011 Se añaden NamedQuerys.
 *    31 de enero de 2011 Se modifican los constructores y se añaden NamedQuerys.
 */
@Entity
@Table (name = "hist_contratos_permisos")
@NamedQueries({
    @NamedQuery(name = "HistContratosPermisos.findAll", query = "SELECT h FROM HistContratosPermisos h"),
    @NamedQuery(name = "HistContratosPermisos.findIdContrato", query = "SELECT h FROM HistContratosPermisos h WHERE h.idContrato = :idContrato"),
    @NamedQuery(name = "HistContratosPermisos.findFechaInicio", query = "SELECT h FROM HistContratosPermisos h WHERE h.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "HistContratosPermisos.findFechaFin", query = "SELECT h FROM HistContratosPermisos h WHERE h.fechaFin = :fechaFin"),
    @NamedQuery(name = "HistContratosPermisos.findFechaBetween", query = "SELECT h FROM HistContratosPermisos h WHERE h.id = :id AND"
    + "(:fecha BETWEEN h.fechaInicio AND h.fechaFin)"),  
    @NamedQuery(name = "HistContratosPermisos.findById", query = "SELECT h FROM HistContratosPermisos h WHERE h.id = :id")})
public class HistContratosPermisos extends BaseContratosPermisos implements Serializable  {
    // <editor-fold defaultstate="collapsed" desc="Section Properties">
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column (length = 25, nullable = false)
    private String idContrato;
    private Timestamp fechaInicio;
    private Timestamp fechaFin;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructores">
    /**
     * Constructor por defecto.
     */
    public HistContratosPermisos() {
        super();
    }

    /**
     * Inicializa la entidad con el id del Contrato y la fecha de inicio.
     * @param idContrato Identificador único del contrato.
     * @param fechaInicio Fecha de inicio del contrato.
     */
    public HistContratosPermisos(String idContrato, Timestamp fechaInicio) {
        this.idContrato = idContrato;
        this.fechaInicio = fechaInicio;
    }

    /**
     * Inicializa la entidad con los permisos el id del Contrato y la fecha de inicio.
     * @param permisos Permisos a asignar a la entidad.
     * @param idContrato Identificador único del contrato.
     * @param fechaInicio Fecha de inicio del contrato.
     * @throws UnknownColumnException emite esta excepción en caso de tener problemas
     * en el acceso a una columna.
     */
    public HistContratosPermisos(BaseContratosPermisos permisos, String idContrato, Timestamp fechaInicio) throws UnknownColumnException {
        super(permisos);
        this.idContrato = idContrato;
        this.fechaInicio = fechaInicio;
    }

    /**
     * Inicializa la entidad a través del objeto que contiene los permisos antiguos.
     * @param contratosPermisos Objeto con los permisos antiguos a almacenar en el histórico.
     * @throws UnknownColumnException emite esta excepción en caso de tener problemas
     * en el acceso a una columna.
     */
    public HistContratosPermisos(BaseContratosPermisos permisos) throws UnknownColumnException  {
        super(permisos);
    }
    // </editor-fold>    

    // <editor-fold defaultstate="expanded" desc="Secction GetSet">
    /**
     * Obtiene el identificador único de la entidad.
     *
     * El identificador es autogenerado al persistir el objeto.
     * @return Identificador único de la entidad.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador único de la entidad.
     *
     * El identificador es autogenerado al persistir el objeto.
     * @param id Identificador único de la entidad.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene la fecha de inicio del contrato.
     *
     * Es la fecha en la que se inica el permiso de acceso.
     * @return Fecha de inicio del contrato.
     */
    public Timestamp getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece la fecha de inicio del contrato.
     *
     * Es la fecha en la que se inica el permiso de acceso.
     * @param fechaInicio Fecha de inicio del contrato.
     */
    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene el identificador del contrato.
     * @return Identificador único del contrato.
     */
    public String getIdContrato() {
        return idContrato;
    }

    /**
     * Establece el identificador único del contrato.
     * @param idContrato Identificador único del contrato.
     */
    public void setIdContrato(String idContrato) {
        this.idContrato = idContrato;
    }

    /**
     * Obtiene la Fecha de finalización de los permisos.
     *
     * Esta fecha es calculada por el sistema en el momento de la persistencia.
     * @return Fecha en la que se realiza el cambio de permisos.
     */
    public Timestamp getfechaFin() {
        return fechaFin;
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
        if (!(object instanceof HistContratosPermisos)) {
            return false;
        }
        HistContratosPermisos other = (HistContratosPermisos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.historicos.seguridad.HistContratosPermisos[id=" + id + "]";
    }
    // </editor-fold>
}
