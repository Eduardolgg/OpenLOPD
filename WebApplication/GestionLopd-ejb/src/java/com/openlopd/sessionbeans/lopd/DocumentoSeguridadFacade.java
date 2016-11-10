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
import com.openlopd.documents.CumplimentarPlantilla;
import com.openlopd.entities.documentos.FileDataBase;
import com.openlopd.entities.interfaz.TipoOperacion;
import com.openlopd.entities.lopd.DocumentoSeguridad;
import com.openlopd.entities.lopd.DocumentoSeguridad_;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.entities.seguridad.utils.primarykey.GenKey;
import com.openlopd.exceptions.SeguridadWriteException;
import com.openlopd.exceptions.SeguridadWriteLimitException;
import com.openlopd.sessionbeans.AbstractFacadeDataTable;
import com.openlopd.sessionbeans.interfaz.OperacionLopdFacadeLocal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Negocio de los documentos de seguridad.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 15 de septiembre de 2012du
 */
@Stateless
public class DocumentoSeguridadFacade extends AbstractFacadeDataTable<DocumentoSeguridad> implements DocumentoSeguridadFacadeLocal {
    @EJB
    private OperacionLopdFacadeLocal operacionLopdFacade;
    private static Logger logger = LoggerFactory.getLogger(DocumentoSeguridadFacade.class);
    
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DocumentoSeguridadFacade() {
        super(DocumentoSeguridad.class);
    }    

    @Override
    public void create(AccessInfo accessInfo, DocumentoSeguridad documentoSeguridad) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException {
        create(accessInfo, ColumnasPermisos.DOCUMENTO_SEGURIDAD, 
                TipoOperacion.DocumentoSeguridad, documentoSeguridad);
        
    }

    @Override
    public void edit(AccessInfo accessInfo, DocumentoSeguridad documentoSeguridad) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException {
        edit(accessInfo, ColumnasPermisos.DOCUMENTO_SEGURIDAD, 
                TipoOperacion.DocumentoSeguridad, documentoSeguridad);
    }

    @Override
    public void remove(AccessInfo accessInfo, DocumentoSeguridad documentoSeguridad) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException {
        remove(accessInfo, ColumnasPermisos.DOCUMENTO_SEGURIDAD, 
                TipoOperacion.DocumentoSeguridad, documentoSeguridad);
    }

    @Override
    public SingularAttribute getExpression(int iShortCol) {

        if (iShortCol == 0) {
            return DocumentoSeguridad_.fechaAlta;
        }

        if (iShortCol == 1) {
            return DocumentoSeguridad_.fechaBaja;
        }

        return DocumentoSeguridad_.fechaAlta;
    }

    @Override
    public SingularAttribute getEmpresaAttribute() {
        return DocumentoSeguridad_.empresa;
    }

    @Override
    public SingularAttribute getBorradoAttribute() {
        return DocumentoSeguridad_.borrado;
    }

    @Override
    public SingularAttribute getActiveAttribute() {
        return DocumentoSeguridad_.active;
    }

    /**
     * Obtiene el documento de seguridad activo de una empresa.
     * @param accessInfo información de acceso. Se utilizará para identificar
     * la empresa y otra información de acceso de la misma.
     * @return 
     */
    public List<DocumentoSeguridad> getActive(AccessInfo accessInfo) {
        try {
            Query q = em.createNamedQuery("DocumentoSeguridad.findActive");
            q.setParameter("empresa", accessInfo.getSubEmpresa());
            
            return q.getResultList();
        } catch (NoResultException e) {
            logger.info("No hay documento de seguridad para la empresa[{}]", 
                    accessInfo.getSubEmpresa().getIdEmpresa());
            return null;
        } catch (Exception e) {
            logger.error("Imposible obtener el documento de seguridad activo de [{}]",
                    accessInfo.getSubEmpresa().getIdEmpresa());
            throw new PersistenceException("Imposible obtener el documento de seguridad "
                    + "activo de [{" + accessInfo.getSubEmpresa().getIdEmpresa() + "}]");
        }
    }

    @Override
    public DocumentoSeguridad cumplimentarDocSeg(AccessInfo accessInfo) 
            throws UnknownColumnException, SeguridadWriteException, 
            SeguridadWriteLimitException {
        Long now = new Date().getTime();

        List<DocumentoSeguridad> ds = this.getActive(accessInfo);

        // Cumplimentamos la nueva plantilla.
        FileDataBase fdb = new CumplimentarPlantilla(accessInfo, null, 
                ColumnasPermisos.DOCUMENTO_SEGURIDAD)
                .generar("Documento de Seguridad");

        if (fdb != null) {
            // Damos de baja el DS anterior.
            for(DocumentoSeguridad d : ds) {                
                d.setFechaBaja(now);
                this.edit(accessInfo, d);
            }

            // Creamos el nuevo DS.
            DocumentoSeguridad nds = new DocumentoSeguridad(GenKey.newKey(), accessInfo.getSubEmpresa(),
                    now, fdb);
            this.create(accessInfo, nds);

            return nds;
        } else {
            return null;
        }
    }

    @Override
    public Predicate getLikeFilter(CriteriaBuilder cb, Root<DocumentoSeguridad> root, String filterText) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
