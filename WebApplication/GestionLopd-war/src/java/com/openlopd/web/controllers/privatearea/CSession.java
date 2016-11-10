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

package com.openlopd.web.controllers.privatearea;

import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.business.seguridad.SeguridadLocal;
import com.openlopd.entities.seguridad.base.BasePermisosGrupos;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import java.io.Serializable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gestiona la sessión del susuario.
 * @author Eduardo L. García GLez.
 * @version 0.0.0
 */
public class CSession implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(CSession.class);
           
    //<editor-fold defaultstate="collapsed" desc="POST Vars">
    private String usuario = null;
    private String password = null;
    private String timeZone = null;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Session Beans Vars">
    SeguridadLocal seguridad = lookupSeguridadLocal();
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Private Vars">
    private AccessInfo accessInfo = null;
    private AuxTables auxTables;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    /**
     * Constructor por defecto.
     * Inicializa las tablas auxiliares.
     */
    public CSession() {
        auxTables = new AuxTables();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="POST Methods">
    /**
     * Establece el password del usuario.
     * @param password password del usuario.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Establece el id del usuario.
     * @param usuario id del usuario.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    /**
     * Establece la zona horaria del usuario.
     * @param timeZone zona horaria del usuario.
     */
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
    //</editor-fold>
   
    /**
     * Obtiene la información de acceso del usuario.
     * @return Información de acceso del usuario.
     */
    public AccessInfo getAccessInfo() {
        return accessInfo;
    }   
    
    /**
     * Realiza la validación del usuario en el sistema.
     *
     * @return
     * <code>true</code> si es un usuario válido,
     * <code>false</code> en caso contrario.
     */
    public boolean isValidUser() {
        try {
            accessInfo = seguridad.login(usuario, password, timeZone);
            initUserPass();
            if (accessInfo != null) {
                this.auxTables.setAccessInfo(accessInfo);
                return true;
            } else {
                this.auxTables.setAccessInfo(null);
                return false;
            }
        } catch (Exception e) {
            logger.error("En isValidUser: error identificando al"
                    + "usuario [" + usuario + "]", this);
            return false;
        }
    }
    
    /**
     * Verifica si la sessión está iniciada.
     * @return <code>true</code> si la sesión está iniciada, <code>false</code>
     * en caso contrario.
     */
    public boolean isLogged() {
        return (accessInfo == null ? false : true);
    }
    
    /**
     * Acceso a Tablas auxiliares.
     * 
     * @return Retorna un objeto con los métodos para acceder 
     * a tablas auxiliares.
     */
    public AuxTables getAuxTables() {
        return this.auxTables;
    }
    
    public boolean isSysAdmin() throws UnknownColumnException{
        return this.getAccessInfo().getPermisosUsuario()
                .hasAccess(ColumnasPermisos.SYS_ADMIN, BasePermisosGrupos.ESCRITURA);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Private Methods">
    /**
     * Limpia el usuario y la clave de la sessión.
     * Debe llamarse a este método para que el password del usuario
     * permanezca en memoria el menor tiempo posible.
     */
    private void initUserPass() {
        this.usuario = null;
        this.password = null;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Looup Section">
    private SeguridadLocal lookupSeguridadLocal() {
        try {
            Context c = new InitialContext();
            return (SeguridadLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/Seguridad!com.openlopd.business.seguridad.SeguridadLocal");
        } catch (NamingException ne) {
            logger.error("Imposible recuperar el bean de Seguridad.");
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>
}
