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

import com.openlopd.business.files.GenerarDocumentosLocal;
import com.openlopd.entities.documentos.FileDataBase;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.web.controllers.privatearea.CSession;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Encargado de generar documentos genéricos.
 * @author Eduardo L. García Glez.
 * @version 0.0.0 22 de septiembre de 2012
 */
public class generation extends HttpServlet {
    @EJB
    private GenerarDocumentosLocal generarDocumentos;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        FileDataBase f;
        StringBuilder jsonResponse;
        
        
        try {
            ColumnasPermisos permiso = request.getParameter("nombre").equals("ResponsableSeguridad") ?
                    ColumnasPermisos.DOCUMENTO_SEGURIDAD : ColumnasPermisos.DOCUMENTOS_GENERALES;
            f = generarDocumentos.CumplimentarPlantilla(
                    ((CSession) request.getSession().getAttribute("cSession")).getAccessInfo(), 
                    request.getParameter("nombre"), request.getParameterMap(),
                    permiso); //TODO: Asignar mejor los permisos.
            
            jsonResponse = new StringBuilder();
            
            jsonResponse.append("{\"status\":\"OK\",");
            jsonResponse.append("\"message\":\"\",");            
            jsonResponse.append("\"id\":\"").append(f.getId()).append("\",");
            jsonResponse.append("\"usuario\":\"").append(f.getUsuario().getUsuario()).append("\",");
            jsonResponse.append("\"uploadDate\":\"").append(f.getUploadDate()).append("\",");
            jsonResponse.append("\"filename\":\"").append(f.getFilename()).append("\",");
            jsonResponse.append("\"mimeType\":\"").append(f.getMimeType()).append("\",");
            jsonResponse.append("\"md5\":\"").append(f.getMd5()).append("\",");
            jsonResponse.append("\"size\":\"").append(f.getSize()).append("\"");
            jsonResponse.append("}");
            out.print(jsonResponse.toString());
        } catch (Exception e) {
            out.print("{\"status\":\"ERROR\",\"message\":\"Error Generando el fichero\"}");
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
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
     * Handles the HTTP
     * <code>POST</code> method.
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
