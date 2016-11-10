<%-- 
    Document   : infoRegistro
    Created on : 06-mar-2011, 10:53:31
    Author     : Eduardo L. García Glez.
    Versión    : 0.0.3
    Modificaciones:
         14 de mar de 2011 Muestra los importes trayendolos de DB
         15 de mar de 2011 Añadidos cambios para mostrar forma de pago.
         16 de mar de 2011 Control de errores de lado de servidor.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />
<jsp:useBean id="InfoRegistroBean" scope="session" class="public_area.InfoRegistro" />
<jsp:setProperty property="*" name="InfoRegistroBean" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/common/standard/headSection.jspf" />
        <link rel="stylesheet" type="text/css" href="${cssPath}/logincontrol.min.css" />
        <link rel="stylesheet" type="text/css" href="${cssPath}/index.min.css" />
        <script type="text/javascript" charset="utf-8">
            $(document).ready(function() {   
                $("#cntbtn").click(function(evento){
                    $("#cntbtn").css("display", "none");
                    evento.preventDefault();                    
                    $("#cargando").css("display", "inline");
                    $("#formSend").load("../common/ajax/contratar.jsp", {code: 1234, timeZone: getTimeZone()}, function(){
                        $("#cargando").css("display", "none");                        
                    });
                 });
            } );
        </script>
    </head>
    <body>
        <c:if test="${InfoRegistroBean.error}">
            <c:redirect url="./alta.jsp"/>
        </c:if>
        <div id="container">
            <div id="indexLogin">
                <jsp:directive.include file="/WEB-INF/jspf/common/seguridad/loginControl.jspf" />
            </div>
            <div id="intro">
                <jsp:directive.include file="/WEB-INF/jspf/common/standard/encabezado.jspf" />
                <div id="extraHeader">
                    <div id="hormigas">
                        <ul>
                            <li><a href="../index.jsp"><fmt:message bundle="${StandardBundle}" key="label.inicio" /> &gt;</a></li>
                            <li><a href="tarifas.jsp"><fmt:message bundle="${StandardBundle}" key="label.tarifas" /> &gt;</a></li>
                            <li><a href="alta.jsp"><fmt:message bundle="${StandardBundle}" key="label.alta" /></a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div id="contentArea">
                <div id="contentRow">
                    <jsp:directive.include file="/WEB-INF/jspf/public/linkList.jspf" />
                    <div id="appArea">
                        <div id="appContent1">
                            <h3>Datos de Alta</h3>
                            <p class="p1"><span>Por favor verifique que los datos son correctos y pulse continuar para finalizar el registro.</span></p>
                            <form action="infoRegistro.jsp" method="POST" id="form1">
                                <h3 style="display:none;">Tipo de Paquete</h3>
                                <%--<c:if test="${InfoRegistroBean.tipoPaquete > 2}">
                                    <c:redirect url="alta.jsp" />
                                </c:if>--%>
                                <p class="p1" style="display:none;"><b></b><c:out value="${InfoRegistroBean.tipoPaqueteDesc}" /></p>                                
                                <jsp:directive.include file="/WEB-INF/jspf/common/empresa/getDatosEmpresa.jspf" />
                                <jsp:directive.include file="/WEB-INF/jspf/common/personas/getPersonaContacto.jspf" />
                                <h3 style="display:none;">Forma de Pago</h3>
                                <p class="p1" style="display:none;"><b></b><fmt:message bundle="${StandardBundle}" key="${InfoRegistroBean.formaPagoDesc}" /></p>
                                <h3 style="display:none;">Importe Total</h3>
                                <p class="p1" style="display:none;"><b></b><c:out value="${InfoRegistroBean.importeContrato}" />€ (Impuestos Incluidos)</p>
                                <div id="formSend">
                                    <input id="cntbtn" class="sendButton" type="submit" value="Contratar" />
                                    <div id="cargando" style="display:none; color: green;">Cargando...</div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <jsp:directive.include file="/WEB-INF/jspf/common/standard/pieweb.jspf" />
        </div>
        <div id="extraFooter">
        </div>
        <%-- Utilizarlos para añadir información extra --%>
        <div id="extraInfo1"><span></span></div>
        <div id="extraInfo2"><span></span></div>
        <div id="extraInfo3"><span></span></div>
</body>
</html>

