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

package com.openlopd.entities.interfaz;

import com.openlopd.entities.lopd.TipoNivelSeguridad;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Entidad encargada del acceso al listado de los links del sistema.
 * 
 * @author Eduardo L. García Glez.
 * @version 0.0.5 16 de oct de 2011
 * Modificaciones
 *  Clase original de jkingii, modificada para adaptarla a esta aplicación.
 */
@Entity
@Table (name = "link_list", schema = "application")
@NamedQueries({
    @NamedQuery(name = "LinkList.findAll", query = "SELECT l from LinkList l"),
    @NamedQuery(name = "LinkList.findById", 
                query = "SELECT l FROM LinkList l WHERE l.id = :id "
                + "AND l.locale = :locale"),
    @NamedQuery(name = "LinkList.findByActive", 
                query = "SELECT l FROM LinkList l "
                + "WHERE l.active = :active "
                + "  AND l.locale = :locale "
                + "ORDER BY l.orden ASC"),
    @NamedQuery(name = "LinkList.findByOrden",
                query = "SELECT l FROM LinkList l WHERE l.orden = :orden "
                + "AND l.active = :active AND l.locale = :locale "
                + "ORDER BY l.orden ASC"),
    @NamedQuery(name = "LinkList.findLevel", 
                query = "SELECT l FROM LinkList l "
                + "WHERE LENGTH(l.orden) = :level * 2 "
                + "AND l.orden like :orden AND l.active = :active "
                + "AND l.locale = :locale"),
    @NamedQuery(name = "LinkList.findOperations", query = ""
        + "SELECT l FROM LinkList l "
        + "WHERE l.active = :active "
        + "  AND l.nivel <= :nivel "
        + "  AND l.operacion IS NOT NULL "
        + "  AND l.codOperacion IS NOT NULL "
        + "ORDER BY l.prioridad DESC"),    
    @NamedQuery(name = "LinkList.findByLink", query = ""
            + "SELECT l FROM LinkList l "
            + "WHERE l.active = :active "
            + "  AND l.link = :link"),
    @NamedQuery(name = "LinkList.findTareasHabituales", query = ""
        + "SELECT l FROM LinkList l "
        + "WHERE l.active = TRUE"
        + "  AND l.tarea = TRUE"
        + "  AND l.nivel <= :nivel "
        + "ORDER BY l.prioridad DESC")
})
public class LinkList implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Properties">
    private static final long serialVersionUID = 1L;
    @Id
    @Column (name = "id", nullable = false, length = 37)
    private String id;
    @Column (name = "name", nullable = false, length = 30)
    private String name;
    @Column (name = "descripcion", nullable = true, length = 510)
    private String descripcion;
    @Column (name = "operacion", nullable = true, length = 510)
    private String operacion;
    @Column (name = "link", nullable = false, length = 255)
    private String link;
    @Column (name = "active", nullable = false)
    private Boolean active;
    @Column (name = "orden", nullable = false, length = 10)
    private String orden;
    @Enumerated(EnumType.ORDINAL)
    @Column (name = "nivel", nullable = false, length = 10)
    private TipoNivelSeguridad nivel;
    @Column (name = "prioridad", nullable = false)
    private Integer prioridad;
    @Enumerated(EnumType.ORDINAL)
    @Column (name = "cod_operacion", nullable = true)
    private TipoOperacion codOperacion;
    @Column (name = "locale", nullable = false, length = 5)
    private String locale;
    @Enumerated(EnumType.ORDINAL)
    @Column (name = "permiso", nullable = true)
    private ColumnasPermisos permiso;
    @Column (name = "tarea", nullable = true)
    private boolean tarea;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructores">
    public LinkList() {
    }

    public LinkList(String id) {
        this.id = id;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Section GetSet">
    /**
     * Obtiene el id del link.
     * @return Identificador único del link.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el id del link.
     * @param id Identificador único del link.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene si el link está activo.
     *
     * A través de este campo es posible desactivar los links o sublinks
     * que se muestran en la página.
     *
     * @return <code>true</code> en caso de que el link se encuentre activo
     * <code>false</code> en el caso contrario.
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * Establece si el link está activo.
     *
     * A través de este campo es posible desactivar los links o sublinks
     * que se muestran en la página.
     *
     * @param active <code>true</code> en caso de que el link se encuentre activo
     * <code>false</code> en el caso contrario.
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Descripción sobre el link.
     *
     * Una descripción detallada que permita saber a que está destinado el link.
     *
     * @return <code>String</code> con la descripción asociada al link.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Descripción sobre el link.
     *
     * Una descripción detallada que permita saber a que está destinado el link.
     *
     * @param descripcion <code>String</code> con la descripción asociada al link.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el nombre del link
     *
     * El nombre del link es el texto a mostrar cuando se muestra el link
     * en pantalla.
     *
     * @return Nombre del link a mostrar en la página.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del link
     *
     * El nombre del link es el texto a mostrar cuando se muestra el link
     * en pantalla.
     *
     * @param name Nombre del link a mostrar en la página.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene la url del link
     * @return url del link.
     */
    public String getLink() {
        return link;
    }

    /**
     * Establece la url del link
     * @param link url del link.
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * Obtiene los Niveles en los que se dividen los links.
     *
     * A través de este campo se establecen los niveles de los links, es un
     * String dividido en campos de dos carácteres, cada dos carácteres indican
     * un nivel de anidamiento.
     * ej:
     *   Un link de orden 00 ó 01 ó 02 ó etc indican el primer nivel de los
     * enlaces.
     *   Un link de orden 0000 ó 0001 ó 0002 indican que se tratan de los sub-links
     *   del nivel 00 y cada sublint tiene su propio orden que se encuentra a continuación
     *   de los dos primiros carácteres.
     *
     * @return <code>String</code> Con el orden correspondiente al link.
     */
    public String getOrden() {
        return orden;
    }

    /**
     * Establece los Niveles en los que se dividen los links.
     *
     * A través de este campo se establecen los niveles de los links, es un
     * String dividido en campos de dos carácteres, cada dos carácteres indican
     * un nivel de anidamiento.
     * ej:
     *   Un link de orden 00 ó 01 ó 02 ó etc indican el primer nivel de los
     * enlaces.
     *   Un link de orden 0000 ó 0001 ó 0002 indican que se tratan de los sub-links
     *   del nivel 00 y cada sublint tiene su propio orden que se encuentra a continuación
     *   de los dos primiros carácteres.
     *
     * @return <code>String</code> Con el orden correspondiente al link.
     * @param orden
     */
    public void setOrden(String orden) {
        this.orden = orden;
    }
    
    /**
     * Establece el idioma al que está orientado el enlace.
     * @return Idioma al que está orientado el enlace en formato "es" o "es_ES".
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Obtiene el idioma al que está orientado el enlace.
     * @param locale Idioma al que está orientado el enlace en
     * formato "es" o "es_ES".
     */
    public void setLocale(String locale) {
        this.locale = locale;
    }

    /**
     * Obtiene la operación a realizar en realación a la LOPD.
     * 
     * Por ejemplo para hacer el documento de seguridad la operación podría
     * llamarse Documento de Seguridad.
     * 
     * @return Descripción de la operación.
     */
    public String getOperacion() {
        return operacion;
    }
    
    /**
     * Establece la operación a realizar en realación a la LOPD.
     * 
     * Por ejemplo para hacer el documento de seguridad la operación podría
     * llamarse Documento de Seguridad.
     * 
     * @param operacion Descripción de la operación.
     */
    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    /**
     * Obtiene el nivel de seguridad al que pertenece.
     * @return Nivel de seguridad.
     */
    public TipoNivelSeguridad getNivel() {
        return nivel;
    }

    /**
     * Establece el nivel de seguridad al que pertenece.
     * @param nivel Nivel de seguridad.
     */
    public void setNivel(TipoNivelSeguridad nivel) {
        this.nivel = nivel;
    }

    /**
     * Obtiene la prioridad de la operación.
     * 
     * Es la prioridad de la operación lopd. El cero es la menor prioridad.
     * 
     * @return prioridad de la operación.
     */
    public Integer getPrioridad() {
        return prioridad;
    }

    /**
     * Establece la prioridad de la operación.
     * 
     * Es la prioridad de la operación lopd. El cero es la menor prioridad.
     * 
     * @param prioridad prioridad de la operación.
     */
    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    /**
     * Establece el código que identifica a la operación LOPD.
     * @return código que identifica a la operación LOPD.
     */
    public TipoOperacion getCodOperacion() {
        return codOperacion;
    }

    /**
     * Establece el código que identifica a la operación LOPD.
     * @param codOperacion código que identifica a la operación LOPD.
     */
    public void setCodOperacion(TipoOperacion codOperacion) {
        this.codOperacion = codOperacion;
    }
    
    /**
     * Obtiene el permiso de acceso al link.
     * @return Tipo de permiso necesario para el acceso al link.
     */
    public ColumnasPermisos getPermiso() {
        return permiso;
    }

    /**
     * Establece el permiso de acceso al link.
     * @param permiso Tipo de permiso necesario para el acceso al link.
     */
    public void setPermiso(ColumnasPermisos permiso) {
        this.permiso = permiso;
    }

    /**
     * Indica si el enlace está considerado como una tarea habitual.
     * @return true en caso de tarea habitual false en caso contrario.
     */    
    public boolean isTarea() {
        return tarea;
    }

    /**
     * Establece si el enlace debe ser considerado una tarea habitual.
     * @param tarea true en caso de tarea habitual false en caso contrario.
     */
    public void setTarea(boolean tarea) {
        this.tarea = tarea;
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
        if (!(object instanceof LinkList)) {
            return false;
        }
        LinkList other = (LinkList) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.aiesalcala.home.entities.interfaz.linkList[id=" + id + "]";
    }
    // </editor-fold>

}
