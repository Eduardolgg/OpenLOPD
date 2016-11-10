<%-- 
    Document   : AddData
    Created on : 29-may-2012, 18:23:57
    Author     : Eduardo L. García Glez.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<jsp:useBean id="cAddData" class="com.openlopd.web.controllers.privatearea.procedimientos.CAddData" scope="page" />
<jsp:setProperty name="cAddData" property="*" />
<jsp:setProperty name="cAddData" property="session" value="${cSession}" />
<c:out escapeXml="false" value="${cAddData.id}" />
