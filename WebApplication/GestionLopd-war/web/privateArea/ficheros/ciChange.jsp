<%-- 
    Document   : ciChange
    Created on : 08-abr-2013, 10:24:52
    Author     : Eduardo L. García Glez.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<jsp:useBean id="ciChange" class="com.openlopd.web.controllers.privatearea.ficheros.CiChange" scope="page" />
<jsp:setProperty name="ciChange" property="*" />
<jsp:setProperty name="ciChange" property="session" value="${cSession}" />
<c:out value="${ciChange.update}" />