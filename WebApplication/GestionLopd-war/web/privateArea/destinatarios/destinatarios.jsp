<%-- 
    Document   : destinatarios
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
<jsp:useBean id="cDestinatarios" class="com.openlopd.web.controllers.privatearea.destinatarios.CDestinatarios" scope="page" />
<jsp:setProperty name="cDestinatarios" property="session" value="${cSession}" />
<c:if test="${!cDestinatarios.readable}">
    <jsp:forward page="/common/error.jsp?e=read" />
</c:if>

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/private/headSection.jspf" />
        <script type="text/javascript"> 
                
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
               //$("#enlaceajaxulFirst").menu();
               var oTable = $('#myTable').dataTable({
                   "bJQueryUI": true,
                   "bServerSide": true,
                   "bDeferRender": true,
                   "bProcessing": true,
                   "sAjaxSource": "${appRoot}/privateArea/destinatarios/jSonTable.jsp",
                   "sServerMethod": "POST",
                   "aaSorting": [[ 5, "desc" ]],
                   "sPaginationType": "full_numbers",
                   // Definiendo el ancho de las celdas.
                   "aoColumns": [{sWidth:'25%'},{sWidth:'45%'},{sWidth:'10%'},{sWidth:'10%'},{sWidth:'5%'}, {sWidth:'5%'}],
                   "aoColumnDefs": [ 
                         { "bSortable": false, "aTargets": [ 4,5 ] }
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
               });
               
               oTable.makeEditable({
                   aoTableActions: [{
                         sAction: "EditData",
                         sServerActionURL: "UpdateData.jsp",
                         oFormOptions: { autoOpen: false, modal:true, width: "450px" }
                   }],
                   aoColumns: [ null , null ,  null , null , null, null ],
                   sAddURL: "AddData.jsp",
                   sDeleteURL: "DeleteData.jsp",
                   sUpdateURL: "UpdateData.jsp",
                   oAddNewRowButtonOptions: {label: "${buttonAlta}", icons: {primary:'ui-icon-plus'}},
		   oDeleteRowButtonOptions: {label: "${buttonBorrar}", icons: {primary:'ui-icon-trash'}},
                   oAddNewRowFormOptions: {
                       minWidth: 450
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
                    <c:if test="${cDestinatarios.writable}">
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
                    </c:if>
                    <h1>Destinatarios</h1>
                    <p>En este apartado se listan los destinatarios a los que está autorizado el envío de soportes.</p>
                    <table cellpadding="0" cellspacing="0" border="0" class="display" id="myTable">
                        <thead>
                            <tr>	
                                <th>Nombre</th>
                                <th>Descripción</th>
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
                                <th>Fecha Alta</th>
                                <th>Fecha Baja</th>
                                <th>Edit</th>
                                <th>Details</th>
                            </tr>
                        </tfoot>
                    </table>
                    <c:if test="${cDestinatarios.writable}">
                        <div class="add_delete_toolbar" ></div>
                    </c:if><br>
                    <jsp:directive.include file="/WEB-INF/jspf/private/enlacesRelacionados/soportesLinksPrincipales.jspf" />
                </div>
            </div>
            <jsp:directive.include file="/WEB-INF/jspf/common/standard/pieweb.jspf" />
        </div>
        <div id="extraFooter">
        </div>
        <jsp:directive.include file="/WEB-INF/jspf/common/standard/extraDivs.jspf" />
    </body>
</html>