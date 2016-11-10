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

package com.openlopd.sessionbeans.empresas;

import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.empresas.Empresa_;
import com.openlopd.entities.empresas.Persona;
import com.openlopd.entities.interfaz.TipoOperacion;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.exceptions.SeguridadWriteException;
import com.openlopd.exceptions.SeguridadWriteLimitException;
import com.openlopd.sessionbeans.AbstractFacadeDataTable;
import com.openlopd.sessionbeans.interfaz.OperacionLopdFacadeLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

/**
 * Acceso a datos para le entidad Empresa.
 * @author Eduardo L. García Glez.
 * @version 0.0.0 17 de mar de 2011
 */
@Stateless
public class EmpresaFacade extends AbstractFacadeDataTable<Empresa> implements EmpresaFacadeLocal {
    @EJB
    private OperacionLopdFacadeLocal operacionLopdFacade;
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpresaFacade() {
        super(Empresa.class);
    }

    @Override
    public void create(AccessInfo accessInfo, Empresa empresa)
            throws UnknownColumnException, SeguridadWriteException,
            SeguridadWriteLimitException {
        create(accessInfo, ColumnasPermisos.EMPRESA_Y_SEDES, null, empresa);
    }

    @Override
    public void edit(AccessInfo accessInfo, Empresa empresa)
            throws UnknownColumnException, SeguridadWriteException,
            SeguridadWriteLimitException {
        edit(accessInfo, ColumnasPermisos.EMPRESA_Y_SEDES, null, empresa);
        
        if (empresa.isChangeNombreActividad()) {
            operacionLopdFacade.actualizarOperacion(accessInfo, 
                    TipoOperacion.Ficheros, Boolean.FALSE);
        }
        
        if (empresa.isChangePercontacto()) {
            operacionLopdFacade.actualizarOperacion(accessInfo, 
                    TipoOperacion.DesignarRespSeguridad, Boolean.TRUE);
        }        
    }

    /**
     * Actualiza la persona de contacto de la empresa.
     *
     * @param accessInfo Información de acceso del usuario.
     * @param empresa Empresa a editar.
     * @param persona Nueva persona de contacto.
     * @throws UnknownColumnException
     * @throws SeguridadWriteException
     * @throws SeguridadWriteLimitException
     */
    @Override
    public void updatePerContacto(AccessInfo accessInfo, Empresa empresa,
            Persona persona) throws UnknownColumnException,
            SeguridadWriteException, SeguridadWriteLimitException {
        if (persona.isPerContacto()
                && !accessInfo.getSubEmpresa().getPerContacto().equals(persona.getId())) {
            accessInfo.getSubEmpresa().setPerContacto(persona.getId());
            edit(accessInfo, empresa);
        }
    }

    @Override
    public void remove(AccessInfo accessInfo, Empresa empresa)
            throws UnknownColumnException, SeguridadWriteException,
            SeguridadWriteLimitException {
        remove(accessInfo, ColumnasPermisos.EMPRESA_Y_SEDES, null, empresa);
    }

    /**
     * Función que recupera la empresa para recuperar la contraseña.
     *
     * @param cif cif de la empresa.
     * @param email mail de la empresa.
     * @return empresa si la empresa existe, null en caso contrario.
     */
    @Override
    public Object recoveryTest(String cif, String email) {
        try {
            Query q = em.createNamedQuery("Empresa.RecoveryTest");
            q.setParameter("cif", cif);
            q.setParameter("email", email);
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Encuentra la empresa madre de la empresa dada.
     *
     * @param empresa empresa a la que buscar madre.
     * @return empresa madre de la <code>empresa</code> recibida.
     */
    @Override
    public Empresa findEmpresaMadre(Empresa empresa) {
        Query q = em.createNamedQuery("Empresa.findMadre");
        q.setParameter("idEmpresaHija", empresa.getIdEmpresa());
        return (Empresa) q.getSingleResult();
    }

    @Override
    public SingularAttribute getExpression(int iShortCol) {
        if (iShortCol == 0) {
            return Empresa_.cif;
        }
        if (iShortCol == 1) {
            return Empresa_.razonSocial;
        }
        if (iShortCol == 2) {
            return Empresa_.actividad;
        }
        if (iShortCol == 3) {
            return Empresa_.perContacto;
        }
        if (iShortCol == 4) {
            return Empresa_.mailContacto;
        }
        return Empresa_.cif;
    }

    @Override
    public SingularAttribute getEmpresaAttribute() {
        return Empresa_.idEmpresa;
    }

    @Override
    public SingularAttribute getBorradoAttribute() {
        return Empresa_.borrado;
    }

    @Override
    public SingularAttribute getActiveAttribute() {
        return Empresa_.active;
    }

    @Override
    public Predicate getLikeFilter(CriteriaBuilder cb, Root<Empresa> root, String filterText) {
        String filter = filterText.toLowerCase();
        Predicate likeCif = cb.like(cb.lower(root.get(Empresa_.cif)), "%" + filter + "%");
        Predicate likeRazonSocial = cb.like(cb.lower(root.get(Empresa_.razonSocial)), "%" + filter + "%");
        Predicate likeNombre = cb.like(cb.lower(root.get(Empresa_.nombre)), "%" + filter + "%");
        Predicate likeMailContacto = cb.like(cb.lower(root.get(Empresa_.mailContacto)), "%" + filter + "%");
        
        return cb.or(likeCif, likeRazonSocial, likeNombre, likeMailContacto);        
    }
}
