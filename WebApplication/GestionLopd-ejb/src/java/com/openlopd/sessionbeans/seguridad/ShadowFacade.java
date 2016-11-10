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
import com.openlopd.business.seguridad.utils.crypt.TextCrypt;
import com.openlopd.entities.seguridad.Shadow;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.sessionbeans.AbstractFacade;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bean encargado de los métodos necesarios para la gestión de la identificación
 * de los usuarios en el sistema.
 *
 * @author Eduardo L. García Glez. Fecha 01 de feb de 2011
 * @version 1.0.0
 *
 * @see com.openlopd.sessionbeas.AbstractFacade
 * @see com.openlopd.sessionbeans.seguridad.ShadowFacade
 * @see com.openlopd.entities.seguridad.Shadow
 */
@Stateless
public class ShadowFacade extends AbstractFacade<Shadow> implements ShadowFacadeLocal {

    private static Logger logger = LoggerFactory.getLogger(ShadowFacade.class);
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;
    private final static long MEDIA_HORA = 1800000L; // Milisegundos correspondentes a media hora.

    /**
     * Obtiene el entity manager de la clase.
     *
     * Entity manager para la gestión de la entidad Shadow.
     *
     * @return Entity manager.
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Constructor predeterminado.
     */
    public ShadowFacade() {
        super(Shadow.class);
    }

    /**
     * Obtiene la información de un usuario desde la base de datos.
     *
     * @param idUsuario Identificador único del usuario.
     * @param plainPass Password en texto plano.
     * @return Usuario encontrado, si no es posible validar los datos se
     * devuelve <code>null</code>
     */
    @Override
    public Shadow getUser(String idUsuario, String plainPass) {
        try {
            Shadow s = this.find(idUsuario);
            if (s != null) {
                if (s.getClave().equals(TextCrypt.Crypt(plainPass, s.getTipoCifrado()))) {
                    if (s.getFechaFin().compareTo(new Date()) > 0) {
                        return s;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("En getUser, No se pudo identificar al usuario: {}, Exception: {}",
                    idUsuario, e.getMessage());
        }
        logger.info("En getUser, No se pudo identificar al usuario: {}",
                idUsuario);
        return null;
    }
    
    @Override
    public List<Shadow> getOnlineUsers(AccessInfo accessInfo) {
        try {
            if (!accessInfo.getPermisosEmpresa().hasAccess(ColumnasPermisos.SYS_ADMIN)) {
                return null;
            }
            Query q = em.createNamedQuery("Shadow.findOnlineUsers");
            q.setParameter("lastAccessDate", new Date().getTime() - MEDIA_HORA);
            return q.getResultList();
        } catch (UnknownColumnException ex) {
            logger.error("Imposible!!! el premisos SYS_ADMIN debe existir");
            return null;
        }
    }
    
    @Override
    public List<Shadow> getLastOnlineUsers(AccessInfo accessInfo) {
        try {
            if (!accessInfo.getPermisosEmpresa().hasAccess(ColumnasPermisos.SYS_ADMIN)) {
                return null;
            }
            Query q = em.createNamedQuery("Shadow.findLastOnlineUsers");
            q.setParameter("lastAccessDate", new Date().getTime() - MEDIA_HORA);
            return q.getResultList();
        } catch (UnknownColumnException ex) {
            logger.error("Imposible!!! el premisos SYS_ADMIN debe existir");
            return null;
        }
    }
}
