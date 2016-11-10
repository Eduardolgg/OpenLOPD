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

import com.openlopd.agpd.nota.xml.DatosFichero;
import com.openlopd.agpd.nota.xml.XmlEnvioFactory;
import com.openlopd.business.files.GenerarDocumentosLocal;
import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.entities.documentos.FileDataBase;
import com.openlopd.entities.interfaz.TipoOperacion;
import com.openlopd.entities.lopd.FicheroPredefinido;
import com.openlopd.entities.lopd.FicheroPredefinido_;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.entities.seguridad.utils.primarykey.GenKey;
import com.openlopd.exceptions.SeguridadReadException;
import com.openlopd.exceptions.SeguridadWriteException;
import com.openlopd.exceptions.SeguridadWriteLimitException;
import com.openlopd.sessionbeans.AbstractFacadeDataTable;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gestión de las entidades de ficheros predefinidos.
 * @author Eduardo L. García Glez.
 */
@Stateless
public class FicheroPredefinidoFacade extends AbstractFacadeDataTable<FicheroPredefinido> implements FicheroPredefinidoFacadeLocal {
    private static Logger logger = LoggerFactory.getLogger(FicheroPredefinido.class);
    @EJB
    private GenerarDocumentosLocal generarDocumentos;
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FicheroPredefinidoFacade() {
        super(FicheroPredefinido.class);
    }
    
    @Override
    public void create(AccessInfo accessInfo, FicheroPredefinido ficheroPredefinido)
            throws UnknownColumnException, SeguridadWriteException,
            SeguridadWriteLimitException {
        create(accessInfo, ColumnasPermisos.FICHEROS,
                TipoOperacion.Ficheros, ficheroPredefinido);
    }

    @Override
    public void edit(AccessInfo accessInfo, FicheroPredefinido ficheroPredefinido)
            throws UnknownColumnException, SeguridadWriteException,
            SeguridadWriteLimitException {
        edit(accessInfo, ColumnasPermisos.FICHEROS,
                TipoOperacion.Ficheros, ficheroPredefinido);
    }        

    @Override
    public void remove(AccessInfo accessInfo, FicheroPredefinido ficheroPredefinido)
            throws UnknownColumnException, SeguridadWriteException,
            SeguridadWriteLimitException {
        remove(accessInfo, ColumnasPermisos.FICHEROS,
                TipoOperacion.Ficheros, ficheroPredefinido);
    }
    
    @Override
    public FicheroPredefinido create(AccessInfo accessInfo, DatosFichero datosFichero)
            throws Exception {
        FileDataBase xmlFile = generarDocumentos.GenerarNotificacionAgpd(datosFichero, null);
        
        FicheroPredefinido f = new FicheroPredefinido();
        f.setId(GenKey.newKey());
        f.setNombre(datosFichero.getDenominacion());
        f.setDescripcion(datosFichero.getUsosPrevistos());
        f.setEmpresa(accessInfo.getSubEmpresa());
        f.setFechaAltaInterna(new Date().getTime());
        f.setUsuario(accessInfo.getUserInfo().getUsuario());
        f.setFichero(xmlFile);
        this.create(accessInfo, f);
        
        return f;
    }
    
    /**
     * Carga en datosFichero los datos del xml del fichero predefinido.
     * @param accessInfo Información de acceso del usuario.
     * @param datosFichero Objeto a cumplimentar por load.
     * @param id Id del fichero del que extraer los datos.
     * @throws JAXBException Se lanza si existen problemas al leer el XML.
     */
    @Override
    public void load(AccessInfo accessInfo, DatosFichero datosFichero, String id) throws JAXBException {
        FicheroPredefinido f = this.find(id); //TODO: uso de accessInfo.
        InputStreamReader fis = null;

        try {
//            fis = new InputStreamReader(new ByteArrayInputStream(f.getFichero().getFile()), "ISO-8859-1");
            fis = new InputStreamReader(new ByteArrayInputStream(f.getFichero().getFile()));
            XmlEnvioFactory.load(datosFichero, fis);
            datosFichero.setCodInscripcion("");
//        } catch (UnsupportedEncodingException ex) {
//            logger.error("Encode ISO-8859-1 no soportado!!! no puede ser!!!");
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                logger.error(ex.getMessage());
            }
        }
    }
    
    /**
     * Obtiene el listado de ficheros predefinidos.
     * @param accessInfo Información de acceso del usuario.
     * @return Listado de ficheros predefinidos.
     * @throws UnknownColumnException No debería lanzarse nunca, si se lanza es
     * un error de sistema.
     * @throws SeguridadReadException Si el usuario no tiene permisos de lectura.
     */
    @Override
    public List<FicheroPredefinido> findActives(AccessInfo accessInfo)
            throws UnknownColumnException, SeguridadReadException {
        super.verificarPermisosLectura(accessInfo, ColumnasPermisos.FICHEROS);
        Query q = em.createNamedQuery("FicheroPredefinido.findActives");
        return q.getResultList();
    }
    
    @Override
    public SingularAttribute getExpression(int iShortCol) {
        if (iShortCol == 0) {
            return FicheroPredefinido_.nombre;
        }
        return FicheroPredefinido_.nombre;
    }

    @Override
    public SingularAttribute getEmpresaAttribute() {
        return FicheroPredefinido_.empresa;
    }

    @Override
    public SingularAttribute getBorradoAttribute() {
        return FicheroPredefinido_.borrado;
    }

    @Override
    public SingularAttribute getActiveAttribute() {
        return FicheroPredefinido_.active;
    }

    @Override
    public Predicate getLikeFilter(CriteriaBuilder cb, Root<FicheroPredefinido> root, String filterText) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
