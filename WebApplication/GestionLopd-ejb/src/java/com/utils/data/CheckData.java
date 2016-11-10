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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase encargada de la validación de datos, en la que es necesario la utilización
 * de operaciones que no se encuentran de forma por defecto en sistema base.
 *
 * @author Eduardo L. García Glez.
 * @version 1.0.0 07 de feb de 2011
 */
public class CheckData {
     private static Logger logger = LoggerFactory.getLogger(CheckData.class);
    
     public final static int USER_NAME_MIN_LENGTH = 4;
    /**
     * Valida si un nombre de usuario cumple con las especificaciones.
     * 
     * @param userName Nombre de usuario a validar.
     * return <code>true</code> en caso de cumplir las especificaciones 
     * <code>false</code> en caso contratio.
     */
    public static boolean checkUserName(String userName){
        
        Pattern p;
        Matcher m;
        try {
            if (userName.length() < USER_NAME_MIN_LENGTH)
                return false;
            p = Pattern.compile("[A-ZÑa-zñ]+[A-ZÑa-zñ0-9_\\.]*");
            m = p.matcher(userName);
            return (m.matches() ? true : false);
        } catch (Exception e) {
            logger.warn("Nombre de usuario no válido: [{}]", userName);
            return false;
        }        
    }
}
