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
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.interfaz.TipoOperacion;
import com.openlopd.entities.lopd.ProcedimientoHabilitado;
import com.openlopd.entities.lopd.TipoProcedimiento;
import com.openlopd.sessionbeans.AbstractFacade;
import com.openlopd.sessionbeans.interfaz.OperacionLopdFacadeLocal;
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
public class ProcedimientoHabilitadoFacade extends AbstractFacade<ProcedimientoHabilitado> implements ProcedimientoHabilitadoFacadeLocal {
    @EJB
    private OperacionLopdFacadeLocal operacionLopdFacade;
    private static ResourceBundle rb = ResourceBundle.getBundle("com.openlopd.config.config", Locale.getDefault());
    
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProcedimientoHabilitadoFacade() {
        super(ProcedimientoHabilitado.class);
    }
    
    private void checkPendingProcs(AccessInfo accessInfo) {        
        if (this.findPendingProcs(accessInfo)) {
            operacionLopdFacade.actualizarOperacion(accessInfo,
                    TipoOperacion.ProtocolosSeguridad, Boolean.FALSE);
        } else {
            operacionLopdFacade.actualizarOperacion(accessInfo,
                    TipoOperacion.ProtocolosSeguridad, Boolean.TRUE);
        }
    }
    
    @Override
    public void create(AccessInfo accessInfo, ProcedimientoHabilitado p) {
        create(p);
        checkPendingProcs(accessInfo);
    }
    
    @Override
    public void edit(AccessInfo accessInfo, ProcedimientoHabilitado p) {
        this.edit(p);
        checkPendingProcs(accessInfo);
    }
    
    @Override
    public void remove(AccessInfo accessInfo, ProcedimientoHabilitado p) {
        this.remove(p);
        checkPendingProcs(accessInfo);
    }

    @Override
    public List<ProcedimientoHabilitado> getProcedimientosHabilitados(TipoProcedimiento tipoProcedimiento,
            Empresa empresa) {
        return getProcedimientosHabilitados(
                tipoProcedimiento.getTipoProcedimientoPK().getId(),
                empresa.getIdEmpresa());
    }

    @Override
    public List<ProcedimientoHabilitado> getProcedimientosHabilitados(String idTipoProc,
            Empresa empresa) {
        return getProcedimientosHabilitados(idTipoProc, empresa.getIdEmpresa());

    }

    @Override
    public List<ProcedimientoHabilitado> getProcedimientosHabilitados(String idTipoProc,
            String idEmpresa) {
        if (idTipoProc == null) {
            return findAll(idEmpresa);
        } else {
            return findByTipo(idTipoProc, idEmpresa);
        }
    }

    private List<ProcedimientoHabilitado> findAll(String idEmpresa) {

        Query q = em.createNamedQuery("ProcedimientoHabilitado.findAll");
        q.setParameter("idEmpresa", idEmpresa);
        return q.getResultList();

    }

    private List<ProcedimientoHabilitado> findByTipo(String idTipoProc,
            String idEmpresa) {

        Query q = em.createNamedQuery("ProcedimientoHabilitado.findByTipo");
        q.setParameter("idTipoProc", idTipoProc);
        q.setParameter("idEmpresa", idEmpresa);
        return q.getResultList();
    }
    
    private boolean findPendingProcs(AccessInfo accessInfo) {
        String StringQuery = 
                "select pd.id_tipo_procedimiento "
                + "from lopd.procedimientos_disponibles pd "
                + "     left join lopd.procedimientos_habilitados ph "
                + "     on pd.id = ph.idprocedimiento "
                + "     and ph.idempresa = ?idSubEmpresa "
                + "where "
                + "      (pd.id_empresa = ?idSubEmpresa or " 
                + "       pd.id_empresa = ?idGestor or " 
                + "       pd.id_empresa = ?idPrincipal)" 
                + "group by pd.id_tipo_procedimiento " 
                + "having  count(ph.idprocedimiento) = 0  ";
        Query q = em.createNativeQuery(StringQuery);
        q.setParameter("idSubEmpresa", accessInfo.getSubEmpresa().getIdEmpresa());
        q.setParameter("idGestor", accessInfo.getEmpresa().getIdEmpresa());
        q.setParameter("idPrincipal", rb.getString("empresaID"));
        return q.getResultList().size() > 0;
    }
}
