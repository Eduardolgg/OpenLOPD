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

package com.openlopd.web.controllers.privatearea.admin.grupos;

import com.openlopd.business.seguridad.GruposLocal;
import com.openlopd.entities.seguridad.EmpresasGrupos;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.web.controllers.privatearea.AbstractWebPageController;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Controla addgroup para añadir nuevos grupos de usuarios al sistema.
 * @author Eduardo L. García Glez.
 * @version 0.0.0 1 de enero 2013
 */
public class CAddGroup extends AbstractWebPageController implements Serializable {
    GruposLocal grupos = lookupGruposLocal();
    private String nombre;
    private String descripcion;
    
    /**
     * Constructor por defecto.
     * Inicializa la columna de permisos.
     */
    public CAddGroup() {
        super(ColumnasPermisos.APP_ADMIN);
    }

    /**
     * Obtiene el nombre del grupo.
     * @return Nombre del grupo de usuarios.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del grupo.
     * @param nombre Nombre del grupo de usuarios.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción del grupo.
     * @return Descripción del grupo de usuarios.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del grupo.
     * @param descripcion Descripción del grupo de usuarios.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /**
     * Añade el grupo definido en el objeto al sistema.
     */
    public void addGroup() {
        //TODO: Evitar la creación de grupos repetidos y enviar mensaje de
        // estado al usuario.
        EmpresasGrupos grupo = new EmpresasGrupos();
        grupo.setNombre(nombre);
        grupo.setDescripcion(descripcion);
        grupos.addGroup(session.getAccessInfo(), grupo);
//        return grupo.getIdGrupo();
    }
    
    //<editor-fold defaultstate="collapsed" desc="lookup">
    private GruposLocal lookupGruposLocal() {
        try {
            Context c = new InitialContext();
            return (GruposLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/Grupos!com.openlopd.business.seguridad.GruposLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>
  
}
