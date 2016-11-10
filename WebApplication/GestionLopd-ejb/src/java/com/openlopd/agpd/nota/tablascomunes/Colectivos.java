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
public enum Colectivos {
    colectivos01, colectivos02, colectivos03, 
    colectivos04, colectivos05, colectivos06, colectivos07, 
    colectivos08, colectivos09, colectivos10, colectivos11, 
    colectivos12, colectivos13;
    
     public static String getText(String id) {

        switch (id) {
            case "colectivos01": return "EMPLEADOS";
            case "colectivos02": return "CLIENTES Y USUARIOS";
            case "colectivos03": return "PROVEEDORES";
            case "colectivos04": return "ASOCIADOS O MIEMBROS";
            case "colectivos05": return "PROPIETARIOS O ARRENDATARIOS";
            case "colectivos06": return "PACIENTES";
            case "colectivos07": return "ESTUDIANTES";
            case "colectivos08": return "PERSONAS DE CONTACTO";
            case "colectivos09": return "PADRES O TUTORES";
            case "colectivos10": return "REPRESENTANTE LEGAL";
            case "colectivos11": return "SOLICITANTES";
            case "colectivos12": return "BENEFICIARIOS";
            case "colectivos13": return "CARGOS PÚBLICOS";            
        }
        return null;
    }   
    
}
