<%-- 
    Document   : Update
    Created on : 25-mar-2013, 10:26:52
    Author     : edu
--%>

<%@page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<jsp:useBean id="cUpdate" class="com.openlopd.web.controllers.privatearea.laempresa.CUpdate" scope="request" />
<jsp:setProperty name="cUpdate" property="*" />
<jsp:setProperty name="cUpdate" property="session" value="${cSession}" />
<c:out value="${cUpdate.update}" />
