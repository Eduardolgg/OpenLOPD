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

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.Constants;
import org.w3c.dom.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Firma digital.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0 26 de nov de 2012
 */
public class Signature {
    private static Logger logger = LoggerFactory.getLogger(Signature.class);

    //TODO: Varios valores aquí indicados deverian cargarse de una fuente externa.
    private static final String KEYSTORE_TYPE = "JKS";
    private static final String KEYSTORE_FILE = "./OpenLopd/web/NOTA/myKeyStore.jks";
    private static final String KEYSTORE_PASSWORD = "changeit";
    private static final String PRIVATE_KEY_PASSWORD = "changeit";
    private static final String PRIVATE_KEY_ALIAS = "certificado-pk12";

    public File firmar(File inputFile, File outputFile) throws Exception {
        Document doc = DOMUtils.loadDocumentFromFile(inputFile);
        if(logger.isDebugEnabled()) {
            logger.debug("Se abre documento a firmar encode [{}]", doc.getXmlEncoding());
        }
        org.apache.xml.security.Init.init();
//        Constants.setSignatureSpecNSprefix(""); // Sino, pone por defecto como prefijo: "ns"
        
        // Cargamos el almacen de claves  
        KeyStore ks = KeyStore.getInstance(KEYSTORE_TYPE);
        ks.load(new FileInputStream(KEYSTORE_FILE), KEYSTORE_PASSWORD.toCharArray());

        // Obtenemos la clave privada, pues la necesitaremos para encriptar.  
        PrivateKey privateKey = (PrivateKey) ks.getKey(PRIVATE_KEY_ALIAS, PRIVATE_KEY_PASSWORD.toCharArray());
//        File signatureFile = new File("signature.xml");
        File signatureFile = outputFile;
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
        return signatureFile;
    }
}
