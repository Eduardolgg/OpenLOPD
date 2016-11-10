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

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Clave primaria de las facturas.
 * @author Eduardo L. García Glez.
 * @version 0.0.0
 */
@Embeddable
public class ContadorFacturaPK implements Serializable {
    private static final long serialVersionUID = 1L;
    
    //<editor-fold defaultstate="collapsed" desc="Properties">
    
    @Column(name = "serie", length = 5)
    private String serie;
    @Column(name = "empresa_id", length = 37)
    private String empresa;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public ContadorFacturaPK() {
    }
    
    public ContadorFacturaPK(String serie, String empresa) {
        this.serie = serie;
        this.empresa = empresa;
    }
    
    public ContadorFacturaPK(String empresa) {
        this.empresa = empresa;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Section Get/Set">
    public String getEmpresa() {
        return empresa;
    }
    
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }
    
    public String getSerie() {
        return serie;
    }
    
    public void setSerie(String serie) {
        this.serie = serie;
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
        final ContadorFacturaPK other = (ContadorFacturaPK) obj;
        
        if ((this.serie == null) ? (other.serie != null) : !this.serie.equals(other.serie)) {
            return false;
        }
        if (this.empresa != other.empresa && (this.empresa == null || !this.empresa.equals(other.empresa))) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        
        hash = 79 * hash + (this.serie != null ? this.serie.hashCode() : 0);
        hash = 79 * hash + (this.empresa != null ? this.empresa.hashCode() : 0);
        return hash;
    }
    
    @Override
    public String toString() {
        return "ContadorFacturaPK{serie=" + serie + ", empresa=" + empresa + '}';
    }
    //</editor-fold>
}
