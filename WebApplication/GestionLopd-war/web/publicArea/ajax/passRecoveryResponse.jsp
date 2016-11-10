<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%-- 
    Document   : passRecoveryResponse
    Created on : 20-may-2012, 19:09:52
    Author     : Eduardo L. García Glez.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />
<jsp:useBean id="cPassRecovery" class="com.openlopd.web.controllers.publicarea.ajax.CPassRecovery" scope="request" />
<jsp:setProperty name="cPassRecovery" property="*" />
<c:choose>
    <c:when test="${cPassRecovery.changePass}">
        <div class="ui-widget">
            <div style="margin-top: 20px; padding: 0 .7em;" class="ui-state-highlight ui-corner-all">
                <p><span style="float: left; margin-right: .3em;" class="ui-icon ui-icon-info"></span>
                    <strong>¡Hola!</strong> <fmt:message bundle="${StandardBundle}" key="info.changePass.ok" /></p>
            </div>
        </div>        
    </c:when>
    <c:otherwise>
        <div class="ui-widget">
            <div style="padding: 0 .7em;" class="ui-state-error ui-corner-all">
                <p><span style="float: left; margin-right: .3em;" class="ui-icon ui-icon-alert"></span>
                    <strong>Alerta:</strong> <fmt:message bundle="${StandardBundle}" key="info.changePass.error" /></p>
            </div>
        </div>
    </c:otherwise>
</c:choose>


