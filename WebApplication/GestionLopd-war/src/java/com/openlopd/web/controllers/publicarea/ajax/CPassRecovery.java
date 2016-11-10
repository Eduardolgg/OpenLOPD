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

package com.openlopd.web.controllers.publicarea.ajax;

import com.openlopd.business.seguridad.SeguridadLocal;
import java.io.Serializable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controla la página de recuperación de passwords.
 * @author Eduardo L. García Glez.
 * @version 0.0.0
 */
public class CPassRecovery implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(CPassRecovery.class);
    
    SeguridadLocal seguridad = lookupSeguridadLocal();
    private String password;
    private String confirm_password;
    private String key;

    /**
     * Constructor por defecto.
     */
    public CPassRecovery() {
    }

    /**
     * Obtiene la confirmación del nuevo password del usuario.
     * @return Confirmación del nuevo password del usuario.
     */
    public String getConfirm_password() {
        return confirm_password;
    }

    /**
     * Establece la confirmación del nuevo password del usuario.
     * @param confirm_password Confirmación del nuevo password del usuario.
     */
    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    /**
     * Obtiene Identificador único para el cambio de contraseña del usuario.
     * 
     * Cada solicitud de cambio de contraseña genera un identificador único con
     * periodo de validez, este es necesario para el cambio de la contraseña.
     * 
     * @return Identificador único para el cambio de contraseña.
     */
    public String getKey() {
        return key;
    }

    /**
     * Establece el Identificador único para el cambio de contraseña del usuario.
     * 
     * Cada solicitud de cambio de contraseña genera un identificador único con
     * periodo de validez, este es necesario para el cambio de la contraseña.
     * 
     * @param key Identificador único para el cambio de contraseña.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Obtiene el nuevo password del usuario.
     * @return Nuevo password del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece el nuevo password del usuario.
     * @return Nuevo password del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }   
    
    /**
     * Inicia el proceso de cambio de contraseña.
     * @return true si el cambio se ha realizado correctamente, false
     * en caso contrario.
     */
    public Boolean getChangePass() {
        if (password.equals(confirm_password)) {
            return seguridad.changePassword(password, key);
        }
        return false;
    }

    //<editor-fold defaultstate="collapsed" desc="lookup">
    private SeguridadLocal lookupSeguridadLocal() {
        try {
            Context c = new InitialContext();
            return (SeguridadLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/Seguridad!com.openlopd.business.seguridad.SeguridadLocal");
        } catch (NamingException ne) {
            logger.error("Imposible recuperar el bean de session Seguridad.");
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>
    
}
