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

import com.openlopd.entities.seguridad.TiposUsuario;
import java.util.List;
import javax.ejb.Local;

/**
 * Interfaz encargada de los métodos necesarios para la gestión de los tipos
 * de usuario pemitidos en el sistema.
 *
 * IMPORTANTE: Para ver los detalles de cáda método se recomienda ver la clase
 * <code>com.openlopd.sessionbeas.AbstractFacade</code>
 *
 * @see com.openlopd.entities.seguridad.ConstantesSeguridad
 * @author Eduardo L. García Glez.
 * Fecha 01 de feb de 2011
 * @version 1.0.0
 */
@Local
public interface TiposUsuarioFacadeLocal {

    void create(TiposUsuario tiposUsuario);

    void edit(TiposUsuario tiposUsuario);

    void remove(TiposUsuario tiposUsuario);

    TiposUsuario find(Object id);

    List<TiposUsuario> findAll();

    List<TiposUsuario> findRange(int[] range);

    int count();

}
