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

package com.openlopd.sessionbeans.documentos;

import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.business.seguridad.SeguridadLocal;
import com.openlopd.entities.documentos.Plantilla;
import com.openlopd.entities.documentos.Plantilla_;
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.interfaz.TipoOperacion;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.entities.seguridad.utils.primarykey.GenKey;
import com.openlopd.exceptions.SeguridadWriteException;
import com.openlopd.exceptions.SeguridadWriteLimitException;
import com.openlopd.sessionbeans.AbstractFacadeDataTable;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Eduardo L. García GLez.
 * @version 0.0.0
 */
@Stateless
public class PlantillaFacade extends AbstractFacadeDataTable<Plantilla> implements PlantillaFacadeLocal {
    @EJB
    private FileDataBaseFacadeLocal fileDataBaseFacade;
    @EJB
    private SeguridadLocal seguridad;
    private static ResourceBundle rb = ResourceBundle.getBundle("com.openlopd.config.config", Locale.getDefault());
    private static Logger logger = LoggerFactory.getLogger(PlantillaFacade.class);
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlantillaFacade() {
        super(Plantilla.class);
    }
            
    @Override
    public void create(AccessInfo accessInfo, Plantilla p) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException {
         
        Plantilla old = this.find(p.getId());
        
        if (old != null) {
            old.setActive(Boolean.FALSE);
            old.setFechaBaja(new Date().getTime());
            this.edit(accessInfo, ColumnasPermisos.PLANTILLAS, 
            TipoOperacion.GestionDePlantillas, old);
        }
        
        p.setId(GenKey.newKey());        
        super.create(accessInfo, ColumnasPermisos.PLANTILLAS, 
            TipoOperacion.GestionDePlantillas, p);        
    }      

    @Override
    public void edit(AccessInfo accessInfo, Plantilla plantilla) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException {
        
        edit(accessInfo, ColumnasPermisos.PLANTILLAS, 
                TipoOperacion.GestionDePlantillas, plantilla);
    }

    @Override
    public void remove(AccessInfo accessInfo, Plantilla plantilla) 
            throws UnknownColumnException, 
            SeguridadWriteException, SeguridadWriteLimitException {
        
        remove(accessInfo, ColumnasPermisos.PLANTILLAS, 
                TipoOperacion.GestionDePlantillas, plantilla);
    }
    
    private Plantilla getActiveByName_(Empresa empresa, String nombre) {
        try {
            Query q = em.createNamedQuery("Plantilla.findByName");
            q.setParameter("active", true);
            q.setParameter("nombre", nombre);
            q.setParameter("empresa", empresa);
            return (Plantilla) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    @Override
    public Plantilla getActiveByName(Empresa empresa, String nombre) {
        Plantilla p = getActiveByName_(empresa, nombre);
                
        if (p == null) {
            p = getActiveByName_(seguridad.getEmpresaMadre(empresa), nombre);
        }
        
        return p;
    }
    
    /**
     * Obtiene una plantilla por su id.
     * @param id identificador de la plantilla.
     * @param empresa empresa a la que pertenece la plantilla.
     * @return Objeto de tipo plantilla.
     */
    @Override
    public Plantilla find(String id, Empresa empresa) {
        Query q = em.createNamedQuery("Plantilla.findById");
        q.setParameter("id", id);
        q.setParameter("empresa", empresa);
        return (Plantilla) q.getSingleResult();
    }
    
    /**
     * Obtiene información sobre las propiedades de la clase.
     * @param iShortCol Columna por la que se ordena la consulta.
     * @return infor de atributo.
     */
    @Override
    public SingularAttribute getExpression(int iShortCol) {
        if (iShortCol == 0) {
            return Plantilla_.nombre;
        }
        if (iShortCol == 1) {
            return Plantilla_.descripcion;
        }
        if (iShortCol == 2) {
            return Plantilla_.version;
        }
        if (iShortCol == 3) {
            return Plantilla_.documento;
        }
        if (iShortCol == 4) {
            return Plantilla_.fechaAlta;
        }
        if (iShortCol == 5) {
            return Plantilla_.fechaBaja;
        }
        return Plantilla_.nombre;  //TODO: Tal vez esto debería emitir una excepción.
    }

    @Override
    public SingularAttribute getEmpresaAttribute() {
        return Plantilla_.empresa;
    }

    @Override
    public SingularAttribute getBorradoAttribute() {
        return Plantilla_.borrado;
    }

    @Override
    public SingularAttribute getActiveAttribute() {
        return Plantilla_.active;
    } 

    @Override
    public Predicate getLikeFilter(CriteriaBuilder cb, Root<Plantilla> root, String filterText) {
        String filter = filterText.toLowerCase();
        Predicate likeNombre = cb.like(cb.lower(root.get(Plantilla_.nombre)), "%" + filter + "%");
        Predicate likeDesc = cb.like(cb.lower(root.get(Plantilla_.descripcion)), "%" + filter + "%");
        return cb.or(likeDesc, likeNombre);        
    }
    
    /**
     * Inserta las plantillas predefinidas, 
     * no verifica las que existen actualmente.
     */
    @Override
    public void insertarPlantillasPredefinidas(AccessInfo accessInfo) {
        Query q = em.createNamedQuery("Plantilla.findAllByIdEmpresa");
        q.setParameter("idEmpresa", accessInfo.getEmpresa().getIdEmpresa());
        q.setParameter("active", true);
            
        List<Plantilla> lp = q.getResultList();
        
        for(Plantilla p : lp) {
            Plantilla nueva = new Plantilla(GenKey.newKey(), p.getNombre(),
                    p.getDescripcion(), 
                    fileDataBaseFacade.copy(accessInfo, p.getDocumento()),
                    new Date().getTime(), null, "0.00", accessInfo.getSubEmpresa());
                    nueva.setCodOperacion(p.getCodOperacion());
                    nueva.setForm(p.getForm());
            this.create(nueva);
        }
    }
}
