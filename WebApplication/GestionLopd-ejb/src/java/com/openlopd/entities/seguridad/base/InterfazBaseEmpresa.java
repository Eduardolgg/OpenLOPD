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

package com.openlopd.entities.seguridad.base;

/**
 * Interfaz que almacena los datos básicos para  la identificación de una
 * empresa y obtener su importancia dentro del conjunto de epresas.
 * @author Eduardo L. García Glez.
 * @version 1.0.0
 * Fecha 06 de feb d 2011
 */
public interface InterfazBaseEmpresa {
    String getIdEmpresa();
    String getIdContrato();
    Integer getRank();
}
