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

package com.openlopd.business.seguridad.utils;

import com.openlopd.business.seguridad.utils.ICalendar;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.*;

/**
 *
 * @author Eduardo L. García Glez.
 */
public class ICalendarTest {
    
    public ICalendarTest() {
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
     * Test of addYear method, of class ICalendar.
     */
    @Test
    public void testAddYear_0args() {
        System.out.println("addYear");
        Long expResult = null;
        Long result = ICalendar.addYear();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addYear method, of class ICalendar.
     */
    @Test
    public void testAddYear_Date() {
        System.out.println("addYear");
        Date d = null;
        ICalendar instance = new ICalendar();
        Date expResult = null;
        Long result = instance.addYear(d);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addMes method, of class ICalendar.
     */
    @Test
    public void testAddMes() {
        System.out.println("addMes");
        Date d = null;
        ICalendar instance = new ICalendar();
        Date expResult = null;
        Date result = instance.addMes(d);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addMin method, of class ICalendar.
     */
    @Test
    public void testAddMin() {
        System.out.println("addMin");
        int mins = 0;
        Long expResult = null;
        Long result = ICalendar.addMin(mins);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stringToDate method, of class ICalendar.
     */
    @Test
    public void testStringToDate() throws Exception {
        System.out.println("stringToDate");
        String strDate = "01/05/2012";
        Long expResult = 1335826800000L;
        Long result = ICalendar.stringToDate(strDate);
        assertEquals(expResult, result);        
    }

    /**
     * Test of stringToDateTime method, of class ICalendar.
     */
    @Test
    public void testStringToDateTime() throws Exception {
        System.out.println("stringToDateTime");
        String strDate = "";
        Long expResult = null;
        Long result = ICalendar.stringToDateTime(strDate);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
