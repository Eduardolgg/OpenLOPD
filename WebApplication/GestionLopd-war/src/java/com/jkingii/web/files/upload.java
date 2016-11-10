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

import com.elgg.utils.lang.Hexadecimal;
import com.elgg.utils.mimetype.MimeType;
import com.openlopd.business.seguridad.SeguridadLocal;
import com.openlopd.entities.documentos.FileDataBase;
import com.openlopd.entities.seguridad.PermisosGrupos;
import com.openlopd.entities.seguridad.base.ColumnasPermisos;
import com.openlopd.entities.seguridad.exception.UnknownColumnException;
import com.openlopd.sessionbeans.documentos.FileDataBaseFacadeLocal;
import com.openlopd.web.controllers.privatearea.CSession;
import com.jkingii.datatables.ResponseConfig;
import com.jkingii.mail.utils.GenKey;
import eu.medsea.mimeutil.MimeUtil;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Carga de ficheros en el servidor.
 *
 * @author Eduardo L. García Glez.
 * @version 0.0.2 25 de enero de 2012, #136 configuración respuesta Json. #96
 * Versión Inicial. 0.0.1 10 de jun de 2012, #112 Adaptación a LOPD. 0.0.2 25 de
 * enero de 2012, #136 configuración respuesta Json.
 */
public class upload extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(upload.class);
    @EJB
    private SeguridadLocal seguridad;
    @EJB
    private FileDataBaseFacadeLocal fileDataBaseFacade;
    private boolean isMultipart;
    private String filePath;
    private int maxFileSize = 50 * 1024;
    private int maxMemSize = 4 * 1024;
    private File file;
    private String tempDir = "/tmp";
    private static final String PERMISO_FIELD = "p";
    
    @Override
    public void init() {
        // Registra el detector de Mime en la aplicación.
        MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
        // Get the file location where it would be stored.
        filePath = getServletConfig().getInitParameter("docsDirectory") + "/";
        tempDir = getServletConfig().getInitParameter("tempDir");
        maxFileSize = new Integer(getServletConfig().getInitParameter("maxFileSize"));
        maxMemSize = new Integer(getServletConfig().getInitParameter("maxMemSize"));
        showInfo();
    }

    /**
     * Muestra en el log información sobre la configuración.
     */
    private void showInfo() {
        logger.info("filePath:", filePath);
        logger.info("tempDir:", tempDir);
        logger.info("maxFileSize:", maxFileSize);
        logger.info("maxMemSize:", maxMemSize);
    }
    
    private ColumnasPermisos getPermisoRequest(HttpServletRequest request, List fileItems)
            throws UnknownColumnException {
        ColumnasPermisos permiso = null;
        // TODO: obtener el permiso no por parámetro sino por "referer".
//        try {
//            permiso = seguridad.
//                    getPermisoLink(request.getHeader("referer"));
//        } catch (Exception e) {
//            logger.info("No es posible obtener el referer.");
//        }
//        if (permiso == null) {
            Iterator i = fileItems.iterator();            
            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();                
                if (fi.isFormField() && fi.getFieldName().equals(PERMISO_FIELD)) {
                    permiso = seguridad.getPermisoLink(new String(fi.get()));
                }
            }            
//        }
        return permiso;
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
        // Check that we have a file upload request
        isMultipart = ServletFileUpload.isMultipartContent(request);
        FileDataBase fdb = null;
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();        
        try {            
            CSession cSession = (CSession) request.getSession().getAttribute("cSession");
            
            if (!isMultipart) {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet upload</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<p>No file uploaded</p>");
                out.println("</body>");
                out.println("</html>");
                return;
            }
            
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // maximum size that will be stored in memory
            factory.setSizeThreshold(maxMemSize);
            // Location to save data that is larger than maxMemSize.
            factory.setRepository(new File(tempDir));

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            // maximum file size to be uploaded.
            upload.setSizeMax(maxFileSize);

            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);
            
            // Se verifica si el usuario tiene permisos de escritura.
            ColumnasPermisos permiso = checkPermisos(request, fileItems, out);
            if (permiso == null) {
                return;
            }

            // Process the uploaded file items
            Iterator i = fileItems.iterator();
            
            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                
                if (!fi.isFormField()) {
                    // Get the uploaded file parameters
                    logger.info("Prosess Request: obteniendo session: ["
                            + cSession + "] jsessionid = [" + request.getSession().getId() + "]", this);
                    fdb = new FileDataBase(GenKey.newKey(),
                            cSession.getAccessInfo().getEmpresa(),
                            cSession.getAccessInfo().getUserInfo(),
                            new Date().getTime(), fi.getName(),
                            new MimeType().factory().getMime(fi.getName(), fi.get()),
                            fi.get(), Hexadecimal.getHex(md5.digest(fi.get())),
                            fi.getSize(), permiso);
                    fileDataBaseFacade.create(fdb);
                }
            }
            ResponseConfig config = new ResponseConfig();
            config.setTimeZone(cSession.getAccessInfo().getTimeZone());
            out.println(fdb.toJson(config));
        } catch (FileUploadException | NoSuchAlgorithmException e) {
            logger.error("Error cargando el fichero en el servidor, "
                    + "Exception: {}", e.getMessage());
        } catch (UnknownColumnException e) {
            logger.error("Imposible la columna para el link debe existir referer[{}], "
                    + "p[{}]", request.getHeader("referer"),
                    request.getParameter(PERMISO_FIELD));
            logger.debug("referrer: " +  request.getHeader("referrer"));
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
        throw new ServletException("GET method used with "
                + getClass().getName() + ": POST method required.");
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

    private ColumnasPermisos checkPermisos(HttpServletRequest request, List fileItems,
            PrintWriter out) throws UnknownColumnException {
        ColumnasPermisos permiso = getPermisoRequest(request, fileItems);

        if (!(((CSession) request.getSession().getAttribute("cSession"))
                .getAccessInfo().getPermisosUsuario()
                .hasAccess(permiso, PermisosGrupos.ESCRITURA))) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet upload</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>No tiene permisos para cargar ficheros en el servidor.</p>");
            out.println("</body>");
            out.println("</html>");
            return null;
        }
        return permiso;
    }
}
