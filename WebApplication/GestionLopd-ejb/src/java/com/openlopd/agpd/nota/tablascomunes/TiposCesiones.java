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
 *
 * @author Eduardo L. García Glez.
 */
public enum TiposCesiones {

    ORGANIZACIONES_O_PERSONAS_DIRECTAMENTE_RELACIONADAS_CON_EL_RESPONSABLE,
    ORGANISMOS_DE_LA_SEGURIDAD_SOCIAL,
    REGISTROS_PÚBLICOS,
    COLEGIOS_PROFESIONALES,
    ADMINISTRACIÓN_TRIBUTARIA,
    OTROS_ÓRGANOS_DE_LA_AMINISTRACIÓN_PÚBLICA,
    COMISIÓN_NACIONAL_DEL_MERCADO_DE_VALORES,
    COMISIÓN_NACIONAL_DEL_JUEGO,
    NOTARIOS_Y_PROCURADORES,
    FUERZAS_Y_CUERPOS_DE_SEGURIDAD,
    ORGANISMOS_DE_LA_UNIÓN_EUROPEA,
    ENTIDADES_DEDICADAS_AL_CUMPLIMIENTO_O_INCUMPLIMIENTO_DE_OBLIGACIONES_DINERARIAS,
    BANCOS_CAJAS_DE_AHORROS_Y_CAJAS_RURALES,
    ENTIDADES_ASEGURADORAS,
    OTRAS_ENTIDADES_FINANCIERAS,
    ENTIDADES_SANITARIAS,
    PRESTACIONES_DE_SERVICIOS_DE_TELECOMUNICACIONES,
    EMPRESAS_DEDICADAS_A_PUBLICIDAD_O_MARKETING_DIRECTO,
    ASOCIACIONES_Y_ORGANIZACIONES_SIN_ÁNIMO_DE_LUCRO,
    SINDICATOS_Y_JUNTAS_DE_PERSONAL,
    ADMINISTRACIÓN_PÚBLICA_CON_COMPETENCIA_EN_LA_MATERIA;

    public static String getText(String id) {

        switch (id) {
            case "01":
                return "ORGANIZACIONES O PERSONAS DIRECTAMENTE RELACIONADAS CON EL RESPONSABLE";
            case "02":
                return "ORGANISMOS DE LA SEGURIDAD SOCIAL";
            case "03":
                return "REGISTROS PÚBLICOS";
            case "04":
                return "COLEGIOS PROFESIONALES";
            case "05":
                return "ADMINISTRACIÓN TRIBUTARIA";
            case "06":
                return "OTROS ÓRGANOS DE LA AMINISTRACIÓN PÚBLICA";
            case "07":
                return "COMISIÓN NACIONAL DEL MERCADO DE VALORES";
            case "08":
                return "COMISIÓN NACIONAL DEL JUEGO";
            case "09":
                return "NOTARIOS Y PROCURADORES";
            case "10":
                return "FUERZAS Y CUERPOS DE SEGURIDAD";
            case "11":
                return "ORGANISMOS DE LA UNIÓN EUROPEA";
            case "12":
                return "ENTIDADES DEDICADAS AL CUMPLIMIENTO O INCUMPLIMIENTO DE OBLIGACIONES DINERARIAS";
            case "13":
                return "BANCOS, CAJAS DE AHORROS Y CAJAS RURALES";
            case "14":
                return "ENTIDADES ASEGURADORAS";
            case "15":
                return "OTRAS ENTIDADES FINANCIERAS";
            case "16":
                return "ENTIDADES SANITARIAS";
            case "17":
                return "PRESTACIONES DE SERVICIOS DE TELECOMUNICACIONES";
            case "18":
                return "EMPRESAS DEDICADAS A PUBLICIDAD O MARKETING DIRECTO";
            case "19":
                return "ASOCIACIONES Y ORGANIZACIONES SIN ÁNIMO DE LUCRO";
            case "20":
                return "SINDICATOS Y JUNTAS DE PERSONAL";
            case "21":
                return "ADMINISTRACIÓN PÚBLICA CON COMPETENCIA EN LA MATERIA";
        }
        return null;
    }
}
