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

import com.openlopd.business.seguridad.utils.crypt.TextCrypt;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Date;

/**
 * Permite el cifrado de textos de forma unilateral.
 * 
 * @author Eduardo L. García Glez.
 * @version 1.0.0 09 de feb de 2011
 */
public class TextCryptTest {

    public TextCryptTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of Crypt method, of class TextCrypt.
     */
    @Test
    public void testCrypt() {
        System.out.println("Crypt");

        String algorithm = "MD5";
        int expResult = 32;
        for (int i = 0; i < 500000; i++) {
            String text = (new Date()).toString();
            String result = TextCrypt.Crypt(text, algorithm);
            assertEquals(expResult, result.length());

            Pattern p = Pattern.compile("[ABCDEF0-9]*");
            Matcher m = p.matcher(result);
            if (!m.matches()) {
                fail("El formato no es MD5 [" + result + "]");
            }
        }
//        System.out.println(result);
    }
}
