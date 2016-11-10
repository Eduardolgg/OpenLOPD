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
 * Controla la página que inicia el proceso de recuperar contraseñas.
 * @author Eduardo L. García Glez.
 */
public class CLostPass implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(CLostPass.class);    
    SeguridadLocal seguridad = lookupSeguridadLocal();

    private String email = null;
    private String uid = null;

    /**
     * Constructor por defecto.
     */
    public CLostPass() {
    }

    /**
     * Establece el identificador del usuario.
     * 
     * Identificador con el que el usuario inicia la sesión en el sistema.
     * 
     * @param uid Identificador del usuario.
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Establece el e-mail del usuario.
     * @param email e-mail del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Inicia el procedimiento de recuperación de la contraseña.
     * @return <code>true</code> si se ha iniciado el proceso de recuperación
     * y se enviará un mail al usuario, <code>false</code> si no se encontró al
     * usuario en el sistema, <code>null</code> si no se ha realizado todavía
     * la petición de recuperar la contraseña.
     */
    public Boolean getInitRecoveryProccess() {
        logger.info("uid[{}] email[{}]", uid, email);
        if (/*email != null && */uid != null) {
            return seguridad.initPassRecoveryProcess(uid/*, email*/);
        }
        return null;
    }

    //<editor-fold defaultstate="collapsed" desc="lookup">
    private SeguridadLocal lookupSeguridadLocal() {
        try {
            Context c = new InitialContext();
            return (SeguridadLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/Seguridad!com.openlopd.business.seguridad.SeguridadLocal");
        } catch (NamingException ne) {
            logger.error("Imposible localizar SeguridadLocal");
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>

}
