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

import com.openlopd.entities.empresas.Empresa;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Almacena los procedimientos disponibles.
 * @author Eduardo L. García Glez.
 */
@Entity
@Table (name = "procedimientos_disponibles", schema = "lopd")
@NamedQueries ({
    @NamedQuery(name = "ProcedimientoDisponible.findByTipo", query = ""
        + "SELECT p FROM ProcedimientoDisponible p "
        + "WHERE p.tipo.tipoProcedimientoPK.id = :idTipoProc "
        + "  AND (p.empresa.idEmpresa = :idEmpresaGestora "
        + "    OR p.empresa.idEmpresa = :idSubEmpresa"
        + "    OR p.empresa.idEmpresa = :idEmpresaSistema)"
        + "  AND p.id NOT IN (SELECT pa.procedimientoHabilitadoPK.idProcedimiento "
        + "                   FROM ProcedimientoHabilitado pa "
        + "                   WHERE pa.procedimientoHabilitadoPK.idEmpresa = :idSubEmpresa) " ),
    @NamedQuery(name = "ProcedimientoDisponible.findAll", query = ""
        + "SELECT p FROM ProcedimientoDisponible p "
        + "WHERE (p.empresa.idEmpresa = :idEmpresaGestora "
        + "    OR p.empresa.idEmpresa = :idSubEmpresa"
        + "    OR p.empresa.idEmpresa = :idEmpresaSistema)" 
        + "  AND p.id NOT IN (SELECT pa.procedimientoHabilitadoPK.idProcedimiento "
        + "                   FROM ProcedimientoHabilitado pa "
        + "                   WHERE pa.procedimientoHabilitadoPK.idEmpresa = :idSubEmpresa) " )
        
})
public class ProcedimientoDisponible implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column (length = 37)
    private String id;
    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;
    @ManyToOne(cascade = CascadeType.PERSIST)
//    @ManyToOne
    @JoinColumns({
        @JoinColumn(name="id_tipo_procedimiento", referencedColumnName="id"),
        @JoinColumn(name="id_empresa_tipo_procedimiento", referencedColumnName="id_empresa")
    })
    private TipoProcedimiento tipo;
    @Column (name = "descripcion", length = 1024, nullable = true)
    private String descripcion;
    @Column (name = "procedimiento", length = 5000, nullable = false)
    private String procedimiento;
    @Enumerated(EnumType.STRING)
    @Column (name = "nivel", nullable = false, length = 10)
    private TipoNivelSeguridad nivel;

    /**
     * Constructor por defecto.
     */
    public ProcedimientoDisponible() {
    }

    /**
     * Constructor que inicializa el objeto por la clave primaria.
     * @param id Identificador único de la entidad.
     */
    public ProcedimientoDisponible(String id) {
        this.id = id;
    }

    public ProcedimientoDisponible(String id, Empresa empresa, TipoProcedimiento tipo, 
            String descripcion, String procedimiento, TipoNivelSeguridad nivel) {
        this.id = id;
        this.empresa = empresa;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.procedimiento = procedimiento;
        this.nivel = nivel;
    }

    /**
     * Obtiene la clave primaria.
     * @return Clave primaria.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece la clave primaria
     * @param id Clave primaria.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene la empresa que crea el procedimiento.
     * @return Empresa creadora del procedimiento.
     */
    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * Establece la empresa que crea el procedimiento.
     * @param empresa Empresa creadora del procedimeinto.
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    /**
     * Obtiene el tipo de procedimiento.
     * 
     * Los procedimientos deben clasificarse según tipos para
     * luego con ellos poder cumplimentar el documento de seguridad
     * por bloques de tipos.
     * 
     * @return Tipo del procedimiento.
     */
    public TipoProcedimiento getTipo() {
        return tipo;
    }

    /**
     * Obtiene el tipo de procedimiento.
     * 
     * Los procedimientos deben clasificarse según tipos para
     * luego con ellos poder cumplimentar el documento de seguridad
     * por bloques de tipos.
     * 
     * @param tipo Tipo del procedimiento.
     */
    public void setTipo(TipoProcedimiento tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la descripción corta del procedimiento.
     * @return Descripción corta del procedimiento.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece el descripción corta del procedimiento.
     * @param descripcion Descripción corta del procedimiento.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el texto del procedimiento.
     * @return texto del procedimiento.
     */
    public String getProcedimiento() {
        return procedimiento;
    }

    /**
     * Establece el texto del procedimiento.
     * @param procedimiento texto del procedimiento.
     */
    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    /**
     * Obtiene el nivel de seguridad al que aplica el procedimiento.
     * @return nivel de seguridad al que aplica el procedimiento.
     */
    public TipoNivelSeguridad getNivel() {
        return nivel;
    }

    /**
     * Estavlece el nivel de seguridad al que aplica el procedimiento.
     * @param nivel nivel de seguridad al que aplica el procedimiento.
     */
    public void setNivel(TipoNivelSeguridad nivel) {
        this.nivel = nivel;
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
        if (!(object instanceof ProcedimientoDisponible)) {
            return false;
        }
        ProcedimientoDisponible other = (ProcedimientoDisponible) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.lopd.ProcedimientosEstandar[ id=" + id + " ]";
    }
    
}
