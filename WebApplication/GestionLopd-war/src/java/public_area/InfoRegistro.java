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

package public_area;

import com.openlopd.business.facturacion.FacturacionLocal;
import com.openlopd.business.seguridad.ContratosLocal;
import com.openlopd.business.seguridad.SeguridadLocal;
import com.openlopd.business.seguridad.ShortAccessInfo;
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.empresas.EmpresaSede;
import com.openlopd.entities.empresas.Persona;
import com.openlopd.entities.facturacion.Factura;
import com.openlopd.entities.seguridad.ContratosTipo;
import com.openlopd.sessionbeans.seguridad.ContratosTipoFacadeLocal;
import com.openlopd.common.localizacion.business.LocalizacionLocal;
import com.openlopd.common.localizacion.entities.Localidad;
import com.openlopd.common.localizacion.entities.Provincia;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controlador de la página /publicArea/infoRegstro.jsp
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.2 17 de mar de 2011
 * Modificaciones:
 *     14 de mar de 2011 Permite obtener los importes por DB.
 *     15 de mar de 2011 Añadidos métodos para mostrar forma de pago.
 *     16 de mar de 2011 Añadida la entrada de password.
 *     16 de mar de 2011 Control de errores de lado de servidor.
 *     17 de mar de 2011 Añadido el método getCleanObject, además se añaden
 *        en cada uno de los lookup una llamada a StandardLogger.
 */
public class InfoRegistro implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(InfoRegistro.class);
    SeguridadLocal seguridad = lookupSeguridadLocal();
    
    // <editor-fold defaultstate="collapsed" desc="Business Properties">
    FacturacionLocal facturacion = lookupFacturacionLocal();
    ContratosLocal contratos = lookupContratosLocal();
    private LocalizacionLocal localizacion = lookupLocalizacionLocal();
    private ContratosTipoFacadeLocal contratosTipoFacade = lookupContratosTipoFacadeLocal();
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Alta Properties">
    private ShortAccessInfo userInfo;
    private Empresa empresa;
    private EmpresaSede empresaSede;
    private Persona contacto;
    private Factura factura;
    // </editor-fold>

    private boolean error;
    private BigDecimal importe;

    // <editor-fold defaultstate="collapsed" desc="Interfaz Properties">
    private int tipoPaquete;
    // Datos Empresa
    private String cif;
    private String razonSocial;
    private String tel;
    private String movil;
    private String fax;
    private String dir;
    private String cp;
    private String provincia;
    private Long localidad;
    // Password
    private String clave;
    // Datos Persona Contacto
    private String perNombre;
    private String perApellido1;
    private String perApellido2;
    private String perCargo;
    private String perTel;
    private String perMail;
    // Datos Forma Pago
    private int formaPago;
    // Política
    private String aceptaPolitica;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructores">
    public InfoRegistro() {
        
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Section GetSet">
    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Long getLocalidad() {
        return localidad;
    }

    public String getLocalidadDesc() {
        return ((Localidad) localizacion.getLocalidad(localidad)).getLocalidad();
    }

    public void setLocalidad(Long localidad) {
        this.localidad = localidad;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getPerApellido1() {
        return perApellido1;
    }

    public void setPerApellido1(String perApellido1) {
        this.perApellido1 = perApellido1;
    }

    public String getPerApellido2() {
        return perApellido2;
    }

    public void setPerApellido2(String perApellido2) {
        this.perApellido2 = perApellido2;
    }

    public String getPerCargo() {
        return perCargo;
    }

    public void setPerCargo(String perCargo) {
        this.perCargo = perCargo;
    }

    public String getPerMail() {
        return perMail;
    }

    public void setPerMail(String perMail) {
        this.perMail = perMail;
    }

    public String getPerNombre() {
        return perNombre;
    }

    public void setPerNombre(String perNombre) {
        this.perNombre = perNombre;
    }

    public String getPerTel() {
        return perTel;
    }

    public void setPerTel(String perTel) {
        this.perTel = perTel;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getProvinciaDesc() {
        return ((Provincia) localizacion.getProvincia(provincia)).getProvincia();
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * Obtiene el tipo de paquete (contrato) que desea el usuario.
     * @return código del tipo de paquete.
     */
    public int getTipoPaquete() {
        return tipoPaquete;
    }

    /**
     * Establece el tipo de paquete (contrato) que desea el usuario.
     * @param tipoPaquete código del tipo de paquete.
     */
    public void setTipoPaquete(int tipoPaquete) {
        this.tipoPaquete = tipoPaquete;
    }

    public String getImporteContrato() {
        this.importe = ((ContratosTipo) contratos.getContratoTipo((short) tipoPaquete)).getImporte();
        return this.importe.toPlainString();
    }

    public int getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(int formaPago) {
        this.formaPago = formaPago;
    }

    public String getFormaPagoDesc() {
        try {
            return this.facturacion.getTipoFormaPagoById((short) formaPago).getNombre();
        } catch (Exception e) {
            return "desconocido";
        }
    }

    public String getAceptaPolitica() {
        return aceptaPolitica;
    }

    public void setAceptaPolitica(String aceptaPolitica) {
        this.aceptaPolitica = aceptaPolitica;
    }

    /**
     * Este método verifica si los datos recibidos son correctos.
     * @return
     */
    public boolean getError() {
        if (tipoPaquete < 0 || formaPago < 0 || cif == null || razonSocial == null
                || clave == null || tel == null || dir == null || cp == null
                || provincia == null || localidad == null     
                || perNombre == null || perApellido1 == null || perCargo == null
                || perMail == null || aceptaPolitica == null) {
            return true;
        }
        if (cif.length() < 9 || !aceptaPolitica.equals("on")) {
            return true;
        }
        return false;
    }

    public String getTipoPaqueteDesc() {
        try {
            return ((ContratosTipo) contratosTipoFacade.find((short) tipoPaquete)).getNombre();            
        } catch (Exception e) {
            return "desconocido";
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Extra Methods">
    /**
     * Inicializa el objeto eliminando toda la información del mismo.
     * @return devuelve true en caso de que el resultado del borrado sea correcto, false
     * si no se ha podido eliminar la información contenida en el mismo.
     */
    public boolean getCleanObject() {
        try {
            tipoPaquete = -1;
            // Datos Empresa
            cif = razonSocial = tel = movil = fax = dir = cp = provincia = null;
            localidad = null;
            // Password
            clave = null;
            // Datos Persona Contacto
            perNombre = perApellido1 = perApellido2 = perCargo = perTel = perMail = null;
            // Datos Forma Pago
            formaPago = -1;
            // Política
            aceptaPolitica = null;
            return true;
        } catch (Exception e) {
            logger.error("No se ha podido borrar toda la información del objeto: " + this.toString());
            return false;
        }
    }

    /**
     * Crea un nuevo contrato con los objetos ya inicializados de la clase.
     * @return Un código de error dependiendo de si se ha podido realizar el alta.
     * E0000 en caso de realizar el alta correctamente.
     * E0001 en caso de que el objeto no esté correctamente inicializado.
     * E0002 en caso de que no se pueda realizar el contrato correctamente.
     */
    public String getContratar(){
        if (this.getError()) {
            return "E0001";
        }
        try {
            userInfo = new ShortAccessInfo(cif, clave);
            empresa = new Empresa(cif, razonSocial);
            empresaSede = new EmpresaSede(empresa, true, razonSocial, tel, movil, fax,
                    dir, cp, provincia, localidad);
            contacto = new Persona(perNombre, perApellido1, perApellido2, perTel,
                    perMail, perCargo);
            factura = new Factura((short) formaPago, importe);
            String result = seguridad.nuevoContrato(userInfo, empresa, empresaSede, contacto, factura, tipoPaquete);
            if (result.length() == 5) {
                return result;
            } else {
                return "E0000";
            }
        } catch (Exception e) {
            logger.error("Error dando de alta el contrato: " + this.toString() + "\n"
                    + "Exception: " + e.getMessage());
            return "E0002";
        }
    }
    
    /**
     * Log para el primer loggin del usuario.
     */
    public void logginOk() {
        logger.info("Se ha iniciado el primer loggin de [{}] correctamente.", cif);
        this.getCleanObject();
    }
    
    /**
     * Log para el primer loggin del usuario.
     */
    public void logginErr() {
        logger.error("Se ha producido un error en el primer loggin de [{}]", cif);
        this.getCleanObject();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Lookup Section">
    private ContratosTipoFacadeLocal lookupContratosTipoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ContratosTipoFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/ContratosTipoFacade!com.openlopd.sessionbeans.seguridad.ContratosTipoFacadeLocal");
        } catch (NamingException ne) {
            //Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            logger.error("Error el resolver el nombre para lookupContratosTipoFacadeLocal, clase:"
                    + this.getClass().getName().toString());
            throw new RuntimeException(ne);
        }
    }

    private SeguridadLocal lookupSeguridadLocal() {
        try {
            Context c = new InitialContext();
            return (SeguridadLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/Seguridad!com.openlopd.business.seguridad.SeguridadLocal");
        } catch (NamingException ne) {
            logger.error("lookupSeguridadLocal ERROR: {}", ne.getMessage());
            throw new RuntimeException(ne);
        }
    }
    
    private LocalizacionLocal lookupLocalizacionLocal() {
        try {
            Context c = new InitialContext();
            return (LocalizacionLocal) c.lookup("java:global/GestionLopd/common-ejb/Localizacion!com.openlopd.common.localizacion.business.LocalizacionLocal");
        } catch (NamingException ne) {            
            logger.error("En lookup para lookupLocalizacionLocal, clase:"
                    + this.getClass().getName().toString());
            throw new RuntimeException(ne);
        }
    }

    private ContratosLocal lookupContratosLocal() {
        try {
            Context c = new InitialContext();
            return (ContratosLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/Contratos!com.openlopd.business.seguridad.ContratosLocal");
        } catch (NamingException ne) {
            //Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            logger.error("En lookup para lookupLocalizacionLocal, clase:"
                    + this.getClass().getName().toString());
            throw new RuntimeException(ne);
        }
    }

    private FacturacionLocal lookupFacturacionLocal() {
        try {
            Context c = new InitialContext();
            return (FacturacionLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/Facturacion!com.openlopd.business.facturacion.FacturacionLocal");
        } catch (NamingException ne) {
            //Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            logger.error("En lookup para lookupLocalizacionLocal, clase:"
                    + this.getClass().getName().toString());
            throw new RuntimeException(ne);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Default Methods">
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InfoRegistro other = (InfoRegistro) obj;
        if ((this.cif == null) ? (other.cif != null) : !this.cif.equals(other.cif)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.cif != null ? this.cif.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "InfoRegistro{" + "userInfo=" + userInfo + ", empresa=" + empresa + ", empresaSede=" + empresaSede + ", contacto=" + contacto + ", factura=" + factura + ", error=" + error + ", importe=" + importe + ", tipoPaquete=" + tipoPaquete + ", cif=" + cif + ", razonSocial=" + razonSocial + ", tel=" + tel + ", movil=" + movil + ", fax=" + fax + ", dir=" + dir + ", cp=" + cp + ", provincia=" + provincia + ", localidad=" + localidad + ", clave=" + clave + ", perNombre=" + perNombre + ", perApellido1=" + perApellido1 + ", perApellido2=" + perApellido2 + ", perCargo=" + perCargo + ", perTel=" + perTel + ", perMail=" + perMail + ", formaPago=" + formaPago + ", aceptaPolitica=" + aceptaPolitica + '}';
    }
    // </editor-fold>    
}
