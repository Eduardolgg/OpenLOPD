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

package com.openlopd.agpd.nota.xml;

import com.openlopd.agpd.nota.tablascomunes.TipoSolicitud;
import com.openlopd.agpd.nota.tablascomunes.FormaCumplimentacion;
import com.openlopd.agpd.nota.tablascomunes.Signatura;
import com.openlopd.agpd.nota.tablascomunes.Soporte;
import com.openlopd.agpd.nota.tablascomunes.MedioNotificacion;
import com.openlopd.agpd.nota.tablascomunes.ModificacionesEnvio;
import com.elgg.utils.Calendar.ManejadorFechas;
import com.openlopd.entities.documentos.FileDataBase;
import com.openlopd.entities.lopd.Fichero;
import com.openlopd.entities.lopd.TipoNivelSeguridad;
import es.agpd.nota.dos.cero.Envio;
import es.agpd.nota.dos.cero.ObjectFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Permite cumplimentar XML o utilizar existentes para el
 * Alta/Modificación/Supresión de ficheros en la AGPD.
 *
 * Esta clase extiente la clase Envío autogenerada, autocumplimentando varios
 * campos.
 *
 *
 * @author Eduardo L. García Glez.
 */
public class XmlEnvioFactory {

    private static Logger logger = LoggerFactory.getLogger(XmlEnvioFactory.class);
    /**
     * Indicaciones de la AGPD: Rellenar con Cero.
     */
    private static final String IND_ROCK = "0";
    /**
     * Indicaciones de la AGPD: Se rellena con “0”. Se trata de un código de
     * tratamiento interno del Registro General de Protección de Datos. 1
     * carácter.
     */
    private static final String IND_PROCESADO = "0";
    /**
     * Indicaciones de la AGPD: Tipo de registro. Siempre debe ser “1”. 1
     * carácter.
     */
    private static final String ID_ROCK = "1";
    public static final String ACCION_ALTA = TipoSolicitud.ALTA.getValue();
    public static final String ACCION_SUPR = TipoSolicitud.SUPRESION.getValue();
    public static final String ACCION_MODI = TipoSolicitud.MODIFICACION.getValue();
    private static final ObjectFactory of = new ObjectFactory();
    private static final ResourceBundle rb = ResourceBundle.getBundle("com.openlopd.config.config", Locale.getDefault());

    public XmlEnvioFactory() {
    }

    //<editor-fold defaultstate="collapsed" desc="read/write xml">
    public static File writeXml(Envio e, File xml) throws javax.xml.bind.JAXBException {
        
        javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(e.getClass().getPackage().getName());
        javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
                marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
//        marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "ISO-8859-1"); //NOI18N
        marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(e, xml);
        return xml;
    }
    
    @Deprecated
    public static Envio readXml(File file) throws JAXBException {
        javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(Envio.class.getPackage().getName());
        javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
        return (Envio) unmarshaller.unmarshal(file); //NOI18N
    }
    
    public static Envio readXml(Reader file) throws JAXBException {
        javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(Envio.class.getPackage().getName());
        javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
        return (Envio) unmarshaller.unmarshal(file); //NOI18N
    }
    
    public static Envio readXml(byte[] file) throws JAXBException {
        InputStreamReader fis = null;
        Envio e = null;
        try {
//            fis = new InputStreamReader(new ByteArrayInputStream(file), "ISO-8859-1"); 
            fis = new InputStreamReader(new ByteArrayInputStream(file), "UTF-8"); 
            e = readXml(fis);           
        } catch (UnsupportedEncodingException ex) {
            logger.error("Imposible el encoding 'ISO-8859-1' no existe !!!");
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                logger.error("No se puede cerrar el flujo de entrada.");
            }
        }
        return e;
    }
    
    public static Envio readXml(FileDataBase file) throws JAXBException {
        return readXml(file.getFile());
    }    
    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="load Method">
    public static void load(DatosFichero datosFichero, Reader file) throws JAXBException {
        Envio xml = readXml(file);
        //<editor-fold defaultstate="collapsed" desc="Derechos ARCO">
        Envio.RegUno.Declaracion.Responsable.Derecho d = xml.getRegUno()
                .getDeclaracion().get(0).getResponsable().getDerecho();
        datosFichero.setDerechosOficina(d.getOficina());
        datosFichero.setDerechosCif(d.getNifCif());
        datosFichero.setDerechosDireccionPostal(d.getDirPostal());
        datosFichero.setDerechosLocalidad(d.getLocalidad());
        datosFichero.setDerechosCodigoPostal(d.getPostal());
        datosFichero.setDerechosProvincia(d.getProvincia());
        datosFichero.setDerechosPais(d.getPais());
        datosFichero.setDerechosTelefono(d.getTelefono());
        datosFichero.setDerechosFax(d.getFax());
        datosFichero.setDerechosEmail(d.getEmail());
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Encargado Del Tratamiento">
        Envio.RegUno.Declaracion.Fichero.Encargado e = xml.getRegUno()
                .getDeclaracion().get(0).getFichero().get(0).getEncargado();
        datosFichero.setEncDenominacionSocial(e.getNRazon());
        datosFichero.setEncCif(e.getCifNif());
        datosFichero.setEncDomicilioSocial(e.getDirPostal());
        datosFichero.setEncLocalidad(e.getLocalidad());
        datosFichero.setEncCodigoPostal(e.getPostal());
        datosFichero.setEncProvincia(e.getProvincia());
        datosFichero.setEncPais(e.getPais());
        datosFichero.setEncTelefono(e.getTelefono());
        datosFichero.setEncFax(e.getFax());
        datosFichero.setEncEmail(e.getEmail());
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Denominación e Identificación del fichero">
        Envio.RegUno.Declaracion.Fichero.IdentificaFinalidad.Denominacion den =
                xml.getRegUno().getDeclaracion().get(0).getFichero().get(0)
                .getIdentificaFinalidad().getDenominacion();
        datosFichero.setDenominacion(den.getFichero());
        datosFichero.setUsosPrevistos(den.getDescFinUsos());
        datosFichero.setFinalidades(xml.getRegUno().getDeclaracion().get(0).getFichero().get(0)
                .getIdentificaFinalidad().getTipificacion().getFinalidades());
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Origen y procedencia de los datos">
        Envio.RegUno.Declaracion.Fichero.Procedencia.Origen po =
                xml.getRegUno().getDeclaracion().get(0).getFichero().get(0)
                .getProcedencia().getOrigen();
        datosFichero.setOrigenInte(datosFichero.getCheckBoxFromAgpdBoolean(po.getIndicaInte()));
        datosFichero.setOrigenOtras(datosFichero.getCheckBoxFromAgpdBoolean(po.getIndicaOtras()));
        datosFichero.setOrigenFap(datosFichero.getCheckBoxFromAgpdBoolean(po.getIndicFap()));
        datosFichero.setOrigenRp(datosFichero.getCheckBoxFromAgpdBoolean(po.getIndicRp()));
        datosFichero.setOrigenEp(datosFichero.getCheckBoxFromAgpdBoolean(po.getIndicEp()));
        datosFichero.setOrigenAp(datosFichero.getCheckBoxFromAgpdBoolean(po.getIndicAp()));
        Envio.RegUno.Declaracion.Fichero.Procedencia.ColectivosCateg pc =
                xml.getRegUno().getDeclaracion().get(0).getFichero().get(0)
                .getProcedencia().getColectivosCateg();
        datosFichero.setColectivos(pc.getColectivos());
        datosFichero.setOtrosColectivos(pc.getOtroCol());
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Medidas de Seguridad">
        datosFichero.setNivel(new Integer(TipoNivelSeguridad.getById(xml
                .getRegUno().getDeclaracion().get(0).getFichero().get(0)
                .getMedidasSeg().getNivel()).ordinal()).toString());
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Datos especialmente protegidos">
        Envio.RegUno.Declaracion.Fichero.Estructura.DatosEspProteg dep = xml.getRegUno()
                .getDeclaracion().get(0).getFichero().get(0).getEstructura().getDatosEspProteg();
        
        datosFichero.setDatEspProtIdeologia(datosFichero.getCheckBoxFromAgpdBoolean(dep.getIndIde()));
        datosFichero.setDatEspProtAfiliacionSindical(datosFichero.getCheckBoxFromAgpdBoolean(dep.getIndAs()));
        datosFichero.setDatEspProtReligion(datosFichero.getCheckBoxFromAgpdBoolean(dep.getIndR()));
        datosFichero.setDatEspProtCreencias(datosFichero.getCheckBoxFromAgpdBoolean(dep.getIndC()));
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Otros datos especialmete protegido">
        Envio.RegUno.Declaracion.Fichero.Estructura.OtrosEspProteg depo = xml.getRegUno()
                .getDeclaracion().get(0).getFichero().get(0).getEstructura()
                .getOtrosEspProteg();
        
        datosFichero.setOtrosEspProtOrigenRacial(datosFichero.getCheckBoxFromAgpdBoolean(depo.getIndRe()));
        datosFichero.setOtrosEspProtSalud(datosFichero.getCheckBoxFromAgpdBoolean(depo.getIndSal()));
        datosFichero.setOtrosEspprotVidaSexual(datosFichero.getCheckBoxFromAgpdBoolean(depo.getIndSexo()));
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Datos de carácter identificativo">
        Envio.RegUno.Declaracion.Fichero.Estructura.Identificativos ide = xml.getRegUno()
                .getDeclaracion().get(0).getFichero().get(0).getEstructura()
                .getIdentificativos();
        datosFichero.setIdentifNIF(datosFichero.getCheckBoxFromAgpdBoolean(ide.getIndNif()));
        datosFichero.setIdentifNSS(datosFichero.getCheckBoxFromAgpdBoolean(ide.getIndSs()));
        datosFichero.setIdentifNombreApp(datosFichero.getCheckBoxFromAgpdBoolean(ide.getIndNA()));
        datosFichero.setIdentifTarjSanitaria(datosFichero.getCheckBoxFromAgpdBoolean(ide.getIndTs()));
        datosFichero.setIdentifDireccion(datosFichero.getCheckBoxFromAgpdBoolean(ide.getIndDir()));
        datosFichero.setIdentifTel(datosFichero.getCheckBoxFromAgpdBoolean(ide.getIndTel()));
        datosFichero.setIdentifFirmaManual(datosFichero.getCheckBoxFromAgpdBoolean(ide.getIndFirmaManual()));
        datosFichero.setIdentifFirmaHuella(datosFichero.getCheckBoxFromAgpdBoolean(ide.getIndHuella()));
        datosFichero.setIdentifOtrosDatosBio(datosFichero.getCheckBoxFromAgpdBoolean(ide.getIndBiometricos()));
        datosFichero.setIdentifImagVoz(datosFichero.getCheckBoxFromAgpdBoolean(ide.getIndImg()));
        datosFichero.setIdentifMarcasFisicas(datosFichero.getCheckBoxFromAgpdBoolean(ide.getIndMarcas()));
        datosFichero.setIdentifFirmaElectronica(datosFichero.getCheckBoxFromAgpdBoolean(ide.getIndFirma()));
        datosFichero.setIdentifOtros(ide.getODCI());
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Otros datos tipificados">
        Envio.RegUno.Declaracion.Fichero.Estructura.Otros otros = xml.getRegUno()
                .getDeclaracion().get(0).getFichero().get(0).getEstructura()
                .getOtros();
        datosFichero.setOtrosDatosTipificados(otros.getOtrosTipos());
        datosFichero.setOtrosTiposDeDatos(otros.getDescOtrosTipos());
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Sistema de tratamiento">
        String sisT = xml.getRegUno()
                .getDeclaracion().get(0).getFichero().get(0).getEstructura()
                .getSistTratamiento().getSisTrata();
        datosFichero.setSistTratamiento(sisT);
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Categorías de destinatarios de cesiones">
        Envio.RegUno.Declaracion.Fichero.Cesion c = xml.getRegUno()
                .getDeclaracion().get(0).getFichero().get(0).getCesion();
        datosFichero.setDestCesiones(c.getCesiones());
        datosFichero.setOtrosDestCesiones(c.getDescOtros());
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Países y destinatarios de la transferencia">
        Envio.RegUno.Declaracion.Fichero.TransferInter t =xml.getRegUno()
                .getDeclaracion().get(0).getFichero().get(0).getTransferInter();
        
        datosFichero.setTransCodPais(t.getPais());
        datosFichero.setTransCategoria(t.getCategoria());
        datosFichero.setTransCategoriaOtros(t.getOtros());
        //</editor-fold>
        
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Operaciones AGPD">
    public static Envio alta(Envio envio, DatosFichero datosFichero) throws Exception {
        if (envio == null) {
            envio = of.createEnvio();
        }
        
        envio.setId(envio.getId());
        
        if (envio.getRegCero() == null) {
            envio.setRegCero(createDefaultRegCero(datosFichero));
        }
        
        if (envio.getRegUno() == null) {
            envio.setRegUno(createDefaultRegUno(datosFichero));
        }
        
        return envio;
    }
    
    public static Envio modificacion(Envio envio, DatosFichero datosFichero,
            Fichero fichero) throws Exception {
        
        datosFichero.setCodInscripcion(fichero.getCodInscripcion());
        Envio e = XmlEnvioFactory.alta(envio, datosFichero);
        
        Envio.RegUno.Declaracion.Fichero.Control.AccionesMod am =
                e.getRegUno().getDeclaracion().get(0).getFichero().get(0)
                .getControl().getAccionesMod();
        
        Envio old = readXml(fichero.getSolicitud());
        
        am.setResponsable(XmlEnvioFactory.compareResponsable(e, old).getId());
        am.setCifNifAnt(XmlEnvioFactory.compareCifNif(e, old));
        am.setServicioUnidad(XmlEnvioFactory.compareServicioUnidad(e, old).getId());
        am.setDisposicion(XmlEnvioFactory.compareDisposicion(e, old).getId());
        am.setIdenFinalid(XmlEnvioFactory.compareIdenFinalid(e, old).getId());
        am.setEncargado(XmlEnvioFactory.compareEncargado(e, old).getId());
        am.setEstructSistema(XmlEnvioFactory.compareEstructSistema(e, old).getId());
        am.setMedidasSeg(XmlEnvioFactory.compareMedidasSeg(e, old).getId());
        am.setOrigen(XmlEnvioFactory.compareOrigen(e, old).getId());
        am.setTransInter(XmlEnvioFactory.compareTransInter(e, old).getId());
        am.setComunicCes(XmlEnvioFactory.compareComunicCes(e, old).getId());
        
        e.getRegUno().getDeclaracion().get(0).getFichero().get(0).getControl()
                .setAccionesMod(am);
        return e;
    }
    
    public static Envio supresion(Envio envio, DatosFichero datosFichero,
            Fichero fichero) throws Exception {
        datosFichero.setCodInscripcion(fichero.getCodInscripcion());
        return XmlEnvioFactory.alta(envio, datosFichero);
    }
    //</editor-fold>
    
    /**
     * Cumplimenta el item reg_cero del envío.
     *
     * @return reg_cero cumplimentado.
     */
    private static Envio.RegCero createDefaultRegCero(DatosFichero datosFichero) {
        Envio.RegCero rc = of.createEnvioRegCero();
        rc.setIdRock(IND_ROCK);

        rc.setIndTitula(datosFichero.getTitularidad());
        rc.setIndSoporte(Soporte.FICHERO_XML_FIRMADO.getValue());
        rc.setFProceso(ManejadorFechas.getFechaActual().replace("-", ""));
        rc.setHProceso(ManejadorFechas.getHoraActual().replace(":", ""));
        rc.setIndProcesado(IND_PROCESADO);
        return rc;

    }

    /**
     * Cumplimenta el Reg_Uno del envío.
     *
     * @return reg_uno cumplimentado.
     */
    private static Envio.RegUno createDefaultRegUno(DatosFichero datosFichero) throws Exception {
        Envio.RegUno ru = of.createEnvioRegUno();
        
        if (datosFichero.getAccion().equals(ACCION_ALTA)) {
            ru.setControl(createDefaultControl(FormaCumplimentacion.XML_FIRMADO_ALTA,
                    datosFichero));
        } else if (datosFichero.getAccion().equals(ACCION_MODI)) {
            ru.setControl(createDefaultControl(FormaCumplimentacion.XML_FIRMADO_MODIFICACION,
                    datosFichero));
        } else if (datosFichero.getAccion().equals(ACCION_SUPR)) {
            ru.setControl(createDefaultControl(FormaCumplimentacion.XML_FIRMADO_SUPRESION,
                    datosFichero));
        }
        
        ru.setDeclarante(declarante(datosFichero));
        ru.getDeclaracion().add(declaracion(datosFichero));
        ru.setFinal(finalRegUno());
        return ru;
    }

    private static Envio.RegUno.Control createDefaultControl(
            FormaCumplimentacion formaCumplimentacion,
            DatosFichero datosFichero) throws Exception {

        Envio.RegUno.Control c = of.createEnvioRegUnoControl();
        c.setIdRock(ID_ROCK);
        c.setFormaC(formaCumplimentacion.getValue());

        //TODO Esto debe ser modificado según la titularidad el fichero.
        c.setSignatura(Signatura.PRIVADO.getValue());
        c.setIdUpload(generateIdUpload());

        // Lo incluirá el servicio web al devolver la
        // Hoja de Solicitud al solicitante, para xml firmado, siempre y cuando no se hayan
        // detectado errores. El formato es el habitual para las fechas: ddmmaaaa. 8 caracteres.
        c.setFWeb("");

        // Idem para la hora. 6 caracteres.
        c.setHWeb("");

        // Este elemento lo devuelve cumplimentado el servicio web .
        c.setNumReg("");
        return c;
    }

    private static String generateIdUpload() throws Exception {
        String cifResponsable = rb.getString("cifFirma");
        StringBuilder idUpload = new StringBuilder();

        idUpload.append(cifResponsable.toUpperCase());
        idUpload.append(ManejadorFechas.getFechaActualMesHexa());
        idUpload.append(ManejadorFechas.getHoraActual().replace(":", ""));

        if (idUpload.length() != 22) {
            logger.error("Generando el IdUpload de {} valor [{}]", cifResponsable, idUpload.toString());
            throw new Exception("IdUpload incorrecto.");
        }

        return idUpload.toString();
    }

    private static Envio.RegUno.Declarante declarante(DatosFichero datosFichero) {
        Envio.RegUno.Declarante d = of.createEnvioRegUnoDeclarante();
        Envio.RegUno.Declarante.Control c = of.createEnvioRegUnoDeclaranteControl();
        Envio.RegUno.Declarante.HojaSolicitud h = of.createEnvioRegUnoDeclaranteHojaSolicitud();

        // Control
        c.setFuturoUso("");
        c.setEstErr("");
        c.setDocAnexa("");

        // Hoja de Solicitud  
        // Hoja de Solicitud: Datos identificativos del declarante.  
        Envio.RegUno.Declarante.HojaSolicitud.PersonaFisica p = of.createEnvioRegUnoDeclaranteHojaSolicitudPersonaFisica();
        p.setRazonS(datosFichero.getEmpresa().getRazonSocial());
        p.setCifNif(datosFichero.getEmpresa().getCif());

        if (datosFichero.getNombreDeclarante() == null || 
                datosFichero.getNombreDeclarante().isEmpty()) {
            p.setNombre(rb.getString("nombreDeclarante"));
            p.setApellido1(rb.getString("primerApellidoDeclarante"));
            p.setApellido2(rb.getString("segundoApellidoDeclarante"));
            p.setNif(rb.getString("cifFirma"));
            // Cargo máximo de 70 carácteres.
            p.setCargo(rb.getString("cargoDeclarante"));
        } else {            
            p.setNombre(datosFichero.getNombreDeclarante());
            p.setApellido1(datosFichero.getPrimerApellidoDeclarante());
            p.setApellido2(datosFichero.getSegundoApellidoDeclarante());
            p.setNif(datosFichero.getCifFirma());
            // Cargo máximo de 70 carácteres.
            p.setCargo(datosFichero.getCargoDeclarante());
        }

        h.setPersonaFisica(p);

        // Hoja de Solicitud: Dirección de notificación.
        Envio.RegUno.Declarante.HojaSolicitud.DireccionNotif dn = of.createEnvioRegUnoDeclaranteHojaSolicitudDireccionNotif();
        dn.setDenominaP(datosFichero.getNotifRazonSocial());
        dn.setDirPostal(datosFichero.getNotifDirPostal());
        dn.setPais(datosFichero.getNotifPais());
        dn.setProvincia(datosFichero.getNotifProvincia());
        dn.setLocalidad(datosFichero.getNotifLocalidad());
        dn.setPostal(datosFichero.getNotifCodPostal());
        dn.setTelefono(datosFichero.getNotifTel());
        dn.setFax(datosFichero.getNotifFax());
        dn.setEmail(datosFichero.getNotifEMail());
        dn.setForma(MedioNotificacion.CORREO_POSTAL.getValue());
        dn.setIdNotific("");
        dn.setIndDeberes(datosFichero.getNotifDeberes()); // Siempre va a 1;

        h.setDireccionNotif(dn);


        d.setControl(c);
        d.setHojaSolicitud(h);
        return d;
    }

    private static Envio.RegUno.Declaracion declaracion(DatosFichero datosFichero) {
        Envio.RegUno.Declaracion d = of.createEnvioRegUnoDeclaracion();

        d.setResponsable(responsable(datosFichero));
        d.getFichero().add(fichero(datosFichero));

        return d;
    }

    private static Envio.RegUno.Declaracion.Responsable responsable(
            DatosFichero datosFichero) {
        Envio.RegUno.Declaracion.Responsable r = of.createEnvioRegUnoDeclaracionResponsable();

        Envio.RegUno.Declaracion.Responsable.Control c = of.createEnvioRegUnoDeclaracionResponsableControl();
        c.setOrdinal("01");
        c.setEstErr("");
        c.setTextoLibre("");
        r.setControl(c);

        r.setResponsableFichero(responsableFichero(datosFichero));
        r.setDerecho(derechoARCO(datosFichero));

        return r;
    }

    private static Envio.RegUno.Declaracion.Responsable.ResponsableFichero responsableFichero(DatosFichero datosFichero) {
        Envio.RegUno.Declaracion.Responsable.ResponsableFichero rf =
                of.createEnvioRegUnoDeclaracionResponsableResponsableFichero();

        rf.setCif(datosFichero.getEmpresa().getCif());
        rf.setDirPostal(datosFichero.getRespDomicilioSocial());
        rf.setPostal(datosFichero.getRespCodigoPostal());
        rf.setLocalidad(datosFichero.getRespLocalidad());
        rf.setProvincia(datosFichero.getRespProvincia());
        rf.setPais(datosFichero.getRespPais());
        rf.setTelefono(datosFichero.getRespTelefono());
        rf.setFax(datosFichero.getRespFax());
        rf.setEmail(datosFichero.getRespEmail());
        rf.setNRazon(datosFichero.getEmpresa().getRazonSocial());
        rf.setCap(datosFichero.getEmpresa().getActividad());

        // Los siguientes campos son para ficheros de titularidad Pública.
        rf.setTipAdmin("");
        rf.setCodAutomia("");
        rf.setDenominaEnte("");
        rf.setDenominaDirdep("");
        rf.setDenominaOrgano("");

        return rf;
    }

    private static Envio.RegUno.Declaracion.Responsable.Derecho derechoARCO(DatosFichero datosFichero) {
        Envio.RegUno.Declaracion.Responsable.Derecho d = of.createEnvioRegUnoDeclaracionResponsableDerecho();

        d.setOficina(datosFichero.getDerechosOficina());
        d.setNifCif(datosFichero.getDerechosCif());
        d.setDirPostal(datosFichero.getDerechosDireccionPostal());
        d.setLocalidad(datosFichero.getDerechosLocalidad());
        d.setPostal(datosFichero.getDerechosCodigoPostal());
        d.setProvincia(datosFichero.getDerechosProvincia());
        d.setPais(datosFichero.getDerechosPais());
        d.setTelefono(datosFichero.getDerechosTelefono());
        d.setFax(datosFichero.getDerechosFax());
        d.setEmail(datosFichero.getDerechosEmail());

        return d;
    }

    private static Envio.RegUno.Declaracion.Fichero fichero(
            DatosFichero datosFichero) {
        Envio.RegUno.Declaracion.Fichero f = of.createEnvioRegUnoDeclaracionFichero();

        f.setControl(ficheroControl(datosFichero));
        f.setDispoGenCms(ficheroDispoGenCms(datosFichero));
        f.setEncargado(FicheroEncargadoTratamiento(datosFichero));
        f.setIdentificaFinalidad(FicheroIdentificaFinalidad(datosFichero));
        f.setProcedencia(ficheroProcedencia(datosFichero));
        f.setMedidasSeg(ficheroMedidasSeg(datosFichero));
        f.setEstructura(ficheroEstructura(datosFichero));
        f.setCesion(ficheroCesion(datosFichero));
        f.setTransferInter(ficheroTransferInter(datosFichero));

        return f;
    }

    //<editor-fold defaultstate="collapsed" desc="Envio.RegUno.Declaracion.Fichero.Control">
    private static Envio.RegUno.Declaracion.Fichero.Control ficheroControl(DatosFichero datosFichero) {
        Envio.RegUno.Declaracion.Fichero.Control c = of.createEnvioRegUnoDeclaracionFicheroControl();

        // Cumplimentación de las acciones generales.
        Envio.RegUno.Declaracion.Fichero.Control.AccionesGenerales ac =
                of.createEnvioRegUnoDeclaracionFicheroControlAccionesGenerales();

        ac.setOrdinal("0001");
        
        if (datosFichero.getAccion().equals(ACCION_ALTA)) {
           ac.setTipoSolicitud(TipoSolicitud.ALTA.getValue());
        } else if(datosFichero.getAccion().equals(ACCION_MODI)) {            
           ac.setTipoSolicitud(TipoSolicitud.MODIFICACION.getValue());
        } else if(datosFichero.getAccion().equals(ACCION_SUPR)) {            
           ac.setTipoSolicitud(TipoSolicitud.SUPRESION.getValue());
        }
        
        ac.setEstErr("");
        ac.setDocAnexa("");
        c.setAccionesGenerales(ac);

        // Cumplimentación de acciones_not_alta
        Envio.RegUno.Declaracion.Fichero.Control.AccionesNotAlta ana =
                of.createEnvioRegUnoDeclaracionFicheroControlAccionesNotAlta();

        ana.setFechaReg("");
        ana.setNumReg("");
        ana.setIdResolucion("");
        c.setAccionesNotAlta(ana);

        // Cumplimentación de acciones_mod
        c.setAccionesMod(ficheroControlAccionesModificacion(datosFichero));

        // Cumplimentación de acciones_sup
        Envio.RegUno.Declaracion.Fichero.Control.AccionesSupr as =
                of.createEnvioRegUnoDeclaracionFicheroControlAccionesSupr();

        if(datosFichero.getAccion().equals(ACCION_SUPR)) {
            as.setMotivos(datosFichero.getSupresionMotivos());
            as.setDestinoPrevisiones(datosFichero.getSupresionDestInfo());
            as.setCifnif(datosFichero.getEmpresa().getCif());
        } else {
            as.setMotivos("");
            as.setDestinoPrevisiones("");
            as.setCifnif("");
        }
        c.setAccionesSupr(as);

        return c;
    }

    private static Envio.RegUno.Declaracion.Fichero.Control.AccionesMod ficheroControlAccionesModificacion(DatosFichero datosFichero) {
        Envio.RegUno.Declaracion.Fichero.Control.AccionesMod am =
                of.createEnvioRegUnoDeclaracionFicheroControlAccionesMod();

        am.setResponsable("");
        am.setCifNifAnt("");
        am.setServicioUnidad("");
        am.setDisposicion("");
        am.setIdenFinalid("");
        am.setEncargado("");
        am.setEstructSistema("");
        am.setMedidasSeg("");
        am.setOrigen("");
        am.setTransInter("");
        am.setComunicCes("");

        return am;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Envio.RegUno.Declaracion.Fichero.DispoGenCms">
    private static Envio.RegUno.Declaracion.Fichero.DispoGenCms ficheroDispoGenCms(
            DatosFichero datosFichero) {
        Envio.RegUno.Declaracion.Fichero.DispoGenCms dgc =
                of.createEnvioRegUnoDeclaracionFicheroDispoGenCms();
        
        dgc.setCodBoletin("");
        dgc.setNumBoletin("");
        dgc.setFecha("");
        dgc.setDisposicion("");
        dgc.setUrl("");
        
        return dgc;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Envio.RegUno.Declaracion.Fichero.Encargado">
    private static Envio.RegUno.Declaracion.Fichero.Encargado FicheroEncargadoTratamiento(
            DatosFichero datosFichero) {
        Envio.RegUno.Declaracion.Fichero.Encargado e =
                of.createEnvioRegUnoDeclaracionFicheroEncargado();
        
        e.setNRazon(datosFichero.getEncDenominacionSocial());
        e.setCifNif(datosFichero.getEncCif());
        e.setDirPostal(datosFichero.getEncDomicilioSocial());
        e.setLocalidad(datosFichero.getEncLocalidad());
        e.setPostal(datosFichero.getEncCodigoPostal());
        e.setProvincia(datosFichero.getEncProvincia());
        e.setPais(datosFichero.getEncPais());
        e.setTelefono(datosFichero.getEncTelefono());
        e.setFax(datosFichero.getEncFax());
        e.setEmail(datosFichero.getEncEmail());
        
        return e;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Envio.RegUno.Declaracion.Fichero.IdentificaFinalidad">
    private static Envio.RegUno.Declaracion.Fichero.IdentificaFinalidad
            FicheroIdentificaFinalidad(DatosFichero datosFichero) {
        Envio.RegUno.Declaracion.Fichero.IdentificaFinalidad ident =
                of.createEnvioRegUnoDeclaracionFicheroIdentificaFinalidad();
        
        //Denominación del fichero
        Envio.RegUno.Declaracion.Fichero.IdentificaFinalidad.Denominacion d =
                of.createEnvioRegUnoDeclaracionFicheroIdentificaFinalidadDenominacion();
        
        d.setFichero(datosFichero.getDenominacion());
        d.setCInscripcion(datosFichero.getCodInscripcion() != null ? 
                datosFichero.getCodInscripcion() : "");
        //TODO: Código de otras agencias
        d.setCInscripT("");
        d.setFInscrip("");
        d.setDescFinUsos(datosFichero.getUsosPrevistos());
        ident.setDenominacion(d);
        
        //Finalidades
        Envio.RegUno.Declaracion.Fichero.IdentificaFinalidad.Tipificacion t =
                of.createEnvioRegUnoDeclaracionFicheroIdentificaFinalidadTipificacion();
        t.setFinalidades(datosFichero.getFinalidades());
        ident.setTipificacion(t);
        
        return ident;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Envio.RegUno.Declaracion.Fichero.Procedencia">
    private static Envio.RegUno.Declaracion.Fichero.Procedencia
            ficheroProcedencia(DatosFichero datosFichero) {
        Envio.RegUno.Declaracion.Fichero.Procedencia p =
                of.createEnvioRegUnoDeclaracionFicheroProcedencia();
        // Origen de los datos
        Envio.RegUno.Declaracion.Fichero.Procedencia.Origen o =
                of.createEnvioRegUnoDeclaracionFicheroProcedenciaOrigen();
        
        o.setIndicaInte(datosFichero.getOrigenInte());
        o.setIndicaOtras(datosFichero.getOrigenOtras());
        o.setIndicFap(datosFichero.getOrigenFap());
        o.setIndicRp(datosFichero.getOrigenRp());
        o.setIndicEp(datosFichero.getOrigenEp());
        o.setIndicAp(datosFichero.getOrigenAp());
        
        // Colectivos
        Envio.RegUno.Declaracion.Fichero.Procedencia.ColectivosCateg c =
                of.createEnvioRegUnoDeclaracionFicheroProcedenciaColectivosCateg();
        c.setColectivos(datosFichero.getColectivos());
        c.setOtroCol(datosFichero.getOtrosColectivos());
        
        p.setOrigen(o);
        p.setColectivosCateg(c);
        
        return p;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Envio.RegUno.Declaracion.Fichero.MedidasSeg">
    private static Envio.RegUno.Declaracion.Fichero.MedidasSeg
            ficheroMedidasSeg(DatosFichero datosFichero) {
        Envio.RegUno.Declaracion.Fichero.MedidasSeg ms =
                of.createEnvioRegUnoDeclaracionFicheroMedidasSeg();
        ms.setNivel(TipoNivelSeguridad.values()
                [ new Integer(datosFichero.getNivel()) ].getValue());
        //TODO Rellenar en caso de modificación. Hay que confirmar con la AGPD que se pone.
        ms.setFAudit("");
        ms.setTAudit("");
        return ms;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Envio.RegUno.Declaracion.Fichero.Estructura">
    private static Envio.RegUno.Declaracion.Fichero.Estructura ficheroEstructura(DatosFichero datosFichero) {
        Envio.RegUno.Declaracion.Fichero.Estructura e = of.createEnvioRegUnoDeclaracionFicheroEstructura();
        
        Envio.RegUno.Declaracion.Fichero.Estructura.DatosEspProteg datosEspProteg =
                of.createEnvioRegUnoDeclaracionFicheroEstructuraDatosEspProteg();
        
        datosEspProteg.setIndIde(datosFichero.getDatEspProtIdeologia());
        datosEspProteg.setIndAs(datosFichero.getDatEspProtAfiliacionSindical());
        datosEspProteg.setIndR(datosFichero.getDatEspProtReligion());
        datosEspProteg.setIndC(datosFichero.getDatEspProtCreencias());
        e.setDatosEspProteg(datosEspProteg);
        
        Envio.RegUno.Declaracion.Fichero.Estructura.OtrosEspProteg otrosEspProteg =
                of.createEnvioRegUnoDeclaracionFicheroEstructuraOtrosEspProteg();
        otrosEspProteg.setIndRe(datosFichero.getOtrosEspProtOrigenRacial());
        otrosEspProteg.setIndSal(datosFichero.getOtrosEspProtSalud());
        otrosEspProteg.setIndSexo(datosFichero.getOtrosEspprotVidaSexual());
        e.setOtrosEspProteg(otrosEspProteg);
        
        // Penal, solo para ficheros públicos.
        Envio.RegUno.Declaracion.Fichero.Estructura.InfraccionesPenal penal =
                of.createEnvioRegUnoDeclaracionFicheroEstructuraInfraccionesPenal();
        penal.setIndIad("0");
        penal.setIndIpen("0");
        e.setInfraccionesPenal(penal);
        
        Envio.RegUno.Declaracion.Fichero.Estructura.Identificativos identificativos =
                of.createEnvioRegUnoDeclaracionFicheroEstructuraIdentificativos();
        identificativos.setIndNif(datosFichero.getIdentifNIF());
        identificativos.setIndSs(datosFichero.getIdentifNSS());
        identificativos.setIndNA(datosFichero.getIdentifNombreApp());
        identificativos.setIndTs(datosFichero.getIdentifTarjSanitaria());
        identificativos.setIndDir(datosFichero.getIdentifDireccion());
        identificativos.setIndTel(datosFichero.getIdentifTel());
        identificativos.setIndFirmaManual(datosFichero.getIdentifFirmaManual());
        identificativos.setIndHuella(datosFichero.getIdentifFirmaHuella());
        identificativos.setIndBiometricos(datosFichero.getIdentifOtrosDatosBio());
        identificativos.setIndImg(datosFichero.getIdentifImagVoz());
        identificativos.setIndMarcas(datosFichero.getIdentifMarcasFisicas());
        identificativos.setIndFirma(datosFichero.getIdentifFirmaElectronica());
        //TODO: Confirmar con la agencia que es este campo, en la documentación
        // no tiene descripción y no es un campo del form en PDF.
        identificativos.setIndRegistro("0");
        identificativos.setODCI(datosFichero.getIdentifOtros());
        e.setIdentificativos(identificativos);
        
        Envio.RegUno.Declaracion.Fichero.Estructura.Otros otros =
                of.createEnvioRegUnoDeclaracionFicheroEstructuraOtros();
        otros.setOtrosTipos(datosFichero.getOtrosDatosTipificados());
        otros.setDescOtrosTipos(datosFichero.getOtrosTiposDeDatos());
        e.setOtros(otros);
        
        Envio.RegUno.Declaracion.Fichero.Estructura.SistTratamiento sisTrat =
                of.createEnvioRegUnoDeclaracionFicheroEstructuraSistTratamiento();
        sisTrat.setSisTrata(datosFichero.getSistTratamiento());
        e.setSistTratamiento(sisTrat);
        
        return e;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Envio.RegUno.Declaracion.Fichero.Cesion">
    private static Envio.RegUno.Declaracion.Fichero.Cesion ficheroCesion(DatosFichero datosFichero) {
        Envio.RegUno.Declaracion.Fichero.Cesion c = of.createEnvioRegUnoDeclaracionFicheroCesion();
        
        c.setCesiones(datosFichero.getDestCesiones());
        c.setDescOtros(datosFichero.getOtrosDestCesiones());
        
        return c;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Envio.RegUno.Declaracion.Fichero.TransferInter">
    private static Envio.RegUno.Declaracion.Fichero.TransferInter ficheroTransferInter(DatosFichero datosFichero) {
        Envio.RegUno.Declaracion.Fichero.TransferInter t =
                of.createEnvioRegUnoDeclaracionFicheroTransferInter();
        t.setPais(datosFichero.getTransCodPais());
        t.setCategoria(datosFichero.getTransCategoria());
        t.setOtros(datosFichero.getTransCategoriaOtros());
        return t;
    }
    //</editor-fold>
    
    private static Envio.RegUno.Final finalRegUno() {
        Envio.RegUno.Final f = of.createEnvioRegUnoFinal();

        f.setContador("");
        f.setTotAltas("");
        f.setTotBajas("");
        f.setTotModif("");

        return f;
    }

    /**
     * Verifica cambios en “Envio/reg_uno/declaracion/responsable/responsable_fichero”
     * @param newEnvio Envío de modificación de fichero.
     * @param oldEnvio Envío anterior.
     * @return SIN_MODIFICACIONES si el contenido no está modificado, 
     * MODIFICADO si el contenido es diferente.
     */
    private static ModificacionesEnvio compareResponsable(Envio newEnvio, Envio oldEnvio) {
        Envio.RegUno.Declaracion.Responsable.ResponsableFichero rn = newEnvio
                .getRegUno().getDeclaracion().get(0).getResponsable().getResponsableFichero();
        Envio.RegUno.Declaracion.Responsable.ResponsableFichero ro = oldEnvio
                .getRegUno().getDeclaracion().get(0).getResponsable().getResponsableFichero();
        
        ModificacionesEnvio status;
        
        if (rn.getCap().equals(ro.getCap())
                && rn.getCif().equals(ro.getCif())
                && rn.getCodAutomia().equals(ro.getCodAutomia())
                && rn.getDenominaDirdep().equals(ro.getDenominaDirdep())
                && rn.getDenominaEnte().equals(ro.getDenominaEnte())
                && rn.getDenominaOrgano().equals(ro.getDenominaOrgano())
                && rn.getDirPostal().equals(ro.getDirPostal())
                && rn.getEmail().equals(ro.getEmail())
                && rn.getFax().equals(ro.getFax())
                && rn.getLocalidad().equals(ro.getLocalidad())
                && rn.getNRazon().equals(ro.getNRazon())
                && rn.getPais().equals(ro.getPais())
                && rn.getPostal().equals(ro.getPostal())
                && rn.getProvincia().equals(ro.getProvincia())
                && rn.getTelefono().equals(ro.getTelefono())
                && rn.getTipAdmin().equals(ro.getTipAdmin())) {
            status =  ModificacionesEnvio.SIN_MODIFICACIONES;
        } else {
            status = ModificacionesEnvio.MODIFICADO;
        }
        
        return status;
    }   
        
    /**
     * Verifica si existe cambio de nif
     * @param newEnvio Envío de modificación de fichero.
     * @param oldEnvio Envío anterior.
     * @return El cif anterior si hay un cambio de cif, una cadena vacía 
     * en caso contrario.
     */
    private static String compareCifNif(Envio newEnvio, Envio oldEnvio) {
        Envio.RegUno.Declaracion.Responsable.ResponsableFichero rn = newEnvio
                .getRegUno().getDeclaracion().get(0).getResponsable().getResponsableFichero();
        Envio.RegUno.Declaracion.Responsable.ResponsableFichero ro = oldEnvio
                .getRegUno().getDeclaracion().get(0).getResponsable().getResponsableFichero();
        
        if (rn.getCif().equals(ro.getCif())) {
            return "";
        } else {
            return ro.getCif();
        }        
    }
    
    /**
     * Verifica cambios en el nodo Envio/reg_uno/declaracion/responsable/derecho
     * @param newEnvio Envío de modificación de fichero.
     * @param oldEnvio Envío anterior.
     * @return SIN_MODIFICACIONES si el contenido no está modificado, 
     * MODIFICADO si el contenido es diferente, y CONTENIDO_ELIMINADO en caso
     * del que el campo haya quedado en blanco.
     */
    private static ModificacionesEnvio compareServicioUnidad(Envio newEnvio, Envio oldEnvio) {
        Envio.RegUno.Declaracion.Responsable.Derecho ne = newEnvio
                .getRegUno().getDeclaracion().get(0).getResponsable().getDerecho();
        Envio.RegUno.Declaracion.Responsable.Derecho oe = oldEnvio
                .getRegUno().getDeclaracion().get(0).getResponsable().getDerecho();
        
        ModificacionesEnvio status;
        
        if (ne.getDirPostal().equals(oe.getDirPostal())
                && ne.getEmail().equals(oe.getEmail())
                && ne.getFax().equals(oe.getFax())
                && ne.getLocalidad().equals(oe.getLocalidad())
                && ne.getNifCif().equals(oe.getNifCif())
                && ne.getOficina().equals(oe.getOficina())
                && ne.getPais().equals(oe.getPais())
                && ne.getPostal().equals(oe.getPostal())
                && ne.getProvincia().equals(oe.getProvincia())
                && ne.getTelefono().equals(oe.getTelefono())) {
            status =  ModificacionesEnvio.SIN_MODIFICACIONES;
        } else {
            status = ModificacionesEnvio.MODIFICADO;
        }
        
        if (status == ModificacionesEnvio.MODIFICADO 
                && ne.getNifCif().isEmpty()) {
            status = ModificacionesEnvio.CONTENIDO_ELIMINADO;
        }
        return status;
    }
    
    /**
     * Verifica cambios en el nodo Envio/reg_uno/declaracion/fichero/dispo_gen_cms
     * @param newEnvio Envío de modificación de fichero.
     * @param oldEnvio Envío anterior.
     * @return SIN_MODIFICACIONES si el contenido no está modificado, 
     * MODIFICADO si el contenido es diferente.
     */
    private static ModificacionesEnvio compareDisposicion(Envio newEnvio, Envio oldEnvio) {
        Envio.RegUno.Declaracion.Fichero.DispoGenCms ne = newEnvio
                .getRegUno().getDeclaracion().get(0).getFichero().get(0).getDispoGenCms();
        Envio.RegUno.Declaracion.Fichero.DispoGenCms oe = oldEnvio
                .getRegUno().getDeclaracion().get(0).getFichero().get(0).getDispoGenCms();
        
        ModificacionesEnvio status;
        
        if (ne.getCodBoletin().equals(oe.getCodBoletin())
                && ne.getDisposicion().equals(oe.getDisposicion())
                && ne.getFecha().equals(oe.getFecha())
                && ne.getNumBoletin().equals(oe.getNumBoletin())
                && ne.getUrl().equals(oe.getUrl())) {
            status =  ModificacionesEnvio.SIN_MODIFICACIONES;
        } else {
            status = ModificacionesEnvio.MODIFICADO;
        }
        
        return status;
    }    
    
    /**
     * Verifica cambios en el nodo Envio/reg_uno/declaracion/fichero/identifica_finalidad
     * @param newEnvio Envío de modificación de fichero.
     * @param oldEnvio Envío anterior.
     * @return SIN_MODIFICACIONES si el contenido no está modificado, 
     * MODIFICADO si el contenido es diferente.
     */
    private static ModificacionesEnvio compareIdenFinalid(Envio newEnvio, Envio oldEnvio) {
        Envio.RegUno.Declaracion.Fichero.IdentificaFinalidad ne = newEnvio
                .getRegUno().getDeclaracion().get(0).getFichero().get(0).getIdentificaFinalidad();
        Envio.RegUno.Declaracion.Fichero.IdentificaFinalidad oe = oldEnvio
                .getRegUno().getDeclaracion().get(0).getFichero().get(0).getIdentificaFinalidad();
        
        ModificacionesEnvio status;
        
        if (ne.getDenominacion().getCInscripT().equals(oe.getDenominacion().getCInscripT())
                && ne.getDenominacion().getCInscripcion().equals(oe.getDenominacion().getCInscripcion())
                && ne.getDenominacion().getDescFinUsos().equals(oe.getDenominacion().getDescFinUsos())
                && ne.getDenominacion().getFInscrip().equals(oe.getDenominacion().getFInscrip())
                && ne.getDenominacion().getFichero().equals(oe.getDenominacion().getFichero())
                && ne.getTipificacion().getFinalidades().equals(oe.getTipificacion().getFinalidades())) {
            status =  ModificacionesEnvio.SIN_MODIFICACIONES;
        } else {
            status = ModificacionesEnvio.MODIFICADO;
        }
        
        return status;
    }
    
    /**
     * Verifica cambios en el nodo Envio/reg_uno/declaracion/fichero/encargado
     * @param newEnvio Envío de modificación de fichero.
     * @param oldEnvio Envío anterior.
     * @return SIN_MODIFICACIONES si el contenido no está modificado, 
     * MODIFICADO si el contenido es diferente, y CONTENIDO_ELIMINADO en caso
     * del que el campo haya quedado en blanco.
     */
    private static ModificacionesEnvio compareEncargado(Envio newEnvio, Envio oldEnvio) {
        Envio.RegUno.Declaracion.Fichero.Encargado ne = newEnvio
                .getRegUno().getDeclaracion().get(0).getFichero().get(0).getEncargado();
        Envio.RegUno.Declaracion.Fichero.Encargado oe = oldEnvio
                .getRegUno().getDeclaracion().get(0).getFichero().get(0).getEncargado();
        
        ModificacionesEnvio status;
        
        if (ne.getCifNif().equals(oe.getCifNif())
                && ne.getDirPostal().equals(oe.getDirPostal())
                && ne.getEmail().equals(oe.getEmail())
                && ne.getFax().equals(oe.getFax())
                && ne.getLocalidad().equals(oe.getLocalidad())
                && ne.getNRazon().equals(oe.getNRazon())
                && ne.getPais().equals(oe.getPais())
                && ne.getPostal().equals(oe.getPostal())
                && ne.getProvincia().equals(oe.getProvincia())
                && ne.getTelefono().equals(oe.getTelefono())) {
            status =  ModificacionesEnvio.SIN_MODIFICACIONES;
        } else {
            status = ModificacionesEnvio.MODIFICADO;
        }
        
        if (status == ModificacionesEnvio.MODIFICADO 
                && ne.getCifNif().isEmpty()) {
            status = ModificacionesEnvio.CONTENIDO_ELIMINADO;
        }
        return status;
    }
    
    /**
     * Verifica cambios en el nodo Envio/reg_uno/declaracion/fichero/estructura
     * @param newEnvio Envío de modificación de fichero.
     * @param oldEnvio Envío anterior.
     * @return SIN_MODIFICACIONES si el contenido no está modificado, 
     * MODIFICADO si el contenido es diferente.
     */
    private static ModificacionesEnvio compareEstructSistema(Envio newEnvio, Envio oldEnvio) {
        Envio.RegUno.Declaracion.Fichero.Estructura ne = newEnvio
                .getRegUno().getDeclaracion().get(0).getFichero().get(0).getEstructura();
        Envio.RegUno.Declaracion.Fichero.Estructura oe = oldEnvio
                .getRegUno().getDeclaracion().get(0).getFichero().get(0).getEstructura();
        
        ModificacionesEnvio status;
        
        if (ne.getDatosEspProteg().getIndAs().equals(oe.getDatosEspProteg().getIndAs())
                && ne.getDatosEspProteg().getIndC().equals(oe.getDatosEspProteg().getIndC())
                && ne.getDatosEspProteg().getIndIde().equals(oe.getDatosEspProteg().getIndIde())
                && ne.getDatosEspProteg().getIndR().equals(oe.getDatosEspProteg().getIndR())
                
                && ne.getIdentificativos().getIndBiometricos().equals(oe.getIdentificativos().getIndBiometricos() ) 
                && ne.getIdentificativos().getIndDir().equals(oe.getIdentificativos().getIndDir())
                && ne.getIdentificativos().getIndFirma().equals(oe.getIdentificativos().getIndFirma())
                && ne.getIdentificativos().getIndFirmaManual().equals(oe.getIdentificativos().getIndFirmaManual())
                && ne.getIdentificativos().getIndHuella().equals(oe.getIdentificativos().getIndHuella())
                && ne.getIdentificativos().getIndImg().equals(oe.getIdentificativos().getIndImg())
                && ne.getIdentificativos().getIndMarcas().equals(oe.getIdentificativos().getIndMarcas())
                && ne.getIdentificativos().getIndNA().equals(oe.getIdentificativos().getIndNA())
                && ne.getIdentificativos().getIndNif().equals(oe.getIdentificativos().getIndNif())
                && ne.getIdentificativos().getIndRegistro().equals(oe.getIdentificativos().getIndRegistro())
                && ne.getIdentificativos().getIndSs().equals(oe.getIdentificativos().getIndSs())
                && ne.getIdentificativos().getIndTel().equals(oe.getIdentificativos().getIndTel())
                && ne.getIdentificativos().getIndTs().equals(oe.getIdentificativos().getIndTs())
                && ne.getIdentificativos().getODCI().equals(oe.getIdentificativos().getODCI())
                
                && ne.getInfraccionesPenal().getIndIad().equals(oe.getInfraccionesPenal().getIndIad())
                && ne.getInfraccionesPenal().getIndIpen().equals(oe.getInfraccionesPenal().getIndIpen())
                
                && ne.getOtros().getDescOtrosTipos().equals(oe.getOtros().getDescOtrosTipos())
                && ne.getOtros().getOtrosTipos().equals(oe.getOtros().getOtrosTipos())
                
                && ne.getOtrosEspProteg().getIndRe().equals(oe.getOtrosEspProteg().getIndRe())
                && ne.getOtrosEspProteg().getIndSal().equals(oe.getOtrosEspProteg().getIndSal())
                && ne.getOtrosEspProteg().getIndSexo().equals(oe.getOtrosEspProteg().getIndSexo())
              
                && ne.getSistTratamiento().getSisTrata().equals(oe.getSistTratamiento().getSisTrata())
                
                ) {
            status =  ModificacionesEnvio.SIN_MODIFICACIONES;
        } else {
            status = ModificacionesEnvio.MODIFICADO;
        }
        
        return status;
    }

    /**
     * Verifica cambios en el nodo Envio/reg_uno/declaracion/fichero/medidas_seg
     * @param newEnvio Envío de modificación de fichero.
     * @param oldEnvio Envío anterior.
     * @return SIN_MODIFICACIONES si el contenido no está modificado, 
     * MODIFICADO si el contenido es diferente.
     */
    private static ModificacionesEnvio compareMedidasSeg(Envio newEnvio, Envio oldEnvio) {
        Envio.RegUno.Declaracion.Fichero.MedidasSeg ne = newEnvio
                .getRegUno().getDeclaracion().get(0).getFichero().get(0).getMedidasSeg();
        Envio.RegUno.Declaracion.Fichero.MedidasSeg oe = oldEnvio
                .getRegUno().getDeclaracion().get(0).getFichero().get(0).getMedidasSeg();
        
        ModificacionesEnvio status;
        
        if (ne.getNivel().equals(oe.getNivel())) {
            status =  ModificacionesEnvio.SIN_MODIFICACIONES;
        } else {
            status = ModificacionesEnvio.MODIFICADO;
        }
        
        return status;
    }

    /**
     * Verifica cambios en el nodo Envio/reg_uno/declaracion/fichero/procedencia
     * @param newEnvio Envío de modificación de fichero.
     * @param oldEnvio Envío anterior.
     * @return SIN_MODIFICACIONES si el contenido no está modificado, 
     * MODIFICADO si el contenido es diferente.
     */
    private static ModificacionesEnvio compareOrigen(Envio newEnvio, Envio oldEnvio) {
        Envio.RegUno.Declaracion.Fichero.Procedencia ne = newEnvio
                .getRegUno().getDeclaracion().get(0).getFichero().get(0).getProcedencia();
        Envio.RegUno.Declaracion.Fichero.Procedencia oe = oldEnvio
                .getRegUno().getDeclaracion().get(0).getFichero().get(0).getProcedencia();
        
        ModificacionesEnvio status;
        
        if (ne.getColectivosCateg().getColectivos().equals(oe.getColectivosCateg().getColectivos())
                && ne.getColectivosCateg().getOtroCol().equals(oe.getColectivosCateg().getOtroCol())
                
                && ne.getOrigen().getIndicAp().equals(oe.getOrigen().getIndicAp())
                && ne.getOrigen().getIndicEp().equals(oe.getOrigen().getIndicEp())
                && ne.getOrigen().getIndicFap().equals(oe.getOrigen().getIndicFap())
                && ne.getOrigen().getIndicRp().equals(oe.getOrigen().getIndicRp())
                && ne.getOrigen().getIndicaInte().equals(oe.getOrigen().getIndicaInte())
                && ne.getOrigen().getIndicaOtras().equals(oe.getOrigen().getIndicaOtras())
                ) {
            status =  ModificacionesEnvio.SIN_MODIFICACIONES;
        } else {
            status = ModificacionesEnvio.MODIFICADO;
        }

        return status;
    }
    
    /**
     * Verifica cambios en el nodo Envio/reg_uno/declaracion/fichero/transfer_inter
     * @param newEnvio Envío de modificación de fichero.
     * @param oldEnvio Envío anterior.
     * @return SIN_MODIFICACIONES si el contenido no está modificado, 
     * MODIFICADO si el contenido es diferente, y CONTENIDO_ELIMINADO en caso
     * del que el campo haya quedado en blanco.
     */
    private static ModificacionesEnvio compareTransInter(Envio newEnvio, Envio oldEnvio) {
        Envio.RegUno.Declaracion.Fichero.TransferInter ne = newEnvio
                .getRegUno().getDeclaracion().get(0).getFichero().get(0).getTransferInter();
        Envio.RegUno.Declaracion.Fichero.TransferInter oe = oldEnvio
                .getRegUno().getDeclaracion().get(0).getFichero().get(0).getTransferInter();
        
        ModificacionesEnvio status;
        
        if (ne.getCategoria().equals(oe.getCategoria())
                && ne.getOtros().equals(oe.getOtros())
                && ne.getPais().equals(oe.getPais())) {
            status =  ModificacionesEnvio.SIN_MODIFICACIONES;
        } else {
            status = ModificacionesEnvio.MODIFICADO;
        }
        
        if (status == ModificacionesEnvio.MODIFICADO 
                && ne.getPais().isEmpty()) {
            status = ModificacionesEnvio.CONTENIDO_ELIMINADO;
        }
        return status;
    }

    /**
     * Verifica cambios en el nodo Envio/reg_uno/declaracion/fichero/cesion

     * @param newEnvio Envío de modificación de fichero.
     * @param oldEnvio Envío anterior.
     * @return SIN_MODIFICACIONES si el contenido no está modificado, 
     * MODIFICADO si el contenido es diferente, y CONTENIDO_ELIMINADO en caso
     * del que el campo haya quedado en blanco.
     */
    private static ModificacionesEnvio compareComunicCes(Envio newEnvio, Envio oldEnvio) {
        Envio.RegUno.Declaracion.Fichero.Cesion ne = newEnvio
                .getRegUno().getDeclaracion().get(0).getFichero().get(0).getCesion();
        Envio.RegUno.Declaracion.Fichero.Cesion oe = oldEnvio
                .getRegUno().getDeclaracion().get(0).getFichero().get(0).getCesion();
        
        ModificacionesEnvio status;
        
        if (ne.getCesiones().equals(oe.getCesiones())
                && ne.getDescOtros().equals(oe.getDescOtros())) {
            status =  ModificacionesEnvio.SIN_MODIFICACIONES;
        } else {
            status = ModificacionesEnvio.MODIFICADO;
        }
        
        if (status == ModificacionesEnvio.MODIFICADO 
                && ne.getCesiones().isEmpty() && ne.getDescOtros().isEmpty()) {
            status = ModificacionesEnvio.CONTENIDO_ELIMINADO;
        }
        return status;
    }
}
