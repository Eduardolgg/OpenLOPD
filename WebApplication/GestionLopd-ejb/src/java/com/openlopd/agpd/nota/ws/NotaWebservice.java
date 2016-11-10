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

package com.openlopd.agpd.nota.ws;

import com.openlopd.config.Entornos;
import com.openlopd.entities.documentos.FileDataBase;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.xml.namespace.QName;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.Base64;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Conexión con el servicio web del sistema Nota.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 26 de nov de 2012
 */
public class NotaWebservice {
    private static final ResourceBundle rb = ResourceBundle.getBundle("com.openlopd.config.config", Locale.getDefault());
    public final static String entorno = rb.getString("entorno");
    private static Logger logger = LoggerFactory.getLogger(NotaWebservice.class);

    public String registrarXml(FileDataBase xmlFile) throws Exception {
        String endpoint =
                "https://www.aespd.es:443/agenciapd/axis/SolicitudService?wsdl";
        
        if (logger.isInfoEnabled()) {
            logger.info("Iniciando notificación en el entorno de {}.", entorno);
        }

        Service service = new Service();
        Call call = (Call) service.createCall();

        call.setTargetEndpointAddress(new java.net.URL(endpoint));

        if (entorno.equals(Entornos.exp.name())) {
            // Operación para el sistema en producción.
            call.setOperationName(new QName("http://soapinterop.org/", "registrarXml"));
        } else {
            // Operación para el sistema en pruebas.
            call.setOperationName(new QName("http://soapinterop.org/", "probarXml"));
        }        

        // Codificación y envío del fichero.
        String codecFile = Base64.encode(xmlFile.getFile());
        String ret = (String) call.invoke(new Object[]{codecFile});
        // TODO: Hay que verificar que la firma de esto es correcta.
        return StringEscapeUtils.unescapeHtml4(new String(Base64.decode(ret), "ISO-8859-1"));
        //return new String(Base64.decode(ret), "ISO-8859-1");
    }
}
