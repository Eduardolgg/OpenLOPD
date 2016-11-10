<%-- 
    Document   : setHome
    Created on : 09-ene-2013, 17:43:56
    Author     : Eduardo L. García Glez.
--%>

<%@page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<jsp:useBean id="cSetHome" class="com.openlopd.web.controllers.privatearea.cambiarempresa.CSetHome" scope="request" />
<jsp:setProperty name="cSetHome" property="*" />
<jsp:setProperty name="cSetHome" property="session" value="${cSession}" />

<c:choose>
    <c:when test="${cSetHome.cambiarEmpresa()}">        
        <jsp:forward page="listado.jsp?error=0" />
    </c:when>
    <c:otherwise>
        <jsp:forward page="listado.jsp?error=1" />
    </c:otherwise>
</c:choose>
