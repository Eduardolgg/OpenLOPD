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
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.interfaz.TipoOperacion;
import com.openlopd.entities.lopd.ModoDestruccion;
import com.openlopd.entities.lopd.ModoDestruccion_;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.exceptions.SeguridadWriteException;
import com.openlopd.exceptions.SeguridadWriteLimitException;
import com.openlopd.sessionbeans.AbstractFacadeDataTable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

/**
 *
 * @author Eduardo L. García Glez.
 */
@Stateless
public class ModoDestruccionFacade extends AbstractFacadeDataTable<ModoDestruccion> implements ModoDestruccionFacadeLocal {

    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ModoDestruccionFacade() {
        super(ModoDestruccion.class);
    }
    
    @Override
    public void create(AccessInfo accessInfo, ModoDestruccion modoDestruccion) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException {
        create(accessInfo, ColumnasPermisos.MODOS_DESTRUCCION,
                TipoOperacion.ModosDeDestruccion, modoDestruccion);
    }
    
    @Override
    public void edit(AccessInfo accessInfo, ModoDestruccion modoDestruccion) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException {
        edit(accessInfo, ColumnasPermisos.MODOS_DESTRUCCION,
                TipoOperacion.ModosDeDestruccion, modoDestruccion);
    }
    
    @Override
    public void remove(AccessInfo accessInfo, ModoDestruccion modoDestruccion) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException {
        remove(accessInfo, ColumnasPermisos.MODOS_DESTRUCCION,
                TipoOperacion.ModosDeDestruccion, modoDestruccion);
    }

    @Override
    public List<ModoDestruccion> findAll(Empresa empresa) {
        Query q = em.createNamedQuery("ModoDestruccion.findAll");
        q.setParameter("empresa", empresa);
        return q.getResultList();
    }

    @Override
    public SingularAttribute getExpression(int iShortCol) {
        if (iShortCol == 0) {
            return ModoDestruccion_.nombre;
        }
        if (iShortCol == 1) {
            return ModoDestruccion_.descripcion;
        }
        if (iShortCol == 2) {
            return ModoDestruccion_.fechaAlta;
        }
        if (iShortCol == 3) {
            return ModoDestruccion_.fechaBaja;
        }
        return ModoDestruccion_.nombre;
    }

    @Override
    public SingularAttribute getEmpresaAttribute() {
        return ModoDestruccion_.empresa;
    }

    @Override
    public SingularAttribute getBorradoAttribute() {
        return ModoDestruccion_.borrado;
    }

    @Override
    public SingularAttribute getActiveAttribute() {
        return ModoDestruccion_.active;
    }

    @Override
    public Predicate getLikeFilter(CriteriaBuilder cb, Root<ModoDestruccion> root, String filterText) {
        String filter = filterText.toLowerCase();
        Predicate likeNombre = cb.like(cb.lower(root.get(ModoDestruccion_.nombre)), "%" + filter + "%");
        Predicate likeDesc = cb.like(cb.lower(root.get(ModoDestruccion_.descripcion)), "%" + filter + "%");
        return cb.or(likeDesc, likeNombre);
    }
}
