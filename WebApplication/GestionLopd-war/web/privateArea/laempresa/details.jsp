<%-- 
    Document   : details
    Created on : 08-oct-2012, 20:19:07
    Author     : edu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<c:if test="${!cSession.logged}">
    <jsp:forward page="../../publicArea/login.jsp" />
</c:if>
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />
<jsp:directive.include file="/WEB-INF/jspf/common/standard/formsTranslate.jspf" />

<jsp:useBean id="cDetails" class="com.openlopd.web.controllers.privatearea.laempresa.CDetails" scope="request" />
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
                
               cargarProvincias("${appRoot}");
               //$("#destino").load("${appRoot}/common/ajax/localidades.jsp", {idProvincia: $("#provincia").val()}); 
            
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
                    <%-- 
                         Esta es la variable de donde se recoge los datos de la 
                         sede a modificar.
                    --%>
                    <c:set var="se" scope="request" value="${cDetails.getDetailsSede()}" />
                    <form id="formEditData" action="./UpdateData.jsp" title="${titleActualizar}" class="tableDataForm detailsForm">
                        <input type="hidden" name="id" id="id" value="${se.id}" />
                        <label for="nombreSede">Nombre</label>
                        <input type="text" name="nombreSede" id="nombreSede" class="required" maxlength="100" value="${se.nombreSede}"/><br />                        
                        <label for="gestiona" title="Solo se puede establecer una sede de gestión LOPD.
                               Esta será utilizada en la generación de la documentación y envío de 
                               correspondencia. Si activa esta sede como principal se 
                               desactivará la que actualmente esté activa.">Sede principal de gestión LOPD</label>
                        <c:choose>
                            <c:when test="${se.gestionaContratLOPD}">
                                <c:set var="gesLOPD" value="checked=\"checked\"" />
                            </c:when>
                            <c:otherwise>
                                <c:set var="gesLOPD" value="" />
                            </c:otherwise>
                        </c:choose>                        
                        <input type="radio" name="gestiona" id="gestiona" value="ON" ${gesLOPD} /><br />
                        <label for="telefono">Teléfono</label>
                        <input type="text" name="telefono" id="telefono" value="${se.telefono}" maxlength="9" /><br />  
                        <label for="movil">Móvil</label>
                        <input type="text" name="movil" id="movil" value="${se.movil}" maxlength="9" /><br />                       
                        <label for="fax">Fax</label>
                        <input type="text" name="fax" id="fax" value="${se.fax}" maxlength="9"/>
                        <label for="direccion">Dirección</label>
                        <input type="text" name="direccion" id="direccion" value="${se.direccion}" maxlength="100"/>
                        <label for="cp">Código postal</label>
                        <input type="text" name="cp" id="cp" value="${se.cp}" maxlength="5"/>
                        <label for="provincia">Provincia</label>
                        <select name="provincia" id="provincia">
                            <option></option>
                            <c:forEach var="provincia" items="${cSession.auxTables.provincias}">
                                <c:choose>
                                    <c:when test="${provincia.id.equals(se.provincia)}">
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
                                <select name="localidad" id="localidad">  
                                    <%-- TODO: Completar el listado de localidades. --%>
                                    <option selected="selected" value="${se.localidad}">${cSession.auxTables.getNombreLocalidad(se.localidad)}</option>
                                </select>
                            </div>
                            <div id="cargando" style="display:none; color: green;"><fmt:message bundle="${StandardBundle}" key="alert.cargando" /></div>
                        </div>
                        <label for="perContacto">Persona de contacto</label>
                        <select name="perContacto" id="perContacto">
                            <c:forEach var="p" items="${cDetails.personas}">
                                <c:choose>                                        
                                    <c:when test="${p.id.equals(se.perContacto)}">
                                        <c:set var="selected" value="selected=\"selected\"" />
                                    </c:when>
                                    <c:otherwise>                                        
                                        <c:set var="selected" value="" />
                                    </c:otherwise>
                                </c:choose>
                                <option ${selected} value="${p.id}"><c:out value="${p.nombreCompleto}" /></option>
                            </c:forEach>
                        </select>
                        <label for="nota">Nota</label>
                        <textarea name="nota" rows="4" cols="20"><c:out value="${se.nota}"/></textarea>
                        
                        <%--<span class="datafield" style="display:none" rel="4"><a class="table-action-EditData">Edit</a></span>
			<span class="datafield" style="display:none" rel="5"><a href="/Details/DT_RowId">Details</a></span>
--%>
                        <input class="button" type="submit" value="Enviar" />
                        <a class="button" href="./laempresa.jsp">
                            <fmt:message bundle="${StandardBundle}" key="button.volver"/>
                        </a>
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
