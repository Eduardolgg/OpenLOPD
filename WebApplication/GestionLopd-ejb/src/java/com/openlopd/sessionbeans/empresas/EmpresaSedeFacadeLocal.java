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

import com.openlopd.exceptions.RequiredEntityException;
import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.empresas.EmpresaSede;
import com.jkingii.datatables.AbstractCDataTable;
import java.util.List;
import javax.ejb.Local;

/**
 * Gestión de las sedes de la empresa.
 * @author Eduardo L. García GLez.
 * @version 0.0.0 5 de may de 2012 versión inicial
 */
@Local
public interface EmpresaSedeFacadeLocal {

    void create(EmpresaSede empresaSede);

    void edit(EmpresaSede empresaSede);

    void remove(EmpresaSede empresaSede, AccessInfo accessInfo)
            throws RequiredEntityException;

    EmpresaSede find(Object id);

    List<EmpresaSede> findAll();

    List<EmpresaSede> findRange(int[] range);

    int count();

    public EmpresaSede getPrincipal(Empresa empresa);
    
    public List<EmpresaSede> findAllFiltering(AbstractCDataTable a, AccessInfo accessInfo);    

    public void edit(AccessInfo accessInfo, EmpresaSede empresaSede);

    public void create(AccessInfo AccessInfo, EmpresaSede empresaSede);
}
