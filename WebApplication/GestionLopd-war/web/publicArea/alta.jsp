<%-- 
    Document   : alta
    Created on : 09-ene-2011, 13:48:43
    Author     : Eduardo L. Garcia Glez.
    Versión    : 1.0.4
    Modificacion:
        14 de mar de 2011 Modificada la ubicación del listado de formas de pago.
        14 de mar de 2011 Modificado el javascript para evitar problemas en el envío
        cuando se recarga el formulario ya que no detectava a la localidad como seleccionada
        y permitía enviar el formulario.
        16 de mar de 2011 Mejorada la lógica de la validación del formulario, se añaden
        además los campos para introducir la clave.
        16 de mar de 2011, Se checkea que esté seleccionada la forma de pago.
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />
<jsp:useBean id="altaBean" scope="page" class="public_area.Alta" />
<jsp:setProperty property="*" name="altaBean" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/common/standard/headSection.jspf" />
        <link rel="stylesheet" type="text/css" href="${cssPath}/logincontrol.min.css" />
        <link rel="stylesheet" type="text/css" href="${cssPath}/index.min.css" />
        <script type="text/javascript" charset="utf-8">
            $(document).ready(function() {
                $('#appContent1').css("display", "none");

                $('#continuarButton').click(function(){
                    $('#appContent3').css("display", "none");
                    $('#appContent1').fadeIn();
                });                

                $('#form1').submit(function(){ 
                    var estado;
                    
                    $('#datosRequeridos').hide();
                    $('#passFormatRequired').hide();
                    $('#polPrivacidadRequerida').hide();
                    
                    estado = checkCamposDatosEmpresa();
                    estado = checkCamposPersonaContacto() && estado;
                    estado = checkCamposFormaPago() && estado;
                    estado = checkCamposClave() && estado;
                    estado = checkSend("#aceptaPolitica") && estado;
                    if (!estado)
                        $('#datosRequeridos').fadeIn();
                    if (!checkCamposClave())
                        $('#passFormatRequired').fadeIn();
                    if (!checkSend("#aceptaPolitica"))
                        $('#polPrivacidadRequerida').fadeIn();
                        
                    return estado;
                });
                
                $("#cif").change(function(){
                    $("#cif").val($("#cif").val().toUpperCase());
                });
                
                cargarProvincias("${appRoot}");

                $("#destino").load("../common/ajax/localidades.jsp", {idProvincia: $("#provincia").val()});
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
                            <li><a href="tarifas.jsp"><fmt:message bundle="${StandardBundle}" key="label.tarifas" /> &gt;</a></li>
                            <li><a href="alta.jsp"><fmt:message bundle="${StandardBundle}" key="label.alta" /></a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div id="contentArea">
                <div id="contentRow">
                    <jsp:directive.include file="/WEB-INF/jspf/public/linkList.jspf" />
                    <div id="appArea">
                        <div id="appContent3">
                            <h3>Descripción del proceso de alta</h3>
                            <ol>
                                <li><p class="">Una vez que pulse el botón <b>continuar</b> que se encuentra al pié de la descripción del proceso de alta se le solicitará que cumplimente un formulario con los datos de la empresa y de una persona de contacto a la cual nos dirigiremos en caso de tener que ponernos en contacto con su empresa.</p></li>
                                <%--<li><p class="">Al enviar el formulario se le mostrará una página de validación de datos, en ella podrá revisar la información antes de proceder a la formalización del contrato.</p></li>--%>
                                <li><p class="">Al enviar el formulario se le mostrará una página de validación de datos, en ella podrá revisar la información antes de proceder al alta.</p></li>
                                <%--<li><p class="">Cuando haya revisado los datos y pulse el botón <b>Contratar</b> se procederá a solicitar la información de cobro del importe del contrato tras lo cual aunque dependiendo de la forma de pago elegida podrá acceder al producto contratado.</li>--%>
                                <li><p class="">Cuando haya revisado los datos y pulse el botón <b>Finalizar</b> podrá acceder al la aplicación.</li>
                                <p class="p1"></p>
                            </ol>
                            <input id="continuarButton" class="sendButton" type="submit" value="Continuar" name="Continuar" />
                        </div>
                        <div id="appContent1">
                            <h3>Formulario de Alta</h3>
                            <%--<p class="p1"><span>Se encuentra en el formulario de alta, en el se le solicitaran
                                    todos los datos que son necesarios para la contratación de nuestros servicios y se
                                    tarda solo cinco minutos en realizar el proceso,
                                    todos <b>los campos que están marcados con (*) son obligatorios</b> y sin ellos no podremos
                                    proceder a darle de alta en el sistema, en caso de que el sistema detecte algún problema
                                    relacionado con sus datos le informará y en la mayoría de los casos le dirá donde se encuentra
                                    el problema.</span></p>
                                    <p class="p1"><span>Si quiere información a cerca del proceso de alta acceda a
                                        <a href="">"Descripción del proceso de alta"</a></span></p>--%>
                            <p class="p1"><span>Se encuentra en el formulario de alta, en el se le solicitaran
                                    todos los datos que son necesarios para en nuestros servicios y se
                                    tarda solo cinco minutos en realizar el proceso,
                                    todos <b>los campos que están marcados con (*) son obligatorios</b> y sin ellos no podremos
                                    proceder a darle de alta en el sistema, en caso de que el sistema detecte algún problema
                                    relacionado con sus datos le informará y en la mayoría de los casos le dirá donde se encuentra
                                    el problema.</span></p>
                                    <p class="p1"><span>Si quiere información a cerca del proceso de alta acceda a
                                        <a href="">"Descripción del proceso de alta"</a></span></p>
                            <form action="infoRegistro.jsp" method="GET" id="form1">
                                <h3 style="display: none;"><fmt:message bundle="${StandardBundle}" key="title.tipoPaquete" /></h3>
                                <select name="tipoPaquete" style="display: none;">
                                    <option><fmt:message bundle="${StandardBundle}" key="option.sinAsignar" /></option>
                                    <option 
                                        <c:if test="${altaBean.tipoPaquete == 0}">
                                            <c:out value="selected=\"selected\"" />
                                        </c:if> value="0">Básico</option>                                     
                                    <option 
                                        <c:if test="${altaBean.tipoPaquete == 1}">
                                            <c:out value="selected=\"selected\"" />
                                        </c:if> value="1">Normal</option>
                                    <option 
                                        <c:if test="${altaBean.tipoPaquete == 2}">
                                            <c:out value="selected=\"selected\"" />
                                        </c:if> value="2">Extra</option>
                                    <option 
                                        <c:if test="${altaBean.tipoPaquete == 5}">
                                            <c:out value="selected=\"selected\"" />
                                        </c:if> value="5">Free</option>
                                </select>
                                <jsp:directive.include file="/WEB-INF/jspf/common/empresa/setDatosEmpresa.jspf" />
                                <jsp:directive.include file="/WEB-INF/jspf/common/personas/setPersonaContacto.jspf" />
                                <div id="appContent2">
                                    <jsp:directive.include file="/WEB-INF/jspf/common/facturacion/setFormaPago.jspf" />
                                </div>
                                <div id="formSend">
                                    <p><input id="aceptaPolitica" class="inputNoDecor" type="checkbox" name="aceptaPolitica"/><a href="privacidad.jsp" target="_blank"><span><fmt:message bundle="${StandardBundle}" key="label.aceptoCondiciones" /></span></a></p>
                                    <input class="sendButton" type="submit" value="Continuar" />
                                    <jsp:directive.include file="/WEB-INF/jspf/common/standard/datosRequeridos.jspf" />
                                </div>
                            </form>
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
