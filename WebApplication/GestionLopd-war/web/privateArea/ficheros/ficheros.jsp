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
            var oTable = null;
            //$(document).tooltip(); Desactivado porque da error en los titulos de la etiqueta span.
            var onScreenHelp;
            
            function startOnScreenHelp() {                
                onScreenHelp = $("body").onScreenHelp(
                    [{	
                        selector : "#myTable",
                        description : "Esta tabla contiene los ficheros registrados o pendientes de registro en la Agencia Española de Protección de Datos.",
                        position: "top",
                        startWith: true
                    },{	
                        selector : "#alta",
                        description : "Este botón te permitirá acceder al formulario de alta de nuevos ficheros, la finalización del formulario producirá un envío a la Agencia de Protección de Datos.",
                        position: "top"
                    },{
                        selector : "#altaRegistrado",
                        description : "Este botón te permite añadir un fichero ya registrado, te envíará al formulario de alta pero al finalizarlo no realizará el envío a la Agencia de Protección de Datos",
                        position: "top"
                    },{
                        selector : "#addCiButton",
                        description : "Este botón te permite añadir el código de inscripción del fichero una ver que lo recibas por correo.",
                        position: "top"
                    },{
                        selector : "#ficherosPredefinidos",
                        description : "En este apartado se listan ficheros predefinidos, al pulsar sobre uno de ellos accederás al formulario de alta con el formulario configurado para dar de alta el fichero del tipo seleccionado.",
                        position: "top"
                    }],{
                        // global options
                        scrollAlways : false, // allways scroll to the next / prev step, can be overwritten through step's setting (default => true)
                        hideKeyCode : 27, // close button key (default => 27)
                        allowEventPropagation : true, //(default => true)
                        autoPagination: 20000,
                        closeOnEnd: true
                    });
            }
            
            function updateTable() {
                if ($(".ui-icon-refresh").length > 0) {
                  oTable.fnPageChange( 'first' );
                }
            }; 
            
            function addActions() {
                $("#infofile").hide('blind');
                $("#file_upload").show('blind');
                $("#msgfile").remove("Fichero añadido: " + file.name 
                        + " " + obj.size + " bytes"
                        + "<br> MD5: "+ obj.md5);
            }
            
            $(document).ready(function(){
                //$("formEditData").validate({lang:'es'});
               $(".dateTimePicker").datetimepicker(getDateTimePickerConfig());
               $(".button").button();
               
               $("#alta").click(function(){
                   
               });
               
               //$("#enlaceajaxulFirst").menu();
               oTable = $('#myTable').dataTable({
                   "bJQueryUI": true,
                   "bServerSide": true,
                   "bDeferRender": true,
                   "bProcessing": true,
                   "sAjaxSource": "${appRoot}/privateArea/ficheros/jSonTable.jsp",
                   "sServerMethod": "POST",
                   "aaSorting": [[ 3, "asc" ]],
                   "sPaginationType": "full_numbers",
                   // Definiendo el ancho de las celdas.
                   "aoColumns": [{sWidth:'25%'},{sWidth:'10%'},{sWidth:'15%'},{sWidth:'15%'},{sWidth:'15%'},
                       {sWidth:'10%'},{sWidth:'10%'},{sWidth:'10%'},{sWidth:'10%'}<%--,{sWidth:'10%'}--%>],
                   "aoColumnDefs": [ 
                         { "bSortable": false, "aTargets": [ 6,7,8<%--,9 --%>] }
                   ],
                   "oLanguage": {
                         "sUrl": "${libPath}/lang/dataTable_es_ES.txt"
                   },
                   // fnDrawCallback fnInitComplete
                   "fnDrawCallback": function() {
                       if (oTable != undefined) {
                            $('#myTable').find("tr").click( function () {
                              var data = oTable.fnGetData( this );
                              //var extraInfo = getExtraInfo('#myTable', data.DT_RowId, this); 
                            });
                       }
                   }
               });
               
               oTable.makeEditable({
                   aoTableActions: [{
                         sAction: "EditData",
                         sServerActionURL: "UpdateData.jsp",
                         oFormOptions: { autoOpen: false, modal:true, width: "450px" }
                   }],
                   aoColumns: [ null , null ,  null , null , null, null , null, null, null<%--, null--%>],
                   sAddURL: "AddData.jsp",
                   sDeleteURL: "DeleteData.jsp",
                   sUpdateURL: "UpdateData.jsp",
                   oAddNewRowButtonOptions: {label: "${buttonAlta}", icons: {primary:'ui-icon-plus'}},
		   oDeleteRowButtonOptions: {label: "${buttonBorrar}", icons: {primary:'ui-icon-trash'}},
                   oAddNewRowFormOptions: {
                       minWidth: 450
                   }
               });
               
              tableTimer = setInterval("updateTable()", 80000); 
                
                initCodInscripcionControl(oTable);
                
                $("#btnAddNewRowOk").click(function() {
                    //addActions();
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
                <div id="appArea"> <%--
                    <form id="formEditData" action="UpdateData.jsp" method="POST" title="${titleActualizar}">                        
                        <label for="nombre">Nombre</label>
                        <input type="hidden" name="id" id="id"  class="DT_RowId" />
                        <input type="text" name="nombre" id="nombre" class="required" maxlength="50" rel="0"/><br />                        
                        <label for="descripcion">Descripción</label>
                        <input type="text" name="descripcion" id="descripcion" class="required" maxlength="255" rel="1"/><br />
                        <label for="fAlta">Fecha Alta</label>
                        <input type="text" name="fAlta" id="fAltae" class="dateTimePicker required" rel="2"/><br />                        
                        <label for="fBaja">Fecha Baja</label>
                        <input type="text" name="fBaja" id="fBajae" class="dateTimePicker" rel="3"/><br />  
			
                        <span class="datafield" style="display:none" rel="4"><a class="table-action-EditData">Edit</a></span>
			<span class="datafield" style="display:none" rel="5"><a href="/Details/DT_RowId">Details</a></span>
                        <br><hr>
                        <button id="formEditDataOk" type="submit">
                            <c:out value="${buttonEnviar}" />
                        </button>
                        <button id="formEditDataCancel" type="button">
                            <c:out value="${buttonCancelar}" />
                        </button>                      
                    </form>
                    <form id="formAddNewRow" action="#" title="${titleActualizar}" class="tableDataForm">
                        <jsp:directive.include file="/WEB-INF/jspf/private/forms/destinatario.jspf" />
                    </form>
                    <div class="add_delete_toolbar" ></div>--%>
                    <h1>Ficheros</h1>
                    <p>En el Reglamento de Desarrollo de la LOPD se define un fichero como todo conjunto organizado de datos de carácter personal, que permita el acceso a los datos con arreglo a criterios determinados, cualquiera que fuere la forma o modalidad de su creación, almacenamiento, organización y acceso. Por este motivo muchos ficheros y bases de datos de tu sistema de información puede ser notificado como un único fichero en la Agencia.</p>
                    <p>En este apartado puedes realizar la notificación de los ficheros a la Agencia de Protección de datos de una manera rápida y sencilla, además <span style="color:#307082; font-weight: bold;">puedes utilizar los ficheros predefinidos que encontrarás bajo la tabla para facilitar aún más el proceso.</span></p>
                    <p>Ejemplos</p>
                    <ul>
                        <li>Si tienes personal a cargo puedes utilizar el fichero predefido Nóminas Personal y Recursos Humanos.</li>
                        <li>Si tienes instaladas cámaras de videovigilancia puedes utilizar el fichero predefinido Videovigilancia.</li>
                    </ul>
                    <c:if test="${fn:length(cFicheros.ficherosPendientesFirma) > 0}">
                        <div style="margin-top: 20px; padding: 0 .7em;" class="nota ui-state-highlight ui-corner-all">
                            <p><span style="float: left; margin-right: .3em;" class="ui-icon ui-icon-info"></span>
                                Tienes ficheros pendientes de firma. <a href="${appRoot}/privateArea/ficheros/firmaPendiente.jsp">Quiero verlos</a></p>
                        </div>
                    </c:if>
                    <c:if test="${!cFicheros.hasAllRegistrationCodes()}">
                        <div style="margin-top: 20px; padding: 0 .7em;" class="nota ui-state-highlight ui-corner-all">
                            <p><span style="float: left; margin-right: .3em;" class="ui-icon ui-icon-info"></span>
                                Tienes ficheros sin códigos de inscripción, cuando se registra un fichero en la AGPD
                                esta emite una carta a la dirección de notificación que indicaste en el formulario de registro
                                del fichero, una vez que recibas la carta debes indicar el código de inscripción en el fichero
                                correspondiente para que se habiliten las opciones de modificación y supresión.</p>
                        </div>
                    </c:if>
                    <table cellpadding="0" cellspacing="0" border="0" class="display" id="myTable">
                        <thead>
                            <tr>	
                                <th>Nombre</th>
                                <th>Nivel</th>
                                <th>Nº de Registro</th>
                                <th>Fecha Solicitud</th>
                                <th>Fecha Registro</th>
                                <th>Cod. Inscripción</th>
                                <th>Envío</th>
                                <th>Modificación</th>
                                <th>Supresión</th>
                                <%--<th>Detalles</th>--%>
                            </tr>
                        </thead>
                        <tbody>		
                        </tbody>
                        <tfoot>
                            <tr>
                                <th>Nombre</th>
                                <th>Nivel</th>
                                <th>Nº de Registro</th>
                                <th>Fecha Solicitud</th>
                                <th>Fecha Registro</th>
                                <th>Cod. Inscripción</th>
                                <th>Envío</th>
                                <th>Modificación</th>
                                <th>Supresión</th>
                                <%--<th>Detalles</th>--%>
                            </tr>
                        </tfoot>
                    </table>
                    <c:if test="${cFicheros.writable}">
                        <c:url var="notaUrl" value="nota.jsp">
                            <c:param name="titularidad" value="<%= Titularidad.PRIVADA.getValue()%>"/>
                            <c:param name="accion" value="<%= TipoSolicitud.ALTA.getValue()%>"/>
                            <c:param name="registrar" value="1"/>
                        </c:url>
                        <a class="button" id="alta" href="${notaUrl}">Alta de Fichero</a>
                        
                        <c:url var="notaUrl" value="nota.jsp">
                            <c:param name="titularidad" value="<%= Titularidad.PRIVADA.getValue()%>"/>
                            <c:param name="accion" value="<%= TipoSolicitud.ALTA.getValue()%>"/>
                            <c:param name="registrar" value="0"/>
                        </c:url>
                        <a class="button" id="altaRegistrado" href="${notaUrl}">Alta de Fichero Registrado</a>
                        <div id="addCiButton" class="button">
                            Añadir Código de Inscripción
                        </div>
                        <div id="ciDialog" title="Añadir código de inscripción" style="display: none">
                            <form id="ciForm" method="POST" action="./ciChange.jsp">
                                <label for="fichero">Fichero</label>
                                <input type="text" name="fichero" id="ciFichero" value="" readonly="readonly" class="required"/>
                                <label for="codInscripcion">Código de Inscripción</label>
                                <input type="text" name="codInscripcion" id="codInscripcion" value="" class="required" />
                            </form>
                        </div>
                        <br><br>
                        <div id="ficherosPredefinidos">
                            <fieldset>
                                <legend>Ficheros Predefinidos</legend>
                                <c:forEach var="f" items="${cFicheros.ficherosPredefinidos}">
                                    <c:url var="notaUrl" value="nota.jsp">
                                        <c:param name="titularidad" value="<%= Titularidad.PRIVADA.getValue()%>"/>
                                        <c:param name="accion" value="<%= TipoSolicitud.ALTA.getValue()%>"/>
                                        <c:param name="id" value="${f.id}"/>
                                        <c:param name="registrar" value="1" />
                                    </c:url>                                  
                                    <a title="${f.descripcion}" class="button  ficheroPredefinidoItem" id="alta" href="${notaUrl}"><c:out value="${f.nombre}"/></a>
                                </c:forEach>
                            </fieldset>
                        </div>
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