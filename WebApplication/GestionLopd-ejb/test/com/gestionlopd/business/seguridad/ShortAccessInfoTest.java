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

package com.openlopd.business.seguridad;

import com.openlopd.business.seguridad.ShortAccessInfo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Clase para las pruebas de ShortAccessInfo
 * @author Eduardo L. García Glez.
 * @version 1.0.0 16 de feb de 2010
 */
public class ShortAccessInfoTest {

    public ShortAccessInfoTest() {
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
     * Test of getClave method, of class ShortAccessInfo.
     */
    @Test
    public void testGetSetClave() {
        System.out.println("getClave");
        ShortAccessInfo instance = new ShortAccessInfo();
        String expResult = null;
        String result = instance.getClave();
        assertEquals(expResult, result);

        // Se espra que los datos que se introduzcan coincidan
        // sin modificaciones y que no de penda del tamaño ni formato.
        instance.setClave("¿/(·$\"&/((?=??=)(?()*^ÇÑ¨: ;)añlsdkjf'9187325");
        expResult = "¿/(·$\"&/((?=??=)(?()*^ÇÑ¨: ;)añlsdkjf'9187325";
        result = instance.getClave();
        assertEquals(expResult, result);
        
        instance.setUsuario("¿/(·$\"&/((?=??=)(?()*^ÇÑ¨: ;)añlsdkjf'9187325");
        expResult = "¿/(·$\"&/((?=??=)(?()*^ÇÑ¨: ;)añlsdkjf'9187325";
        result = instance.getUsuario();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUsuario method, of class ShortAccessInfo.
     */
    @Test
    public void testGetSetUsuario() {
        System.out.println("getUsuario");
        ShortAccessInfo instance = new ShortAccessInfo();
        String expResult = null;
        String result = instance.getUsuario();
        assertEquals(expResult, result);

        // Se espra que los datos que se introduzcan coincidan
        // sin modificaciones y que no de penda del tamaño ni formato.
        instance.setUsuario("¿/(·$\"&/((?=??=)(?()*^ÇÑ¨: ;)añlsdkjf'9187325");
        expResult = "¿/(·$\"&/((?=??=)(?()*^ÇÑ¨: ;)añlsdkjf'9187325";
        result = instance.getUsuario();
        assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class ShortAccessInfo.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        ShortAccessInfo obj = null;
        ShortAccessInfo instance = new ShortAccessInfo();
        boolean expResult = false;
        boolean result = instance.equals(obj);
        assertEquals(expResult, result);

        // En las siguientes variables se especifican los valore que se tendrán
        // En cuanta para la realización de las pruebas.
        String clavesInstance[] =
        {null, "clave", null, "Clave", "Clave", null,  null,  "clave", "·$&\"?=)(/&"};
        String clavesObj[] =
        {null, null,    null, "Clave", "clave", null,  null,  "clave", "·$&\"?=)(/&"};
        String usuariosInstance[] =
        {null, null,    "user", null,  null,    "Usu", "Usu", "usu",   "·$&\"?=)(/&"};
        String usuariosObj[] =
        {null, null,    null,   null,  null,    "Usu", "usu", "usu",   "·$&\"?=)(/&"};
        boolean expResults[] =
        {true, false,   false,  true,  false,   true,  false, true, true};
        assertEquals(true, clavesInstance.length == clavesObj.length
                        && clavesInstance.length == usuariosInstance.length
                        && clavesInstance.length == usuariosObj.length
                        && clavesInstance.length == expResults.length);
        for (int i = 0; i < clavesInstance.length; i++) {
            instance = new ShortAccessInfo(usuariosInstance[i], clavesInstance[i]);
            obj = new ShortAccessInfo(usuariosObj[i], clavesObj[i]);
            result = instance.equals(obj);
            assertEquals(expResults[i], result);
        }
    }

    /**
     * Test of hashCode method, of class ShortAccessInfo.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        ShortAccessInfo instance;

        String usuarios[] =
        {null, "clave", null, "clave"};
        String claves[] =
        {null, null,    "usu", "usu"};
        boolean expResult[] =
        {true, true,    true,  true};
        int oldValue  = 0;
        for (int i=0; i < usuarios.length; i++) {
            instance = new ShortAccessInfo(usuarios[i], claves[i]);
            assertEquals(expResult[i], oldValue != instance.hashCode());
            oldValue = instance.hashCode();
        }
    }

    /**
     * Test of toString method, of class ShortAccessInfo.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        ShortAccessInfo instance = new ShortAccessInfo("EQRTVAGA", "apodfby");
        boolean expResult = true;
        String result = instance.toString();
        assertEquals(expResult, result.contains("EQRTVAGA") && result.contains("apodfby"));
    }

}