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

import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.seguridad.Shadow;

/**
 * Contiene los datos necesarios para el registro de un fichero.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 26 de nov de 2012
 */
public class DatosFichero {
    public static final String CHECKBOX_ON = "ON";
    public static final String CHECKBOX_OFF = "";
    public static final String AGPD_TRUE = "1";
    public static final String AGPD_FALSE = "0";

    private Empresa empresa;
    private Shadow userInfo;
    private String codInscripcion;
    
    // Campos de formularios
    // Acción y titularidad
    private String id;
    private String titularidad;
    private String accion;
    
    // Responsable del fichero.
    private String respDenominacionSocial;
    private String respActividad;
    private String respCif;
    private String respDomicilioSocial;
    private String respLocalidad;
    private String respCodigoPostal;
    private String respProvincia;
    private String respPais;
    private String respTelefono;
    private String respFax;
    private String respEmail;
    //Derechos de oposición, acceso, rectificación y cancelación
    private String derechosOficina;
    private String derechosCif;
    private String derechosDireccionPostal;
    private String derechosLocalidad;
    private String derechosCodigoPostal;
    private String derechosProvincia;
    private String derechosPais;
    private String derechosTelefono;
    private String derechosFax;
    private String derechosEmail;
    // Dirección a efectos de notificación
    private String notifRazonSocial;
    private String notifDirPostal;
    private String notifLocalidad;
    private String notifCodPostal;
    private String notifProvincia;
    private String notifPais;
    private String notifTel;
    private String notifFax;
    private String notifEMail;
    private String notifDeberes;    
    
    // Declarante
    private String nombreDeclarante;
    private String primerApellidoDeclarante;
    private String segundoApellidoDeclarante;
    private String cifFirma;
    private String cargoDeclarante;
    
    // Encargado del Tratamiento
    private String encDenominacionSocial;
    private String encCif;
    private String encDomicilioSocial;
    private String encLocalidad;
    private String encCodigoPostal;
    private String encProvincia;
    private String encPais;
    private String encTelefono;
    private String encFax;
    private String encEmail;
            
    // Denominación e Identificación del fichero
    private String denominacion;
    private String usosPrevistos;
    private String finalidades;
    
    // Origen y procedencia de los datos
    private String origenInte;
    private String origenOtras;
    private String origenFap;
    private String origenRp;
    private String origenEp;
    private String origenAp;
    
    private String colectivos;
    private String otrosColectivos;
    
    // Medidas de seguridad
    private String nivel;
    
    // Datos especialmente protegidos
    private String datEspProtIdeologia;
    private String datEspProtAfiliacionSindical;
    private String datEspProtReligion;
    private String datEspProtCreencias;
    
    // Otros datos especialmete protegido
    private String otrosEspProtOrigenRacial;
    private String otrosEspProtSalud;
    private String otrosEspprotVidaSexual;
    
    // Datos de carácter identificativo
    private String identifNIF;
    private String identifNSS;
    private String identifNombreApp;
    private String identifTarjSanitaria;
    private String identifDireccion;
    private String identifTel;
    private String identifFirmaManual;
    private String identifFirmaHuella;
    private String identifOtrosDatosBio;
    private String identifImagVoz;
    private String identifMarcasFisicas;
    private String identifFirmaElectronica;
    private String identifOtros;
    
    // Otros datos tipificados
    private String otrosDatosTipificados;
    private String otrosTiposDeDatos;
    
    // Sistema de tratamiento
    private String sistTratamiento;
    
    // Categorías de destinatarios de cesiones
    private String destCesiones;
    private String otrosDestCesiones;
    
    // Países y destinatarios de la transferencia
    private String transCodPais;
    private String transCategoria;
    private String transCategoriaOtros;
    
    // Supresión
    private String supresionCodInscripcion;
    private String supresionMotivos;
    private String supresionDestInfo;
        
    public String getCheckBoxOn() {
        return CHECKBOX_ON;
    }    
    
    public String getAGPDTrue() {
        return AGPD_TRUE;
    }

    //<editor-fold defaultstate="collapsed" desc="Acción y Titularidad">
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getTitularidad() {
        return titularidad;
    }
    
    public void setTitularidad(String titularidad) {
        this.titularidad = titularidad;
    }
    
    public String getAccion() {
        return accion;
    }
    
    public void setAccion(String accion) {
        this.accion = accion;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Propiedades con datos de sesión">
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Shadow getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Shadow userInfo) {
        this.userInfo = userInfo;
    }
    
    public String getCodInscripcion() {
        return codInscripcion;
    }

    public void setCodInscripcion(String codInscripcion) {
        this.codInscripcion = codInscripcion;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Derechos de oposición, acceso, rectificación y cancelación">
    public String getDerechosOficina() {
        return checkString(derechosOficina);
    }

    public void setDerechosOficina(String derechosOficina) {
        this.derechosOficina = derechosOficina;
    }

    public String getDerechosCif() {
        return checkString(derechosCif);
    }

    public void setDerechosCif(String derechosCif) {
        this.derechosCif = derechosCif;
    }

    public String getDerechosDireccionPostal() {
        return checkString(derechosDireccionPostal);
    }

    public void setDerechosDireccionPostal(String derechosDireccionPostal) {
        this.derechosDireccionPostal = derechosDireccionPostal;
    }

    public String getDerechosLocalidad() {
        return checkString(derechosLocalidad);
    }

    public void setDerechosLocalidad(String derechosLocalidad) {
        this.derechosLocalidad = derechosLocalidad;
    }

    public String getDerechosCodigoPostal() {
        return checkString(derechosCodigoPostal);
    }

    public void setDerechosCodigoPostal(String derechosCodigoPostal) {
        this.derechosCodigoPostal = derechosCodigoPostal;
    }

    public String getDerechosProvincia() {
        return checkString(derechosProvincia);
    }

    public void setDerechosProvincia(String derechosProvincia) {
        this.derechosProvincia = derechosProvincia;
    }

    public String getDerechosPais() {
        return checkString(derechosPais);
    }

    public void setDerechosPais(String derechosPais) {
        this.derechosPais = derechosPais;
    }

    public String getDerechosTelefono() {
        return checkString(derechosTelefono);
    }

    public void setDerechosTelefono(String derechosTelefono) {
        this.derechosTelefono = derechosTelefono;
    }

    public String getDerechosFax() {
        return checkString(derechosFax);
    }

    public void setDerechosFax(String derechosFax) {
        this.derechosFax = derechosFax;
    }

    public String getDerechosEmail() {
        return checkString(derechosEmail);
    }

    public void setDerechosEmail(String derechosEmail) {
        this.derechosEmail = derechosEmail;
    }
    //</editor-fold>   

    //<editor-fold defaultstate="collapsed" desc="Responsable del fichero">
    public String getRespDenominacionSocial() {
        return checkString(respDenominacionSocial);
    }

    public void setRespDenominacionSocial(String respDenominacionSocial) {
        this.respDenominacionSocial = respDenominacionSocial;
    }

    public String getRespActividad() {
        return checkString(respActividad);
    }

    public void setRespActividad(String respActividad) {
        this.respActividad = respActividad;
    }

    public String getRespCif() {
        return checkString(respCif);
    }

    public void setRespCif(String respCif) {
        this.respCif = respCif;
    }

    public String getRespDomicilioSocial() {
        return checkString(respDomicilioSocial);
    }

    public void setRespDomicilioSocial(String respDomicilioSocial) {
        this.respDomicilioSocial = respDomicilioSocial;
    }

    public String getRespLocalidad() {
        return checkString(respLocalidad);
    }

    public void setRespLocalidad(String respLocalidad) {
        this.respLocalidad = respLocalidad;
    }

    public String getRespCodigoPostal() {
        return checkString(respCodigoPostal);
    }

    public void setRespCodigoPostal(String respCodigoPostal) {
        this.respCodigoPostal = respCodigoPostal;
    }

    public String getRespProvincia() {
        return checkString(respProvincia);
    }

    public void setRespProvincia(String respProvincia) {
        this.respProvincia = respProvincia;
    }

    public String getRespPais() {
        return checkString(respPais);
    }

    public void setRespPais(String respPais) {
        this.respPais = respPais;
    }

    public String getRespTelefono() {
        return checkString(respTelefono);
    }

    public void setRespTelefono(String respTelefono) {
        this.respTelefono = respTelefono;
    }

    public String getRespFax() {
        return checkString(respFax);
    }

    public void setRespFax(String respFax) {
        this.respFax = respFax;
    }

    public String getRespEmail() {
        return checkString(respEmail);
    }

    public void setRespEmail(String respEmail) {
        this.respEmail = respEmail;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Dirección a efectos de notificación">
    public String getNotifRazonSocial() {
        return checkString(notifRazonSocial);
    }

    public void setNotifRazonSocial(String notifRazonSocial) {
        this.notifRazonSocial = notifRazonSocial;
    }

    public String getNotifDirPostal() {
        return checkString(notifDirPostal);
    }

    public void setNotifDirPostal(String notifDirPostal) {
        this.notifDirPostal = notifDirPostal;
    }

    public String getNotifLocalidad() {
        return checkString(notifLocalidad);
    }

    public void setNotifLocalidad(String notifLocalidad) {
        this.notifLocalidad = notifLocalidad;
    }

    public String getNotifCodPostal() {
        return checkString(notifCodPostal);
    }

    public void setNotifCodPostal(String notifCodPostal) {
        this.notifCodPostal = notifCodPostal;
    }

    public String getNotifProvincia() {
        return checkString(notifProvincia);
    }

    public void setNotifProvincia(String notifProvincia) {
        this.notifProvincia = notifProvincia;
    }

    public String getNotifPais() {
        return checkString(notifPais);
    }

    public void setNotifPais(String notifPais) {
        this.notifPais = notifPais;
    }

    public String getNotifTel() {
        return checkString(notifTel);
    }

    public void setNotifTel(String notifTel) {
        this.notifTel = notifTel;
    }

    public String getNotifFax() {
        return checkString(notifFax);
    }

    public void setNotifFax(String notifFax) {
        this.notifFax = notifFax;
    }

    public String getNotifEMail() {
        return checkString(notifEMail);
    }

    public void setNotifEMail(String notifEMail) {
        this.notifEMail = notifEMail;
    }

    public String getNotifDeberes() {
        return checkString(notifDeberes);
    }

    public void setNotifDeberes(String notifDeberes) {
        this.notifDeberes = notifDeberes;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Declarante">
    public String getNombreDeclarante() {
        return nombreDeclarante;
    }
    
    public void setNombreDeclarante(String nombreDeclarante) {
        this.nombreDeclarante = nombreDeclarante;
    }
    
    public String getPrimerApellidoDeclarante() {
        return primerApellidoDeclarante;
    }
    
    public void setPrimerApellidoDeclarante(String primerApellidoDeclarante) {
        this.primerApellidoDeclarante = primerApellidoDeclarante;
    }
    
    public String getSegundoApellidoDeclarante() {
        return segundoApellidoDeclarante;
    }
    
    public void setSegundoApellidoDeclarante(String segundoApellidoDeclarante) {
        this.segundoApellidoDeclarante = segundoApellidoDeclarante;
    }
    
    public String getCifFirma() {
        return cifFirma;
    }
    
    public void setCifFirma(String cifFirma) {
        this.cifFirma = cifFirma;
    }
    
    public String getCargoDeclarante() {
        return cargoDeclarante;
    }
    
    public void setCargoDeclarante(String cargoDeclarante) {
        this.cargoDeclarante = cargoDeclarante;
    }
    //</editor-fold>
        
    //<editor-fold defaultstate="collapsed" desc="Encargado del Tratamiento">
    public String getEncDenominacionSocial() {
        return checkString(encDenominacionSocial);
    }

    public void setEncDenominacionSocial(String encDenominacionSocial) {
        this.encDenominacionSocial = encDenominacionSocial;
    }

    public String getEncCif() {
        return checkString(encCif);
    }

    public void setEncCif(String encCif) {
        this.encCif = encCif;
    }

    public String getEncDomicilioSocial() {
        return checkString(encDomicilioSocial);
    }

    public void setEncDomicilioSocial(String encDomicilioSocial) {
        this.encDomicilioSocial = encDomicilioSocial;
    }

    public String getEncLocalidad() {
        return checkString(encLocalidad);
    }

    public void setEncLocalidad(String encLocalidad) {
        this.encLocalidad = encLocalidad;
    }

    public String getEncCodigoPostal() {
        return checkString(encCodigoPostal);
    }

    public void setEncCodigoPostal(String encCodigoPostal) {
        this.encCodigoPostal = encCodigoPostal;
    }

    public String getEncProvincia() {
        return checkString(encProvincia);
    }

    public void setEncProvincia(String encProvincia) {
        this.encProvincia = encProvincia;
    }

    public String getEncPais() {
        return checkString(encPais);
    }

    public void setEncPais(String encPais) {
        this.encPais = encPais;
    }

    public String getEncTelefono() {
        return checkString(encTelefono);
    }

    public void setEncTelefono(String encTelefono) {
        this.encTelefono = encTelefono;
    }

    public String getEncFax() {
        return checkString(encFax);
    }

    public void setEncFax(String encFax) {
        this.encFax = encFax;
    }

    public String getEncEmail() {
        return checkString(encEmail);
    }

    public void setEncEmail(String encEmail) {
        this.encEmail = encEmail;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Denominación e Identificación del fichero">
    public String getDenominacion() {
        return checkString(denominacion);
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getUsosPrevistos() {
        return checkString(usosPrevistos);
    }

    public void setUsosPrevistos(String usosPrevistos) {
        this.usosPrevistos = usosPrevistos;
    }

    public String getFinalidades() {
        return checkString(finalidades);
    }

    public void setFinalidades(String finalidades) {
        this.finalidades = finalidades;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Origen y procedencia de los datos">
    public String getOrigenInte() {
        return getAgpdBooleanFromCheckBox(origenInte);
    }
    
    public void setOrigenInte(String origenInte) {
        this.origenInte = origenInte;
    }
    
    public String getOrigenOtras() {
        return getAgpdBooleanFromCheckBox(origenOtras);
    }
    
    public void setOrigenOtras(String origenOtras) {
        this.origenOtras = origenOtras;
    }
    
    public String getOrigenFap() { 
        return getAgpdBooleanFromCheckBox(origenFap);   
    }
    
    public void setOrigenFap(String origenFap) {
        this.origenFap = origenFap;
    }
    
    public String getOrigenRp() {   
        return getAgpdBooleanFromCheckBox(origenRp);
    }
    
    public void setOrigenRp(String origenRp) {
        this.origenRp = origenRp;
    }
    
    public String getOrigenEp() {
        return getAgpdBooleanFromCheckBox(origenEp);
    }
    
    public void setOrigenEp(String origenEp) {
        this.origenEp = origenEp;
    }
    
    public String getOrigenAp() {
        return getAgpdBooleanFromCheckBox(origenAp);
    }
    
    public void setOrigenAp(String origenAp) {
        this.origenAp = origenAp;
    }
    public String getColectivos() {
        return checkString(colectivos);
    }

    public void setColectivos(String colectivos) {
        this.colectivos = colectivos;
    }

    public String getOtrosColectivos() {
        return checkString(otrosColectivos);
    }

    public void setOtrosColectivos(String otrosColectivos) {
        this.otrosColectivos = otrosColectivos;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Medidas de seguridad">
    public String getNivel() {
        return checkString(nivel);
    }
    
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Datos especialmente protegidos">
    public String getDatEspProtIdeologia() {
        return getAgpdBooleanFromCheckBox(datEspProtIdeologia);
    }
    
    public void setDatEspProtIdeologia(String datEspProtIdeologia) {
        this.datEspProtIdeologia = datEspProtIdeologia;
    }
    
    public String getDatEspProtAfiliacionSindical() {
        return getAgpdBooleanFromCheckBox(datEspProtAfiliacionSindical);
    }
    
    public void setDatEspProtAfiliacionSindical(String datEspProtAfiliacionSindical) {
        this.datEspProtAfiliacionSindical = datEspProtAfiliacionSindical;
    }
    
    public String getDatEspProtReligion() {
        return getAgpdBooleanFromCheckBox(datEspProtReligion);
    }
    
    public void setDatEspProtReligion(String datEspProtReligion) {
        this.datEspProtReligion = datEspProtReligion;
    }
    
    public String getDatEspProtCreencias() {
        return getAgpdBooleanFromCheckBox(datEspProtCreencias);
    }
    
    public void setDatEspProtCreencias(String datEspProtCreencias) {
        this.datEspProtCreencias = datEspProtCreencias;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Otros datos especialmete protegido">
    public String getOtrosEspProtOrigenRacial() {
        return getAgpdBooleanFromCheckBox(otrosEspProtOrigenRacial);
    }
    
    public void setOtrosEspProtOrigenRacial(String otrosEspProtOrigenRacial) {
        this.otrosEspProtOrigenRacial = otrosEspProtOrigenRacial;
    }
    
    public String getOtrosEspProtSalud() {
        return getAgpdBooleanFromCheckBox(otrosEspProtSalud);
    }
    
    public void setOtrosEspProtSalud(String otrosEspProtSalud) {
        this.otrosEspProtSalud = otrosEspProtSalud;
    }
    
    public String getOtrosEspprotVidaSexual() {
        return getAgpdBooleanFromCheckBox(otrosEspprotVidaSexual);
    }
    
    public void setOtrosEspprotVidaSexual(String otrosEspprotVidaSexual) {
        this.otrosEspprotVidaSexual = otrosEspprotVidaSexual;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Datos de carácter identificativo">
    public String getIdentifNIF() {
        return getAgpdBooleanFromCheckBox(identifNIF);
    }
    
    public void setIdentifNIF(String identifNIF) {
        this.identifNIF = identifNIF;
    }
    
    public String getIdentifNSS() {
        return getAgpdBooleanFromCheckBox(identifNSS);
    }
    
    public void setIdentifNSS(String identifNSS) {
        this.identifNSS = identifNSS;
    }
    
    public String getIdentifNombreApp() {
        return getAgpdBooleanFromCheckBox(identifNombreApp);
    }
    
    public void setIdentifNombreApp(String identifNombreApp) {
        this.identifNombreApp = identifNombreApp;
    }
    
    public String getIdentifTarjSanitaria() {
        return getAgpdBooleanFromCheckBox(identifTarjSanitaria);
    }
    
    public void setIdentifTarjSanitaria(String identifTarjSanitaria) {
        this.identifTarjSanitaria = identifTarjSanitaria;
    }
    
    public String getIdentifDireccion() {
        return getAgpdBooleanFromCheckBox(identifDireccion);
    }
    
    public void setIdentifDireccion(String identifDireccion) {
        this.identifDireccion = identifDireccion;
    }
    
    public String getIdentifTel() {
        return getAgpdBooleanFromCheckBox(identifTel);
    }
    
    public void setIdentifTel(String identifTel) {
        this.identifTel = identifTel;
    }
    
    public String getIdentifFirmaManual() {
        return getAgpdBooleanFromCheckBox(identifFirmaManual);
    }
    
    public void setIdentifFirmaManual(String identifFirmaManual) {
        this.identifFirmaManual = identifFirmaManual;
    }    
    
    public String getIdentifFirmaHuella() {
        return getAgpdBooleanFromCheckBox(identifFirmaHuella);
    }
    
    public void setIdentifFirmaHuella(String identifFirmaHuella) {
        this.identifFirmaHuella = identifFirmaHuella;
    }

    public String getIdentifOtrosDatosBio() {
        return getAgpdBooleanFromCheckBox(identifOtrosDatosBio);
    }

    public void setIdentifOtrosDatosBio(String identifOtrosDatosBio) {
        this.identifOtrosDatosBio = identifOtrosDatosBio;
    }
    
    public String getIdentifImagVoz() {
        return getAgpdBooleanFromCheckBox(identifImagVoz);
    }
    
    public void setIdentifImagVoz(String identifImagVoz) {
        this.identifImagVoz = identifImagVoz;
    }
    
    public String getIdentifMarcasFisicas() {
        return getAgpdBooleanFromCheckBox(identifMarcasFisicas);
    }
    
    public void setIdentifMarcasFisicas(String identifMarcasFisicas) {
        this.identifMarcasFisicas = identifMarcasFisicas;
    }
    
    public String getIdentifFirmaElectronica() {
        return getAgpdBooleanFromCheckBox(identifFirmaElectronica);
    }
    
    public void setIdentifFirmaElectronica(String identifFirmaElectronica) {
        this.identifFirmaElectronica = identifFirmaElectronica;
    }
    
    public String getIdentifOtros() {
        return checkString(identifOtros);
    }
    
    public void setIdentifOtros(String identifOtros) {
        this.identifOtros = identifOtros;
    }
    //</editor-fold>   

    //<editor-fold defaultstate="collapsed" desc="Otros datos tipificados">
    public String getOtrosDatosTipificados() {
        return checkString(otrosDatosTipificados);
    }
    
    public void setOtrosDatosTipificados(String otrosDatosTipificados) {
        this.otrosDatosTipificados = otrosDatosTipificados;
    }
    
    public String getOtrosTiposDeDatos() {
        return checkString(otrosTiposDeDatos);
    }
    
    public void setOtrosTiposDeDatos(String otrosTiposDeDatos) {
        this.otrosTiposDeDatos = otrosTiposDeDatos;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Sistema de tratamiento">
    public String getSistTratamiento() {
        return checkString(sistTratamiento);
    }
    
    public void setSistTratamiento(String sistTratamiento) {
        this.sistTratamiento = sistTratamiento;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Categorías de destinatarios de cesiones">
    public String getDestCesiones() {
        return checkString(destCesiones);
    }
    
    public void setDestCesiones(String destCesiones) {
        this.destCesiones = destCesiones;
    }
    
    public String getOtrosDestCesiones() {
        return checkString(otrosDestCesiones);
    }
    
    public void setOtrosDestCesiones(String otrosDestCesiones) {
        this.otrosDestCesiones = otrosDestCesiones;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Países y destinatarios de la transferencia">
    public String getTransCodPais() {
        return checkString(transCodPais);
    }
    
    public void setTransCodPais(String transCodPais) {
        this.transCodPais = transCodPais;
    }
    
    public String getTransCategoria() {
        return checkString(transCategoria);
    }
    
    public void setTransCategoria(String transCategoria) {
        this.transCategoria = transCategoria;
    }
    
    public String getTransCategoriaOtros() {
        return checkString(transCategoriaOtros);
    }
    
    public void setTransCategoriaOtros(String transCategoriaOtros) {
        this.transCategoriaOtros = transCategoriaOtros;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Supresión">
    public String getSupresionCodInscripcion() {
        return supresionCodInscripcion;
    }
    
    public void setSupresionCodInscripcion(String supresionCodInscripcion) {
        this.supresionCodInscripcion = supresionCodInscripcion;
    }
    
    public String getSupresionMotivos() {
        return supresionMotivos;
    }
    
    public void setSupresionMotivos(String supresionMotivos) {
        this.supresionMotivos = supresionMotivos;
    }
    
    public String getSupresionDestInfo() {
        return supresionDestInfo;
    }
    
    public void setSupresionDestInfo(String supresionDestInfo) {
        this.supresionDestInfo = supresionDestInfo;
    }
    //</editor-fold>
      
    private String getAgpdBooleanFromCheckBox(String checkbox) {        
        if(checkbox != null && checkbox.equals(CHECKBOX_ON)) {
            return AGPD_TRUE;
        } else {
            return AGPD_FALSE;
        }        
    }
    
    public String getCheckBoxFromAgpdBoolean(String agpdBoolean) {
        if(agpdBoolean != null && agpdBoolean.equals(AGPD_TRUE)) {
            return CHECKBOX_ON;
        } else {
            return CHECKBOX_OFF;
        }
    }
    
    private String checkString(String myString) {
        return (myString != null ? myString : "");
    }
}
