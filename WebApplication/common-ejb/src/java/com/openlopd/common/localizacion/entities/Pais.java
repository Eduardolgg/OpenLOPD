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

package com.openlopd.common.localizacion.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Listado de Paises.
 * @author Eduardo L. García Glez.
 * @version 0.0.0 30 de nov de 2012
 */
@Entity
@Table (name = "aux_paises")
@NamedQueries({
    @NamedQuery(name = "Pais.findAll", query = "SELECT p FROM Pais p ORDER BY p.pais ASC"),
    @NamedQuery(name = "Pais.findByAGPDCode", query = ""
        + "SELECT p FROM Pais p WHERE p.agpdCode = :agpdCode"),
    @NamedQuery(name = "Pais.findByAcceso", query = ""
        + "SELECT p FROM Pais p WHERE p.agpdAcceso = TRUE ORDER BY p.pais ASC"),
    @NamedQuery(name = "Pais.findByDeclarante", query = ""
        + "SELECT p FROM Pais p WHERE p.agpdDeclarante = TRUE ORDER BY p.pais ASC"),
    @NamedQuery(name = "Pais.findByEncargado", query = ""
        + "SELECT p FROM Pais p WHERE p.agpdEncargado = TRUE ORDER BY p.pais ASC"),
    @NamedQuery(name = "Pais.findByResponsable", query = ""
        + "SELECT p FROM Pais p WHERE p.agpdResponsable = TRUE ORDER BY p.pais ASC"),
    @NamedQuery(name = "Pais.findByTransfInternacional", query = ""
        + "SELECT p FROM Pais p WHERE p.agpdTransferenciaInternacional = TRUE ORDER BY p.pais ASC")
})
public class Pais implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String agpdCode;
    private String pais;
    private Boolean agpdResponsable;
    private Boolean agpdAcceso;
    private Boolean agpdEncargado;
    private Boolean agpdTransferenciaInternacional;
    private Boolean agpdDeclarante;

    public Pais() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgpdCode() {
        return agpdCode;
    }

    public void setAgpdCode(String agpdCode) {
        this.agpdCode = agpdCode;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Boolean getAgpdResponsable() {
        return agpdResponsable;
    }

    public void setAgpdResponsable(Boolean agpdResponsable) {
        this.agpdResponsable = agpdResponsable;
    }

    public Boolean getAgpdAcceso() {
        return agpdAcceso;
    }

    public void setAgpdAcceso(Boolean agpdAcceso) {
        this.agpdAcceso = agpdAcceso;
    }

    public Boolean getAgpdEncargado() {
        return agpdEncargado;
    }

    public void setAgpdEncargado(Boolean agpdEncargado) {
        this.agpdEncargado = agpdEncargado;
    }

    public Boolean getAgpdTransferenciaInternacional() {
        return agpdTransferenciaInternacional;
    }

    public void setAgpdTransferenciaInternacional(Boolean agpdTransferenciaInternacional) {
        this.agpdTransferenciaInternacional = agpdTransferenciaInternacional;
    }

    public Boolean getAgpdDeclarante() {
        return agpdDeclarante;
    }

    public void setAgpdDeclarante(Boolean agpdDeclarante) {
        this.agpdDeclarante = agpdDeclarante;
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
        if (!(object instanceof Pais)) {
            return false;
        }
        Pais other = (Pais) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pais{" + "id=" + id + ", agpdCode=" + agpdCode + ", pais=" + pais + ", agpdResponsable=" + agpdResponsable + ", agpdAcceso=" + agpdAcceso + ", agpdEncargado=" + agpdEncargado + ", agpdTransferenciaInternacional=" + agpdTransferenciaInternacional + ", agpdDeclarante=" + agpdDeclarante + '}';
    }
}
