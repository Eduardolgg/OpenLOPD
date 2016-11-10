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

package com.openlopd.business.seguridad.utils.crypt;

import com.openlopd.business.seguridad.ShortAccessInfo;
import com.openlopd.entities.seguridad.Shadow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Verifica si un password es válido
 *
 * Verifica si un password es válido en relación a uno encriptado.
 *
 * @author Eduardo L. García Glez.
 * @version 1.0.0 11 de feb de 2011
 */
public class PassValidate {
    private static Logger logger = LoggerFactory.getLogger(PassValidate.class);
    
    /**
     * Permite validar si el password recibido corresponde con el hash
     *
     * La clase genera un log en caso de producirse excepciones.
     * El log sera warning para información sobre el usuario y password e
     * info para la información sobre la excepción producida.
     *
     * @param pass Password sin encriptar, se encripta internamente para compararlo
     * con <code>shadow.getClave</code>
     * @param shadow Objeto que contiene la información del usuario, se utiliza
     * para obtener el password encriptado del usuario.
     * @param clean Indica si <code>pass</code> tiene que ser limpiado, en caso
     * de ser <code>true</code> se le asigna el valor de <code>shadow.getClave</code>.
     * @return <code>true</code> en caso de una validación correcta, false en caso
     * contrario.
     */
    public static boolean isValid(ShortAccessInfo sAccessInfo, Shadow shadow, boolean clean) {
        try {
            if (TextCrypt.Crypt(sAccessInfo.getClave(), shadow.getTipoCifrado()).equals(shadow.getClave())) {
                if (clean)
                    sAccessInfo.setClave(shadow.getClave());
                return true;
            }
            return false;
        } catch (Exception e) {
            logger.warn("Se ha realizado una identificación negativa "
                    + "para el Usuario [{}] con la clave [{}]",
                    shadow.getUsuario(), sAccessInfo.getClave());
            logger.info(e.toString());
            return false;
        }
    }

    @Override
    public String toString() {
        return "PassValidate{" + '}';
    }
}
