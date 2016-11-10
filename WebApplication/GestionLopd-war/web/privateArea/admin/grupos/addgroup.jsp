<%-- 
    Document   : addgroup
    Created on : 01-ene-2013, 11:28:15
    Author     : Eduardo L. García Glez.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<jsp:useBean id="cAddGroup" class="com.openlopd.web.controllers.privatearea.admin.grupos.CAddGroup" scope="request" />
<jsp:setProperty name="cAddGroup" property="*" />
<jsp:setProperty name="cAddGroup" property="session" value="${cSession}" />
<c:choose>
    <c:when test="${cAddGroup.writable}">
        <c:out value="${cAddGroup.addGroup()}" />
        <jsp:forward page="grupos.jsp" />
    </c:when>
    <c:otherwise>
        <jsp:forward page="grupos.jsp?error=error" />
    </c:otherwise>
</c:choose>


