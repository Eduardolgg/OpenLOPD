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

package com.openlopd.business.seguridad;

import java.util.regex.Pattern;

public class FiscalValidate {

    private static final Pattern cifPattern = Pattern.compile("[[A-H][J-N][P-S]UVW][0-9]{7}[0-9A-J]");
    private static final String CONTROL_SOLO_NUMEROS = "ABEH"; // Sólo admiten números como carácter de control
    private static final String CONTROL_SOLO_LETRAS = "KPQS"; // Sólo admiten letras como carácter de control
    private static final String CONTROL_NUMERO_A_LETRA = "JABCDEFGHI"; // Conversión de dígito a letra de control.

    public static boolean validateCif(String cif) {
        try {
            if (!cifPattern.matcher(cif).matches()) {
                // No cumple el patrón
                return false;
            }

            int parA = 0;
            for (int i = 2; i < 8; i += 2) {
                final int digito = Character.digit(cif.charAt(i), 10);
                if (digito < 0) {
                    return false;
                }
                parA += digito;
            }

            int nonB = 0;
            for (int i = 1; i < 9; i += 2) {
                final int digito = Character.digit(cif.charAt(i), 10);
                if (digito < 0) {
                    return false;
                }
                int nn = 2 * digito;
                if (nn > 9) {
                    nn = 1 + (nn - 10);
                }
                nonB += nn;
            }

            final int parcialC = parA + nonB;
            final int digitoE = parcialC % 10;
            final int digitoD = (digitoE > 0)
                    ? (10 - digitoE)
                    : 0;
            final char letraIni = cif.charAt(0);
            final char caracterFin = cif.charAt(8);

            final boolean esControlValido =
                    // ¿el carácter de control es válido como letra?
                    (CONTROL_SOLO_NUMEROS.indexOf(letraIni) < 0
                    && CONTROL_NUMERO_A_LETRA.charAt(digitoD) == caracterFin)
                    || // ¿el carácter de control es válido como dígito?
                    (CONTROL_SOLO_LETRAS.indexOf(letraIni) < 0
                    && digitoD == Character.digit(caracterFin, 10));
            return esControlValido;

        } catch (Exception e) {
            return false;
        }
    }
    
    
    public static final String NIF_STRING_ASOCIATION = "TRWAGMYFPDXBNJZSQVHLCKE";

    /**
     * Devuelve un NIF completo a partir de un DNI. Es decir, añade la letra del
     * NIF
     *
     * @param dni dni al que se quiere añadir la letra del NIF
     * @return NIF completo.
     */
    public static String letraDNI(int dni) {
        return String.valueOf(dni) + NIF_STRING_ASOCIATION.charAt(dni % 23);
    }
}
