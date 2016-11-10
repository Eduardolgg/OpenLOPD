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

import com.utils.lang.Hexadecimal;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Permite el cifrado de textos de forma unilateral.
 * 
 * @author Eduardo L. García Glez.
 * @version 1.0.1 19 de mar de 2011
 * Modificaciones:
 *    19 de mar de 2011, Añadido el método String Crypt(String text, algoritmos algorithm)
 */
public class TextCrypt implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(TextCrypt.class);
    
    public enum algoritmos {MD5};
    public static final algoritmos DEFAULT_ALGORITM = algoritmos.MD5;

    /**
     * Cifra un texto con el algoritmo especificado.
     * @param text Texto a cifrar.
     * @param algorithm Algoritmo de cifrado.
     * @return Texto cifrado.
     */
    public static String Crypt(String text, algoritmos algorithm){
        return Crypt(text, algorithm.toString());
    }

    /**
     * Cifra un texto con el algoritmo especificado.
     * @param text Texto a cifrar.
     * @param algorithm Algoritmo de cifrado.
     * @return Texto cifrado.
     */
    public static String Crypt(String text, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] d= md.digest(text.getBytes());
            return Hexadecimal.getHex(md.digest(text.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            logger.error("El algoritmo de encriptación seleccionado [{}] no está"
                    + " soportado.", algorithm);
            return null;
        }
    }
}
