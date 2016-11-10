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

package com.openlopd.common.localizacion.business;

import com.openlopd.common.localizacion.business.Localizacion;
import com.openlopd.common.localizacion.entities.Provincia;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Eduardo L. García Glez.
 */
public class LocalizacionTest {

    public LocalizacionTest() {
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
     * Test of getProvincias method, of class Localizacion.
     */
    @Test
    public void testGetProvincias() throws Exception {
        System.out.println("getProvincias");
        Localizacion instance = (Localizacion)javax.ejb.embeddable.EJBContainer.createEJBContainer().getContext().lookup("java:global/classes/Localizacion");
        List expResult = null;
        List result = instance.getProvincias();
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getLocalidades method, of class Localizacion.
     */
    @Test
    public void testGetLocalidades_Provincia() throws Exception {
        System.out.println("getLocalidades");
        Provincia provincia = null;
        Localizacion instance = (Localizacion)javax.ejb.embeddable.EJBContainer.createEJBContainer().getContext().lookup("java:global/classes/Localizacion");
        List expResult = null;
        List result = instance.getLocalidades(provincia);
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getLocalidades method, of class Localizacion.
     */
    @Test
    public void testGetLocalidades_String() throws Exception {
        System.out.println("getLocalidades");
        String idProvincia = "";
        Localizacion instance = (Localizacion)javax.ejb.embeddable.EJBContainer.createEJBContainer().getContext().lookup("java:global/classes/Localizacion");
        List expResult = null;
        List result = instance.getLocalidades(idProvincia);
//        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

}