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

import com.openlopd.agpd.nota.xml.XmlEnvioFactory;
import es.agpd.nota.dos.cero.Envio;
import es.agpd.nota.dos.cero.ObjectFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.rpc.ServiceException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.Base64;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;  
import org.xml.sax.SAXException;

/**
 * Controla el acceso al sistema Nota, altas, modificaciones y bajas de ficheros
 * en la agencia española de protección de datos.
 * 
 * 
 * @author Eduardo L. García Glez.
 * @version 0.0.0 01 de nov de 2012
 */
public class CNotaSystem implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(CNotaSystem.class);

    public CNotaSystem() {
    }
    
    public String test() { 
        try {
            String endpoint =
                    "https://www.aespd.es:443/agenciapd/axis/SolicitudService?wsdl";

            Service service = new Service();
            Call call = (Call) service.createCall();

            call.setTargetEndpointAddress(new java.net.URL(endpoint));
            call.setOperationName(new QName("http://soapinterop.org/", "probarXml"));
//            call.setOperationName(new QName("http://soapinterop.org/", "registrarXml"));
  
            String ret = (String) call.invoke(new Object[]{readfile()});

            return new String(Base64.decode(ret));
        } catch (ServiceException | MalformedURLException | RemoteException e) {
            return e.getMessage();            
        }
    }
    
    public String altaFichero() throws Exception {
        ObjectFactory of = new ObjectFactory();
        Envio e = XmlEnvioFactory.alta(null, null);
        
        try {            
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(e.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(e, System.out);
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
        
        return null;
        
    }
    
    private static final String KEYSTORE_TYPE         = "JKS";  
    private static final String KEYSTORE_FILE         = "./OpenLopd/web/NOTA/myKeyStore.jks";  
    private static final String KEYSTORE_PASSWORD     = "changeit";  
    private static final String PRIVATE_KEY_PASSWORD  = "";
    private static final String PRIVATE_KEY_ALIAS     = "certificado-pk12";

    public String firmar () throws Exception {
        Document doc = DOMUtils.createSampleDocument();
        org.apache.xml.security.Init.init();
//        Constants.setSignatureSpecNSprefix(""); // Sino, pone por defecto como prefijo: "ns"
                
        // Cargamos el almacen de claves  
        KeyStore ks = KeyStore.getInstance(KEYSTORE_TYPE);
        ks.load(new FileInputStream(KEYSTORE_FILE), KEYSTORE_PASSWORD.toCharArray());

        // Obtenemos la clave privada, pues la necesitaremos para encriptar.  
        PrivateKey privateKey = (PrivateKey) ks.getKey(PRIVATE_KEY_ALIAS, PRIVATE_KEY_PASSWORD.toCharArray());
        File signatureFile = new File("signature.xml");
        String baseURI = signatureFile.toURL().toString();   // BaseURI para las URL Relativas.  

        // Instanciamos un objeto XMLSignature desde el Document. El algoritmo de firma será DSA  
        XMLSignature xmlSignature = new XMLSignature(doc, baseURI, XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA1);

        // Añadimos el nodo de la firma a la raiz antes de firmar.  
        // Observe que ambos elementos pueden ser mezclados en una forma con referencias separadas  
        doc.getDocumentElement().appendChild(xmlSignature.getElement());

        // Creamos el objeto que mapea: Document/Reference  
        Transforms transforms = new Transforms(doc);
        transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);

        // Añadimos lo anterior Documento / Referencia  
        // ALGO_ID_DIGEST_SHA1 = "http://www.w3.org/2000/09/xmldsig#sha1";
        xmlSignature.addDocument("", transforms, Constants.ALGO_ID_DIGEST_SHA1);
        
        // Añadimos el KeyInfo del certificado cuya clave privada usamos  
        X509Certificate cert = (X509Certificate) ks.getCertificate(PRIVATE_KEY_ALIAS);
        xmlSignature.addKeyInfo(cert.getPublicKey());
        xmlSignature.addKeyInfo(cert);
        
        // Vemos como queda el documento antes de firmarlo.
        // DOMUtils.outputDocToFile(doc, new File("preSignature.xml"));

        // Realizamos la firma 
        xmlSignature.sign(privateKey);

        // Guardamos archivo de firma en disco  
        DOMUtils.outputDocToFile(doc, signatureFile);
        return "Signed XML: " + doc.getTextContent();
    }
    
    private String readfile() {
        try {
            File file = new File("signature.xml");
            return Base64.encode(this.read(file)).toString();
        } catch (IOException e) {
            return "Error obteniendo el fichero";
        }
    }   
    
    private byte[] read(File file) throws IOException {

        byte[] buffer = new byte[(int) file.length()];
        InputStream ios = null;
        try {
            ios = new FileInputStream(file);
            if (ios.read(buffer) == -1) {
                throw new IOException("EOF reached while trying to read the whole file");
            }
        } finally {
            try {
                if (ios != null) {
                    ios.close();
                }
            } catch (IOException e) {
            }
        }
        return buffer;
    }
    
    public String verify() {org.apache.xml.security.Init.init();  
        boolean schemaValidate = false;
        final String signatureSchemaFile = "./Sistemas/libs/firmaDigital/ApacheSantuario/xml-security-1_5_3/samples/data/xmldsig-core-schema.xsd";
        String signatureFileName = "./glassfish-3.1.1/glassfish/domains/domain1/signature.xml";

        if (schemaValidate) {
            System.out.println("We do schema-validation");
        }

        javax.xml.parsers.DocumentBuilderFactory dbf =
            javax.xml.parsers.DocumentBuilderFactory.newInstance();

        if (schemaValidate) {
            dbf.setAttribute("http://apache.org/xml/features/validation/schema",
                             Boolean.TRUE);
            dbf.setAttribute("http://apache.org/xml/features/dom/defer-node-expansion",
                             Boolean.TRUE);
            dbf.setValidating(true);
            dbf.setAttribute("http://xml.org/sax/features/validation",
                             Boolean.TRUE);
        }

        dbf.setNamespaceAware(true);
        dbf.setAttribute("http://xml.org/sax/features/namespaces", Boolean.TRUE);

        if (schemaValidate) {
            dbf.setAttribute("http://apache.org/xml/properties/schema/external-schemaLocation",
                             Constants.SignatureSpecNS + " " + signatureSchemaFile);
        }

        try {

            File f = new File(signatureFileName);

            System.out.println("Try to verify " + f.toURI().toURL().toString());

            javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();

            db.setErrorHandler(new org.apache.xml.security.utils.IgnoreAllErrorHandler());

            if (schemaValidate) {
                db.setEntityResolver(new org.xml.sax.EntityResolver() {

                    @Override
                    public org.xml.sax.InputSource resolveEntity(
                        String publicId, String systemId
                    ) throws org.xml.sax.SAXException, FileNotFoundException {

                        if (systemId.endsWith("xmldsig-core-schema.xsd")) {
                            try {
                                return new org.xml.sax.InputSource(
                                    new FileInputStream(signatureSchemaFile));
                            } catch (FileNotFoundException ex) {
                                throw new org.xml.sax.SAXException(ex);
                            }
                        } else {
                            return null;
                        }
                    }
                });
            }

            org.w3c.dom.Document doc = db.parse(new java.io.FileInputStream(f));

            XPathFactory xpf = XPathFactory.newInstance();
            XPath xpath = xpf.newXPath();
            xpath.setNamespaceContext(new DSNamespaceContext());

            String expression = "//ds:Signature[1]";
            Element sigElement = 
                (Element) xpath.evaluate(expression, doc, XPathConstants.NODE);
            XMLSignature signature = 
                new XMLSignature(sigElement, f.toURI().toURL().toString());

            signature.addResourceResolver(new OfflineResolver());

            KeyInfo ki = signature.getKeyInfo();

            if (ki != null) {
                if (ki.containsX509Data()) {
                    System.out.println("Could find a X509Data element in the KeyInfo");
                }

                X509Certificate cert = signature.getKeyInfo().getX509Certificate();

                if (cert != null) {
                    System.out.println("The XML signature in file "
                                       + f.toURI().toURL().toString() + " is "
                                       + (signature.checkSignatureValue(cert)
                                           ? "valid (good)"  : "invalid !!!!! (bad)"));
                } else {
                    System.out.println("Did not find a Certificate");

                    PublicKey pk = signature.getKeyInfo().getPublicKey();

                    if (pk != null) {
                        System.out.println("The XML signature in file "
                                           + f.toURI().toURL().toString() + " is "
                                           + (signature.checkSignatureValue(pk)
                                               ? "valid (good)" : "invalid !!!!! (bad)"));
                    } else {
                        System.out.println(
                            "Did not find a public key, so I can't check the signature");
                    }
                }
            } else {
                System.out.println("Did not find a KeyInfo");
            }
            //TODO: Ser más específico con esto.
        } catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException | XMLSecurityException ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }
    
}
