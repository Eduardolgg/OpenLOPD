<%-- 
    Document   : details
    Created on : 08-oct-2012, 20:19:07
    Author     : edu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<c:if test="${!cSession.logged}">
    <jsp:forward page="../../publicArea/login.jsp" />
</c:if>
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />
<jsp:directive.include file="/WEB-INF/jspf/common/standard/formsTranslate.jspf" />

<jsp:useBean id="cDetails" class="com.openlopd.web.controllers.privatearea.destinatarios.CDetails" scope="request" />
<jsp:setProperty name="cDetails" property="*" />
<jsp:setProperty name="cDetails" property="session" value="${cSession}" />

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/private/headSection.jspf" />
        <script type="text/javascript"> 
            $(document).ready(function(){
                //$("formEditData").validate({lang:'es'});
                $(".dateTimePicker").datetimepicker(getDateTimePickerConfig());
                //$("#enlaceajaxulFirst").menu();
                $(".button").button();
                
                $('#formEditData').ajaxForm(function() { 
                    showAppMsg("Operación completada."); 
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
                    <h1>Detalles de Destinatario</h1>
                    <%-- 
                         Esta es la variable de donde se recoge el destinatario 
                         Modificar.
                    --%>
                    <c:set var="d" scope="request" value="${cDetails.getDetails()}" />
                    <form id="formEditData" action="./UpdateData.jsp" title="${titleActualizar}" class="tableDataForm detailsForm">
                        <input type="hidden" name="id" id="id" value="${d.id}" />
                        <label for="nombre">Nombre</label>
                        <input type="text" name="nombre" id="nombre" class="required" maxlength="50" rel="0" value="${d.nombre}"/><br />                        
                        <label for="descripcion">Descripción</label>
                        <input type="text" name="descripcion" id="descripcion" class="required" maxlength="255" value="${d.descripcion}" rel="1"/><br />
                        <label for="fAlta">Fecha Alta</label>
                        <input type="text" name="fAlta" id="fAlta" class="dateTimePicker required" value="${d.getDateTime(d.fechaAlta, cSession.accessInfo.timeZone)}" rel="2"/><br />                        
                        <label for="fBaja">Fecha Baja</label>
                        <input type="text" name="fBaja" id="fBaja" class="dateTimePicker" value="${d.getDateTime(d.fechaBaja, cSession.accessInfo.timeZone)}" rel="3"/>
                        <label for="observaciones">Observaciones</label>
                        <textarea cols="30" rows="5" id="observaciones" name="observaciones" maxlength="2000"><c:out value="${d.observaciones}" /></textarea>
                        <%--<span class="datafield" style="display:none" rel="4"><a class="table-action-EditData">Edit</a></span>
			<span class="datafield" style="display:none" rel="5"><a href="/Details/DT_RowId">Details</a></span>
--%>
                        <input class="button" type="submit" value="Enviar" />
                        <a class="button" href="./destinatarios.jsp"><fmt:message bundle="${StandardBundle}" key="button.volver"/></a>
                    </form>                    
                </div>
            </div>
            <jsp:directive.include file="/WEB-INF/jspf/common/standard/pieweb.jspf" />
        </div>
        <div id="extraFooter">
        </div>
        <jsp:directive.include file="/WEB-INF/jspf/common/standard/extraDivs.jspf" />
    </body>
</html>
