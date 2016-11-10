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

package com.openlopd.web.controllers.privatearea;

import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.entities.empresas.Persona;
import com.openlopd.entities.facturacion.TipoFormaPago;
import com.openlopd.entities.lopd.Destinatario;
import com.openlopd.entities.lopd.ModoDestruccion;
import com.openlopd.entities.lopd.TipoActividadPrincipal;
import com.openlopd.entities.lopd.TipoSoporte;
import com.openlopd.entities.lopd.UnidadesInformacion;
import com.openlopd.entities.seguridad.TiposUsuario;
import com.openlopd.sessionbeans.empresas.PersonaFacadeLocal;
import com.openlopd.sessionbeans.facturacion.TipoFormaPagoFacadeLocal;
import com.openlopd.sessionbeans.lopd.DestinatarioFacadeLocal;
import com.openlopd.sessionbeans.lopd.ModoDestruccionFacadeLocal;
import com.openlopd.sessionbeans.lopd.TipoActividadPrincipalFacadeLocal;
import com.openlopd.sessionbeans.lopd.TipoSoporteFacadeLocal;
import com.openlopd.sessionbeans.lopd.UnidadesInformacionFacadeLocal;
import com.openlopd.sessionbeans.seguridad.TiposUsuarioFacadeLocal;
import com.openlopd.common.localizacion.business.LocalizacionLocal;
import com.openlopd.common.localizacion.entities.Pais;
import com.openlopd.common.localizacion.entities.Provincia;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gestiona la recogida de datos de tablas auxiliares que puede ser común en
 * muchas de las clases controladoras.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0
 */
public class AuxTables {

    private static Logger logger = LoggerFactory.getLogger(AuxTables.class);
    private UnidadesInformacionFacadeLocal unidadesInformacionFacade = lookupUnidadesInformacionFacadeLocal();
    private TiposUsuarioFacadeLocal tiposUsuarioFacade = lookupTiposUsuarioFacadeLocal();
    private TipoFormaPagoFacadeLocal tipoFormaPagoFacade = lookupTipoFormaPagoFacadeLocal();
    private TipoSoporteFacadeLocal tipoSoporteFacade = lookupTipoSoporteFacadeLocal();
    private TipoActividadPrincipalFacadeLocal tipoActividadPrincipalFacade = lookupTipoActividadPrincipalFacadeLocal();
    private PersonaFacadeLocal personaFacade = lookupPersonaFacadeLocal();
    private ModoDestruccionFacadeLocal modoDestruccionFacade = lookupModoDestruccionFacadeLocal();
    private DestinatarioFacadeLocal destinatarioFacade = lookupDestinatarioFacadeLocal();
    private LocalizacionLocal localizacion = lookupLocalizacionLocal();
    private AccessInfo accessInfo;

    /**
     * Constructor por defecto.
     */
    public AuxTables() {
    }

    /**
     * Inicializa el objeto con la información de acceso del usuario.
     * 
     * La información de acceso será utilizada para obtener los listados
     * (principalmente tablas auxiliares) disponibles en este objeto.
     * 
     * @param accessInfo Información de acceso del usuario.
     */
    public AuxTables(AccessInfo accessInfo) {
        this.accessInfo = accessInfo;
    }

    /**
     * Establece la información de acceso del usuario.
     * @param accessInfo Información de acceso del usuario.
     */
    public void setAccessInfo(AccessInfo accessInfo) {
        this.accessInfo = accessInfo;
    }

    /**
     * Obtiene el listado de destinatarios de soportes habilitados en la
     * empresa gestionada.
     * @return Listado de destinatarios habilitados.
     */
    public List<Destinatario> getAuxDestinatarios() {
        return destinatarioFacade.findAll(accessInfo.getSubEmpresa());
    }

    /**
     * Obtiene el listado de modos de destrucción disponibles en la empresa
     * gestionada.
     * @return Listado de modos de destrución disponibles.
     */
    public List<ModoDestruccion> getAuxModoDestruccion() {
        return modoDestruccionFacade.findAll(accessInfo.getSubEmpresa());
    }

    /**
     * Obtiene las personas autorizadas para la salida/entrega de soportes.
     * @return Listado de personas autorizadas para la salida/entrega
     * de soportes.
     */
    public List<Persona> getAutorizadosSalida() {
        return personaFacade.getAutorizadosSalida(accessInfo.getSubEmpresa());
    }

    /**
     * Obtiene las personas autorizadas para la recepción de soportes.
     * @return Listado de personas autorizadas para la recepción de soportes.
     */
    public List<Persona> getAutorizadosEntrada() {
        //TODO: Pendiente de añadir permisos.
        return personaFacade.getAutorizadosEntrada(accessInfo.getSubEmpresa());
    }

    /**
     * Tipos de soportes disponibles para la empresa gestionada.
     * @return Listado con los tipos de soportes.
     */
    public List<TipoSoporte> getAuxTiposSoportes() {
        return tipoSoporteFacade.getActives(accessInfo);
    }
    
    /**
     * Información sobre la actividad de la empresa.
     * @return actividad principal de la empresa.
     */
    public List<TipoActividadPrincipal> getTipoActividadPrincipal() {
        return tipoActividadPrincipalFacade.findAll();
    }
    
    /**
     * Información sobre la actividad de la empresa.
     * @param id Identificador de la actividad a buscar.
     * @return actividad principal de la empresa.
     */
    public TipoActividadPrincipal getTipoActividadPrincipal(String id) {
        return tipoActividadPrincipalFacade.find(id);
    }

    /**
     * Tipos de formas de pago habilitadas.
     * @return tipos de formas de pago.
     */
    public List<TipoFormaPago> getAuxTiposFormaPago() {
        return tipoFormaPagoFacade.getActivos();
    }
    
    /**
     * Información sobre el responsable de seguridad de la empresa
     * gestionada.
     * @return <code>Persona</code> responsable de seguridad.
     */
    public Persona getResponsableSeguridad() {
        return personaFacade.getResponsableSeguridad(accessInfo);
    }

    /**
     * Obtiene los tipos de usuario.
     *
     * CIF, NIF, Admin y Usuario.
     *
     * @return Tipos de usuarios.
     */
    public List<TiposUsuario> getAuxTiposUsuario() {
        return tiposUsuarioFacade.findAll();
    }

    /**
     * Obtiene los tipos de unidades de información de almacenamiento
     * disponibles.
     * @return Listado de unidades de almacenamiento de información.
     */
    public List<UnidadesInformacion> getAuxUnidadesInformacion() {
        return unidadesInformacionFacade.getActives();
    }

    /**
     * Obtiene un listado de todas las provincias disponibles.
     * 
     * Por el momento solo provincias españolas.
     * 
     * @return Listado de provincias.
     */
    public List<Provincia> getProvincias() {
        return localizacion.getProvincias();
    }

    /**
     * Obtiene el listado de todos los paises disponibles.
     * @return Listado de paises disponibles.
     */
    public List<Pais> getPaises() {
        return localizacion.getPaises();
    }
    
    public List<Pais> getPaisesAgpdResponsable() {
        return localizacion.getPaisesAgpdResponsable();
    }
    
    public List<Pais> getPaisesAgpdTransferInternacional() {
        return localizacion.getPaisesAgpdTransferInternacional();
    }
    
    public List<Pais> getPaisesAgpdDeclarante() {
        return localizacion.getPaisesAgpdDeclarante();
    }
    
    public List<Pais> getPaisesAgpdEncargado() {
        return localizacion.getPaisesAgpdEncargado();
    }
    
    public List<Pais> getPaisesAgpdAcceso() {
        return localizacion.getPaisesAgpdAcceso();
    }
    
    public String getNombreLocalidad(Long idLocalidad) {
        try {
            return localizacion.getLocalidad(idLocalidad).getLocalidad();
        } catch (Exception e) {
            return "";
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Section EJB">
    private DestinatarioFacadeLocal lookupDestinatarioFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (DestinatarioFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/DestinatarioFacade!com.openlopd.sessionbeans.lopd.DestinatarioFacadeLocal");
        } catch (NamingException ne) {
            logger.error("lookupDestinatarioFacadeLocal ERROR: {}", ne.getMessage());
            throw new RuntimeException(ne);
        }
    }

    private ModoDestruccionFacadeLocal lookupModoDestruccionFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ModoDestruccionFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/ModoDestruccionFacade!com.openlopd.sessionbeans.lopd.ModoDestruccionFacadeLocal");
        } catch (NamingException ne) {
            logger.error("lookupModoDestruccionFacadeLocal ERROR: {}", ne.getMessage());
            throw new RuntimeException(ne);
        }
    }

    private PersonaFacadeLocal lookupPersonaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (PersonaFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/PersonaFacade!com.openlopd.sessionbeans.empresas.PersonaFacadeLocal");
        } catch (NamingException ne) {
            logger.error("lookupPersonaFacadeLocal ERROR: {}", ne.getMessage());
            throw new RuntimeException(ne);
        }
    }

    private TipoSoporteFacadeLocal lookupTipoSoporteFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (TipoSoporteFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/TipoSoporteFacade!com.openlopd.sessionbeans.lopd.TipoSoporteFacadeLocal");
        } catch (NamingException ne) {
            logger.error("lookupTipoSoporteFacadeLocal ERROR: {}", ne.getMessage());
            throw new RuntimeException(ne);
        }
    }

    private TipoFormaPagoFacadeLocal lookupTipoFormaPagoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (TipoFormaPagoFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/TipoFormaPagoFacade!com.openlopd.sessionbeans.facturacion.TipoFormaPagoFacadeLocal");
        } catch (NamingException ne) {
            logger.error("lookupTipoFormaPagoFacadeLocal ERROR: {}", ne.getMessage());
            throw new RuntimeException(ne);
        }
    }

    private TiposUsuarioFacadeLocal lookupTiposUsuarioFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (TiposUsuarioFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/TiposUsuarioFacade!com.openlopd.sessionbeans.seguridad.TiposUsuarioFacadeLocal");
        } catch (NamingException ne) {
            logger.error("lookupTiposUsuarioFacadeLocal ERROR: {}", ne.getMessage());
            throw new RuntimeException(ne);
        }
    }

    private UnidadesInformacionFacadeLocal lookupUnidadesInformacionFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (UnidadesInformacionFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/UnidadesInformacionFacade!com.openlopd.sessionbeans.lopd.UnidadesInformacionFacadeLocal");
        } catch (NamingException ne) {
            logger.error("lookupUnidadesInformacionFacadeLocal ERROR: {}", ne.getMessage());
            throw new RuntimeException(ne);
        }
    }
    
    private LocalizacionLocal lookupLocalizacionLocal() {
        try {
            Context c = new InitialContext();
            return (LocalizacionLocal) c.lookup("java:global/GestionLopd/common-ejb/Localizacion!com.openlopd.common.localizacion.business.LocalizacionLocal");
        } catch (NamingException ne) {
            logger.error("lookupLocalizacionLocal ERROR: {}", ne.getMessage());
            throw new RuntimeException(ne);
        }
    }

    private TipoActividadPrincipalFacadeLocal lookupTipoActividadPrincipalFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (TipoActividadPrincipalFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/TipoActividadPrincipalFacade!com.openlopd.sessionbeans.lopd.TipoActividadPrincipalFacadeLocal");
        } catch (NamingException ne) {
            logger.error("lookupTipoActividadPrincipalFacadeLocal ERROR: {}", ne.getMessage());
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>

}
