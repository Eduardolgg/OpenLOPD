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
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.seguridad.GestoresEmpresas;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.sessionbeans.AbstractFacade;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interfaz encargado de los métodos necesarios para el acceso a las subEmpresas
 * de un gestor.
 *
 * @author Eduardo L. García Glez.
 * Fecha 01 de feb de 2011
 * @version 1.0.1 20 de mar de 2011
 * Modificaciones:
 *    20 de mar de 2011, Se añade el método addEmpresaGestionada
 *
 * @see com.openlopd.entities.seguridad.GestoresEmpresas
 * @see com.openlopd.sessionbeans.AbstractFacade
 * @see com.openlopd.sessionbeans.seguridad.GestoresEmpresasFacadeLocal
 */
@Stateless
public class GestoresEmpresasFacade extends AbstractFacade<GestoresEmpresas> implements GestoresEmpresasFacadeLocal {
    private static Logger logger = LoggerFactory.getLogger(GestoresEmpresasFacade.class);
    
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    /**
     * Obtiene el entity manager de la clase.
     *
     * Permite realizar las gestiones para la persistencia de
     * <code>GestoresEmpresas</code>
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Constructor predeterminado.
     */
    public GestoresEmpresasFacade() {
        super(GestoresEmpresas.class);
    }

    /**
     * Permite añadir una nueva empresa gestionada.
     * @param g Entidad a añadir.
     * @return <code>GestoresEmpresas</code> con la entidad añadida o
     * <code>null</code> en caso de error.
     */
    @Override
    public GestoresEmpresas addEmpresaGestionada(GestoresEmpresas g) {
        try {
            g.setRank(0);
            this.create(g);
            return g;
        } catch (Exception e) {
            logger.error("Error creando la entidad {} desde {}", 
                    g.toString(), this.getClass().getName());
            return null;
        }
    }

    /**
     * Permite añadir una nueva empresa gestionada.
     *
     * @param CifGestor CIF/NIF del Gestor
     * @param idContrato Identificador del contrato de la empresa gestionada.
     * @param idEmpresa Identificador de la empresa gestionada.
     * @return <code>GestoresEmpresas</code> con la entidad añadida o
     * <code>null</code> en caso de error.
     */
    @Override
    public GestoresEmpresas addEmpresaGestionada(String CifGestor,
            String idContrato, String idEmpresa) {
        GestoresEmpresas g = new GestoresEmpresas(CifGestor, idContrato, idEmpresa, 0);
        return addEmpresaGestionada(g);
    }
    
   /**
    * Busca la empresa gestionada por el gestor.
    * @param accessInfo Información de acceso del usuario.
    * @return Empresa Gestionada.
    */
    @Override
    public GestoresEmpresas findEmpresaGestionada(AccessInfo accessInfo) {
        return this.findEmpresaGestinada(accessInfo, accessInfo.getSubEmpresa());
    }
    
    /**
     * Busca la empresa gestionada por el gestor.
     * @param accessInfo Información de acceso del usuario.
     * @param empresaGestionada Empresa gestionada a buscar.
     * @return Empresa Gestionada.
     */
    @Override
    public GestoresEmpresas findEmpresaGestinada(AccessInfo accessInfo, 
            Empresa empresaGestionada) {        
        Query q = em.createNamedQuery("GestoresEmpresas.findEmpresaGestionada");
        q.setParameter("cifGestor", accessInfo.getEmpresa().getCif());
        q.setParameter("idEmpresaGestionada", empresaGestionada.getIdEmpresa());
        List<GestoresEmpresas> l = q.getResultList();
        return l.isEmpty() ? null : l.get(0);
    }
    
    /**
     * Busca a un gestor a través de su cif
     * @param accessInfo información de acceso del usuario al sistema.
     * @param cif cif del gestor a buscar.
     * @return null si no se encuentra y GestorEmpresas en casos contrario.
     */
    @Override
    public GestoresEmpresas findByCifGestor(AccessInfo accessInfo, String cif) {
//        if (accessInfo.getPermisosEmpresa().hasAccess(ColumnasPermisos.GESTION_EMPRESAS)) {
// TODO           
//        }
        Query q = em.createNamedQuery("GestoresEmpresas.findByCifGestor");
        q.setParameter("cifGestor", cif);
        List<GestoresEmpresas> l = q.getResultList();
        return l.isEmpty() ? null : l.get(0);
    }
}

