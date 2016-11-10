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
public enum Finalidades {
    finalidades400, finalidades401, finalidades402, finalidades403, 
    finalidades404, finalidades405, finalidades406, finalidades407, 
    finalidades408, finalidades409, finalidades410, finalidades411, 
    finalidades412, finalidades413, finalidades414, finalidades415, 
    finalidades416, finalidades417, finalidades418, finalidades419, 
    finalidades420, finalidades421, finalidades422, finalidades423, 
    finalidades499;
    
     public static String getText(String id) {

        switch (id) {
            case "finalidades400": return "GESTIÓN DE CLIENTES, CONTABLE, FISCAL Y ADMINISTRATIVA";
            case "finalidades401": return "RECURSOS HUMANOS";
            case "finalidades402": return "GESTIÓN DE NÓMINAS";
            case "finalidades403": return "REVENCIÓN DE RIESGOS LABORALES";
            case "finalidades404": return "PRESTACIÓN DE SERVICIOS DE SOLVENCIA PATRIMONIAL Y CRÉDITO";
            case "finalidades405": return "CUMPLIMIENTO/INCUMPLIMIENTO DE OBLIGACIONES DINERARIAS";
            case "finalidades406": return "SERVICIOS ECONÓMICOS-FINANCIEROS Y SEGUROS";
            case "finalidades407": return "ANÁLISIS DE PERFILES";
            case "finalidades408": return "PUBLICIDAD Y PROSPECCIÓN COMERCIAL";
            case "finalidades409": return "PRESTACIÓN DE SERVICIOS DE COMUNICACIONES ELECTÓNICAS";
            case "finalidades410": return "GUIAS/REPERTORIOS DE SERVICIOS DE COMUNICACIONES ELECTRÓNICAS";
            case "finalidades411": return "COMERCIO ELECTRÓNICO";
            case "finalidades412": return "PRESTACIÓN DE SERVICIOS DE CERTIFICACIÓN ELECTRÓNICA";
            case "finalidades413": return "GESTIÓN DE ASOCIADOS O MIEMBROS DE PARTIDOS POLÍTICOS, SINDICATOS, IGLESIAS, "
            + "CONFESIONES O COMUNIDADES RELIGIOSAS Y ASOCIACIONES, FUNDACIONES Y OTRAS "
            + "ENTIDADES SIN ÁNIMO DE LUCRO, CUYA FINALIDAD SEA POLÍTICA, FILOSÓFICA, RELIGIOSA O SINDICAL";
            case "finalidades414": return "GESTIÓN DE ACTIVIDADES ASOCIATIVAS, CULTURALES, RECREATIVAS, DEPORTIVAS Y SOCIALES";
            case "finalidades415": return "GESTIÓN DE ASISTENCIA SOCIAL";
            case "finalidades416": return "EDUCACIÓN";
            case "finalidades417": return "INVESTIGACIÓN EPIDEMIOLÓGICA Y ACTIVIDADES ANÁLOGAS";
            case "finalidades418": return "GESTIÓN Y CONTROL SANITARIO";
            case "finalidades419": return "HISTORIAL CLÍNICO";
            case "finalidades420": return "SEGURIDAD PRIVADA";
            case "finalidades421": return "SEGURIDAD Y CONTROL DE ACCESO A EDIFICIOS";
            case "finalidades422": return "VIDEOVIGILANCIA";
            case "finalidades423": return "FINES ESTADÍSTICOS, HISTÓRICOS O CIENTÍFICOS";
            case "finalidades499": return "OTRAS FINALIDADES";
        }
        return null;
    }   
    
}
