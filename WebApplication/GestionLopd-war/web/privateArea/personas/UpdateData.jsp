<%-- 
    Document   : UpdateData
    Created on : 29-may-2012, 18:23:57
    Author     : Eduardo L. García Glez.
--%>
<%@page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<jsp:useBean id="cUpdateData" class="com.openlopd.web.controllers.privatearea.personas.CUpdateData" scope="request" />
<jsp:setProperty name="cUpdateData" property="*" />
<jsp:setProperty name="cUpdateData" property="session" value="${cSession}" />

<c:out value="${cUpdateData.update}" />
