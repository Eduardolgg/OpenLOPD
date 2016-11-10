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

import com.openlopd.entities.seguridad.ContratosTipo;
import com.openlopd.sessionbeans.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Bean encargado de los métodos necesarios para la gestión de los tipos de
 * contrato del sistema por defecto.
 *
 * @see com.openlopd.entities.seguridad.ContratosTipo
 * @author Eduardo L. García Glez.
 * Fecha 01 de feb de 2011
 * @version 1.0.1
 * Modificaciones:
 *     14 de mar de 2011 Nuevo método getContratoTipoByName
 */
@Stateless
public class ContratosTipoFacade extends AbstractFacade<ContratosTipo> implements ContratosTipoFacadeLocal {
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    /**
     * Obtiene el Entity manager de la clase.
     *
     * Entity manager que permite realizar las operaciones necesarias para la
     * getión de la entidad <code>contratosTipo</code>
     * @return Entity manager de la clase.
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Constructor por defecto.
     */
    public ContratosTipoFacade() {
        super(ContratosTipo.class);
    }

    /**
     * Permite encontrar un contrato a través de su nombre;
     * @param name Nombre del contrato a buscar.
     * @return ContratoTipo que coincide con el nombre recibido.
     */
    @Override
    public ContratosTipo getContratoTipoByName(String name) {
        Query q = em.createNamedQuery("ContratosTipo.findByNombre");
        q.setParameter("nombre", name);
        return (ContratosTipo) q.getSingleResult();
    }
}
