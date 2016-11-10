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

package com.openlopd.sessionbeans.interfaz;

import com.elgg.utils.Locale.StringLocale;
import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.entities.interfaz.LinkList;
import com.openlopd.entities.lopd.TipoNivelSeguridad;
import com.openlopd.entities.seguridad.PermisosGrupos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.sessionbeans.AbstractFacade;
import com.openlopd.sessionbeans.lopd.FicheroFacadeLocal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gestión del listado de enlaces.
 * @author Eduardo L. García Glez.
 */
@Stateless
public class LinkListFacade extends AbstractFacade<LinkList> implements LinkListFacadeLocal {
    @EJB
    private FicheroFacadeLocal ficheroFacade;
    private static Logger logger = LoggerFactory.getLogger(LinkListFacade.class);
    
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LinkListFacade() {
        super(LinkList.class);
    }
    /**
     * Busca todas las entradas activas, por idioma.
     *
     * Encuentra todas las entradas activas ordenadas por orden ascendente por
     * el campo order para el idioma recibido.
     *     
     * @param locale Idioma del grupo de enlaces a buscar.
     *
     * @return Listado de los links activos, null en caso de que no se encuentren
     * enlaces, si se producen errores se graban en el log del sistema.
     */
    private List<LinkList> findActives_(String locale) {
        Query q = em.createNamedQuery("LinkList.findByActive");
        q.setParameter("active", true);
        q.setParameter("locale", locale);
        try {
            List<LinkList> l = q.getResultList();
            return l;
        } catch (Exception e) {
            logger.error("Se produjo un error recuperando los links locale [{}]"
                    + " Exception: {}", locale, e.getMessage());
            return null;
        }
    }
    
    /**
     * Busca todas las entradas activas, por grupo e idioma.
     *
     * Encuentra todas las entradas activas ordenadas por orden ascendente por
     * el campo order para el idioma recibido.
     *
     * @param locale Idioma del grupo de enlaces a buscar.
     *
     * @return Listado de los links activos para el idioma recibido, si no encuentra
     * para el idioma recibido se mostrará el idioma por defecto, null en caso
     * de que no se encuentren enlaces, si se producen errores se graban
     * en el log del sistema.
     */
    @Override
    public List<LinkList> findActives(String locale) {
        
        List<LinkList> result;
        String currentLocale;
        StringLocale l = new StringLocale();
        l.setLocale(locale);

        try {
            Iterator i = l.getList().iterator();
            while (i.hasNext()) {
                currentLocale = (String) i.next();
                result = findActives_(currentLocale);
                if (!result.isEmpty())
                    return result;
            }
            logger.info("Warnin en findActivesByGroup al recuperar el contenido para"
                    + "No se encontró información para el idioma por defecto y/o el grupo recibido",
                    this);
            return null;
        } catch (Exception e) {
            logger.error("Error al recuperar el contenido Exception: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * Busca todo el listado de operaciones disponibles.
     * @return Listado de operaciones.
     */
    @Override
    public List<LinkList> findOperations() {
        return this.findOperations(TipoNivelSeguridad.Alto);
    }
    
    /**
     * Busca el listado de operaciones según el nivel de seguridad.
     * @param nivel nivel de seguridad más alto de las operaciones a encontrar.
     * @return Listado de operaciones, con un nivel máximo de "nivel".
     */
    @Override
    public List<LinkList> findOperations(TipoNivelSeguridad nivel) {
        Query q = em.createNamedQuery("LinkList.findOperations");
        q.setParameter("active", true);
        q.setParameter("nivel", nivel);
        return q.getResultList();        
    }
    
    @Override
    public LinkList findByLink(String link) {
        Query q = em.createNamedQuery("LinkList.findByLink");
        q.setParameter("link", link);
        q.setParameter("active", Boolean.TRUE);
        return (LinkList) q.getSingleResult();
    }
    
    /**
     * Obtiene el listado de tareas habituales del usuario.
     * @param accessInfo Información de acceso del usuario.
     * @return Listado de tareas abituales permitidas para el usuario.
     * @throws UnknownColumnException 
     */
    @Override
    public List<LinkList> findTareasHabituales(AccessInfo accessInfo) {
        Query q = em.createNamedQuery("LinkList.findTareasHabituales");        
        q.setParameter("nivel", ficheroFacade.getNivel(accessInfo));
        return getLinksAutorizados(accessInfo, q.getResultList());
    }
    
    /**
     * Filtra los links a los que se tiene acceso.
     * @param accessInfo Información de acceso del usuario.
     * @param links Listado de links a revisar.
     * @return Listado de links a los que el usuario tiene acceso.
     * @throws UnknownColumnException 
     */
    private List<LinkList> getLinksAutorizados(AccessInfo accessInfo, 
            List<LinkList> links) {
        List<LinkList> linksAutorizados = new ArrayList();
        for(LinkList link : links) {
            try {
                if (accessInfo.getPermisosUsuario().hasAccess(link.getPermiso(), PermisosGrupos.LECTURA)) {
                    linksAutorizados.add(link);
                }
            } catch (UnknownColumnException e) {
                logger.error("Error la columna [{}] no existe, se omite.", link.getPermiso());
            }
        }
        return linksAutorizados;
    }
}
