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

import com.openlopd.entities.seguridad.ConstantesSeguridad;
import com.openlopd.sessionbeans.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Bean encargado del los métodos necesarios para la gestión de las constantes
 * de seguridad.
 *
 * @see com.openlopd.entities.seguridad.ConstantesSeguridad
 * @author Eduardo L. García Glez.
 * Fecha 01 de feb de 2011
 * @version 1.0.0
 */
@Stateless
public class ConstantesSeguridadFacade extends AbstractFacade<ConstantesSeguridad> implements ConstantesSeguridadFacadeLocal {
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    /**
     * Obtiene el Entity Manager de la clase.
     *
     * Permite realizar las operaciónes necesarias con la entidad
     * <code>ConstantesSeguridad</code>
     *
     * @return Entity Manager de la clase.
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Constructor Predeterminado.
     */
    public ConstantesSeguridadFacade() {
        super(ConstantesSeguridad.class);
    }
    
    /**
     * Busca una constante por su nombre.
     * @param nombre nombre de la constante.
     * @return Constante con el nombre dado.
     */
    @Override
    public ConstantesSeguridad findByName(String nombre) {
        Query q = em.createNamedQuery("ConstantesSeguridad.findByNombre");
        q.setParameter("nombre", nombre);
        return (ConstantesSeguridad) q.getSingleResult();
    }

}
