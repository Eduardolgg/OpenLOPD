<%-- 
    Document   : details
    Created on : 08-oct-2012, 20:19:07
    Author     : Eduardo L. García Glez.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<c:if test="${!cSession.logged}">
    <jsp:forward page="../../publicArea/login.jsp" />
</c:if>
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />
<jsp:directive.include file="/WEB-INF/jspf/common/standard/formsTranslate.jspf" />

<jsp:useBean id="cDetails" class="com.openlopd.web.controllers.privatearea.incidencias.CDetails" scope="request" />
<jsp:setProperty name="cDetails" property="*" />
<jsp:setProperty name="cDetails" property="session" value="${cSession}" />

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/private/headSection.jspf" />
        <script type="text/javascript"> 
            $(document).ready(function(){
                //$("formEditData").validate({lang:'es'});
                $(".dateTimePicker").datetimepicker(getDateTimePickerConfig());
                $("#menuListadoTipoIncidencias").button({
                                                text: false,
                                                icons: {
                                                    primary: "ui-icon-plus"
                                                }
                                            }).click(function() {
                                                $('#ulListadoTiposIncidencias').show();
                                            });   
                //$("#enlaceajaxulFirst").menu();
                $(".button").button();
                
                                    $(".tipoIncItem").click(function(event){
                                        event.preventDefault();
                                        $("#tipoIncidencia").val($("#tipoIncidencia").val() + 
                                            " [" + $(this).text() + "]");
                                        $('#ulListadoTiposIncidencias').hide();
                                    });
                                    
                                    $('#ulListadoTiposIncidencias').menu({
                                        my: "left top",
                                        at: "left bottom",
                                        of: this
                                    });
                                    $('#ulListadoTiposIncidencias').hide();
                                    
                $('#formEditData').ajaxForm(function() { 
                    showAppMsg("Operación completada."); 
                }); 
                
                $('.file_upload').uploadify({
                        'swf'      : '${libPath}/uploadify.swf',
                        'uploader' : '${appRoot}/upload;jsessionid=<%= request.getSession().getId() %>',
                        // Parámetros opcionales.
                        <%-- TODO:'checkExisting' : --%>
                        'buttonText' : 'Cargar/Cambiar',
                        'buttonClass': 'ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only',
                        'multi': false,
                        'fileSizeLimit': '5MB', 
                        <%-- //TODO: Buscar Solución a esta ruta absoluta. --%>
                        'formData': {'p' : '${appRoot}/privateArea/incidencias/incidencias.jsp'},                        
                        //'fileTypeDesc': 'Open Document',
                        //'fileTypeExts' : '*.odt',
                        'onUploadSuccess':function(file, data, response) {
                            var obj = $.parseJSON(data);
                            $(".msgfile").empty().append(
                                "<p><span class=\"ui-icon ui-icon-info\" style=\"float: left; margin-right: .3em;\"></span>"
                                + "Fichero añadido:<br>"
                                + file.name 
                                + " " + obj.size + " bytes"
                                + "<br> MD5: "+ obj.md5 + "</p>");
                            $(".fileid").val(obj.id);
                            $(".file_upload").hide('blind');
                            $(".infofile").show('slide');
                        }
                    }); 
                    
                    $("#btnAddNewRowOk").click(function() {
                        addFileInfo();
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
                    <%-- 
                         Esta es la variable de donde se recoge el destinatario 
                         Modificar.
                    --%>
                    <c:set var="d" scope="request" value="${cDetails.getDetails()}" />
                    <h1><c:out value="${d.codigo}" /></h1>
                    <form id="formEditData" action="./UpdateData.jsp" title="${titleActualizar}" class="tableDataForm detailsForm">
                        <input type="hidden" name="id" id="id" value="${d.id}" />
                        <label for="fIncidencia">Fecha</label>
                        <input type="text" name="fIncidencia" id="fIncidencia" class="dateTimePicker required" rel="1" value="${d.getDateTime(d.fechaIncidencia, cSession.accessInfo.timeZone)}"/><br /> 
                        <label for="deteccion">En la fecha dada la incidencia fue:</label>
                        <select name="deteccion" id="deteccion">
                            <c:choose>
                                <c:when test="${d.deteccion}">
                                    <c:set var="selected" value="selected=\"selected\"" />
                                </c:when>
                                <c:otherwise>
                                    <c:set var="selected" value="" />
                                </c:otherwise>
                            </c:choose>
                            <option value="true" ${selected}>Detectada</option>
                            <c:choose>
                                <c:when test="${!d.deteccion}">
                                    <c:set var="selected" value="selected=\"selected\"" />
                                </c:when>
                                <c:otherwise>
                                    <c:set var="selected" value="" />
                                </c:otherwise>
                            </c:choose>
                            <option value="false" ${selected}>Producida</option>
                        </select><br />
                        <label for="tipoIncidencia">Tipo de Incidencia</label>
                        <a id="menuListadoTipoIncidencias">Añadir etiqueta de tipo de incidencia</a>
                        <div id="listadoTiposIncidencias">
                            <jsp:directive.include file="/WEB-INF/jspf/private/forms/tiposIncidencias.jspf" />
                        </div>
                        <textarea cols="80" rows="5" name="tipoIncidencia" id="tipoIncidencia"><c:out value="${d.tipoIncidencia}" /></textarea><br />
                        <label for="notificadoPor">Nombre del Notificante</label>
                        <input type="text" name="notificadoPor" id="notificadoPor" value="${d.notificadoPor}" rel=""/><br />
                        <label for="notificadoA">Nombre del Receptor de la incidencia</label>                        
                        <input type="text" name="notificadoA" id="notificadoA" value="${d.notificadoA}" rel="2"/><br />
                        <label for="efectosDerivados">Efectos derivados de la Incidencia</label>
                        <textarea cols="80" rows="5" name="efectosDerivados" id="efectosDerivados"><c:out value="${d.efectosDerivados}" /></textarea><br />
                        <label for="sistemasAfectados">Sistemas Afectados</label>
                        <textarea cols="80" rows="5" name="sistemasAfectados" id="sistemasAfectados" rel="3"><c:out value="${d.sistemaAfectado}" /></textarea><br />
                        <label for="medidasCorrectoras">Medidas correctoras aplicadas</label>
                        <textarea cols="80" rows="5" name="medidasCorrectoras" id="medidasCorrectoras" rel="4"><c:out value="${d.medidasCorrectoras}" /></textarea><br />
			                        
                        <h1>Nivel Medio</h1>
                        <label for="personaEjecutora">Persona que ejecuta los procedimientos de recuperación de datos.</label>
                        <textarea cols="80" rows="5" name="personaEjecutora" id="personaEjecutora"><c:out value="${d.personaEjecutora}" /></textarea><br />
                        <label for="datosRestaurados">Datos Restaurados.</label>
                        <textarea cols="80" rows="5" name="datosRestaurados" id="datosRestaurados"><c:out value="${d.datosRestaurados}" /></textarea><br />                        
                        <label for="datosRestauradosManualmente">Datos Restaurados Manualmente.</label>
                        <textarea cols="80" rows="5" name="datosRestauradosManualmente" id="datosRestauradosManualmente"><c:out value="${d.datosRestauradosManualmente}" /></textarea><br />            
                        <label for="protocoloUtilizado">Protocolo utilizado para la recuperación de datos.</label>
                        <textarea cols="80" rows="5" name="protocoloUtilizado" id="protocoloUtilizado"><c:out value="${d.protocoloUtilizado}" /></textarea><br />
                        
                        <c:url var="autorizacion" value="/privateArea/plantillas/plantillasDetail.jsp">
                            <c:param name="nombre" value="AutorizacionRecuperacionDatos"/>
                        </c:url>
                        <p>La recuperación de datos de nivel medio requiere de la autorización
                            del responsable del fichero, puedes descargar el modelo de
                            autorización <a href="${autorizacion}" target="_blank" >aquí</a> y te recomendamos adjuntar
                            posteriormente una copia en la incidencia</p>
                        <p>            
                        <c:if test="${d.autorizacion != null}">
                            <label for="downloadFile">Acceso directo al fichero de autorización:</label>
                            <a id="downloadFile" href="${appRoot}/download?file=${d.autorizacion.id}">download</a>
                        </c:if> 
                        </p>
                        <input type="hidden" name="fileid" id="fileid" class="fileid required" value="${d.autorizacion.id}" rel="3"/>
                        <input type="file" name="file_upload" id="file_upload" class="file_upload required" value="" />
                        <div id="infofile" class="infofile ui-widget" style="display: none;">
                            <div id="msgfile" class="msgfile ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
                                
                            </div>
                        </div>
                        <%--<span class="datafield" style="display:none" rel="4"><a class="table-action-EditData">Edit</a></span>
			<span class="datafield" style="display:none" rel="5"><a href="/Details/DT_RowId">Details</a></span>--%>
                        <input class="button" type="submit" value="Enviar" />
                        <a class="button" href="./incidencias.jsp"><fmt:message bundle="${StandardBundle}" key="button.volver"/></a>
                    </form>                    
                </div>
            </div>
            <jsp:directive.include file="/WEB-INF/jspf/common/standard/pieweb.jspf" />
        </div>
        <div id="extraFooter">
        </div>
        <jsp:directive.include file="/WEB-INF/jspf/common/standard/extraDivs.jspf" />
    </body>
</html>
