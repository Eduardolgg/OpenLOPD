<%-- 
    Document   : grupos
    Created on : 31-dic-2012, 9:59:53
    Author     : edu
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
<!DOCTYPE html>

<%-- Verificación de permiso de lectura de esta página. --%>
<jsp:useBean id="cGrupos" class="com.openlopd.web.controllers.privatearea.admin.grupos.CGrupos" scope="page" />
<jsp:setProperty name="cGrupos" property="session" value="${cSession}" />
<c:if test="${!cGrupos.readable}">
    <jsp:forward page="/common/error.jsp?e=read" />
</c:if>

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/private/headSection.jspf" />
        <script type="text/javascript">             
            $(document).ready(function(){
                $(".ajaxForm").ajaxForm();   
                $(".permisoClass").change(function() {
                    var data = "newValue=" + $(this).val();
                    $($(this).parent()).ajaxSubmit();
                    return false;
                });
                
                $("#tablePermisos").dataTable({
                   "bJQueryUI": true,
                   "bServerSide": false,
                   "sPaginationType": "full_numbers",
                   "iDisplayLength": 100,
                   "oLanguage": {
                         "sUrl": "${libPath}/lang/dataTable_es_ES.txt"
                   }
                });
                
                $("#addGroup").button({
                    icons: {
                        primary: "ui-icon-plus"
                    }
                }).click(function(){
                    $("#formAddGroup").dialog("open");
                });
                
                $("#formAddGroup").dialog({
                    autoOpen: false,
                    height: 300,
                    width: 350,
                    modal: true,
                    buttons: {
                        "Añadir": function() {
                            var bValid = false;
                            if($("#formAddGroup").valid()) {
                                $("#formAddGroup").submit();
                            }
                        },
                        "Cancelar": function() {
                            $( this ).dialog( "close" );
                        }
                    },
                    close: function() {
                        $("#formAddGroup input, #formAddGroup textarea")
                            .val( "" )
                            .removeClass( "ui-state-error" );
                    }
                }).validate();
            });
        </script>
        <style>
            .dataTables_filter {
                display: block;
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
                    <h1>Gestión de Grupos de Usuarios</h1>
                    <p>En este apartado puedes configurar los accesos de los grupos de usuarios, 
                        para ello solo tienes que modificar el permiso de cada apartado. 
                        Si mecesitas crear un nuevo grupo pulsa el botón bajo la tabla.</p>
                    <c:set var="grupos" value="${cGrupos.grupos}" />
                    <table cellpadding="0" cellspacing="0" border="0" class="display" id="tablePermisos">
                        <thead>
                            <tr>
                                <th></th>
                                <c:forEach var="g" items="${grupos}">
                                    <th title="${g.descripcion}"><c:out value="${g.nombre}" /></th>
                                </c:forEach>                        
                            </tr>
                        </thead>
                        <tbody>	
                            <c:forEach var="permiso" items="${cGrupos.columnasPermisos}">
                                <tr>
                                    <td><fmt:message bundle="${StandardBundle}" key="permiso.${permiso}"/></td>
                                    <c:forEach var="g" items="${grupos}">
                                        <td>
                                            <form class="ajaxForm" method="POST" action="./updatePermiso.jsp?p=${permiso}&g=${g.empresasGruposPK.idGrupo}">
                                                <c:set var="codPermiso" value="${cGrupos.getPermiso(g, permiso)}" />
                                                <c:choose>
                                                    <c:when test="${codPermiso == 0}">
                                                        <c:set var="pSinAcceso" value="selected=\"selected\"" />
                                                        <c:set var="pLectura" value="" />
                                                        <c:set var="pEscritura" value="" />
                                                    </c:when>
                                                    <c:when test="${codPermiso == 1}">
                                                        <c:set var="pSinAcceso" value="" />
                                                        <c:set var="pLectura" value="selected=\"selected\"" />
                                                        <c:set var="pEscritura" value="" />
                                                    </c:when>
                                                    <c:when test="${codPermiso == 3}">
                                                        <c:set var="pSinAcceso" value="" />
                                                        <c:set var="pLectura" value="" />
                                                        <c:set var="pEscritura" value="selected=\"selected\"" />
                                                    </c:when>
                                                    <c:otherwise>
                                                    </c:otherwise>
                                                </c:choose>
                                                <select name="newValue" class="permisoClass">
                                                    <option value="0" ${pSinAcceso}>Sin Acceso</option>
                                                    <option value="1" ${pLectura}>Lectura</option>
                                                    <option value="3" ${pEscritura}>Escritura</option>
                                                </select>
                                                <c:out value="" />
                                            </form>
                                        </td>
                                    </c:forEach>
                                </tr>                        
                            </c:forEach>  	
                        </tbody>                      
                    </table> 
                    <button id="addGroup" >
                        <fmt:message bundle="${StandardBundle}" key="button.alta"/>
                    </button> 
                    <form id="formAddGroup" title="Añadir Grupo" methos="POST"
                          action="addgroup.jsp">
                        <label for="nombre">Nombre</label>
                        <input type="text" name="nombre" value="" class="required" maxlength="25"/><br>
                        <label for="descripcion">Descripcion</label>
                        <textarea name="descripcion" rows="4" cols="20" 
                                  class="required" maxlength="100"></textarea>
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