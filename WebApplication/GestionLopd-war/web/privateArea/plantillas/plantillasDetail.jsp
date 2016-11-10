<%-- 
    Document   : plantillasDetail
    Created on : 22-sep-2012, 12:00:35
    Author     : Eduardo L. García Glez.
    Description:
        Muestra detalles de las plantillas.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<c:if test="${!cSession.logged}">
    <jsp:forward page="../../publicArea/login.jsp" />
</c:if>
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />

<jsp:useBean id="cPlantillasDetail" scope="request" class="com.openlopd.web.controllers.privatearea.plantillas.CPlantillasDetail" />
<jsp:setProperty name="cPlantillasDetail" property="*" />
<jsp:setProperty name="cPlantillasDetail" property="empresa" value="${cSession.accessInfo.subEmpresa}" />
<jsp:setProperty name="cPlantillasDetail" property="plantilla" value="${cPlantillasDetail.findPlantilla()}" />


<!DOCTYPE html>
<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/private/headSection.jspf" />
        <script type="text/javascript">
            function checkData(formData, jqForm, options) {
                appStatusLoading();
            }
            
            function showResponse(responseText, statusText, xhr, $form) {
                var data = $.parseJSON(responseText);                
                var htmlData;
                if (statusText == 'success') {
                            if (data.status != 'OK') {
                                showAppMsg("No es posible en este momento generar la plantilla "
                                        + "por favor intentelo más tarde.");
                                return 0;
                            }

                            htmlData = "<h2><a href=\"#\">" + data.uploadDate + "</a></h2>";
                            htmlData += "<div>";    
                            htmlData += "<p>id: " + data.id + "</p>";
                            htmlData += "<p>usuario: " + data.usuario + "</p>";
                            htmlData += "<p>uploadDate: " + data.uploadDate + "</p>";
                            htmlData += "<p>filename: " + data.filename + "</p>";
                            htmlData += "<p>mimeType: " + data.mimeType + "</p>";
                            htmlData += "<p>md5: " + data.md5 + "</p>";
                            htmlData += "<p>size: " + data.size + "Bytes</p>";
                            htmlData += "<a href=\"${appRoot}/download?file=" + data.id + "\">download</a>";
                            htmlData += "</div>";
                            //$("#accordion").children().before(htmlData);
                                    $("#accordion").html(htmlData + $("#accordion").html());
                                    
                                    
                            $( "#accordion" ).accordion( "destroy" ).accordion();
                            $( "#accordion" ).accordion( "destroy" ).accordion();
                            appStatusComplete();
                            
                            } else {
                                appStatusError();
                            }
                
            }
            
            $(document).ready(function (){
                $(".button").button();
                $("#accordion").accordion();
                $(".listadoPersonas").autocomplete({
                    source: "../info/info.jsp?i=personas",
                    minLength: 2
                });
                var options = {
                    beforeSubmit: checkData,
                    success: showResponse
                };
                $("#test").click(function(){
                    //TODO: Poder generar por ID así podemos generar las antiguas versiones.
                    $("#plantillaForm").ajaxSubmit(options);
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
                    <h1>Detalles del Documento</h1><br>
                    <c:if test="${ cPlantillasDetail.nombre == null }">
                        <h2>Nombre</h2>
                        <p><c:out value="${cPlantillasDetail.plantilla.nombre}" /></p>
                        <h2>Descripción</h2>
                    </c:if>
                    <p><c:out escapeXml="false" value="${cPlantillasDetail.plantilla.descripcion}" /></p>
                    <c:if test="${ cPlantillasDetail.nombre == null }">
                        <h2>Fecha Alta</h2>
                        <p><c:out value="${cPlantillasDetail.plantilla.getDateTime(cPlantillasDetail.plantilla.fechaAlta, cSession.accessInfo.timeZone)}" /></p>
                        <h2>Fecha Baja</h2>
                        <p><c:out value="${cPlantillasDetail.plantilla.getDateTime(cPlantillasDetail.plantilla.fechaBaja, cSession.accessInfo.timeZone)}" /></p>
                        <h2>Versión</h2>
                        <p><c:out value="${cPlantillasDetail.plantilla.version}" /></p>
                        <h2>File Name</h2>
                        <p><c:out value="${cPlantillasDetail.plantilla.documento.filename}" /></p>
                        <h2>MD5</h2>
                        <p><c:out value="${cPlantillasDetail.plantilla.documento.md5}" /></p>
                        <h2>MimeType</h2>
                        <p><c:out value="${cPlantillasDetail.plantilla.documento.mimeType}" /></p>
                    </c:if>
                    <form name="plantillaForm" action="${appRoot}/generation" id="plantillaForm" method="POST">
                        <input type="hidden" name="nombre" value="${cPlantillasDetail.plantilla.nombre}" readonly="readonly" />
                        <div id="contenidoDoc">
                            <%--<label for="textoPlantilla">Emitir este documento para:</label>
                            <input type="text" name="textoPlantilla" id="textoPlantilla" class="ui-autocomplete-input"/>--%>
                            <c:if test="${cPlantillasDetail.plantilla.form != null}" >
                                <fieldset>
                                    <legend>Datos necesarios para cumplimentar este documento</legend>
                                    <c:out escapeXml="false" value="${cPlantillasDetail.plantilla.form}" />
                                </fieldset>
                            </c:if>
                        </div>
                    </form>
                    <p>
                        <c:if test="${ cPlantillasDetail.nombre == null }">
                            <a class="button" href="${appRoot}/download?file=<c:out value="${cPlantillasDetail.plantilla.documento.id}" />">Descargar Plantilla</a>
                        </c:if>
                        <button class="button" id="test">Generar Documento</button>
                    </p>
                    <br>                    
                    <div id="accordion">
                    </div>
                </div>
            </div>
            <jsp:directive.include file="/WEB-INF/jspf/common/standard/pieweb.jspf" />
        </div>
        <div id="extraFooter">
        </div>
        <jsp:directive.include file="/WEB-INF/jspf/common/standard/extraDivs.jspf" />
    </body>
</html>
