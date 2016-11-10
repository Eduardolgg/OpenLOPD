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

import com.openlopd.entities.seguridad.ContratosTipoGestor;
import com.openlopd.sessionbeans.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Bean encargado de los métodos necesarios para la gestión de los permisos
 * de los contratos tipo creados por un gestor.
 *
 * @see com.openlopd.entities.seguridad.ContratosTipoGestor
 * @author Eduardo L. García Glez.
 * Fecha 01 de feb de 2011
 * @version 1.0.0
 */
@Stateless
public class ContratosTipoGestorFacade extends AbstractFacade<ContratosTipoGestor> implements ContratosTipoGestorFacadeLocal {
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    /**
     * Obtiene el entity manager de la clase.
     *
     * El entity manager obtenido permite realizar las operaciones necesarias
     * para la gestión de la entidad <code>ContratosTipoGestor</code>
     *
     * @return Entity manager de la clase.
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Constructor por defecto.
     */
    public ContratosTipoGestorFacade() {
        super(ContratosTipoGestor.class);
    }

}
