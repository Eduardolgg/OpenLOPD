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

package com.jkingii.datatables;

import java.util.Objects;
import java.util.TimeZone;

/**
 * Configuración de las respuestas para DTResponse.
 * @author Eduardo L. García Glez.
 * @version 0.0.0
 */
public class ResponseConfig {
    TimeZone timeZone;
    
    public ResponseConfig() {
        timeZone = null;
    }
    
    /**
     * Obtiene la zona horaria para la que se emite la respuesta.
     * @return Zona horaria.
     */
    public TimeZone getTimeZone() {
        return this.timeZone;
    }
    
    /**
     * Establece  la zona horaria para la que se emite la respuesta.
     * @param timeZone Zona horaria.
     */
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.timeZone);
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
        final ResponseConfig other = (ResponseConfig) obj;
        if (!Objects.equals(this.timeZone, other.timeZone)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ResponseConfig{" + "timeZone=" + timeZone + '}';
    }
}
