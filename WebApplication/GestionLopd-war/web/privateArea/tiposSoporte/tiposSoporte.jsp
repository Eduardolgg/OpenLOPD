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
<jsp:useBean id="cTiposSoporte" class="com.openlopd.web.controllers.privatearea.tipossoporte.TiposSoporte" scope="page" />
<jsp:setProperty name="cTiposSoporte" property="session" value="${cSession}" />
<c:if test="${!cTiposSoporte.readable}">
    <jsp:forward page="/common/error.jsp?e=read" />
</c:if>

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/private/headSection.jspf" />
        <script type="text/javascript">            
            $(document).ready(function(){
               $.datepicker.setDefaults($.datepicker.regional['es']);
               $("#fAlta").datetimepicker(); 
               $("#fBaja").datetimepicker();  
               $("#fAltae").datetimepicker(); 
               $("#fBajae").datetimepicker();              
               $('#myTable').dataTable({
                   "bJQueryUI": true,
                   "bServerSide": true,
                   "bDeferRender": true,
                   "bProcessing": true,
                   "sAjaxSource": "${appRoot}/privateArea/tiposSoporte/jSonTable.jsp",
                   "sServerMethod": "POST",
                   "sPaginationType": "full_numbers",
                   "oLanguage": {
                         "sUrl": "${libPath}/lang/dataTable_es_ES.txt"
                   }
               }).makeEditable({
                   aoTableActions: [{
                         sAction: "EditData",
                         sServerActionURL: "UpdateData.jsp",
                         oFormOptions: { autoOpen: false, modal:true, width: "450px" }
                   }],
                   // Poner los siguientes elementos a {} para que puedan ser
                   // editados en línea.
                   aoColumns: [ null , null ,  null , null , null<%--, null, null --%>],
                   sAddURL: "AddData.jsp",
                   sDeleteURL: "DeleteData.jsp",
                   sUpdateURL: "UpdateData.jsp",
                   oAddNewRowButtonOptions: {label: "Alta", icons: {primary:'ui-icon-plus'}},
		   oDeleteRowButtonOptions: {label: "Borrar", icons: {primary:'ui-icon-trash'}},
                   oAddNewRowFormOptions: {
                       minWidth: 450
                   }
               });
               $(".button").button();
               $(".button").click(function(){
                   var nombre = "";
                   var descripcion = "";
                   
                   switch($(this).attr('id')) {
                       case 'cdrw700': 
                           nombre = "CD-RW 700MB"; 
                           descripcion = "Disco compacto regrabable: capacidad 700MB";
                           break
                       case 'cdr700': 
                           nombre = "CD-ROM 700MB"; 
                           descripcion = " Compact Disc - Read Only Memory: capacidad 700MB";
                           break
                       case 'pendrive': 
                           nombre = "Pendrive"; 
                           descripcion = "unidad flash USB";
                           break
                   }
                   $("#nombre").val(nombre);    
                   $("#descripcion").val(descripcion);
                   $("#btnAddNewRow").click();
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
                    <c:if test="${cTiposSoporte.writable}">
                        <form id="formEditData" action="UpdateData.jsp" title="Actualizar datos del soporte">
                            <input type="hidden" name="id" id="id"  class="DT_RowId" />
                            <label for="nombre">Nombre</label>
                            <input type="text" name="nombre" id="nombre" class="required" maxlength="50" rel="0"/><br />                        
                            <label for="descripcion">Descripción</label>
                            <input type="text" name="descripcion" id="descripcion" class="required" maxlength="255" rel="1"/><br />
                            <label for="fAlta">Fecha Alta</label>
                            <input type="text" name="fAlta" id="fAltae" class="dateTimePicker required" rel="2"/><br />                        
                            <label for="fBaja">Fecha Baja</label>
                            <input type="text" name="fBaja" id="fBajae" class="dateTimePicker" rel="3"/><br />  
                                
                            <span class="datafield" style="display:none" rel="4"><a class="table-action-EditData">Edit</a></span>
                            <%--<span class="datafield" style="display:none" rel="5"><a href="/Details/DT_RowId">Details</a></span>--%>
                            <br><hr>
                            <button id="formEditDataOk" type="submit">
                                <c:out value="${buttonEnviar}" />
                            </button>
                            <button id="formEditDataCancel" type="button">
                                <c:out value="${buttonCancelar}" />
                            </button>
                        </form>
                        <form id="formAddNewRow" action="#" title="Añadir nuevo tipo de soporte" class="tableDataForm">
                            <input type="hidden" name="id" id="id" value="DATAROWID" />
                            <label for="nombre">Nombre</label>
                            <input type="text" name="nombre" id="nombre" class="required" maxlength="50" rel="0"/><br />                        
                            <label for="descripcion">Descripción</label>
                            <input type="text" name="descripcion" id="descripcion" class="required" maxlength="255" rel="1"/><br />
                            <label for="fAlta">Fecha Alta</label>
                            <input type="text" name="fAlta" id="fAlta" class="dateTimePicker required" rel="2"/><br />                        
                            <label for="fBaja">Fecha Baja</label>
                            <input type="text" name="fBaja" id="fBaja" class="dateTimePicker" rel="3"/>
                                
                            <span class="datafield" style="display:none" rel="4"><a class="table-action-EditData">Edit</a></span>
                            <%--<span class="datafield" style="display:none" rel="5"><a href="/Details/DT_RowId">Details</a></span>--%>
                        </form>
                    </c:if>
                    <h1>Tipos de Soporte</h1>
                    <p>Este apartado te permite definir los tipos de soportes utilizados en la empresa.</p>
                    <table cellpadding="0" cellspacing="0" border="0" class="display" id="myTable">
                        <thead>
                            <tr>
                                <th>Nombre</th>	
                                <th>Descripción</th>
                                <th>Fecha Alta</th>
                                <th>Fecha Baja</th>
                                <th>Edit</th>
                                <%--<th>Details</th>--%>
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
                                <%--<th>Details</th>--%>
                            </tr>
                        </tfoot>
                    </table>
                    <c:if test="${cTiposSoporte.writable}">       
                        <div class="add_delete_toolbar" ></div>
                    </c:if><br>
                    <fieldset>
                        <legend>Tipos predefinidos</legend>
                        <button id="cdrw700" class="button">CD-RW 700MB</button>
                        <button id="cdr700" class="button">CD-R 700MB</button>
                        <button id="pendrive" class="button">Pendrive</button>
                    </fieldset><br>
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