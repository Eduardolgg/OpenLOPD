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

package com.openlopd.entities.facturacion;

import com.openlopd.entities.empresas.Empresa;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Contador de facturas de las distintas empresas.
 *
 * Cada empresa tiene asignado un campo encargado de almacenar el número de
 * factura actual y otro para el contador.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0
 */
@Entity
@Table(name = "contadores_facturas", schema = "public")
@NamedQueries ({
    @NamedQuery (name = "ContadorFactura.findUnLock", query = ""
        + "SELECT c FROM ContadorFactura c WHERE c.lock = FALSE")
})
public class ContadorFactura implements Serializable {

    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="Properties">
    @EmbeddedId
    protected ContadorFacturaPK contadorFacturaPK;
    @Column (name = "numero", nullable = false)
    private Long numero;
    @Column (name = "lock", nullable = false)
    private boolean lock;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public ContadorFactura() {
        numero = 0L;
        lock = false;
    }
    
    public ContadorFactura(Empresa empresa) {
        contadorFacturaPK = new ContadorFacturaPK("0", empresa.getIdEmpresa());
        numero = 0L;
        lock = false;        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Section Get/Set">
    public ContadorFacturaPK getContadorFacturaPK() {
        return contadorFacturaPK;
    }

    public void setContadorFacturaPK(ContadorFacturaPK contadorFacturaPK) {
        this.contadorFacturaPK = contadorFacturaPK;
    }
    
    public Long getNumero() {
        return numero;
    }
    
    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Default methods">
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ContadorFactura other = (ContadorFactura) obj;
        if (this.contadorFacturaPK != other.contadorFacturaPK && (this.contadorFacturaPK == null || !this.contadorFacturaPK.equals(other.contadorFacturaPK))) {
            return false;
        }
        if (this.lock != other.lock) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.contadorFacturaPK != null ? this.contadorFacturaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "ContadorFactura{" + "contadorFacturaPK=" + contadorFacturaPK + ", lock=" + lock + '}';
    }
    //</editor-fold>
}
