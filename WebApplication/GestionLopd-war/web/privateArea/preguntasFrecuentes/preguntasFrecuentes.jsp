<%-- 
    Document   : procedimientos
    Created on : 27-sep-2012, 16:55:21
    Author     : Eduardo L. García Glez.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<c:if test="${!cSession.logged}">
    <jsp:forward page="../../publicArea/login.jsp" />
</c:if>
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%-- Verificación de permiso de lectura de esta página. --%>
<jsp:useBean id="cPreguntasFrecuentes" scope="request" class="com.openlopd.web.controllers.privatearea.CPreguntasFrecuentes" />
<jsp:setProperty name="cPreguntasFrecuentes" property="*" />
<jsp:setProperty name="cPreguntasFrecuentes" property="session" value="${cSession}" />
<c:if test="${!cPreguntasFrecuentes.readable}">
    <jsp:forward page="/common/error.jsp?e=read" />
</c:if>

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/private/headSection.jspf" />
        
        <script type="text/javascript">             
            $(document).ready(function(){                    
                $(document).tooltip(getTooltipConfig());
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
                    <h1>Preguntas Frecuentes</h1><br>
                    <c:forEach var="p" items="${cPreguntasFrecuentes.preguntasFrecuentes}">
                        <div class="pfDiv">
                            <h2 class="pfPregunta"><c:out escapeXml="false" value="${p.pregunta}" /></h2>
                            <div class="pfRespuesta">
                                <c:out escapeXml="false" value="${p.respuesta}" /> 
                            </div>                       
                        </div>
                    </c:forEach>
                </div>        
            </div>
            <jsp:directive.include file="/WEB-INF/jspf/common/standard/pieweb.jspf" />
        </div>
        <div id="extraFooter">
        </div>
        <jsp:directive.include file="/WEB-INF/jspf/common/standard/extraDivs.jspf" />
    </body>
</html>
