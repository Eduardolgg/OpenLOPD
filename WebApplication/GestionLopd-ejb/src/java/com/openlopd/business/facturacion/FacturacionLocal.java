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

package com.openlopd.business.facturacion;

import com.openlopd.entities.facturacion.Factura;
import com.openlopd.entities.facturacion.TipoFormaPago;
import com.openlopd.exceptions.NumFacturaGenerationException;
import java.util.List;
import javax.ejb.Local;

/**
 * Session Bean encargado de la lógica de negocio relacionada con la facturación
 * @author Eduardo L. García Glez.
 * @version 0.0.1 14 de feb de 2011
 * Modificaciones
 *    15 de mar de 2011 añadido método getTipoFormaPagoById(short id);
 */
@Local
public interface FacturacionLocal {

    List<TipoFormaPago> getTipoFormasPagoActivas();

    public TipoFormaPago getTipoFormaPagoById(short id);

    public Factura nuevaFactura(Factura factura) throws NumFacturaGenerationException;
    
}
