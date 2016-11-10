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
<jsp:useBean id="cModosDestruccion" class="com.openlopd.web.controllers.privatearea.modosdestruccion.CModosDestruccion" scope="page" />
<jsp:setProperty name="cModosDestruccion" property="session" value="${cSession}" />
<c:if test="${!cModosDestruccion.readable}">
    <jsp:forward page="/common/error.jsp?e=read" />
</c:if>

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/private/headSection.jspf" />
        <script type="text/javascript">            
            $(document).ready(function(){
               $(".dateTimePicker").datetimepicker(getDateTimePickerConfig());
               
               
               $('#myTable').dataTable({
                   "bJQueryUI": true,
                   "bServerSide": true,
                   "bDeferRender": true,
                   "bProcessing": true,
                   "sAjaxSource": "${appRoot}/privateArea/modosDestruccion/jSonTable.jsp",
                   "sServerMethod": "POST",
                   "sPaginationType": "full_numbers",
                   "aoColumnDefs": [                       
                         { "bSortable": false, "aTargets": [ 4 ] }
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
                   // Poner los siguientes elementos a {} para que puedan ser
                   // editados en línea.
                   aoColumns: [ null , null ,  null , null , null<%--, null, null --%>],
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
                    <c:if test="${cModosDestruccion.writable}">
                        <form id="formEditData" action="UpdateData.jsp" title="Actualizar Modo de Destrucción">
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
                        <form id="formAddNewRow" action="#" title="Añadir" class="tableDataForm">
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
                    <h1>Modos de Destrucción</h1>
                    <p>Con el fin de que personas no autorizadas puedan acceder a la información de un soporte es necesario establecer los procedimientos adecuados para la destrucción de dicha información, puedes encontrarte en varios escenarios en relación a la destrucción de esta información, algunos ejemplos podrían ser:</p>
                    <ul>
                        <li>Un disco de copias de seguridad que ya presenta problemas en la copia. En este caso una opción interesante puede ser destruir físicamente el soporte.</li>
                        <li>Un equipo que pasa de un directivo a cualquier otro empleado de la empresa, en este caso solo te puede interesar destruir la información sensible a la que otro empleado no deba acceder, por lo que el procedimiento es diferente al anterior.</li>
                    </ul>
                    <p>En esta tabla puedes definir los procesos de destrucción de información que utilizar para destruir esta información, estos te serán de utilidad en el apartado de "Inventario de Soportes".</p>
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
                            <!-- Los datos se cargan de forma dinámica -->
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
                    <c:if test="${cModosDestruccion.writable}">
                        <div class="add_delete_toolbar" ></div>
                    </c:if> 
                </div>
            </div>
            <jsp:directive.include file="/WEB-INF/jspf/common/standard/pieweb.jspf" />
        </div>
        <div id="extraFooter">
        </div>
        <%-- Utilizarlos para añadir información extra --%>
        <div id="extraInfo1"><span></span></div>
        <div id="extraInfo2"><span></span></div>
        <div id="extraInfo3"><span></span></div>
    </body>
</html>