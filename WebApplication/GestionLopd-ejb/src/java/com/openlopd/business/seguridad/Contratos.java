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

import com.openlopd.entities.seguridad.ContratosTipo;
import com.openlopd.sessionbeans.seguridad.ContratosTipoFacadeLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Clase de negocio que permite realizar la gestión de contratos.
 * @author Eduardo L. García Glez.
 * @version 0.0.0 13 de mar de 2011
 */
@Stateless
public class Contratos implements ContratosLocal {
    @EJB
    private ContratosTipoFacadeLocal contratosTipoFacade;

    /**
     * Permite obtener un contrato tipo
     *
     * Se obtiene toda la información del contrato enviando un objeto
     * <code>ContratosTipo</code> con el identificador a localizar
     * almacenado en la propiedad correspondiente.
     * @param c Contrato tipo que contiene el id a buscar.
     * @return Contrato tipo recogido de la base de datos al que corresponde
     * el id recibido.
     */
    @Override
    public ContratosTipo getContratoTipo(ContratosTipo c){
        return contratosTipoFacade.find(c.getId());
    }

    /**
     * Permite obtener un contrato tipo.
     *
     * Obtiente el contrato tipo a través de su id.
     *
     * @param id Identificador único del contrato a localizar.
     * @return Contrato tipo recogido de la base de datos al que corresponde
     * el id recibido.
     */
    @Override
    public ContratosTipo getContratoTipo(Short id){
        return contratosTipoFacade.find(id);
    }

    /**
     * Permite obtener un contrato tipo.
     *
     * Obtiene el contrato tipo al que corresponde nombre.
     *
     * @param nombre Nombre del contrato tipo a localizar.
     * @return Contrato tipo identificado por el nombre recibido.
     */
    @Override
    public ContratosTipo getContratoTipo(String nombre){
        return contratosTipoFacade.getContratoTipoByName(nombre);
    }
}
