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

package com.openlopd.sessionbeans.lopd;

import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.entities.interfaz.TipoOperacion;
import com.openlopd.entities.lopd.Soporte;
import com.openlopd.entities.lopd.Soporte_;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.exceptions.SeguridadWriteException;
import com.openlopd.exceptions.SeguridadWriteLimitException;
import com.openlopd.sessionbeans.AbstractFacadeDataTable;
import com.openlopd.sessionbeans.interfaz.OperacionLopdFacadeLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

/**
 * Negocio del alta de soportes.
 *
 * @author Eduardo L. García Glez.
 */
@Stateless
public class SoporteFacade extends AbstractFacadeDataTable<Soporte> implements SoporteFacadeLocal {
    @EJB
    private OperacionLopdFacadeLocal operacionLopdFacade;

    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SoporteFacade() {
        super(Soporte.class);
    }
    
    @Override
    public void create(AccessInfo accessInfo, Soporte soporte) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException{
        create(accessInfo, ColumnasPermisos.INVENTARIO_SOPORTES, 
                TipoOperacion.InventarioDeSoportes, soporte);      
    }
    
    @Override
    public void edit(AccessInfo accessInfo, Soporte soporte) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException{
        edit(accessInfo, ColumnasPermisos.INVENTARIO_SOPORTES, 
                TipoOperacion.InventarioDeSoportes, soporte);
    }

    @Override
    public void remove(AccessInfo accessInfo, Soporte soporte) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException {
        remove(accessInfo, ColumnasPermisos.INVENTARIO_SOPORTES, 
                TipoOperacion.InventarioDeSoportes, soporte);
    }

    @Override
    public SingularAttribute getExpression(int iShortCol) {
        if (iShortCol == 0) {
            return Soporte_.descripcion;
        }
        if (iShortCol == 1) {
            return Soporte_.fechaAlta;
        }
        if (iShortCol == 2) {
            return Soporte_.fechaBaja;
        }
        if (iShortCol == 3) {
            return Soporte_.etiqueta;
        }
        if (iShortCol == 4) {
            return Soporte_.capacidad;
        }
        return Soporte_.tipoSoporte;
    }

    @Override
    public SingularAttribute getEmpresaAttribute() {
        return Soporte_.empresa;
    }

    @Override
    public SingularAttribute getBorradoAttribute() {
        return Soporte_.borrado;
    }

    @Override
    public SingularAttribute getActiveAttribute() {
        return Soporte_.active;
    }

    @Override
    public Predicate getLikeFilter(CriteriaBuilder cb, Root<Soporte> root, String filterText) {
        String filter = filterText.toLowerCase();
        Predicate likeDesc = cb.like(cb.lower(root.get(Soporte_.descripcion)), "%" + filter + "%");
        Predicate likeEtiqueta = cb.like(cb.lower(root.get(Soporte_.etiqueta)), "%" + filter + "%");
        return cb.or(likeDesc, likeEtiqueta);
    }
}
