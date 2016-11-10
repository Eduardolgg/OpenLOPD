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

package com.utils.data;

import com.elgg.utils.Calendar.ManejadorFechas;
import java.sql.Timestamp;
import java.util.Date;
import java.util.TimeZone;

/**
 * Proporciona de los métodos de conversión de fechas a los entities.
 * @author Eduardo L. García Glez.
 */
public class DateTimeEntity {
    
    public DateTimeEntity() {

    }
    
    public String getDateTime(Object dateTime, TimeZone timeZone) {
            
        if (dateTime instanceof Long && (Long) dateTime > 0) {
            return ManejadorFechas.getFechaHora(new Date((Long) dateTime), timeZone);
        } else if (dateTime instanceof Timestamp) {            
            return ManejadorFechas.getFechaHora((Timestamp) dateTime, timeZone);
        } else {
            return "";
        }
    }
}
