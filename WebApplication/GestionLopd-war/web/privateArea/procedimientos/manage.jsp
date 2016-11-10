<%-- 
    Document   : manage
    Created on : 30-sep-2012, 11:36:33
    Author     : Eduardo L. García Glez.
--%>

<%@page contentType="text" pageEncoding="UTF-8"  trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<c:if test="${!cSession.logged}">
    <jsp:forward page="../../publicArea/login.jsp" />
</c:if>
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />

<jsp:useBean id="cManage" scope="request" class="com.openlopd.web.controllers.privatearea.procedimientos.CManage" />
<jsp:setProperty name="cManage" property="a" param="a" />
<jsp:setProperty name="cManage" property="idProc" param="p" />
<jsp:setProperty name="cManage" property="accessInfo" value="${cSession.accessInfo}" />
<c:out escapeXml="false" value="${cManage.status}" />
