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
 * Entidad que se encarga de gestionar los permisos asociados a los grupos.
 * a la aplicación.
 * @author Eduardo L. García Glez.
 * Fecha: 23 de ene de 2011
 * @version 1.0.3, 20 de mar de 2011
 * Modificaciones:
 *    31 de enero de 2011 Se añade propiedad fecha de inicio.
 *    05 de feb de 2011 modificadas querys para adaptarlas a las necesidades del login.
 *    20 de mar de 2011 Eliminada la propiedad rank y se añade constructor para todas las
 *    propiedades.
 */
@Entity
@Table(name = "permisos_grupos")
@NamedQueries({
    @NamedQuery(name = "PermisosGrupos.findAll", query = "SELECT p FROM PermisosGrupos p"),
    @NamedQuery(name = "PermisosGrupos.findById", query = ""
        + "SELECT p FROM PermisosGrupos p, EmpresasGrupos eg "
        + "WHERE p.id = eg.empresasGruposPK.idGrupo "
        + "  AND p.id = :id AND eg.empresasGruposPK.idEmpresa = :idEmpresa"),
    @NamedQuery(name = "PermisosGrupos.findByIdUsuario",
    query =
            "SELECT p "
            + "FROM PermisosGrupos p, GruposUsuarios g "
            + "WHERE p.id = g.gruposUsuariosPK.idGrupo AND "
            + "g.gruposUsuariosPK.idUsuario = :idUsuario")})
public class PermisosGrupos extends BasePermisosGrupos implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Secction Properties">
    private static final long serialVersionUID = 1L;
    @Id
    @Column (nullable = false, length = 37)
    private String id; // Id del grupo.
    @Column (nullable = false)
    private boolean todasEmpresas;
    @Column (nullable = false)
    private Timestamp fechaInicio;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructores">
    /**
     * Constructor por defecto.
     */
    public PermisosGrupos() {
        this.todasEmpresas = false;
    }

    /**
     * Inicializa el objeto con el identificador único del grupo.
     * @param id Identificador único del grupo.
     */
    public PermisosGrupos(String id) {
        this.id = id;
        this.todasEmpresas = false;
    }

    /**
     * Permite inicializar todas las propiedades del objeto.
     * @param permisos Permisos a asignar al grupo.
     * @param id Identficador del grupo.
     * @param todasEmpresas <code>true</code> indica que puede acceder a todas
     * las empresas gestionadas.
     * @throws UnknownColumnException Se lanza esta excepció si alguna de las
     * columnas no es reconocida.
     */
    public PermisosGrupos(BasePermisosGrupos permisos, String id, boolean todasEmpresas) throws UnknownColumnException {
        super(permisos);
        this.id = id;
        this.todasEmpresas = todasEmpresas;
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

    /**
     * Obtiene la Fecha en la que se asignaron los permisos al grupo.
     *
     * Es autogenerada al persistir el objeto.
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
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Métodos Extra">
    /**
     * Cópia de permisos.
     *
     * Permite hacer la copia en el objeto de los permisos de otro objeto
     * de las mismas carácterísticas.
     * @param permisos Permisos a los que realizar la copia.
     * @exception java.lang.ClassCastException Se produce esta excepción si el
     * objeto enviado no es de la clase esperada.
     * @exception java.lang.NullPointerException Se produce esta excepción si se
     * intentan importar permisos de un objeto nulo.
     * @exception UnknownColumnException Se produce cuando alguna de las columnas
     * de permisos no puede ser importada.
     */
    public final void importaPermisos(PermisosGrupos permisos) throws UnknownColumnException {
        if (permisos.todasEmpresas) {
            this.todasEmpresas = permisos.todasEmpresas;
        }
        super.importaPermisos(permisos);
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
        if (!(object instanceof PermisosGrupos)) {
            return false;
        }
        PermisosGrupos other = (PermisosGrupos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.seguridad.PremisosGrupos[id=" + id + "]";
    }
    // </editor-fold>
}
