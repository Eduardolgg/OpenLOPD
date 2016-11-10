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

package com.openlopd.business.files;

import com.openlopd.agpd.nota.xml.DatosFichero;
import com.openlopd.agpd.nota.xml.XmlEnvioFactory;
import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.documents.CumplimentarPlantilla;
import com.openlopd.entities.documentos.FileDataBase;
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.lopd.Fichero;
import com.openlopd.entities.seguridad.Shadow;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.utils.primarykey.GenKey;
import com.openlopd.sessionbeans.documentos.FileDataBaseFacadeLocal;
import com.openlopd.sessionbeans.documentos.FileToDataBase;
import com.openlopd.sessionbeans.documentos.PlantillaFacadeLocal;
import es.agpd.nota.dos.cero.Envio;
import es.agpd.nota.dos.cero.ObjectFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Se encarga de gestionar la generación de los documentos a partir de
 * plantillas.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 17 de sep de 2012
 */
@Stateless
public class GenerarDocumentos implements GenerarDocumentosLocal {
    
    private static Logger logger = LoggerFactory.getLogger(GenerarDocumentos.class);

    @EJB
    private FileDataBaseFacadeLocal fileDataBaseFacade;
    @EJB
    private PlantillaFacadeLocal plantillaFacade;
    private static final ResourceBundle rb = ResourceBundle.getBundle("com.openlopd.config.config", Locale.getDefault());
    public static final String TMP_DIR = rb.getString("tempDir");

    public GenerarDocumentos() {
    }

    /**
     * Verifica la existencia del directorio temporal.
     * @return true si el directorio existe, false en caso contrario.
     */
    @Override
    public boolean existTmpDir() {
        System.out.println(TMP_DIR.substring(0, TMP_DIR.length()-1));
        return new File(TMP_DIR.substring(0, TMP_DIR.length()-1)).exists();
    }
    
    @Override
    public FileDataBase CumplimentarPlantilla(AccessInfo accessInfo,
            String nombrePlantilla, Map<String, String[]> formData,
            ColumnasPermisos permiso) {
        
        return new CumplimentarPlantilla(accessInfo, null, permiso)
                .generar(nombrePlantilla, formData);
    }
    
    @Override
    public FileDataBase CumplimentarPlantilla(AccessInfo accessInfo,
            String nombrePlantilla, ColumnasPermisos permiso) {

        return new CumplimentarPlantilla(accessInfo, null, permiso)
                .generar(nombrePlantilla);
    }

    @Override
    public FileDataBase CumplimentarPlantilla(Empresa empresaGeneradora,
            Empresa empresaDestino, String nombrePlantilla, 
            ColumnasPermisos permiso) {

        return new CumplimentarPlantilla(empresaGeneradora, empresaDestino,
                null, permiso)
                .generar(nombrePlantilla);
    }

    /**
     * Genera la notificación de la AGPD
     * @param datosFichero datos a incluir en la notificación.
     * @param fichero Opcional, solo necesario en las modificaciones para verificar
     * los datos modificados.
     * @return Notificación firmada digitalmente.
     * @throws Exception En caso de error.
     */    
    private FileDataBase GenerarNotificacionAgpd_(DatosFichero datosFichero,
        Fichero fichero, boolean firmar) throws Exception {
        ObjectFactory of = new ObjectFactory();
        File xmlFile = new File(rb.getString("tempDir") + GenKey.newKey() + ".xml");
        File xmlSignedFile = new File(rb.getString("tempDir") + GenKey.newKey() + ".xml");
        Envio e = null;

        if (datosFichero.getAccion().equals(XmlEnvioFactory.ACCION_ALTA)) {
            e = XmlEnvioFactory.alta(of.createEnvio(), datosFichero);
        } else if (datosFichero.getAccion().equals(XmlEnvioFactory.ACCION_MODI)) {
            e = XmlEnvioFactory.modificacion(of.createEnvio(), datosFichero, fichero);
        } else if (datosFichero.getAccion().equals(XmlEnvioFactory.ACCION_SUPR)) {
            e = XmlEnvioFactory.supresion(of.createEnvio(), datosFichero, fichero);
        }
        XmlEnvioFactory.writeXml(e, xmlFile);

        if (firmar) {
            logger.debug("Realizando firma con certificado digital interno.");
            new Signature().firmar(xmlFile, xmlSignedFile);
        } else {
            logger.debug("El fichero está firmado, no se firmará con el certificado interno.");
            xmlSignedFile = xmlFile;
        }

        FileDataBase dbfOut = new FileToDataBase(xmlSignedFile,
                datosFichero.getEmpresa(), datosFichero.getUserInfo(), 
                ColumnasPermisos.SYS_ADMIN).commit();
        fileDataBaseFacade.create(dbfOut);

        if (firmar) {
            xmlFile.delete();
        }

        return dbfOut;
    }
    
    /**
     * Genera la notificación de la AGPD
     * @param datosFichero datos a incluir en la notificación.
     * @param fichero Opcional, solo necesario en las modificaciones para verificar
     * los datos modificados.
     * @return Notificación firmada digitalmente.
     * @throws Exception En caso de error.
     */
    @Override
    public FileDataBase GenerarNotificacionAgpdFirmada(DatosFichero datosFichero,
        Fichero fichero) throws Exception {
        boolean firmar = true;
        return this.GenerarNotificacionAgpd_(datosFichero, fichero, firmar);
    }
    
    /**
     * Genera la notificación de la AGPD no firmada
     * @param datosFichero datos a incluir en la notificación.
     * @return Notificación en xml.
     * @throws Exception En caso de error.
     */
    @Override
    public FileDataBase GenerarNotificacionAgpd(DatosFichero datosFichero,
        Fichero fichero) throws Exception {
        boolean firmar = false;
        return this.GenerarNotificacionAgpd_(datosFichero, null, firmar);
    }

    /**
     * Genera un fichero en disco.
     * @param fileText Texto del fichero
     * @return File apuntando al fichero creado.
     * @throws FileNotFoundException Si el fichero no existe.
     * @throws IOException Si se producen errores al escribir en el fichero.
     */
    @Override
    public File GenerarFicheroEnDisco(String fileText) throws FileNotFoundException, IOException {
        String fileName = rb.getString("tempDir") + GenKey.newKey() + ".xml";
        
        try (FileOutputStream fsf = new FileOutputStream(fileName)) {
            fsf.write(fileText.getBytes());
        }

        return new File(fileName);
    }
    
    
    /**
     * Almacena el fichero en la base de datos.
     * 
     * El fichero es borrado una vez almacenado.
     * 
     * @param file fichero a guardar.
     * @param empresa empresa a la que pertenece el fichero, si es nulo se creará
     * a nombre de la empresa del sistema.
     * @param userInfo usuario que realiza la operación, si es nulo se creará con
     * el usuario del sistema.
     * @throws FileNotFoundException Si el fichero no existe.
     * @throws IOException Si se producen errores al escribir en el fichero.
     */
    @Override
    public FileDataBase guardarFichero(File file, Empresa empresa,
            Shadow userInfo, ColumnasPermisos permiso)
            throws FileNotFoundException, IOException {
        Empresa e; 
        Shadow s;
        e = (empresa != null ? empresa : new Empresa(rb.getString("empresaID")));
        s = (userInfo != null ? userInfo : new Shadow(rb.getString("userID")));
        FileDataBase f = new FileToDataBase(file, e, s, permiso).commit();
        fileDataBaseFacade.create(f);
        return f;
    }
    
}
