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
import com.openlopd.entities.empresas.Persona;
import com.openlopd.entities.empresas.Persona_;
import com.openlopd.entities.interfaz.TipoOperacion;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.exceptions.SeguridadWriteException;
import com.openlopd.exceptions.SeguridadWriteLimitException;
import com.openlopd.sessionbeans.AbstractFacadeDataTable;
import com.openlopd.sessionbeans.lopd.PersonaFicheroFacadeLocal;
import java.util.List;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Acceso a datos para la entidad Persona.
 * @author Eduardo L. García Glez.
 * @version 0.0.0 17 de mar de 2011
 */
@Stateless
public class PersonaFacade extends AbstractFacadeDataTable<Persona> implements PersonaFacadeLocal {
    @EJB
    private EmpresaFacadeLocal empresaFacade;
    @EJB
    private PersonaFicheroFacadeLocal personaFicheroFacade;
    private static Logger logger = LoggerFactory.getLogger(PersonaFacade.class);
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonaFacade() {
        super(Persona.class);
    }
    
    /**
     * Añade una presona al listado de personas con acceso a datos.
     * @param accessInfo Información de acceso del usuario que crea la fila.
     * @param p Persona a añadir al sistema.
     * @throws UnknownColumnException Uso en desarrollo no debería ocurrir.
     * Se emite cuando se consultan un permiso inexistente.
     * @throws SeguridadWriteException  Se lanza si el usuario no tiene permisos
     * de escritura de personas.
     */
    @Override
    public void create(AccessInfo accessInfo, Persona persona) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException{        
        this.updatePerContacto(accessInfo, persona);
        empresaFacade.updatePerContacto(accessInfo, accessInfo.getSubEmpresa(), persona);
        create(accessInfo, ColumnasPermisos.GESTION_PERSONAL, 
                TipoOperacion.GestionPersonal, persona);
    }
    
    @Override
    public void edit(AccessInfo accessInfo, Persona persona) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException {        
        this.updatePerContacto(accessInfo, persona);
        empresaFacade.updatePerContacto(accessInfo, accessInfo.getSubEmpresa(), persona);
        edit(accessInfo, ColumnasPermisos.GESTION_PERSONAL, 
                TipoOperacion.GestionPersonal, persona);
    }
    
    @Override
    public void edit(AccessInfo accessInfo, Persona persona, String ficheros) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException {
        this.updatePerContacto(accessInfo, persona);
        empresaFacade.updatePerContacto(accessInfo, accessInfo.getSubEmpresa(), persona);
        personaFicheroFacade.updateAll(accessInfo, ficheros, persona.getId());
        edit(accessInfo, ColumnasPermisos.GESTION_PERSONAL, 
                TipoOperacion.GestionPersonal, persona);
    }
    
    private void updatePerContacto(AccessInfo accessInfo, Persona persona) {
        if (persona.isPerContacto() 
                && !accessInfo.getSubEmpresa().getPerContacto().equals(persona.getId())){
            Query q = em.createNamedQuery("Persona.unsetPerContacto");
            q.executeUpdate();            
        }
    }

    @Override
    public void remove(AccessInfo accessInfo, Persona persona) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException {
        //TODO Cuando se borra la persona de contacto se debería avisar a la persona o poner una tarea.
        remove(accessInfo, ColumnasPermisos.GESTION_PERSONAL, 
                TipoOperacion.GestionPersonal, persona);
    }

    
    /**
     * Función que recupera la empresa para recuperar la contraseña.
     * @param cif cif de la empresa.
     * @param email mail de la empresa.
     * @return empresa si la empresa existe, null en caso contrario.
     */
    @Override
    public Object recoveryTest(String dni, String email) {
        try {
            Query q = em.createNamedQuery("Persona.RecoveryTest");
            q.setParameter("dni", dni);
            q.setParameter("email", email);
            return q.getSingleResult();
        } catch (NoResultException e) {
            logger.info("Sin resultados para cif [{}] y email [{}].");
            return null;
        }
    }
    
    @Override
    public List<Persona> findAll(AccessInfo accessInfo, String term) {
        Query q = em.createNamedQuery("Persona.findByIdEmpreasAndText");
        q.setParameter("idEmpresa", accessInfo.getSubEmpresa().getIdEmpresa());
        q.setParameter("term", "%" + term.toUpperCase() + "%");
        return q.getResultList();
    }
    
    @Override
    public List<Persona> findAll(AccessInfo accessInfo) {
        Query q = em.createNamedQuery("Persona.findByIdEmpresa");
        q.setParameter("idEmpresa", accessInfo.getSubEmpresa().getIdEmpresa());
        return q.getResultList();
    }
    
    @Override
    public List<Persona> getAutorizadosSalida(Empresa empresa) {
        Query q = em.createNamedQuery("Persona.findAutorizadosSalidaSoportes");
        q.setParameter("empresa", empresa);
        q.setParameter("autorizado", Boolean.TRUE);
        return q.getResultList();
    }
    
    @Override
    public List<Persona> getAutorizadosEntrada(Empresa empresa) {
        Query q = em.createNamedQuery("Persona.findAutorizadosEntradaSoportes");
        q.setParameter("empresa", empresa);
        q.setParameter("autorizado", Boolean.TRUE);
        return q.getResultList();
    }
    
    @Override
    public List<Persona> getAutorizadosCopiaReproduccion(Empresa empresa) {
        Query q = em.createNamedQuery("Persona.findAutorizadosCopiaReproduccion");
        q.setParameter("empresa", empresa);
        q.setParameter("autorizado", Boolean.TRUE);
        return q.getResultList();
    }
    
    /**
     * Obtiene al responsable de seguridad de la empresa.
     * @param accessInfo
     * @return Objeto <code>Persona</code> con el responsable encontrado.
     */
    @Override
    public Persona getResponsableSeguridad(AccessInfo accessInfo) {
        return find(accessInfo.getSubEmpresa().getPerContacto());
    }
    
    @Override
    public SingularAttribute getExpression(int iShortCol) {
        if (iShortCol == 0) {
            return Persona_.nombre;
        }
        if (iShortCol == 1) {
            return Persona_.apellido1;
        }
        if (iShortCol == 2) {
            return Persona_.usuario;
        }
        if (iShortCol == 3) {
            return Persona_.perContacto;
        }
        if (iShortCol == 4) {
            return Persona_.fInicio;
        }
        if (iShortCol == 5) {
            return Persona_.fFin;
        }
        return Persona_.nombre;
    }

    @Override
    public SingularAttribute getEmpresaAttribute() {
        return Persona_.empresa;
    }

    @Override
    public SingularAttribute getBorradoAttribute() {
        return Persona_.borrado;
    }

    @Override
    public SingularAttribute getActiveAttribute() {
        return Persona_.active;
    }

    @Override
    public Predicate getLikeFilter(CriteriaBuilder cb, Root<Persona> root, String filterText) {
        String filter = filterText.toLowerCase();
        Predicate likeNombre = cb.like(cb.lower(root.get(Persona_.nombre)), "%" + filter + "%");
        Predicate likeAp1 = cb.like(cb.lower(root.get(Persona_.apellido1)), "%" + filter + "%");
        Predicate likeAp2 = cb.like(cb.lower(root.get(Persona_.apellido2)), "%" + filter + "%");
        Predicate likeDNI = cb.like(cb.lower(root.get(Persona_.dni)), "%" + filter + "%");
        Predicate likeUsuario = cb.like(cb.lower(root.get(Persona_.usuario)), "%" + filter + "%");
        Predicate likePerfil = cb.like(cb.lower(root.get(Persona_.perfil)), "%" + filter + "%");
        Predicate likeTel = cb.like(root.get(Persona_.telefono), "%" + filter + "%");
        Predicate likeMail = cb.like(cb.lower(root.get(Persona_.email)), "%" + filter + "%");
        return cb.or(likeNombre, likeAp1, likeAp2, likeDNI, likeUsuario, 
                likePerfil, likeTel, likeMail);
    }
}
