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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Mantiene los contadores de incidencia de las distintas empresas.
 * @author Eduardo L. García Glez.
 * @version 0.0.0 28 de oct de 2012
 */
@Entity
@Table (name = "contador_incidencias", schema = "lopd")
public class ContadorIncidencias implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column (name = "id_empresa")
    private String idEmpresa;
    private Long contador;

    public ContadorIncidencias() {
    }

    public ContadorIncidencias(String idEmpresa, Long contador) {
        this.idEmpresa = idEmpresa;
        this.contador = contador;
    }    

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Long getContador() {
        return contador;
    }

    public void setContador(Long contador) {
        this.contador = contador;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpresa != null ? idEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the idEmpresa fields are not set
        if (!(object instanceof ContadorIncidencias)) {
            return false;
        }
        ContadorIncidencias other = (ContadorIncidencias) object;
        if ((this.idEmpresa == null && other.idEmpresa != null) || (this.idEmpresa != null && !this.idEmpresa.equals(other.idEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.lopd.ContadorIncidencias[ id=" + idEmpresa + " ]";
    }
    
}
