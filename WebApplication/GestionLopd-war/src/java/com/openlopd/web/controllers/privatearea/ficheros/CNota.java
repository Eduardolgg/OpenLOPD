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

package com.openlopd.web.controllers.privatearea.ficheros;

import com.openlopd.agpd.nota.tablascomunes.CategoriasTransferenciasInternacionales;
import com.openlopd.agpd.nota.tablascomunes.Colectivos;
import com.openlopd.agpd.nota.tablascomunes.DestCesiones;
import com.openlopd.agpd.nota.tablascomunes.Finalidades;
import com.openlopd.agpd.nota.tablascomunes.OtrosDatosTipificados;
import com.openlopd.agpd.nota.tablascomunes.TipoSolicitud;
import com.openlopd.agpd.nota.tablascomunes.Titularidad;
import com.openlopd.agpd.nota.xml.DatosFichero;
import com.openlopd.sessionbeans.lopd.FicheroFacadeLocal;
import com.openlopd.sessionbeans.lopd.FicheroPredefinidoFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import java.io.Serializable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controlador del formulario de alta/modificacion/supresión de ficheros.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 9 de dic de 2012
 */
public class CNota extends DatosFichero implements Serializable {
    FicheroPredefinidoFacadeLocal ficheroPredefinidoFacade = lookupFicheroPredefinidoFacadeLocal();
    private static Logger logger = LoggerFactory.getLogger(CNota.class);
    FicheroFacadeLocal ficheroFacade = lookupFicheroFacadeLocal();
    private CSession session;

    //<editor-fold defaultstate="collapsed" desc="Configuración del formulario">
    private String titularidad; // Titularidad del fichero, por el momento solo privada.
    private String accion; // Accion a realizar Alta, Modficación o Supresión del fichero.
    private String id; // Id del fichero en el que ejecutar la acción, si es alta null;
    private String registrar;
    //</editor-fold>
    
    public CNota() {
    }
    
    public boolean init() {
        try {
            //TODO: Enviar el accessInfo.
            if (!this.isAccionAlta()) {
                ficheroFacade.load(null, this, id);
            } else if (id != null) {
                ficheroPredefinidoFacade.load(null, this, id);
            }
            return true;
        } catch (JAXBException ex) {
            return false;
        }
    }

    public void setSession(CSession session) {
        this.session = session;
    }

    //<editor-fold defaultstate="collapsed" desc="titularidad">
    @Override
    public String getTitularidad() {
        return titularidad;
    }

    @Override
    public void setTitularidad(String titularidad) {
        this.titularidad = titularidad;
    }

    public boolean isTitularidadPublica() {
        return titularidad.equals(Titularidad.PUBLICA.getValue());
    }

    public boolean isTitularidadPrivada() {
        return titularidad.equals(Titularidad.PRIVADA.getValue());
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="accion">
    @Override
    public String getAccion() {
        return accion;
    }

    @Override
    public void setAccion(String accion) {
        this.accion = accion;
    }

    public boolean isAccionAlta() {
        return accion.equals(TipoSolicitud.ALTA.getValue());
    }

    public boolean isAccionModificacion() {
        return accion.equals(TipoSolicitud.MODIFICACION.getValue());
    }

    public boolean isAccionSupresion() {
        return accion.equals(TipoSolicitud.SUPRESION.getValue());
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="id del Fichero">
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    //</editor-fold>
    
    public String getRegistrar() {
        return registrar;
    }

    public void setRegistrar(String registrar) {
        this.registrar = registrar;
    }    
    
    //<editor-fold defaultstate="collapsed" desc="Tablas Comunes GET">
    public Finalidades[] getlistadoFinalidades() {
        return Finalidades.values(); 
    }
    
    public Colectivos[] getlistadoColectivos() { 
        return Colectivos.values(); 
    }
    
    public OtrosDatosTipificados[] getOtrosDatosTipificadosDisponibles() { 
        return OtrosDatosTipificados.values(); 
    }
    
    public DestCesiones[] getlistadoDestCesiones() { 
        return DestCesiones.values(); 
    }
    
    public String[] getlistadoCategoriasTransferenciasInternacionales() {
        return CategoriasTransferenciasInternacionales.values();
    }
    
    private String paisTransferencia(String code) {
        String[] paises = this.getTransCodPais().split(";");
        String otro = "";
        if (getTransCategoriaOtros() != null &&  !getTransCategoriaOtros().isEmpty()) {
            otro =  paises[paises.length - 1];
            paises[paises.length - 1] = "";
        }
        switch (code) {
            case "primero": return paises.length > 0 ? paises[0] : "";
            case "segundo": return paises.length > 1 ? paises[1] : "";
            case "tercero": return paises.length > 2 ? paises[2] : "";
            case "cuarto": return paises.length > 3 ? paises[3] : "";
            case "otro": return otro;
        }
        return "";
    }   
    
    public String getPaisTransferenciaP() {
        return this.paisTransferencia("primero");
    }   
    
    public String getPaisTransferenciaS() {
        return this.paisTransferencia("segundo");
    }   
    
    public String getPaisTransferenciaT() {
        return this.paisTransferencia("tercero");
    }   
    
    public String getPaisTransferenciaC() {
        return this.paisTransferencia("cuarto");
    }   
    
    public String getPaisTransferenciaO() {
        return this.paisTransferencia("otro");
    }
   
    private String getCategoriaTransferencia(String code) {
        String[] categorias = this.getTransCategoria().split(";");
        switch (code) {
            case "primero": return categorias.length > 0 ? categorias[0] : "";
            case "segundo": return categorias.length > 1 ? categorias[1] : "";
            case "tercero": return categorias.length > 2 ? categorias[2] : "";
            case "cuarto": return categorias.length > 3 ? categorias[3] : "";
        }
        return "";
    }   
    
    public String getCategoriaTransferenciaP() {
        return this.getCategoriaTransferencia("primero");
    }   
    
    public String getCategoriaTransferenciaS() {
        return this.getCategoriaTransferencia("segundo");
    }   
    
    public String getCategoriaTransferenciaT() {
        return this.getCategoriaTransferencia("tercero");
    }   
    
    public String getCategoriaTransferenciaC() {
        return this.getCategoriaTransferencia("cuarto");
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Lookup">
    private FicheroFacadeLocal lookupFicheroFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (FicheroFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/FicheroFacade!com.openlopd.sessionbeans.lopd.FicheroFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean: lookupFicheroFacadeLocal.");
            throw new RuntimeException(ne);
        }
    }
    
    private FicheroPredefinidoFacadeLocal lookupFicheroPredefinidoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (FicheroPredefinidoFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/FicheroPredefinidoFacade!com.openlopd.sessionbeans.lopd.FicheroPredefinidoFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean: FicheroPredefinidoFacadeLocal.");
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>    
}
