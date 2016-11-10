<%-- 
    Document   : incidencias
    Created on : 27-may-2012, 12:59:40
    Author     : Eduardo L. García Glez.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<c:if test="${!cSession.logged}">
    <jsp:forward page="../../publicArea/login.jsp" />
</c:if>
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />
<jsp:directive.include file="/WEB-INF/jspf/common/standard/formsTranslate.jspf" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%-- Verificación de permiso de lectura de esta página. --%>
<jsp:useBean id="cIncidencias" scope="request" class="com.openlopd.web.controllers.privatearea.incidencias.CIncidencias" />
<jsp:setProperty name="cIncidencias" property="session" value="${cSession}" />
<c:if test="${!cIncidencias.readable}">
    <jsp:forward page="/common/error.jsp?e=read" />
</c:if>

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/private/headSection.jspf" />
        <script type="text/javascript">  
            var onScreenHelp;
            
            function startOnScreenHelp() {                
                onScreenHelp = $("body").onScreenHelp(
                    [{	
                        selector : "#myTable",
                        description : "Esta tabla contiene el listado de incidencias que afectan a los datos de carácter personal que gestiona la empresa.",
                        position: "bottom",
                        startWith: true
                    },{	
                        selector : "#myTable_filter",
                        description : "Puedes buscar entre las indicencias a través de este cuadro.",
                        position: "bottom"
                    },{
                        selector : "#myTable tbody tr td .ui-icon-plus",
                        description : "Para modificar o añadir má datos a una incidencia puedes pulsar sobre este botón",
                        position: "left"
                    },{
                        selector : "#btnAddNewRow",
                        description : "Si quieres añadir una nueva incidencia pulsa este botón.",
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
            
            $(document).ready(function(){
               $(".dateTimePicker").datetimepicker(getDateTimePickerConfig());
               //$("#enlaceajaxulFirst").menu();
               $("#menuListadoTipoIncidencias").button({
                                                text: false,
                                                icons: {
                                                    primary: "ui-icon-plus"
                                                }
                                            }).click(function() {
                                                $('#ulListadoTiposIncidencias').show();
                                            });   
                                            
                    
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
               
               $.datepicker.setDefaults($.datepicker.regional['es']);
               var oTable = $('#myTable').dataTable({
                   "bJQueryUI": true,
                   "bServerSide": true,
                   "bDeferRender": true,
                   "bProcessing": true,
                   "sAjaxSource": "${appRoot}/privateArea/incidencias/jSonTable.jsp",
                   "sServerMethod": "POST",
                   "aaSorting": [[ 1, "desc" ]],
                   "sPaginationType": "full_numbers",
                   "aoColumnDefs": [ 
                         { "bSortable": false, "aTargets": [ 5 ] }
                         
                   ],
                   "oLanguage": {
                         "sUrl": "${libPath}/lang/dataTable_es_ES.txt"
                   },
                   // fnDrawCallback fnInitComplete
                   "fnDrawCallback": function() {
                       if (oTable != undefined) {
                            $('#myTable').find("tr").click( function () {
                              var data = oTable.fnGetData( this );
                              if (data != null) {
                                var extraInfo = getExtraInfo('#myTable', data.DT_RowId, this); 
                              }
                            });
                       }
                   }
               }).makeEditable({
                   aoTableActions: [{
                         sAction: "EditData",
                         sServerActionURL: "UpdateData.jsp",
                         oFormOptions: { autoOpen: false, modal:true, width: "450px" }
                   }],
                   aoColumns: [ null , null ,  null , null , null , null, null ],
                   sAddURL: "AddData.jsp",
                   sDeleteURL: "DeleteData.jsp",
                   sUpdateURL: "UpdateData.jsp",
                   oAddNewRowButtonOptions: {label: "${buttonAlta}", icons: {primary:'ui-icon-plus'}},
		   oDeleteRowButtonOptions: {label: "${buttonBorrar}", icons: {primary:'ui-icon-trash'}},
                   oAddNewRowFormOptions: {
                       minWidth: 450
                   }
               });
               
               $('.file_upload').uploadify({
                        'swf'      : '${libPath}/uploadify.swf',
                        'uploader' : '${appRoot}/upload;jsessionid=<%= request.getSession().getId() %>',
                        // Parámetros opcionales.
                        <%-- TODO:'checkExisting' : --%>
                        'buttonText' : 'Archivo',
                        'buttonClass': 'ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only',
                        'multi': false,
                        'fileSizeLimit': '5MB',  'formData': {'p' : '<%= request.getRequestURI() %>'},
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
                    
                    /*$("#btnAddNewRowOk").click(function() {
                        addFileInfo();
                    });*/
                    
                    $("#recupNivelMedio").change(function(){                                
                        if($("#recupNivelMedio:checked").val() == "ON"){
                            $("#nivelMedioFields").show('blind');
                        } else {
                            $("#nivelMedioFields").hide('blind');
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
                    <c:if test="${cIncidencias.writable}">
                        <%--<form id="formEditData" action="#" title="Update Platforms">
                            <input type="hidden" name="id" id="id" value="DATAROWID" />
                            <label for="descripcion">Descripción</label>
                            <input type="text" name="descripcion" id="descripcion" class="required" rel="0"/><br />                        
                            <label for="observaciones">Observaciones</label>
                            <input type="text" name="observaciones" id="observaciones" rel="1"/><br />                        
                            <label for="mostrarObs">Mostrar Observaciones</label>
                            <input type="text" name="mostrarObs" id="mostrarObs" rel="2"/><br />                        
                            <label for="fAlta">Fecha Alta</label>
                            <input type="text" name="fAlta" id="fAltae" class="required" rel="3"/><br />                        
                            <label for="fBaja">Fecha Baja</label>
                            <input type="text" name="fBaja" id="fBajae" rel="4"/><br />
                            <label for="fBajaa">Fecha Baja2</label>
                            <input type="text" name="fBajaa" id="fBajaa" rel="4"/><br />
                            <span class="datafield" style="display:none" rel="5"><a class="table-action-EditData">Edit</a></span>
                            <span class="datafield" style="display:none" rel="6"><a href="/Details/DT_RowId">Details</a></span>
                            <button id="formEditDataOk" type="submit">Ok</button>
                            <button id="formEditDataCancel" type="button">Cancel</button>
                        </form>--%>
                        <form id="formAddNewRow" action="#" title="Añadir nueva incidencia" class="tableDataForm">
                            <input type="hidden" name="id" id="id" value="DATAROWID" rel="0"/>
                            <label for="fIncidencia">Fecha</label>
                            <input type="text" name="fIncidencia" id="fIncidencia" class="dateTimePicker required" rel="1"/><br /> 
                            <label for="deteccion">En la fecha dada la incidencia fue:</label>
                            <select name="deteccion" class="required">
                                <option value="true">Detectada</option>
                                <option value="false">Producida</option>
                            </select><br />
                            <label for="tipoIncidencia">Tipo de Incidencia</label>
                                <button id="menuListadoTipoIncidencias">Añadir etiqueta de tipo de incidencia</button>
                            <div id="listadoTiposIncidencias">
                                <jsp:directive.include file="/WEB-INF/jspf/private/forms/tiposIncidencias.jspf" />
                            </div>
                            <textarea cols="80" rows="5" name="tipoIncidencia" id="tipoIncidencia" class="required"></textarea><br />
                            <label for="notificadoPor">Nombre del Notificante</label>
                            <input type="text" name="notificadoPor" id="notificadoPor" class="required" rel=""/><br />
                            <label for="notificadoA">Nombre del Receptor de la incidencia</label>                        
                            <input type="text" name="notificadoA" id="notificadoA" rel="2"/><br />
                            <label for="efectosDerivados">Efectos derivados de la Incidencia</label>
                            <textarea cols="80" rows="5" name="efectosDerivados"></textarea><br />
                            <label for="sistemasAfectados">Sistemas Afectados</label>
                            <textarea cols="80" rows="5" name="sistemasAfectados" rel="3"></textarea><br />
                            <label for="medidasCorrectoras">Medidas correctoras aplicadas</label>
                            <textarea cols="80" rows="5" name="medidasCorrectoras" rel="4"></textarea><br />
                            <span class="datafield" style="display:none" rel="5"><a href="/Details/DT_RowId">Details</a></span>
                            <c:if test="${cIncidencias.nivelMedioSystem}">
                                <br>
                                <input type="checkbox" name="recupNivelMedio" id="recupNivelMedio" value="ON" />
                                <label for="recupNivelMedio">Es una recuperación de datos en ficheros de nivel medio/alto</label><br><br>
                                <div id="nivelMedioFields" style="border:0;display: none">
                                    <%--<legend>Infomación de la Recuperación de datos</legend>
                                     <h1>Nivel Medio</h1> --%>
                                    <label for="personaEjecutora">Persona que ejecuta los procedimientos de recuperación de datos.</label>
                                    <textarea cols="80" rows="5" name="personaEjecutora"></textarea><br />
                                    <label for="datosRestaurados">Datos Restaurados.</label>
                                    <textarea cols="80" rows="5" name="datosRestaurados"></textarea><br />                        
                                    <label for="datosRestauradosManualmente">Datos Restaurados Manualmente.</label>
                                    <textarea cols="80" rows="5" name="datosRestauradosManualmente"></textarea><br />            
                                    <label for="protocoloUtilizado">Protocolo utilizado para la recuperación de datos.</label>
                                    <textarea cols="80" rows="5" name="protocoloUtilizado"></textarea><br />
                                    <c:url var="autorizacion" value="/privateArea/plantillas/plantillasDetail.jsp">
                                        <c:param name="nombre" value="AutorizacionRecuperacionDatos"/>
                                    </c:url>
                                    <p>La recuperación de datos de nivel medio requiere de la autorización
                                        del responsable del fichero, puedes descargar el modelo de
                                        autorización <a href="${autorizacion}" target="_blank" >aquí</a> y te recomendamos adjuntar
                                         posteriormente una copia en la incidencia al que puedes acceder
                                         pulsando el botón + en el listado de incidencias.
                                    </p>
                                    <%--<label for="descAuth">Descargar plantilla de autorización</label>
                                    <a href="http://localhost:8080/GestionLopd/privateArea/plantillas/plantillasDetail.jsp?nombre=AutorizacionRecuperacionDatos">download</a>
                                      
                                    <input type="hidden" name="fileid" id="fileid" class="fileid" value="" />
                                    <input type="file" name="file_upload" id="file_upload" class="file_upload" value="" />
                                    <div id="infofile" class="infofile ui-widget" style="display: none;">
                                        <div id="msgfile" class="msgfile ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
                                            
                                        </div>
                                    </div> --%>
                                </div>                                     
                            </c:if>
                        </form>
                    </c:if>
                    <%--<div class="add_delete_toolbar" ></div>--%>
                    <h1>Incidencias</h1>
                    <p>
                        Este es el registro de incidencias, en el se debe registrar cualquier anomalía que afecte o pudiera afectar a la seguridad de los datos, debes tener en cuenta que si la incidencia afecta datos de nivel medio y es necesaria una recuperación de datos será necesaria la autorización del responsable del fichero.
                    </p>
                    <table cellpadding="0" cellspacing="0" border="0" class="display" id="myTable">
                        <thead>
                            <tr>	
                                <th>id</th>
                                <th>Fecha</th>
                                <th>Recibida Por</th>
                                <th>Sistema Afectado</th>
                                <th>Medidas Correctoras</th>
                                <th>Details</th>
                            </tr>
                        </thead>
                        <tbody>		
                        </tbody>
                        <tfoot>
                            <tr>	
                                <th>id</th>
                                <th>Fecha</th>
                                <th>Recibida Por</th>
                                <th>Sistema Afectado</th>
                                <th>Medidas Correctoras</th>
                                <th>Details</th>
                            </tr>
                        </tfoot>
                    </table>
                    <c:if test="${cIncidencias.writable}">
                        <div class="ui-buttonset tableButtons">
                            <button id="btnAddNewRow">Add</button>
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