<%-- 
    Document   : Listado empresas
    Created on : 27-may-2012, 12:59:40
    Author     : Eduardo L. García Glez.
    Descripción:
      Permite cambiar entre las empresas administradas.
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
<jsp:useBean id="cListado" class="com.openlopd.web.controllers.privatearea.cambiarempresa.CListado" scope="page" />
<jsp:setProperty name="cListado" property="session" value="${cSession}" />
<c:if test="${!cListado.readable}">
    <jsp:forward page="/common/error.jsp?e=read" />
</c:if>

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/private/headSection.jspf" />
        <script type="text/javascript">  
            var listEmpresasTable;
            var onScreenHelp;
            
            function startOnScreenHelp() {                
                onScreenHelp = $("body").onScreenHelp(
                    [{	
                        selector : "#myTable",
                        description : "En esta tabla se encuentran las empresas a las que gestionas o adaptas a la LOPD",
                        position: "top",
                        startWith: true
                    },{	
                        selector : "#myTable",
                        description : "Cada una de las filas de la tabla corresponden a una empresa diferente",
                        position: "top"
                    },{
                        selector : "#myTable",
                        description : "Uno de los aspectos más importantes de esta tabla es la columna Rank",
                        position: "top"
                    },{
                        selector : "#myTable",
                        html : 'Rank es un indicador de las operaciones que se han realizado sobre una empresa',
                        position: "top"
                    },{
                        selector : "#myTable",
                        html : 'Cuanto menor es el valor de Rank menos operaciones se habrán realizado sobre la empresa',
                        position: "top"
                    },{
                        selector : "#myTable",
                        html : 'Por esto se mostrarán primero las empresas con menos trabajos realizados',
                        position: "top"
                    },{
                        selector : "#myTable",
                        description : 'Para gestionar la LOPD de una empresa primero tienes que entrar en ella',
                        position: "top"
                    },{
                        selector : "#myTable",
                        description : 'Para ello pulsa el botón "poner aquí el dibujo de una casa"',
                        position: "top"
                    },{
                        selector : "#empInfo",
                        description : "Si quieres saber que empresa estás gestionando mira la información de la barra de estado"
                    },{
                        selector : "#myTable thead tr th",
                        description : "Puedes ordenar el listado pulsando el nombre de las columnas"
                    },{
                        selector : "#myTable_filter",
                        description : "Puedes simplificar el contenido de la tabla añadiendo lo que quieres ver aquí, por ejemplo el CIF de la empresa que buscas."
                    },{
                        selector : "#btnAddNewRow",
                        description : "Si quieres añadir una nueva empresa al listado pulsa este botón",
                        position: "top"
                    }],{
                        // global options
                        scrollAlways : true, // allways scroll to the next / prev step, can be overwritten through step's setting (default => true)
                        hideKeyCode : 27, // close button key (default => 27)
                        allowEventPropagation : true, //(default => true)
                        autoPagination: 20000,
                        closeOnEnd: true
                    });
            }
            
            $(document).ready(function(){
               $(".dateTimePicker").datetimepicker(getDateTimePickerConfig());
               //$("#enlaceajaxulFirst").menu();
               $.datepicker.setDefaults($.datepicker.regional['es']);
               listEmpresasTable = $('#myTable').dataTable({
                   "bJQueryUI": true,
                   "bServerSide": true,
                   "bDeferRender": true,
                   "bProcessing": true,
                   "sAjaxSource": "${appRoot}/privateArea/cambiarEmpresa/jSonTable.jsp",
                   "sServerMethod": "POST",
                   "aaSorting": [[ 8, "asc" ]],
                   "sPaginationType": "full_numbers",
                   "aoColumnDefs": [ 
                         { "bSortable": false, "aTargets": [ 9 ] }
                         
                   ],
                   "oLanguage": {
                         "sUrl": "${libPath}/lang/dataTable_es_ES.txt"
                   }
               }).makeEditable({
                   aoTableActions: [{
                         sAction: "EditData",
                         sServerActionURL: "UpdateData.jsp",
                         oFormOptions: { autoOpen: false, modal:true, width: "450px" }
                   }],
                   aoColumns: [ null , null ,  null , null , null, null, null, null , null, null ],
                   sAddURL: "AddData.jsp",
                   sDeleteURL: "DeleteData.jsp",
                   sUpdateURL: "UpdateData.jsp",
                   oAddNewRowButtonOptions: {label: "${buttonAlta}", icons: {primary:'ui-icon-plus'}},
		   oDeleteRowButtonOptions: {label: "${buttonBorrar}", icons: {primary:'ui-icon-trash'}},
                   oAddNewRowFormOptions: {
                       minWidth: 450
                   }
               });
            });
        </script>
                   <style type="text/css">
                        #myTable_filter { display: block;}
                   </style>
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
                    <c:if test="${cListado.writable}">
                        <form id="formEditData" action="UpdateData.jsp" method="POST" title="Actualizar">
                            <input type="hidden" name="id" id="id"  class="DT_RowId" />
                           <label for="cif">CIF/NIF</label>
                            <input type="text" name="cif" id="cif" class="required" rel="0" maxlength="9"/><br />  
                            <label for="razonSocial">Razón Social</label>
                            <input type="text" name="razonSocial" id="razonSocial" rel="1" maxlength="70"/><br />                        
                            <label for="actividad">Actividad</label>
                            <select name="actividad" id="actividad"  rel="2">
                                <option></option>
                                <c:forEach var="actividad" items="${cSession.auxTables.tipoActividadPrincipal}">                                        
                                    <option value="${actividad.id}"><c:out value="${actividad.descripcion}"/></option>                             
                                </c:forEach>
                            </select><br /><br />
                            <c:url var="gesPerLink" value="${approot}/privateArea/personas/personas.jsp"></c:url>
                            <label for="perContacto">Nombre</label> (para cambiar este campo ve a <a href="${gesPerLink}">Gestión de Personal</a>)
                            <input type="text" name="perContacto" id="perContacto" class="required" rel="3" disabled="disabled"/><br />
                            <label for="mailContacto">Mail Contacto</label>
                            <input type="text" name="mailContacto" id="mailContacto" rel="4" maxlength="70"/><br />                        
                            <label for="telefono">Telefono</label>
                            <input type="text" name="telefono" id="telefono" rel="5" minlength="9" maxlength="9"/><br />                        
                            <label for="movil">Movil</label>
                            <input type="text" name="movil" id="movil" rel="6" minlength="9" maxlength="9"/><br />
                            <label for="fax">Fax</label>
                            <input type="text" name="fax" id="fax" rel="7" minlength="9" maxlength="9"/><br />
                            <span class="datafield" style="display:none" rel="9"><a class="table-action-EditData">Edit</a></span>
                            <span class="datafield" style="display:none" rel="10"><a href="/Details/DT_RowId">Details</a></span>
                            <button id="formEditDataOk" type="submit">Ok</button>
                            <button id="formEditDataCancel" type="button">
                                <fmt:message bundle="${StandardBundle}" key="button.cancelar"/>
                            </button>
                        </form>
                        <form id="formAddNewRow" action="#" title="Añadir nueva empresa" class="tableDataForm">
                            <input type="hidden" name="id" id="id" value="DATAROWID" 
                            <c:if test="${cSession.sysAdmin}">
                                <label for="gestor">Es Gestor</label>
                                <input type="checkbox" name="gestor" value="ON" /><br/>
                            </c:if>
                            <label for="cif">CIF/NIF</label>
                            <input type="text" name="cif" id="cif" class="required" rel="0" maxlength="9"/><br />  
                            <label for="razonSocial">Razón Social</label>
                            <input type="text" name="razonSocial" id="razonSocial" rel="1" maxlength="70"/><br />                        
                            <label for="actividad">Actividad</label>
                            <%--<input type="text" name="actividad" id="actividad" rel="2"/><br /><br />--%>
                            <select name="actividad" id="actividad"  rel="2">
                                <option></option>
                                <c:forEach var="actividad" items="${cSession.auxTables.tipoActividadPrincipal}">                                        
                                    <option value="${actividad.id}"><c:out value="${actividad.descripcion}"/></option>                             
                                </c:forEach>
                            </select><br /><br />
                            <fieldset>
                                <legend>Persona de Contacto</legend>
                                <label for="perContacto">Nombre</label>
                                <input type="text" name="perContacto" id="perContacto" class="required" rel="3" maxlength="25"/><br />
                                <label for="pApellido">Primer Apellido</label>
                                <input type="text" name="pApellido" id="pApellido" class="required" maxlength="25"/><br />
                                <label for="sApellido">Segundo Apellido</label>
                                <input type="text" name="sApellido" id="sApellido" maxlength="25"/><br />
                            </fieldset><br />
                            <label for="mailContacto">Mail Contacto</label>
                            <input type="text" name="mailContacto" id="mailContacto" rel="4" maxlength="70"/><br />                        
                            <label for="telefono">Telefono</label>
                            <input type="text" name="telefono" id="telefono" rel="5" minlength="9" maxlength="9"/><br />                        
                            <label for="movil">Movil</label>
                            <input type="text" name="movil" id="movil" rel="6" minlength="9" maxlength="9"/><br />                        
                            <label for="fax">Fax</label>
                            <input type="text" name="fax" id="fax" rel="7" minlength="9" maxlength="9"/><br /> 
                            <input type="hidden" name="rank" value=""  rel="8"/>
                            <span class="datafield" style="display:none" rel="9"><a class="table-action-EditData">Edit</a></span>
                            <span class="datafield" style="display:none" rel="10"><a href="/Details/DT_RowId">Details</a></span>
                        </form>
                    </c:if>
                    <h1>Listado de empresas gestionadas</h1>
                    <p>En este listado puedes ver las empresas gestionadas desde tu empresa, puedes cambiar de empresa pulsando sobre el icono de la columna Editar de la empresa correspondiente. La columna Rank es un indicador de acciones realizadas sobre la empresa, a menor valor, menos acciones se han realizado sobre la misma, por esto en el listado se muestran primero las empresas con menor Rank.</p>
                    <table cellpadding="0" cellspacing="0" border="0" class="display" id="myTable">
                        <thead>
                            <tr>	
                                <th>CIF/NIF</th>
                                <th>Razón Social</th>
                                <th>Actividad</th>
                                <th>Per Contacto</th>
                                <th>Mail Contacto</th>
                                <th>Telefono</th>
                                <th>Movil</th>
                                <th>fax</th>
                                <th>Rank</th>
                                <th>Entrar</th>
                            </tr>
                        </thead>
                        <tbody>		
                        </tbody>
                        <tfoot>
                            <tr>
                                <th>CIF/NIF</th>
                                <th>Razón Social</th>
                                <th>Actividad</th>
                                <th>Per Contacto</th>
                                <th>Mail Contacto</th>
                                <th>Telefono</th>
                                <th>Movil</th>
                                <th>fax</th>
                                <th>Rank</th>
                                <th>Entrar</th>
                            </tr>
                        </tfoot>
                    </table>                         
                    <c:if test="${cListado.writable}">
                        <div class="add_delete_toolbar" ></div>
                    </c:if>
                </div>
                <jsp:directive.include file="/WEB-INF/jspf/common/standard/pieweb.jspf" />
            </div>
        </div>
        <div id="extraFooter">
        </div>
        <jsp:directive.include file="/WEB-INF/jspf/common/standard/extraDivs.jspf" />
    </body>
</html>