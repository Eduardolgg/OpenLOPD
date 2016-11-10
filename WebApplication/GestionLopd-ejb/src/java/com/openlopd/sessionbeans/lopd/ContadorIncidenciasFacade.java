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

import com.openlopd.entities.lopd.ContadorIncidencias;
import com.openlopd.sessionbeans.AbstractFacade;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lógica de negocio para la gestion de los contadores de incidencias.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 28 de oct de 2012
 */
@Singleton
@Lock(LockType.WRITE)
public class ContadorIncidenciasFacade extends AbstractFacade<ContadorIncidencias> implements ContadorIncidenciasFacadeLocal {

    private static Logger logger = LoggerFactory.getLogger(ContadorIncidenciasFacade.class);
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;
    private final static String PREFIJO = "INC";
    private final static String SEPARADOR_CAMPOS = "-";

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContadorIncidenciasFacade() {
        super(ContadorIncidencias.class);
    }

    /**
     * Obtiene el contador de la empresa.
     *
     * Si el contador no existe se crea.
     *
     * @param idEmpresa Identificador único de la empresa.
     * @return Contador de incidencias de la empresa.
     */
    @Lock(LockType.WRITE)
    private ContadorIncidencias getContador(String idEmpresa) {
        try {
            ContadorIncidencias c = this.find(idEmpresa);            
            if (c == null) {
                return new ContadorIncidencias(idEmpresa, 0L);
            }
            return c;
        } catch (Exception e) {
            logger.info("No se encontró el contador de la empresa [{}] se "
                    + "crea uno nuevo.", idEmpresa);
            return new ContadorIncidencias(idEmpresa, 0L);
        }
        
    }

    /**
     * Devuelve el siguiente contador en formato Long.
     * @param idEmpresa Identificador de la empresa.
     * @return
     */
    @Lock(LockType.WRITE)
    private Long nextId_(String idEmpresa) {
        ContadorIncidencias c = this.getContador(idEmpresa);
        c.setContador(c.getContador() + 1);
        em.merge(c);
        em.flush();
        return c.getContador();
    }

    /**
     * Obtiene el siguiente código de incidencia de la empresa.
     * @param idEmpresa identificador único de la empresa.
     * @return Devuelve el siguiente código de incidencia de la empresa.
     */
    @Lock(LockType.WRITE)
    @Override
    public String nextId(String idEmpresa) {
        String contador = nextId_(idEmpresa).toString();
        
        for (int i = contador.length(); i < 3; i++) {
            contador = "0" + contador;
        }
        
        return PREFIJO + SEPARADOR_CAMPOS + contador + SEPARADOR_CAMPOS 
                + new GregorianCalendar().get(GregorianCalendar.YEAR);
    }
}
