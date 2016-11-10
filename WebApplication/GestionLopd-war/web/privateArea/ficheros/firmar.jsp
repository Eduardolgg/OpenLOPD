<%-- 
    Document   : firmar
    Created on : 21-abr-2013, 9:31:53
    Author     : Eduardo L. García Glez.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<c:if test="${!cSession.logged}">
    <jsp:forward page="../../publicArea/login.jsp" />
</c:if>

<jsp:useBean id="cFirmar" class="com.openlopd.web.controllers.privatearea.ficheros.CFirmar" scope="page" />
<jsp:setProperty name="cFirmar" property="*" />
<jsp:setProperty name="cFirmar" property="session" value="${cSession}" />
<%-- Verificación de permiso de lectura de esta página. --%>
<c:if test="${!cFirmar.readable}">
    <jsp:forward page="/common/error.jsp?e=read" />
</c:if>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:directive.include file="/WEB-INF/jspf/private/headSection.jspf" />
        <script type="text/javascript" src="${libPath}/websigner/common-js/deployJava.js"></script>
        <script type="text/javascript" src="${libPath}/websigner/common-js/firma.js"></script>
        <script type="text/javascript" src="${libPath}/websigner/common-js/instalador.js"></script>
        <script type="text/javascript" src="${libPath}/websigner/constantes.js"></script>
        <script type="text/javascript" language="javascript">

            function configurarOperacion()
            {
                configuraFirma();

                // Configuramos la configuracion de firma por defecto
                //clienteFirma.setSignatureAlgorithm("SHA1withRSA");
                clienteFirma.setSignatureFormat("XMLDSig Enveloped");
                clienteFirma.setSignatureMode("implicit");
                //clienteFirma.setShowExpiratedCertificates(false);

                // Configuramos los ficheros de firma                
                fdatos = "${cFirmar.textToSign}";
                if (fdatos != null && fdatos != undefined && trim(fdatos) != "") {
                    clienteFirma.setData(fdatos);
                }
                ffirma = "";
                if (ffirma != null && ffirma != undefined && trim(ffirma) != "") {
                    clienteFirma.setElectronicSignatureFile(ffirma);
                }
            }

            function realizarOperacion()
            {
                // Limpiamos la configuracion del cliente
                initialize();

                // Configuramos los datos de la operacion
                configurarOperacion();

                // Ejecutamos la operacion de firma
                clienteFirma.sign();

                // Guardamos la firma en disco si todo ha salido bien
                if (!clienteFirma.isError()) {
                    //clienteFirma.saveSignToFile();
                    //alert(clienteFirma.getSignatureBase64Encoded());
                    $("#firmaB64").val(clienteFirma.getSignatureBase64Encoded());
                }

                /* Solo mostramos el error via JavaScript si se ha configurado 
                 * que no lo muestre el propio cliente */
                else if (!showErrors) {
                    alert(clienteFirma.getErrorMessage());
                }
            }

            function trim(cad)
            {
                return cad.replace(/^(\s|\t|\r|\n)*|(\s|\t|\r|\n)*$/g, "");
            }

        </script>
        <title>Firmar Notificación</title>
    </head>
    <body>
        <script type="text/javascript">
            cargarAppletFirma('MEDIA');
        </script>
        <form name="firmaCliente" id="firmaCliente" action="firmaSave.jsp" method="POST">
            <input type="hidden" name="idFichero" value="${cFirmar.id}" />
            <input type="hidden" name="firmaB64" id="firmaB64" value="" />
            <%--<input name="btnFirma" type="button" title="Firmar" value="Firmar" 
                   onclick="realizarOperacion(); return false;">--%>
        </form>
    </body>
</html>

        <script type="text/javascript">
            
            $(document).ready(function() {
                realizarOperacion();
                $("#firmaCliente").submit();                
            });

        </script>
