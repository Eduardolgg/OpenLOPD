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

package com.openlopd.entities.seguridad.utils.primarykey;

import com.openlopd.entities.seguridad.utils.primarykey.GenKey;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test de Generación de UUID que se utilizan como claves primarias.
 * 
 * @author Eduardo L. García Glez.
 * @version 1.0.0
 * Fecha 09 de feb de 2011
 */
public class GenKeyTest {

    public GenKeyTest() {
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
     * Test of getKey method, of class GenKey.
     */
    @Test
    public void testGetKey() {
        ArrayList l = new ArrayList();
        System.out.println("getKey");
        String result;
        for (int i = 0; i < 50000; i++) {
            result = (new GenKey()).getKey();
            if (result.length() != 36)
                fail("Longitud del Identicador único no válida es [" + result.length() + "]");
            if (l.contains(result))
                fail("Se encontró calve repetida.");
            l.add(result);
        }

        GenKey instance = new GenKey();
        GenKey expResult = instance;
        assertEquals("La instancia debió ser igual a si misma", expResult, instance);
        assertFalse("La instancia no debió ser igual a un  nuevo objeto", instance.equals(new GenKey()));
    }

}