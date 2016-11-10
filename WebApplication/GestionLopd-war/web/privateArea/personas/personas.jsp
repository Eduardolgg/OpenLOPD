<%-- 
    Document   : personas
    Created on : 22-oct-2012
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
<jsp:useBean id="cPersonas" class="com.openlopd.web.controllers.privatearea.personas.CPersonas" scope="page" />
<jsp:setProperty name="cPersonas" property="session" value="${cSession}" />
<c:if test="${!cPersonas.readable}">
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
                        description : "En esta tabla debes añadir todas aquellas personas que trabajen en la empresa y que accedan a datos de carácter personal.",
                        position: "bottom",
                        startWith: true
                    },{	
                        selector : "#btnAddNewRow",
                        description : "Añade nuevas personas pulsado este botón.",
                        position: "top"
                    },{
                        selector : "#myTable tbody tr td .ui-icon-plus",
                        description : "Añade información extra o modifica la información actual pulsando el botón + ",
                        position: "left"
                    }],{
                        // global options
                        scrollAlways : false, // allways scroll to the next / prev step, can be overwritten through step's setting (default => true)
                        hideKeyCode : 27, // close button key (default => 27)
                        allowEventPropagation : true, //(default => true)
                        autoPagination: 20000,
                        closeOnEnd: true
                    });
            }
                
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
                   "sAjaxSource": "${appRoot}/privateArea/personas/jSonTable.jsp",
                   "sServerMethod": "POST",
                   "aaSorting": [[ 5, "desc" ]],
                   "sPaginationType": "full_numbers",
                   // Definiendo el ancho de las celdas.
                   "aoColumns": [{sWidth:'20%'},{sWidth:'20%'},{sWidth:'20%'},{sWidth:'10%'}, {sWidth:'10%'}, {sWidth:'10%'}],
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
                    <c:if test="${cPersonas.writable}"> <%--
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
                    </form>--%>
                        <form id="formAddNewRow" action="#" title="${titleActualizar}" class="tableDataForm">
                            <input type="hidden" name="id" id="id"  class="DT_RowId" />
                            <label for="nombre">Nombre</label>
                            <input type="text" name="nombre" id="nombre" class="required" maxlength="25" rel="0"/><br /> 
                            <label for="apellido1">Primer Apellido</label>
                            <input type="text" name="apellido1" id="apellido1" class="required" maxlength="25" rel="1"/><br /> 
                            <label for="apellido2">Segundo Apellido</label>
                            <input type="text" name="apellido2" id="apellido2" class="" maxlength="25" rel="2"/><br />
                            <label for="dni">DNI/NIE</label>
                            <input type="text" name="dni" id="dni" class="" maxlength="9" /><br />
                            <label for="fInicio">Fecha Inicio Prestación</label>
                            <input type="text" name="fInicio" id="fInicio" class="dateTimePicker required" maxlength="16" rel="3"/><br /> 
                            <label for="fFin">Fecha Fin Prestación</label>
                            <input type="text" name="fFin" id="fFin" class="dateTimePicker" maxlength="16" rel="4"/><br /> 
                            <%-- TODO: Buscar la forma de añadir usuarios de la app. --%>
                            <input type="hidden" name="usuario" id="usuario" value="" rel="5"/><br /> 
                            <span class="datafield" style="display:none" rel="6"><a href="/Details/DT_RowId">Details</a></span>
                                
                            <label for="perfil">Cargo</label>
                            <input type="text" name="perfil" id="perfil" maxlength="255" /><br /> 
                            <label for="telefono">Teléfono</label>
                            <input type="text" name="telefono" id="telefono" maxlength="15" /><br />                      
                            <label for="email">E-mail</label>
                            <input type="text" name="email" id="email" class="email" maxlength="255" /><br />
                            <%--<label for="perContacto">Es persona de contacto:</label>
                            <input type="checkbox" name="perContacto" value="on"/><br />--%>
                            <label for="autorizadoSalidaSoportes">Está autorizado para el envío de soportes:</label>
                            <input type="checkbox" name="autorizadoSalidaSoportes"  value="on"/><br />
                            <label for="autorizadoEntradaSoportes">Está autorizado para recepción de soportes:</label>
                            <input type="checkbox" name="autorizadoEntradaSoportes"  value="on"/><br />
                            <label for="autorizadoCopiaReproduccion">Está autorizado para copia/reproducción de documentos:</label>
                            <input type="checkbox" name="autorizadoCopiaReproduccion"  value="on"/><br />
                                
                        </form>
                    </c:if>
                    <h1>Personal con Acceso a Datos</h1>
                    <p>Este listado debe contener todo el personal de la empresa con acceso a datos de carácter personal, independientemente de si accede a sistemas automatizados o si solo accede a datos en papel.</p>
                    <table cellpadding="0" cellspacing="0" border="0" class="display" id="myTable">
                        <thead>
                            <tr>	
                                <th>Nombre</th>
                                <th>Apellido</th>
                                <th>Usuario</th>
                                <%--<th>Es Contacto</th>--%>
                                <th>Alta</th>
                                <th>Baja</th>
                                <th>Details</th>
                            </tr>
                        </thead>
                        <tbody>		
                        </tbody>
                        <tfoot>
                            <tr>	
                                <th>Nombre</th>
                                <th>Apellido</th>
                                <th>Usuario</th>
                                <%--<th>Es Contacto</th>--%>
                                <th>Alta</th>
                                <th>Baja</th>
                                <th>Details</th>
                            </tr>
                        </tfoot>
                    </table>
                       <c:if test="${cPersonas.writable}">
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