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
 * Clase de negocio que permite realizar la gestión de contratos.
 * @author Eduardo L. García Glez.
 * @version 0.0.0 13 de mar de 2011
 */
@Local
public interface ContratosLocal {

    public com.openlopd.entities.seguridad.ContratosTipo getContratoTipo(com.openlopd.entities.seguridad.ContratosTipo c);

    public com.openlopd.entities.seguridad.ContratosTipo getContratoTipo(java.lang.Short id);

    public com.openlopd.entities.seguridad.ContratosTipo getContratoTipo(java.lang.String nombre);
    
}
