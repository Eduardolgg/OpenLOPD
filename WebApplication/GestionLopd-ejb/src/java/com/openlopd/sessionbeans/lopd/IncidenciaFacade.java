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
import com.openlopd.entities.lopd.Incidencia;
import com.openlopd.entities.lopd.Incidencia_;
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
 * Negocio de Incidencias LOPD
 * @author Eduardo L. García Glez.
 * @version 0.0.0 
 */
@Stateless
public class IncidenciaFacade extends AbstractFacadeDataTable<Incidencia> implements IncidenciaFacadeLocal {
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IncidenciaFacade() {
        super(Incidencia.class);
    }

    @Override
    public void create(AccessInfo accessInfo, Incidencia incidencia) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException {
        create(accessInfo, ColumnasPermisos.INCIDENCIAS, 
                TipoOperacion.GestionPersonal, incidencia);
    }

    @Override
    public void edit(AccessInfo accessInfo, Incidencia incidencia) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException {
        edit(accessInfo, ColumnasPermisos.INCIDENCIAS, 
                TipoOperacion.GestionPersonal, incidencia);
    }

    @Override
    public void remove(AccessInfo accessInfo, Incidencia incidencia) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException {
        remove(accessInfo, ColumnasPermisos.INCIDENCIAS, 
                TipoOperacion.GestionPersonal, incidencia);
    }
    
    @Override
    public SingularAttribute getExpression(int iShortCol) {
        if (iShortCol == 0) {
            return Incidencia_.codigo;
        }
        if (iShortCol == 1) {
            return Incidencia_.fechaIncidencia;
        }
        if (iShortCol == 2) {
            return Incidencia_.notificadoA;
        }
        if (iShortCol == 3) {
            return Incidencia_.sistemaAfectado;
        }
        if (iShortCol == 4) {
            return Incidencia_.medidasCorrectoras;
        }
        return Incidencia_.id;
    }

    @Override
    public SingularAttribute getEmpresaAttribute() {
        return Incidencia_.empresa;
    }

    @Override
    public SingularAttribute getBorradoAttribute() {
        return Incidencia_.borrado;
    }

    @Override
    public SingularAttribute getActiveAttribute() {
        return Incidencia_.active;
    }

    @Override
    public Predicate getLikeFilter(CriteriaBuilder cb, Root<Incidencia> root, String filterText) {
        String filter = filterText.toLowerCase();
        Predicate likeCod = cb.like(cb.lower(root.get(Incidencia_.codigo)), "%" + filter + "%");
        Predicate likeTipo = cb.like(cb.lower(root.get(Incidencia_.tipoIncidencia)), "%" + filter + "%");
        Predicate likeNP = cb.like(cb.lower(root.get(Incidencia_.notificadoPor)), "%" + filter + "%");
        Predicate likeNA = cb.like(cb.lower(root.get(Incidencia_.notificadoA)), "%" + filter + "%");
        Predicate likeEfectos = cb.like(cb.lower(root.get(Incidencia_.efectosDerivados)), "%" + filter + "%");
        Predicate likeMC = cb.like(cb.lower(root.get(Incidencia_.medidasCorrectoras)), "%" + filter + "%");
        Predicate likeSis = cb.like(cb.lower(root.get(Incidencia_.sistemaAfectado)), "%" + filter + "%");
        Predicate likePE = cb.like(cb.lower(root.get(Incidencia_.personaEjecutora)), "%" + filter + "%");
        Predicate likeDR = cb.like(cb.lower(root.get(Incidencia_.datosRestaurados)), "%" + filter + "%");
        Predicate likeDRM = cb.like(cb.lower(root.get(Incidencia_.datosRestauradosManualmente)), "%" + filter + "%");
        Predicate likePU = cb.like(cb.lower(root.get(Incidencia_.protocoloUtilizado)), "%" + filter + "%");
        return cb.or(likeCod, likeTipo, likeNP, likeNA, likeEfectos, likeMC,
                likeSis, likePE, likeDR, likeDRM, likePU);
    }
}
