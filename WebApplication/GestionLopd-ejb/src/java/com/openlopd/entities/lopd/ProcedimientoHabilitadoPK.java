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
import javax.persistence.OneToOne;

/**
 * Almacena los tipos de procedimientos.
 *
 * @author Eduardo L. García Glez.
 */
@Embeddable
public class ProcedimientoHabilitadoPK implements Serializable {

    private static final long serialVersionUID = 1L;
    // <editor-fold defaultstate="collapsed" desc="Section Properties">
    @Basic(optional = false)
    @Column(length = 37)
    private String idProcedimiento;
    @Basic(optional = false)
    @Column(length = 37)
    private String idEmpresa;
    // </editor-fold>

    public ProcedimientoHabilitadoPK() {
    }

    public ProcedimientoHabilitadoPK(String idProcedimiento, String idEmpresa) {
        this.idProcedimiento = idProcedimiento;
        this.idEmpresa = idEmpresa;
    }

    public String getIdProcedimiento() {
        return idProcedimiento;
    }

    public void setIdProcedimiento(String idProcedimiento) {
        this.idProcedimiento = idProcedimiento;
    }
    
    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    
}
