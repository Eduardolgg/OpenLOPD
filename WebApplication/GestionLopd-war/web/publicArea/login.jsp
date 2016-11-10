<%-- 
    Document   : login
    Created on : 22-feb-2011, 18:20:59
    Author     : Eduardo L. Garcia Glez.
    Versión    : 1.0.0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/common/standard/headSection.jspf" />
        <link rel="stylesheet" type="text/css" href="${cssPath}/logincontrol.min.css" />
        <link rel="stylesheet" type="text/css" href="${cssPath}/login.min.css" />
        <%--<script src="${libPath}/jquery.cookie.js"></script>
        <script type="text/javascript">
            
        </script>--%>
    </head>
    <body>
        <div id="container">
            <div id="intro">
                <jsp:directive.include file="/WEB-INF/jspf/common/standard/encabezado.jspf" />
                <div id="extraHeader">
                    <div id="hormigas">
                        <ul>
                            <li><a href="../index.jsp"><fmt:message bundle="${StandardBundle}" key="label.inicio" /> &gt;</a></li>
                            <li><a href="login.jsp"><fmt:message bundle="${StandardBundle}" key="label.login" /></a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div id="contentArea">
                <div id="contentRow">
                    <jsp:directive.include file="/WEB-INF/jspf/public/linkList.jspf" />
                    <div id="appArea">
                        <div id="appContent1">
                            <h3><span><fmt:message bundle="${StandardBundle}" key="title.areaPrivada" /></span></h3>
                            <div id="login">
                                <jsp:directive.include file="/WEB-INF/jspf/common/seguridad/loginControl.jspf" />
                                <a href="https://lopd.jkingii.es">Login en página segura</a>
                                <div id="alta">
                                    <h3><span>¿No estás registrado? date de alta aquí, no tiene ningún coste.</span></h3>
                                    <form action="alta.jsp" method="POST">
                                        <input type="hidden" name="tipoPaquete" value="5" />
                                        <input class="sendButton" type="submit" value="Quiero registrarme" />
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <jsp:directive.include file="/WEB-INF/jspf/common/standard/pieweb.jspf" />

        </div>
        <div id="extraFooter">
        </div>
    </body>
</html>
