<%-- 
    Document   : lostPassResponse
    Created on : 20-may-2012, 9:03:39
    Author     : Eduardo L. García Glez.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />

<jsp:useBean id="cLostPass" class="com.openlopd.web.controllers.publicarea.ajax.CLostPass" scope="request" />
<jsp:setProperty name="cLostPass" property="*" />
<div id="lostPassRecInf">
    <c:choose>
        <c:when test="${cLostPass.initRecoveryProccess}">
        <div class="ui-widget">
            <div style="margin-top: 20px; padding: 0 .7em;" class="ui-state-highlight ui-corner-all">
                <p><span style="float: left; margin-right: .3em;" class="ui-icon ui-icon-info"></span>
                    <strong>¡Hola!</strong> <fmt:message bundle="${StandardBundle}" key="lostPass.ok.text" /></p>
            </div>
        </div>
        </c:when>
        <c:otherwise>
            <div class="ui-widget">
                <div style="padding: 0 .7em;" class="ui-state-error ui-corner-all">
                    <p><span style="float: left; margin-right: .3em;" class="ui-icon ui-icon-alert"></span>
                        <strong>Alerta:</strong> <fmt:message bundle="${StandardBundle}" key="lostPass.fail.text" /></p>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>

