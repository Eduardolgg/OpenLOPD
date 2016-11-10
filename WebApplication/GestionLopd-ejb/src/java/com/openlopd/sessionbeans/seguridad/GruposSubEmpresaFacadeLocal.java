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

import com.openlopd.entities.seguridad.GruposSubEmpresa;
import java.util.List;
import javax.ejb.Local;

/**
 * Interfaz encargado de los métodos necesarios para la gestión de los permisos de
 * un grupo sobre una subEmpresa.
 *
 * IMPORTANTE: Para ver los detalles de cáda método se recomienda ver la clase
 * <code>com.openlopd.sessionbeas.AbstractFacade</code>
 *
 * @author Eduardo L. García Glez.
 * Fecha 01 de feb de 2011
 * @version 1.0.0
 *
 * @see com.openlopd.sessionbeas.AbstractFacade
 * @see com.openlopd.entities.seguridad.GruposSubEmpresa
 * @see com.openlopd.sessionbeans.seguridad.GruposSubEmpresaFacade
 */
@Local
public interface GruposSubEmpresaFacadeLocal {

    void create(GruposSubEmpresa gruposSubEmpresa);

    void edit(GruposSubEmpresa gruposSubEmpresa);

    void remove(GruposSubEmpresa gruposSubEmpresa);

    GruposSubEmpresa find(Object id);

    List<GruposSubEmpresa> findAll();

    List<GruposSubEmpresa> findRange(int[] range);

    int count();

    public java.util.List<com.openlopd.entities.seguridad.GruposSubEmpresa> getGruposByIdUsuario(java.lang.String idUsurio);

    public com.openlopd.entities.seguridad.GruposSubEmpresa addGrupoSubEmpresa(com.openlopd.entities.seguridad.GruposSubEmpresa u);

    public com.openlopd.entities.seguridad.GruposSubEmpresa addGrupoSubEmpresa(java.lang.String idGrupo, java.lang.String idEmpresa, java.lang.String idContrato);

}
