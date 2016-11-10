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

package com.openlopd.entities.interfaz;

import com.openlopd.entities.empresas.Empresa;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Recoge la información de operaciones de LOPD, a través de esta entidad se
 * puede saber si la operación LOPD está realizada.
 *
 * @author Eduardo L. García GLez.
 * @version 0.0.0 11 de dic de 2012
 */
@Entity
@Table(name = "operations", schema = "application")
@NamedQueries({
    @NamedQuery(name = "OperacionLopd.findOpsByEmpresa", query = ""
    + "SELECT l FROM OperacionLopd l JOIN l.operacion ll "
    + "WHERE l.empresa = :empresa "
    + "  AND ll.active = :active "
    + "  AND ll.nivel <= :nivel "
    + "  AND ll.operacion IS NOT NULL "
    + "ORDER BY ll.prioridad DESC"),
    @NamedQuery(name = "OperacionLopd.findOpsRecomendada", query = ""
    + "SELECT l FROM OperacionLopd l JOIN l.operacion ll "
    + "WHERE l.empresa = :empresa "
    + "  AND ll.active = :active "
    + "  AND ll.nivel <= :nivel "
    + "  AND ll.operacion IS NOT NULL "
    + "  AND l.estado <> :estado "
    + "ORDER BY ll.prioridad DESC"),
    @NamedQuery(name = "OperacionLopd.findOpByLink", query = ""
    + "SELECT count(l) FROM OperacionLopd l JOIN l.operacion ll "
    + "WHERE l.empresa = :empresa "
    + "  AND ll = :link"),
    @NamedQuery(name = "OperacionLopd.findOpToUpdate", query = ""
    + "SELECT l FROM OperacionLopd l JOIN l.operacion ll "
    + "WHERE l.empresa = :empresa "
    + "  AND ll.codOperacion = :codOperacion"),
    @NamedQuery(name = "OperacionLopd.eliminarOperacionesAntiguas", query = ""
        + "DELETE FROM OperacionLopd o "
        + "WHERE o.operacion.id NOT IN "
        + "(SELECT l.id FROM LinkList l WHERE l.codOperacion IS NOT NULL)")
})
public class OperacionLopd implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    @ManyToOne(optional = false)
    private LinkList operacion;
//    @Column (name = "estado", nullable = true, length = 9)
//    @Enumerated(EnumType.STRING)    
    private Boolean estado;
    @ManyToOne(optional = false)
    private Empresa empresa;

    public OperacionLopd() {
    }

    public OperacionLopd(String id, LinkList operacion, Empresa empresa) {
        this.id = id;
        this.operacion = operacion;
        this.empresa = empresa;
        this.estado = false;
    }  

    public OperacionLopd(TipoOperacion operacion, Boolean estado) {
        this.operacion = new LinkList();
        this.operacion.setCodOperacion(operacion);
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LinkList getOperacion() {
        return operacion;
    }

    public void setOperacion(LinkList operacion) {
        this.operacion = operacion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
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
        if (!(object instanceof OperacionLopd)) {
            return false;
        }
        OperacionLopd other = (OperacionLopd) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.openlopd.entities.interfaz.OperacionLopd[ id=" + id + " ]";
    }
}
