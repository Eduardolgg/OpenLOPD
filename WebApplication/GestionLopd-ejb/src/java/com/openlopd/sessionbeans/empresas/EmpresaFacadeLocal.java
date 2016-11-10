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

package com.openlopd.sessionbeans.empresas;

import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.empresas.Persona;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.exceptions.SeguridadWriteException;
import com.openlopd.exceptions.SeguridadWriteLimitException;
import com.jkingii.datatables.AbstractCDataTable;
import java.util.List;
import javax.ejb.Local;

/**
 * Acceso a datos para le entidad Empresa.
 * @author Eduardo L. García Glez.
 * @version 0.0.0 17 de mar de 2011
 */
@Local
public interface EmpresaFacadeLocal {

    void create(Empresa empresa);
    
    void create(AccessInfo accessInfo, Empresa empresa) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException;

    void edit(AccessInfo accessInfo, Empresa empresa) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException;

    void remove(AccessInfo accessInfo, Empresa empresa) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException;

    Empresa find(Object id);

    List<Empresa> findAll();

    List<Empresa> findRange(int[] range);

    int count();

    public Object recoveryTest(java.lang.String cif, java.lang.String email);

    public List<Empresa> findAllFiltering(AbstractCDataTable a, AccessInfo accessInfo);

    public com.openlopd.entities.empresas.Empresa findEmpresaMadre(com.openlopd.entities.empresas.Empresa empresa);

    public void updatePerContacto(AccessInfo accessInfo, Empresa empresa, Persona persona) throws UnknownColumnException, SeguridadWriteException, SeguridadWriteLimitException;

}
