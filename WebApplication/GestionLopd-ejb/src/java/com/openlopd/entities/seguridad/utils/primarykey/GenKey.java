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

package com.openlopd.entities.seguridad.utils.primarykey;

import java.io.Serializable;
import java.util.UUID;

/**
 * Genera claves únicas.
 *
 * @author Eduardo L. García Glez.
 * @version 1.0.1
 * Fecha 09 de feb de 2011
 */
public class GenKey implements Serializable {
    String key;
    
    /**
     * Constructor por defecto.
     */
    public GenKey() {
        key =  GenKey.newKey();
    }

    /**
     * Obtiene el identificador único generado en formato String
     * @return Identificador único.
     */
    public String getKey() {
        return this.key;
    }
    
    public static String newKey() {
        return UUID.randomUUID().toString();
    }

    // <editor-fold defaultstate="collapsed" desc="Default Methods">
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GenKey other = (GenKey) obj;
        if ((this.key == null) ? (other.key != null) : !this.key.equals(other.key)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.key != null ? this.key.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "GenKey{" + "key=" + key + '}';
    }
    // </editor-fold>
}
