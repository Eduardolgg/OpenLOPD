<%-- 
    Document   : AddData
    Created on : 29-may-2012, 18:23:57
    Author     : Eduardo L. García Glez.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<jsp:useBean id="cAddData" class="com.openlopd.web.controllers.privatearea.cambiarempresa.CAddData" scope="page" />
<jsp:setProperty name="cAddData" property="*" />
<jsp:setProperty name="cAddData" property="session" value="${cSession}" />

<c:set var="resp" value="${cAddData.id}" scope="request" />
<c:set var="errorString" value="Error" scope="page" />
<c:choose>
    <c:when test="${fn:startsWith(resp, errorString)}">
        <% 
            String msg = (String) request.getAttribute("resp");
            response.sendError(response.SC_BAD_REQUEST, msg); 
        %>        
    </c:when>
    <c:otherwise>
        <c:out value="${resp}" />
    </c:otherwise>
</c:choose>
