<%-- 
    Document   : details
    Created on : 06-oct-2012, 20:01:26
    Author     : Eduardo L. García Glez.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<jsp:useBean id="cExtraInfo" class="com.openlopd.web.controllers.privatearea.laempresa.CExtraInfo" scope="request" />
<jsp:setProperty name="cExtraInfo" property="*" />
<jsp:setProperty name="cExtraInfo" property="session" value="${cSession}" />
<c:out escapeXml="false" value="${cExtraInfo.extraInfo}" />