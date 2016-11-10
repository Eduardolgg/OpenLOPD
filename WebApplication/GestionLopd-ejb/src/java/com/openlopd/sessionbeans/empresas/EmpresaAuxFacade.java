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

import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.empresas.EmpresaAux;
import com.openlopd.sessionbeans.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Acceso a datos para la entidad EmpresaAux.
 * @author Eduardo L. García Glez.
 * @version 0.0.1 16 de may de 2012
 */
@Stateless
public class EmpresaAuxFacade extends AbstractFacade<EmpresaAux> implements EmpresaAuxFacadeLocal {
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpresaAuxFacade() {
        super(EmpresaAux.class);
    }
    
    @Override
    public List<Empresa> findByCif(String cif) {
        try {
            Query q = em.createNamedQuery("Empresa.findByCif");
            q.setParameter("cif", cif);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
