<%-- 
    Document   : tarifas
    Created on : 09-ene-2011, 13:48:43
    Author     : Eduardo L. Garcia Glez.
    Versión    : 1.0.0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />
<jsp:useBean id="cTarifas" scope="session" class="public_area.CTarifas" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/common/standard/headSection.jspf" />
        <link rel="stylesheet" type="text/css" href="${cssPath}/logincontrol.min.css" />
        <link rel="stylesheet" type="text/css" href="${cssPath}/index.min.css" />
        <script type="text/javascript" charset="utf-8">
            $(document).ready(function() {
                $('#example').dataTable( {
                    "bPaginate": false,
                    "bLengthChange": false,
                    "bFilter": false,
                    "bSort": false,
                    "bInfo": false,
                    "bAutoWidth": false } );
            } );
            
        </script>
    </head>
    <body>
        <div id="container">
            <div id="indexLogin">
                <jsp:directive.include file="/WEB-INF/jspf/common/seguridad/loginControl.jspf" />
            </div>
            <div id="intro">
                <jsp:directive.include file="/WEB-INF/jspf/common/standard/encabezado.jspf" />
                <div id="extraHeader">
                    <div id="hormigas">
                        <ul>
                            <li><a href="../index.jsp"><fmt:message bundle="${StandardBundle}" key="label.inicio" /> &gt;</a></li>
                            <li><a href="tarifas.jsp"><fmt:message bundle="${StandardBundle}" key="label.tarifas" /></a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div id="contentArea">
                <div id="contentRow">
                    <jsp:directive.include file="/WEB-INF/jspf/public/linkList.jspf" />
                    <div id="appArea">
                        <div id="appContent1">
                            <h3><span><fmt:message bundle="${StandardBundle}" key="title.tarifas" /></span></h3>
                            <p class=""><fmt:message bundle="${StandardBundle}" key="texto.tarifasIntro" /></p>
                            <h3><span>Gestión LOPD</span></h3>
                            <table class="display" id="example" cellpadding="0" cellspacing="0" border="0">
                                <thead>
                                    <tr><th class="cbCellDesc"><span>Funcionalidades</span></th><th class="thCell"><span>Básico</span></th><th class="thCell"><span>Normal</span></th><th class="thCell"><span>Extra</span></th></tr>
                                </thead>
                                <tbody>
                                    <tr class="odd"><td class="cbCellDesc"><span>Asistene paso a paso</span></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td></tr>
                                    <tr class=""><td class="cbCellDesc"><span>Políticas de seguridad predefinidas</span></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td></tr>
                                    <tr class="odd"><td class="cbCellDesc"><span>Personalización de políticas de seguridad</span></td><td></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td></tr>
                                    <tr class=""><td class="cbCellDesc"><span>Generación del Documento de Seguridad</span></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td></tr>
                                    <tr class="odd"><td class="cbCellDesc"><span>Histórico Documentos de Seguridad</span></td><td></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td></tr>
                                    <tr class=""><td class="cbCellDesc"><span>Alta/Modificación/Supresión de ficheros de titularidad privada diréctamente con la AGPD</span></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td></tr>
                                    <tr class="odd"><td class="cbCellDesc"><span>Ficheros predefinidos</span></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td></tr>
                                    <tr class=""><td class="cbCellDesc"><span>Listado del personal con acceso a datos</span></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td></tr>
                                    <tr class="odd"><td class="cbCellDesc"><span>Registro de Incidencias</span></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td></tr>
                                    <tr class=""><td class="cbCellDesc"><span>Inventario de Soportes</span></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td></tr>
                                    <tr class="odd"><td class="cbCellDesc"><span>Registro de entrada/salida de soportes</span></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td></tr>
                                    <tr class=""><td class="cbCellDesc"><span>Contratos y cláusulas informativas para incluir en la documentación de la empresa</span></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td></tr>
                                    <tr class="odd"><td class="cbCellDesc"><span>Registro de accesos</span></td><td></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td></tr>
                                    <tr class=""><td class="cbCellDesc"><span>Sistema Multiusuario</span></td><td></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td></tr>
                                    <tr class="odd"><td class="cbCellDesc"><span>Personalización de plantillas</span></td><td></td><td></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td></tr>
                                    <%--<tr class=""><td class="cbCellDesc"><span>Gestión de Acceso Físico</span></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td></td><td></td></tr>
                                    <tr class="odd"><td class="cbCellDesc"><span>Alertas</span></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td><td></td><td></td></tr>
                                    <tr class="odd"><td class="cbCellDesc"><span>Auditorías</span></td><td></td><td></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td></tr>
                                    <tr class="odd"><td class="cbCellDesc"><span>Guía de elección de ficheros</span></td><td></td><td></td><td><center><img src="../images/bola.png" width="10" height="10" alt="back_disabled"/></center></td></tr>--%>
                                    <tr class="even"><td class="cbCellDesc"><span>Importe</span></td><td><c:out value="${cTarifas.importeB}"/>€</td><td><c:out value="${cTarifas.importeM}"/>€</td><td><c:out value="${cTarifas.importeP}"/>€</td></tr>
                                    <%--<tr class="even"><td class="cbCellDesc"></td><td></td><td></td><td></td></tr>--%>
                                    <tr class="even"><td class="cbCellDesc"><span></span></td>
                                        <td>
                                            <form action="alta.jsp" method="GET">
                                                <input type="hidden" name="tipoPaquete" value="0" />
                                                <input class="sendButton" type="submit" value="Alta" />
                                            </form>
                                        </td>
                                        <td>
                                            <form action="alta.jsp" method="POST">
                                                <input type="hidden" name="tipoPaquete" value="1" />
                                                <input class="sendButton" type="submit" value="Alta" />
                                            </form>
                                        </td>
                                        <td>
                                            <form action="alta.jsp" method="POST">
                                                <input type="hidden" name="tipoPaquete" value="2" />
                                                <input class="sendButton" type="submit" value="Alta" />
                                            </form>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
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
