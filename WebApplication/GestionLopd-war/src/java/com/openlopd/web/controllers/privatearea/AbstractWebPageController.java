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

import com.openlopd.entities.seguridad.base.BasePermisosGrupos;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;

/**
 * Controla los permisos de acceso a una página web.
 * 
 * Métodos comunes para la verificación de permisos en la interfaz.
 * 
 * @author Eduardo L. García Glez.
 * @version 0.0.0 30 de dic de 2012
 */
public abstract class AbstractWebPageController {
    protected CSession session;
    private ColumnasPermisos columna;
            
    /**
     * Inicializa el objeto con la columna de permisos que aplica
     * en la página actual.
     * @param columna Columna de permisos a utilizar.
     */
    public AbstractWebPageController(ColumnasPermisos columna) {
        this.columna = columna;
    }

    /**
     * Establece la información de sesión del usuario.
     * @param session Sesión del usuarioi.
     */
    public void setSession(CSession session) {
        this.session = session;
    }
    
    /**
     * Permite verificar si se tienen permisos de lectura en la página.
     * @return true si se tienen permisos de lectura, false en caso
     * contrario.
     * @throws UnknownColumnException Si la columna de permisos solicitada
     * es desconocida.
     */
    public boolean isReadable()
            throws UnknownColumnException {
        return session.getAccessInfo().getPermisosUsuario()
                .hasAccess(columna, BasePermisosGrupos.LECTURA);
    }
    
    /**
     * Permite verificar si se tienen permisos de escritura en la página.
     * @return true si se tienen permisos de escritura, false en 
     * caso contrario.
     * @throws UnknownColumnException Si la columna de permisos solicitada
     * es desconocida.
     */
    public boolean isWritable() 
            throws UnknownColumnException {        
        return session.getAccessInfo().getPermisosUsuario()
                .hasAccess(columna, BasePermisosGrupos.ESCRITURA);
    }    
    
}
