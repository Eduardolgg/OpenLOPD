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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Eduardo L. García Glez.
 */
public class ExtractEditIDTest {
    
    public ExtractEditIDTest() {
    }

    /**
     * Test de un "Edit id" con html
     */
    @Test
    public void testGetLinkedId() {
        String test = "<a href=\"/OpenLopd/download?file=97857a49-be1f-410a-a4bb-30fbb275adf1\"><img src=\"/OpenLopd/images/odf.png\" /></a>";
        String result = ExtractEditID.getId(test);
        String expResult = "97857a49-be1f-410a-a4bb-30fbb275adf1";
        assertEquals(result, expResult);
    }
    
    /**
     * Test de un id sin html.
     */
    @Test
    public void testGetId() {
        String test = "7ce12334-d20d-491f-9207-18ef606844ae";
        String result = ExtractEditID.getId(test);
        String expResult = "7ce12334-d20d-491f-9207-18ef606844ae";
        assertEquals(result, expResult);
    }
}
