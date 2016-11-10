<%-- 
    Document   : passRecovery
    Created on : 20-may-2012, 17:44:45
    Author     : Eduardo L. García GLez.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />
<jsp:useBean id="cPassRecovery" class="com.openlopd.web.controllers.publicarea.ajax.CPassRecovery" scope="request" />
<jsp:setProperty name="cPassRecovery" property="*" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/common/standard/headSection.jspf" />
        <link rel="stylesheet" type="text/css" href="${cssPath}/logincontrol.min.css" />
        <link rel="stylesheet" type="text/css" href="${cssPath}/login.min.css" />
        <script type="text/javascript">
            function hideCallInfo() {
                if($('#callinfo').is(':visible')) {
                    $('#callinfo').hide('blind');
                }
            }
            $(document).ready(function(){                
                $('#lostPassForm').submit(function() {
                    hideCallInfo();
                    $.ajax({
                        type: 'POST',
                        url: $(this).attr('action'),
                        data: $(this).serialize(),
                        success: function(data) {
                            $('#callinfo').html(data);
                            $('#callinfo').show('blind');
                        }   
                    })        
                    return false;
                }); 
                
                $("#lostPassForm").validate({
                    rules:{
                        password:{
                            required: true,
                            minlength: 5
                        },
                        confirm_password: {
                            required: true,
                            minlength: 5,
                            equalTo: "#password"
                        }
                    }//,
                    //messages: {
                    //    confirm_password: {
                    //        equalTo: "Las contraseñas no son iguales"
                    //    }
                    //}
                });
                                
                $('#lostPassForm input[type="password"]').click(function(){
                    hideCallInfo();
                });
                
                hideCallInfo();
            })
        </script>
    </head>
    <body>
        <div id="container">
            <div id="intro">
                <jsp:directive.include file="/WEB-INF/jspf/common/standard/encabezado.jspf" />
                <div id="extraHeader">
                    <div id="hormigas">
                        <ul>
                            <li><a href="../index.jsp"><fmt:message bundle="${StandardBundle}" key="label.inicio" /> &gt;</a></li>
                            <li><a href="login.jsp"><fmt:message bundle="${StandardBundle}" key="label.login" /> &gt;</a></li>
                            <li><a href="lostPass.jsp"><fmt:message bundle="${StandardBundle}" key="Title.lostPass" /></a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div id="contentArea">
                <div id="contentRow">
                    <jsp:directive.include file="/WEB-INF/jspf/public/linkList.jspf" />
                    <div id="appArea">
                        <div id="appContent1">
                            <h3><span><fmt:message bundle="${StandardBundle}" key="Title.passRecovery" /></span></h3>
                            <div id="lostPass">
                                <p class="formInfo"><fmt:message bundle="${StandardBundle}" key="info.passRecovery" /></p>
                                <form id="lostPassForm" class="formStyle" action="ajax/passRecoveryResponse.jsp" method="POST">
                                    <div class="formEntry">
                                        <label for="password" class="formDesc">
                                            <fmt:message bundle="${StandardBundle}" key="label.clave" />
                                        </label>
                                        <div class="formData">
                                            <input type="password" name="password" id="password" value="" class="formBoxMedio required error" />
                                        </div>
                                    </div>
                                    <div class="formEntry">
                                        <label for="confirm_password" class="formDesc">
                                            <fmt:message bundle="${StandardBundle}" key="label.claveConfirmar" />
                                        </label>
                                        <div class="formData">
                                            <input type="password" name="confirm_password" id="confirm_password"  class="formBoxMedio required error" value="" />
                                        </div>
                                    </div>
                                    <input type="hidden" name="key" value="${cPassRecovery.key}" readonly="readonly" />   
                                    <div class="formEntry">
                                        <div class="formDesc"></div>
                                        <div class="formData"> 
                                            <input type="submit" id="submit" value="<fmt:message bundle="${StandardBundle}" key="button.enviar"/>" />
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div id="msgCarga">
                            </div>
                            <div id="callinfo">
                                <%-- Carga del resultado de la llamada a passRecoveryResponse --%>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <jsp:directive.include file="/WEB-INF/jspf/common/standard/pieweb.jspf" />

        </div>
        <div id="extraFooter">
        </div>
    </body>
</html>