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

package com.openlopd.business.seguridad;

import com.openlopd.entities.seguridad.ConstantesSeguridad;
import com.openlopd.entities.seguridad.GestoresEmpresas;
import com.openlopd.entities.seguridad.PermisosGrupos;
import com.openlopd.entities.seguridad.GruposUsuarios;
import com.openlopd.entities.seguridad.GruposSubEmpresa;
import com.openlopd.entities.seguridad.Shadow;
import com.openlopd.entities.seguridad.ContratosTipo;
import com.openlopd.entities.seguridad.TiposUsuario;
import com.openlopd.entities.seguridad.ContratosPermisos;
import com.openlopd.entities.seguridad.PassRecovery;
import com.openlopd.sessionbeans.seguridad.ConstantesSeguridadFacadeLocal;
import com.openlopd.sessionbeans.seguridad.PermisosGruposFacadeLocal;
import com.openlopd.sessionbeans.seguridad.GruposUsuariosFacadeLocal;
import com.openlopd.sessionbeans.seguridad.GestoresEmpresasFacadeLocal;
import com.openlopd.sessionbeans.seguridad.GruposSubEmpresaFacadeLocal;
import com.openlopd.sessionbeans.seguridad.ShadowFacadeLocal;
import com.openlopd.sessionbeans.seguridad.ContratosPermisosFacadeLocal;
import com.openlopd.sessionbeans.seguridad.PassRecoveryFacadeLocal;
import com.elgg.utils.Calendar.ManejadorFechas;
import com.openlopd.business.Empresas.EmpresaInfo;
import com.openlopd.business.FilterQuery;
import com.openlopd.business.facturacion.FacturacionLocal;
import com.openlopd.business.seguridad.utils.ICalendar;
import com.openlopd.business.seguridad.utils.crypt.TextCrypt;
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.empresas.EmpresaSede;
import com.openlopd.entities.empresas.Persona;
import com.openlopd.entities.facturacion.ContadorFactura;
import com.openlopd.entities.facturacion.Factura;
import com.openlopd.entities.facturacion.Producto;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.entities.seguridad.utils.primarykey.GenKey;
import com.openlopd.sessionbeans.empresas.EmpresaFacadeLocal;
import com.openlopd.sessionbeans.empresas.EmpresaSedeFacadeLocal;
import com.openlopd.sessionbeans.empresas.PersonaFacadeLocal;
import com.openlopd.sessionbeans.facturacion.ContadorFacturaFacadeLocal;
import com.openlopd.sessionbeans.interfaz.LinkListFacadeLocal;
import com.openlopd.sessionbeans.interfaz.OperacionLopdFacadeLocal;
import com.jkingii.datatables.AbstractCDataTable;
import com.jkingii.mail.entities.MailAccountPK;
import com.jkingii.mail.sessionbeans.OutBoxFacadeLocal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Esta clase implementa los métodos necesarios para la gestión de la seguridad
 * del sistema, como crear usuarios, grupos, etc.
 *
 * @author Eduardo L. García Glez. Fecha 01 de feb de 2011
 * @version 1.0.1 17 de mar de 2011 Modificaciones: 17 de mar de 2011, se añade
 * el método isSystemUser.
 */
@Stateless
public class Seguridad implements SeguridadLocal {
    @EJB
    private ContadorFacturaFacadeLocal contadorFacturaFacade;
    //<editor-fold defaultstate="collapsed" desc="EJB">
    @EJB
    private LinkListFacadeLocal linkListFacade;
    @EJB
    private OperacionLopdFacadeLocal operacionLopdFacade;
    @EJB
    private GestoresEmpresasFacadeLocal gestoresEmpresasFacade;
    @EJB
    private FacturacionLocal facturacion;
    @EJB
    private PassRecoveryFacadeLocal passRecoveryFacade;
    @EJB
    private OutBoxFacadeLocal outBoxFacade;
    @EJB
    private ContratosLocal contratos;
    @EJB
    private GruposSubEmpresaFacadeLocal gruposSubEmpresaFacade;
    @EJB
    private PersonaFacadeLocal personaFacade;
    @EJB
    private EmpresaSedeFacadeLocal empresaSedeFacade;
    @EJB
    private EmpresaFacadeLocal empresaFacade;
    @EJB
    private ConstantesSeguridadFacadeLocal constantesSeguridadFacade;
    @EJB
    private ContratosPermisosFacadeLocal contratosPermisosFacade;
    @EJB
    private PermisosGruposFacadeLocal permisosGruposFacade;
    @EJB
    private GruposUsuariosFacadeLocal gruposUsuariosFacade;
    @EJB
    private ShadowFacadeLocal shadowFacade;
    @EJB
    private GruposLocal grupos;
    //</editor-fold>
     
    @PersistenceContext(unitName = "GestionLopd-ejbPU")
    private EntityManager em;  
    
    private static Logger logger = LoggerFactory.getLogger(Seguridad.class);
    private static ResourceBundle rb = ResourceBundle.getBundle("com.openlopd.config.config", Locale.getDefault());
    
    public static final int INITIAL_RANK = 0;
    public static final int RECOVERY_EXPIRATE_MINS = 30;
    
    // TODO: Se puede poner un enumerado con los tipos de error en el login.
    //auque luego siempre se muestre que no se puede acceder se puede dar un código.

    /**
     * Permite identificarse en el sistema como otra empresa.
     * @param accessInfo Información de acceso del usuario.
     * @param idEmpresa Identificador de la empresa a usar.
     * @return Información de acceso modificado.
     */
    @Override
    public AccessInfo cambiarEmpresa(AccessInfo accessInfo, String idEmpresa) {
        accessInfo.setSubEmpresa(empresaFacade.find(idEmpresa));
        accessInfo.setPermisosSubEmpresa(contratosPermisosFacade.
                getContratoEmpresa(idEmpresa));
        return accessInfo;
    }
    
    /**
     * Obtiene los premisos asignados a una dirección.
     * @param link 
     * @exception UnknownColumnException, si no se encuentra el permiso del link.
     */
    @Override
    public ColumnasPermisos getPermisoLink(String link) 
            throws UnknownColumnException {
        try {
            return linkListFacade.findByLink(link).getPermiso();
        } catch (Exception e) {
            logger.error("No es posible encontrar el permiso para [{}]", link);
            throw new UnknownColumnException("Permiso no encontrado");
        }
    }
    
    /**
     * // no se traen los permisos del contrato ya que puede que no son
     * necesarios //hasta que se elige una empresa.
     *
     * @param usuario identificador único del usuario.
     * @param clave clave del usuario
     * @param timeZoneId Zona Horaria del usuario
     * @return
     */
    @Override
    public AccessInfo login(String usuario, String clave, String timeZoneId) {
        Shadow s;
        List<GruposUsuarios> lg;
        PermisosGrupos p = new PermisosGrupos();
        List<PermisosGrupos> pAux;
        ContratosPermisos pEmpresa;
        List<ConstantesSeguridad> constantes;
        Empresa empresa;
        usuario = usuario.toLowerCase();
        try {
            s = shadowFacade.getUser(usuario, clave);
            // El usuario no existe en el sistema.
            if (s == null) {
                return null;
            }
            
            // El usuario no tiene asignado grupos y por tanto no tiene acceso.
            lg = gruposUsuariosFacade.getGruposByIdUsuario(usuario);
            if (lg == null) {
                return null;
            }
            pAux = permisosGruposFacade.getGruposByIdUsuario(usuario);

            p.importaListaPermisos(pAux);
            pEmpresa = contratosPermisosFacade.getContratoEmpresa( 
                    s.getIdEmpresa());
            empresa = empresaFacade.find(s.getIdEmpresa());
            constantes = constantesSeguridadFacade.findAll();
            return new AccessInfo(s, p, empresa,
                    pEmpresa, pEmpresa, pAux, constantes, timeZoneId);
        } catch (Exception e) {
            logger.error("Se ha producido un error en el inicio de sesión del "
                    + "usuario: {}", usuario);
            logger.info("La clave utilizada para [{}] es [{}]",
                    usuario, clave);
            return null;
        }
    }

    /**
     * Verifica si el usuario recibido existe en el sistema.
     *
     * Implementa un log de tipo debug
     *
     * @param idUsuario Identificador de usuario a comprobar en el sistema.
     * @return
     * <code>true</code> si el usuario existe en el sistema
     * <code>false</false> en caso contrario.
     */
    @Override
    public boolean isSystemUser(String idUsuario) {
        try {

            if (shadowFacade.find(idUsuario.toLowerCase()) != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.debug("isSystemUser, Error al recibir el id usuario [{}]",idUsuario);
            return false;
        }
    }
    
    /**
     * Actualiza la fecha de acceso del usuario.
     * @param accessInfo información de acceso del usuario.
     */
    @Override
    public void updateUserLastAccess(AccessInfo accessInfo) {
        Shadow s = accessInfo.getUserInfo();
        s.setUltimoAcceso(new Date().getTime());
        shadowFacade.edit(s);
    }
    
    @Override
    public void activeUser(AccessInfo accessInfo, String idUsuario, String grupos) {
        idUsuario = idUsuario.toLowerCase();
        Timestamp fechaFin = new Timestamp(ICalendar.addYear(accessInfo.getPermisosSubEmpresa()
                .getFechaInicio()));
        Shadow user = new Shadow(idUsuario,
                TextCrypt.Crypt(GenKey.newKey(), TextCrypt.DEFAULT_ALGORITM),
                TextCrypt.DEFAULT_ALGORITM.name(),
                accessInfo.getSubEmpresa().getCif(),
                accessInfo.getSubEmpresa().getIdEmpresa(),
                accessInfo.getSubEmpresa().getRazonSocial(), fechaFin, false,
                new TiposUsuario(new Integer(4) /* 4 corresponde con Usuario */));
        // TODO: user.setGestor();
        if (!isSystemUser(idUsuario)) {
            shadowFacade.create(user);
        } else {
            shadowFacade.edit(user);
        }
        
        gruposUsuariosFacade.updateUserGroups(accessInfo, idUsuario, 
                grupos.split(";"));        
    }    
    
    @Override
    public void desactiveUser(AccessInfo accessInfo, String idUsuario) {
        
        if (idUsuario == null || idUsuario.isEmpty()) {
            return;
        }
        Shadow s = shadowFacade.find(idUsuario.toLowerCase());
        if (s != null) {
            s.setFechaFin(new Timestamp(new Date().getTime()));
            shadowFacade.edit(s);
        }
    }
    
    /**
     * Crea el listado de productos para la factura del nuevo contrato.
     * @param tipoContrato Contrato solicitado por el cliente.
     * @param factura Factura que se está realizando.
     * @return Listado de productos.
     */
    private List<Producto> getListaProductos(ContratosTipo tipoContrato, Factura factura){
        List<Producto> lp = new ArrayList();
        lp.add(new Producto(GenKey.newKey(), factura, tipoContrato.getDescripcion(),
                1L, tipoContrato.getImporte(), tipoContrato.getImporte()));
        return lp;        
    }
    
    /**
     * Añade a un gestor de empresas al sistema.
     * @return 
     */
    @Override
    public String nuevoGestor(AccessInfo accessInfo, Empresa empresa, 
            EmpresaSede empresaSede, Persona contacto) {
//        ShortAccessInfo userInfo = null;
        int tipoPaquete = 4;
//        Factura factura = null;
        String r = "sin datos";
        
//        return nuevoContrato(userInfo, empresa, empresaSede, contacto, factura,
//                tipoPaquete, rb.getString("userID"));  
        
        empresa.setCif(empresa.getCif().toUpperCase());
        
        GestoresEmpresas g = gestoresEmpresasFacade.findByCifGestor(accessInfo, empresa.getCif());
        if (this.isSystemUser(empresa.getCif().toLowerCase())) {
            if (g != null && g.getBorrado() != null) {
                return "Error: La empesa existió y fue eliminada, póngase en contacto con el "
                        + "administrador de la aplicación para recuperarla.";
            } else {
                return "Error: La empresa ya existe";
            }
        }
        
        contacto.setPerContacto(true);
        contacto.setId(GenKey.newKey());
        empresa.setPerContacto(contacto.getId());
        empresa.setIdEmpresa(GenKey.newKey());
        contacto.setEmpresa(empresa);
        empresaSede.setId(GenKey.newKey());
        empresaSede.setEmpresa(empresa);
        empresaSede.setPerContacto(contacto.getId());
        
        try {
            ContratosTipo tipoContrato = contratos.getContratoTipo((short) tipoPaquete);
            ContratosPermisos cp = new ContratosPermisos(tipoContrato);
            cp.setIdContrato(GenKey.newKey());
            contratosPermisosFacade.create(cp); 
            empresaFacade.create(empresa);
            personaFacade.create(contacto);
            empresaSedeFacade.create(empresaSede);                     
            
            Shadow s = new Shadow(empresa.getCif().toLowerCase(), 
                    TextCrypt.Crypt("pass" + empresa.getCif(), TextCrypt.DEFAULT_ALGORITM.name()), 
                    TextCrypt.DEFAULT_ALGORITM.name(), 
                    empresa.getCif(), empresa.getIdEmpresa(), empresa.getRazonSocial(),
                    new Timestamp(ICalendar.addYear()), true, new TiposUsuario(1));
            shadowFacade.create(s);
            
            r = grupos.addAdminGroup(empresa.getIdEmpresa(), s.getUsuario(), (short) tipoPaquete);
            GruposSubEmpresa gse = new GruposSubEmpresa(r, empresa.getIdEmpresa(),
                    cp.getIdContrato(), INITIAL_RANK);
            gruposSubEmpresaFacade.create(gse);
           
            GestoresEmpresas ge = new GestoresEmpresas(empresa.getCif(),
                    empresa.getIdEmpresa(), cp.getIdContrato(), INITIAL_RANK);
            gestoresEmpresasFacade.create(ge);
            operacionLopdFacade.getActualizarListOperaciones(accessInfo, empresa);
            
            // Extra para Gestor.
            ContadorFactura cf = new ContadorFactura(empresa);
            contadorFacturaFacade.create(cf);
        } catch (Exception e) {
            return "problemas en el commit, haciendo rollback: " + e.toString();
        }
        return r;        
    }
    
    /**
     * Permite crear el acceso de un nuevo cliente al sistema, desde la web
     * pública.
     *
     * @param userInfo Información de acceso del usuario administrador al
     * sistema.
     * @param empresa Información sobre la empresa.
     * @param empresaSede Datos de localización de la empresa.
     * @param contacto Datos de la persona de contacto.
     * @param factura Datos sobre la facturación elegida.
     * @return Código del error producido al realizarse el alta.
     */
    @Override
    public String nuevoContrato(ShortAccessInfo userInfo,
            Empresa empresa,
            EmpresaSede empresaSede,
            Persona contacto,
            Factura factura,
            int tipoPaquete) {
        
        return nuevoContrato(userInfo, empresa, empresaSede, contacto, factura,
                tipoPaquete, rb.getString("userID").toUpperCase());
    }
    
    /**
     * Permite crear el acceso de un nuevo cliente asignado a un gestor.
     * @param empresa Información sobre la empresa.
     * @param empresaSede Datos de localización de la empresa.
     * @param contacto Datos de la persona de contacto.
     * @return Código del error producido al realizarse el alta.
     */
    @Override
    public String nuevoContrato(AccessInfo accessInfo, Empresa empresa, 
            EmpresaSede empresaSede, Persona contacto) {
//        ShortAccessInfo userInfo = null;
        int tipoPaquete = 3;
//        Factura factura = null;
        String r = "sin datos";
        
//        return nuevoContrato(userInfo, empresa, empresaSede, contacto, factura,
//                tipoPaquete, rb.getString("userID")); 
        
        empresa.setCif(empresa.getCif().toUpperCase());
        
        if (this.isSystemUser(empresa.getCif().toLowerCase())) {
            return "Error: La empresa ya existe";
        }        
        
        contacto.setPerContacto(true);
        contacto.setId(GenKey.newKey());
        empresa.setPerContacto(contacto.getId());
        empresa.setIdEmpresa(GenKey.newKey());
        contacto.setEmpresa(empresa);
        empresaSede.setId(GenKey.newKey());
        empresaSede.setEmpresa(empresa);
        empresaSede.setPerContacto(contacto.getId());
        
        try {
            ContratosTipo tipoContrato = contratos.getContratoTipo((short) tipoPaquete);
            ContratosPermisos cp = new ContratosPermisos(tipoContrato);
            cp.setIdContrato(GenKey.newKey());
            contratosPermisosFacade.create(cp); 
            empresaFacade.create(empresa);
            personaFacade.create(contacto);
            empresaSedeFacade.create(empresaSede);
            
            GruposSubEmpresa gse = new GruposSubEmpresa(r, empresa.getIdEmpresa(),
                    cp.getIdContrato(), INITIAL_RANK);
            gruposSubEmpresaFacade.create(gse);
           
            GestoresEmpresas ge = new GestoresEmpresas(accessInfo.getEmpresa().getCif(),
                    empresa.getIdEmpresa(), cp.getIdContrato(), INITIAL_RANK);
            gestoresEmpresasFacade.create(ge);
            operacionLopdFacade.getActualizarListOperaciones(accessInfo, empresa);
        } catch (Exception e) {
            return "problemas en el commit, haciendo rollback: " + e.toString();
        }
        return r;
    }
    
    /**
     * Permite crear el acceso de un nuevo cliente al sistema.
     *
     * @param userInfo Información de acceso del usuario administrador al
     * sistema.
     * @param empresa Información sobre la empresa.
     * @param contacto Datos de la persona de contacto.
     * @param factura Datos sobre la facturación elegida.
     * @return Código del error producido al realizarse el alta.
     */
    // TODO: verificar información devuelta, puede ser mejor que se devuelva
    // un mensaje indicado el resultado del procedimiento.
    
    @Override
    public String nuevoContrato(ShortAccessInfo userInfo,
            Empresa empresa, EmpresaSede empresaSede,
            Persona contacto, Factura factura,
            int tipoPaquete, String empresaGeneradora) {
        String r = "sin datos";
        userInfo.setUsuario(userInfo.getUsuario().toLowerCase());
        empresa.setCif(empresa.getCif().toUpperCase());
        if (this.isSystemUser(userInfo.getUsuario())) {
            logger.warn("E0003: [{}] ya existe en base de datos.", userInfo.getUsuario());
            return "E0003";
        }
        //TODO: Cobrar
        if (false /*
                 * Si no se puede cobrar, controlar errores en el nuevo método
                 */) {
            return "E0004";
        }
        Shadow s = new Shadow(userInfo.getUsuario(),
                TextCrypt.Crypt(userInfo.getClave(), TextCrypt.DEFAULT_ALGORITM),
                TextCrypt.DEFAULT_ALGORITM.name(),
                empresa.getCif(), GenKey.newKey(), empresa.getRazonSocial(),
                new Timestamp(ICalendar.addYear()),
                false, new TiposUsuario(new Integer(1)));
        System.out.println(TextCrypt.DEFAULT_ALGORITM.name());
        contacto.setId(GenKey.newKey());
        contacto.setPerContacto(true);
        contacto.setfInicio(new Timestamp(new Date().getTime()));
        empresa.setIdEmpresa(s.getIdEmpresa());
        contacto.setEmpresa(empresa);
        if (empresa.getNombre() == null) //TODO: Poner esto en su sitio.
        {
            empresa.setNombre(empresa.getRazonSocial());
        }
        if (empresa.getMailContacto() == null) {
            empresa.setMailContacto(contacto.getEmail());
        }
        empresa.setPerContacto(contacto.getId());
        empresaSede.setId(GenKey.newKey());
        empresaSede.setEmpresa(empresa);
        empresaSede.setPerContacto(contacto.getId());

        try {
            ContratosTipo tipoContrato = contratos.getContratoTipo((short) tipoPaquete);
            ContratosPermisos cp = new ContratosPermisos(tipoContrato);
            cp.setIdContrato(GenKey.newKey());
            contratosPermisosFacade.create(cp);
            shadowFacade.create(s);
            r = grupos.addAdminGroup(empresa.getIdEmpresa(), userInfo.getUsuario(), (short) tipoPaquete); //TODO: En la factura no debe ir el típo de contrato????
            empresaFacade.create(empresa);
            personaFacade.create(contacto);
            empresaSedeFacade.create(empresaSede);
            
            GruposSubEmpresa gse = new GruposSubEmpresa(r, empresa.getIdEmpresa(),
                    cp.getIdContrato(), INITIAL_RANK);
            gruposSubEmpresaFacade.create(gse);
            String[] params = {empresa.getRazonSocial(), ManejadorFechas.getFechaActual()};
            outBoxFacade.addMessage(new MailAccountPK("GestionLOPD", "info"), "AltaEmpresa", params, empresa.getMailContacto());

            // TODO: Hacer esto compatible con todas las empresas
            GestoresEmpresas ge = new GestoresEmpresas(empresaGeneradora,
                    empresa.getIdEmpresa(), cp.getIdContrato(), INITIAL_RANK);
            gestoresEmpresasFacade.create(ge);
            //TODO: Añadir los datos de la factura (entity facturas y generarla o al revés.)
            factura.setEmpresa(empresa);
            factura.setProductos(getListaProductos(tipoContrato, factura));
            facturacion.nuevaFactura(factura);
            //TODO: Añadir los datos en la tabla personas.
            //TODO: Este mensagge tiene que ser parametrizado.
        } catch (Exception e) {
            logger.error("Imposible dar de alta el nuevo contrato, userInfo[{}]", userInfo.toString());
            logger.error("Imposible dar de alta el nuevo contrato, empresa[{}]", empresa.toString());
            logger.error("Imposible dar de alta el nuevo contrato, empresaSede[{}]", empresaSede.toString());
            logger.error("Imposible dar de alta el nuevo contrato, contacto[{}]", contacto.toString());
            logger.error("Imposible dar de alta el nuevo contrato, factura[{}]", factura.toString());
            logger.error("Imposible dar de alta el nuevo contrato, tipPaquete[{}]", new Integer(tipoPaquete).toString());                    
            logger.error("Exception: {}", e.getMessage());
            return "problemas en el commit, haciendo rollback: " + e.toString();
        }
        operacionLopdFacade.getActualizarListOperaciones(
                login(userInfo.getUsuario(), userInfo.getClave(), 
                TimeZone.getDefault().getID()), empresa);
        return r;
    }
    
        /**
     * Da de baja una subempresa.
     * @param accessInfo imformación de acceso del usuario.
     * @param idEmpresa identificador único de la empresa a dar de baja.
     * @return Información sobre el resultado de la operación.
     */
    @Override
    public String bajaContrato(AccessInfo accessInfo, String idEmpresa) {
        Empresa e = empresaFacade.find(idEmpresa);
        return this.bajaContrato(accessInfo, e);
    }
    
    /**
     * Da de baja una subempresa.
     * @param accessInfo imformación de acceso del usuario.
     * @param empresa Empresa a dar de baja.
     * @return Información sobre el resultado de la operación.
     */
    @Override
    public String bajaContrato(AccessInfo accessInfo, Empresa empresa){
        try {
            GestoresEmpresas ge = gestoresEmpresasFacade.findEmpresaGestinada(accessInfo, empresa);
            ge.setBorrado(new Date().getTime());
            ge.setBorradoPor(accessInfo.getUserInfo().getUsuario());
            //TODO: usar accessInfo aquí.
            gestoresEmpresasFacade.edit(ge);
            
            //TODO: Borrar en gruposSubEmpresa.
//            GruposSubEmpresa gse = gruposSubEmpresaFacade.find(ge.getIdContrato());
//            gruposSubEmpresaFacade.remove(gse);
        } catch (Exception e) {
            return "Error al realizar la baja.";
        }
        return "";
    }
    
    private String getShortingColSubEmpresa(int iShortCol, String iSortingCol) {
        String order;
        if (iShortCol == 0)
            order = " e.cif ";
        else if (iShortCol == 1)
            order = " e.razonsocial ";
        else if (iShortCol == 2)
            order = " ap.descripcion ";
        else if (iShortCol == 3)
            order = " p.nombre ";
        else if (iShortCol == 4)
            order = " e.mailcontacto ";
        else if (iShortCol == 1)
            order = " es.telefono ";
        else if (iShortCol == 5)
            order = " es.movil ";
        else if (iShortCol == 6)
            order = " es.fax ";
        else 
            order = " rank ";
        return order + iSortingCol + ",";
    }
    
    private String getOrderColSubEmpresa(AbstractCDataTable dt) {
        String order = "";
        for(int i = 0; i < dt.getiSortingCols(); i++ ) {
            order += getShortingColSubEmpresa(dt.getiSortCol_C(i), dt.getsSortDir_C(i));
        }
        return order.substring(0, order.length() - 1);
    }
    
    private String getComandString(AccessInfo accessInfo, String cmd) {
        switch (cmd) {
            case "gestores":
                return " and e.cif in (select distinct(cifgestor) from gestores_empresas) ";
            default:
                return (accessInfo.getEmpresa().getIdEmpresa().equals(rb.getString("empresaID"))
                        ? // Si es empresa del sistema se tran además de las subEmpresas a los gestores
                        "  and (ge.cifgestor = s.cif "
                        + "  or e.cif in (select distinct(cifgestor) from gestores_empresas) )"
                        : // Si no es gestor solo se tran las subEmpresas del gestor.
                        "  and ge.cifgestor = s.cif ");
        }
    }
    
    @Override
    public List<EmpresaInfo> getSubEmpresas(AccessInfo accessInfo, AbstractCDataTable dt){
//            int inicio, int totalReg, String textSearch, 
//            List<String> iSortCol_C){
        String idUsuario = accessInfo.getUserInfo().getUsuario();
        FilterQuery filter = new FilterQuery((String) dt.getSSearch()); 
        
        try {
            List<EmpresaInfo> l = new ArrayList();
            Query todasEmpresasQuery = em.createNativeQuery(""
                    + "Select count(*) "
                    + "from grupos_usuarios gu, permisos_grupos pg "
                    + "where gu.idusuario = ?idUsuario "
                    + "  and pg.id = gu.idgrupo " 
                    + "  and todasempresas = true ");
            todasEmpresasQuery.setParameter("idUsuario", idUsuario);
            Long todasEmpresasCount = (Long) todasEmpresasQuery.getSingleResult();
            
            Query q = null;
            
            if (todasEmpresasCount < 1) {
             // No se tiene acceso a todas las empresas, se consulta por
             // privilegios de grupo.
             q = em.createNativeQuery(""
                        + "select e.idEmpresa, e.cif, e.razonsocial, e.actividad, "
                        + "p.nombre || ' ' || p.apellido1 || coalesce(' ' || p.apellido2, '') as percontacto, "
                        + "e.mailcontacto, "
                        + "es.telefono, es.movil, es.fax, rank "
                        + "from public.empresas e, public.empresas_sedes es, "
                        + "     public.grupos_sub_empresa gse, public.personas p "
                        + "where e.idempresa = gse.idempresa "
                        + "  and e.percontacto = p.id "
                        + "  and es.idempresa = es.empresa_idempresa and es.gestionacontratlopd = true "
                        + "  and gse.idgrupo in ( "
                        + "    select gse2.idgrupo "
                        + "    from public.grupos_sub_empresa gse2, public.grupos_usuarios gu "
                        + "    where gse2.idgrupo = gu.idgrupo "
                        + "      and idusuario = ?idUsuario  "
                        + "      and "
                        // Aquí se realiza la búsqueda por texto.
                        + "      (e.cif like ?textSearch "
                        + "       OR e.razonsocial like ?textSearch "
                        + "       OR (p.nombre || ' ' || p.apellido1 || coalesce(' ' || p.apellido2, '')) like ?textSearch "
                        + "       OR e.actividad like ?textSearch "
                        + "       OR e.mailcontacto like ?textSearch "
                        + "       OR es.telefono like ?textSearch "
                        + "       OR es.movil like ?textSearch "
                        + "       OR es.fax like ?textSearch )"
                        + "  )"
                        + "ORDER BY " + getOrderColSubEmpresa(dt));
                logger.info("En getSubEmpresas, buscando [{}] por el usuario [{}]",
                        dt.getSSearch(), idUsuario);
                q.setParameter("idUsuario", idUsuario);
                q.setParameter("textSearch", (filter.getFilter() != null ? "%" + filter.getFilter() + "%" : "%"));
            } else {
                // Se tiene acceso a todas las empresas, se devuelve el conjunto
                // de empresas gestionadas (empresas hijas.)
                q = em.createNativeQuery(""
                        + "select e.idEmpresa, e.cif, e.razonsocial, ap.descripcion, "
                        + "p.nombre || ' ' || p.apellido1 || coalesce(' ' || p.apellido2, '') as percontacto, "
                        + "e.mailcontacto, "
                        + "es.telefono, es.movil, es.fax, ge.rank,  "
                        + "case when e.cif in (select distinct(cifgestor) from gestores_empresas)"
                        + "then true else false end as gestor "
                        + "from public.empresas e LEFT JOIN lopd.actividad_principal ap ON e.actividad = ap.codigo, "
                        + "     public.empresas_sedes es, "
                        + "     public.personas p, public.gestores_empresas ge, "
                        + "     public.shadow s "
                        + "where es.percontacto = p.id "
                        + "  and e.idempresa = es.empresa_idempresa and es.gestionacontratlopd = true "
                        + "  and ge.idempresa = e.idempresa and s.usuario = ?idUsuario "
                        
                        + this.getComandString(accessInfo, filter.getCommand())
                        
                        + "  and ge.borrado is null "
                        + "    "
                        + "    "
                        + "      and "
                        // Aquí se realiza la búsqueda por texto.
                        + "      (lower(e.cif) like ?textSearch "
                        + "       OR lower(e.razonsocial) like ?textSearch "
                        + "       OR (lower(p.nombre) || ' ' || lower(p.apellido1) || coalesce(' ' || lower(p.apellido2), '')) like ?textSearch "
                        + "       OR lower(ap.descripcion) like ?textSearch "
                        + "       OR lower(e.mailcontacto) like ?textSearch "
                        + "       OR es.telefono like ?textSearch "
                        + "       OR es.movil like ?textSearch "
                        + "       OR es.fax like ?textSearch )"
                        + "  "
                        + "ORDER BY " + getOrderColSubEmpresa(dt));
                logger.info("En getSubEmpresas, buscando [{}] por el usuario [{}]",
                        dt.getSSearch(), idUsuario);
                q.setParameter("idUsuario", idUsuario);
                q.setParameter("textSearch", (filter.getFilter() != null ? "%" + 
                        filter.getFilter().toLowerCase() + "%" : "%"));
            }
            
            
            EmpresaInfo e;
            Object [] oa;
            List<Object []> lo = q.getResultList();
            for (int i =  0; /*i < dt.getIDisplayLength() && */i < lo.size(); i++){
                oa = lo.get(i);
                e = new EmpresaInfo();
                e.setIdEmpresa((String) oa[0]);
                e.setCif((String) oa[1]);
                e.setRazonSocial((String) oa[2]);
                e.setActividad((String) oa[3]);              
                e.setPerContacto((String) oa[4]);
                e.setMailContacto((String) oa[5]);
                e.setTelefono1((String) oa[6]);
                e.setMovil((String) oa[7]);
                e.setFax((String) oa[8]);
                e.setRank((Integer) oa[9]);
                e.setGestor((Boolean) oa[10]);
                l.add(e);
            }
            return l;              
        } catch (Exception e) {
            logger.error("Error obteniendo la información de subempresas"
                    + "para el usuario [{}] Exception: {}", idUsuario, e.getMessage());
            return null;
        }
    }

    /**
     * Inicia el procedimiento de recuperación de contraseña.
     *
     * @param userId identificador del usuario.
     * @param email
     * @return id de recuperación, si el usuario existe en el sistema,
     * <code>null</code> si el usuario no existe. //TODO: ¿para usuario
     * expirados?
     */
    @Override
    public Boolean initPassRecoveryProcess(String userId/*, String email*/) {
        String email = "";
        userId = userId.toLowerCase();
        try {
            Shadow s = shadowFacade.find(userId);
            email = userId.contains("@") ? userId : 
                    empresaFacade.find(s.getIdEmpresa()).getMailContacto();
//            Object userInfo;
//            if (s.getTipo().getId() > 3) {
//                userInfo = personaFacade.recoveryTest(userId, email);
//            } else {
//                userInfo = empresaFacade.recoveryTest(userId, email);
//            }

            if (s != null) {
                PassRecovery pr = new PassRecovery(GenKey.newKey(), userId,
                        ICalendar.addMin(RECOVERY_EXPIRATE_MINS));
                passRecoveryFacade.create(pr);
                String[] params = {userId, pr.getId()};
                outBoxFacade.addMessage(new MailAccountPK("GestionLOPD", "info"),
                        "PassRecovery", params, email);
                return true;
            }
        } catch (Exception e) {
            Object [] params = {userId, email, e.getMessage()};
            logger.error("Error iniciando la recuperación de contraseña "
                                      + "de usuario [{0}], email [{1}]."
                                      + " Exception: {2}", params);
        }
        return false;
    }
    
    /**
     * Permite cambiar la contraseña del usuario.
     * @param password nuevo password.
     * @param key clave de recuperación
     * @return <code>true</code> si 
     */
    @Override
    public Boolean changePassword(String password, String key) {
        try {
            PassRecovery p = passRecoveryFacade.find(key);
            if (p != null && new Date().getTime() <= p.getExpDate()) {
                Shadow s = shadowFacade.find(p.getIdUsuario());
                s.setClave(TextCrypt.Crypt(password, TextCrypt.DEFAULT_ALGORITM));
                s.setTipoCifrado(TextCrypt.DEFAULT_ALGORITM.name());
                shadowFacade.edit(s);
                return true;
            }
        } catch (Exception e) {
            Object[] params = {password, key, e.getMessage()};
            logger.error("Error cambiando el pass[{}] con el key[{}]", params);
        }
        return false;
    }
    
    @Override
    public Empresa getEmpresaMadre(Empresa empresa) {
        Empresa madre;
        return empresaFacade.findEmpresaMadre(empresa);
    }
    
    /**
     * Obtiene el listado de permisos que puede gestionar una empresa que
     * gestiona una subEmpresa.
     *
     * @param accessInfo Información de acceso del usuario.
     * @return Listado de permisos.
     */
    @Override
    public List<ColumnasPermisos> getColumnasPermisos(AccessInfo accessInfo) {
        List<ColumnasPermisos> permisos = new ArrayList<ColumnasPermisos>();
        
        try {
            for (ColumnasPermisos c : ColumnasPermisos.values()) {
                if (accessInfo.getPermisosEmpresa().hasAccess(c)) {
                    permisos.add(c);
                }
            }
        } catch (UnknownColumnException ex) {
            logger.error("Imposible!!, Columna no encontrada.");
        }
        return permisos;
    }
}
