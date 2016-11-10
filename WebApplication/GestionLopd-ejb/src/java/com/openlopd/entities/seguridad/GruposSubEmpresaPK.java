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
 * Clase encargada de gestionar la clave primaria de la entidad GruposSubEmpresa.
 * @author Eduardo L. García Glez.
 * Fecha 23 de enero de 2011
 * @version 1.0.1
 * Modificaciones
 *    06 de feb de 2011 Corregidos los nombres de los métodos get y set.
 */
@Embeddable
public class GruposSubEmpresaPK implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Section Properties">
    @Basic(optional = false)
    @Column(nullable = false, length = 37)
    private String idGrupo;
    @Basic(optional = false)
    @Column(nullable = false, length = 37)
    private String idEmpresa;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Secction Constructores">
    public GruposSubEmpresaPK() {
    }

    public GruposSubEmpresaPK(String idGrupo, String idEmpresa) {
        this.idGrupo = idGrupo;
        this.idEmpresa = idEmpresa;
    }
    // </editor-fold>

    // <editor-fold defaultstate="expanded" desc="Secction GetSet">
    /**
     * Obtiene el id del Grupo.
     *
     * Obtiene el id del grupo relacionado con la subEmpresa indicada por la propiedad
     * <code>idEmpresa</code>
     * @return Identificador único del grupo.
     */
    public String getIdGrupo() {
        return idGrupo;
    }

    /**
     * Establece el id del grupo.
     *
     * Establece el identificador único del grupo relacionado con la subEmpresa
     * indicada en la propiedad <code>idEmpresa</code>.
     * @param idGrupo Identificador único del grupo.
     */
    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    /**
     * Obtiene el id de la empresa.
     *
     * Obtiene el id de la empresa relacionada con el grupo indicado en la
     * propiedad idGrupo.
     * @return Id del usuario.
     */
    public String getIdEmpresa() {
        return idEmpresa;
    }

    /**
     * Establece el id de la empresa.
     *
     * Establece el id de la empresa relacionada con el grupo que se indica en la
     * propiedad <code>idGrupo</code>.
     * @param idEmpresa identificador único de la empresa.
     */
    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Default Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpresa != null ? idEmpresa.hashCode() : 0);
        hash += (idGrupo != null ? idGrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GruposSubEmpresaPK)) {
            return false;
        }
        GruposSubEmpresaPK other = (GruposSubEmpresaPK) object;
        if ((this.idEmpresa == null && other.idEmpresa != null) || (this.idEmpresa != null && !this.idEmpresa.equals(other.idEmpresa))) {
            return false;
        }
        if ((this.idGrupo == null && other.idGrupo != null) || (this.idGrupo != null && !this.idGrupo.equals(other.idGrupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.seguridUsuariod.MitablaPK[idEmresa=" + idEmpresa + ", idGrupo=" + idGrupo + "]";
    }
    // </editor-fold>
}
