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

import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.business.seguridad.SeguridadLocal;
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.lopd.TipoProcedimiento;
import com.openlopd.sessionbeans.AbstractFacade;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Eduardo L. García Glez.
 */
@Stateless
public class TipoProcedimientoFacade extends AbstractFacade<TipoProcedimiento> implements TipoProcedimientoFacadeLocal {
    @EJB
    private SeguridadLocal seguridad;
    private static ResourceBundle rb = ResourceBundle.getBundle("com.openlopd.config.config", Locale.getDefault());
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TipoProcedimientoFacade() {
        super(TipoProcedimiento.class);
    }
    
    /**
     * Obtiene el listado de tipos de procedimientos activos.
     * @param empresa Empresa a la que pertenecen
     * los procedimientos (empresa madre, gestor).
     * @return Listado de tipos de procedimientos activos.
     */
    @Override
    public List<TipoProcedimiento> getTipoProcedimientoActivo(Empresa empresa) {        
        Query q = em.createNamedQuery("TipoProcedimiento.findActivesByEmpresa");
        q.setParameter("idEmpresa", empresa.getIdEmpresa());
        q.setParameter("idEmpresaGestora", seguridad.getEmpresaMadre(empresa).getIdEmpresa());
        q.setParameter("idEmpresaSistema", rb.getString("empresaID"));
        return q.getResultList();
    }
    
    /**
     * Obtiene la información del tipo de procedimiento dado.
     * @param empresa Empresa a la que pertenecen
     * @param idTipoProc identificador del tipo de procedimeinto.
     * @return Tipo de procedimiento encontrado.
     */
    @Override
    public TipoProcedimiento getTipoProcedimientoActivo(Empresa empresa, String idTipoProc) { 
        try {
            Query q = em.createNamedQuery("TipoProcedimiento.findActivesByTipo");
            q.setParameter("idEmpresa", empresa.getIdEmpresa());
            q.setParameter("idEmpresaGestora", seguridad.getEmpresaMadre(empresa).getIdEmpresa());            
            q.setParameter("idEmpresaSistema", rb.getString("empresaID"));
            q.setParameter("idTipoProc", idTipoProc);
            List<TipoProcedimiento> listTipo = q.getResultList();
            return listTipo.get(0) ;
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public TipoProcedimiento getSiguiente(AccessInfo accessInfo, String idTipoProc) {
        List<TipoProcedimiento> listaTipos =  this.getTipoProcedimientoActivo(accessInfo.getSubEmpresa());
        int i = listaTipos.indexOf(this.getTipoProcedimientoActivo(accessInfo.getSubEmpresa(), idTipoProc));
        return i + 1 < listaTipos.size() ? listaTipos.get(i + 1) : null;
    }
        
    
    @Override
    public TipoProcedimiento getAnterior(AccessInfo accessInfo, String idTipoProc) {
        List<TipoProcedimiento> listaTipos =  this.getTipoProcedimientoActivo(accessInfo.getSubEmpresa());
        int i = listaTipos.indexOf(this.getTipoProcedimientoActivo(accessInfo.getSubEmpresa(), idTipoProc));
        return i > 0 ? listaTipos.get(i - 1) : null;
    }
    /**
     * Obtiene el listado de tipos de procedimientos activos.
     * @param idEmpresa identificador único de la empresa a la que pertenecen
     * los procedimientos (empresa madre, gestor).
     * @return Listado de tipos de procedimientos activos.
     * @deprecated
     */
//    @Override
//    public List<TipoProcedimiento> getTipoProcedimientoActivo(String idEmpresa) {
//        Query q = em.createNamedQuery("TipoProcedimiento.findActivesByEmpresa");
//        q.setParameter("idEmpresa", idEmpresa);
//        return q.getResultList();
//    }
    
}
