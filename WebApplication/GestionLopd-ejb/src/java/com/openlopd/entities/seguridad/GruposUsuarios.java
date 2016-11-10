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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Entidad encargada de almacenar las relaciones entre usuarios y sus grupos
 * correspondientes.
 * @author Eduardo L. García Glez.
 * Fecha 23 de enero de 2011
 * @version 1.0.1
 * Modificaciones:
 *      Se corrige el método ToString y el nombre del objeto sobre el que se selecciona en las NamedQuerys.
 */
@Entity
@Table(name = "grupos_usuarios")
@NamedQueries({
    @NamedQuery(name = "GruposUsuarios.findAll", query = "SELECT g FROM GruposUsuarios g"),
    @NamedQuery(name = "GruposUsuarios.findByIdUsuario", query = "SELECT g FROM GruposUsuarios g WHERE g.gruposUsuariosPK.idUsuario = :idUsuario"),
    @NamedQuery(name = "GruposUsuarios.findByIdGrupo", query = "SELECT g FROM GruposUsuarios g WHERE g.gruposUsuariosPK.idGrupo = :idGrupo"),
    @NamedQuery(name = "GruposUsuarios.deleteByIdUsuario", query = ""
        + "DELETE FROM GruposUsuarios g "
        + "WHERE g.gruposUsuariosPK.idUsuario = :idUsuario")
})
public class GruposUsuarios implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Section Properties">
    @EmbeddedId
    protected GruposUsuariosPK gruposUsuariosPK;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructores">
    /**
     * Constructor predeterminado.
     */
    public GruposUsuarios() {
    }

    /**
     * Permite inicializar el objeto enviando un objeto con la clave.
     * @param gruposUsuariosPK Objeto con la clave primaria a utilizar.
     */
    public GruposUsuarios(GruposUsuariosPK gruposUsuariosPK) {
        this.gruposUsuariosPK = gruposUsuariosPK;
    }

    /**
     * Permite inicializar el objeto con los datos necesario para la clave primaria.
     * @param idUsuario Identificador único del usuario.
     * @param idGrupo Identificador único del grupo.
     */
    public GruposUsuarios(String idUsuario, String idGrupo) {
        this.gruposUsuariosPK = new GruposUsuariosPK(idUsuario, idGrupo);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Secction GetSet">
    /**
     * Obtiene la clave primaria compuesta de la entidad.
     * @return Clave primaria.
     */
    public GruposUsuariosPK getGruposUsuariosPK() {
        return gruposUsuariosPK;
    }

    /**
     * Establece la clave primaria compuesta de la entidad.
     * @param gruposUsuariosPK Clave primaria a establecer.
     */
    public void setGruposUsuariosPK(GruposUsuariosPK gruposUsuariosPK) {
        this.gruposUsuariosPK = gruposUsuariosPK;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Default Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gruposUsuariosPK != null ? gruposUsuariosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GruposUsuarios)) {
            return false;
        }
        GruposUsuarios other = (GruposUsuarios) object;
        if ((this.gruposUsuariosPK == null && other.gruposUsuariosPK != null)
                || (this.gruposUsuariosPK != null && !this.gruposUsuariosPK.equals(other.gruposUsuariosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.seguridad.GruposUsuariosPK[gruposUsuariosPK=" + gruposUsuariosPK + "]";
    }
    // </editor-fold>
}
