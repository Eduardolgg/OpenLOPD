<%-- 
    Document   : index
    Created on : 10-may-2012, 19:48:36
    Author     : Eduardo L. García Glez.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<c:if test="${!cSession.logged}">
    <jsp:forward page="../publicArea/login.jsp" />
</c:if>
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%-- Verificación de permiso de lectura de esta página. --%>
<jsp:useBean id="cPrivateIndex" class="com.openlopd.web.controllers.privatearea.CPrivateIndex" scope="page" />
<jsp:setProperty name="cPrivateIndex" property="session" value="${cSession}" />
<c:if test="${!cPrivateIndex.readable}">
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
                        selector : "#logo",
                        description : "Esta es la barra de estado, te da información útil durante el manejo de la aplicación.",
                        startWith: true
                    },{	
                        selector : "#progress",
                        description : "Uno de los aspectos más interesantes de la barra de estado es el indicador de completado, este te informa sobre las tareas más importantes de la LOPD que has completado."
                    },{
                        selector : "#operaciones",
                        description : "Las operaciones que tienes que realizar se listan en este apartado, lo más recomendable es que empieces por resolver desde la parte superior a la parte inferior del cuadro",
                        position: "right"
                    },{
                        selector : "#operaciones",
                        html : 'Las operaciones están marcadas en su parte izquierda con un símbolo, <img src="../images/chk_on.png"> indica que la operación ha sido realizada y <img src="../images/chk_off.png"> que está pendiente de realizar.',
                        position: "right"
                    },{
                        selector : "#operaciones",
                        description : "Las operaciones tienen dependencias unas de otras, así que si ejecutas una operación puede hacer que otras de las operaciones se marquen como pendientes de realizar.",
                        position: "right"
                    },{
                        selector : "#helpBulb",
                        description : "Esta bombilla la verás en distintas partes de la aplicación, si mantienes el cursor del ratón sobre ella te dará ayuda rápida de la zona en la que te encuentres."
                    },{
                        selector : "#recomendacion",
                        description : "Siempre que tengas operaciones pendientes aquí podrás ver cuál es la que te recomendamos ejecutar."
                    },{
                        selector : "#linkList",
                        description : "Este es el menú de la aplicación, te da acceso a todas las operaciones que se pueden realizar.",
                        position: "right"
                    },{
                        selector : "#tareas",
                        description : "Y para que no pierdas tiempo, aquí en Tareas Habituales puedes encontrar los enlaces más comunmente utilizados.",
                        position: "left"
                    },{
                        selector : "#screenHelp",
                        description : "Si vuelves a necesitar ver este manual solo tienes que pulsar aquí. Además puedes pulsar este botón en otras secciones de la aplicación para ver su propia ayuda en pantalla.",
                        position: "right"//,
                        //runAfter: closeHelptOnScreen()
                    }],{
                        // global options
                        scrollAlways : false, // allways scroll to the next / prev step, can be overwritten through step's setting (default => true)
                        hideKeyCode : 27, // close button key (default => 27)
                        allowEventPropagation : true, //(default => true)
                        autoPagination: 20000,
                        closeOnEnd: true
                    });
            }
            
            $(document).ready(function(){
                $(document).tooltip();
                if('${cSession.accessInfo.userInfo.ultimoAcceso}'.length == 0 ) {
                    $("body").append('<div id="helpOnScreenDialog" title="Tutorial">' 
                    + 'Si quiere ver un tutorial para aprender a manejar la aplicación ' 
                    + 'pulse el botón "Ver tutorial", en caso contrario puede pulsar '
                    + 'el botón cancelar y ver el tutorial en cualquier otro momento '
                    + 'pulsando el botón "Ayuda en pantalla" que se encuentra en la '
                    + 'parte inferior del menú.</div>');
                    $("#helpOnScreenDialog").dialog({
                        modal: false,
                        resisable: false,
                        width: 500,
                        buttons: {
                            "Ver tutorial": function() {
                                startOnScreenHelp();                                
                                $(this).dialog("close");
                            },
                            "Cancelar": function() {
                                $(this).dialog("close");
                            }
                        }
                    });
                    
                }
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
                    <div id="operaciones">
                        <h1>Operaciones Realizadas/Pendientes <span id="helpBulb" style="display: inline-block" class="help ui-icon ui-icon-lightbulb" title="Sigue estos pasos para adecuar la empresa a la LOPD"></span></h1>
                        <ul id="indexCheckList" class="checklist">
                            <c:forEach var="op" items="${cLinkList.operations}">
                                <c:choose>
                                    <c:when test="${op.estado}">
                                        <c:set var="opStatusClass" value="ok" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="opStatusClass" value="no" />
                                    </c:otherwise>
                                </c:choose>
                                <li class="${opStatusClass}"><a href="${op.operacion.link}"><c:out value="${op.operacion.operacion}" /></a></li>
                             </c:forEach>   
                        </ul>
                    </div>
                    <div id="tareas" class="checklist">
                        <h1>Tareas Habituales <span style="display: inline-block" class="help ui-icon ui-icon-lightbulb"  title="Estos enlaces permiten acceder rápidamente a las tareas más habituales, estas puedes encontrarlas también en el menú principal"></span></h1>
                        <ul>
                            <c:forEach var="tarea" items="${cLinkList.tareas}" >
                                <li><a href="${tarea.link}">${tarea.operacion}</a></li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
            <jsp:directive.include file="/WEB-INF/jspf/common/standard/pieweb.jspf" />
        </div>
        <div id="extraFooter">
        </div>
        <jsp:directive.include file="/WEB-INF/jspf/common/standard/extraDivs.jspf" />
    </body>
</html>

