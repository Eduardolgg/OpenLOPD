
<%-- 
    Document   : LoginCheck.jsp
    Created on : 10-may-2012, 19:44:57
    Author     : Eduardo L. García Glez.
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<jsp:setProperty name="cSession" property="usuario" param="usuario" />
<jsp:setProperty name="cSession" property="password" param="password" />
<jsp:setProperty name="cSession" property="timeZone" param="timeZone" />
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />


<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestion LOPD - Validación de usuario</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${cSession.validUser}">
                <jsp:forward page="../privateArea/privateIndex.jsp"></jsp:forward>
            </c:when>
            <c:otherwise>
                <jsp:forward page="login.jsp"></jsp:forward>
            </c:otherwise>
        </c:choose>
    </body>
</html>
