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

package com.openlopd.business.lopd;

import com.openlopd.agpd.nota.tablascomunes.TipoSolicitud;
import com.openlopd.agpd.nota.ws.NotaWebservice;
import com.openlopd.agpd.nota.xml.XmlEnvioFactory;
import com.openlopd.business.files.GenerarDocumentosLocal;
import com.openlopd.entities.lopd.Fichero;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.sessionbeans.lopd.FicheroFacadeLocal;
import es.agpd.nota.dos.cero.Envio;
import java.io.File;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Se encarga del envío de ficheros a la AGPD de forma periódica.
 *
 * @author Eduardo L. García Glez.
 */
@Singleton
@LocalBean
public class FileRegisterBot {

    @EJB
    private GenerarDocumentosLocal generarDocumentos;
    @EJB
    private FicheroFacadeLocal ficheroFacade;
    private NotaWebservice sw = new NotaWebservice();
    private static Logger logger = LoggerFactory.getLogger(FileRegisterBot.class);
    private static final char SEP_FECHA = '/';
    private static final char SEP_HORA = ':';
    public static final String ENVIO_OK = "00";
    public static final String ERR_CODE_UNKNOWN = "SS";
    public static final String SW_INTERNAL_ERROR = "10:12";
    private boolean autoRegistroActivo = false;

    /**
     * Establece si el sistema automático de registro de ficheros está activo.
     * @return true el sistema está activado, false en caso contrario.
     */
    @Lock(LockType.READ)
    public boolean isAutoRegistroActivo() {
        return autoRegistroActivo;
    }

    /**
     * Obtiene si el sistema automático de registro de ficheros está activo.
     * @param autoRegistroActivo true el sistema está activado, false en 
     * caso contrario.
     */
    @Lock(LockType.WRITE)
    public void setAutoRegistroActivo(boolean autoRegistroActivo) {
        this.autoRegistroActivo = autoRegistroActivo;
    }   
    
    private String getFechaRegistro(String fecha, String hora) { 
        if (fecha == null || fecha.isEmpty()) {
            return "";
        }
        if (hora == null || hora.isEmpty()) {
            return "";
        }
        try {
            StringBuilder fechaRegistro = new StringBuilder();
            fechaRegistro.append(fecha).append(" ").append(hora.substring(0, 4));
            fechaRegistro.insert(2, SEP_FECHA);
            fechaRegistro.insert(5, SEP_FECHA);
            fechaRegistro.insert(13, SEP_HORA);
            return fechaRegistro.toString();
        } catch (Exception e) {
            logger.error("Imposible calcular la hora [{}] y fecha [{}] del fichero.", hora, fecha);
            return "";
        }
    }

    /**
     * Registro periódico de ficheros en la AGPD.
     *
     * Busca la lista de ficheros que aún no están registrados y los envía a la
     * AGPD.
     */
    @Lock(LockType.WRITE)
    @Schedule(minute = "*/2", second = "0", dayOfMonth = "*", month = "*", year = "*", hour = "*", dayOfWeek = "*")
    public void sheduleRegister() {

        //TODO: Hay que revisar este método, en caso de fallo en el contacto 
        // con el sistema de ficheros o la base de datos
        // perderemos la información del registro.
        String lastResponse;
        File lastFile;
        String estError;
        String numRegistro;
        String fechaRegistro;
        List<Fichero> ficherosARegistrar = ficheroFacade.ficherosSinRegistrar();
        
        if (!autoRegistroActivo) {
            logger.info("El sistema automático de registro de ficheros "
                    + "en la AGPD está desactivado, consulte con el administrador "
                    + "del sistema para activarlo.");
            return;
        }
        
        if (logger.isInfoEnabled()) {
            logger.info("Iniciando Notificación de [{}] ficheros.", 
                    ficherosARegistrar.size());
        }
        
        //TODO antes de iniciar el registro verificar que existe el directorio Temporal.

        for (Fichero f : ficherosARegistrar) {
            try {
                /* Solo se reenvían peticiones que han tenido error
                cuando el error no se encuentra en la solicitud, es decir
                que existió un error interno deconocido en el SW de la AGPD */
                if (f.getError() == null || SW_INTERNAL_ERROR.contains(f.getError())) {
                    lastResponse = sw.registrarXml(f.getSolicitud());
                    lastFile = generarDocumentos.GenerarFicheroEnDisco(lastResponse);
                    f.setRespuesta(generarDocumentos.guardarFichero(lastFile, null,
                            null, ColumnasPermisos.SYS_ADMIN));
                    
                    Envio e = XmlEnvioFactory.readXml(f.getRespuesta().getFile());

                    estError = e.getRegUno().getDeclarante().getControl().getEstErr();
                    numRegistro = e.getRegUno().getControl().getNumReg();
                    
                    fechaRegistro = this.getFechaRegistro(
                            e.getRegUno().getControl().getFWeb(),
                            e.getRegUno().getControl().getHWeb());
                    
                    if (estError.equals(ENVIO_OK)) {
                        f = insertarInfoRegistro(f, e, numRegistro, fechaRegistro);
                    } else {
                        f = registrarError(f, estError);
                    }
                } else {
                    logger.error("Fichero [{}] no notificado, revisar error y limpiar el código de error de "
                            + "la base de datos para continuar", f.getId());
                }
            } catch (Exception ex) {
                f = registrarError(f, ex.getMessage());                
            }
            ficheroFacade.edit(f);
        }
    }

    private Fichero insertarInfoRegistro(Fichero fichero, Envio envio, 
            String numRegistro, String fechaRegistro) {
        if (fichero.getAccion().equals(TipoSolicitud.ALTA)) {
            if (!envio.getRegUno().getDeclaracion().isEmpty()) {
                Envio.RegUno.Declaracion.Fichero f = envio.getRegUno()
                        .getDeclaracion().get(0).getFichero().get(0);
                fichero.setNumeroDeRegistro(f.getControl().getAccionesNotAlta().getNumReg());
                fichero.setFechaHoraRegistro(f.getControl().getAccionesNotAlta().getFechaReg());
            }
        }
        fichero.setError(ENVIO_OK);
        fichero.setFechaError(null);
        fichero.setNumeroDeRegistro(numRegistro);
        fichero.setFechaHoraRegistro(fechaRegistro);
        if (fichero.getAccion() == TipoSolicitud.SUPRESION) {
            fichero.setActive(false);
        }
        return fichero;
    }

    /**
     * Registra el error en el sitema.
     *
     * @param f fichero que al registrar produce el error.
     * @param error Mensaje o código de error.
     */
    private Fichero registrarError(Fichero fichero, String error) {
        Fichero f = fichero;
        logger.error("El fichero [{}] no pudo ser registrado, quedará "
                + "marcado como error y se volverá a intentar en el "
                + "próximo ciclo si se ha limpiado el error de DB. Error: {}", f.getId(), error);
        
        f.setError(error != null && error.length() == 2 ? error : ERR_CODE_UNKNOWN);
        f.setFechaError(new Date().getTime());
        ficheroFacade.edit(f);
        return f;
    }
}
