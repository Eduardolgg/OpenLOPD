<%-- 
    Document   : lostPass
    Created on : 15-may-2012, 18:24:31
    Author     : Eduardo L. Garcia Glez.
    Versión    : 1.0.0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />
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
                        // Mostramos un mensaje con la respuesta
                        success: function(data) {
                            $('#callinfo').html(data);
                            $('#callinfo').show('blind');
                        }   
                    })        
                    return false;
                }); 
                
                $('#lostPassForm input[type="text"]').click(function(){
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
                            <li><a href="../index.jsp"><fmt:message bundle="${StandardBundle}" key="label.inicio" /> ></a></li>
                            <li><a href="login.jsp"><fmt:message bundle="${StandardBundle}" key="label.login" /> ></a></li>
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
                            <h3><span><fmt:message bundle="${StandardBundle}" key="Title.lostPass" /></span></h3>
                            <div id="lostPass">
                                <p><fmt:message bundle="${StandardBundle}" key="info.lostPass" /></p>
                                <form id="lostPassForm" class="formStyle" action="ajax/lostPassResponse.jsp" method="POST">
                                    <div class="formEntry">
                                        <label for="uid" class="formDesc">
                                            <%--<fmt:message bundle="${StandardBundle}" key="label.cif" />--%>
                                            Usuario
                                        </label>
                                        <div class="formData">
                                            <input type="text" name="uid" id="uid" class="formBoxMedio" value="" />
                                        </div>
                                    </div><%--
                                    <div class="formEntry">
                                        <label for="email" class="formDesc">
                                            <fmt:message bundle="${StandardBundle}" key="label.email" />
                                        </label>
                                        <div class="formData">
                                            <input type="text" name="email" id="email"  class="formBoxMedio" value="" />
                                        </div>
                                    </div>--%>
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
                                <%-- Carga del resultado de la llamada a lostPassResponse --%>
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