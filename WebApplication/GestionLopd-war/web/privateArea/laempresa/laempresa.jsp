<%-- 
    Document   : laempresa
    Created on : 10-ene-2013, 19:11:52
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
<!DOCTYPE html>

<%-- Verificación de permiso de lectura de esta página. --%>
<jsp:useBean id="cLaEmpresa" class="com.openlopd.web.controllers.privatearea.laempresa.CLaEmpresa" scope="page" />
<jsp:setProperty name="cLaEmpresa" property="session" value="${cSession}" />
<c:if test="${!cLaEmpresa.readable}">
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
                        selector : "#enviarDatosIdentLaEmpresa",
                        description : "Cuando realices cambios en los datos generales de la empresa debes pulsar este botón para confirmarlos.",
                        position:"top",
                        startWith: true
                    },{
                        selector : "#myTable",
                        description : "En esta tabla debes añadir las direcciones en las que la empresa realiza el tratamiento de datos de carácter personal.",
                        position: "top"
                    },{
                        selector : "#btnAddNewRow",
                        description : "Puedes añadir nuevas direcciones pulsando este botón.",
                        position: "top"
                    },{
                        selector : "#myTable tbody tr td .ui-icon-plus",
                        html : 'También puedes modificar una dirección existente pulsando el botón + ',
                        position: "left"
                    }],{
                        // global options
                        scrollAlways : true, // allways scroll to the next / prev step, can be overwritten through step's setting (default => true)
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
            
            function prosessRequest(formData, jqForm, options) {
                var returnValue = true;
                $.each(formData, function(index, value) {
                    if(value.name == "idPersona" && value.value == "") {
                       <%--if(confirm("El responsable de seguridad no está dado de alta en el sistema, si pulsa aceptar se creará de forma automática.")) {
                           returnValue = true;
                       } else {
                           returnValue = false;
                       }--%>
                       alert("No se puede actualizar, el responsable de seguridad no existe como personal, debe primero darlo de alta en Gestión de Personal.");
                       returnValue = false;
                    }
                });
                return returnValue;
            }
            
            function showResponse(responseText, statusText, xhr, $form) {
                showAppMsg(responseText);
            }                
            
            $(document).ready(function(){
                //$("formEditData").validate({lang:'es'});
               $(".dateTimePicker").datetimepicker(getDateTimePickerConfig());
               //$("#enlaceajaxulFirst").menu();
               $(document).tooltip();
               
                $("#respSeguridad").autocomplete({
                    source: "../info/info.jsp?i=personas",
                    minLength: 2,
                    select: function(event, ui) {
                        $("#idPersona").val(ui.item.id);
                        $("#respSeguridad").val(ui.item.label);
                        return false;
                    },
                    search: function(event, ui) {
                        $("#idPersona").val("");
                    }
                });
                
                $("#sedePerContacto").autocomplete({
                    source: "../info/info.jsp?i=personas",
                    minLength: 2,
                    select: function(event, ui) {
                        $("#sedePerContactoidPersona").val(ui.item.id);
                        $("#sedePerContacto").val(ui.item.label);
                        return false;
                    },
                    search: function(event, ui) {
                        $("#sedePerContactoidPersona").val("");
                    }
                });
                
                $("#datosIdentLaEmpresa").ajaxForm({
                    beforeSubmit: prosessRequest,
                    success: showResponse
                });
                
               $(".button").button();
               var oTable = $('#myTable').dataTable({
                   "bJQueryUI": true,
                   "bServerSide": true,
                   "bDeferRender": true,
                   "bProcessing": true,
                   "sAjaxSource": "${appRoot}/privateArea/laempresa/jSonTable.jsp",
                   "sServerMethod": "POST",
                   "aaSorting": [[ 0, "asc" ]],
                   "sPaginationType": "full_numbers",
                   // Definiendo el ancho de las celdas.
                   "aoColumns": [{sWidth:'30%'},{sWidth:'55%'},{sWidth:'10%'},{sWidth:'5%'}],
                   "aoColumnDefs": [ 
                         { "bSortable": false, "aTargets": [ 3 ] }
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
                   aoColumns: [ null , null ,  null , null ],
                   sAddURL: "AddData.jsp",
                   sDeleteURL: "DeleteData.jsp",
                   sUpdateURL: "UpdateData.jsp",
                   oAddNewRowButtonOptions: {label: "${buttonAlta}", icons: {primary:'ui-icon-plus'}},
		   oDeleteRowButtonOptions: {label: "${buttonBorrar}", icons: {primary:'ui-icon-trash'}},
                   oAddNewRowFormOptions: {
                       minWidth: 550
                   }
               });
               
                $("#btnAddNewRowOk").click(function() {
                    //addActions();
                });
                
               cargarProvincias("${appRoot}");
               $("#destino").load("${appRoot}/common/ajax/localidades.jsp", {idProvincia: $("#provincia").val()}); 
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
                    <h1>Información General de La Empresa</h1>
                    <p>En este apartado puedes encontrar la información de la empresa, en la tabla que se encuentra
                    más abajo puedes indicar todos los establecimientos en los que la empresa realiza su actividad.</p>
                    <form id="datosIdentLaEmpresa" method="POST" action="Update.jsp">
                        <fieldset>
                            <legend>Empresa</legend>
                            <label for="cif">CIF/NIF</label>
                            <span title="Para cambiar el CIF/NIF póngase en contacto con nuestro servicio de atención al cliente." 
                                         class="help ui-icon ui-icon-lightbulb" 
                                         style="display: inline-block"></span>
                            <input type="text" name="cif" value="${cSession.accessInfo.subEmpresa.cif}" readonly="readonly"/>
                            <label for="razonSocial">Razón Social</label>
                            <input type="text" name="razonSocial" value="${cSession.accessInfo.subEmpresa.razonSocial}" />
                            <label for="nombre">Nombre</label>
                            <input type="text" name="nombre" value="${cSession.accessInfo.subEmpresa.nombre}" />
                            <label for="mailContacto">e-mail</label>
                            <input type="text" name="mailContacto" value="${cSession.accessInfo.subEmpresa.mailContacto}" />
                            <label for="respSeguridad">Responsable de Seguridad</label>
                            <input type="text" name="respSeguridad" id="respSeguridad" value="${cSession.auxTables.responsableSeguridad.nombreCompleto}" />
                            <input type="hidden" name="idPersona" id="idPersona" value="${cSession.accessInfo.subEmpresa.perContacto}" />
                            <label for="actividad">Actividad</label>
                            <select name="actividad">
                                <option></option>
                                <c:forEach var="actividad" items="${cSession.auxTables.tipoActividadPrincipal}">
                                    <c:choose>
                                        <c:when test="${actividad.descripcion.equals(cLaEmpresa.actividad)}">
                                            <option value="${actividad.id}" selected="selected">${actividad.descripcion}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${actividad.id}">${actividad.descripcion}</option>
                                        </c:otherwise>
                                    </c:choose>                                    
                                </c:forEach>
                            </select>                     
                            <input type="submit" id="enviarDatosIdentLaEmpresa" value="Enviar" name="send" class="button"/>
                        </fieldset>
                    </form><br>
                    <c:if test="${cLaEmpresa.writable}"><%--
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
                        <c:set var="se" scope="request" value="" />
                        <form id="formAddNewRow" action="#" title="${titleActualizar}" class="tableDataForm">
                            <input type="hidden" name="id" id="id" value="" class="DT_RowId" />
                        <label for="nombreSede">Nombre</label>
                        <input type="text" name="nombreSede" id="nombreSede" class="required" maxlength="100" value="" rel="0"/><br />
                        <label for="gestiona" title="Solo se puede establecer una sede de gestión LOPD.
                               Esta será utilizada en la generación de la documentación y envío de 
                               correspondencia. Si activa esta sede como principal se 
                               desactivará la que actualmente esté activa.">Sede principal de gestión LOPD</label>
                        <input type="checkbox" name="gestiona" id="gestiona" value="ON" /><br />
                        <label for="telefono">Teléfono</label>
                        <input type="text" name="telefono" id="telefono" value="" minlength="9" maxlength="9" rel="2"/><br />  
                        <label for="movil">Móvil</label>
                        <input type="text" name="movil" id="movil" value="" minlength="9" maxlength="9" /><br />                       
                        <label for="fax">Fax</label>
                        <input type="text" name="fax" id="fax" value="" minlength="9" maxlength="9"/>
                        <label for="direccion">Dirección</label>
                        <input type="text" name="direccion" id="direccion" class="required" value="" maxlength="100" rel="1"/>
                        <label for="cp">Código Postal</label>
                        <input type="text" name="cp" id="cp" class="required" value="" maxlength="5"/>
                        <label for="provincia">Provincia</label>
                        <select name="provincia" id="provincia" class="required">
                            <option></option>
                            <c:forEach var="provincia" items="${cSession.auxTables.provincias}">
                                <c:choose>
                                    <c:when test="${provincia.id.equals(sedeResponsable.provincia)}">
                                        <option value="${provincia.id}" SELECTED><c:out value="${provincia.provincia}" /></option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${provincia.id}"><c:out value="${provincia.provincia}" /></option>
                                    </c:otherwise>
                                </c:choose>                                       
                            </c:forEach>
                        </select>
                        <label for="localidad">Localidad</label><%--
                        <input type="text" name="localidad" id="localidad" value="${se.localidad}" />--%>
                        <div class="formData">            
                            <div id="destino">
                                <select name="provincia" id="localidad" class="required">
                                    <option selected="selected" value="-1"><fmt:message bundle="${StandardBundle}" key="option.sinAsignar" /></option>
                                </select>
                            </div>
                            <div id="cargando" style="display:none; color: green;"><fmt:message bundle="${StandardBundle}" key="alert.cargando" /></div>
                        </div>
                        <label for="sedePerContacto">Persona de contacto (tiene que estar dada de alta en Gestión de Personal)</label>
                        <input type="text" name="sedePerContacto" id="sedePerContacto" value="" />
                        <input type="hidden" name="idPersona" id="sedePerContactoidPersona" value="" class="required"/>


                        <label for="nota">Nota</label>
                        <textarea name="nota" rows="4" cols="20"><c:out value=""/></textarea>
                        <span class="datafield" style="display:none" rel="3"><a class="table-action-EditData">Edit</a></span>
			<span class="datafield" style="display:none" rel="4"><a href="/Details/DT_RowId">Details</a></span>
                        </form>
                    </c:if>
                    <table cellpadding="0" cellspacing="0" border="0" class="display" id="myTable">
                        <thead>
                            <tr>
                                <th>Nombre</th>
                                <th>Dirección</th>
                                <th>Teléfono</th>
                                <th>Details</th>
                            </tr>
                        </thead>
                        <tbody>		
                        </tbody>
                        <tfoot>
                            <tr>
                                <th>Nombre</th>
                                <th>Dirección</th>
                                <th>Teléfono</th>
                                <th>Details</th>
                            </tr>
                        </tfoot>
                    </table>
                    <c:if test="${cLaEmpresa.writable}">
                        <div class="add_delete_toolbar" ></div>
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
