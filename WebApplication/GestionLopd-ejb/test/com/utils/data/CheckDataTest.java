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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Verifica el funcionamiento de la clase CheckData.
 * @author Eduardo L. García Glez.
 * @version 1.0.0 07 de feb de 2011
 */
public class CheckDataTest {

    public CheckDataTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of checkUserName method, of class CheckData.
     */
    @Test
    public void testCheckUserName() {
        System.out.println("checkUserName");
        // Se comprueba si se admite una cadena vacia.
        String userName = "";
        boolean expResult = false;
        boolean result = CheckData.checkUserName(userName);
        assertEquals(expResult, result);

        // Se comprueba si se admite una cadena válida con un tamaño inferior
        // al mínimo exigido.
        userName = "";
        for (int i = 1; i < CheckData.USER_NAME_MIN_LENGTH; i++) {
            userName += "a";
        }
        expResult = false;
        result = CheckData.checkUserName(userName);
        assertEquals(expResult, result);

        // Se comprueba si se admite una cadena de longitud válida con carácteres
        // no válidos.
        userName = "";
        String aUserName[] = {"º","ª","\\","!","|","\"","@","·","#","$","%","&",
        "/","(",")","=","?","'","¿","¡","€","^","`","à","[","+","*","]","¨",
        "ü","á","ç","Ç","{","}",";",",",":","-"};
        for (int i = 0; i < aUserName.length; i++) {
            userName = "aaaa" + aUserName[i];
            expResult = false;
            result = CheckData.checkUserName(userName);
            assertEquals(expResult, result);
        }
        // Se comprueba que no puede empezar por punto.
        userName = ".";
        for (int i = 0; i < CheckData.USER_NAME_MIN_LENGTH; i++) {
            userName += "a";
        }
        expResult = false;
        result = CheckData.checkUserName(userName);
        assertEquals(expResult, result);

        // Se comprueba que no puede empezar por _.
        userName = "_";
        for (int i = 0; i < CheckData.USER_NAME_MIN_LENGTH; i++) {
            userName += "a";
        }
        expResult = false;
        result = CheckData.checkUserName(userName);
        assertEquals(expResult, result);

        // Se comprueba que no puede empezar por número.
        userName = "0";
        for (int i = 0; i < CheckData.USER_NAME_MIN_LENGTH; i++) {
            userName += "a";
        }
        expResult = false;
        result = CheckData.checkUserName(userName);
        assertEquals(expResult, result);

        // Se comprueba si se admite una cadena de longitud y carácteres válidos.
        userName = "aa";
        for (int i = 0; i < CheckData.USER_NAME_MIN_LENGTH; i++) {
            userName += "a";
        }
        expResult = true;
        result = CheckData.checkUserName(userName);
        assertEquals(expResult, result);
        
        // Se comprueba si se admite una cadena de longitud y carácteres válidos.
        userName = "aa._897ñ";
        for (int i = 0; i < CheckData.USER_NAME_MIN_LENGTH; i++) {
            userName += "a";
        }
        expResult = true;
        result = CheckData.checkUserName(userName);
        assertEquals(expResult, result);
    }

}