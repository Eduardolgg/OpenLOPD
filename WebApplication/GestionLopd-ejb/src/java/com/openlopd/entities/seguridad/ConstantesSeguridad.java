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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Contiene las constantes que son necesarias para el sistema de seguridad.
 * <b>El id del objeto se genera de forma automática.</b>
 * @author Eduardo L. García Glez.
 * Fecha 09 de ene de 2011
 * @version 1.0.1
 * Modificado
 *    18 de enero de 2011 añadida propiedad descripción y NamedQuerys
 *    24 de enero de 2011 Se corrigen los nombres de las NamedQuerys
 */
@Entity
@Table(name = "constantes_seguridad")
@NamedQueries({
    @NamedQuery(name = "ConstantesSeguridad.findAll", query = "SELECT c FROM ConstantesSeguridad c"),
    @NamedQuery(name = "ConstantesSeguridad.findById", query = "SELECT c FROM ConstantesSeguridad c WHERE c.id = :id"),
    @NamedQuery(name = "ConstantesSeguridad.findByNombre", query = "SELECT c FROM ConstantesSeguridad c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ConstantesSeguridad.findByTipo", query = "SELECT c FROM ConstantesSeguridad c WHERE c.tipo = :tipo")})
public class ConstantesSeguridad implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Section Properties">
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Short id;
    @Column (nullable = false, length = 25)
    private String nombre;
    @Column (nullable = false, length = 100)
    private String descripcion;
    @Column (nullable = false, length = 20)
    private String valor;
    @Column (nullable = false, length = 20)
    private String tipo;
    // </editor-fold>

    // <editor-fold defaultstate="expanded" desc="Secction Constructores">
    /**
     * Constructor por defecto.
     */
    public ConstantesSeguridad() { }

    /**
     * Inicializa los parámetros de la entidad.
     *
     * El id es generado de forma automática.
     * @param nombre Nombre o identificación de la constante de seguridad.
     * @param descripcion Descripción detallada de la constante.
     * @param valor Valor de la constante de seguridad.
     * @param tipo Tipo de la constante de seguridad, integer, String, etc.
     */
    public ConstantesSeguridad(String nombre, String descripcion, String valor, String tipo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.valor = valor;
        this.tipo = tipo;
    }
    // </editor-fold>

    // <editor-fold defaultstate="expanded" desc="Secction GetSet">
    /**
     * Obtiene el identificador único que tiene asignado la constante.
     * @return Id de la constante.
     */
    public Short getId() {
        return id;
    }

    /**
     * Establece el identificador único asignado a la constante.
     *
     * Nota: El identificados se genera de forma automática durante la insersión.
     * @param id Identificador único asociado a la constante.
     */
    public void setId(Short id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la constante.
     * @return Nombre de la contante.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la constante.
     * @param nombre Nombre de la constante.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción detallada de la constante.
     * @return Descripción detallada de la constante.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción detallada de la constante.
     * @param descripcion Descripción detallada de la constante.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el tipo de la variable.
     * 
     * Esta propiedad es importante ya que es necesaria para hacer la conversión
     * necesaria de la propiedad <b>valor</b> para que no se produzcan errores
     * en la aplicación.
     * @return Tipo de la variable (String, integer, etc.)
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de la variable.
     *
     * Esta propiedad es importante ya que es necesaria para hacer la conversión
     * necesaria de la propiedad <b>valor</b> para que no se produzcan errores
     * en la aplicación.
     * @param tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    /**
     * Obtiene el valor de la constante.
     *
     * Para evitar problemas con el tipo del valor es necesario utilizar la propiedad
     * <b>tipo</b> en la que se establece el tipo del valor aquí almacenado.
     * @return Valor de la constante.
     */
    public String getValor() {
        return valor;
    }

    /**
     * Establece el valor de la constante.
     *
     * Para evitar problemas con el tipo del valor es necesario utilizar la propiedad
     * <b>tipo</b> en la que se establece el tipo del valor aquí almacenado.
     * @param valor Valor para la constante.
     */
    public void setValor(String valor) {
        this.valor = valor;
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
        if (!(object instanceof ConstantesSeguridad)) {
            return false;
        }
        ConstantesSeguridad other = (ConstantesSeguridad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.seguridad.ConstantesSeguridad[id=" + id + "]";
    }
    // </editor-fold>
}
