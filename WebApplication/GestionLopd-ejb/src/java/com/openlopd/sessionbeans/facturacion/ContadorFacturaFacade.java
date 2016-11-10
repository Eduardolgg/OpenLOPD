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

package com.openlopd.sessionbeans.facturacion;

import com.openlopd.entities.facturacion.ContadorFactura;
import com.openlopd.exceptions.NumFacturaGenerationException;
import com.openlopd.sessionbeans.AbstractFacade;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gestiona el acceso a los contadores de facturas.
 * @author Eduardo L. García Glez.
 * @version 0.0.0
 */
@Singleton
@Lock(LockType.WRITE)
public class ContadorFacturaFacade extends AbstractFacade<ContadorFactura> implements ContadorFacturaFacadeLocal {
    private static Logger logger = LoggerFactory.getLogger(ContadorFacturaFacade.class);
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;
    private static final String COUNTER_SEPARATOR = ":";
    HashMap <String, ContadorFactura> counters;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContadorFacturaFacade() {
        super(ContadorFactura.class);
    }
    
    private List<ContadorFactura> getCounters() {
        Query q = em.createNamedQuery("ContadorFactura.findUnLock");
        return q.getResultList();
    }
    
    @PostConstruct
    /**
     * Inicializa los contadores de facturas.
     */
    public void initialize() {  
        counters = new HashMap<String, ContadorFactura>();
        List<ContadorFactura> cl = this.getCounters();
        String key;
        
        logger.info("Obteniendo contadores...");
        for(ContadorFactura c : cl) {
            key = c.getContadorFacturaPK().getEmpresa() + COUNTER_SEPARATOR 
                    + c.getContadorFacturaPK().getSerie();
            counters.put(key, c);
            logger.info("Iniciado el contador: key[{}], último generado[{}]",
                    key, c.getNumero());
        }
    }
    
    /**
     * Devuelve el contador de la caché.
     * @param idEmpresa identificador de la empresa a la que peretenece el contador.
     * @param serie Serie buscada.
     * @return devuelve el contador actual.
     */
    //TODO: Es posible optimizar esto.
    @Lock(LockType.WRITE)
    public ContadorFactura buscarContador(String idEmpresa, String serie){
        ContadorFactura c = counters.get(idEmpresa + COUNTER_SEPARATOR + serie);
        c.setNumero(c.getNumero() + 1);
        em.merge(c);
        return c;
    }
    
    /**
     * Obtiene el siguiente id único de una shortUri.
     * 
     * En cada llamada incrementa el uno el contador interno. que se
     * encuentra en una variable de clase y además lo almacena en la
     * base de datos.
     *
     * @return Nuevo ID.
     * @throws ShortURIGenerationException
     */
    @Override
    @Lock(LockType.READ)    
    public Long nextId(String idEmpresa, String serie) throws NumFacturaGenerationException {
        try {
            ContadorFactura c = buscarContador(idEmpresa, serie);
            return c.getNumero();
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Exception: {}", e.getMessage());
            }
            throw new NumFacturaGenerationException("Imposible generar el nuevo "
                    + "número de factura para idEmpresa[" + idEmpresa 
                    + "], Serie[" + serie + "].");
        }
    }
}
