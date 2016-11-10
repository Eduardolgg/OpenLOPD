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

package com.openlopd.business.seguridad;

import javax.ejb.Local;

/**
 *
 * @author Eduardo L. García Glez.
 */
@Local
public interface GruposLocal {

    public java.lang.String addAdminGroup(String idEmpresa, java.lang.String cif, short idContratoTipo);

    public java.util.List<com.openlopd.entities.seguridad.EmpresasGrupos> findAll(com.openlopd.business.seguridad.AccessInfo accessInfo);

    public com.openlopd.entities.seguridad.PermisosGrupos findPermisosGrupo(com.openlopd.business.seguridad.AccessInfo accessInfo, java.lang.String idGrupo);

    public java.util.Hashtable<java.lang.String, com.openlopd.entities.seguridad.PermisosGrupos> getPermisosDeCadaGrupo(com.openlopd.business.seguridad.AccessInfo accessInfo);

    public com.openlopd.entities.seguridad.PermisosGrupos updatePermiso(com.openlopd.business.seguridad.AccessInfo accessInfo, com.openlopd.entities.seguridad.base.ColumnasPermisos permiso, java.lang.String idGrupo, java.lang.Byte newValue);

    public java.lang.String addGroup(com.openlopd.business.seguridad.AccessInfo accessInfo, com.openlopd.entities.seguridad.EmpresasGrupos grupo);

    public java.util.List<com.openlopd.entities.seguridad.EmpresasGrupos> findAllHabilitados(com.openlopd.business.seguridad.AccessInfo accessInfo, java.lang.String idUsuario);

    public java.util.List<com.openlopd.entities.seguridad.EmpresasGrupos> findAllDisponibles(com.openlopd.business.seguridad.AccessInfo accessInfo, java.lang.String idUsuario);
    
}
