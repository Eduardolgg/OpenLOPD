<%-- 
    Document   : procedimientos
    Created on : 27-sep-2012, 16:55:21
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%-- Verificación de permiso de lectura de esta página. --%>
<jsp:useBean id="cProcedimientos" scope="request" class="com.openlopd.web.controllers.privatearea.procedimientos.CProcedimientos" />
<jsp:setProperty name="cProcedimientos" property="*" />
<jsp:setProperty name="cProcedimientos" property="session" value="${cSession}" />
<c:if test="${!cProcedimientos.readable}">
    <jsp:forward page="/common/error.jsp?e=read" />
</c:if>

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/private/headSection.jspf" />
        <c:set var="tipoProc" value="${cProcedimientos.tipoProcedimiento}" scope="page" />
        
        <script type="text/javascript">
            var onScreenHelp;
            
            function startOnScreenHelpSinFiltro() {
                               
                onScreenHelp = $("body").onScreenHelp(
                    [{	
                        selector : "#titleProtocolos",
                        description : "Te encuentras en la pantalla de configuración de protocolos.",
                        position: "bottom",
                        startWith: true
                    },{	
                        selector : "#titleProtocolos",
                        description : "Estos son los protocolos que serán incluidos en el documento de seguridad.",
                        position: "bottom"
                    },{
                        selector : "#filtrar",
                        description : "Para facilitar la tarea de configurar los protocolos pudes pulsar este botón",
                        position: "right"
                    },{
                        selector : "#titleProtocolos",
                        description : "Si has aplicado el filtrado por tipo podrás ver aquí información del protocolo en el que te encuentras.",
                        position: "bottom"
                    },{
                        selector : "#availableProtocolsContainer",
                        description : "Estos son todos los protocolos que tienes disponibles.",
                        position: "right"
                    },{
                        selector : "#enabledProtocolsContainer",
                        description : "Estos son todos los protocolos que tienes habilitados y que forman parte de tu documento de seguridad.",
                        potition: "left"
                    },{
                        selector : "#selectedProtocoldiv",
                        description : "Aquí puedes ver el texto del protocolo y es el que será incluido en el documento de seguridad cuando lo generes.",
                        position: "top"
                    },{
                        selector : "#filtrar",
                        description : "Te recomendamos que utilices el filtrado por tipo de protocolo, te será me mayor utilidad en la mayoría de los casos.",
                        position: "right"
                    },{
                        selector : "#screenHelp",
                        description : "También te recomendamos que cuando entres el el filtrado de protocolos vuelvas a abrir la ayuda en pantalla, ya que verán información adicional.",
                        position: "right"//,
                        //runAfter: closeHelptOnScreen()
                    }],{
                        // global options
                        scrollAlways : true, // allways scroll to the next / prev step, can be overwritten through step's setting (default => true)
                        hideKeyCode : 27, // close button key (default => 27)
                        allowEventPropagation : true, //(default => true)
                        autoPagination: 20000,
                        closeOnEnd: true
                    });
            }
            
            function startOnScreenHelpConFiltro() {
                               
                onScreenHelp = $("body").onScreenHelp(
                    [{	
                        selector : "#titleProtocolos",
                        description : "Te encuentras configurando los protocolos que se incluirán en tu documento de seguridad.",
                        //position: "bottom",
                        startWith: true
                    },{	
                        selector : "#tipoProcName",
                        description : "Este es el protocolo en el que te encuentras.",
                        position: "bottom"
                    },{
                        selector : "#tipoProcInfo",
                        description : "Esta es la descripción del protocolo, te ayudará leerla para definir tu protocolo",
                        position: "bottom"
                    },{
                        selector : "#availableProtocolsContainer",
                        description : "Estos son los protocolos disponibles para el tipo de protocolo actual.",
                        position: "right"
                    },{
                        selector : "#enabledProtocolsContainer",
                        description : "Estos son los protocolos habilitados para el tipo de protocolo actual, son los que se mostrarán en tu documento de seguridad.",
                        position: "left"
                    },{
                        selector : "#selectedProtocoldiv",
                        description : "Aquí puedes ver el texto del protocolo, es el texto que se incluirá en el documento de seguridad.",
                        position: "top"
                    },{
                        selector : "#addProtocoloBt",
                        description : "En caso de que no encuentres un protocolo adecuado a ti puedes crear uno nuevo pulsando este botón.",
                        position: "right"
                    },{
                        selector : "#addProtocoloBt",
                        description : "Una vez creado el nuevo protocolo se añadirá en Protocolos disponibles y podrás habilitarlo de la misma manera que el resto de protocolos.",
                        position: "right"
                    },{
                        selector : "#botonera",
                        description : "Una vez que tengas habilitados todos los elementos que te interesas puedes cambiar al siguiente tipo de protocolo a través de estos botones.",
                        position: "bottom"//,
                        //runAfter: closeHelptOnScreen()
                    }],{
                        // global options
                        scrollAlways : true, // allways scroll to the next / prev step, can be overwritten through step's setting (default => true)
                        hideKeyCode : 27, // close button key (default => 27)
                        allowEventPropagation : true, //(default => true)
                        autoPagination: 20000,
                        closeOnEnd: true
                    });
            }
            
            function startOnScreenHelp() { 
                if (${tipoProc == null}) {
                    startOnScreenHelpSinFiltro();
                } else {
                    startOnScreenHelpConFiltro();
                }
            }
            
            $(document).ready(function(){     
                
                $(document).tooltip(getTooltipConfig());
                
                <%-- 
                        Código para Añadir nuevos protocolos 
                --%>
                $(".button").button();
                $(".lock").button("disable");
                $("#cancelar").click(function(){
                        $("#addProtocoloDiv").dialog("close");
                        $('#addProtocoloForm').resetForm();   
                        
                });
                
                $("#addProtocoloDiv").dialog({
                    width: "450px",
                    autoOpen: false,
                    modal: true
                });
                
                $("#addProtocoloBt").click(function() {
                   $("#addProtocoloDiv").dialog("open");
                });
                
                $("#addProtocoloForm").validate();
                $('#addProtocoloForm').ajaxForm({
                    beforeSubmit: function(){
                        appStatusLoading(); 
                    },
                    error: function() {
                        appStatusError();
                    },
                    success: function(data) {                     
                        $("#addProtocoloDiv").dialog("close");
                        $('#addProtocoloForm').resetForm();
                        appStatusComplete();
                        showAppMsg(addProc(data));
                        $("#availableProtocols").animate({scrollTop: $("#availableProtocols").height() + 100}, 1000);
                    }                    
                });
               
               <%-- 
                  Gestión de activación/desactivación de protocolos.
               --%>
                $('.dropme').sortable({
                    connectWith: '.dropme',
                    cursor: 'pointer',
                    receive: function(event, ui) {
                        //protocolClick(event, ui);
                    }
                }).droppable({
                    accept: '.selectableItem',
                    activeClass: 'highlight',
                    drop: function(event, ui) {
                        /*var $li = $('<div>').html('List ' + ui.draggable.html());
                        $li.appendTo(this);*/
        
                        protocolClick(event, ui);
                    }
                });
                
                $(".dropme").on("mouseup", ".selectableItem",
                    function(event){
                        $("#SelProtocolText").empty();
                            var protocolo = $(this).find(".hiddenProc").text();
                            $("#SelProtocolText").append(protocolo);
                        }
                );     
                
                <%--
                $('#appArea').layout({ applyDefaultStyles: true });
                
                $('div.ui-layout-west').layout({
                    applyDefaultStyles: true,
                    minSize: 300 // ALL panes
                    , center__paneSelector: ".west-center"
                    , south__paneSelector: ".west-south"
                    , south__size: 300
                } ); --%>   
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
                         Se obtiene información del protocolo, se mostrará en caso 
                         de que se filtre por tipo de protocolo.
                    --%>                    
                    <c:choose>
                        <c:when test="${tipoProc != null}">
                            <h2 id="titleProtocolos"><span id="tipoProcName"><c:out value="Configuración de Protocolos: ${tipoProc.titulo}"/></span></h2>
                            <c:set var="idproc" value="${tipoProc.tipoProcedimientoPK.id}" scope="page"  />
                            <c:set var="descproc" value="${tipoProc.descripcion}" scope="page"  />
                            <div id="tipoProcInfo">
                                <p>
                                    <c:out value="${descproc}" />
                                    <span title="Puedes activar/desactivar un protocolo simplemente
                                         haciendo clic sobre el mismo y arrastrándolo." 
                                         class="help ui-icon ui-icon-lightbulb" 
                                         style="display: inline-block"></span>
                                </p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <h2 id="titleProtocolos">Configuración de Protocolos</h2>
                            <fmt:message bundle="${StandardBundle}" key="texto.proc.uso"/>
                            <c:set var="idproc" value="" scope="page"  />
                            <c:set var="descproc" value="" scope="page"  />                                    
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${cProcedimientos.tipoProcedimiento != null}">    
                        <div id="botonera" style="width: 220px;">
                            <c:set var="pAnterior" value="${cProcedimientos.anterior}" />
                            <c:if test="${pAnterior == null}">
                                <c:set var="pAnteriorLock" value="lock" />
                            </c:if>
                            <a class="button ${pAnteriorLock}" href="./procedimientos.jsp?tipo=${pAnterior}">
                                <fmt:message bundle="${StandardBundle}" key="button.anterior"/>
                            </a>
                            <c:set var="pSiguiente" value="${cProcedimientos.siguiente}" />
                            <c:if test="${pSiguiente == null}">
                                <c:set var="pSiguienteLock" value="lock" />
                            </c:if>
                            <a class="button ${pSiguienteLock}" href="./procedimientos.jsp?tipo=${pSiguiente}">
                                <fmt:message bundle="${StandardBundle}" key="button.siguiente"/>
                            </a>
                        </div>
                        </c:when>
                        <c:otherwise>
                            <c:set var="pSiguiente" value="${cProcedimientos.siguiente}" />
                            <c:if test="${pSiguiente == null}">
                                <c:set var="pSiguienteLock" value="lock" />
                            </c:if>
                            <a id="filtrar" title="Puede simplificar la configuración de los procedimientos
                            visualizandolos por tipo" class="button ${pSiguienteLock}" href="./procedimientos.jsp?tipo=${pSiguiente}">
                                Ver los procedimientos por tipo
                            </a>
                        </c:otherwise>
                    </c:choose>
                    <div class="ui-layout-west">
                        <div class="protocols" id="availableProtocolsContainer">
                            <h3 class="ui-dialog-titlebar ui-widget-header ui-corner-top ui-helper-clearfix">Protocolos Disponibles</h3>
                            <div id="availableProtocols" class="ui-widget-content resizable west-center">
                                <div class="dropme">
                                    <c:forEach var="proc" items="${cProcedimientos.procedimientosDisponibles}">
                                        <div id ="${proc.id}" class="ui-widget-content selectableItem">
                                            <div class="hiddenID"><c:out value="${proc.id}" /></div>
                                            <div class="Description">
                                                <c:out value="${proc.descripcion}" />
                                            <div class="level ${proc.nivel}">
                                                 <img alt="Nivel de seguridad ${proc.nivel}" 
                                                     src="../../images/bola${proc.nivel}.png" />
                                            </div>
                                            </div>
                                            <div class="hiddenProc">
                                                <c:out value="${proc.procedimiento}" />
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                        <div class="protocols" id="enabledProtocolsContainer">
                        <h3 class="ui-dialog-titlebar ui-widget-header ui-corner-top ui-helper-clearfix">Protocolos Habilitados</h3>
                        <div id="enabledProtocols" class="ui-widget-content resizable west-south">                            
                            <div class="dropme">
                                <c:forEach var="proc" items="${cProcedimientos.procedimientosHabilitados}">
                                    <div class="ui-widget-content selectableItem">
                                        <div class="hiddenID"><c:out value="${proc.procInfo.id}" /></div>
                                        <div class="Description">
                                            <c:out value="${proc.procInfo.descripcion}" />
                                            <div class="level ${proc.procInfo.nivel}">
                                                <img alt="Nivel de seguridad ${proc.procInfo.nivel}" 
                                                     src="../../images/bola${proc.procInfo.nivel}.png" />
                                            </div>
                                        </div>
                                        <div class="hiddenProc">
                                            <c:out value="${proc.procInfo.procedimiento}" />
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                        </div>
                    </div>                     
                    <div id="selectedProtocoldiv" class="protocols ui-layout-center">
                        <h3 class="ui-dialog-titlebar ui-widget-header ui-corner-top ui-helper-clearfix">Protocolo Seleccionado</h3>
                        <div id="selectedProtocol" class="ui-widget-content resizable ">                            
                            <div id="SelProtocolText"></div>
                        </div>                        
                    </div>
                    <c:if test="${tipoProc != null}">
                        <div id="addProtocoloDiv" title="Añadir Protocolo">
                            <form id="addProtocoloForm" action="AddData.jsp" method="POST">
                                <%--<label for="idProtocolo">Identificador del protocolo</label>
                                <input type="text" maxlength="37" name="idProtocolo" value="${idproc}" class="required" /><br />
                                <label for="tipoDescripcion">Descripcion del identificador de protocolos</label>
                                <textarea cols="80" rows="5" name="tipoDescripcion" maxlength="255" class="required" >${descproc}</textarea><br />
                                --%>
                                <input type="hidden" maxlength="37" name="idProtocolo" value="${cProcedimientos.tipo}" class="required" />
                                <input type="hidden" maxlength="1024" name="tipoDescripcion" value="${tipoProc.descripcion}" class="required" />
                                <input type="hidden" maxlength="30" name="tipoTitulo" value="${tipoProc.titulo}" class="required" />
                                <label for="procDescripcion">Descripción Protocolo</label>
                                <textarea cols="80" rows="5" name="procDescripcion" maxlength="255" class="required" ></textarea><br />
                                <label for="protocolo">Protocolo</label>
                                <textarea cols="80" rows="5" name="protocolo" maxlength="5000" class="required" ></textarea><br />
                                <label for="nivel">Nivel</label>
                                <select name="nivel" class="required" >
                                    <option value="Basico">Básico</option>
                                    <option value="Medio">Medio</option>
                                    <option value="Alto">Alto</option>
                                </select><br />
                                <div class="ui-dialog-buttonpane ui-widget-content ui-helper-clearfix">
                                    <input class="button" type="submit" value="Crear" />
                                    <input class="button" type="reset" value="Reset" />
                                    <input id="cancelar" class="button" type="submit" value="Cancelar" />
                                </div>
                            </form>
                        </div>
                        <button class="button"  id="addProtocoloBt">Añadir nuevo protocolo</button>                    
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
