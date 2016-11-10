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

package com.openlopd.common.localizacion.sessionbeans;

import com.openlopd.common.localizacion.entities.Localidad;
import com.openlopd.common.localizacion.entities.Provincia;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bean de Session encargado de los métodos básicos para la gestión de
 * localidades.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 09 de marzo de 2011
 */
@Stateless
public class LocalidadFacade extends AbstractFacade<Localidad> implements LocalidadFacadeLocal {

    @PersistenceContext(unitName = "common-ejbPU")
    private EntityManager em;
    
    private static Logger logger = LoggerFactory.getLogger(LocalidadFacade.class);

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LocalidadFacade() {
        super(Localidad.class);
    }

    @Override
    public List<Localidad> getLocalidades(Provincia provincia) {
        return getLocalidades(provincia.getId());
    }

    @Override
    public List<Localidad> getLocalidades(String idProvincia) {
        try {
            Query q = em.createNamedQuery("Localidad.findByProvincia");
            q.setParameter("codProvincia", idProvincia);
            return q.getResultList();
        } catch (Exception e) {
            logger.error("Imposible recuperar las Localidades de la privincia "
                    + "idprovincia[{}]. Exception: {}", idProvincia, e.getMessage());
            return null;
        }
    }
    
    @Override
    public List<String> findAll(String term) {
        Query q = em.createNamedQuery("Localidad.findByText", String.class);
        q.setParameter("term", "%" + term.toUpperCase() + "%");
        return q.getResultList();
    }    
}
