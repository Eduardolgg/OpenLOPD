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

<jsp:useBean id="cDetails" class="com.openlopd.web.controllers.privatearea.personas.CDetails" scope="request" />
<jsp:setProperty name="cDetails" property="*" />
<jsp:setProperty name="cDetails" property="session" value="${cSession}" />

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/private/headSection.jspf" />
        <script type="text/javascript"> 
            var separador = ';';
            
            function divCheckBoxInit() {                
                if($("#esUsuario:checked").val() == "ON"){
                    $("#permisosUsuario").show('blind');
                } else {
                    $("#permisosUsuario").hide('blind');
                }
                heightRecalc()
            }
            
            function emailCheckBlock(){                
                if ($("#esUsuario:checked").val() == "ON") {
                    $("#email").attr("readonly", "readonly");
                } else {
                    $("#email").removeAttr("readonly"); 
                }
            }
            
            function heightRecalc() {                
                   setUlHeight("#gruposDisponibles","#gruposHabilitadas");
                   setUlHeight("#ficherosDisponibles","#ficherosHabilitadas");
            }
            
            function setActivesLi(ulId, inputId) {
                $(inputId).val("");
                
                $(ulId + " li").each(function(index){
                    $(inputId).val($(inputId).val() + separador + $(this).attr("id")); 
                });
                
                $(inputId).val($(inputId).val().substr(1, $(inputId).val().length));
            }
            
            function showResponse(responseText, statusText, xhr, $form) {
                showAppMsg(responseText);
                emailCheckBlock();
            }
            
            function validate(formData, jqForm, options) {
                return jqForm.valid();
            }
            
            $(document).ready(function(){
                $("formEditData").validate({lang:'es'});  
                $(".dateTimePicker").datetimepicker(getDateTimePickerConfig());
                //$("#enlaceajaxulFirst").menu();
                $(".button").button();
                
                $("#enviarButton").click(function() {
                    setActivesLi("#gruposHabilitadas", "#grupos");
                    setActivesLi("#ficherosHabilitadas", "#ficheros");
                    if($("#esUsuario:checked").val() == "ON" && 
                        $("#grupos").val() == "") {
                        showAppMsg("Debe Seleccionar un grupo.");
                    } else if($("#esUsuario:checked").val() == "ON" &&
                        $("#email").val() == "") {
                        showAppMsg("Debe añadir el e-mail del usuario.");
                    } else {
                        $('#formEditData').ajaxSubmit({
                            beforeSubmit: validate,
                            success: showResponse
                        });
                    }
                    return false;
                });
               
                
                emailCheckBlock();
                $("#esUsuario").click(function(){
                   divCheckBoxInit();
                });
                
                $( "ul.droptrue" ).sortable({
                    connectWith: "ul"
                });
                
                $("#gruposDisponibles, #gruposHabilitadas, "
                  + " #ficherosDisponibles, #ficherosHabilitadas").resize(function(event){
                   heightRecalc();                  
                });
                
               heightRecalc();
               divCheckBoxInit();
            });
        </script>
        <style>
            #gruposDisponibles,  #gruposHabilitadas,
            #ficherosDisponibles,  #ficherosHabilitadas { 
                list-style-type: none; margin: 0; padding: 0; float: left; 
                margin-right: 10px; background: #eee; padding: 5px; width: 443px;
            }
            
            #gruposDisponibles li, #gruposHabilitadas li,
            #ficherosDisponibles li, #ficherosHabilitadas li { 
                margin: 5px; padding: 5px;  width: 420px; 
            }
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
                    <%-- 
                         Esta es la variable de donde se recoge la persona 
                         Modificar.
                    --%>
                    
                    <c:set var="d" scope="request" value="${cDetails.getDetails()}" />
                    <form id="formEditData" action="./UpdateData.jsp" title="${titleActualizar}" class="tableDataForm detailsForm">
                       <h1>Detalle de datos personales</h1>
                       <p>En este formulario puedes actualizar los datos de la persona
                            que has seleccionado o cambiar la persona de contacto de la empresa, así como
                            asignar los ficheros a los que tiene acceso y si está autorizado
                            al envío y/o recepción de soportes.</p>
                       <input type="hidden" name="id" id="id" value="${d.id}"  />
                       <label for="nombre">Nombre</label>
                       <input type="text" name="nombre" id="nombre" class="required" value="${d.nombre}" maxlength="25" rel="0"/><br /> 
                       <label for="apellido1">Primer Apellido</label>
                       <input type="text" name="apellido1" id="apellido1" class="required" value="${d.apellido1}" maxlength="25" rel="1"/><br /> 
                       <label for="apellido2">Segundo Apellido</label>
                       <input type="text" name="apellido2" id="apellido2" class="" maxlength="25" value="${d.apellido2}" rel="2"/><br /> 
                       <label for="dni">DNI/NIE</label>
                       <input type="text" name="dni" id="dni" class="" maxlength="9" value="${d.dni}" /><br />
                       <label for="fInicio">Fecha Inicio Prestación</label>
                       <input type="text" name="fInicio" id="fInicio" class="dateTimePicker required" value="${d.getDateTime(d.fInicio, cSession.accessInfo.timeZone)}" maxlength="16" rel="3"/><br /> 
                       <label for="fFin">Fecha Fin Prestación</label>
                       <input type="text" name="fFin" id="fFin" class="dateTimePicker" value="${d.getDateTime(d.fFin, cSession.accessInfo.timeZone)}" maxlength="16" rel="4"/><br /> 
                       <%-- TODO: Buscar la forma de añadir usuarios de la app. --%>
                       <input type="hidden" name="usuario" id="usuario" value="" value="${d.usuario}" rel="5"/><br /> 
                       
                       <label for="perfil">Cargo</label>
                       <input type="text" name="perfil" id="perfil" value="${d.perfil}" maxlength="255" /><br /> 
                       <label for="telefono">Teléfono</label>
                       <input type="text" name="telefono" id="telefono" value="${d.telefono}" maxlength="15" /><br />                      
                       <label for="email">E-mail</label>
                       <input type="text" name="email" id="email" class="email" value="${d.email}" maxlength="255" /><br />
                       <%--
                       <label for="perContacto">Es persona de contacto:</label>
                       <c:choose>
                           <c:when test="${d.perContacto}">
                               <c:set var="pc" value="checked" scope="request" />
                           </c:when>
                           <c:otherwise>
                               <c:set var="pc" value="" scope="request" />
                           </c:otherwise>
                       </c:choose>
                       <input type="radio" name="perContacto" value="on"  ${pc} /><br />--%>
                       
                       <label for="autorizadoSalidaSoportes">Está autorizado para el envío de soportes:</label>
                       <c:choose>
                           <c:when test="${d.autorizadoSalidaSoportes}">
                               <c:set var="s" value="checked" scope="request" />
                           </c:when>
                           <c:otherwise>
                               <c:set var="s" value="" scope="request" />
                           </c:otherwise>
                       </c:choose>
                       <input type="checkbox" name="autorizadoSalidaSoportes" value="on"  ${s} /><br />
                       
                       <label for="autorizadoEntradaSoportes">Está autorizado para recepción de soportes:</label>
                       <c:choose>
                           <c:when test="${d.autorizadoEntradaSoportes}">
                               <c:set var="s" value="checked" scope="request" />
                           </c:when>
                           <c:otherwise>
                               <c:set var="s" value="" scope="request" />
                           </c:otherwise>
                       </c:choose>
                       <input type="checkbox" name="autorizadoEntradaSoportes" value="on"  ${s} /><br />
                       
                       <label for="autorizadoCopiaReproduccion">Está autorizado para copia/reproducción de documentos:</label>
                       <c:choose>
                           <c:when test="${d.autorizadoCopiaReproduccion}">
                               <c:set var="s" value="checked" scope="request" />
                           </c:when>
                           <c:otherwise>
                               <c:set var="s" value="" scope="request" />
                           </c:otherwise>
                       </c:choose>
                       <input type="checkbox" name="autorizadoCopiaReproduccion" value="on"  ${s} /><br /><br />
                       <div id="ficherosUsuario">
                           <fieldset class="selector">
                               <legend>Gestión de los ficheros a los que accede esta persona</legend>
                               <input type="hidden" name="ficheros" id="ficheros" value="" class="" />
                               <fieldset class="ulSelectorFielset">
                                   <legend>Ficheros Disponibles</legend>
                                   <ul id="ficherosDisponibles" class="droptrue">
                                       <c:forEach var="fichero" items="${cDetails.ficherosDisponibles}">
                                           <li class="ui-state-default" id="${fichero.nombre}" title="${fichero.descripcion}">
                                               <c:out value="${fichero.nombre}" />
                                           </li>
                                       </c:forEach>                                         
                                   </ul>
                               </fieldset>
                               <fieldset class="ulSelectorFielset">
                                   <legend>Accede a Estos Ficheros</legend>
                                   <ul id="ficherosHabilitadas" class="droptrue">
                                       <c:forEach var="fichero" items="${cDetails.ficherosHabilitados}">
                                           <li class="ui-state-default" id="${fichero.nombre}" title="${fichero.descripcion}">
                                               <c:out value="${fichero.nombre}" />
                                           </li>
                                       </c:forEach>
                                   </ul>
                               </fieldset>
                           </fieldset>
                       </div> <br />
                       <%-- Gestión de la persona como Usuario del Sistema. --%>
                       <c:if test="${cDetails.appAdmin}">
                           <label for="esUsuario">Permitir acceso como Usuario:</label>
                           <c:choose>
                               <c:when test="${cDetails.isSystemUser(d.usuario)}">
                                   <c:set var="s" value="checked" scope="request" />
                               </c:when>
                               <c:otherwise>
                                   <c:set var="s" value="" scope="request" />
                               </c:otherwise>
                           </c:choose>
                           <input type="checkbox" id="esUsuario" name="esUsuario" value="ON"  ${s} /><br /><br />
                           <div id="permisosUsuario" style="display:none">
                               <fieldset class="selector">
                                   <legend>Gestión de los grupos del usuario</legend>
                                   <input type="hidden" name="grupos" id="grupos" value="" class="" />
                                   <fieldset class="ulSelectorFielset">
                                       <legend>Grupos Disponibles</legend>
                                       <ul id="gruposDisponibles" class="droptrue">
                                           <c:forEach var="grupo" items="${cDetails.getGruposDisponibles(d.email)}">
                                               <li class="ui-state-default" id="${grupo.empresasGruposPK.idGrupo}" title="${grupo.descripcion}">
                                                   <c:out value="${grupo.nombre}" />
                                               </li>
                                           </c:forEach>                                           
                                       </ul>
                                   </fieldset>
                                   <fieldset class="ulSelectorFielset">
                                       <legend>Grupos Habilitados</legend>
                                       <ul id="gruposHabilitadas" class="droptrue">
                                           <c:forEach var="grupo" items="${cDetails.getGruposHabilitados(d.email)}">
                                               <li class="ui-state-default" id="${grupo.empresasGruposPK.idGrupo}" title="${grupo.descripcion}">
                                                   <c:out value="${grupo.nombre}" />
                                               </li>
                                           </c:forEach> 
                                       </ul>
                                   </fieldset>
                               </fieldset>
                           </div>                       
                       </c:if>
                       <button id="enviarButton" class="button">
                           <fmt:message bundle="${StandardBundle}" key="button.enviar"/>
                       </button>
                       <a class="button" href="./personas.jsp">
                           <fmt:message bundle="${StandardBundle}" key="button.volver"/>
                       </a>
                    </form>                    
                </div>
                <jsp:directive.include file="/WEB-INF/jspf/common/standard/pieweb.jspf" />
            </div>
            <div id="extraFooter">
            </div>
            <jsp:directive.include file="/WEB-INF/jspf/common/standard/extraDivs.jspf" />
    </body>
</html>
