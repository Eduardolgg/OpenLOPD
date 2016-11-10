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
                    <fmt:message var="nombreEmpresa" bundle="${StandardBundle}" key="nombreEmpresa" scope="page" />
                    <div id="appArea">
                        <h3><span>Benefíciate de los servicios para Partners de ${nombreEmpresa}</span></h3>
                        <p>Siendo partner de ${nombreEmpresa} podrás ofrecer contar y ofrecer servicios de alta calidad al mejor precio del mercado.</p>

                        <p>Servicios a los parters:</p>
                        <ul>
                            <li>Podrás ofrecer a tus clientes un servicio de valor añadido a bajo coste.</li>
                            <li>Podrás ofrecer a tus clientes los servicios estándar de ${nombreEmpresa}.</li>
                            <li>Podrás ofrecer servicios de consultoría desde una plataforma de tecnología puntera.</li>
                        </ul>

                        <p>Como ves existen varias modalidades, y todas ellas con beneficios para tu empresa, con la seguridad de tener un sistema siempre actualizado y disponible las 24 horas.</p>

                        <p>Pídenos información te contaremos más ventajas que obtendrás siendo partner de ${nombreEmpresa} y además podremos adaptar nuestros servicios a tus necesidades ${mailEmpresa}</p>

                    </div>
                </div>
            </div>

            <jsp:directive.include file="/WEB-INF/jspf/common/standard/pieweb.jspf" />

        </div>
        <div id="extraFooter">
        </div>
    </body>
</html>
