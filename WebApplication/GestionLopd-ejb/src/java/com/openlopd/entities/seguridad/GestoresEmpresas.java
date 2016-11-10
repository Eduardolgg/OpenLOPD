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
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Se encarga de mantener las relacione entre los gestores y las empresas
 * que pueden gestionar.
 * @author Eduardo L. García Glez.
 * Fecha 09 de ene de 2011
 * @version 1.0.3
 * Modificaciones:
 *    24 de enero de 2011 Se añaden el estandar de clave primaria y NamedQuerys.
 *    05 de feb de 2011 modificadas querys para adaptarlas a las necesidades del login.
 *    06 de feb de 2011 Se extiende interfaz InterfazBaseEmpresa para compatibilidad en
 *    la gestión de permisos.
 */
@Entity
@Table (name = "gestores_empresas")
@NamedQueries({
    @NamedQuery(name = "GestoresEmpresas.findAll", query = "SELECT s FROM GestoresEmpresas s"),
    @NamedQuery(name = "GestoresEmpresas.findByCifGestor", query = "SELECT s FROM GestoresEmpresas s WHERE s.gestoresEmpresasPK.cifGestor = :cifGestor"),
    @NamedQuery(name = "GestoresEmpresas.findByidEmpresa", query = "SELECT s FROM GestoresEmpresas s WHERE s.gestoresEmpresasPK.idEmpresa = :idEmpresa"),
    @NamedQuery(name = "GestoresEmpresas.findByidContrato", query = "SELECT s FROM GestoresEmpresas s WHERE s.idContrato = :idContrato"),
    @NamedQuery(name = "GestoresEmpresas.findByrank", query = "SELECT s FROM GestoresEmpresas s WHERE s.rank = :rank ORDER BY s.rank ASC"),
    @NamedQuery(name = "GestoresEmpresas.findEmpresaGestionada", query = ""
        + "SELECT e FROM GestoresEmpresas e "
        + "WHERE e.gestoresEmpresasPK.idEmpresa = :idEmpresaGestionada "
        + "  AND e.gestoresEmpresasPK.cifGestor = :cifGestor")
})
public class GestoresEmpresas implements Serializable, InterfazBaseEmpresa {
    // <editor-fold defaultstate="expanded" desc="Section Properties">
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    private GestoresEmpresasPK gestoresEmpresasPK;
    @Column (length = 37)
    private String idContrato;
    private Integer rank;
    @Column(name = "borrado", nullable = true)
    private Long borrado;
    @Column(name = "borrado_por", nullable = true, length = 25)
    private String borradoPor;
    @Column(name = "active", nullable = true)
    private boolean active;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructores">
    /**
     * Constructor por defecto.
     */
    public GestoresEmpresas() {
        this.active = true;
        this.borrado = null;
        this.borradoPor = null;
    }

    /**
     * Inicializa la entidad a través de un objeto de clave primaria.
     * @param gestoresEmpresasPK Clave primaria a asignar.
     */
    public GestoresEmpresas(GestoresEmpresasPK gestoresEmpresasPK) {
        this.gestoresEmpresasPK = gestoresEmpresasPK;
    }

    /**
     * Inicializa la entidad a través de los valores de la clave primaria.
     * @param cifGestor CIF/NIF de la empresa/autónomo gestora/gestor.
     * @param idEmpresa Idenificador único de la empresa gestionada.
     */
    public GestoresEmpresas(String cifGestor, String idEmpresa){
        this.gestoresEmpresasPK = new GestoresEmpresasPK(cifGestor, idEmpresa);
    }

    /**
     * Inicializa todos las propiedades de la entidad.
     * @param gestoresEmpresasPK Clave primaria a asignar.
     * @param idContrato Identificador único del contrato de la subEmpresa o empresa gestionada.
     * @param rank Importancia de la subEmpresa en el conjunto de subEmpresas.
     */
    public GestoresEmpresas(GestoresEmpresasPK gestoresEmpresasPK, String idContrato, Integer rank) {
        this.gestoresEmpresasPK = gestoresEmpresasPK;
        this.idContrato = idContrato;
        this.rank = rank;
    }

    /**
     * Inicializa todos las propiedades de la entidad.
     * @param cifGestor CIF/NIF de la empresa/autónomo gestora/gestor.
     * @param idEmpresa Idenificador único de la empresa gestionada.
     * @param idContrato Identificador único del contrato de la subEmpresa o empresa gestionada.
     * @param rank Importancia de la subEmpresa en el conjunto de subEmpresas.
     */
    public GestoresEmpresas(String cifGestor, String idEmpresa, String idContrato, Integer rank) {
        this.gestoresEmpresasPK = new GestoresEmpresasPK(cifGestor, idEmpresa);
        this.idContrato = idContrato;
        this.rank = rank;
    }
    // </editor-fold>

    // <editor-fold defaultstate="expanded" desc="Secction GetSet">
    /**
     * Obtiene la clave primaria de la entidad.
     * @return Clave primaria de la entidad.
     */
    public GestoresEmpresasPK getGestoresEmpresasPK() {
        return gestoresEmpresasPK;
    }

    /**
     * Establece la clave primaria de la entidad.
     * @param gestoresEmpresasPK Clave primaria de la entidad.
     */
    public void setGruposSubEmpresaPK(GestoresEmpresasPK gestoresEmpresasPK) {
        this.gestoresEmpresasPK = gestoresEmpresasPK;
    }

    /**
     * Establece el identificador único de la empresa.
     *
     * Hay que tener en cuenta que IdEmpresa forma parte de la clave primaria
     * de esta entidad y que por tando para la gestión normal de la clave
     * primaria es necesario el uso de <code>gestoresEmpresasPK</code>, este
     * método se utilizará cuando se esté enmascarando el objeto a través
     * de la interfáz <code>InterfazBaseEmpresa</code>.
     *
     * @return Idetificador único de la empresa.
     */
    @Override
    public String getIdEmpresa() {
        return this.gestoresEmpresasPK.getIdEmpresa();
    }

    /**
     * Obtiene el identificador único del contrato.
     *
     * Se refiere el identificador del contrato de la empresa gestionada.
     * @return Identificador único del contrato.
     */
    @Override
    public String getIdContrato() {
        return idContrato;
    }

    /**
     * Establece el identificador único del contrato.
     *
     * Se refiere el identificador del contrato de la empresa gestionada.
     * @param idContrato Identificador único del contrato.
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
    }
    
    public Long getBorrado() {
        return borrado;
    }

    public void setBorrado(Long borrado) {
        this.borrado = borrado;
    }

    public String getBorradoPor() {
        return borradoPor;
    }

    public void setBorradoPor(String borradoPor) {
        this.borradoPor = borradoPor;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Default Methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gestoresEmpresasPK != null ? gestoresEmpresasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GestoresEmpresas)) {
            return false;
        }
        GestoresEmpresas other = (GestoresEmpresas) object;
        if ((this.gestoresEmpresasPK == null && other.gestoresEmpresasPK != null) || (this.gestoresEmpresasPK != null && !this.gestoresEmpresasPK.equals(other.gestoresEmpresasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.seguridad.GestoresEmpresas[gestoresEmpresasPK=" + gestoresEmpresasPK + "]";
    }
    // </editor-fold>
}
