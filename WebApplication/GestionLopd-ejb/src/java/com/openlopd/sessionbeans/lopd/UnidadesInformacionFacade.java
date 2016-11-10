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

import com.openlopd.entities.lopd.TipoSoporte;
import com.openlopd.entities.lopd.UnidadesInformacion;
import com.openlopd.sessionbeans.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Eduardo L. García Glez.
 */
@Stateless
public class UnidadesInformacionFacade extends AbstractFacade<UnidadesInformacion> implements UnidadesInformacionFacadeLocal {
    private static Logger logger = LoggerFactory.getLogger(UnidadesInformacionFacade.class);
    
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UnidadesInformacionFacade() {
        super(UnidadesInformacion.class);
    }
    
    @Override
    public List<UnidadesInformacion> getActives() {
        try {
            Query q = em.createNamedQuery("UnidadesInformacion.findByActives");
            q.setParameter("active", true);
            return q.getResultList();
        } catch (Exception e) {
            logger.error("En getActives: error buscanto "
                    + "unidades de Bytes activos. Exception: {}", e.getMessage());
            return null;
        }
    }
    
}
