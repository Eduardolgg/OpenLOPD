<%-- 
    Document   : notaSave
    Created on : 26-nov-2012, 19:36:15
    Author     : Eduardo L. García Glez.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<jsp:useBean id="cNotaSave" class="com.openlopd.web.controllers.privatearea.ficheros.CNotaSave" scope="request" />
<jsp:setProperty name="cNotaSave" property="*" />
<jsp:setProperty name="cNotaSave" property="session" value="${cSession}" />
<c:out value="${cNotaSave.save}" />
<c:choose>
    <c:when test="${!cNotaSave.firmar}">
        <c:url var="firmarLnk" value="firmar.jsp">
            <c:param name="id" value="${cNotaSave.fichero.id}"/>
        </c:url>
        <c:redirect url="${firmarLnk}" />
    </c:when>
    <c:otherwise>
        <c:redirect url="ficheros.jsp" />
    </c:otherwise>
</c:choose>