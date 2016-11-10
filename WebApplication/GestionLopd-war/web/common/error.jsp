<%-- 
    Document   : error
    Created on : 30-dic-2012, 15:23:03
    Author     : Eduardo L. García Glez.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WARNING!!</title>
    </head>
    <body>
        <h1>WARNING!</h1>
        <p>No tiene permisos para realizar la operación, pulse el siguiente 
            enlace para volver al inicio de la aplicación.</p>
        <a href="${appRoot}/privateArea/privateIndex.jsp">Volver a Inicio</a>
    </body>
</html>
