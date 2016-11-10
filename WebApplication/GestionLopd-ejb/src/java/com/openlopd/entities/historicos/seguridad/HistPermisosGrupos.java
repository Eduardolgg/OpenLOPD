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

import com.openlopd.entities.seguridad.base.BasePermisosGrupos;
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
 * Esta entidad es la encargada de almacenar un histórico sobre los cambios de 
 * los permisos en los grupos.
 *
 * @author Eduardo L. García Glez.
 * Fecha: 23 de ene de 2011
 * @version 1.0.0
 */
@Entity
@Table (name = "hist_permisos_grupos")
@NamedQueries({
    @NamedQuery(name = "HistPermisosGrupos.findAll", query = "SELECT h FROM HistPermisosGrupos h"),
    @NamedQuery(name = "HistPermisosGrupos.findId", query = "SELECT h FROM HistPermisosGrupos h WHERE h.id = :id"),
    @NamedQuery(name = "HistPermisosGrupos.findFechaInicio", query = "SELECT h FROM HistPermisosGrupos h WHERE h.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "HistPermisosGrupos.findFechaFin", query = "SELECT h FROM HistPermisosGrupos h WHERE h.fechaFin = :fechaFin"),
    @NamedQuery(name = "HistPermisosGrupos.findFechaBetween", query = "SELECT h FROM HistPermisosGrupos h WHERE h.id = :id AND"
    + "(:fecha BETWEEN h.fechaInicio AND h.fechaFin)")})
public class HistPermisosGrupos extends BasePermisosGrupos implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Section Properties">
    private static final long serialVersionUID = 1L;
    @Id
    private String id;  //id del grupo.
    @Column(nullable = false)
    private boolean todasEmpresas;
    @Column(nullable = false)
    private Timestamp fechaInicio;
    @Column(nullable = false)
    private Timestamp fechaFin;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructores">
    /**
     * Constructor por defecto.
     */
    public HistPermisosGrupos() {
        super();
    }

    /**
     * Inicializa la entidad con todas las propiedades excepto los permisos.
     * @param id Identificador único del grupo.
     * @param todasEmpresas Indica si el grupo podía acceder a todas las empresas.
     * @param fechaInicio Fecha en la que se crearon los permisos del grupo.
     */
    public HistPermisosGrupos(String id, boolean todasEmpresas, Timestamp fechaInicio) {
        super();
        this.id = id;
        this.todasEmpresas = todasEmpresas;
        this.fechaInicio = fechaInicio;
    }

    /**
     * Inicializa todas las propiedades de la entidad.
     * @param permisos Permisos agisnados al grupo antes del cambio.
     * @param id Identificador único del grupo.
     * @param todasEmpresas Indica si el grupo podía acceder a todas las empresas.
     * @param fechaInicio Fecha en la que se crearon los permisos del grupo.
     * @throws UnknownColumnException emite esta excepción en caso de tener problemas
     * en el acceso a una columna.
     */
    public HistPermisosGrupos(BasePermisosGrupos permisos, String id, boolean todasEmpresas, Timestamp fechaInicio, Timestamp fechaFin) throws UnknownColumnException {
        super(permisos);
        this.id = id;
        this.todasEmpresas = todasEmpresas;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Secction GetSet">
    /**
     * Obtiene el identificador único del grupo del grupo.
     * @return Id del grupo.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador único del grupo.
     * @param id Identificador del grupo.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene la Fecha en los que cambian los permisos del grupo.
     *
     * Esta fecha se añade de forma automática en la persistencia del objeto.
     * @return Fecha en los que cambian los permisos del grupo.
     */
    public Timestamp getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece la Fecha en los que cambian los permisos del grupo.
     *
     * Esta fecha se añade de forma automática en la persistencia del objeto.
     * @param fechaFin Fecha en los que cambian los permisos del grupo.
     */
    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Obtiene la Fecha en la que se asignaron los permisos al grupo.
     *
     * @return Fecha en la que se asignaron los permisos.
     */
    public Timestamp getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece la Fecha en la que se asignaron los permisos al grupo.
     *
     * Es autogenerada al persistir el objeto.
     * @param fechaInicio Fecha en la que se asignaron los permisos.
     */
    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Indica si el grupo tiene acceso a todas las empresas gestionadas.
     * @return <code>true</code> en caso de tener acceso a todas las empresas
     * gestionadas, <code>false</code> en caso contrario.
     */
    public boolean isTodasEmpresas() {
        return todasEmpresas;
    }

    /**
     * Establece si el grupo tiene acceso a todas las empresas gestionadas.
     * @param todasEmpresas <code>true</code> en caso de tener acceso a todas
     * las empresas gestionadas, <code>false</code> en caso contrario.
     */
    public void setTodasEmpresas(boolean todasEmpresas) {
        this.todasEmpresas = todasEmpresas;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Section Persistencia">
    @PrePersist
    protected void prePersist () {
        java.util.Date ahora = new java.util.Date();
        fechaFin = new Timestamp(ahora.getTime());
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Default Method">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistPermisosGrupos)) {
            return false;
        }
        HistPermisosGrupos other = (HistPermisosGrupos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.historicos.seguridad.HistPermisosGrupos[id=" + id + "]";
    }
    // </editor-fold>

}
