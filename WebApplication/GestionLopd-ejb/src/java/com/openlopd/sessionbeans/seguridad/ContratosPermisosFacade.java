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

import com.openlopd.entities.seguridad.ContratosPermisos;
import com.openlopd.entities.seguridad.base.BaseContratosPermisos;
import com.openlopd.sessionbeans.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bean encargado de los métodos necesarios para la gestión de los permisos
 * de los contratos.
 *
 * @see com.openlopd.entities.seguridad.ContratosPermisos
 * @author Eduardo L. García Glez.
 * Fecha 01 de feb de 2011
 * @version 1.0.1 20 de mar de 2011
 * Modificaciones:
 *    20 de mar de 2011, Añadidas los métodos addPermisos.
 */
@Stateless
public class ContratosPermisosFacade extends AbstractFacade<ContratosPermisos> implements ContratosPermisosFacadeLocal {
    private static Logger logger = LoggerFactory.getLogger(ContratosPermisosFacade.class);
    
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    /**
     * Obtiene el Entity Manager de la Clase.
     *
     * Entity Manager que permite realizar las operaciones necesarias sobre la
     * entidad <code>ContratosPermisos</code>
     * @return Entity Manager de la Clase.
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Constructor Predeterminado.
     */
    public ContratosPermisosFacade() {
        super(ContratosPermisos.class);
    }

    /**
     * Obtiene los permisos del contrato de un gestor.
     * @param cif CIF/NIF del gestor/autonomo.
     * @return Contrato del gestor indicado en cif, null en caso de no encontrarlo.
     */
    @Override
    public ContratosPermisos getContratoGestor(String cif, String idEmpresaGestionada) {
        try {
            Query q = em.createNamedQuery("ContratosPermisos.findContratoGestor");
            q.setParameter("cif", cif);
            q.setParameter("idEmpresaGestionada", idEmpresaGestionada);
            return (ContratosPermisos) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Obtiene los permisos del contrato de una empresa.
     * @param cif CIF/NIF del gestor/autonomo.
     * @return Contrato del gestor indicado en cif, null en caso de no encontrarlo.
     */
    @Override
    public ContratosPermisos getContratoEmpresa(String idEmpresa) {
        try {
            Query q = em.createNamedQuery("ContratosPermisos.findContratoEmpresa");
            q.setParameter("idEmpresaGestionada", idEmpresa);
            return (ContratosPermisos) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Permite añadir los permisos de un nuevo contrato al sistema.
     * @param c Entidad a añadir que contiene los permisos y el identificador
     * del contrato.
     * @return La entidad añadida el sistema o <code>null</code> en caso de error.
     */
    @Override
    public ContratosPermisos addPermisos(ContratosPermisos c) {
        try {
            this.create(c);
            return c;
        } catch (Exception e) {
            logger.error("Error añadiendo los permisos del contrato {} desde la clase {}",
                    c.toString(), this.getClass().getName());
            return null;
        }
    }

    /**
     * Permite añadir los permisos de un nuevo contrato al sistema.
     * @param p Permisos a añadir al contrato.
     * @param idContrato Identificador único del nuevo contrato a añadir.
     * @return La entidad añadida el sistema o <code>null</code> en caso de error.
     */
    @Override
    public ContratosPermisos addPermisos(BaseContratosPermisos p, String idContrato) {
        try {
        return addPermisos(new ContratosPermisos(p, idContrato));
        } catch (Exception e) {
            logger.error("Error añadiendo los permisos del contrato desde la clase ", 
                    idContrato, this.getClass().getName());
            return null;
        }
    }
}
