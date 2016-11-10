<%-- 
    Document   : textoPlantillas
    Created on : 11-nov-2012, 13:07:53
    Author     : Eduardo L. García Glez.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<jsp:useBean id="cExtraInfo" class="com.openlopd.web.controllers.privatearea.info.CInfo" scope="request" />
<jsp:setProperty name="cExtraInfo" property="*" />
<jsp:setProperty name="cExtraInfo" property="session" value="${cSession}" />
<c:out escapeXml="false" value="${cExtraInfo.info}" />