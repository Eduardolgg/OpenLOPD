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
import com.openlopd.entities.seguridad.EmpresasGrupos;
import com.openlopd.entities.seguridad.utils.primarykey.GenKey;
import com.openlopd.sessionbeans.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bean encargado de los métodos necesarios para la gestión de los grupos creados
 * por una empresa.
 *
 * @see com.openlopd.entities.seguridad.EmpresasGrupos
 * @author Eduardo L. García Glez.
 * Fecha 01 de feb de 2011
 * @version 1.0.1 19 de mar de 2011
 * Modificaciones:
 *    19 de mar de 2011, Se añaden los métodos addGroup y addAdminGroup
 */
@Stateless
public class EmpresasGruposFacade extends AbstractFacade<EmpresasGrupos> implements EmpresasGruposFacadeLocal { 
    private static Logger logger = LoggerFactory.getLogger(EmpresasGruposFacade.class);
    
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    /**
     * Obtiene el Entity Manager de la clase.
     *
     * El entity manager permite gestionar la persistencia de la clase
     * <code>EmpresasGrupos</code>
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Constructor por defecto.
     */
    public EmpresasGruposFacade() {
        super(EmpresasGrupos.class);
    }

    /**
     * Crea un grupo de tipo Admin.
     * @param cif CIF/NIF de la empresa/autonomo sobre la que crear el grupo.
     * @return <code>EnpresasGrupos</code> con el grupo creado o <code>null</code>
     * si se produce un error.
     */
    @Override
    public EmpresasGrupos addAdminGroup(String cif){
        return this.addGroup(cif, "Admin", "Grupo con permisos de administrador");
    }

    /**
     * Crea un nuevo grupo.
     * @param cif Empresa sobre la que se crea el grupo.
     * @param nombre Nombre del grupo, una sola palabra, sin espacios.
     * @param desc Descripción detallada del grupo.
     * @return <code>EnpresasGrupos</code> con el grupo creado o <code>null</code>
     * si se produce un error.
     */
    @Override
    public EmpresasGrupos addGroup(String IdEmpresa, String nombre, String desc){
        EmpresasGrupos eg = new EmpresasGrupos(IdEmpresa, (new GenKey()).getKey(), nombre, desc);
        try {
            this.create(eg);
            return eg;
        } catch (Exception e) {
            Object[] params = {eg.getIdGrupo(), eg.getIdEmpresa(), this.getClass().getName()};
            logger.error("Error creando el grupo {} para la empresa {} "
                    + "desde la clase {}", params);
            return null;
        }
    }
    
    /**
     * Obtiene todos los grupos de una empresa.
     * @param idEmpresa Identificador único de la empresa.
     * @return Listado de grupos de la empresa.
     */
    @Override
    public List<EmpresasGrupos> findByIdEmpresa(String idEmpresa) {
        Query q = em.createNamedQuery("EmpresasGrupos.findByidEmpresa");
        q.setParameter("idEmpresa", idEmpresa);
        return q.getResultList();
    }
    
    /**
     * Obtiene un grupo por su nombre.
     * @param accessInfo Información de acceso del usuario.
     * @param nombre nombre del grupo a buscar.
     * @return Grupo encontrado.
     */
    @Override
    public EmpresasGrupos findByNombre(AccessInfo accessInfo, String nombre) {
        try {
            Query q = em.createNamedQuery("EmpresasGrupos.findByNombre");
            q.setParameter("nombre", nombre);
            q.setParameter("idEmpresa", accessInfo.getSubEmpresa().getIdEmpresa());
            return (EmpresasGrupos) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public List<EmpresasGrupos> findByIdUsuario(AccessInfo accessInfo, String idUsuario) {
        Query q = em.createNamedQuery("EmpresasGrupos.findByIdUsuario");
        q.setParameter("idEmpresa", accessInfo.getSubEmpresa().getIdEmpresa());
        q.setParameter("idUsuario", idUsuario);
        return q.getResultList();
    }
}
