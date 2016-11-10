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
import com.openlopd.entities.lopd.TipoSoporte;
import com.openlopd.entities.lopd.TipoSoporte_;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.exceptions.SeguridadWriteException;
import com.openlopd.exceptions.SeguridadWriteLimitException;
import com.openlopd.sessionbeans.AbstractFacadeDataTable;
import com.openlopd.sessionbeans.interfaz.OperacionLopdFacadeLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Eduardo L. García Glez.
 */
@Stateless
public class TipoSoporteFacade extends AbstractFacadeDataTable<TipoSoporte> implements TipoSoporteFacadeLocal {
    @EJB
    private OperacionLopdFacadeLocal operacionLopdFacade;

    private static Logger logger = LoggerFactory.getLogger(TipoSoporteFacade.class);
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoSoporteFacade() {
        super(TipoSoporte.class);
    }
    
    private void updateOperacionLopd(AccessInfo accessInfo) {
        Query q = em.createNamedQuery("TipoSoporte.findAlta");
        q.setParameter("empresa", accessInfo.getSubEmpresa());
        if (q.getResultList().size() > 0) {
            operacionLopdFacade.actualizarOperacion(accessInfo,
                    TipoOperacion.TiposSoportes, Boolean.TRUE);
        } else {
            operacionLopdFacade.actualizarOperacion(accessInfo,
                    TipoOperacion.TiposSoportes, Boolean.FALSE);
        }
    }

    @Override
    public void create(AccessInfo accessInfo, TipoSoporte tipoSoporte) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException {
        create(accessInfo, ColumnasPermisos.TIPOS_DE_SOPORTES, 
                null, tipoSoporte);
        operacionLopdFacade.actualizarOperacion(accessInfo, 
                TipoOperacion.TiposSoportes, Boolean.TRUE);
    }

    @Override
    public void edit(AccessInfo accessInfo, TipoSoporte tipoSoporte) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException {
        edit(accessInfo, ColumnasPermisos.TIPOS_DE_SOPORTES, 
                null, tipoSoporte);
        this.updateOperacionLopd(accessInfo);
    }

    @Override
    public void remove(AccessInfo accessInfo, TipoSoporte tipoSoporte) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException {
        remove(accessInfo, ColumnasPermisos.TIPOS_DE_SOPORTES, 
                null, tipoSoporte);
        this.updateOperacionLopd(accessInfo);
    }

    @Override
    public List<TipoSoporte> getActives(AccessInfo accessInfo) {
        try {
            Query q = em.createNamedQuery("TipoSoporte.findAlta");
//            q.setParameter("active", true);
            q.setParameter("empresa", accessInfo.getSubEmpresa());
            return q.getResultList();
        } catch (Exception e) {
            logger.error("En getActives: error buscanto "
                    + "los tipos de soportes activos {}", e.getMessage());
            return null;
        }
    }

    @Override
    public SingularAttribute getExpression(int iShortCol) {
        if (iShortCol == 0) {
            return TipoSoporte_.nombre;
        }
        if (iShortCol == 1) {
            return TipoSoporte_.descripcion;
        }
        if (iShortCol == 2) {
            return TipoSoporte_.fechaAlta;
        }
        if (iShortCol == 3) {
            return TipoSoporte_.fechaBaja;
        }
        return TipoSoporte_.nombre;
    }

    @Override
    public SingularAttribute getEmpresaAttribute() {
        return TipoSoporte_.empresa;
    }

    @Override
    public SingularAttribute getBorradoAttribute() {
        return TipoSoporte_.borrado;
    }

    @Override
    public SingularAttribute getActiveAttribute() {
        return TipoSoporte_.active;
    }

    @Override
    public Predicate getLikeFilter(CriteriaBuilder cb, Root<TipoSoporte> root, String filterText) {
        String filter = filterText.toLowerCase();
        Predicate likeNombre = cb.like(cb.lower(root.get(TipoSoporte_.nombre)), "%" + filter + "%");
        Predicate likeDesc = cb.like(cb.lower(root.get(TipoSoporte_.descripcion)), "%" + filter + "%");
        return cb.or(likeDesc, likeNombre);
    }
}
