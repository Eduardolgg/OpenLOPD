<%-- 
    Document   : contratar
    Created on : 16-mar-2011, 22:07:27
    Author     : edu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />
<jsp:useBean id="InfoRegistroBean" scope="session" class="public_area.InfoRegistro" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <%-- Contratando!!!__ --%>
        <c:set var="infoContrato" value="${InfoRegistroBean.contratar}" />
        <c:choose>            
            <c:when test="${fn:startsWith(infoContrato, 'E0000')}">
                <jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
                <jsp:setProperty name="cSession" property="timeZone" param="timeZone" />
                <jsp:setProperty name="cSession" property="usuario" value="${InfoRegistroBean.cif}" />
                <jsp:setProperty name="cSession" property="password" value="${InfoRegistroBean.clave}" />
                <c:choose>
                    <c:when test="${cSession.validUser}">
                        ${InfoRegistroBean.logginOk()}
                    </c:when>
                    <c:otherwise>
                        ${InfoRegistroBean.logginErr()}
                    </c:otherwise>
                </c:choose>
                <fmt:message bundle="${StandardBundle}" key="texto.contrato.ok" />
            </c:when>
            <c:otherwise>
                <fmt:message bundle="${StandardBundle}" key="${infoContrato}" />
            </c:otherwise>
        </c:choose>        
    </body>
</html>
