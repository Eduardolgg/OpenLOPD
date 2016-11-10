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

import com.openlopd.entities.facturacion.ContadorFactura;
import com.openlopd.exceptions.NumFacturaGenerationException;
import java.util.List;
import javax.ejb.Local;

/**
 * Gestiona el acceso a los contadores de facturas.
 * @author Eduardo L. García Glez.
 * @version 0.0.0
 */
@Local
public interface ContadorFacturaFacadeLocal {

    void create(ContadorFactura contadorFactura);

    void edit(ContadorFactura contadorFactura);

    void remove(ContadorFactura contadorFactura);

    ContadorFactura find(Object id);

    List<ContadorFactura> findAll();

    List<ContadorFactura> findRange(int[] range);

    int count();

    public Long nextId(String idEmpresa, String serie) throws NumFacturaGenerationException;
    
}
