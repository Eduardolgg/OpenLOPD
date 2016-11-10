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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Gestiona los procedimientos habilitados de una Empresa.
 * @author Eduardo L. García Glez.
 */
@Entity
@Table (name = "procedimientos_habilitados", schema = "lopd")
@NamedQueries ({
    @NamedQuery(name = "ProcedimientoHabilitado.findByTipo", query = ""
        + "SELECT p FROM ProcedimientoHabilitado p "
        + "WHERE p.procInfo.tipo.tipoProcedimientoPK.id = :idTipoProc "
        + "  AND p.procedimientoHabilitadoPK.idEmpresa = :idEmpresa" ),
    @NamedQuery(name = "ProcedimientoHabilitado.findAll", query = ""
        + "SELECT p FROM ProcedimientoHabilitado p "
        + "WHERE p.procedimientoHabilitadoPK.idEmpresa = :idEmpresa" )
        
})
public class ProcedimientoHabilitado implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    ProcedimientoHabilitadoPK procedimientoHabilitadoPK;
    @OneToOne
    private ProcedimientoDisponible procInfo;
    

    public ProcedimientoHabilitado() {
    }

    public ProcedimientoHabilitado(ProcedimientoHabilitadoPK procedimientoHabilitadoPK) {
        this.procedimientoHabilitadoPK = procedimientoHabilitadoPK;
    }

    public ProcedimientoHabilitadoPK getProcedimientoHabilitadoPK() {
        return procedimientoHabilitadoPK;
    }

    public void setProcedimientoHabilitadoPK(ProcedimientoHabilitadoPK procedimientoHabilitadoPK) {
        this.procedimientoHabilitadoPK = procedimientoHabilitadoPK;
    }

    public ProcedimientoDisponible getProcInfo() {
        return procInfo;
    }

    public void setProcInfo(ProcedimientoDisponible procInfo) {
        this.procInfo = procInfo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.procedimientoHabilitadoPK);
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
        final ProcedimientoHabilitado other = (ProcedimientoHabilitado) obj;
        if (!Objects.equals(this.procedimientoHabilitadoPK, other.procedimientoHabilitadoPK)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProcedimientoHabilitado{" + "procedimientoHabilitadoPK=" + procedimientoHabilitadoPK + '}';
    }
    
}
