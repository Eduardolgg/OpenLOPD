<%-- 
    Document   : ficheros
    Created on : 28-10-2012
    Author     : Eduardo L. García Glez.
--%>
<%@page import="com.openlopd.agpd.nota.tablascomunes.TipoSolicitud"%>
<%@page import="com.openlopd.agpd.nota.tablascomunes.Titularidad"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<c:if test="${!cSession.logged}">
    <jsp:forward page="../../publicArea/login.jsp" />
</c:if>
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />
<jsp:directive.include file="/WEB-INF/jspf/common/standard/formsTranslate.jspf" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%-- Verificación de permiso de lectura de esta página. --%>
<jsp:useBean id="cFicheros" class="com.openlopd.web.controllers.privatearea.ficheros.CFicheros" scope="page" />
<jsp:setProperty name="cFicheros" property="session" value="${cSession}" />
<c:if test="${!cFicheros.readable}">
    <jsp:forward page="/common/error.jsp?e=read" />
</c:if>

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/private/headSection.jspf" />
        <script type="text/javascript"> 
            $(document).ready(function(){
               var link; 
               
               
               $(".dateTimePicker").datetimepicker(getDateTimePickerConfig());
               $(".button").button();     
               
               $(".firmarFichero").click(function() {
                   link = "${appRoot}/privateArea/ficheros/firmar.jsp?id=" + $(this).attr("id");
                   $("#infoLegal").dialog("open");
               });
               
               $("#infoLegal").dialog({
                    resizable: false,
                    autoOpen: false,
                    width: '90%',
                    modal: true,
                    buttons: {
                        Firmar: function() {
                            window.location.href = link;
                        },
                        Cancelar: function() {
                            $( this ).dialog( "close" );
                        }
                    }
               });
               
            });
        </script>
    </head>
    <body>
        <div id="container">
            <div id="intro">
                <jsp:directive.include file="/WEB-INF/jspf/private/encabezado.jspf" />
                <div id="extraHeader">
                    <div id="hormigas">
                        <ul>
                            <li><a href="index.jsp"><fmt:message bundle="${StandardBundle}" key="label.inicio" /></a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div id="contentArea">
                <div id="ants">
                    <a href="${appRoot}/privateArea/privateIndex.jsp">Inicio</a>
                </div>
                <div id="status">
                    <jsp:directive.include file="/WEB-INF/jspf/private/status.jspf" />
                </div>
                <jsp:directive.include file="/WEB-INF/jspf/private/linkList.jspf" />
                <div id="appArea"> 
                    <h1>Ficheros pendientes de firmar</h1>
                    <c:if test="${fn:length(cFicheros.ficherosPendientesFirma) == 0}" >
                        <div style="margin-top: 20px; padding: 0 .7em;" class="nota ui-state-highlight ui-corner-all">
                            <p><span style="float: left; margin-right: .3em;" class="ui-icon ui-icon-info"></span>
                                No tienes ficheros pendientes de firma.</p>
                        </div>
                    </c:if>
                    <c:forEach var="f" items="${cFicheros.ficherosPendientesFirma}">
                        <div class="blockInfo">
                            <div class="text">
                                <h2><c:out value="${f.nombre}"/></h2>
                                <p><c:out value="${f.descripcion}" /></p>
                                <p><span class="titulo">Nivel: </span><c:out value="${f.nivel}" /></p>
                                <p><span class="titulo">Tipo: </span><c:out value="${f.tipo}" /></p>
                                <p><span class="titulo">Acción a realizar tras firmar: </span><c:out value="${f.accion}" /> del fichero en la AGPD.</p>
                                <p><span class="titulo">Tipo de datos: </span><c:out value="${f.tipoDatos}" /></p>
                                <p><span class="titulo">Origen de los datos que contiene el fichero: </span><c:out value="${f.origenDatos}" /></p>
                                <c:if test="${fn:length(f.cesiones) > 0}">                                    
                                    <p><span class="titulo">Cesiones de datos: </span><c:out value="${f.cesiones}" /></p>
                                </c:if>
                                <c:if test="${fn:length(f.transferenciasInternacionales) > 0}">
                                    <p><span class="titulo">Transferencias internacionales: </span>
                                        <c:out value="${f.transferenciasInternacionales}" /></p>
                                </c:if>
                            </div>
                            <div class="buttonPannel">
                                <button id="${f.id}" class="button firmarFichero">firmar</button>
                            </div>                            
                        </div>
                    </c:forEach>
                    <div id="infoLegal" title="Información Legal">
                        <p>De conformidad con lo establecido en la Ley Orgánica 15/1999, de 13 de diciembre, de Protección de Datos de Carácter Personal, solicito la inscripción en el Registro General de Protección de Datos del fichero de datos de carácter personal al que hace referencia el presente formulario de notificación. Asimismo, bajo mi responsabilidad manifiesto que dispongo de representación suficiente para solicitar la inscripción de este fichero en nombre del responsable del fichero y que éste está informado del resto de obligaciones que se derivan de la LOPD. Igualmente, declaro que todos los datos consignados son ciertos y que el responsable del fichero ha sido informado de los supuestos legales que habilitan el tratamiento de datos especialmente protegidos, así como la cesión y la transferencia internacional de datos. La Agencia Española de Protección de Datos podrá requerir que se acredite la representación de la persona que formula la presente notificación.</p>
                        <p>En cumplimiento del artículo 5 de la Ley 15/1999, por el que se regula el derecho de información en la recogida de los datos, se advierte de los siguientes extremos: Los datos de carácter personal, que pudieran constar en esta notificación, se incluirán en el fichero de nombre “Registro General Protección de Datos”, creado por Resolución del Director de la Agencia Española de Protección de Datos (AEPD) de fecha 28 de abril de 2006, (B.O.E. nº 117) por la que se crean y modifican los ficheros de datos de carácter personal existentes en la AEPD. La finalidad del fichero es velar por la publicidad de la existencia de los ficheros que contengan datos de carácter personal con el fin de hacer posible el ejercicio de los derechos de información, oposición, acceso, rectificación y cancelación de los datos. Los datos relativos a la persona física que presenta la notificación de ficheros y solicita su inscripción en el Registro General de Protección de Datos se utilizarán en los términos previstos en los procedimientos administrativos que sean necesarios para la tramitación de la correspondiente solicitud y posteriores comunicaciones con la AEPD. Tendrán derecho a acceder a sus datos personales, rectificarlos o, en su caso, cancelarlos en la AEPD, órgano responsable del fichero.</p>
                        <p>En caso de que en la notificación deban incluirse datos de carácter personal, referentes a personas físicas distintas de la que efectúa la solicitud o del responsable del fichero, deberá, con carácter previo a su inclusión, informarles de los extremos contenidos en el párrafo anterior.</p>
                    </div>
                    <c:if test="${cFicheros.writable}">
                        
                    </c:if>
                </div>
            </div>
            <jsp:directive.include file="/WEB-INF/jspf/common/standard/pieweb.jspf" />
        </div>
        <div id="extraFooter">
        </div>
        <jsp:directive.include file="/WEB-INF/jspf/common/standard/extraDivs.jspf" />
    </body>
</html>