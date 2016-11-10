<%-- 
    Document   : Registro de Accesos
    Created on : 02-abr-2013
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
<jsp:useBean id="cRegAcceso" class="com.openlopd.web.controllers.privatearea.regaccesos.CRegistroAcceso" scope="page" />
<jsp:setProperty name="cRegAcceso" property="session" value="${cSession}" />
<c:if test="${!cRegAcceso.readable}">
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
               
                $("#usuario").autocomplete({
                    source: "../info/info.jsp?i=personas",
                    minLength: 2,
                    select: function(event, ui) {
                        $("#idPersona").val(ui.item.id);
                        $("#usuario").val(ui.item.label);
                        return false;
                    },
                    search: function(event, ui) {
                        $("#idPersona").val("");
                    }
                });
               
               //$("#enlaceajaxulFirst").menu();
               var oTable = $('#myTable').dataTable({
                   "bJQueryUI": true,
                   "bServerSide": true,
                   "bDeferRender": true,
                   "bProcessing": true,
                   "sAjaxSource": "${appRoot}/privateArea/regaccesos/jSonTable.jsp",
                   "sServerMethod": "POST",
                   "aaSorting": [[ 1, "desc" ]],
                   "sPaginationType": "full_numbers",
                   "aoColumns": [{sWidth:'35%'},{sWidth:'15%'},{sWidth:'35%'},{sWidth:'10%'}, {sWidth:'5%'}],
                   /*"aoColumnDefs": [                       
                         { "bSortable": false, "aTargets": [ 3 ] }
                   ],*/
                   "oLanguage": {
                         "sUrl": "${libPath}/lang/dataTable_es_ES.txt"
                   },
                   // fnDrawCallback fnInitComplete
                   "fnDrawCallback": function() {
                       /*if (oTable != undefined) {
                            $('#myTable').find("tr").click( function () {
                              var data = oTable.fnGetData( this );
                              if (data != null) {
                                var extraInfo = getExtraInfo('#myTable', data.DT_RowId, this); 
                              }
                            });
                       }*/
                   }
               }).makeEditable({
                   aoTableActions: [{
                         sAction: "EditData",
                         sServerActionURL: "UpdateData.jsp",
                         oFormOptions: { autoOpen: false, modal:true, width: "450px" }
                   }],
                   aoColumns: [ null , null ,  null , null , null, null, null ],
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
                    <c:if test="${cRegAcceso.writable}">
                        <form id="formAddNewRow" action="#" title="Añadir Registro" class="tableDataForm">
                            <input type="hidden" name="id" id="id" value="DATAROWID" />
                            <label for="usuario">Usuario</label>
                            <input type="text" name="usuario" id="usuario" class ="required" rel="0"/>
                            <input type="hidden" name="idPersona" id="idPersona" value=""/>
                            <label for="fechaAcceso">Fecha acceso</label>
                            <input type="text" name="fechaAcceso" class ="dateTimePicker required" rel="1"/><br />
                            
                            <label for="fichero">Fichero accedido</label>
                            <select  name="fichero" id="fichero" class ="required" rel="2">
                                <option></option>
                                <c:forEach var="f" items="${cRegAcceso.listFicheros}">
                                    <option><c:out value="${f.nombre}"/></option>
                                </c:forEach>
                            </select>
                            <br />
                            <label for="tipoAcceso">Tipo de acceso</label>
                            <select name="tipoAcceso" id="tipoAcceso" class="required" rel="3">
                                <option></option>
                                <c:forEach var="t" items="${cRegAcceso.listTipoAccesoDoc}">
                                    <option><c:out value="${t}"/></option>
                                </c:forEach>
                            </select>
                            <label for="autorizado">Se ha permitido el acceso al documento</label>
                            <input type="radio" name="autorizado" id="autorizado" class="required" value="SI" rel="4"/><br />
                            <label for="denegado">Se ha denegado el acceso al documento</label>
                            <input type="radio" name="autorizado" id="denegado" class="required" value="NO" rel="4"/><br />
                        </form>
                        <%--<div class="add_delete_toolbar" ></div>--%>
                    </c:if>
                    <h1>Registro de Accesos a Datos de Nivel Alto</h1>
                    <p></p>
                    <table cellpadding="0" cellspacing="0" border="0" class="display" id="myTable">
                        <thead>
                            <tr>
                                <th>Usuario</th>
                                <th>Fecha</th>
                                <th>Fichero</th>
                                <th>Tipo Acceso</th>
                                <th>Autorizado</th>
                            </tr>
                        </thead>
                        <tbody>		
                        </tbody>
                        <tfoot>
                            <tr>
                                <th>Usuario</th>
                                <th>Fecha</th>
                                <th>Fichero</th>
                                <th>Tipo Acceso</th>
                                <th>Autorizado</th>
                            </tr>
                        </tfoot>
                    </table>
                    <c:if test="${cRegAcceso.writable}">
                        <div class="ui-buttonset tableButtons">
                            <button id="btnAddNewRow">Add</button>
                        </div>
                    </c:if><br>
                    </div>
            </div>
            <jsp:directive.include file="/WEB-INF/jspf/common/standard/pieweb.jspf" />
        </div>
        <div id="extraFooter">
        </div>
        <jsp:directive.include file="/WEB-INF/jspf/common/standard/extraDivs.jspf" />
    </body>
</html>