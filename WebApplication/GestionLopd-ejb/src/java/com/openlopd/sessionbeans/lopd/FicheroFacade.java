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

import com.openlopd.agpd.nota.tablascomunes.CategoriasTransferenciasInternacionales;
import com.openlopd.agpd.nota.tablascomunes.OtrosDatosTipificados;
import com.openlopd.agpd.nota.tablascomunes.TipoSolicitud;
import com.openlopd.agpd.nota.tablascomunes.TiposCesiones;
import com.openlopd.agpd.nota.xml.DatosFichero;
import com.openlopd.agpd.nota.xml.XmlEnvioFactory;
import com.openlopd.business.files.GenerarDocumentosLocal;
import com.openlopd.business.lopd.FileRegisterBot;
import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.entities.documentos.FileDataBase;
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.interfaz.TipoOperacion;
import com.openlopd.entities.lopd.Fichero;
import com.openlopd.entities.lopd.Fichero_;
import com.openlopd.entities.lopd.TipoDeFichero;
import com.openlopd.entities.lopd.TipoNivelSeguridad;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.entities.seguridad.utils.primarykey.GenKey;
import com.openlopd.exceptions.SeguridadReadException;
import com.openlopd.exceptions.SeguridadWriteException;
import com.openlopd.exceptions.SeguridadWriteLimitException;
import com.openlopd.sessionbeans.AbstractFacadeDataTable;
import com.openlopd.common.localizacion.sessionbeans.PaisFacadeLocal;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gestión de los ficheros (AEPD) de la empresa.
 *
 * @author Eduardo L. García Glez.
 */
@Stateless
public class FicheroFacade extends AbstractFacadeDataTable<Fichero> implements FicheroFacadeLocal {
    @EJB
    private FileRegisterBot fileRegisterBot;
    @EJB
    private PaisFacadeLocal paisFacade;
    private static Logger logger = LoggerFactory.getLogger(FicheroFacade.class);
    @EJB
    private GenerarDocumentosLocal generarDocumentos;
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FicheroFacade() {
        super(Fichero.class);
    }

    @Override
    public void create(AccessInfo accessInfo, Fichero fichero)
            throws UnknownColumnException, SeguridadWriteException,
            SeguridadWriteLimitException {
        create(accessInfo, ColumnasPermisos.FICHEROS,
                TipoOperacion.Ficheros, fichero);
    }

    @Override
    public void edit(AccessInfo accessInfo, Fichero fichero)
            throws UnknownColumnException, SeguridadWriteException,
            SeguridadWriteLimitException {
        edit(accessInfo, ColumnasPermisos.FICHEROS,
                TipoOperacion.Ficheros, fichero);
    }

    @Override
    public void remove(AccessInfo accessInfo, Fichero fichero)
            throws UnknownColumnException, SeguridadWriteException,
            SeguridadWriteLimitException {
        remove(accessInfo, ColumnasPermisos.FICHEROS,
                TipoOperacion.Ficheros, fichero);
    }

    @Override
    public List<Fichero> findActives(Empresa empresa) {
        Query q = em.createNamedQuery("Fichero.findActives");
        q.setParameter("empresa", empresa);
        return q.getResultList();
    }

    /**
     * Obtiene el listado de cargos/perfiles de un fichero.
     *
     * @param accessInfo información de acceso del usuario que realiza la
     * consulta.
     * @param f fichero sobre el que consultar.
     * @return listado de perfiles asociados con el fichero.
     */
    public List<String> findPerfiles(AccessInfo accessInfo, Fichero f) {
        Query q = em.createNamedQuery("Fichero.findPerfiles", String.class);
        q.setParameter("empresa", accessInfo.getSubEmpresa());
        q.setParameter("nombre", f.getNombre());
        return q.getResultList();
    }

    @Override
    public String findPerfilesAsString(AccessInfo accessInfo, Fichero f) {
        StringBuilder p = new StringBuilder();
        for (String perfil : findPerfiles(accessInfo, f)) {
            p.append(", ").append(perfil);
        }
        return p.length() > 0 ? p.delete(0, 2).toString() : null;
    }

    @Override
    public List<Fichero> findFicherosDisponiblesPersona(AccessInfo accessInfo,
            String idPersona) {
        Query q = em.createNamedQuery("Fichero.findFicherosDisponiblesPersona");
        q.setParameter("empresa", accessInfo.getSubEmpresa());
        q.setParameter("idPersona", idPersona);
        return q.getResultList();
    }

    @Override
    public List<Fichero> findFicherosHabilitadosPersona(AccessInfo accessInfo,
            String idPersona) {
        Query q = em.createNamedQuery("Fichero.findFicherosHabilitadosPersona");
        q.setParameter("empresa", accessInfo.getSubEmpresa());
        q.setParameter("idPersona", idPersona);
        return q.getResultList();
    }
    
    
    @Override
    public List<Fichero> findFicherosConError(AccessInfo accessInfo) {
        try {
            if (!accessInfo.getPermisosSubEmpresa().hasAccess(ColumnasPermisos.SYS_ADMIN)) {
                return null;
            }
            Query q = em.createNamedQuery("Fichero.findWithErrors");
            q.setParameter("errorCode", "00");
            return q.getResultList();
        } catch (UnknownColumnException ex) {
            logger.error("Imposible!!! el permiso SYS_ADMIN debe existir.");
            return null;
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Información extra de los ficheros">
    private String getCesionDatos(DatosFichero datosFichero) {

        String[] cesiones = datosFichero.getDestCesiones().split(";");
        StringBuilder ces = new StringBuilder();
        for (String cesion : cesiones) {
            ces.append(", ").append(TiposCesiones.getText(cesion));
        }
        if (datosFichero.getOtrosDestCesiones() != null
                && !datosFichero.getOtrosDestCesiones().isEmpty()) {
            ces.append(", ").append(datosFichero.getOtrosDestCesiones().toUpperCase());
        }
        return ces.delete(0, 2).toString();
    }

    private String getTipoDatos(DatosFichero datosFichero) {

        StringBuilder tiposDatos = new StringBuilder();
        if (DatosFichero.AGPD_TRUE.equals(datosFichero.getDatEspProtIdeologia())) {
            tiposDatos.append(", ").append("IDEOLOGÍA");
        }
        if (DatosFichero.AGPD_TRUE.equals(datosFichero.getDatEspProtAfiliacionSindical())) {
            tiposDatos.append(", ").append("AFILIACIÓN SINDICAL");
        }
        if (DatosFichero.AGPD_TRUE.equals(datosFichero.getDatEspProtReligion())) {
            tiposDatos.append(", ").append("RELIGIÓN");
        }
        if (DatosFichero.AGPD_TRUE.equals(datosFichero.getDatEspProtCreencias())) {
            tiposDatos.append(", ").append("CREENCIAS");
        }
        if (DatosFichero.AGPD_TRUE.equals(datosFichero.getOtrosEspProtOrigenRacial())) {
            tiposDatos.append(", ").append("ORIGEN RACIAL O ÉTNICO");
        }
        if (DatosFichero.AGPD_TRUE.equals(datosFichero.getOtrosEspProtSalud())) {
            tiposDatos.append(", ").append("SALUD");
        }
        if (DatosFichero.AGPD_TRUE.equals(datosFichero.getOtrosEspprotVidaSexual())) {
            tiposDatos.append(", ").append("VIDA SEXUAL");
        }
        if (DatosFichero.AGPD_TRUE.equals(datosFichero.getIdentifNIF())) {
            tiposDatos.append(", ").append("CIF/DNI");
        }
        if (DatosFichero.AGPD_TRUE.equals(datosFichero.getIdentifNSS())) {
            tiposDatos.append(", ").append("Nº SS/MUTUALIDAD");
        }
        if (DatosFichero.AGPD_TRUE.equals(datosFichero.getIdentifNombreApp())) {
            tiposDatos.append(", ").append("NOMBRE Y APELLIDOS");
        }
        if (DatosFichero.AGPD_TRUE.equals(datosFichero.getIdentifTarjSanitaria())) {
            tiposDatos.append(", ").append("TARJETA SANITARIA");
        }
        if (DatosFichero.AGPD_TRUE.equals(datosFichero.getIdentifDireccion())) {
            tiposDatos.append(", ").append("DIRECCIÓN");
        }
        if (DatosFichero.AGPD_TRUE.equals(datosFichero.getIdentifTel())) {
            tiposDatos.append(", ").append("TELÉF1O");
        }
        if (DatosFichero.AGPD_TRUE.equals(datosFichero.getIdentifFirmaHuella())) {
            tiposDatos.append(", ").append("FIRMA/HUELLA");
        }
        if (DatosFichero.AGPD_TRUE.equals(datosFichero.getIdentifImagVoz())) {
            tiposDatos.append(", ").append("IMAGEN/VOZ");
        }
        if (DatosFichero.AGPD_TRUE.equals(datosFichero.getIdentifMarcasFisicas())) {
            tiposDatos.append(", ").append("MARCAS FÍSICAS");
        }
        if (DatosFichero.AGPD_TRUE.equals(datosFichero.getIdentifFirmaElectronica())) {
            tiposDatos.append(", ").append("FIRMA ELECTRÓNICA");
        }
        if (!datosFichero.getIdentifOtros().isEmpty()) {
            tiposDatos.append(", ").append(datosFichero.getIdentifOtros().toUpperCase());
        }
        
        if (!datosFichero.getOtrosDatosTipificados().isEmpty()) {
            String[] tipos = datosFichero.getOtrosDatosTipificados().split(";");
            for (String tipo : tipos) {
                tiposDatos.append(", ").append(OtrosDatosTipificados.getText(tipo));
            }
        }
        if (datosFichero.getOtrosTiposDeDatos() != null
                && !datosFichero.getOtrosTiposDeDatos().isEmpty()) {
            tiposDatos.append(", ").append(datosFichero.getOtrosTiposDeDatos().toUpperCase());
        }
        return tiposDatos.delete(0, 2).toString();
    }

    public String getOrigenDatos(DatosFichero datosFichero) {

        StringBuilder origenDatos = new StringBuilder();
        
        if (DatosFichero.AGPD_TRUE.equals(datosFichero.getOrigenInte())) {
            origenDatos.append(", ").append("EL PROPIO INTERESADO O SU REPRESENTANTE LEGAL");
        }
        if (DatosFichero.AGPD_TRUE.equals(datosFichero.getOrigenOtras())) {
            origenDatos.append(", ").append("OTRAS PERSONAS FÍSICAS");
        }
        if (DatosFichero.AGPD_TRUE.equals(datosFichero.getOrigenFap())) {
            origenDatos.append(", ").append("FUENTES ACCESIBLES AL PÚBLICO");
        }
        if (DatosFichero.AGPD_TRUE.equals(datosFichero.getOrigenRp())) {
            origenDatos.append(", ").append("REGISTROS PÚBLICOS");
        }
        if (DatosFichero.AGPD_TRUE.equals(datosFichero.getOrigenEp())) {
            origenDatos.append(", ").append("ENTIDAD PRIVADA");
        }
        if (DatosFichero.AGPD_TRUE.equals(datosFichero.getOrigenAp())) {
            origenDatos.append(", ").append("ADMINISTRACIONES PÚBLICAS");
        }
        return origenDatos.delete(0, 2).toString();
    }

    public String getTransferenciasInternacionales(DatosFichero datosFichero) {
        StringBuilder transferencias = new StringBuilder();
        String[] codPaises = datosFichero.getTransCodPais().isEmpty() ? new String[0]
                : datosFichero.getTransCodPais().split(";");
        String[] categorias = (datosFichero.getTransCategoria() + ";"
                + datosFichero.getTransCategoriaOtros()).split(";");
        for (int i = 0; i < codPaises.length; i++) {
            transferencias.append("; ").append(paisFacade.findByAGPDCode(codPaises[i]).getPais())
                    .append(", ")
                    .append(CategoriasTransferenciasInternacionales.getText(categorias[i]));
        }
        return transferencias.delete(0, 2).toString();
//        return "";
    }
    //</editor-fold>
    
    /**
     * Establece el código de inscripción de un fichero.
     * @param accessInfo información de acceso del usuari al sistema.
     * @param nombreFichero nombre del fichero a modificar.
     * @param codInscripcion nuevo código de inscripción.
     * @return fichero modificado.
     * @throws SeguridadWriteException Si el usuario no tiene permisos de escritura.
     * @throws UnknownColumnException No se debería producir.
     * @throws SeguridadReadException Si el usuario no tiene permisos de lectura.
     * @throws SeguridadWriteLimitException  Si se excede el límite de escrituras.
     */
    @Override
    public Fichero updateCodInscripcion(AccessInfo accessInfo, 
            String nombreFichero, String codInscripcion) 
            throws SeguridadWriteException, UnknownColumnException, 
            SeguridadReadException, SeguridadWriteLimitException {
        
        Fichero f = this.findByName(accessInfo, nombreFichero);
        f.setCodInscripcion(codInscripcion);
        this.edit(accessInfo, f);
        return f;
    }
    
    @Override
    public boolean hasAllRegistrationCodes(AccessInfo accessInfo) {
        Query q = em.createNamedQuery("Fichero.findWithoutIncrpCod");
        q.setParameter("empresa", accessInfo.getSubEmpresa());
        return q.getResultList().isEmpty();
    }
    
    private Fichero findByName(AccessInfo accessInfo, 
            String nombreFichero) throws UnknownColumnException, SeguridadReadException {
        super.verificarPermisosLectura(accessInfo, ColumnasPermisos.FICHEROS);
        Query q = em.createNamedQuery("Fichero.findByNombre");
        q.setParameter("nombre", nombreFichero);
        q.setParameter("empresa", accessInfo.getSubEmpresa());
        return (Fichero) q.getSingleResult();
    }
    
        /**
     * Establece si el sistema automático de registro de ficheros está activo.
     * @return true el sistema está activado, false en caso contrario.
     */
    @Override
    public boolean isAutoRegistroActivo() {
        return fileRegisterBot.isAutoRegistroActivo();
    }

    /**
     * Obtiene si el sistema automático de registro de ficheros está activo.
     * @param autoRegistroActivo true el sistema está activado, false en 
     * caso contrario.
     */
    @Override
    public void setAutoRegistroActivo(boolean autoRegistroActivo) {
        fileRegisterBot.setAutoRegistroActivo(autoRegistroActivo);
    } 

    /**
     * Gruarda la información de una solicitud de Alta/Baja/Modificación de un fichero.
     * @param accessInfo Información de acceso del usuario al sistema.
     * @param datosFichero Datos de la solicitud.
     * @param registrar Indica si es necesario realizar el registro en la agencia,
     * true realiza el envío a la agencia, false no realiza el envío.
     * @return Fichero añadido al sistama.
     * @throws Exception 
     */
    @Override
    public Fichero realizarSolicitud(AccessInfo accessInfo, DatosFichero datosFichero, 
        boolean registrar, boolean firmar) throws Exception {
        switch (datosFichero.getAccion()) {
            case "1":
                return alta(accessInfo, datosFichero, registrar, firmar);
            case "2":
                return modificacion(accessInfo, datosFichero, firmar);
            case "3":
                return supresion(accessInfo, datosFichero, firmar);
            default:
                throw new Exception("Accion solicitada no encontrada");
        }
    }

    private Fichero alta(AccessInfo accessInfo, DatosFichero datosFichero, 
        boolean registrar, boolean firmar)
            throws Exception {
        
        FileDataBase xmlSol = null;
        if (firmar) {
            xmlSol = generarDocumentos.GenerarNotificacionAgpdFirmada(datosFichero, null);
        } else {
            xmlSol = generarDocumentos.GenerarNotificacionAgpd(datosFichero, null);
        }

        Fichero f = new Fichero(GenKey.newKey(), datosFichero.getEmpresa(), 
                datosFichero.getDenominacion(), GenKey.newKey());
        f.setDescripcion(datosFichero.getUsosPrevistos());
        f.setFechaAltaInterna(new Date().getTime());
        f.setNivel(TipoNivelSeguridad.values()[new Integer(datosFichero.getNivel())]);
        f.setTipo(TipoDeFichero.values()[new Integer(datosFichero.getSistTratamiento()) - 1]);
        f.setSolicitud(xmlSol);
        f.setAccion(TipoSolicitud.values()[new Integer(datosFichero.getAccion()) - 1]);
        f.setUsuario(datosFichero.getUserInfo().getUsuario());
        f.setCesiones(this.getCesionDatos(datosFichero));
        f.setTipoDatos(this.getTipoDatos(datosFichero));
        f.setOrigenDatos(this.getOrigenDatos(datosFichero));
        f.setTransferenciasInternacionales(this.getTransferenciasInternacionales(datosFichero));

        if (logger.isDebugEnabled()) {
            logger.debug("Añadido xml de fichero de alta fileId[{}]", xmlSol.getId());
        }
        
        if(!registrar) {
            f.setError(FileRegisterBot.ENVIO_OK);
        }
        
        if(!firmar) {
            f.setActive(false);
        }

        this.create(accessInfo, f);

        return f;
    }

    private Fichero modificacion(AccessInfo accessInfo, DatosFichero datosFichero,
            boolean firmar) throws Exception {
        Fichero f = this.find(datosFichero.getId());
        FileDataBase solXML = null;
        
        if (firmar) {
            solXML = generarDocumentos.GenerarNotificacionAgpdFirmada(datosFichero, f);
        } else {
            solXML = generarDocumentos.GenerarNotificacionAgpd(datosFichero, f);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Añadido xml de fichero de modificacion fileId[{}]", solXML.getId());
        }

        f.setActive(false);
        this.edit(accessInfo, f);

        Fichero ficheroModificado = new Fichero(GenKey.newKey(), f.getEmpresa(), 
                datosFichero.getDenominacion(), f.getNombreInterno());
        ficheroModificado.setDescripcion(datosFichero.getUsosPrevistos());
        ficheroModificado.setFechaAltaInterna(new Date().getTime());
        ficheroModificado.setSolicitud(solXML);
        ficheroModificado.setNivel(TipoNivelSeguridad.values()[new Integer(datosFichero.getNivel())]);
        ficheroModificado.setTipo(TipoDeFichero.values()[new Integer(datosFichero.getSistTratamiento())]);
        ficheroModificado.setAccion(TipoSolicitud.MODIFICACION);
        ficheroModificado.setUsuario(datosFichero.getUserInfo().getUsuario());
        ficheroModificado.setCesiones(this.getCesionDatos(datosFichero));
        ficheroModificado.setTipoDatos(this.getTipoDatos(datosFichero));
        ficheroModificado.setOrigenDatos(this.getOrigenDatos(datosFichero));
        ficheroModificado.setTransferenciasInternacionales(this.getTransferenciasInternacionales(datosFichero));       
        
        if(!firmar) {
            f.setActive(false);
        }
        
        //TODO: Pendiente de verificar si la fecha y número de registro deben conservarse.
        this.create(accessInfo, ficheroModificado);

        return ficheroModificado;
    }

    private Fichero supresion(AccessInfo accessInfo, DatosFichero datosFichero,
            boolean firmar) throws Exception {
        Fichero f = this.find(datosFichero.getId());
        
        FileDataBase solXML = null;
        if (firmar) {
            solXML = generarDocumentos.GenerarNotificacionAgpdFirmada(datosFichero, f);
        } else {
            solXML = generarDocumentos.GenerarNotificacionAgpd(datosFichero, f);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Añadido xml de fichero de supresión fileId[{}]", solXML.getId());
        }
        
        f.setActive(false);
        this.edit(accessInfo, f);

        Fichero ficheroBorrado = new Fichero(GenKey.newKey(), f.getEmpresa(), 
                datosFichero.getDenominacion(), f.getNombreInterno());
        ficheroBorrado.setDescripcion(datosFichero.getUsosPrevistos());
        ficheroBorrado.setSolicitud(solXML);
        ficheroBorrado.setNivel(TipoNivelSeguridad.values()[new Integer(datosFichero.getNivel())]);
        ficheroBorrado.setTipo(TipoDeFichero.values()[new Integer(datosFichero.getSistTratamiento())]);
        ficheroBorrado.setAccion(TipoSolicitud.SUPRESION);
        ficheroBorrado.setUsuario(datosFichero.getUserInfo().getUsuario()); 
        ficheroBorrado.setCesiones(this.getCesionDatos(datosFichero));
        ficheroBorrado.setTipoDatos(this.getTipoDatos(datosFichero));
        ficheroBorrado.setOrigenDatos(this.getOrigenDatos(datosFichero));
        ficheroBorrado.setTransferenciasInternacionales(this.getTransferenciasInternacionales(datosFichero));
        
        ficheroBorrado.setFechaAltaInterna(new Date().getTime());
        ficheroBorrado.setBorradoPor(ficheroBorrado.getUsuario());
        ficheroBorrado.setBorrado(ficheroBorrado.getFechaAltaInterna());        
        
        if(!firmar) {
            f.setActive(false);
        }
        
        //TODO: Pendiente de verificar si la fecha y número de registro deben conservarse.
        this.create(ficheroBorrado);

        return ficheroBorrado;
    }

    /**
     * Obtiene el nivel de seguridad que tiene que implementar la empresa.
     *
     * @param accessInfo Información de acceso de la empresa.
     * @return Nivel de seguridad asociado a la empresa gestionada.
     */
    @Override
    public TipoNivelSeguridad getNivel(AccessInfo accessInfo) {
        List<Fichero> listaFicheros = this.findActives(accessInfo.getSubEmpresa());
        TipoNivelSeguridad nivel = TipoNivelSeguridad.Basico;

        for (Fichero f : listaFicheros) {
            switch (f.getNivel().name()) {
                case "Medio":
                    if (nivel == TipoNivelSeguridad.Basico) {
                        nivel = TipoNivelSeguridad.Medio;
                    }
                    break;
                case "Alto":
                    nivel = TipoNivelSeguridad.Alto;
                    break;
            }
        }
        return nivel;
    }

    /**
     * Obtiene si la empresa tiene que implementar un nivel de seguridad medio.
     *
     * @return true en caso de implementar nivel medio false en caso contrario.
     */
    @Override
    public Boolean isNivelMedioSystem(AccessInfo accessInfo) {
        TipoNivelSeguridad nivel = getNivel(accessInfo);
        if (nivel == TipoNivelSeguridad.Medio || nivel == TipoNivelSeguridad.Alto) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * Devuelve un listado de todos los ficheros en el sistema que no están
     * registrados
     *
     * Este listado es solo para administratores de sistema o el sistema
     * automatizado de registro.
     *
     * @return Listado de ficheros que aún no han sido registrados en la AGPD.
     */
    @Override
    public List<Fichero> ficherosSinRegistrar() {
        Query q = em.createNamedQuery("Fichero.findNoRegistrados");
        //TODO: Obtener el código de error del sistema.
        q.setParameter("errorCodeOK", FileRegisterBot.ENVIO_OK);
        return q.getResultList();
    }
    
    @Override
    public List<Fichero> pendientesFirmar(AccessInfo accessInfo) {
        Query q = em.createNamedQuery("Fichero.findPendienteFirma");
        q.setParameter("empresa", accessInfo.getSubEmpresa());
        return q.getResultList();
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
        Fichero f = this.find(id); //TODO: uso de accessInfo.
        InputStreamReader fis = null;

        try {
//            fis = new InputStreamReader(new ByteArrayInputStream(f.getSolicitud().getFile()), "ISO-8859-1");
            fis = new InputStreamReader(new ByteArrayInputStream(f.getSolicitud().getFile()));
            XmlEnvioFactory.load(datosFichero, fis);
            datosFichero.setCodInscripcion(f.getCodInscripcion());
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

    @Override
    public SingularAttribute getExpression(int iShortCol) {
        if (iShortCol == 0) {
            return Fichero_.nombre;
        }
        return Fichero_.nombre;
    }

    @Override
    public SingularAttribute getEmpresaAttribute() {
        return Fichero_.empresa;
    }

    @Override
    public SingularAttribute getBorradoAttribute() {
        return Fichero_.borrado;
    }

    @Override
    public SingularAttribute getActiveAttribute() {
        return Fichero_.active;
    }

    @Override
    public Predicate getLikeFilter(CriteriaBuilder cb, Root<Fichero> root, String filterText) {
        String filter = filterText.toLowerCase();
        Predicate likeNombre = cb.like(cb.lower(root.get(Fichero_.nombre)), "%" + filter + "%");
        Predicate likeDesc = cb.like(cb.lower(root.get(Fichero_.descripcion)), "%" + filter + "%");
        Predicate likeNumReg = cb.like(cb.lower(root.get(Fichero_.numeroDeRegistro)), "%" + filter + "%");
        Predicate likeFechReg = cb.like(cb.lower(root.get(Fichero_.fechaHoraRegistro)), "%" + filter + "%");
        Predicate likeCodIns = cb.like(cb.lower(root.get(Fichero_.codInscripcion)), "%" + filter + "%");
        return cb.or(likeNombre, likeDesc, likeNumReg, likeFechReg, likeCodIns);
    }
}
