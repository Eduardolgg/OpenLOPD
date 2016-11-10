<%-- 
    Document   : destinatarios
    Created on : 16-jun-2012
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
<jsp:useBean id="cDocumentoSeguridad" class="com.openlopd.web.controllers.privatearea.documentoseguridad.CDocumentoSeguridad" scope="page" />
<jsp:setProperty name="cDocumentoSeguridad" property="session" value="${cSession}" />
<c:if test="${!cDocumentoSeguridad.readable}">
    <jsp:forward page="/common/error.jsp?e=read" />
</c:if>

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/private/headSection.jspf" />
        <style type="text/css">
            .dataTables_filter {
                display: none;
            }
        </style>
        <script type="text/javascript"> 
            
            function addActions() {
                $("#infofile").hide('blind');
                $("#file_upload").show('blind');
                $("#msgfile").remove("Fichero añadido: " + file.name 
                        + " " + obj.size + " bytes"
                        + "<br> MD5: "+ obj.md5);
            }
            $(document).ready(function(){
               //$("#enlaceajaxulFirst").menu();
               var dsTable = $('#myTable').dataTable({
                   "bJQueryUI": true,
                   "bServerSide": true,
                   "bDeferRender": true,
                   "bProcessing": true,
                   "sAjaxSource": "${appRoot}/privateArea/documentoSeguridad/jSonTable.jsp",
                   "sServerMethod": "POST",
                   "aaSorting": [[ 0, "desc" ]],
                   "sPaginationType": "full_numbers",
                   "aoColumnDefs": [                       
                       //NOTA: Adaptar este listado al número de columnas a recibir.
                         { "bSortable": false, "aTargets": [ 2 ] } 
                   ],
                   "oLanguage": {
                         "sUrl": "${libPath}/lang/dataTable_es_ES.txt"
                   }
               });      
               $("#genDocSeg").button();
               $("#genDocSeg").click(function () {
                    $("#genDocSeg").button("option", "disabled", true);
                    appStatusLoading();
                    $.getJSON('${appRoot}/privateArea/documentoSeguridad/genDS.jsp', 
                        function(data) {
                            if(data.status.toUpperCase() != 'OK') {
                                $("#msg").text(data.message); 
                                $("#msg").dialog('open');
                            } else  {
                                showAppMsg("Documento Generado.");
                            }                            
                            
                        }
                    )
                    //.success(function() { /* TODO: Indicar que el sistema está trabajando. */  })
                    .error(function() { 
                        appStatusError();
                        $("#msg").text('Existe un problema de comunicación con el servidor,' 
                            + ' por favor inténtelo más tarde.'); 
                        $("#msg").dialog('open');
                    })
                    .complete(function() { 
                        dsTable.fnDraw(); 
                        appStatusComplete();                        
                        $("#genDocSeg").button("option", "disabled", false);
                    });
               }); 
               $("#msg").dialog({
			height: 140,
			modal: true,
                        autoOpen: false
		})
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
                    <h1>Historial de Documentos de Seguridad</h1>
                    <p>En este apartado puedes generar el documento de seguridad de la empresa en el que se recogen las medidas de índole técnica y organizativa acordes a la normativa de seguridad vigente que es de obligado cumplimiento para el personal con acceso a los sistemas de información.</p>
                    <table cellpadding="0" cellspacing="0" border="0" class="display" id="myTable">
                        <thead>
                            <tr>	
                                <th>Fecha Alta</th>
                                <th>Fecha Baja</th>
                                <th>Documento Seguridad</th>
                            </tr>
                        </thead>
                        <tbody>		
                        </tbody>
                        <tfoot>
                            <tr>	
                                <th>Fecha Alta</th>
                                <th>Fecha Baja</th>  
                                <th>Documento Seguridad</th>                 
                            </tr>
                        </tfoot>
                    </table>
                    <c:if test="${cDocumentoSeguridad.writable}">
                        <button id="genDocSeg">Generar Documento de Seguridad</button>
                    </c:if>                    
                </div>
            </div>
            <jsp:directive.include file="/WEB-INF/jspf/common/standard/pieweb.jspf" />
        </div>
        <div id="extraFooter">
        </div>
        <dir id="msg" title="Estado" style="display: none;"></dir>
        <jsp:directive.include file="/WEB-INF/jspf/common/standard/extraDivs.jspf" />
    </body>
</html>