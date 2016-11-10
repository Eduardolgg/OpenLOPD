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

import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.sessionbeans.AbstractFacade;
import com.openlopd.entities.seguridad.PermisosGrupos;
import com.openlopd.entities.seguridad.base.BasePermisosGrupos;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bean encargado de los métodos necesarios para la gestión de los permisos
 * de los grupos.
 *
 * @see com.openlopd.entities.seguridad.PermisosGrupos
 * @author Eduardo L. García Glez.
 * Fecha 01 de feb de 2011
 * @version 1.0.1 de 20 de mar de 2011
 * Modificaciones
 *    20 de mar de 2011, añadidos los métodos addPermisos.
 *
 * @see com.openlopd.entities.seguridad.PermisosGrupos
 * @see com.openlopd.sessionbeans.AbstractFacade
 * @see com.openlopd.sessionbeans.seguridad.PermisosGruposFacade
 */
@Stateless
public class PermisosGruposFacade extends AbstractFacade<PermisosGrupos> implements PermisosGruposFacadeLocal {
    private static Logger logger = LoggerFactory.getLogger(PermisosGruposFacade.class);
    
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    /**
     * Obtiene el entity manager de la clase.
     *
     * El entity manager para la persistencia de la entidad
     * <code>PermisosGrupos</code>
     * @return Entity manager de la clase.
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Constructor por defecto.
     */
    public PermisosGruposFacade() {
        super(PermisosGrupos.class);
    }

    /**
     * Obtiene el conjunto de grupos a los que pertenece un usuario.
     * @param idUsurio Identficador del usuario.
     * @return Listado de grupos a los que pertenece el usuario o
     * <code>null</code> en caso de no tener grupos.
     */
    @Override
    public List<PermisosGrupos> getGruposByIdUsuario(String idUsurio) {
        Query q = em.createNamedQuery("PermisosGrupos.findByIdUsuario");
        q.setParameter("idUsuario", idUsurio);
        return q.getResultList();
    }

    /**
     * Permite añadir un nuevo grupo al sistema.
     * @param p Grupo a añadir
     * @return El grupo añadido o <code>null</code> en caso de error.
     */
    @Override
    public PermisosGrupos addPermisos(PermisosGrupos p){
        try {
            this.create(p);
            return p;
        } catch (Exception e) {
            logger.error("Error al crear los perisos para {}" +
                    " Exception {} ", p.toString(), e.getMessage());
            return null;
        }
    }

    /**
     * Permite añadir un nuevo grupo al sistema.
     * @param permisos permisos correspondientes al nuevo grupo.
     * @param id identificador único del grupo.
     * @param todasEmpresas <code>true</code> indica si el grupo puede acceder
     * a todas las empresas.
     * @return El grupo añadido o <code>null</code> en caso de error.
     */
    @Override
    public PermisosGrupos addPermisos(BasePermisosGrupos permisos, String id, boolean todasEmpresas) {
        try {
            PermisosGrupos p = new PermisosGrupos (permisos, id, todasEmpresas);
            return addPermisos(p);
        } catch (Exception e) {
            logger.error("Error al crear los perisos para {}" +
                    " Exception {} ", id, e.getMessage());
            return null;
        }
    }
    
    @Override
    public PermisosGrupos findByIdGrupo(AccessInfo accessInfo, String idGrupo, 
            String idEmpresa) {
        Query q = em.createNamedQuery("PermisosGrupos.findById");
        q.setParameter("id", idGrupo);
        q.setParameter("idEmpresa", idEmpresa);
        return  (PermisosGrupos) q.getSingleResult();        
    }
}
