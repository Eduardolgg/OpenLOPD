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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Gestiona las peticiones de cambios de contraseñas.
 * @author Eduardo L. García Glez.
 */
@Entity
public class PassRecovery implements Serializable {
    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="Private properties">
    @Id
    @Column (length = 37)
    private String id;
    @Column(name = "idusuario", length = 255, nullable = false) 
    private String idUsuario;
    @Column(name = "expdate", nullable = false)
    private Long expDate;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     * Constructor por defecto.
     */
    public PassRecovery() {
    }
    
    /**
     * Inicializa el Objeto a través de la clave primaria.
     * @param id Clave primaria.
     */
    public PassRecovery(String id) {
        this.id = id;
    }
    
    /**
     * Inicializa todos los campos obligatorios.
     * @param id Clave primarias.
     * @param empresaID Identificacor único de la empresa.
     * @param expDate fecha de expiración del cambio.
     */
    public PassRecovery(String id, String idUsuario, Long expDate) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.expDate = expDate;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Section GetSet">
    /**
     * Obtiene la clave primaria.
     * @return clave primaria.
     */
    public String getId() {
        return id;
    }
    
    /**
     * Establece la clave primaria.
     * @param id clave primaria.
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Obtiene el indentificador único del usuario
     * @return identificador único del usuario.
     */
    public String getIdUsuario() {
        return idUsuario;
    }
    
    /**
     * Establece el identificador único del usuario.
     * @param idUsuario
     */
    public void setidUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    /**
     * Obtiene la fecha de expiración de la posibilidad del cambio.
     * @return fecha de expiración del cambio.
     */
    public Long getExpDate() {
        return expDate;
    }
    
    /**
     * Establece la fecha de expiración del cambio.
     * @param expDate fecha de expiración del cambio.
     */
    public void setExpDate(Long expDate) {
        this.expDate = expDate;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Defaulth methods">
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PassRecovery)) {
            return false;
        }
        PassRecovery other = (PassRecovery) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "com.openlopd.entities.seguridad.PassRecovery[ id=" + id + " ]";
    }
    //</editor-fold>
    
}
