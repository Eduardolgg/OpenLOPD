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

package com.openlopd.entities.lopd;

import com.openlopd.entities.documentos.FileDataBase;
import com.openlopd.entities.empresas.Empresa;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Gestiona los ficheros predefinidos para el envío a la AGPD
 * @author Eduardo L. García Glez.
 * @version 0.0.0 21 de feb de 2013
 */
@Entity
@Table(name = "ficheros_predefinidos", schema="lopd")
@NamedQueries({
    @NamedQuery(name="FicheroPredefinido.findActives", query=""
        + "SELECT f FROM FicheroPredefinido f "
        + "WHERE f.active = TRUE "
        + "  AND f.borrado IS NULL")
})
public class FicheroPredefinido implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(length = 37)
    private String id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
    @Column(name ="usuario", nullable = false, length = 255)
    private String usuario;
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;
    @Column(name = "descripcion", nullable = false, length = 350)
    private String descripcion;
    @OneToOne
    private FileDataBase fichero;
    @Column(name = "fecha_alta_int", nullable = false)
    private Long fechaAltaInterna;
    @Column(name = "borrado", nullable = true)
    private Long borrado;
    @Column(name = "borrado_por", nullable = true, length = 25)
    private String borradoPor;
    @Column(name = "active", nullable = true)
    private boolean active;

    public FicheroPredefinido() {
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }

    /**
     * Obtiene el identificador único de la entidad.
     * @return Identificador único de la entidad.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador único de la entidad.
     * @param id Identificador único de la entidad.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Establece la empresa a la que pertenece la entidad.
     * @return Empresa a la que pertenece la entidad.
     */
    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * Obtiene la empresa a la que pertenece la entidad.
     * @param empresa Empresa a la que pertenece la entidad.
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    /**
     * Obtiene el usuario que crea la entidad.
     * @return Usuario que crea la entidad.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario que crea la entidad.
     * @param usuario Usuario que crea la entidad.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Establece el nombre de con el que el usuario identifica la entidad.
     * @return Nombre descriptivo de la entidad.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el nombre de con el que el usuario identifica la entidad.
     * @param nombre Nombre descriptivo de la entidad.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción de la entidad.
     * @return Descripción detallada de la entidad.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la entidad.
     * @param descripcion Descripción detallada de la entidad.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el fichero predefinido.
     * @return Estructura completa en formato XML NOTA del fichero predefinido.
     */
    public FileDataBase getFichero() {
        return fichero;
    }

    /**
     * Establece el fichero predefinido.
     * @param fichero Estructura completa en formato XML NOTA del fichero predefinido.
     */
    public void setFichero(FileDataBase fichero) {
        this.fichero = fichero;
    }

    /**
     * Obtiene la fecha en la que la entidad entra en la base de datos.
     * @return Fecha en la que la entidad entra en la base de datos.
     */
    public Long getFechaAltaInterna() {
        return fechaAltaInterna;
    }

    /**
     * Establece la fecha en la que la entidad entra en la base de datos.
     * @param fechaAltaInterna Fecha en la que la entidad entra en la base de datos.
     */
    public void setFechaAltaInterna(Long fechaAltaInterna) {
        this.fechaAltaInterna = fechaAltaInterna;
    }

    /**
     * Obtiene la fecha en la que la entidad es borrada.
     * 
     * Además si está cumplimentado indica al sistema que la entidad está
     * borrada.
     * 
     * @return Fecha en la que la entidad es borrada.
     */
    public Long getBorrado() {
        return borrado;
    }

    /**
     * Establece la fecha en la que la entidad es borrada.
     * 
     * Además si está cumplimentado indica al sistema que la entidad está
     * borrada.
     * 
     * @param borrado Fecha en la que la entidad es borrada.
     */
    public void setBorrado(Long borrado) {
        this.borrado = borrado;
    }

    /**
     * Establece el usuario que borra la entidad.
     * @return identificador del usuario que borra la entidad.
     */
    public String getBorradoPor() {
        return borradoPor;
    }

    /**
     * Obtiene el usuario que borra la entidad.
     * @param borradoPor identificador del usuario que borra la entidad.
     */
    public void setBorradoPor(String borradoPor) {
        this.borradoPor = borradoPor;
    }

    /**
     * Obtiene si la entidad está activa.
     * 
     * Permite hacer que la entidad se muestre o no al usuario.
     * 
     * @return true si la entidad está activa, false en caso contrario.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Establece si la entidad está activa.
     * 
     * Permite hacer que la entidad se muestre o no al usuario.
     * 
     * @param active true si la entidad está activa, false en caso contrario.
     */
    public void setActive(boolean active) {
        this.active = active;
    }    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FicheroPredefinido)) {
            return false;
        }
        FicheroPredefinido other = (FicheroPredefinido) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.lopd.FicherosPredefinidos[ id=" + id + " ]";
    }
    
}
