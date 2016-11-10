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
import com.openlopd.entities.lopd.DocumentoSeguridad;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.exceptions.SeguridadWriteException;
import com.openlopd.exceptions.SeguridadWriteLimitException;
import com.jkingii.datatables.AbstractCDataTable;
import java.util.List;
import javax.ejb.Local;

/**
 * Negocio de los documentos de seguridad.
 * @author Eduardo L. García Glez.
 * @version 0.0.0 15 de septiembre de 2012
 */
@Local
public interface DocumentoSeguridadFacadeLocal {

    void create(AccessInfo accessInfo, DocumentoSeguridad documentoSeguridad) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException;

    void edit(AccessInfo accessInfo, DocumentoSeguridad documentoSeguridad) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException;

    void remove(AccessInfo accessInfo, DocumentoSeguridad documentoSeguridad) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException;

    DocumentoSeguridad find(Object id);

    List<DocumentoSeguridad> findAll();

    List<DocumentoSeguridad> findRange(int[] range);

    int count();
    
    public List<DocumentoSeguridad> findAllFiltering(AbstractCDataTable a, AccessInfo accessInfo);

    public DocumentoSeguridad cumplimentarDocSeg(AccessInfo accessInfo) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException;
    
}
