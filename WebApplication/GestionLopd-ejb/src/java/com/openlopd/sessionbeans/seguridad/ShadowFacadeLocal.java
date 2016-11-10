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

package com.openlopd.sessionbeans.seguridad;

import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.entities.seguridad.Shadow;
import java.util.List;
import javax.ejb.Local;

/**
 * Interfaz encargada de los métodos necesarios para la gestión de la identificación
 * de los usuarios en el sistema.
 *
 * IMPORTANTE: Para ver los detalles de cáda método se recomienda ver la clase
 * <code>com.openlopd.sessionbeas.AbstractFacade</code>
 *
 * @author Eduardo L. García Glez.
 * Fecha 01 de feb de 2011
 * @version 1.0.0
 *
 * @see com.openlopd.sessionbeas.AbstractFacade
 * @see com.openlopd.sessionbeans.seguridad.ShadowFacade
 * @see com.openlopd.entities.seguridad.Shadow
 */
@Local
public interface ShadowFacadeLocal {

    void create(Shadow shadow);

    void edit(Shadow shadow);

    void remove(Shadow shadow);

    Shadow find(Object id);

    List<Shadow> findAll();

    List<Shadow> findRange(int[] range);

    int count();

    public com.openlopd.entities.seguridad.Shadow getUser(java.lang.String idUsuario, java.lang.String plainPass);

    public List<Shadow> getOnlineUsers(AccessInfo accessInfo);

    public List<Shadow> getLastOnlineUsers(AccessInfo accessInfo);

}
