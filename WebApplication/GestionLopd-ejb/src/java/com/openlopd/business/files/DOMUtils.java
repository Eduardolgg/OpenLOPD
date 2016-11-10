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
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;  
  
/** 
 * Clase de utilidad para el ejemplo 
 * @author Carlos García. Autentia. 
 * @see http://www.mobiletest.es 
 */  
public class DOMUtils {  
      
    /** 
     * Serializa un objeto Document en un archivo 
     */  
    public static void outputDocToFile(Document doc, File file) throws Exception {  
        FileOutputStream    f              = new FileOutputStream(file);  
//        OutputStreamWriter f = new OutputStreamWriter( new FileOutputStream(file),Charset.forName("ISO-8859-1"));
        TransformerFactory factory         = TransformerFactory.newInstance();  
        Transformer        transformer     = factory.newTransformer();  
          
//        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");  
        transformer.transform(new DOMSource(doc), new StreamResult(f));
  
        f.close();  
    }  
    
      
    /** 
     * Lee un Document desde un archivo 
     */  
    public static Document loadDocumentFromFile(java.io.File file) throws Exception {  
        DocumentBuilderFactory  factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder         builder = null;  
          
        factory.setNamespaceAware(true);  
          
        builder = factory.newDocumentBuilder();  
          
        return builder.parse(file);  
    }   
      
    /** 
     * @return Crea un elemento <tag>value</tag> 
     */  
    public static Element createNode(Document document, String tag, String value){  
        Element node = document.createElement(tag);  
        if (value != null){  
            node.appendChild(document.createTextNode(value));  
        }  
        return node;  
    }    
      
    /** 
     * @return Devuelve un Document a firmar 
     * @throws Exception Cualquier incidencia 
     */  
    public static Document createSampleDocument() throws Exception {  
        DocumentBuilderFactory  factory  = DocumentBuilderFactory.newInstance();  
        DocumentBuilder         builder  = factory.newDocumentBuilder();
        Document document = builder.parse("./OpenLopd/web/NOTA/NoSigned.xml");  
        return document;  
    }     
}  