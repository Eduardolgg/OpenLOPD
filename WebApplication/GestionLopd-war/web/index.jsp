<%-- 
    Document   : index
    Created on : 09-ene-2011, 13:48:43
    Author     : Eduardo L. Garcia Glez.
    Versión    : 1.0.0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%--<c:redirect url="/publicArea/login.jsp" />--%>
<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/common/standard/headSection.jspf" />
        <link rel="stylesheet" type="text/css" href="${cssPath}/logincontrol.min.css" />
        <link rel="stylesheet" type="text/css" href="${cssPath}/index.min.css" />
    </head>
    <body>
        <div id="container">
            <div id="indexLogin">
                <jsp:directive.include file="/WEB-INF/jspf/common/seguridad/loginControl.jspf" />
            </div>
            <div id="intro">
                <jsp:directive.include file="/WEB-INF/jspf/common/standard/encabezado.jspf" />
                <div id="extraHeader">
                    <div id="hormigas">
                        <ul>
                            <li><a href="index.jsp"><fmt:message bundle="${StandardBundle}" key="label.inicio" /></a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div id="contentArea">
                <div id="contentRow">
                    <jsp:directive.include file="/WEB-INF/jspf/public/linkList.jspf" />
                    <div id="appArea">
                        <div id="appContent1"><%--
                            <h3><span>-------------</span></h3>
                            <p class="p1"><span>-----------</span></p>--%>
                            <h3>¿Qué es lopd.jkingii.es?</h3>
                            <p>Es una aplicación que te ayuda en la gestión de la LOPD en tu empresa, y con 
                            ella podrás notificar tus ficheros a la Agencia Española de Protección de Datos,
                            generar tu documento de seguridad de una manera rápida y sencilla, gestionar incidencias, etc.</p>
                            <h3>Empezar a utilizar lopd.jkingii.es es muy fácil</h3>
                            <p>Sólo necesitas tener instalado y configurado un navegador de última generación como 
                                <a href="http://www.mozilla.org/es-ES/firefox/new/">Firefox</a>, 
                                <a href="http://www.google.es/intl/es/chrome/browser/">Chrome</a>, 
                                <a href="http://www.opera.com/es-es">Opera</a> o  
                                <a href="http://windows.microsoft.com/es-es/internet-explorer/download-ie">Internet Explorer 11</a> y si quieres 
                                registrar tus ficheros
                                desde la aplicación necesitarás un certificado digital de la <a href="http://www.cert.fnmt.es/">FMNT</a> y tener el plugin de 
                                <a href="http://www.java.com/es/download/installed.jsp">java</a> instalado en tu navegador.</p>
                            <p>En lopd.jkingii.es hemos creado un avanzado sistema de gestión que te ayuda a registrar
                            tus ficheros y mantener actualizado tu Documento de Seguridad. Hacer tu Documento de 
                            Seguridad es muy fácil ya que sólo tendrás que seguir los pasos que la aplicación te indica,
                            y además podrás seleccionar entre las múltiples politicas de seguridad que hemos añadido
                            a la aplicación para que cualquier persona pueda hacerlo.</p>
                            <div id="caracteristicas">
                                <h3>Características</h3>
                                <ul>
                                    <li>1. Asistente paso a paso</li>
                                    <li>2. Personalización de políticas de seguridad</li>
                                    <li>3. Generación e histórico de documentos de seguridad</li>
                                    <li>4. Alta/Modificación/Supresión de ficheros de titularidad privada directamente con la AGPD</li>
                                    <li>5. Ficheros predefinidos</li>
                                    <li>6. Listado de personal con acceso a datos</li>
                                    <li>7. Registro de Incidencias</li>
                                    <li>8. Inventario de Soportes</li>
                                    <li>9. Registro de entrada/salida de soportes</li>
                                    <li>10. Contratos y Cláusulas informativas para incluir en la documentación de la empresa.</li>
                                </ul>
                                <p>¡Y un largo etc!</p>
                            </div>
                            <p>Aquí tienes unas capturas de pantalla para que puedas ver la aplicación y decidirte a 
                            dar el paso.</p>
                            <div id="capturas" style="text-align: center;">
                                <img src="./images/captura01.png" /><br><br>
                                <img src="./images/captura02.png" />
                            </div>
                            <div id="alta">
                                <h3><span>No pierdas la oportunidad y date de alta ahora, no tiene ningún coste.</span></h3>
                                <form action="publicArea/alta.jsp" method="POST" style="text-align: center;">
                                    <input type="hidden" name="tipoPaquete" value="5" />
                                    <input class="sendButton" type="submit" value="Quiero registrarme" />
                                </form>
                            </div>
                            <div id="contacto">
                                <fmt:message bundle="${StandardBundle}" key="mailContacto"  var="mailContacto" scope="page"/>
                                Para cualquier consulta puede ponerse en contacto con nosotros en 
                                <a href="mailto:${mailContacto}"><c:out value="${mailContacto}" /></a>
                            </div>
                            <%--<div id="productos">
                                <ul>
                                    <li id="producto1">
                                        <a href="./publicArea/alta.jsp?tipoPaquete=0">
                                            <img src="./images/productos/producto1.png" alt="Paquete Básico" />
                                        </a>
                                    </li>
                                    <li id="producto2">
                                        <a href="./publicArea/alta.jsp?tipoPaquete=1">
                                            <img src="./images/productos/producto2.png" alt="Paquete Avanzado" />
                                        </a></li>
                                    <li id="producto3">
                                        <a href="./publicArea/alta.jsp?tipoPaquete=2">
                                            <img src="./images/productos/producto3.png" alt="Paquete Premium" />
                                        </a></li>
                                    <li id="producto4">
                                        <a href="./publicArea/alta.jsp?tipoPaquete=3">
                                            <img src="./images/productos/producto4.png" alt="Paquete Custom" />
                                        </a>
                                    </li>
                                </ul>
                            </div>--%>
                        </div>
                    </div>
                </div>
            </div>
            <jsp:directive.include file="/WEB-INF/jspf/common/standard/pieweb.jspf" />
        </div>
        <div id="extraFooter">
        </div>
        <%-- Utilizarlos para añadir información extra --%>
        <div id="extraInfo1"><span></span></div>
        <div id="extraInfo2"><span></span></div>
        <div id="extraInfo3"><span></span></div>
    </body>
</html>
