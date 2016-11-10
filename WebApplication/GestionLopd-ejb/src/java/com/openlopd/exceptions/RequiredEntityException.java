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
 * Esta excepcitón debe lanzarse cuando se intenta eliminar un entity
 * que es necesario para que el sistema funcione correctamente.
 * @author Eduardo L. García Glez.
 */
public class RequiredEntityException extends Exception {

    /**
     * Creates a new instance of
     * <code>RequiredEntityException</code> without detail message.
     */
    public RequiredEntityException() {
    }

    /**
     * Constructs an instance of
     * <code>RequiredEntityException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public RequiredEntityException(String msg) {
        super(msg);
    }
}
