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

import com.openlopd.entities.facturacion.TipoFormaPago;
import com.openlopd.sessionbeans.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Gestiona el acceso a datos de la entidad TipoFormasPago.
 * @author Eduardo L. García Glez.
 * @version 0.0.0 14 de mar de 2011
 */
@Stateless
public class TipoFormaPagoFacade extends AbstractFacade<TipoFormaPago> implements TipoFormaPagoFacadeLocal {
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoFormaPagoFacade() {
        super(TipoFormaPago.class);
    }

    /**
     * Permite obtener los tipos de formas de pago que están actualmente activos.
     * @return Listado con las formas de pago activas.
     */
    @Override
    public List<TipoFormaPago> getActivos() {
        Query q = em.createNamedQuery("TipoFormaPago.findActives");
        return q.getResultList();
    }

}
