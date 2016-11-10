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
 * Finalidades: Tipificación correspondiente a la finalidad y usos previstos
 *
 * @author Eduaro L. García Glez.
 * @version 0.0.0
 */
public enum DestCesiones {
    destCesiones01, destCesiones02, destCesiones03, 
    destCesiones04, destCesiones05, destCesiones06, destCesiones07, 
    destCesiones08, destCesiones09, destCesiones10, destCesiones11, 
    destCesiones12, destCesiones13, destCesiones14, destCesiones15, 
    destCesiones16, destCesiones17, destCesiones18, destCesiones19, 
    destCesiones20, destCesiones21;
    
     public static String getText(String id) {

        switch (id) {
            case "destCesiones01": return "ORGANIZACIONES O PERSONAS DIRECTAMENTE RELACIONADAS CON EL RESPONSABLE";
            case "destCesiones02": return "ORGANISMOS DE LA SEGURIDAD SOCIAL";
            case "destCesiones03": return "REGISTROS PÚBLICOS";
            case "destCesiones04": return "COLEGIOS PROFESIONALES";
            case "destCesiones05": return "ADMINISTRACIÓN TRIBUTARIA";
            case "destCesiones06": return "OTROS ÓRGANOS DE LA AMINISTRACIÓN PÚBLICA";
            case "destCesiones07": return "COMISIÓN NACIONAL DEL MERCADO DE VALORES";
            case "destCesiones08": return "COMISIÓN NACIONAL DEL JUEGO";
            case "destCesiones09": return "NOTARIOS Y PROCURADORES";
            case "destCesiones10": return "FUERZAS Y CUERPOS DE SEGURIDAD";
            case "destCesiones11": return "ORGANISMOS DE LA UNIÓN EUROPEA";
            case "destCesiones12": return "ENTIDADES DEDICADAS AL CUMPLIMIENTO O INCUMPLIMIENTO DE OBLIGACIONES DINERARIAS";
            case "destCesiones13": return "BANCOS, CAJAS DE AHORROS Y CAJAS RURALES";
            case "destCesiones14": return "ENTIDADES ASEGURADORAS";
            case "destCesiones15": return "OTRAS ENTIDADES FINANCIERAS";
            case "destCesiones16": return "ENTIDADES SANITARIAS";
            case "destCesiones17": return "PRESTACIONES DE SERVICIOS DE TELECOMUNICACIONES";
            case "destCesiones18": return "EMPRESAS DEDICADAS A PUBLICIDAD O MARKETING DIRECTO";
            case "destCesiones19": return "ASOCIACIONES Y ORGANIZACIONES SIN ÁNIMO DE LUCRO";
            case "destCesiones20": return "SINDICATOS Y JUNTAS DE PERSONAL";
            case "destCesiones21": return "ADMINISTRACIÓN PÚBLICA CON COMPETENCIA EN LA MATERIA";
        }
        return null;
    }   
    
}
