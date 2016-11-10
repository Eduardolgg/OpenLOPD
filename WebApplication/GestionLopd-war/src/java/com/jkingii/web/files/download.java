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

package com.jkingii.web.files;

import com.openlopd.entities.documentos.FileDataBase;
import com.openlopd.entities.seguridad.PermisosGrupos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.sessionbeans.documentos.FileDataBaseFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import java.io.BufferedInputStream;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Descara de ficheros
 * @author Eduardo L. García Glez.
 * @version 0.0.1 16 de ene de 2012, #94 Detección automática del mime.
 * Modificaciones:
 *   0.0.0 13 de jul de 2011, versión inicial.
 */
public class download extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(download.class);
    @EJB
    private FileDataBaseFacadeLocal fileDataBaseFacade;
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //get the 'file' parameter
        String fileId = (String) request.getParameter("file");
        if (fileId == null || fileId.isEmpty()) {
            throw new ServletException(
                    "Invalid or non-existent file parameter in download servlet.");
        }
        
        ServletOutputStream stream = null;
        BufferedInputStream buf = null;
        try {
            stream = response.getOutputStream();
            CSession session = (CSession) request.getSession().getAttribute("cSession");
            
            //TODO: Activar permisos de descarga por usuarios.
            FileDataBase fdb = fileDataBaseFacade.find(fileId);
            if (fdb.getEmpresa().equals(session.getAccessInfo().getSubEmpresa())
                    && session.getAccessInfo().getPermisosUsuario()
                    .hasAccess(fdb.getPermiso(), PermisosGrupos.LECTURA)) {
                response.setContentType(fdb.getMimeType());
                response.addHeader("Content-Disposition", "attachment; filename="
                        + fdb.getFilename());
                response.setContentLength(fdb.getSize().intValue());
                stream.write(fdb.getFile());
            } else {
                response.setContentType("text/html");
                stream.write(("<!DOCTYPE html><html><head>"
                        + "<title>File Download</title></head><body>" 
                        + "No tiene permisos para acceder al fichero." 
                        + "</body></html>").getBytes());
            }
        } catch (IOException ioe) {
            logger.error("Error enviando fichero al cliente : " + ioe.getMessage());
            throw new ServletException(ioe.getMessage());
        } catch (UnknownColumnException uce) {
            logger.error("Permios desconocido fichero: [{}], no se entrega", 
                    request.getParameter("file"));
        } finally {
            if (stream != null) {
                stream.close();
            }
            if (buf != null) {
                buf.close();
            }
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
