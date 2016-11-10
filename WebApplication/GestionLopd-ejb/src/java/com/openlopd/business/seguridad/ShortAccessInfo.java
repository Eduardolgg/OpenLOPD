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

package com.openlopd.business.seguridad;

import java.io.Serializable;

/**
 * Información de usuario y contraseña.
 *
 * Se prevee utilizar este objeto principalmente para guardar los datos iniciales
 * del usuario por lo que en un principio la clave no va encriptada.
 *
 * @author Eduardo L. García Glez.
 * @version 1.0.0 11 de feb de 2011
 */
public class ShortAccessInfo implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="Section Properties">
    private String usuario;
    private String clave;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructores">
    /**
     * Constructor por defecto.
     */
    public ShortAccessInfo() {
        usuario = null;
        clave = null;
    }

    /**
     * Inicializa todos los parámetros
     *
     * @param usuario Identificador del usuario.
     * @param clave Clave del usuario.
     */
    public ShortAccessInfo(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Section GetSet">
    /**
     * Obtiene el valor de la clave.
     * @return Clave almacenada.
     */
    public String getClave() {
        return clave;
    }

    /**
     * Establece el valor de la clave.
     * @param clave Valor de la clave.
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * Establece el id de usuario.
     * @return Identificador del usuario.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Establece el nombre del usuario.
     * @param usuario Valor del nombre del usuario.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Default Methods">
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ShortAccessInfo other = (ShortAccessInfo) obj;
        if ((this.usuario == null) ? (other.usuario != null) : !this.usuario.equals(other.usuario)) {
            return false;
        }
        if ((this.clave == null) ? (other.clave != null) : !this.clave.equals(other.clave)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.usuario != null ? this.usuario.hashCode() : 0);
        hash = 89 * hash + (this.clave != null ? this.clave.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "ShortAccessInfo{" + "usuario=" + usuario + "clave=" + clave + '}';
    }
    // </editor-fold>
}
