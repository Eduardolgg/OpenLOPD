<%-- 
    Document   : AddData
    Created on : 03-abr-2013
    Author     : Eduardo L. García Glez.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<jsp:useBean id="cAddData" class="com.openlopd.web.controllers.privatearea.regaccesos.CAddData" scope="request" />
<jsp:setProperty name="cAddData" property="*" />
<jsp:setProperty name="cAddData" property="session" value="${cSession}" />

<c:set var="resp" value="${cAddData.id}" />
<c:choose>
    <c:when test="${resp.isEmpty()}">
        <% response.sendError(response.SC_BAD_REQUEST, "Error en la petición: Verifique que el campo Usuario esta cumplimentado con el nombre de una persona dada de alta en la aplicación"); %>
    </c:when>
    <c:otherwise>
        <c:out value="${resp}" />
    </c:otherwise>
</c:choose>