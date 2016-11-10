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

/**
 * Extrae el ID de una entidad del texto de edición proveniente de un
 * DataTable.
 * 
 * @author Eduardo L. García Glez.
 * @version 0.0.0 03 de oct de 2012
 */
public class ExtractEditID {
    
    public static String getId(String htmlID) {
        
        int beginIndex = htmlID.indexOf("file=");
        if (beginIndex < 0) {
            return htmlID;
        }
        int endIndex = htmlID.indexOf("\"", beginIndex + 5);
        return htmlID.substring(beginIndex + 5, endIndex);
    }
}
