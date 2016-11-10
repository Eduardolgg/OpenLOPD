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
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 * Gestiona los permisos de los contratos de cada una de las empresas.
 * @author Eduardo L. García Glez.
 * Fecha 09 de ene de 2011
 * @version 1.0.4
 * Modificaciones:
 *    24 de enero de 2011 se añaden comentarios y querys.
 *    26 de enero de 2011 añadida herencia de permisos y nuevos constructores.
 *    31 de enero de 2011 Se modifican los constructores.
 *    31 de enero de 2011 Se añade fecha de inicio.
 */
@Entity
@Table(name = "contratos_permisos")
@NamedQueries({
    @NamedQuery(name = "ContratosPermisos.findAll", query = "SELECT c FROM ContratosPermisos c"),
    @NamedQuery(name = "ContratosPermisos.findByIdContrato", query = "SELECT c FROM ContratosPermisos c WHERE c.idContrato = :idContrato"),
    @NamedQuery(name = "ContratosPermisos.findContratoGestor",
    query = "SELECT c "
            + "FROM ContratosPermisos c, GestoresEmpresas g "
            + "WHERE c.idContrato = g.idContrato AND "
            + "g.gestoresEmpresasPK.cifGestor = :cif AND "
            + "g.gestoresEmpresasPK.idEmpresa = :idEmpresaGestionada "),
    @NamedQuery(name = "ContratosPermisos.findContratoEmpresa",
    query = "SELECT c "
            + "FROM ContratosPermisos c, GestoresEmpresas g "
            + "WHERE c.idContrato = g.idContrato AND "
            + "g.gestoresEmpresasPK.idEmpresa = :idEmpresaGestionada ")
})
public class ContratosPermisos extends BaseContratosPermisos implements Serializable {
    // <editor-fold defaultstate="expanded" desc="Section Properties">
    private static final long serialVersionUID = 1L;
    @Id
    @Column (length = 37)
    private String idContrato;
    @Column (nullable = false)
    private Timestamp fechaInicio;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructores">
    /**
     * Constructor por defecto.
     */
    public ContratosPermisos() {
        super();
    }

    /**
     * Crea una instancia del objeto asociando el id del Contrato.
     * @param idContrato Id del contrato de la entidad.
     */
    public ContratosPermisos(String idContrato) {
        super();
        this.idContrato = idContrato;
    }

    /**
     * Inicializa el objeto añadiendo los permisos e idContrato correspondiente.
     * @param permisos Almacena los permisos a asignar el contrato.
     * @param idContrato Identificador único del contrato.
     * @throws UnknownColumnException emite esta excepción en caso de tener problemas
     * en el acceso a una columna.
     */
    public ContratosPermisos(BaseContratosPermisos permisos, String idContrato) throws UnknownColumnException  {
        super(permisos);
        this.idContrato = idContrato;
    }

    /**
     * Inicializa el objeto a través de los permisos
     * @param permisos Permisos a asignar al contrato.
     * @throws UnknownColumnException Se emite esta excepción si se encuentran
     * problemas realacionados con las columnas que almacenan los permisos.
     */
    public ContratosPermisos(BaseContratosPermisos permisos) throws UnknownColumnException {
        super(permisos);
    }

    // </editor-fold>

    // <editor-fold defaultstate="expanded" desc="Secction GetSet">
    /**
     * Obtiene el identificador único del contrato.
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
        hash += (idContrato != null ? idContrato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContratosPermisos)) {
            return false;
        }
        ContratosPermisos other = (ContratosPermisos) object;
        if ((this.idContrato == null && other.idContrato != null) || (this.idContrato != null && !this.idContrato.equals(other.idContrato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.seguridad.ContratosTipo[id=" + idContrato + "]";
    }
    // </editor-fold>
}
