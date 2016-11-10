<%-- 
    Document   : genDS
    Created on : 16-sep-2012, 12:38:49
    Author     : edu
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />

<c:choose>
    <c:when test="${!cSession.logged}">
        {"status": "Error", "message": "No tiene acceso al sistema"}
    </c:when>
    <c:otherwise>
        <jsp:useBean id="cGenDS" class="com.openlopd.web.controllers.privatearea.documentoseguridad.CGenDS" scope="request" />
        <c:out escapeXml="false" value="${cGenDS.createNuevoDS(cSession.accessInfo)}"/>
    </c:otherwise>
</c:choose>



