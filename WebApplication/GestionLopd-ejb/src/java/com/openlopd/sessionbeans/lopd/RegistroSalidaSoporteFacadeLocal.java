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

package com.openlopd.sessionbeans.lopd;

import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.entities.lopd.RegistroSalidaSoporte;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.exceptions.SeguridadWriteException;
import com.openlopd.exceptions.SeguridadWriteLimitException;
import com.jkingii.datatables.AbstractCDataTable;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Eduardo L. García GLez.
 */
@Local
public interface RegistroSalidaSoporteFacadeLocal {

    void create(AccessInfo accessInfo, RegistroSalidaSoporte registroSalidaSoporte) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException;

    void edit(AccessInfo accessInfo, RegistroSalidaSoporte registroSalidaSoporte) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException;

    void remove(AccessInfo accessInfo, RegistroSalidaSoporte registroSalidaSoporte) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException;

    RegistroSalidaSoporte find(Object id);

    List<RegistroSalidaSoporte> findAll();

    List<RegistroSalidaSoporte> findRange(int[] range);

    int count();
    
    public List<RegistroSalidaSoporte> findAllFiltering(AbstractCDataTable a, AccessInfo accessInfo);
    
}
