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

package com.openlopd.business;

import com.openlopd.business.FilterQuery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Verificación de la clase.
 *
 * @author Eduardo L. García Glez.
 */
public class FilterQueryTest {

    public FilterQueryTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getTextSearch method, of class FilterQuery.
     */
    @Test
    public void testGetTextSearch() {
        System.out.println("getTextSearch null");

        String textSearch = null;
        FilterQuery instance = new FilterQuery(textSearch);
        // Verificación del contenido de la clase.
        assertEquals(null, instance.getTextSearch());
        assertEquals("", instance.getCommand());
        assertEquals(null, instance.getFilter());
    }

    @Test
    public void testGetTextSearch1() {
        System.out.println("getTextSearch empty");
        String textSearch = "";
        FilterQuery instance = new FilterQuery(textSearch);
        // Verificación del contenido de la clase.
        assertEquals("", instance.getTextSearch());
        assertEquals("", instance.getCommand());
        assertEquals("", instance.getFilter());
    }

    @Test
    public void testGetTextSearch2() {
        System.out.println("getTextSearch cmd");
        String textSearch = "cmd";
        FilterQuery instance = new FilterQuery(textSearch);
        // Verificación del contenido de la clase.
        assertEquals("cmd", instance.getTextSearch());
        assertEquals("", instance.getCommand());
        assertEquals("cmd", instance.getFilter());
    }

    @Test
    public void testGetTextSearch3() {
        System.out.println("getTextSearch cmd:");

        String textSearch = "cmd:";
        FilterQuery instance = new FilterQuery(textSearch);
        // Verificación del contenido de la clase.
        assertEquals("cmd:", instance.getTextSearch());
        assertEquals("", instance.getCommand());
        assertEquals("", instance.getFilter());
    }

    @Test
    public void testGetTextSearch4() {
        System.out.println("getTextSearch cmd:miComando");

        String textSearch = "cmd:miComando";
        FilterQuery instance = new FilterQuery(textSearch);
        // Verificación del contenido de la clase.
        assertEquals("cmd:miComando", instance.getTextSearch());
        assertEquals("miComando", instance.getCommand());
        assertEquals("", instance.getFilter());
    }

    @Test
    public void testGetTextSearch5() {
        System.out.println("getTextSearch cmd:miComando_");

        String textSearch = "cmd:miComando ";
        FilterQuery instance = new FilterQuery(textSearch);
        // Verificación del contenido de la clase.
        assertEquals("cmd:miComando ", instance.getTextSearch());
        assertEquals("miComando", instance.getCommand());
        assertEquals("", instance.getFilter());
    }

    @Test
    public void testGetTextSearch6() {
        System.out.println("getTextSearch cmd:miComando filtro de búsqueda");

        String textSearch = "cmd:miComando filtro de búsqueda";
        FilterQuery instance = new FilterQuery(textSearch);
        // Verificación del contenido de la clase.
        assertEquals("cmd:miComando filtro de búsqueda", instance.getTextSearch());
        assertEquals("miComando", instance.getCommand());
        assertEquals("filtro de búsqueda", instance.getFilter());

    }
}