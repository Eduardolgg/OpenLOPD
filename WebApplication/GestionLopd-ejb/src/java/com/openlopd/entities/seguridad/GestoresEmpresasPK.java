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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Clase encargada de gestionar la clave primaria de la entidad GestoresEmpresas.
 * @author Eduardo L. García Glez.
 * Fecha 24 de enero de 2011
 * @version 1.0.0
 */
@Embeddable
public class GestoresEmpresasPK implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Section Properties">
    @Basic(optional = false)
    @Column(nullable = false, length = 9)
    private String cifGestor;
    @Basic(optional = false)
    @Column(nullable = false, length = 37)
    private String idEmpresa;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Secction Constructores">
    /**
     * Constructor por defecto.
     */
    public GestoresEmpresasPK() {
    }

    /**
     * Contructor que inicializa los valores de la clave primaria.
     * @param cifGestor CIF/NIF del la empresa/autonomo gestor/gestora.
     * @param idEmpresa Id de la empresa gestionada.
     */
    public GestoresEmpresasPK(String cifGestor, String idEmpresa) {
        this.cifGestor = cifGestor;
        this.idEmpresa = idEmpresa;
    }
    // </editor-fold>

    // <editor-fold defaultstate="expanded" desc="Secction GetSet">
    /**
     * Obtiene el CIF/NIF del Gestor.
     * @return CIF/NIF del Gestor.
     */
    public String getCifGestor() {
        return cifGestor;
    }

    /**
     * Establece el CIF/NIF del Gestor.
     * @param cifGestor CIF/NIF del Gestor
     */
    public void setCifGestor(String cifGestor) {
        this.cifGestor = cifGestor;
    }

    /**
     * Obtiene el identificador único de la empresa.
     * @return Identificador único de la empresa.
     */
    public String getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * Establece el Identificador único de la empresa.
     * @param idEmpresa Identificador único de la empresa.
     */
    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Default Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cifGestor != null ? cifGestor.hashCode() : 0);
        hash += (idEmpresa != null ? idEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GestoresEmpresasPK)) {
            return false;
        }
        GestoresEmpresasPK other = (GestoresEmpresasPK) object;
        if ((this.cifGestor == null && other.cifGestor != null) || (this.cifGestor != null && !this.cifGestor.equals(other.cifGestor))) {
            return false;
        }
        if ((this.idEmpresa == null && other.idEmpresa != null) || (this.idEmpresa != null && !this.idEmpresa.equals(other.idEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.seguridUsuariod.GestoresEmpresasPK[cifGestor=" + cifGestor + ", idEmpresa=" + idEmpresa + "]";
    }
    // </editor-fold>
}
