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
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Almacena los ficheros a los que cada persona puede acceder.
 *
 * @author Eduardo L. García Glez.
 */
@Embeddable
public class PersonaFicheroPK implements Serializable {

    private static final long serialVersionUID = 1L;
    // <editor-fold defaultstate="collapsed" desc="Section Properties">
    @Basic(optional = false)
    @Column(length = 37)
    private String idPersona;
    @Basic(optional = false)
    @Column(length = 50)
    private String nombreFichero;
    // </editor-fold>

    public PersonaFicheroPK() {
    }

    public PersonaFicheroPK(String idPersona, String nombreFichero) {
        this.idPersona = idPersona;
        this.nombreFichero = nombreFichero;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombreFichero() {
        return nombreFichero;
    }

    public void setNombreFichero(String nombreFichero) {
        this.nombreFichero = nombreFichero;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.idPersona);
        hash = 37 * hash + Objects.hashCode(this.nombreFichero);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PersonaFicheroPK other = (PersonaFicheroPK) obj;
        if (!Objects.equals(this.idPersona, other.idPersona)) {
            return false;
        }
        if (!Objects.equals(this.nombreFichero, other.nombreFichero)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PersonaFicheroPK{" + "idPersona=" + idPersona + ", nombreFichero=" + nombreFichero + '}';
    }
}
