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
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.seguridad.GestoresEmpresas;
import java.util.List;
import javax.ejb.Local;

/**
 * Interfaz encargado de los métodos necesarios para la gestión de los permisos
 * de los contratos.
 *
 * IMPORTANTE: Para ver los detalles de cáda método se recomienda ver la clase
 * <code>com.openlopd.sessionbeas.AbstractFacade</code>
 *
 * @author Eduardo L. García Glez.
 * Fecha 01 de feb de 2011
 * @version 1.0.0
 *
 * @see com.openlopd.entities.seguridad.GestoresEmpresas
 * @see com.openlopd.sessionbeans.AbstractFacade
 * @see com.openlopd.sessionbeans.seguridad.GestoresEmpresasFacade
 */
@Local
public interface GestoresEmpresasFacadeLocal {

    void create(GestoresEmpresas gestoresEmpresas);

    void edit(GestoresEmpresas gestoresEmpresas);

    void remove(GestoresEmpresas gestoresEmpresas);

    GestoresEmpresas find(Object id);

    List<GestoresEmpresas> findAll();

    List<GestoresEmpresas> findRange(int[] range);

    int count();

    public com.openlopd.entities.seguridad.GestoresEmpresas addEmpresaGestionada(com.openlopd.entities.seguridad.GestoresEmpresas g);

    public com.openlopd.entities.seguridad.GestoresEmpresas addEmpresaGestionada(java.lang.String CifGestor, java.lang.String idContrato, java.lang.String idEmpresa);

    public GestoresEmpresas findEmpresaGestionada(AccessInfo accessInfo);

    public GestoresEmpresas findEmpresaGestinada(AccessInfo accessInfo, Empresa empresaGestionada);

    public GestoresEmpresas findByCifGestor(AccessInfo accessInfo, String cif);

}
