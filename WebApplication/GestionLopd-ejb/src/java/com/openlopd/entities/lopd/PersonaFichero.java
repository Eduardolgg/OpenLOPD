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

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Identifica los ficheros a lo que accede cada persona.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 19 de ene de 2012
 */
@Entity
@Table(name = "personas_ficheros", schema = "lopd")
@NamedQueries({
    @NamedQuery(name = "PersonaFichero.findByIdPersona", query = ""
        + "SELECT pf FROM PersonaFichero pf "
        + "WHERE pf.id.idPersona = :idPersona"),
    @NamedQuery(name = "PersonaFichero.DeleteByIdPersona", query = ""
        + "DELETE FROM PersonaFichero pf WHERE pf.id.idPersona = :idPersona")
})
public class PersonaFichero implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private PersonaFicheroPK id;
    @Column(name = "fecha_alta", nullable = false)
    private Long fechaAlta;
    @Column(name = "usuario", nullable = false, length = 255)
    private String usuario;

    public PersonaFichero() {
    }

    public PersonaFichero(PersonaFicheroPK id, Long fechaAlta, String usuario) {
        this.id = id;
        this.fechaAlta = fechaAlta;
        this.usuario = usuario;
    }

    public PersonaFicheroPK getId() {
        return id;
    }

    public void setId(PersonaFicheroPK id) {
        this.id = id;
    }

    public Long getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Long fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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
        if (!(object instanceof PersonaFichero)) {
            return false;
        }
        PersonaFichero other = (PersonaFichero) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.lopd.PersonaFichero[ id=" + id + " ]";
    }
}
