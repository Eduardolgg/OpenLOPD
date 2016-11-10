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

package com.openlopd.agpd.nota.tablascomunes;

/**
 * Comunes en ambas titularidades
 * 
 * Otros datos tipificados.
 * 
 * @author Eduardo L. García Glez.
 */
public enum OtrosDatosTipificados {
    otrosDatosTipificados01("01"),
    otrosDatosTipificados02("02"),
    otrosDatosTipificados03("03"),
    otrosDatosTipificados04("04"),
    otrosDatosTipificados05("05"),
    otrosDatosTipificados06("06"),
    otrosDatosTipificados07("07");
    
    private final String id;

    OtrosDatosTipificados(String id) {
        this.id = id;
    }

    public String getValue() {
        return id;
    } 
    
    public static String getText(String id) {
        switch (id) {
            case "01": return "CARACTERÍSTICAS PERSONALES";
            case "02": return "CIRCUNSTANCIAS SOCIALES";
            case "03": return "ACADEMICOS Y PROFESIONALES";
            case "04": return "DETALLE DEL EMPLEO";
            case "05": return "INFORMACIÓN COMERCIAL";
            case "06": return "ECONOMICOS FINANCIEROS Y DE SEGUROS";
            case "07": return "TRANSACCIONES DE BIENES Y SERVICIOS";
        }
        return null;
    }
    
    public static String getTextByDesc(String id) {
        switch (id) {
            case "otrosDatosTipificados01": return getText("01");
            case "otrosDatosTipificados02": return getText("02");
            case "otrosDatosTipificados03": return getText("03");
            case "otrosDatosTipificados04": return getText("04");
            case "otrosDatosTipificados05": return getText("05");
            case "otrosDatosTipificados06": return getText("06");
            case "otrosDatosTipificados07": return getText("07");
        }
        return null;
    }
}
