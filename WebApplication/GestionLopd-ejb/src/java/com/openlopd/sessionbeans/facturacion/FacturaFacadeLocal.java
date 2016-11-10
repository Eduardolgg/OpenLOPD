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

package com.openlopd.sessionbeans.facturacion;

import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.entities.facturacion.Factura;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.exceptions.SeguridadWriteException;
import com.openlopd.exceptions.SeguridadWriteLimitException;
import com.jkingii.datatables.AbstractCDataTable;
import java.util.List;
import javax.ejb.Local;

/**
 * Acceso a datos para la entidad Factura.
 * @author Eduardo L. García Glez.
 * @version 0.0.0 17 de mar de 2011
 */
@Local
public interface FacturaFacadeLocal {

    void create(Factura factura);
    
    void create(AccessInfo accessInfo, Factura factura) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException;

    /**
     * Solo para uso interno.
     * @param factura 
     */
    void edit(Factura factura);

    void edit(AccessInfo accessInfo, Factura factura) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException;

    void remove(AccessInfo accessInfo, Factura factura) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException;

    Factura find(Object id);

    List<Factura> findAll();

    List<Factura> findRange(int[] range);

    int count();
    
    public List<Factura> findAllFiltering(AbstractCDataTable a, AccessInfo accessInfo);
    
}
