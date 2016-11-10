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

import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.empresas.EmpresaSede;
import com.openlopd.entities.empresas.Persona;
import com.openlopd.entities.facturacion.Factura;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import javax.ejb.Local;

/**
 * Esta interfaz contiene los métodos necesarios para la gestión de la seguridad
 * del sistema, como crear usuarios, grupos, etc.
 *
 * @author Eduardo L. García Glez.
 * Fecha 01 de feb de 2011
 * @version 1.0.0
 */
@Local
public interface SeguridadLocal {

    AccessInfo login(String usuario, String clave, String timeZoneId);

    public boolean isSystemUser(java.lang.String idUsuario);

    public String nuevoContrato(ShortAccessInfo userInfo, Empresa empresa, 
            EmpresaSede empresaSede, Persona contacto, Factura factura, int tipoPaquete );

    public java.lang.Boolean initPassRecoveryProcess(java.lang.String userId/*, java.lang.String email*/);

    public java.lang.Boolean changePassword(java.lang.String password, java.lang.String key);

//    public List<EmpresaInfo> getSubEmpresas(String idUsuario, int inicio, int totalReg, String textSearch, List<String> iSortCol_C);

    public java.util.List<com.openlopd.business.Empresas.EmpresaInfo> getSubEmpresas(AccessInfo accessInfo, com.jkingii.datatables.AbstractCDataTable dt);

    public com.openlopd.entities.empresas.Empresa getEmpresaMadre(com.openlopd.entities.empresas.Empresa empresa);

    public java.util.List<com.openlopd.entities.seguridad.base.ColumnasPermisos> getColumnasPermisos(com.openlopd.business.seguridad.AccessInfo accessInfo);

    public void activeUser(com.openlopd.business.seguridad.AccessInfo accessInfo, java.lang.String idUsuario, java.lang.String grupos);

    public void desactiveUser(com.openlopd.business.seguridad.AccessInfo accessInfo, java.lang.String idUsuario);

    public com.openlopd.business.seguridad.AccessInfo cambiarEmpresa(com.openlopd.business.seguridad.AccessInfo accessInfo, java.lang.String idEmpresa);

    public java.lang.String nuevoContrato(com.openlopd.business.seguridad.ShortAccessInfo userInfo, com.openlopd.entities.empresas.Empresa empresa, com.openlopd.entities.empresas.EmpresaSede empresaSede, com.openlopd.entities.empresas.Persona contacto, com.openlopd.entities.facturacion.Factura factura, int tipoPaquete, java.lang.String empresaGeneradora);

    public java.lang.String nuevoContrato(com.openlopd.business.seguridad.AccessInfo accessInfo, com.openlopd.entities.empresas.Empresa empresa, com.openlopd.entities.empresas.EmpresaSede empresaSede, com.openlopd.entities.empresas.Persona contacto);

    public com.openlopd.entities.seguridad.base.ColumnasPermisos getPermisoLink(java.lang.String link) throws UnknownColumnException;

    public String bajaContrato(AccessInfo accessInfo, Empresa e);

    public String bajaContrato(AccessInfo accessInfo, String idEmpresa);

    public String nuevoGestor(AccessInfo accessInfo, Empresa empresa, EmpresaSede empresaSede, Persona contacto);

    public void updateUserLastAccess(AccessInfo accessInfo);

}
