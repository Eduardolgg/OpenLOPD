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

package com.openlopd.business.interfaz;

import com.openlopd.business.seguridad.AccessInfo;
import com.openlopd.entities.empresas.Empresa;
import com.openlopd.entities.interfaz.LinkList;
import com.openlopd.entities.lopd.TipoProcedimiento;
import com.openlopd.entities.seguridad.base.BasePermisosGrupos;
import com.openlopd.sessionbeans.interfaz.LinkListFacadeLocal;
import com.openlopd.sessionbeans.lopd.TipoProcedimientoFacadeLocal;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Gestión de la lógina de negocio relacionada con la interfaz.
 *
 * @author Eduardo L. García Glez.
 */
@Stateless
public class Interfaz implements InterfazLocal {
    @EJB
    private TipoProcedimientoFacadeLocal tipoProcedimientoFacade;

    private static Logger logger = LoggerFactory.getLogger(Interfaz.class);
    @EJB
    private LinkListFacadeLocal linkListFacade;
    private static final ResourceBundle rb = ResourceBundle.getBundle("com.openlopd.config.config", Locale.getDefault());
    private static final String userId = rb.getString("userID");

    public Interfaz() {
    }
    
    protected String getLinksProcedimientos(Empresa empresa) {
        List <TipoProcedimiento> tiposProcedimientos = tipoProcedimientoFacade.getTipoProcedimientoActivo(empresa);
        StringBuilder links = new StringBuilder();
        links.append("<ul class=\"subLevelProc\">");
        for (TipoProcedimiento tp : tiposProcedimientos) {            
            links.append("<li><a href=\"")
                    .append(rb.getString("root"))
                    .append("/privateArea/procedimientos/procedimientos.jsp?tipo=")
                    .append(tp.getTipoProcedimientoPK().getId())
                    .append("\">")
                    .append(tp.getTitulo())
                    .append("</a></li>");
        }
        links.append("</ul>");
        return links.length() > 2 ? links.toString() : "";
    }

    protected String getParams(LinkList link) {
        String params = "";
        try {
            return params;
        } catch (Exception e) {
            logger.error("En getParams, imposible construir el String con"
                    + "los parámetros obtenidos de base de datos para contruir el "
                    + "link, exception: {}", e.getMessage());
            return "";
        }
    }

    @Override
    public String getLinkListByGroupString(AccessInfo accessInfo) {
        int level = 0;
        LinkList link;
        String html = "";
        List<LinkList> l = linkListFacade.findActives("es_ES"); //TODO hacer esto bien.
        try {
            Iterator i = l.iterator();
            while (i.hasNext()) {
                link = (LinkList) i.next();
                // TODO: Arreglar esta chapuza!!!!
                if (link.getName().equals("Gestión de Plantillas") &&
                        !accessInfo.getEmpresa().getCif().equals(userId)) {
                    continue;
                }
                if (!accessInfo.getPermisosUsuario()
                        .hasAccess(link.getPermiso(), BasePermisosGrupos.LECTURA)) {
                    continue;
                }
                if (level == link.getOrden().length()) {
                    html += "</li>";
                }
                if (level < link.getOrden().length()) {
                    html += "<ul>";
                    level += 2;
                }
                if (level > link.getOrden().length()) {
                    html += "</li></ul>";
                    level -= 2;
                }
                html += "<li><a href=\""
                        + link.getLink() + this.getParams(link) + "\" id=\"enlaceajax"
                        + link.getOrden() + "\" " + "class=\""
                        + (link.getLink().contains("http") ? "elink" : "enlaceajax" + level)
                        + "\">" + link.getName() + "</a>";
                //TODO: Esto puede fallar al internacionalizar.
                if (link.getName().equals("Procedimientos")) {
                    html += getLinksProcedimientos(accessInfo.getSubEmpresa());
                }
                if (!i.hasNext()) {
                    while (level != 0) {
                        html += "</li></ul>";
                        level -= 2;
                    }
                }
            }
            return html;
        } catch (Exception e) {
            logger.error("No se ha podido imprimir correctamente los "
                    + "enlaces Excepcion {}", e.getMessage());
            return "";
        }
    }
}
