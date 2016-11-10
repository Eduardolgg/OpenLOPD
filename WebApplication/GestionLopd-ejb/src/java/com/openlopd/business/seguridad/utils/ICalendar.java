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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Eduardo L. García Glez.
 */
@Deprecated
public class ICalendar {

    GregorianCalendar calendar;

    public ICalendar() {
        calendar = new GregorianCalendar();
    }

    public static Long addYear() {
        GregorianCalendar c = new GregorianCalendar();
        return ICalendar.add(c, GregorianCalendar.YEAR, 1);
    }

    public static Long addYear(Date d) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTimeInMillis(d.getTime());        
        return ICalendar.add(c, GregorianCalendar.YEAR, 1);
    }

    public Date addMes(Date d) {
        //TODO: implementar;
        return d;
    }

    public static Long addMin(int mins) {
        GregorianCalendar c = new GregorianCalendar();
        return ICalendar.add(c, GregorianCalendar.MINUTE, mins);
    }

    protected static Long add(GregorianCalendar calendar, int field, int amount) {
        calendar.add(field, amount);
        return calendar.getTimeInMillis();
    }

    public static Long stringToDate(String strDate) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = (Date) formatter.parse(strDate);
        return date.getTime();
    }
    
    public static Long stringToDateTime(String strDate) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy kk:mm");
        Date date = (Date) formatter.parse(strDate);
        return date.getTime();
    }
}
