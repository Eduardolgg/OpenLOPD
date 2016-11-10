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

package com.openlopd.servlets;

import com.elgg.utils.system.Command;
import com.elgg.utils.system.CreateDir;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuración básica para el funcionamiento del sistema.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.0
 */
public class SistemConfig extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(SistemConfig.class);
    private static ResourceBundle rb = ResourceBundle.getBundle("com.openlopd.config.config", Locale.getDefault());
    private String configInfo = "Sin configurar.";
    private int port;
    private String convertSistemComand;
    private String tmpDir;

    @Override
    public void init() {
        try {
            port = new Integer(rb.getString("convertPort"));
            convertSistemComand = rb.getString("convertSistemComand");
            tmpDir = rb.getString("tempDir");
            if (new CreateDir(tmpDir).create()) {
                logger.info("Creado directorio temporal en [{}]", tmpDir);
            } else {
                logger.error("Imposible crear el directorio temporal [{}]", tmpDir);
            }
            Command c = new Command();
            c.exec(convertSistemComand.split(" "), false);
            configInfo = "";
        } catch (Exception e) {
            logger.error("Error configurando el sistema.");
            configInfo = e.getMessage();
        }
    }

    /**
     * Envía información de configuración al log.
     */
    private void logSistemConfig() {
        logger.info("Directorio temporal establecido en [{}]", tmpDir);
        logger.info("Comando de sistema [{}]", convertSistemComand);
        logger.info("Puerto [{}]", port);
        logger.error(Integer.toString(convertSistemComand.split(" ").length));
        if (configInfo.isEmpty()) {
            logger.info("El sistema ha sido configurado con éxito.");
        } else {
            logger.info("Error en la configuracion, Exception: ", configInfo);
        }
    }

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
        try {
            /*
             * TODO output your page here. You may use following sample code.
             */
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SistemConfig</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Sistem Configuration Info.</h1>");
            if (configInfo.isEmpty()) {
                out.println("El sistema ha sido configurado con éxito.");
            } else {
                out.println("Sin información sobre la configuración.");
            }
            logSistemConfig();
            out.println("</body>");
            out.println("</html>");
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
