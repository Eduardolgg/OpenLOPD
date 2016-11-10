<%-- 
    Document   : UpdateData
    Created on : 29-may-2012, 18:23:57
    Author     : Eduardo L. García Glez.
--%>
<%@page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<jsp:useBean id="cUpdateData" class="com.openlopd.web.controllers.privatearea.modosdestruccion.CUpdateData" scope="request" />
<jsp:setProperty name="cUpdateData" property="*" />
<jsp:setProperty name="cUpdateData" property="session" value="${cSession}" />

<c:set var="resp" value="${cUpdateData.update}" />
<c:choose>
    <c:when test="${resp.isEmpty()}">
        <% response.sendError(response.SC_BAD_REQUEST, "Error actualizando"); %>        
    </c:when>
    <c:otherwise>
        <c:out value="${resp}" />
    </c:otherwise>
</c:choose>
