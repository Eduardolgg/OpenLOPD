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

package com.openlopd.entities.empresas;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Eduardo L. García Glez.
 * Fecha: 13 de ene de 2011
 * @version 1.0.0
 */
@Entity
@Table(name = "empresa_aux")
public class EmpresaAux implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "idEmpresa", nullable = false, length = 37)
    private String idEmpresa;
    @Column(name = "extra_info_desc", nullable = false, length = 255)
    private String extraInfoDesc;
    @Column(name = "extraInfoText", nullable = false, length = 50000)
    private String extraInfoText;

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getExtraInfoDesc() {
        return extraInfoDesc;
    }

    public void setExtraInfoDesc(String extraInfoDesc) {
        this.extraInfoDesc = extraInfoDesc;
    }

    public String getExtraInfoText() {
        return extraInfoText;
    }

    public void setExtraInfoText(String extraInfoText) {
        this.extraInfoText = extraInfoText;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpresa != null ? idEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmpresaAux)) {
            return false;
        }
        EmpresaAux other = (EmpresaAux) object;
        if ((this.idEmpresa == null && other.idEmpresa != null) ||
                (this.idEmpresa != null && !this.idEmpresa.equals(other.idEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.empresas.EmpresaAux[id=" + idEmpresa + "]";
    }

}
