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

import com.openlopd.business.seguridad.utils.crypt.PassValidate;
import com.openlopd.business.seguridad.utils.crypt.TextCrypt;
import com.openlopd.business.seguridad.ShortAccessInfo;
import java.util.List;
import com.openlopd.entities.seguridad.Shadow;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Verifica el funcionamientode la clase <code>PassValidate</code>.
 * @author Eduardo L. García Glez.
 * @version 1.0.0 11 de feb de 2011
 */
public class PassValidateTest {

    public PassValidateTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of isValid method, of class PassValidate.
     */
    @Test
    public void testIsValid() {
        System.out.println("isValid");
        ShortAccessInfo a = new ShortAccessInfo("usuario", "clave");
        // La siguiente variable representa passwords válidos.
        String pass[] = {"clave", "CLAVE", "ClAvE", "", "123", "c", "a123",
            "123aA", "ºª\\!|\"@#·$~%&¬/()=?'¿¡*+][^`¨´çÇ{}-_<>", "áéíóú", "ÁÉÍÚÓ",
            "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"};

        for (int i = 0; i < pass.length; i++) {
            Shadow shadow = new Shadow("usuario",
                    TextCrypt.Crypt(pass[i],
                    TextCrypt.algoritmos.MD5.name()),
                    TextCrypt.algoritmos.MD5.name());
            boolean clean = true;
            boolean expResult = true;
            a.setClave(pass[i]);
            boolean result = PassValidate.isValid(a, shadow, clean);
            assertEquals(expResult, result);
            assertEquals(a.getClave(), shadow.getClave());
        }

        for (int i = 0; i < pass.length; i++) {
            Shadow shadow = new Shadow("usuario",
                    TextCrypt.Crypt("Clave",
                    TextCrypt.algoritmos.MD5.name()),
                    TextCrypt.algoritmos.MD5.name());
            boolean clean = false;
            boolean expResult = false;
            a.setClave(pass[i]);
            boolean result = PassValidate.isValid(a, shadow, clean);
            assertEquals(expResult, result);
        }

        Shadow shadow = new Shadow("usuario",
                TextCrypt.Crypt("Clave",
                TextCrypt.algoritmos.MD5.name()),
                TextCrypt.algoritmos.MD5.name());
        boolean clean = false;
        boolean expResult = false;
        a.setClave(null);
        boolean result = PassValidate.isValid(a, shadow, clean);
        assertEquals(expResult, result);
    }
}
