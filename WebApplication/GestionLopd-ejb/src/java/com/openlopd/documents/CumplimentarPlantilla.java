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

package com.openlopd.documents;

import com.elgg.utils.Calendar.ManejadorFechas;
import com.elgg.utils.converter.Converter;
import com.elgg.utils.converter.ConverterJod;
import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.entities.documentos.FileDataBase;
import com.openlopd.entities.documentos.Plantilla;
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.empresas.EmpresaSede;
import com.openlopd.entities.empresas.Persona;
import com.openlopd.entities.facturacion.Factura;
import com.openlopd.entities.facturacion.Producto;
import com.openlopd.entities.lopd.Fichero;
import com.openlopd.entities.lopd.ProcedimientoHabilitado;
import com.openlopd.entities.lopd.TipoProcedimiento;
import com.openlopd.entities.seguridad.Shadow;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.utils.primarykey.GenKey;
import com.openlopd.sessionbeans.documentos.DataBaseToFile;
import com.openlopd.sessionbeans.documentos.FileDataBaseFacadeLocal;
import com.openlopd.sessionbeans.documentos.FileToDataBase;
import com.openlopd.sessionbeans.documentos.PlantillaFacadeLocal;
import com.openlopd.sessionbeans.empresas.EmpresaFacadeLocal;
import com.openlopd.sessionbeans.empresas.EmpresaSedeFacadeLocal;
import com.openlopd.sessionbeans.empresas.PersonaFacadeLocal;
import com.openlopd.sessionbeans.interfaz.OperacionLopdFacadeLocal;
import com.openlopd.sessionbeans.lopd.FicheroFacadeLocal;
import com.openlopd.sessionbeans.lopd.ProcedimientoDisponibleFacadeLocal;
import com.openlopd.sessionbeans.lopd.ProcedimientoHabilitadoFacadeLocal;
import com.openlopd.sessionbeans.lopd.TipoProcedimientoFacadeLocal;
import com.jkingii.mail.entities.MailAccountPK;
import com.jkingii.mail.sessionbeans.OutBoxFacadeLocal;
import com.openlopd.common.localizacion.business.LocalizacionLocal;
import com.openlopd.common.localizacion.entities.Localidad;
import com.openlopd.common.localizacion.entities.Provincia;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jdom.JDOMException;
import org.jopendocument.dom.template.JavaScriptFileTemplate;
import org.jopendocument.dom.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Eduardo L. García Glez.
 *
 */
//TODO: Obtener empresa madre al generar la plantilla de una empresa que no la tiene.
public class CumplimentarPlantilla implements Serializable {
    OutBoxFacadeLocal outBoxFacade = lookupOutBoxFacadeLocal();
    OperacionLopdFacadeLocal operacionLopdFacade = lookupOperacionLopdFacadeLocal();  
    FicheroFacadeLocal ficheroFacade = lookupFicheroFacadeLocal();
    TipoProcedimientoFacadeLocal tipoProcedimientoFacade = lookupTipoProcedimientoFacadeLocal();
    ProcedimientoHabilitadoFacadeLocal procedimientoHabilitadoFacade = lookupProcedimientoHabilitadoFacadeLocal();
    ProcedimientoDisponibleFacadeLocal procedimientoDisponibleFacade = lookupProcedimientoDisponibleFacadeLocal();
    private PersonaFacadeLocal personaFacade = lookupPersonaFacadeLocal();
    private LocalizacionLocal localizacion = lookupLocalizacionLocal();
    private EmpresaSedeFacadeLocal empresaSedeFacade = lookupEmpresaSedeFacadeLocal();
    private EmpresaFacadeLocal empresaFacade = lookupEmpresaFacadeLocal();
    private PlantillaFacadeLocal plantillaFacade = lookupPlantillaFacadeLocal();
    private FileDataBaseFacadeLocal fileDataBaseFacade = lookupFileDataBaseFacadeLocal();
    private static Logger logger = LoggerFactory.getLogger(CumplimentarPlantilla.class);
    private static ResourceBundle rb = ResourceBundle.getBundle("com.openlopd.config.config", Locale.getDefault());
    private AccessInfo accessInfo;
    private Empresa empresaGeneradora;
    private Empresa empresaDestino;
    private Shadow userInfo;
    private Plantilla plantillaInfo;
    private GregorianCalendar calendar;
    private Factura factura;
    private ColumnasPermisos permiso;

    /**
     * Inicializa el objeto para crear un nuevo documento para una empresa que
     * está identificada en el sistema.
     *
     * @param accessInfo Información de acceso, null si el
     * @param empresa Empresa para la que generar la plantilla.
     */
    public CumplimentarPlantilla(AccessInfo accessInfo, Factura factura,
            ColumnasPermisos permiso) {
        this.accessInfo = accessInfo;
        this.empresaGeneradora = accessInfo.getEmpresa();
        this.empresaDestino = accessInfo.getSubEmpresa();
        this.userInfo = accessInfo.getUserInfo();
        this.factura = factura;
        this.permiso = permiso;
    }

    public CumplimentarPlantilla(Empresa empresaGeneradora, 
            Empresa empresaDestino, Factura factura, ColumnasPermisos permiso) {
        this.accessInfo = null;
        this.empresaGeneradora = empresaGeneradora != null ? empresaGeneradora : empresaFacade.find(rb.getString("empresaID"));
        this.empresaDestino = empresaDestino;
        this.userInfo = new Shadow(rb.getString("userID"));
        this.factura = factura;
    }
    
    

    /**
     * Genera el nuevo documento desde la plantilla especificada.
     *
     * La plantilla es generada y añadida a la base de datos.
     *
     * @param nombre nombre de la plantilla.
     * @return nuevo fichero generado.
     */
    public FileDataBase generar(String nombre) {
        return this.generar(nombre, null);
    }

    /**
     * Genera el nuevo documento desde la plantilla especificada.
     *
     * La plantilla es generada y añadida a la base de datos.
     *
     * @param nombre nombre de la plantilla.
     * @param formData dátos adicionales para rellenar la plantilla;
     * @return nuevo fichero generado.
     */
    public FileDataBase generar(String nombre, Map<String, String[]> formData) {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("Obteniendo la plantilla {} de la empresa {} base de datos.",
                        nombre, empresaGeneradora.getIdEmpresa());
            }
            plantillaInfo = plantillaFacade.getActiveByName(empresaGeneradora, nombre);
            if (plantillaInfo == null) {
                logger.error("La plantilla [{}] solicitada por [{}] no existe.",
                        nombre, empresaGeneradora.getIdEmpresa());
            }
            this.calendar = new GregorianCalendar(Locale.getDefault());
            FileDataBase doc = cumplimentarPlantilla(plantillaInfo, formData);
            
            if (plantillaInfo.getCodOperacion() != null) {
                operacionLopdFacade.actualizarOperacion(accessInfo,
                        plantillaInfo.getCodOperacion(), Boolean.TRUE);
            }                       
            return doc;
        } catch (ConnectException e) {
            String[] params = {"Imposible conectar con el servidor de conversión"
                    + "a PDF.", ManejadorFechas.getFechaActual()};
            outBoxFacade.addMessage(new MailAccountPK("GestionLOPD", "info"), 
                    "SysError", params, rb.getString("sysAdminMail"));
            logger.error("Imposible conectar con el servidor de conversión a PDF.");
            return null;
        } catch (IOException e) {
            String[] params = {"Imposible acceder al directorio temporal.", 
                ManejadorFechas.getFechaActual()};
            outBoxFacade.addMessage(new MailAccountPK("GestionLOPD", "info"), 
                    "SysError", params, rb.getString("sysAdminMail"));
            logger.error("En generar(), error generando la plantilla "
                    + "para los parámetros, nombre[" + nombre + "] "
                    + "y empresa [" + empresaDestino + "] Exception: no se puede "
                    + "contactar con el sistema de ficheros.");
            return null;            
        } catch (TemplateException | JDOMException e) {
            logger.error("En generar(), error generando la plantilla "
                    + "para los parámetros, nombre[" + nombre + "] "
                    + "y empresa [" + empresaDestino + "] Exception: " + e.getMessage());
            return null;
        } 
    }

    /**
     * Realiza las operaciones necesarias para cumplimentar la plantilla, y
     * almacenar el documento en la base de datos.
     *
     * @param p plantilla a partir de la cual generar el nuevo documento.
     * @return FileDataBase con el nuevo dichero creado en la base de datos.
     * @throws IOException
     * @throws TemplateException
     * @throws JDOMException
     */
    private FileDataBase cumplimentarPlantilla(Plantilla p, 
            Map<String, String[]> formData) throws IOException, 
            TemplateException, JDOMException, ConnectException {
        if (logger.isDebugEnabled()) {
            logger.debug("Obteniendo plantilla de la base de datos.");
        }
        DataBaseToFile dbf = new DataBaseToFile(p.getDocumento());
        File templateFile = dbf.getFile();
        File outFile = new File(rb.getString("tempDir") + GenKey.newKey() + ".odt");
        if (logger.isDebugEnabled()) {
            logger.debug("Creando plantilla.");
        }
        JavaScriptFileTemplate template = new JavaScriptFileTemplate(templateFile);

        insertarCampos(template, formData);

        if (logger.isDebugEnabled()) {
            logger.debug("Guardando en fichero el documento generado.");
        }
        try {
            template.saveAs(outFile);
        } catch (IOException | TemplateException e) {
            logger.error("Error guardando el nuevo documento en el sistema de ficheros, "
                    + "Exception: {}", e.getMessage());
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Iniciando la conversión del documento.");
        }
        Converter c = new ConverterJod(new Integer(rb.getString("convertPort")));
        FileDataBase dbfOut = new FileToDataBase(c.convert(outFile, 
                new File(GenKey.newKey() + ".pdf")),
                empresaDestino, userInfo, permiso).commit();

        if (logger.isDebugEnabled()) {
            logger.debug("Enviando el documento a la base de datos.");
        }
        fileDataBaseFacade.create(dbfOut);
        if (outFile.delete()) {
            logger.debug("Cerrando el fichero.");
        }
        dbf.close();
        return dbfOut;
    }

    private void insertarCampos(JavaScriptFileTemplate template, 
            Map<String, String[]> formData) {
        //TODO: cargar los datos extra de la tabla de etiquetas personalizadas.
        if (logger.isDebugEnabled()) {
            logger.debug("Iniciando el envío del parámetros al documento.");
        }

        insertarCamposFormData(template, formData);
        insertarCamposSistema(template);
        insertarInformacionDePlantilla(template);

        insertarCamposEmpresa(template, empresaGeneradora, "Generadora");
        insertarCamposEmpresa(template, empresaDestino, "Destino");

        insertarCamposContacto(template, personaFacade.find(
                empresaGeneradora.getPerContacto()), "Generadora");
        insertarCamposContacto(template, personaFacade.find(
                empresaDestino.getPerContacto()), "Destino");
        
        if (factura == null) {
            insertarResponsableSeguridad(template);
        }        
        
        insertarProcedimientos(template);
        insertarFicheros(template);
        insertarListadoPersonalAuthCopiaReproduccion(template);

        insertarFactura(template);
    }
    
    private void insertarCamposFormData(JavaScriptFileTemplate template, 
            Map<String, String[]> formData) {
        if (formData != null) {
            for (Entry i : formData.entrySet()) {
                setField(template, (String) i.getKey(),
                        ((String[])i.getValue())[0].trim());
            }
        }
    }
    
    private void insertarResponsableSeguridad(JavaScriptFileTemplate template) {
        Persona respSeg = personaFacade.getResponsableSeguridad(accessInfo);
        setField(template, "lopdEmpDestinoResSeguridadNombre", respSeg.getNombreCompleto());
        setField(template, "lopdEmpDestinoResSeguridadDNI", respSeg.getDni());        
    }

    private static Map<String, String> createMap(String[] cols, String[] data) {
        final Map<String, String> res = new HashMap<>();

        if (cols.length != data.length) {
            logger.error("Número de columnas recibidas diferente al tamaño"
                    + "de los datos - cols[{}], data[{}]. Se devuelve nulo.", cols, data);
            return null;
        }

        for (int i = 0; i < cols.length; i++) {
            res.put(cols[i], data[i] != null ? data[i] : "");
        }

        return res;
    }

    private void insertarTabla(JavaScriptFileTemplate template,
            List<Map<String, String>> tabla, String tableName) {
        template.setField(tableName, tabla);
    }

    private void insertarFactura(JavaScriptFileTemplate template) {
        String[] cols = {"descripcion", "unidades", "precioUnidad", "cantidad"};
        String[] emptyData = {"", "", "", ""};
        String[] data;
        List<Map<String, String>> tabla = new ArrayList<>();

        if (factura == null) {
            setField(template, "facturaFechaEmision", "factura.fechaEmision");
            setField(template, "facturaNumero", "factura.numero");
            setField(template, "facturaSubtotal", "factura.subtotal");
            
            data = new String[]{"DescProd1", "2", "4", "8"};
            tabla.add(createMap(cols, data));
            data = new String[]{"DescProd2", "10", "5", "50"};
            tabla.add(createMap(cols, data));
        } else {
            setField(template, "facturaFechaEmision", factura.getFecha());
            setField(template, "facturaNumero", factura.getId());
            setField(template, "facturaSubtotal", factura.getImporte());

            for (Producto p : factura.getProductos()) {
                data = new String[]{
                    p.getDescripcion(),
                    p.getUnidades().toString(),
                    p.getPrecioUnidad().toString(),
                    p.getImporte().toString()
                };
                tabla.add(createMap(cols, data));
            }
        }
        
        if (tabla.isEmpty()) {
            tabla.add(createMap(cols, emptyData));
        }

        insertarTabla(template, tabla, "facturaProductos");
    }

    private void insertarProcedimientos(JavaScriptFileTemplate template) {
        List<TipoProcedimiento> listTipoProc = tipoProcedimientoFacade
                .getTipoProcedimientoActivo(empresaGeneradora);
        List<ProcedimientoHabilitado> listProcActivos;
        String[] cols = {"descripcion", "procedimiento", "nivel"};
        String[] emptyData = {"", "", ""};
        String[] data;
        List<Map<String, String>> tabla;

        // Para todos los tipos de procedimientos disponibles,
        // se añaden a la plantilla todos los procedimientos activos
        for (TipoProcedimiento tipoProc : listTipoProc) {
            tabla = new ArrayList<>();
            listProcActivos = procedimientoHabilitadoFacade
                    .getProcedimientosHabilitados(tipoProc, empresaDestino);
            for (ProcedimientoHabilitado p : listProcActivos) {
                data = new String[]{
                    p.getProcInfo().getDescripcion(),
                    p.getProcInfo().getProcedimiento(),
                    p.getProcInfo().getNivel().toString()
                };

                tabla.add(createMap(cols, data));
            }
            if (tabla.isEmpty()) {
                tabla.add(createMap(cols, emptyData));
            }
            insertarTabla(template, tabla, tipoProc.getTipoProcedimientoPK().getId());
        }
    }

    private void insertarFicheros(JavaScriptFileTemplate template) {
        List<Fichero> ficheros = ficheroFacade.findActives(empresaDestino);
        String[] cols = {"nombre", "descripcion", "numeroDeRegistro", "nivel", 
            "tipo", "fechaRegistro", "cesiones", "tipoDatos", "origenDatos",
            "transferenciasInternacionales", "perfiles"
        };
        String[] emptyData = {"", "", "", "", "", "", "", "", "", "", ""};
        List<Map<String, String>> tabla = new ArrayList<>();
        String[] data;

        if (logger.isInfoEnabled()) {
            logger.info("Insertando información de ficheros en el documento.");
        }

        for (Fichero f : ficheros) {
            data = new String[]{
                f.getNombre(),
                f.getDescripcion(),
                f.getNumeroDeRegistro(),
                f.getNivel().toString(),
                f.getTipo().toString(),
                f.getFechaHoraRegistro(),
                f.getCesiones(),
                f.getTipoDatos(),
                f.getOrigenDatos(),
                f.getTransferenciasInternacionales(),
                ficheroFacade.findPerfilesAsString(accessInfo, f)
            };
            tabla.add(createMap(cols, data));
        }

        if (tabla.isEmpty()) {
            tabla.add(createMap(cols, emptyData));
        }

        insertarTabla(template, tabla, "ficheros");
    }
    
    private void insertarListadoPersonalAuthCopiaReproduccion(JavaScriptFileTemplate template) {
        
        List<Persona> personas = personaFacade.getAutorizadosCopiaReproduccion(empresaDestino);
        String[] cols = {"nombrePersona", "dniPersona"};
        String[] emptyData = {"", ""};
        List<Map<String, String>> tabla = new ArrayList<>();
        String[] data;

        if (logger.isInfoEnabled()) {
            logger.info("Insertando información de Personas con autorización de"
                    + "copia/reproducción de documentos en el documento.");
        }

        for (Persona p : personas) {
            data = new String[]{
                p.getNombreCompleto(), p.getDni()
            };
            tabla.add(createMap(cols, data));
        }

        if (tabla.isEmpty()) {
            tabla.add(createMap(cols, emptyData));
        }

        insertarTabla(template, tabla, "listadoPersonalAuthCopiaReproduccion");    
    }

    /**
     * Se encarga de añadir a la plantilla información del sistema.
     *
     * @param template plantilla a cumplimentar.
     */
    private void insertarCamposSistema(JavaScriptFileTemplate template) {
        //TODO: Internacionalizar esto.
        SimpleDateFormat df;

        // Sección de Hora del sistema.
        // sisFechaLarga 
        df = new SimpleDateFormat("dd 'de' MMMMM 'de' yyyy", Locale.getDefault());
        setField(template, "sisFechaLarga", df.format(calendar.getTime()));

        // sisFechaCorta
        df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        setField(template, "sisFechaCorta", df.format(calendar.getTime()));

        // sisHoraMinSeg
        df = new SimpleDateFormat("hh:mm:ss aaa", Locale.getDefault());
        setField(template, "sisHoraMinSeg", df.format(calendar.getTime()));

        // sisHoraMin
        df = new SimpleDateFormat("hh:mm aaa", Locale.getDefault());
        setField(template, "sisHoraMin", df.format(calendar.getTime()));

        // sisLongTime        
        setField(template, "sisLongTime", calendar.getTimeInMillis());

        // Sección de Información de aplicación.
        setField(template, "sisAppName", "lopd.jkingii.es");
        setField(template, "sisAppURL", "http://lopd.jkingii.es");
    }
    
    private void insertarInformacionDePlantilla(JavaScriptFileTemplate template) {
        setField(template, "plantillaNombre", plantillaInfo.getDocumento().getFilename());
        setField(template, "plantillaId", plantillaInfo.getDocumento().getId());        
        setField(template, "plantillaMd5", plantillaInfo.getDocumento().getMd5());
    }

    /**
     * Inserta en la plantilla todos los datos de una empresa.
     *
     * @param template plantilla donde insertar.
     * @param e empresa desde la que leer la información.
     * @param tipo sufijo del código de empresa, Generadora o Destino.
     */
    private void insertarCamposEmpresa(JavaScriptFileTemplate template, Empresa e, String tipo) {
        if (logger.isDebugEnabled()) {
            logger.debug("Iniciando el envío del parámetros de la empresa {} al documento.", tipo);
        }
        setField(template, "empresa" + tipo + "Cif", e.getCif());
        setField(template, "empresa" + tipo + "Nombre", e.getNombre());
        setField(template, "empresa" + tipo + "RazonSocial", e.getRazonSocial());
        setField(template, "empresa" + tipo + "Email", e.getMailContacto());

        EmpresaSede emp = empresaSedeFacade.getPrincipal(e);
        setField(template, "empresa" + tipo + "CodigoPostal", emp.getCp());
        setField(template, "empresa" + tipo + "Telefono", emp.getTelefono());
        setField(template, "empresa" + tipo + "Fax", emp.getFax());
        setField(template, "empresa" + tipo + "Direccion", emp.getDireccion());
        setField(template, "empresa" + tipo + "Nota", emp.getNota());

        Localidad l = localizacion.getLocalidad(emp.getLocalidad());
        Provincia p = localizacion.getProvincia(emp.getProvincia());
        setField(template, "empresa" + tipo + "Ciudad", l.getLocalidad());
        setField(template, "empresa" + tipo + "Provincia", p.getProvincia());
    }

    /**
     * Añade los datos de una persona a una plantilla.
     *
     * @param template plantilla donde insertar.
     * @param p persona desde la que leer la información.
     * @param tipo sufijo del código de empresa, Generadora o Destino.
     */
    private void insertarCamposContacto(JavaScriptFileTemplate template, Persona p, String tipo) {
        setField(template, "personaEmp" + tipo + "Nombre", p.getNombre());
        setField(template, "personaEmp" + tipo + "Apellido1", p.getApellido1());
        setField(template, "personaEmp" + tipo + "Apellido2", p.getApellido2());
        setField(template, "personaEmp" + tipo + "Usuario", p.getUsuario());
        setField(template, "personaEmp" + tipo + "Perfil", p.getPerfil());
        setField(template, "personaEmp" + tipo + "Telefono", p.getTelefono());
        setField(template, "personaEmp" + tipo + "Email", p.getEmail());
        setField(template, "personaEmp" + tipo + "FechaInicio", p.getfFin());
        setField(template, "personaEmp" + tipo + "FechaFin", p.getfInicio());
        setField(template, "personaEmp" + tipo + "EsPerContacto", p.isPerContacto() ? "Si" : "No");
    }

    /**
     * Establece una propiedad.
     *
     * En caso de que la propiedad no exista en la plantilla evita que el
     * sistema deje de funcionar al capturar la excepción. Si se produce una
     * excepción no se toma ninguna medida.
     *
     * //TODO: Verificar los tipos de excepciones que puede emitir setField. no
     * se encontró nada en la documentación, ver código y afinar más en el
     * resultado.
     *
     * @param template plantilla a cumplimentar.
     * @param key clave de campo a cumplimentar.
     * @param value valor a añadir a la plantilla.
     */
    private void setField(JavaScriptFileTemplate template, String key, Object value) {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("Insertando el campo de plantilla key[{}], value[{}]", key, value);
            }
            template.setField(key, value != null ? value : "");
        } catch (Exception e) {
            logger.debug("Imposible establecer el field key[{}], value[{}]", key, value);
        }
    }

    // Comentado porque existen errores al convertir a odf parece funcionar bien
    // con hojas de cálculo.
//    private File toPdf(File file) throws FileNotFoundException, DocumentException {
//        // Load the ODS file
//        StandardLogger.debug("filepath: " + file.getPath());
//        final OpenDocument doc = new OpenDocument(file);
//        //La carga directa desde file está fallando, probando con getPath
////        doc.loadFrom(file.getPath());
//        
//        // Open the PDF document
//        Document document = new Document(PageSize.A4);
//        File outFile = new File("/tmp/GestionLopd/" + GenKey.newKey() + ".pdf");
//
//        PdfDocument pdf = new PdfDocument();
//
//        document.addDocListener(pdf);
//
//        FileOutputStream fileOutputStream = new FileOutputStream(outFile);
//        PdfWriter writer = PdfWriter.getInstance(pdf, fileOutputStream);
//        pdf.addWriter(writer);
//
//        document.open();
//
//        // Configure the renderer
//        ODTRenderer renderer = new ODTRenderer(doc);
//        renderer.setIgnoreMargins(true);
//        renderer.setPaintMaxResolution(true);
//        
//        document.close();
//        
//        return outFile;
//    }
    
    
    //<editor-fold defaultstate="collapsed" desc="EJB lookup">
    private FileDataBaseFacadeLocal lookupFileDataBaseFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (FileDataBaseFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/FileDataBaseFacade!com.openlopd.sessionbeans.documentos.FileDataBaseFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean FileDataBase.");
            throw new RuntimeException(ne);
        }
    }

    private PlantillaFacadeLocal lookupPlantillaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (PlantillaFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/PlantillaFacade!com.openlopd.sessionbeans.documentos.PlantillaFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean Plantilla");
            throw new RuntimeException(ne);
        }
    }

    private EmpresaFacadeLocal lookupEmpresaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (EmpresaFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/EmpresaFacade!com.openlopd.sessionbeans.empresas.EmpresaFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean Empresa.");
            throw new RuntimeException(ne);
        }
    }

    private EmpresaSedeFacadeLocal lookupEmpresaSedeFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (EmpresaSedeFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/EmpresaSedeFacade!com.openlopd.sessionbeans.empresas.EmpresaSedeFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean EmpresaSede");
            throw new RuntimeException(ne);
        }
    }

    private LocalizacionLocal lookupLocalizacionLocal() {
        try {
            Context c = new InitialContext();
            return (LocalizacionLocal) c.lookup("java:global/GestionLopd/common-ejb/Localizacion!com.openlopd.common.localizacion.business.LocalizacionLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean LocalizacionLocal.");
            throw new RuntimeException(ne);
        }
    }
    
    private PersonaFacadeLocal lookupPersonaFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (PersonaFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/PersonaFacade!com.openlopd.sessionbeans.empresas.PersonaFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean lookupPersonaFacadeLocal.");
            throw new RuntimeException(ne);
        }
    }

    private ProcedimientoDisponibleFacadeLocal lookupProcedimientoDisponibleFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ProcedimientoDisponibleFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/ProcedimientoDisponibleFacade!com.openlopd.sessionbeans.lopd.ProcedimientoDisponibleFacadeLocal");
        } catch (NamingException ne) {            
            logger.error("Imposible obtener el session bean lookupProcedimientoDisponibleFacadeLocal.");
            throw new RuntimeException(ne);
        }
    }

    private ProcedimientoHabilitadoFacadeLocal lookupProcedimientoHabilitadoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (ProcedimientoHabilitadoFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/ProcedimientoHabilitadoFacade!com.openlopd.sessionbeans.lopd.ProcedimientoHabilitadoFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean lookupProcedimientoHabilitadoFacadeLocal.");
            throw new RuntimeException(ne);
        }
    }

    private TipoProcedimientoFacadeLocal lookupTipoProcedimientoFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (TipoProcedimientoFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/TipoProcedimientoFacade!com.openlopd.sessionbeans.lopd.TipoProcedimientoFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean lookupTipoProcedimientoFacadeLocal.");
            throw new RuntimeException(ne);
        }
    }

    private FicheroFacadeLocal lookupFicheroFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (FicheroFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/FicheroFacade!com.openlopd.sessionbeans.lopd.FicheroFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean lookupFicheroFacadeLocal.");
            throw new RuntimeException(ne);
        }
    }

    private OperacionLopdFacadeLocal lookupOperacionLopdFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (OperacionLopdFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/OperacionLopdFacade!com.openlopd.sessionbeans.interfaz.OperacionLopdFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean lookupOperacionLopdFacadeLocal.");
            throw new RuntimeException(ne);
        }
    }

    private OutBoxFacadeLocal lookupOutBoxFacadeLocal() {
        try {
            Context c = new InitialContext();
            return (OutBoxFacadeLocal) c.lookup("java:global/GestionLopd/GestionLopd-ejb/OutBoxFacade!com.jkingii.mail.sessionbeans.OutBoxFacadeLocal");
        } catch (NamingException ne) {
            logger.error("Imposible obtener el session bean lookupOutBoxFacadeLocal.");
            throw new RuntimeException(ne);
        }
    }
    //</editor-fold>
}
