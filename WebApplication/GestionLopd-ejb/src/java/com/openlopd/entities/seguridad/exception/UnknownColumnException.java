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

package com.openlopd.entities.seguridad.exception;

/**
 * Excepción que se emite cuando no se localiza una columna por parte de las
 * clases que utilizan sistemas que permiten localizar columnas de las
 * entidades por su nombre.
 *
 * @author Edurado L. García Glez.
 * @version 1.0.0
 * Fecha 30 de enero de 2011
 */
public class UnknownColumnException extends Exception {

    /**
     * Creates a new instance of <code>UnknownColumnException</code> without detail message.
     */
    public UnknownColumnException() {
    }


    /**
     * Constructs an instance of <code>UnknownColumnException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public UnknownColumnException(String msg) {
        super(msg);
    }
}
