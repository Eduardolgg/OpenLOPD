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
import com.openlopd.entities.empresas.EmpresaSede;
import com.openlopd.entities.empresas.EmpresaSede_;
import com.openlopd.entities.interfaz.TipoOperacion;
import com.openlopd.sessionbeans.AbstractFacadeDataTable;
import com.openlopd.exceptions.RequiredEntityException;
import com.openlopd.sessionbeans.interfaz.OperacionLopdFacadeLocal;
import java.util.Date;
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
 * Gestión de las sedes de la empresa.
 * @author Eduardo L. García GLez.
 * @version 0.0.0 Versión inicial
 */
@Stateless
public class EmpresaSedeFacade extends AbstractFacadeDataTable<EmpresaSede> implements EmpresaSedeFacadeLocal {
    @EJB
    private OperacionLopdFacadeLocal operacionLopdFacade;
    private static Logger logger = LoggerFactory.getLogger(EmpresaSedeFacade.class);
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpresaSedeFacade() {
        super(EmpresaSede.class);
    }
    
    /**
     * Obtiene la información de la empresa que gestiona en contrato LOPD.
     * @param empresa empresa sobre la que buscar.
     * @return Información de la empresa que gestiona el contrato LOPD.
     */
    @Override
    public EmpresaSede getPrincipal(Empresa empresa) {
        try {
            Query q = em.createNamedQuery("EmpresaSede.findMainSede");
            q.setParameter("empresa", empresa);
            q.setParameter("active", true);
            return (EmpresaSede) q.getSingleResult();
        } catch (Exception e) {
            logger.error("Error obteniendo la empresa principal de la empresa [{}]", empresa);
            return null;
        }
    }
    
    private int resetGestionaContratLOPD(EmpresaSede empresaSede) {
        Query q = em.createNamedQuery("EmpresaSede.resetGestionaContratLOPD");
        q.setParameter("empresa", empresaSede.getEmpresa());
        q.setParameter("varFalse", false);
        q.setParameter("idSede", empresaSede.getId());
        return q.executeUpdate();
    }
    
    private EmpresaSede activeFirst(Empresa empresa) {
        Query q = em.createNamedQuery("EmpresaSede.findActives");
        q.setParameter("empresa", empresa);
        List <EmpresaSede> le = q.getResultList();
        EmpresaSede s = le.get(0);
        s.setGestionaContratLOPD(true);
        super.edit(s);
        return s;
    }
    
    @Override
    public void create(EmpresaSede empresaSede) {
        super.create(empresaSede);
        if (empresaSede.isGestionaContratLOPD()) {
            this.resetGestionaContratLOPD(empresaSede);
        }
    }
    
    @Override
    public void create(AccessInfo AccessInfo, EmpresaSede empresaSede) {
        super.create(empresaSede);
        if (empresaSede.isGestionaContratLOPD()) {
            this.resetGestionaContratLOPD(empresaSede);
            if(empresaSede.isChangeData()) {
                operacionLopdFacade.actualizarOperacion(AccessInfo,
                        TipoOperacion.Ficheros, Boolean.FALSE);
            }
        }
    }
    
    @Override
    public void edit(EmpresaSede empresaSede) {        
        super.edit(empresaSede);
        if (empresaSede.isGestionaContratLOPD()) {
            this.resetGestionaContratLOPD(empresaSede);
        }
    }
    
    
    @Override
    public void edit(AccessInfo accessInfo, EmpresaSede empresaSede) {        
        super.edit(empresaSede);
        if (empresaSede.isGestionaContratLOPD()) {
            this.resetGestionaContratLOPD(empresaSede);
            if (empresaSede.isChangeData()) {
                operacionLopdFacade.actualizarOperacion(accessInfo,
                        TipoOperacion.Ficheros, Boolean.FALSE);
            }
        }
    }

    @Override
    public SingularAttribute getExpression(int iShortCol) {
        if (iShortCol == 0) {
            return EmpresaSede_.nombreSede;
        }
        if (iShortCol == 1) {
            return EmpresaSede_.direccion;
        }
        if (iShortCol == 2) {
            return EmpresaSede_.telefono;
        }
        return EmpresaSede_.nombreSede;
    }

    @Override
    public SingularAttribute getEmpresaAttribute() {
        return EmpresaSede_.empresa;
    }

    @Override
    public SingularAttribute getBorradoAttribute() {
        return EmpresaSede_.borrado;
    }

    @Override
    public SingularAttribute getActiveAttribute() {
        return EmpresaSede_.active;
    }

    @Override
    public void remove(EmpresaSede empresaSede, AccessInfo accessInfo)
            throws RequiredEntityException {  
        if (empresaSede.isGestionaContratLOPD()) {
            throw new RequiredEntityException("Está intentando borrar la sede principal"
                    + " antes es necesario establecer otra sede como principal.");
        }
        
        Long now = new Date().getTime();
        empresaSede.setBorrado(now);
        empresaSede.setBorradoPor(accessInfo.getUserInfo().getUsuario());
        super.edit(empresaSede);
    }

    @Override
    public Predicate getLikeFilter(CriteriaBuilder cb, Root<EmpresaSede> root, String filterText) {
        String filter = filterText.toLowerCase();
        Predicate likeNombre = cb.like(cb.lower(root.get(EmpresaSede_.nombreSede)), "%" + filter + "%");
        Predicate likeTel = cb.like(root.get(EmpresaSede_.telefono), "%" + filter + "%");
        Predicate likeMovil = cb.like(root.get(EmpresaSede_.movil), "%" + filter + "%");
        Predicate likeFax = cb.like(root.get(EmpresaSede_.fax), "%" + filter + "%");
        Predicate likedireccion = cb.like(cb.lower(root.get(EmpresaSede_.direccion)), "%" + filter + "%");
        Predicate likeCp = cb.like(root.get(EmpresaSede_.cp), "%" + filter + "%");
        return cb.or(likeNombre, likeTel, likeMovil, likeFax, likedireccion, likeCp);
    }
}
