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
<jsp:useBean id="cSoportesInventario" class="com.openlopd.web.controllers.privatearea.soportesinventario.CSoportesInventario" scope="page" />
<jsp:setProperty name="cSoportesInventario" property="session" value="${cSession}" />
<c:if test="${!cSoportesInventario.readable}">
    <jsp:forward page="/common/error.jsp?e=read" />
</c:if>

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/private/headSection.jspf" />
        <script type="text/javascript">            
            $(document).ready(function(){
               $(".dateTimePicker").datetimepicker(getDateTimePickerConfig());    
               $(".linkButton").button();
               
               var oTable = $('#myTable').dataTable({
                   "bJQueryUI": true,
                   "bServerSide": true,
                   "bDeferRender": true,
                   "bProcessing": true,
                   "sAjaxSource": "${appRoot}/privateArea/soportesinventario/jSonTable.jsp",
                   "sServerMethod": "POST",
                   "sPaginationType": "full_numbers",
                   "aoColumnDefs": [                       
                         { "bSortable": false, "aTargets": [ 5,6,7 ] }
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
                   aoColumns: [ null , null ,  null , null , null, null, null ],
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
                    <form id="formEditData" action="UpdateData.jsp" title="Actualizar datos del soporte">
                        <input type="hidden" name="id" id="id"  class="DT_RowId" />
                        <%--<label for="tipo">Tipo</label>
                        <select name="tipo" id="tipo" rel="5" class="required" >
                            <option></option>
                            <c:forEach var="t" items="${cSession.auxTables.auxTiposSoportes}"> 
                                <option value="${t.id}"><c:out value="${t.descripcion}" /></option>                            
                            </c:forEach>
                        </select><br />
                        <a href="../tiposSoporte/tiposSoporte.jsp">Añadir Tipo de Soporte</a><br />--%>
                        <label for="descripcion">Descripción</label>
                        <input type="text" name="descripcion" class="required" rel="0" maxlength="255"/><br />                      
                        <label for="fAlta">Fecha Alta</label>
                        <input type="text" name="fAlta" class="dateTimePicker required" rel="1" maxlength="16"/><br />                        
                        <label for="fBaja">Fecha Baja</label>
                        <input type="text" name="fBaja" class="dateTimePicker" rel="2" maxlength="16"/><br />                                                
                        <label for="etiqueta">Etiqueta</label>
                        <input type="text" name="etiqueta" rel="3" maxlength="255"/><br />                        
                        <%--<label for="capacidad">Capacidad</label>
                        <input type="text" name="capacidad" id="capacidad" class="required" rel="4"/>
                        <select name="unidadesInfo" id="unidadesInfo" class="required" >
                            <c:forEach var="u" items="${cSession.auxTables.auxUnidadesInformacion}"> 
                                <option value="${u.id}"><c:out value="${u.simbolo} - ${u.descripcion}" /></option>                            
                            </c:forEach>
                        </select><br />--%>
                        
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
                    <form id="formAddNewRow" action="#" title="Añadir nuevo soporte" class="tableDataForm">
                        <input type="hidden" name="id" id="id" value="DATAROWID" /> 
                        <label for="tipo">Tipo</label>
                        <select name="tipo" id="tipo" rel="5" class="required" >
                            <option></option>
                            <c:forEach var="t" items="${cSession.auxTables.auxTiposSoportes}"> 
                                <option value="${t.id}"><c:out value="${t.descripcion}" /></option>                            
                            </c:forEach>
                        </select><br />
                        <a class="linkButton" href="../tiposSoporte/tiposSoporte.jsp">Añadir Tipo de Soporte</a><br />
                        <label for="descripcion">Descripción</label>
                        <input type="text" name="descripcion" id="descripcion" class="required" rel="0" maxlength="255"/><br />                      
                        <label for="fAlta">Fecha Alta</label>
                        <input type="text" name="fAlta" id="fAlta" class="dateTimePicker required" rel="1" maxlength="16"/><br />                        
                        <label for="fBaja">Fecha Baja</label>
                        <input type="text" name="fBaja" id="fBaja" class="dateTimePicker" rel="2" maxlength="16"/><br />                                                
                        <label for="etiqueta">Etiqueta</label>
                        <input type="text" name="etiqueta" id="etiqueta" rel="3" maxlength="255"/><br />                        
                        <label for="capacidad">Capacidad</label>
                        <input type="text" name="capacidad" id="capacidad" class="required" rel="4"/>
                        <select name="unidadesInfo" id="unidadesInfo" class="required" >
                            <c:forEach var="u" items="${cSession.auxTables.auxUnidadesInformacion}"> 
                                <option value="${u.id}"><c:out value="${u.simbolo} - ${u.descripcion}" /></option>                            
                            </c:forEach>
                        </select><br />
                        <label for="sn">Nº Serie</label>
                        <input type="text" name="sn" maxlength="50" /><br />
                        <label for="observaciones">Observaciones</label>
                        <textarea cols="80" rows="5" name="observaciones" maxlength="20000" ></textarea><br />
                        <span class="datafield" style="display:none" rel="6"><a class="table-action-EditData">Edit</a></span>
			<span class="datafield" style="display:none" rel="7"><a href="/Details/DT_RowId">Details</a></span>
                    </form>
                    <h1>Inventario de Soportes</h1>
                    <p>En este apartado puedes inventariar todos los soportes de la empresa que contienen carácter personal.</p>
                    <table cellpadding="0" cellspacing="0" border="0" class="display" id="myTable">
                        <thead>
                            <tr>	
                                <th>Descripción</th>
                                <th>Fecha Alta</th>
                                <th>Fecha Baja</th>
                                <th>Etiqueta</th>
                                <th>Capacidad</th>
                                <th>Tipo</th>
                                <th>Edit</th>
                                <th>Details</th>
                            </tr>
                        </thead>
                        <tbody>		
                        </tbody>
                        <tfoot>
                            <tr>
                                <th>Descripción</th>
                                <th>Fecha Alta</th>
                                <th>Fecha Baja</th>
                                <th>Etiqueta</th>
                                <th>Capacidad</th>
                                <th>Tipo</th>
                                <th>Edit</th>
                                <th>Details</th>
                            </tr>
                        </tfoot>
                    </table>
                    <div class="add_delete_toolbar" ></div><br>
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