<%-- 
    Document   : AddData
    Created on : 29-may-2012, 18:23:57
    Author     : Eduardo L. García Glez.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<jsp:useBean id="cAddData" class="com.openlopd.web.controllers.privatearea.modosdestruccion.CAddData" scope="request" />
<jsp:setProperty name="cAddData" property="*" />
<jsp:setProperty name="cAddData" property="session" value="${cSession}" />

<c:set var="resp" value="${cAddData.id}" />
<c:choose>
    <c:when test="${resp.isEmpty()}">
        <% response.sendError(response.SC_BAD_REQUEST, "Error al añadir"); %>        
    </c:when>
    <c:otherwise>
        <c:out value="${resp}" />
    </c:otherwise>
</c:choose>
