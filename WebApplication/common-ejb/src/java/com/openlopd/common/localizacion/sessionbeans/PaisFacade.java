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

import com.openlopd.common.localizacion.entities.Pais;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Gestión de lógica de negocio de paises.
 * @author Eduardo L. García Glez.
 */
@Stateless
public class PaisFacade extends AbstractFacade<Pais> implements PaisFacadeLocal {
    @PersistenceContext(unitName = "common-ejbPU")
    private EntityManager em;    
    public static enum TypePais { ACCESO, DECLARANTE, ENCARGADO, RESPONSABLE,
    TRANSFERENCIA_INTERNACIONAL
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PaisFacade() {
        super(Pais.class);
    }
    
    @Override
    public Pais findByAGPDCode(String code) {
        Query q = em.createNamedQuery("Pais.findByAGPDCode");
        q.setParameter("agpdCode", code);
        return (Pais) q.getSingleResult();
    }
    
    @Override
    public List<Pais> getPaisesAgpdType(TypePais type) {
        String queryName = null;
        switch (type) {
            case ACCESO:
                queryName = "Pais.findByAcceso";
                break;
            case DECLARANTE: queryName = "Pais.findByDeclarante";
                break;
            case ENCARGADO: queryName = "Pais.findByEncargado";
                break;
            case RESPONSABLE: queryName = "Pais.findByResponsable";
                break;
            case TRANSFERENCIA_INTERNACIONAL: queryName = "Pais.findByTransfInternacional";
                break;
        }
        Query q = em.createNamedQuery(queryName);
        return q.getResultList();        
    }
}
