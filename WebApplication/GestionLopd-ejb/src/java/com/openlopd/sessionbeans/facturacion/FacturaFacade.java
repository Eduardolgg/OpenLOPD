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
import com.openlopd.entities.facturacion.Factura_;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.exceptions.SeguridadWriteException;
import com.openlopd.exceptions.SeguridadWriteLimitException;
import com.openlopd.sessionbeans.AbstractFacadeDataTable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

/**
 * Gestion de acceso a datos para la entidad Factura.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 17 de mar de 2011
 */
@Stateless
public class FacturaFacade extends AbstractFacadeDataTable<Factura> implements FacturaFacadeLocal {

    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FacturaFacade() {
        super(Factura.class);
    }
    
    @Override
    public void create(AccessInfo accessInfo, Factura factura) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException {
        create(accessInfo, ColumnasPermisos.FACTURAS, null, factura);
    }

    @Override
    public void edit(AccessInfo accessInfo, Factura factura) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException {
        edit(accessInfo, ColumnasPermisos.FACTURAS, null, factura);
    }

    @Override
    public void remove(AccessInfo accessInfo, Factura factura) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException {
        remove(accessInfo, ColumnasPermisos.FACTURAS, null, factura);
    }

    @Override
    public SingularAttribute getExpression(int iShortCol) {
        if (iShortCol == 0) {
            return Factura_.fecha;
        }
        if (iShortCol == 1) {
            return Factura_.tipoFormaPago;
        }
        if (iShortCol == 2) {
            return Factura_.importe;
        }
        if (iShortCol == 3) {
            return Factura_.documento;
        }        
        return Factura_.fecha;
    }

    @Override
    public SingularAttribute getEmpresaAttribute() {
        return Factura_.empresa;
    }

    @Override
    public SingularAttribute getBorradoAttribute() {
        return Factura_.borrado;
    }

    @Override
    public SingularAttribute getActiveAttribute() {
        return Factura_.active;
    }

    @Override
    public Predicate getLikeFilter(CriteriaBuilder cb, Root<Factura> root, String filterText) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
