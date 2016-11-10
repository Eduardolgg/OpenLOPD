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

package com.openlopd.business.seguridad.utils.crypt;

import com.openlopd.business.seguridad.PoliticaSeguridad;

/**
 * Permite verificar si una clave cumple la política de seguridad.
 *
 * @author Eduardo L. García Glez.
 * @version 1.0.0 10 de feb de 2011
 */
public class PassPolicyCheck {

    /**
     * Verifica si el password recibido cumple las politicas de politica.
     *
     * @param password Pasword a verificar.
     * @param politica Política que debe cumplir el password.
     * @return <code>true</code> si el password cumpla la politica,
     * <code>false</code> en caso contrario.
     */
    public static boolean passwordCheck(String password, PoliticaSeguridad politica) {

        if (password.length() < politica.getLongMinClave()) {
            return false;
        }
        return true;
    }
}
