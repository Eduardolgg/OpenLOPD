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

import com.openlopd.entities.seguridad.GruposUsuarios;
import java.util.List;
import javax.ejb.Local;

/**
 * Interfaz encargada de los métodos necesarios para la gestión los usuarios que
 * pertenecen a cada grupo.
 * 
 * IMPORTANTE: Para ver los detalles de cáda método se recomienda ver la clase
 * <code>com.openlopd.sessionbeas.AbstractFacade</code>
 *
 * @author Eduardo L. García Glez.
 * Fecha 01 de feb de 2011
 * @version 1.0.0
 *
 * @see com.openlopd.sessionbeas.AbstractFacade
 * @see com.openlopd.entities.seguridad.GruposUsuarios
 * @see com.openlopd.sessionbeans.seguridad.GruposUsuariosFacade
 */
@Local
public interface GruposUsuariosFacadeLocal {

    void create(GruposUsuarios gruposUsuarios);

    void edit(GruposUsuarios gruposUsuarios);

    void remove(GruposUsuarios gruposUsuarios);

    GruposUsuarios find(Object id);

    List<GruposUsuarios> findAll();

    List<GruposUsuarios> findRange(int[] range);

    int count();

    List<GruposUsuarios> getGruposByIdUsuario(String IdUsuario);

    GruposUsuarios addUserToGroup(String idUsuario, String idGrupo);

    public com.openlopd.entities.seguridad.GruposUsuarios addUserToGroup(com.openlopd.entities.seguridad.GruposUsuarios u);

    public void updateUserGroups(com.openlopd.business.seguridad.AccessInfo accessInfo, java.lang.String idUsuario, java.lang.String[] listaGrupos);

}
