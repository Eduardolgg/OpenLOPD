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
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Tipos de procedimientos
 * @author Eduardo L. García Glez.
 */
@Entity
@Table (name = "tipo_procedimiento", schema = "lopd")
@NamedQueries  ({
    @NamedQuery(name = "TipoProcedimiento.findActivesByEmpresa", query = ""
        + "SELECT t FROM TipoProcedimiento t "
        + "WHERE t.fechaBaja is null "
        + "  AND (t.tipoProcedimientoPK.idEmpresa = :idEmpresa " 
        + "   OR t.tipoProcedimientoPK.idEmpresa = :idEmpresaGestora "
        + "   OR t.tipoProcedimientoPK.idEmpresa = :idEmpresaSistema)"
        + "ORDER BY t.orden" ),
    @NamedQuery(name = "TipoProcedimiento.findActivesByTipo", query = ""
        + "SELECT t FROM TipoProcedimiento t "
        + "WHERE t.fechaBaja is null "
        + "  AND t.tipoProcedimientoPK.id = :idTipoProc "
        + "  AND (t.tipoProcedimientoPK.idEmpresa = :idEmpresa " 
        + "   OR t.tipoProcedimientoPK.idEmpresa = :idEmpresaGestora "
        + "   OR t.tipoProcedimientoPK.idEmpresa = :idEmpresaSistema) "
        + "ORDER BY t.orden " )
})
public class TipoProcedimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private TipoProcedimientoPK tipoProcedimientoPK;
    @Column (name = "descripcion", length = 1024, nullable = true)
    private String descripcion;
    @Column (name = "titulo", length = 30, nullable = false)
    private String titulo;
    @Column (name = "fecha_alta", nullable = false)
    private Long fechaAlta;
    @Column (name = "fecha_baja", nullable = true)
    private Long fechaBaja;
    @Column (name = "users_name", nullable = true)
    private String userName;
    @Column (name = "orden", nullable = true)
    private int orden;

    public TipoProcedimiento() {
    }

    public TipoProcedimiento(TipoProcedimientoPK tipoProcedimientoPK) {
        this.tipoProcedimientoPK = tipoProcedimientoPK;
    }

    public TipoProcedimientoPK getTipoProcedimientoPK() {
        return tipoProcedimientoPK;
    }

    public void setTipoProcedimientoPK(TipoProcedimientoPK tipoProcedimientoPK) {
        this.tipoProcedimientoPK = tipoProcedimientoPK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Long fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Long getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Long fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.tipoProcedimientoPK);
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
        final TipoProcedimiento other = (TipoProcedimiento) obj;
        if (!Objects.equals(this.tipoProcedimientoPK, other.tipoProcedimientoPK)) {
            return false;
        }
        if (!Objects.equals(this.descripcion, other.descripcion)) {
            return false;
        }
        if (!Objects.equals(this.fechaAlta, other.fechaAlta)) {
            return false;
        }
        if (!Objects.equals(this.fechaBaja, other.fechaBaja)) {
            return false;
        }
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TipoProcedimiento{" + "TipoProcedimientoPK=" + tipoProcedimientoPK + ", descripcion=" + descripcion + ", fechaAlta=" + fechaAlta + ", fechaBaja=" + fechaBaja + ", userName=" + userName + '}';
    }
    
}
