<%-- 
    Document   : Plantillas
    Created on : 09-jun-2012
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
<jsp:useBean id="cPlantillas" class="com.openlopd.web.controllers.privatearea.plantillas.CPlantillas" scope="page" />
<jsp:setProperty name="cPlantillas" property="session" value="${cSession}" />
<c:if test="${!cPlantillas.readable}">
    <jsp:forward page="/common/error.jsp?e=read" />
</c:if>

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/private/headSection.jspf" />
        <script type="text/javascript">             
            
            $(document).ready(function(){
               
               $(".button").button();
               
               $(".table-action-EditData .ui-icon .ui-icon-pencil").on({
                   click : function() {
                        $("div #formEditData").parent().css("width","450px");
                   }
               });
        /*
               $(".table-action-EditData .ui-icon .ui-icon-pencil").click( function() {
                        $("div #formEditData").css("width","450px");
               });*/
               
               $(".dateTimePicker").datetimepicker(getDateTimePickerConfig());
               //$("#enlaceajaxulFirst").menu();
               $('#myTable').dataTable({
                   "bJQueryUI": true,
                   "bServerSide": true,
                   "bDeferRender": true,
                   "bProcessing": true,
                   "sAjaxSource": "${appRoot}/privateArea/plantillas/jSonTable.jsp",
                   "sServerMethod": "POST",
                   "aaSorting": [[ 5, "desc" ]],
                   "sPaginationType": "full_numbers",
                   "aoColumns": [ {sWidth:'15%'} , {sWidth:'45%'} ,  {sWidth:'5%'} , {sWidth:'5%'} , {sWidth:'10%'},{sWidth:'10%'}, {sWidth:'5%'}, {sWidth:'5%'} ],
                   "aoColumnDefs": [                       
                         { "bSortable": false, "aTargets": [ 3 ] }
                   ],
                   "oLanguage": {
                         "sUrl": "${libPath}/lang/dataTable_es_ES.txt"
                   }
               }).makeEditable({
                   aoTableActions: [{
                         sAction: "EditData",
                         sServerActionURL: "UpdateData.jsp",
                         oFormOptions: { autoOpen: false, modal:true, width: "550px" }
                   }],
                   aoColumns: [ null , null ,  null , null , null, null, null, null ],
                   sAddURL: "AddData.jsp",
                   sDeleteURL: "DeleteData.jsp",
                   sUpdateURL: "UpdateData.jsp",
                   oAddNewRowButtonOptions: {label: "Alta", icons: {primary:'ui-icon-plus'}},
		   oDeleteRowButtonOptions: {label: "Borrar", icons: {primary:'ui-icon-trash'}},
                   oAddNewRowFormOptions: {
                       minWidth: 550
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
                        'fileTypeDesc': 'Open Document',
                        'fileTypeExts' : '*.odt',
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
                    
                    $('.tbSpinner').spinner({
                        step: 0.01,
                        numberFormat: "n"
                    });
                    
                    $('.tbSpinner').click(function() {
                        if (!$(this).val()) {
                            $(this).val('1.00');
                        }
                    });
                    
            });
            <%-- Eliminado temporalmente porque no carga la información en
            el textarea --%>
            <%--tinymce.init({
                selector: "textarea",
                plugins: [
                        "advlist autolink lists link image charmap print preview anchor",
                        "searchreplace visualblocks code fullscreen",
                        "insertdatetime media table contextmenu paste"
                ],
                toolbar: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image"
            });--%>
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
                    <c:if test="${cPlantillas.writable}"> 
                        <form id="formEditData" action="UpdateData.jsp" method="POST" title="Actualizar plantilla">
                            <input type="hidden" name="id" id="id"  class="DT_RowId" />
                            <label for="nombre">Nombre</label>
                            <input type="text" name="nombre" id="nombre_e" rel="0"/><br />                        
                            <label for="descripcion">Descripción</label>
                            <textarea name="descripcion" id="udescripcion" class="required" rel="1" rows="4" cols="20"></textarea><br />
                            <label for="version">Versión</label>
                            <input type="text" name="version" id="version_e" class="tbSpinner required" rel="2"/>
                            <br />                    
                            <label for="fAlta">Fecha Alta</label>
                            <input type="text" name="fAlta" id="fAltae" class="dateTimePicker required" rel="4"/><br />                        
                            <label for="fBaja">Fecha Baja</label>
                            <input type="text" name="fBaja" id="fBajae" class="dateTimePicker" rel="5"/><br />
                            <%--<label for="formCode">Campos del formulario para cumplimentar la plantilla</label>
                            <textarea name="formCode" rows="4" cols="20"></textarea> <br />
                            <label for="operacion">Operación a ejecutar tras generar la plantilla</label>
                            <select name="operacion">
                                <option></option>
                                <option></option>
                            </select><br />--%><br />
                                
                            <input type="hidden" name="fileid" id="fileid_e" class="fileid required" value="NoSet" rel="3"/>
                            <input type="file" name="file_upload" id="file_upload_e" class="file_upload required" value="" /><br />
                            <div id="infofile_e" class="infofile ui-widget" style="display: none;">
                                <div id="msgfile_e" class="msgfile ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
                                    
                                </div>
                            </div>
                               
                            
                            <span class="datafield" style="display:none" rel="6"><a class="table-action-EditData">Edit</a></span>
                            <span class="datafield" style="display:none" rel="7"><a href="/Details/DT_RowId">Details</a></span>
                            <br><hr>
                            <button id="formEditDataOk" type="submit">
                                <c:out value="${buttonEnviar}" />
                            </button>
                            <button id="formEditDataCancel" type="button">
                                <c:out value="${buttonCancelar}" />
                            </button>                      
                        </form>
                        <form id="formAddNewRow" action="#" title="Añadir nueva plantilla" class="tableDataForm">
                            <input type="hidden" name="id" id="id" value="DATAROWID" />
                            <label for="nombre">Nombre</label>
                            <input type="text" name="nombre" id="nombre" class="required" rel="0"/><br />                        
                            <label for="descripcion">Descripción</label>
                            <textarea name="descripcion" id="descripcion" class="required" rel="1" rows="4" cols="20"></textarea><br />
                            <label for="version">Versión</label>
                            <input type="text" name="version" id="version" class="tbSpinner required" rel="2"/><br /> 
                            <label for="fAlta">Fecha Alta</label>
                            <input type="text" name="fAlta" id="fAlta" class="dateTimePicker required" rel="4"/><br />                        
                            <label for="fBaja">Fecha Baja</label>
                            <input type="text" name="fBaja" id="fBaja" class="dateTimePicker" rel="5"/><br />
                            <%--<label for="formCode">Campos del formulario para cumplimentar la plantilla</label>
                            <textarea name="formCode" rows="4" cols="20"></textarea> <br />
                            <label for="operacion">Operación a ejecutar tras generar la plantilla</label>
                            <select name="operacion">
                                <option></option>
                                <option></option>
                            </select><br />--%><br />
                                
                            <input type="hidden" name="fileid" id="fileid" class="fileid required" value="NoSet" rel="3"/>
                            <input type="file" name="file_upload" id="file_upload" class="file_upload required" value="" />
                            <div id="infofile" class="infofile ui-widget" style="display: none;">
                                <div id="msgfile" class="msgfile ui-state-highlight ui-corner-all" style="margin-top: 20px; padding: 0 .7em;">
                                    
                                </div>
                            </div>
                            <span class="datafield" style="display:none" rel="6"><a class="table-action-EditData">Edit</a></span>
                            <span class="datafield" style="display:none" rel="7"><a href="/Details/DT_RowId">Details</a></span>
                        </form>
                    </c:if>
                    <h1>Plantillas</h1>
                    <p>Estas son las plantillas desde las cuales se generan todos los documentos del sistema, esta es un área delicada, por lo que tienes que tener cuidado con los cambios que realices ya que puedes dejas sin funcionamiento partes de la aplicación.</p>
                    <p>Si realizas cambios aquí y tienes problemas durante el uso de la aplicación ponte en contacto con los administradores del sistema.</p>
                    <table cellpadding="0" cellspacing="0" border="0" class="display" id="myTable">
                        <thead>
                            <tr>	
                                <th>Nombre</th>
                                <th>Descripción</th>
                                <th>Versión</th>
                                <th>Documento</th>
                                <th>Fecha Alta</th>
                                <th>Fecha Baja</th>
                                <th>Edit</th>
                                <th>Details</th>
                            </tr>
                        </thead>
                        <tbody>		
                        </tbody>
                        <tfoot>
                            <tr>
                                <th>Nombre</th>
                                <th>Descripción</th>
                                <th>Versión</th>
                                <th>Documento</th>
                                <th>Fecha Alta</th>
                                <th>Fecha Baja</th>
                                <th>Edit</th>
                                <th>Details</th>
                            </tr>
                        </tfoot>
                    </table>
                    <c:if test="${cPlantillas.writable}">                     
                        <div class="add_delete_toolbar" ></div>
                    </c:if>
                    <%--<c:if test="${!cSession.accessInfo.empresa.equals(cSession.accessInfo.subEmpresa)}">
                        <button class="button">Insertar Plantillas de Sistema</button>
                    </c:if>--%>
                </div>
            </div>
            <jsp:directive.include file="/WEB-INF/jspf/common/standard/pieweb.jspf" />
        </div>
        <div id="extraFooter">
        </div>
        <jsp:directive.include file="/WEB-INF/jspf/common/standard/extraDivs.jspf" />
    </body>
</html>