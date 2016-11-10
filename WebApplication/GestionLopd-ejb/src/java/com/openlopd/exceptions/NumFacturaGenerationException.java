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

package com.openlopd.exceptions;

/**
 * Se emite cuando no es posible imcrementar un contador de facturas.
 * @author Eduardo L. García Glez.
 */
public class NumFacturaGenerationException extends Exception {

    /**
     * Creates a new instance of
     * <code>NumFacturaGenerationException</code> without detail message.
     */
    public NumFacturaGenerationException() {
    }

    /**
     * Constructs an instance of
     * <code>NumFacturaGenerationException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public NumFacturaGenerationException(String msg) {
        super(msg);
    }
}
