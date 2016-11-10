<%-- 
    Document   : 500
    Created on : 19-jun-2012, 18:29:06
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
        <title>Error 500</title>
        <style>
            #container {
                text-align: center;
                margin-top: 300px;
            }
            #h1 {
                display: none;
            }
        </style>
    </head>
    <body>
        <div id="container">
            <h1>Error 500</h1>
            <p>
                <fmt:message bundle="${StandardBundle}" key="E.500" />
            </p>
        </div>
    </body>
</html>
