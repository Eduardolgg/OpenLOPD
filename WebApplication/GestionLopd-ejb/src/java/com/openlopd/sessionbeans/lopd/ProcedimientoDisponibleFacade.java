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
import com.openlopd.entities.interfaz.TipoOperacion;
import com.openlopd.entities.lopd.ProcedimientoDisponible;
import com.openlopd.entities.lopd.TipoProcedimiento;
import com.openlopd.sessionbeans.AbstractFacade;
import com.openlopd.sessionbeans.interfaz.OperacionLopdFacadeLocal;
import java.util.Date;
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
public class ProcedimientoDisponibleFacade extends AbstractFacade<ProcedimientoDisponible> implements ProcedimientoDisponibleFacadeLocal {
    @EJB
    private TipoProcedimientoFacadeLocal tipoProcedimientoFacade;
    @EJB
    private OperacionLopdFacadeLocal operacionLopdFacade;
    @EJB
    private SeguridadLocal seguridad;
    
    private static ResourceBundle rb = ResourceBundle.getBundle("com.openlopd.config.config", Locale.getDefault());

    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProcedimientoDisponibleFacade() {
        super(ProcedimientoDisponible.class);
    }
    
    @Override
    public void create(AccessInfo accessInfo,
            ProcedimientoDisponible procedimientoDisponible) {
        TipoProcedimiento t = tipoProcedimientoFacade.find(
                procedimientoDisponible.getTipo().getTipoProcedimientoPK());
        if (t == null) {
            t = procedimientoDisponible.getTipo();
            t.setFechaAlta(new Date().getTime());            
        } else {
            procedimientoDisponible.setTipo(t);
        }
        super.create(procedimientoDisponible);
        operacionLopdFacade.actualizarOperacion(accessInfo, 
                TipoOperacion.ProtocolosSeguridad, Boolean.FALSE);
    }

    //TODO Buscar bien
    @Override
    public List<ProcedimientoDisponible> getUnusedProcedures(String idTipoProc, AccessInfo accessInfo) {
        if (idTipoProc != null) {
            return findByTipo(idTipoProc, accessInfo);
        } else {
            return findAll(accessInfo);
        }
    }

    public List<ProcedimientoDisponible> findAll(AccessInfo accessInfo) {
        Query q = em.createNamedQuery("ProcedimientoDisponible.findAll");
        q.setParameter("idSubEmpresa", accessInfo.getSubEmpresa().getIdEmpresa());
        q.setParameter("idEmpresaGestora", seguridad.getEmpresaMadre(
                accessInfo.getSubEmpresa()).getIdEmpresa());
        q.setParameter("idEmpresaSistema", rb.getString("empresaID"));
        return q.getResultList();
    }

    public List<ProcedimientoDisponible> findByTipo(String idTipoProc, AccessInfo accessInfo) {
        Query q = em.createNamedQuery("ProcedimientoDisponible.findByTipo");
        q.setParameter("idSubEmpresa", accessInfo.getSubEmpresa().getIdEmpresa());
        q.setParameter("idEmpresaGestora", seguridad.getEmpresaMadre(
                accessInfo.getSubEmpresa()).getIdEmpresa());
        q.setParameter("idEmpresaSistema", rb.getString("empresaID"));
        q.setParameter("idTipoProc", idTipoProc);
        return q.getResultList();
    }
}
