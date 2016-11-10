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

import com.openlopd.entities.seguridad.base.InterfazBaseEmpresa;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Clase encargada de gestionar los accesos de los grupos a cada subEmpresa.
 * @author Eduardo L. García Glez.
 * Fecha 23 de enero de 2011
 * @version 1.0.3
 * Modificaciones:
 *    Se sustituye el nombre de la clave primaria de id a gruposSubEmpresaPK
 *    05 de feb de 2011 modificadas querys para adaptarlas a las necesidades del login.
 *    06 de feb de 2011 Se extiende interfaz InterfazBaseEmpresa para compatibilidad en
 *    la gestión de permisos.
 */
@Entity
@Table (name = "grupos_sub_empresa")
@NamedQueries({
    @NamedQuery(name = "GruposSubEmpresa.findAll", query = "SELECT s FROM GruposSubEmpresa s"),
    @NamedQuery(name = "GruposSubEmpresa.findByidGrupo", query = "SELECT s FROM GruposSubEmpresa s WHERE s.gruposSubEmpresaPK.idGrupo = :idGrupo"),
    @NamedQuery(name = "GruposSubEmpresa.findByidEmpresa", query = "SELECT s FROM GruposSubEmpresa s WHERE s.gruposSubEmpresaPK.idEmpresa = :idEmpresa"),
    @NamedQuery(name = "GruposSubEmpresa.findByidContrato", query = "SELECT s FROM GruposSubEmpresa s WHERE s.idContrato = :idContrato"),
    @NamedQuery(name = "GruposSubEmpresa.findByIdUsuario",
            query = "SELECT gse "
            + "FROM "
            + "      GruposSubEmpresa gse, GruposUsuarios g "
            + "WHERE gse.gruposSubEmpresaPK.idGrupo = g.gruposUsuariosPK.idGrupo AND"
            + "   g.gruposUsuariosPK.idUsuario = :idUsuario "
            + "ORDER BY gse.rank ASC")})
public class GruposSubEmpresa implements Serializable, InterfazBaseEmpresa {
    // <editor-fold defaultstate="collapsed" desc="Section Properties">
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private GruposSubEmpresaPK gruposSubEmpresaPK;
    @JoinColumn(name="GruposUsuarios",
                referencedColumnName = "idGrupo",
                nullable = false, insertable = false, updatable = false)
    @Column(nullable = false, length = 37)
    private String idContrato;
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer rank;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructores">
    /**
     * Constructor por defecto.
     */
    public GruposSubEmpresa() {
    }

    /**
     * Constructor que inicializa todos los parámetros del la entidad.
     * @param gruposSubEmpresaPK Clave primaria.
     * @param idContrato Identificador único del contrato.
     * @param rank Importancia de la subEmpresa en el sistema.
     */
    public GruposSubEmpresa(GruposSubEmpresaPK gruposSubEmpresaPK, String idContrato, Integer rank) {
        this.gruposSubEmpresaPK = gruposSubEmpresaPK;
        this.idContrato = idContrato;
        this.rank = rank;
    }

    /**
     * Constructor que inicializa todos los parámetros del la entidad.
     * @param idGrupo Identificador único del grupo al que se asocia la empresa
     * enviada al parámetro <code>ïdEmpresa</code>.
     * @param idEmpresa Identificador único de la empresa a la que puede acceder el
     * grupo <code>idGrupo</grupo>
     * @param idContrato Identificador del contrato de la empresa <code>idEmpresa</code>
     * enviada como parámetro.
     * @param rank Importancia de la subEmpresa en el sistema.
     */
    public GruposSubEmpresa(String idGrupo, String idEmpresa, String idContrato, Integer rank) {
        this.gruposSubEmpresaPK = new GruposSubEmpresaPK(idGrupo, idEmpresa);
        this.idContrato = idContrato;
        this.rank = rank;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Secction GetSet">
    /**
     * Obtiene el id.
     * 
     * @return Debuelve el indentificador único de la entidad.
     */
    public GruposSubEmpresaPK getGruposSubEmpresaPK() {
        return gruposSubEmpresaPK;
    }

    /**
     * Establece el identificador único de la entidad
     * @param gruposSubEmpresaPK Identificador único de la entidad.
     */
    public void setGruposSubEmpresaPK(GruposSubEmpresaPK gruposSubEmpresaPK) {
        this.gruposSubEmpresaPK = gruposSubEmpresaPK;
    }

    /**
     * Establece el identificador único de la empresa.
     *
     * Hay que tener en cuenta que IdEmpresa forma parte de la clave primaria
     * de esta entidad y que por tando para la gestión normal de la clave
     * primaria es necesario el uso de <code>gruposSubEmpresaPK</code>, este
     * método se utilizará cuando se esté enmascarando el objeto a través
     * de la interfáz <code>InterfazBaseEmpresa</code>.
     * 
     * @return Idetificador único de la empresa.
     */
    @Override
    public String getIdEmpresa() {
        return this.gruposSubEmpresaPK.getIdEmpresa();
    }

    /**
     * Obtiene el Identificador único del contrato.
     * 
     * Se trata del identificador único del contrato de la subEmpresa, a través
     * de el se puede acceder a los permisos del contrato. 
     * @see  com.openlopd.entities.seguridad.ContratosPermisos
     * @return Identificador único del contrato.
     */
    @Override
    public String getIdContrato() {
        return idContrato;
    }

    /**
     * Establece el identificador único del contrato.
     * 
     * Se trata del identificador único del contrato de la subEmpresa, a través
     * de el se puede acceder a los permisos del contrato. 
     * @param idContrato
     */
    public void setIdContrato(String idContrato) {
        this.idContrato = idContrato;
    }

    /**
     * Obtiene el rank de la entidad.
     * 
     * El rank indica la importancia de una de las filas de la tabla respecto al resto.
     * @return Rank asociado a la entidad.
     */
    @Override
    public Integer getRank() {
        return rank;
    }

    /**
     * Establece el rank de la entidad.
     * 
     * El rank indica la importancia de una de las filas de la tabla respecto al resto.
     * @param rank Rank a asociar a la entidad.
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Default Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gruposSubEmpresaPK != null ? gruposSubEmpresaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GruposSubEmpresa)) {
            return false;
        }
        GruposSubEmpresa other = (GruposSubEmpresa) object;
        if ((this.gruposSubEmpresaPK == null && other.gruposSubEmpresaPK != null) || (this.gruposSubEmpresaPK != null && !this.gruposSubEmpresaPK.equals(other.gruposSubEmpresaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.seguridad.GruposSubEmpresa[gruposSubEmpresaPK=" + gruposSubEmpresaPK + "]";
    }
    // </editor-fold>

}
