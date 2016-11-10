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
import com.openlopd.entities.seguridad.GruposUsuarios;
import com.openlopd.sessionbeans.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bean encargado de los métodos necesarios para la gestión los usuarios que
 * pertenecen a cada grupo.
 *
 * @author Eduardo L. García Glez.
 * Fecha 01 de feb de 2011
 * @version 1.0.1
 * Modificaciones
 *    20 de mar de 2011, Añadidas los métodos addUserToGroup
 *
 * @see com.openlopd.entities.seguridad.GruposUsuarios
 * @see com.openlopd.sessionbeans.AbstractFacade
 * @see com.openlopd.sessionbeans.seguridad.GruposUsuariosFacadeLocal
 */
@Stateless
public class GruposUsuariosFacade extends AbstractFacade<GruposUsuarios> implements GruposUsuariosFacadeLocal {
    private static Logger logger = LoggerFactory.getLogger(GruposUsuariosFacade.class);
    
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    /**
     * Obtiene el entity manager de la clase.
     *
     * Entity manager para la persistencia de la entidad <code>GruposUsuarios</code>
     * @return Entity manager.
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Constructor por defecto.
     */
    public GruposUsuariosFacade() {
        super(GruposUsuarios.class);
    }

    /**
     * Obtiene el listado de grupos a los que pertenece un usuario.
     * @param idUsurio identificador del usuario
     * @return Lista con los grupos asignados al usuario al que pertenece
     * <code>idUsuario</code>
     */
    @Override
    public List<GruposUsuarios> getGruposByIdUsuario(String idUsurio) {
        Query q = em.createNamedQuery("GruposUsuarios.findByIdUsuario");
        q.setParameter("idUsuario", idUsurio);
        return q.getResultList();
    }

    /**
     * Añade un usuario a un grupo.
     * @param idUsuario Identificador del usuario.
     * @param idGrupo Identificador del grupo.
     * @return Una entidad <code>GruposUsuarios</code> con el conjunto añadido.
     */
    @Override
    public GruposUsuarios addUserToGroup(String idUsuario, String idGrupo) {
        GruposUsuarios u = new GruposUsuarios(idUsuario, idGrupo);
        return addUserToGroup(u);
    }

    /**
     * Añade un usuario a un grupo.
     * @param u Entidad a añadir al sistema.
     * @return La entidad añadida o <code>null</code> en caso de error.
     */
    @Override
    public GruposUsuarios addUserToGroup(GruposUsuarios u){
        try {
            this.create(u);
            return u;
        } catch (Exception e) {
            logger.error("Error creando el grupo: {} en {}", u.toString(), 
                    this.getClass().getName());
            return null;
        }
    }
   
    @Override
    public void updateUserGroups(AccessInfo accessInfo, String idUsuario, String[] listaGrupos) {
        deleteUserGroups(idUsuario);
        
        for(String g: listaGrupos) {
            GruposUsuarios gu = new GruposUsuarios(idUsuario, g);
            create(gu);
        }
    }
    
    /**
     * Borra todos los grupos a los que pertenece el usuario.
     * @param idUsuario Usuario sobre el que actuar.
     * @return Número de grupos eliminados.
     */
    private int deleteUserGroups(String idUsuario) {
        Query q = em.createNamedQuery("GruposUsuarios.deleteByIdUsuario");
        q.setParameter("idUsuario", idUsuario);
        return q.executeUpdate();        
    }

}
