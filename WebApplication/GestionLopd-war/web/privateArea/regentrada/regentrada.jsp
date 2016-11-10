<%-- 
    Document   : Registro de Entrada
    Created on : 16-jun-2012
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
<jsp:useBean id="cRegEntrada" class="com.openlopd.web.controllers.privatearea.regentrada.CRegistroEntrada" scope="page" />
<jsp:setProperty name="cRegEntrada" property="session" value="${cSession}" />
<c:if test="${!cRegEntrada.readable}">
    <jsp:forward page="/common/error.jsp?e=read" />
</c:if>

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/private/headSection.jspf" />
        <style type="text/css">
            .dataTables_filter {
                display: none;
            }
        </style>
        <script type="text/javascript"> 
            
            function addActions() {
                $("#infofile").hide('blind');
                $("#file_upload").show('blind');
                $("#msgfile").remove("Fichero añadido: " + file.name 
                        + " " + obj.size + " bytes"
                        + "<br> MD5: "+ obj.md5);
            }
            $(document).ready(function(){
               $(".dateTimePicker").datetimepicker(getDateTimePickerConfig());
               
               //$("#enlaceajaxulFirst").menu();
               var oTable = $('#myTable').dataTable({
                   "bJQueryUI": true,
                   "bServerSide": true,
                   "bDeferRender": true,
                   "bProcessing": true,
                   "sAjaxSource": "${appRoot}/privateArea/regentrada/jSonTable.jsp",
                   "sServerMethod": "POST",
                   "aaSorting": [[ 2, "desc" ]],
                   "sPaginationType": "full_numbers",
                   "aoColumnDefs": [                       
                         { "bSortable": false, "aTargets": [ 0,1,2,3,4,5 ] }
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
                   aoColumns: [ null , null ,  null , null , null, null, null, null ],
                   sAddURL: "AddData.jsp",
                   sDeleteURL: "DeleteData.jsp",
                   sUpdateURL: "UpdateData.jsp",
                   oAddNewRowButtonOptions: {label: "Alta", icons: {primary:'ui-icon-plus'}},
		   oDeleteRowButtonOptions: {label: "Borrar", icons: {primary:'ui-icon-trash'}},
                   oAddNewRowFormOptions: {
                       minWidth: 450
                   }
               });
                    
                    $('#file_upload').uploadify({
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
                            $("#msgfile").empty().append(
                                "<p><span class=\"ui-icon ui-icon-info\" style=\"float: left; margin-right: .3em;\"></span>"
                                + "Fichero añadido:<br>"
                                + file.name 
                                + " " + obj.size + " bytes"
                                + "<br> MD5: "+ obj.md5 + "</p>");
                            $("#fileid").val(obj.id);
                            $("#file_upload").hide('blind');
                            $("#infofile").show('slide');
                        }
                    }); 
                    
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
                <div id="appArea">
                    <c:if test="${cRegEntrada.writable}">
                        <form id="formAddNewRow" action="#" title="Añadir Registro de Entrada" class="tableDataForm">
                            <input type="hidden" name="id" id="id" value="DATAROWID" />
                            <label for="personaQueEntrega">Persona que entrega el soporte</label>
                            <input type="text" name="personaQueEntrega" class ="required" />
                            <label for="tipoSoporte">Tipo Soporte</label>
                            <select name="tipoSoporte" id="tipoSoporte" class="required" rel="0">
                                <option selected></option>
                                <c:forEach var="ts" items="${cSession.auxTables.auxTiposSoportes}">
                                    <option value="${ts.id}"><c:out value="${ts.descripcion}" /></option>
                                </c:forEach>
                            </select><br />
                            <%--<input type="text" name="tipoSoporte"/><br />--%>                        
                            <label for="observaciones">Observaciones</label>
                            <textarea name="observaciones" rows="4" cols="20" id="observaciones" rel="1"></textarea><br />
                            <label for="fEntrada">Fecha Entrada</label>
                            <input type="text" name="fEntrada" id="fEntrada" class="dateTimePicker required" rel="2"/><br />                        
                            <label for="cantidad">Número de Soportes</label>
                            <input type="text" name="cantidad" id="cantidad" class="required" rel="3"/><br />
                            <label for="tipoInformacion">Tipo Información</label>
                            <input type="text" name="tipoInformacion" id="tipoInformacion" class="required" rel="4"/><br />
                            <label for="destinatario">Persona autorizada para la recepción del soporte</label>
                            <select name="destinatario" id="destinatario" class="required" rel="5">
                                <option selected></option>
                                <c:forEach var="au" items="${cSession.auxTables.autorizadosEntrada}">
                                    <option value="${au.id}"><c:out value="${au.nombreCompleto}"/></option>
                                </c:forEach>
                            </select><br />
                            <label for="modoEnvio">Forma de envío</label>
                            <input type="text" name="modoEnvio" class="required" maxlength="255" />
                            <%--<input type="text" name="destinatario" id="destinatario" rel="5"/><br />--%> 
                            <span class="datafield" style="display:none" rel="6"><a class="table-action-EditData">Edit</a></span>
                            <span class="datafield" style="display:none" rel="7"><a href="/Details/DT_RowId">Details</a></span>
                        </form>
                        <%--<div class="add_delete_toolbar" ></div>--%>
                    </c:if>
                    <h1>Registro de entrada de Soportes</h1>
                    <p>Este es un listado de todos los soportes con datos de carácter personal recibidos por la empresa.</p>
                    <table cellpadding="0" cellspacing="0" border="0" class="display" id="myTable">
                        <thead>
                            <tr>	
                                <th>Tipo Soporte</th>
                                <th>Observaciones</th>
                                <th>Fecha Entrada</th>
                                <th>Cantidad</th>
                                <th>Tipo Información</th>
                                <th>Persona Autorizada</th>
                            </tr>
                        </thead>
                        <tbody>		
                        </tbody>
                        <tfoot>
                            <tr>
                                <th>Tipo Soporte</th>
                                <th>Observaciones</th>
                                <th>Fecha Entrada</th>
                                <th>Cantidad</th>
                                <th>Tipo Información</th>
                                <th>Persona Autorizada</th>
                            </tr>
                        </tfoot>
                    </table>
                    <c:if test="${cRegEntrada.writable}">
                        <div class="ui-buttonset tableButtons">
                            <button id="btnAddNewRow">Add</button>
                        </div>
                    </c:if><br>
                    <jsp:directive.include file="/WEB-INF/jspf/private/enlacesRelacionados/soportesLinksSecundarios.jspf" />
                </div>
            </div>
            <jsp:directive.include file="/WEB-INF/jspf/common/standard/pieweb.jspf" />
        </div>
        <div id="extraFooter">
        </div>
        <jsp:directive.include file="/WEB-INF/jspf/common/standard/extraDivs.jspf" />
    </body>
</html>