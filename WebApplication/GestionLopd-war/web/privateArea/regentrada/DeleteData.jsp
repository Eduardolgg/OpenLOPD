<%-- 
    Document   : DeleteData
    Created on : 29-may-2012, 18:23:57
    Author     : Eduardo L. García Glez.
--%>
<%@page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<jsp:useBean id="cDeleteData" class="com.openlopd.web.controllers.privatearea.regentrada.CDeleteData" scope="request" />
<jsp:setProperty name="cDeleteData" property="*" />
<jsp:setProperty name="cDeleteData" property="session" value="${cSession}" />
<c:out value="${cDeleteData.delete}" />