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
 * Describe el error producido durante el envío.
 *
 * @author Eduardo L. García Glez.
 */
public enum ErrorEnvio {
    CORRECTO("00"),
    ENVIO_DUPLICADO("01"),
    ERROR_EN_EL_FORMATO_XML("02"),
    ERROR_EN_FORMATO_PDF("03"),
    EL_CERTIFICADO_NO_CORRESPONDE_AL_DECLARANTE("04"),
    CERTIFICADO_REVOCADO("05"),
    CERTIFICADO_NO_VALIDADO_POR_CA("06"),
    FIRMA_INCORRECTA("07"),
    FALTAN_DATOS_OBLIGATORIOS("08"),
    FALTA_MANIFESTACION_DEL_CONOCIMIENTO_DE_DEBERES("09"),
    ERROR_INTERNO_DEL_SISTEMA_DE_LA_AGPD("10"),
    CREDENCIALES_INCORRECTAS("11"),
    ERROR_INTERNO_AGPD_NO_SE_PUEDE_FIRMAR_ACUSE_DE_RECIBO("12"),
    SOLICITUD_MARCADA_COMO_FIRMADA_PERO_SIN_FIRMAR("13"),
    SOLICITUD_MARCADA_COMO_NO_FIRMADA_PERO_ESTA_FIRMADA("20");
    
    
    private final String id;

    ErrorEnvio(String id) {
        this.id = id;
    }

    public String getValue() {
        return id;
    }   
    
    public String getText() {
        switch (id) {
            case "00": case "01": case "02": case "03": case "04": case "05":
            case "06": case "07": case "08": case "09": case "10": case "11":
            case "12": case "13": case "20":
                return this.toString();
            default:
                return id + ": Tipo de error desconocido consultar log del sistema.";

        }
    }
    
}
