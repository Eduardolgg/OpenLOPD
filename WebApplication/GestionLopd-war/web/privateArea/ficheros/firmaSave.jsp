<%-- 
    Document   : firmaSave
    Created on : 21-abr-2013, 13:17:43
    Author     : edu
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<jsp:useBean id="cFirmaSave" class="com.openlopd.web.controllers.privatearea.ficheros.CFirmaSave" scope="request" />
<jsp:setProperty name="cFirmaSave" property="*" />
<jsp:setProperty name="cFirmaSave" property="session" value="${cSession}" />

<c:out value="${cFirmaSave.save}" />
<c:redirect url="ficheros.jsp" />