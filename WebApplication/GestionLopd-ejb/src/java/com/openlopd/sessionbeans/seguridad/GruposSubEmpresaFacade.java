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

import com.openlopd.sessionbeans.AbstractFacade;
import com.openlopd.entities.seguridad.GruposSubEmpresa;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bean encargado de los métodos necesarios para la gestión de los permisos de
 * un grupo sobre una subEmpresa.
 *
 * @author Eduardo L. García Glez.
 * Fecha 01 de feb de 2011
 * @version 1.0.1, 20 de mar de 2011
 * Modificaciones:
 *    20 de mar de 2011, añadido los métodos addGrupoSubEmpresa
 *
 * @see com.openlopd.entities.seguridad.GruposSubEmpresa
 * @see com.openlopd.sessionbeans.AbstractFacade
 * @see com.openlopd.sessionbeans.seguridad.GruposSubEmpresaFacadeLocal
 */
@Stateless
public class GruposSubEmpresaFacade extends AbstractFacade<GruposSubEmpresa> implements GruposSubEmpresaFacadeLocal {
    private static Logger logger = LoggerFactory.getLogger(GruposSubEmpresaFacade.class);
    
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    /**
     * Obtiene el entity manager de la clase.
     *
     * Entity manager necesario para la persistencia de <code>GruposSubEmpresa</code>
     *
     * @return Entity manager de la clase.
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Constructor por defecto.
     */
    public GruposSubEmpresaFacade() {
        super(GruposSubEmpresa.class);
    }

    @Override
    public List<GruposSubEmpresa> getGruposByIdUsuario(String idUsurio) {
        Query q = em.createNamedQuery("GruposSubEmpresa.findByIdUsuario");
        q.setParameter("idUsuario", idUsurio);
        return q.getResultList();
    }

    @Override
    public GruposSubEmpresa addGrupoSubEmpresa(String idGrupo, String idEmpresa,
            String idContrato) {
        return addGrupoSubEmpresa(new GruposSubEmpresa (idGrupo, idEmpresa, idContrato, 0));
    }

    @Override
    public GruposSubEmpresa addGrupoSubEmpresa(GruposSubEmpresa u) {
        try {
            u.setRank(0);
            this.create(u);
            return u;
        } catch (Exception e) {
            logger.error("Creando el grupoSubEmpresa {} desde {}", 
                    u.toString(), this.getClass().getName());
            return null;
        }
    }
}
