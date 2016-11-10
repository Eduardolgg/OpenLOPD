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

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Tipos de usuario que pueden coexistir en el sistema.
 * @author Eduardo L. García Glez.
 * Fecha: 12 de ene de 2011
 * @version 1.0.2 19 de mar de 2011
 * Modificaciones
 *    19 de mar de 2011, añadido el constructora TiposUsuario(Integer id)
 */
@Entity
@Table(name = "tipos_usuario")
@NamedQueries({
    @NamedQuery(name = "TiposUsuario.findAll", query = "SELECT t FROM TiposUsuario t"),
    @NamedQuery(name = "TiposUsuario.findById", query = "SELECT t FROM TiposUsuario t WHERE t.id = :id"),
    @NamedQuery(name = "TiposUsuario.findByNombre", query = "SELECT t FROM TiposUsuario t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TiposUsuario.findByDescripcion", query = "SELECT t FROM TiposUsuario t WHERE t.descripcion = :descripcion")})
public class TiposUsuario implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Section Properties">
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nombre", nullable = false, length = 10)
    private String nombre;
    @Basic(optional = true)
    @Column(name = "descripcion", nullable = false, length = 50)
    private String descripcion;
    @OneToMany(mappedBy = "tipo")
    private List<Shadow> shadowList;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Secction Constructores">
    /**
     * Constructor predeterminado.
     */
    public TiposUsuario() { }

    /**
     * Inicializa el objeto a través del id de la entidad.
     * @param id Identificador único de la entidad.
     */
    public TiposUsuario(Integer id) {
        this.id = id;
    }

    /**
     * Inicializa el nombre de la entidad.
     * @param nombre Nombre a asociar al tipo de usuario.
     */
    public TiposUsuario(String nombre){
        this.nombre = nombre;
    }

    /**
     * Inicializa la entidad para añadir un nuevo tipo de usuario y su descripcion
     * @param nombre Nombre/identificador del usuario.
     * @param descripcion Descripción sobre el nombre añadido.
     */
    public TiposUsuario(String nombre, String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    /**
     * Inicializa todos los parámetros de la entidad.
     * @param id Cláve única asignada a la entidad.
     * @param descripcion Descripción a asociar a la entidad.
     */
    public TiposUsuario(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }
    // </editor-fold>

    // <editor-fold defaultstate="expanded" desc="Secction GetSet">
    /**
     * Obtiene la clave primaria para la tabla, no tiene ninguna carácterística
     * especial se autoincrementa con cada nueva insersión.
     * @return Identificador único para la fila correspondiente.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece la clave primaria para la tabla.
     * Ojo, la clave primaria es autogenerada por lo que una asignación
     * será eliminada al persistir el objeto.
     * @param id Identificador único para la fila correspondiente.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del tipo de usuario.
     * @return nombre del tipo de usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del tipo de usuario.
     * @param nombre nombre del tipo de usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción que permite identificar al tipo de usuario
     * ejemplos: Admin, user, cif, nif...
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción que permite identificar al tipo de usuario
     * ejemplos: Admin, user, cif, nif...
     * @param descripcion Descripción a asignar al objeto.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el listado de usuarios del mismo tipo
     * TODO: Ver si no carga demasiado el sistema al realizar una consulta a través de Shadow
     * @return Listado de usuarios del tipo seleccionado.
     */
    public List<Shadow> getShadowList() {
        return shadowList;
    }

    /**
     * Establece un listado de usuarios relacionados por el tipo.
     * @param shadowList Listado de usuarios a relacionar.
     */
    public void setShadowList(List<Shadow> shadowList) {
        this.shadowList = shadowList;
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
        if (!(object instanceof TiposUsuario)) {
            return false;
        }
        TiposUsuario other = (TiposUsuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.seguridad.TiposUsuario[id=" + id + "]";
    }
    // </editor-fold>
}
