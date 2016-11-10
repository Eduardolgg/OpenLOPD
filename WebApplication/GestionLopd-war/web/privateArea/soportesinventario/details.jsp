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

<jsp:useBean id="cDetails" class="com.openlopd.web.controllers.privatearea.soportesinventario.CDetails" scope="request" />
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
                $(".linkButton").button();
                
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
                    <h1>Detalles del soporte</h1>
                    <%-- 
                         Esta es la variable de donde se recoge el destinatario 
                         Modificar.
                    --%>
                    <c:set var="s" scope="request" value="${cDetails.getDetails()}" />
                    <form id="formEditData" action="./UpdateData.jsp" title="${titleActualizar}" class="tableDataForm detailsForm">
                        <input type="hidden" name="id" id="id" value="${s.id}" />
                        <label for="tipo">Tipo</label>
                        <select name="tipo" id="tipo" rel="5" class="required" >
                            <option></option>
                            <c:forEach var="t" items="${cSession.auxTables.auxTiposSoportes}"> 
                                <c:choose>
                                    <c:when test="${t.id.equals(s.tipoSoporte.id)}">
                                        <c:set var="selected" value="selected=\"selected\"" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="selected" value="" />
                                    </c:otherwise>
                                </c:choose>                            
                                <option value="${t.id}" ${selected}><c:out value="${t.nombre}" /></option>                            
                            </c:forEach>
                        </select><br />
                        <a class="linkButton" href="../tiposSoporte/tiposSoporte.jsp">Añadir Tipo de Soporte</a><br />
                        <label for="descripcion">Descripción</label>
                        <input type="text" name="descripcion" id="descripcion" class="required" rel="0" maxlength="255" value="${s.descripcion}"/><br />                      
                        <label for="fAlta">Fecha Alta</label>
                        <input type="text" name="fAlta" id="fAlta" class="dateTimePicker required" rel="1" maxlength="16" value="${s.getDateTime(s.fechaAlta, cSession.accessInfo.timeZone)}"/><br />                        
                        <label for="fBaja">Fecha Baja</label>
                        <input type="text" name="fBaja" id="fBaja" class="dateTimePicker" rel="2" maxlength="16" value="${s.getDateTime(s.fechaBaja, cSession.accessInfo.timeZone)}"/><br />                                                
                        <label for="modoDestruccion">Modo de Destrucción</label>
                        <select name="modoDestruccion" id="modoDestruccion" >
                            <c:choose>
                                <c:when test="${md.modoDestruccion == null}">
                                    <option selected="selected"></option>
                                </c:when>
                                <c:otherwise>
                                    <option></option>
                                </c:otherwise>
                            </c:choose>
                            <c:forEach var="md" items="${cSession.auxTables.auxModoDestruccion}"> 
                                <c:choose>
                                    <c:when test="${md.id.equals(s.modoDestruccion.id)}">
                                        <c:set var="selected" value="selected=\"selected\"" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="selected" value="" />
                                    </c:otherwise>
                                </c:choose>
                                <option value="${md.id}" ${selected}><c:out value="${md.nombre}" /></option>                            
                            </c:forEach>
                        </select><br />
                        <label for="etiqueta">Etiqueta</label>
                        <input type="text" name="etiqueta" id="etiqueta" rel="3" maxlength="255" value="${s.etiqueta}"/><br />                        
                        <label for="capacidad">Capacidad</label>
                        <input type="text" name="capacidad" id="capacidad" class="required miniInput" rel="4" value="${s.capacidad}"/>
                        <select name="unidadesInfo" id="unidadesInfo" class="required miniInput" >
                            <c:forEach var="u" items="${cSession.auxTables.auxUnidadesInformacion}"> 
                                <c:choose>
                                    <c:when test="${u.id.equals(s.unidades.id)}">
                                        <c:set var="selected" value="selected=\"selected\"" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="selected" value="" />
                                    </c:otherwise>
                                </c:choose>
                                <option value="${u.id}" ${selected}><c:out value="${u.simbolo} - ${u.descripcion}" /></option>                            
                            </c:forEach>
                        </select><br />
                        <label for="sn">Nº Serie</label>
                        <input type="text" name="sn" maxlength="50" value="${s.sn}"/><br />
                        <label for="observaciones">Observaciones</label>
                        <textarea cols="80" rows="5" name="observaciones" maxlength="20000" ><c:out value="${s.observaciones}" /></textarea><br />
                        <%--<span class="datafield" style="display:none" rel="6"><a class="table-action-EditData">Edit</a></span>
			<span class="datafield" style="display:none" rel="7"><a href="/Details/DT_RowId">Details</a>--%>

                        <input class="button" type="submit" value="Enviar" />
                        <a class="button" href="./soportesInventario.jsp"><fmt:message bundle="${StandardBundle}" key="button.volver"/></a>
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
