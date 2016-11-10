<%-- 
    Document   : admin
    Created on : 14-dic-2012, 17:24:12
    Author     : edu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<c:if test="${!cSession.logged}">
    <jsp:forward page="../../publicArea/login.jsp" />
</c:if>
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />
<jsp:directive.include file="/WEB-INF/jspf/common/standard/formsTranslate.jspf" />

<%-- Verificación de permiso de lectura de esta página. --%>
<jsp:useBean id="cAdmin" class="com.openlopd.web.controllers.privatearea.admin.CAdmin" scope="request" />
<jsp:setProperty name="cAdmin" property="*" />
<jsp:setProperty name="cAdmin" property="session" value="${cSession}" />
<c:if test="${!cAdmin.readable}">
    <jsp:forward page="/common/error.jsp?e=read" />
</c:if>





<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/private/headSection.jspf" />
        <script type="text/javascript">            
            $(document).ready(function(){
                //$("formEditData").validate({lang:'es'});
               //$(".dateTimePicker").datetimepicker(getDateTimePickerConfig());
               
               
                $(".button").button();
            });
        </script>
    </head>
    <body>
        <div id="container">
            <div id="intro">
                <jsp:directive.include file="/WEB-INF/jspf/private/encabezado.jspf" />
                <div id="extraHeader">
                    <div id="hormigas">
                        <ul>
                            <li><a href="index.jsp"><fmt:message bundle="${StandardBundle}" key="label.inicio" /></a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div id="contentArea">
                <div id="ants">
                    <a href="${appRoot}/privateArea/privateIndex.jsp">Inicio</a>
                </div>
                <div id="status">
                    <jsp:directive.include file="/WEB-INF/jspf/private/status.jspf" />
                </div>
                <jsp:directive.include file="/WEB-INF/jspf/private/linkList.jspf" />
                <div id="appArea"> 
                    <div><a href="./admin.jsp" class="button">Reload</a></div>
                    <div id="output"><c:out value="${cAdmin.runOp}" /></div>
                    <hr>
                    <form action="admin.jsp?op=actualizarListOperaciones" method="POST" title="">
                        Añadir las nuevas operaciones a todas las empresas: 
                        <button id="actualizarOperaciones" type="submit" class="button">
                            Actualizar Operaciones
                        </button>                    
                    </form>
                     
                    <form action="admin.jsp?op=insertarPlantillas" method="POST" title="">
                        <c:if test="${!cSession.accessInfo.empresa.equals(cSession.accessInfo.subEmpresa)}">
                        Hacer copia de mis plantillas a la empresa actual: 
                            <button id="insertarPlantillas" type="submit" class="button">
                                Insertar Plantillas de Sistema
                            </button>
                        </c:if>                   
                    </form>
                    
                    <form action="admin.jsp?op=cambiarRegistroFicherosAuto" method="POST" title="">
                        Registro Fichero auto: 
                        <c:choose>
                            <c:when test="${cAdmin.autoRegistroActivo}"><span class="no-error">ON</span></c:when>
                            <c:otherwise><span class="error">OFF</span></c:otherwise>
                        </c:choose>
                        <button id="cambiarRegistroFicherosAuto" type="submit" class="button">
                            Cambiar Configuración Registro ficheros auto
                        </button>            
                    </form>
                    
                    <form action="admin.jsp?op=cambiarMailingSendStatus" method="POST" title="">
                        Envío mails estado: 
                        <c:choose>
                            <c:when test="${cAdmin.mailingActivo}"><span class="no-error">ON</span></c:when>
                            <c:otherwise><span class="error">OFF</span></c:otherwise>
                        </c:choose>
                        <button id="cambiarMailingSendStatus" type="submit" class="button">
                            Cambiar Configuración Envío e-mails
                        </button>            
                    </form>
                    <hr>
                    <div id="appStatusInfo">
                        <p>Ficheros con error: <c:out value="${cAdmin.numFicherosConError}" /> <span>Los ficheros con error no serán notificados, hay que quitar la marca de error de base de datos.</span></p>
                        <p>Ficheros por Notificar: <c:out value="${cAdmin.numFicherosPorNotificar}" /></p>
                        <p>Ficheros en el próximo ciclo de registro: <c:out value="${cAdmin.numFicherosPorNotificar - cAdmin.numFicherosConError}" /></p>
                        <table style="border: 1px solid #999999">
                            <thead>
                                <tr>
                                    <th style="border: 1px solid #999999">CIF</th>
                                    <th style="border: 1px solid #999999">FileID</th>
                                    <th style="border: 1px solid #999999">Nombre</th>
                                    <th style="border: 1px solid #999999">Error</th>
                                    <th style="border: 1px solid #999999">Solicitud</th>
                                    <th style="border: 1px solid #999999">Respuesta</th>
                                </tr>
                            </thead>
                            <tbody>                                
                                <c:forEach var="file" items="${cAdmin.ficherosConError}">
                                    <tr>
                                        <td  style="border: 1px solid #999999"><c:out value="${file.empresa.cif}" /></td>
                                        <td  style="border: 1px solid #999999"><c:out value="${file.id}" /></td>
                                        <td style="border: 1px solid #999999"><c:out value="${file.empresa.nombre}" /></td>
                                        <td style="border: 1px solid #999999"><c:out value="${cAdmin.getFileErrorDesc(file.error)}" /></td>
                                        <td style="border: 1px solid #999999"><a href="/OpenLopd/download?file=${file.solicitud.id}">Solicitud xml</a></td>
                                        <td style="border: 1px solid #999999"><a href="/OpenLopd/download?file=${file.respuesta.id}">Response xml</a></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <p>Mails con error: <c:out value="${cAdmin.numMailsConError}" /></p>
                        <hr>
                        <div>
                            <p>Estado:</p>
                            <p>Directorio temporal: 
                                <c:choose>
                                    <c:when test="${cAdmin.estadoTmpDir}">
                                        <span class="no-error">OK</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="error">No encontrado</span>
                                    </c:otherwise>
                                </c:choose>
                            </p>
                            <div>
                                <form action="admin.jsp?op=generarDocumento" method="POST" title="">
                                    Generador de documentos:
                                    <c:choose>
                                        <c:when test="${cAdmin.docGenerado == true}">
                                            <span class="no-error">OK</span>
                                        </c:when>
                                        <c:when test="${cAdmin.docGenerado == false}">
                                            <span class="error">No puedo generar</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="error">Desconocido</span>
                                        </c:otherwise>
                                    </c:choose>
                                    <button id="generarDocumento" type="submit" class="button">Generar</button>
                                </form>
                            </div>
                        </div>
                        <hr>
                        <p>Número de usuarios en el sistema: <c:out value="${cAdmin.numUsuariosOnline}" /></p>
                        <p>Usuarios conectados: 
                            <c:forEach var="s" items="${cAdmin.usuariosOnline}">
                                <c:out value="${s.usuario}" />, 
                            </c:forEach>...
                        </p>
                        <p>Últimos usuarios que estubieron online: 
                            <jsp:useBean id="usersAccessDateTime" class="java.util.Date" scope="page" />
                            <c:forEach var="s" items="${cAdmin.ultimosUsuariosOnline}">
                                <jsp:setProperty name="usersAccessDateTime"  property="time" value="${s.ultimoAcceso}" />                                
                                <c:out value="${s.usuario}" /> (<fmt:formatDate value="${usersAccessDateTime}" pattern="dd-MM-yyyy HH:mm" />), 
                            </c:forEach>...
                        </p>
                    </div>
                </div>
            </div>
            <jsp:directive.include file="/WEB-INF/jspf/common/standard/pieweb.jspf" />
        </div>
        <div id="extraFooter">
        </div>
        <jsp:directive.include file="/WEB-INF/jspf/common/standard/extraDivs.jspf" />
    </body>
</html>