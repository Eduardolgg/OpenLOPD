<%-- 
    Document   : nota
    Created on : 02-nov-2012, 17:13:13
    Author     : Eduardo L. García Glez.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />

<c:if test="${!cSession.logged}">
    <jsp:forward page="../../publicArea/login.jsp" />
</c:if>

<jsp:useBean id="cNota" class="com.openlopd.web.controllers.privatearea.ficheros.CNota" scope="request" />
<jsp:setProperty name="cNota" property="session" value="${cSession}"/>
<jsp:setProperty name="cNota" property="*" />
<c:if test="${!cNota.init()}">
    <script type="text/javascript">
        alert("Error en la Inicialización, consulte con el administrador del sistema.");
    </script>
</c:if>

<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />
<jsp:directive.include file="/WEB-INF/jspf/common/standard/formsTranslate.jspf" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<c:set var="checkboxValue" value="${cNota.getAGPDTrue()}" />

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/private/headSection.jspf" />
        <script type="text/javascript"> 
            var separador = ';';
            var onScreenHelp;
            
            function startOnScreenHelp() {                
                onScreenHelp = $("body").onScreenHelp(
                    [{	
                        selector : "#tabs ol",
                        description : "Estos son los distintos apartados de los que consta el formulario, cada uno de ellos tiene una bombilla en la que puedes obtener ayuda específica.",
                        position: "bottom",
                        startWith: true
                    },{	
                        selector : "#formEditDataOk",
                        description : "Este botón permite enviar el formulario a la Agencia de protección de datos.",
                        position: "top"
                    },{
                        selector : "#formEditDataCancel",
                        description : "Este botón hace que vuelvas a la pantalla anterior",
                        position: "top"
                    },{
                        selector : "#buttonBar",
                        description : "Estos botones te permiten moverte a través de las pestañas del formulario.",
                        position: "top"
                    }],{
                        // global options
                        scrollAlways : true, // allways scroll to the next / prev step, can be overwritten through step's setting (default => true)
                        hideKeyCode : 27, // close button key (default => 27)
                        allowEventPropagation : true, //(default => true)
                        autoPagination: 20000,
                        closeOnEnd: true
                    });
            }
            
            function nextTab(tabId) {
                active = $(tabId).tabs( "option", "active" );
                $(tabId).tabs( "option", "active", active + 1);
                return active + 1 == $("#tabs ol li").length - 1;
            }
            
            function previousTab(tabId) {
                active = $(tabId).tabs( "option", "active" );
                if (active > 0) {
                    $(tabId).tabs( "option", "active", active - 1);
                }
                return active - 1 == 0;
            }
            
            function cambiarTab(num){
                   $("#tabs").tabs( "option", "active", num);
                   revTabButtonStatus("#tabs");
                   heightRecalc();        
                   $('html, body').animate({ scrollTop: 0 }, 'slow');
            }
            
            function revTabButtonStatus(tabId) {
                active = $(tabId).tabs( "option", "active" );
                switch (active) {
                   case 0 :
                       $("#btAnterior").button("disable");                   
                       $("#btSiguiente").button("enable");
                       break;
                
                   case $("#tabs ol li").length - 1:
                       $("#btSiguiente").button("disable");                   
                       $("#btAnterior").button("enable");
                       break;
                   default:
                       $("#btSiguiente").button("enable");                   
                       $("#btAnterior").button("enable");
                }
            }
            
            function heightRecalc() {                
                   setUlHeight("#finalidadesDisponibles","#finalidadesHabilitadas");
                   setUlHeight("#colectivosDisponibles","#colectivosHabilitadas");
                   setUlHeight("#otrosDatosTipificadosDisponibles","#otrosDatosTipificadosHabilitadas");
                   setUlHeight("#destCesionesDisponibles","#destCesionesHabilitadas");
            }
    
    function setActivesLi(ulId, inputId) {  
        var inputIdLen = $(inputId).attr("id").length;
        $(inputId).val("");
        
        $(ulId + " li").each(function(index){
            $(inputId).val($(inputId).val() + separador + $(this).attr("id").substr(inputIdLen,$(this).attr("id").length)); 
        });       
        $(inputId).val($(inputId).val().substr(1, $(inputId).val().length));
    }
    
            function recalcPais() {
                $(".selProv").each(function(){
                    var nextId = nextId = $(this).attr("id").replace("Provincia","") + "Pais";
                    
                    if($(this).val() != "") {        
                        $("#" + nextId).val("ES");
                    } else {       
                        $("#" + nextId).val("");
                    }
                });
            }
    
    function setTransfInt() {
        $("#transCodPais").val("");
        $("#transCategoria").val("");
        
        $(".transPSelect").each(function(index){
            if ($("#transCodPais0" + (index + 1)).val() != "") {
                $("#transCodPais").val($("#transCodPais").val() + separador + $("#transCodPais0" + (index + 1)).val());
                if($("#transCodPais05").val() != "" && index < $(".transPSelect").length -1 ||
                    $("#transCodPais05").val() == "") {
                    $("#transCategoria").val($("#transCategoria").val() + separador + $("#transCategoria0" + (index + 1)).val());
                }
            }
        });
        
        if ($("#transCodPais05").val() == "") {
            $("#transCategoria05").val("");
        }
        
        $("#transCodPais").val($("#transCodPais").val().substr(1, $("#transCodPais").val().length));
        $("#transCategoria").val($("#transCategoria").val().substr(1, $("#transCategoria").val().length));
    }
            
    function submitForm(predefinido){
        // Se calcula el valor de los campos ocultos.
        setActivesLi("#finalidadesHabilitadas", "#finalidades");
        setActivesLi("#colectivosHabilitadas", "#colectivos");
        setActivesLi("#otrosDatosTipificadosHabilitadas", "#otrosDatosTipificados");
        setActivesLi("#destCesionesHabilitadas", "#destCesiones");
        setTransfInt();
        $("#predefinido").val(predefinido);
        $("#origenHidden").val($('.origen:checked').length > 0 ? "ok" : "");
        if($("#colectivos").val().length > 0 || $("#otrosColectivos").val().length > 0) {
            $("#colectivos").removeClass("required");
        } else {
            $("#colectivos").addClass("required");
        }
        $("#identifHidden").val($('.identif:checked').length > 0 || 
                $("#identifOtros").val().length > 0 ? "ok" : "");
        $("#nivelHidden").val($("input[name=nivel]:checked").length > 0 ? "ok" : "");
        $("#sistTratamientoHidden").val($("input[name=sistTratamiento]:checked").length > 0 ? "ok" : "");
        
        var formValidator = $("#formEditData").validate();
        if(!formValidator.form()) {
            $("#errContainer").dialog('open');
        }
    }
       
    function fieldSetDerechosCheckBox(){
        if($("#fieldSetDerechosCheckBox:checked").val() == "ON"){
            $("#fieldSetDerechos").hide('blind');
            $("#fieldSetDerechos input, #fieldSetDerechos select").val("");
            $(".derechosRequired").removeClass("required");
        } else {
            $("#fieldSetDerechos").show('blind');
            $(".derechosRequired").addClass("required");
        }
    }
    function fieldSetTratamientoCheckBox(){
        if($("#fieldSetTratamientoCheckBox:checked").val() == "ON"){
            $("#fieldSetTratamiento").hide('blind');
            $("#fieldSetTratamiento input, #fieldSetTratamiento select").val("");
            $(".encRequired").removeClass("required");
        } else {
            $("#fieldSetTratamiento").show('blind');
            $(".encRequired").addClass("required");
        }
    }
    function chOtrosColectivos(){
        if($("#chOtrosColectivos:checked").val() == "ON"){
            $("#otrosColectivos").show('blind');
        } else {
            $("#otrosColectivos").hide('blind');
            $("#otrosColectivos").val("");
        }
    }
    function chOtrosTiposDeDatos(){
        if($("#chOtrosTiposDeDatos:checked").val() == "ON"){
            $("#otrosTiposDeDatos").show('blind');
        } else {
            $("#otrosTiposDeDatos").hide('blind');
            $("#otrosTiposDeDatos").val("");
        }
    }
    function chOtrosDestCesiones(){
        if($("#chOtrosDestCesiones:checked").val() == "ON"){
            $("#otrosDestCesiones").show('blind')
        } else {
            $("#otrosDestCesiones").hide('blind');
            $("#otrosDestCesiones").val("");
        }
    }
               
            $(document).ready(function(){
               /* eliminada probremas en listas largas y uso del tabulador
                *$("select").transformSelect({useManualInputAsFilter : true});*/
                //$("formEditData").validate({lang:'es'});
               $(".help, .tab").tooltip({show: {delay: 300}});
               $(".dateTimePicker").datetimepicker(getDateTimePickerConfig());
               $(".button").button();
               //$("#enlaceajaxulFirst").menu();
               $(".dialog").dialog({autoOpen: false, width: "500px"});
               $(".help").click(function() {
                   var selector = "#" + $(this).attr('id') + "-d";
                  $(selector).dialog("open");
               });
               $(".autoLocalidad").autocomplete({
                    source: "../info/info.jsp?i=localidades",
                    minLength: 2     
               });               
               
               $( "#tabs" ).tabs({ 
                   show: { effect: "fade", duration: 800 }
                   //hide: { effect: "fade", duration: 400 }
               });
               //$( "#tabs" ).tabs( "option", "disabled", [ 3 ] ). 
               
               $("#tabs").click(function(){
                   revTabButtonStatus("#tabs");
               });
                              
               $("#btAnterior").click(function(event){
                   event.preventDefault();         
                   if (previousTab("#tabs")) {
                       $("#btAnterior").button("disable");
                   }
                   $("#btSiguiente").button("enable");
                   heightRecalc();        
                   $('html, body').animate({ scrollTop: 0 }, 'slow');
               });
               
               $("#btSiguiente").click(function(event){
                   event.preventDefault();         
                   if (nextTab("#tabs")) {
                       $("#btSiguiente").button("disable");
                   }
                   $("#btAnterior").button("enable");
                   heightRecalc();  
                   $('html, body').animate({ scrollTop: 0 }, 'slow');
               });
               
               $( "ul.droptrue" ).sortable({
                    connectWith: "ul"
               });
               
               fieldSetDerechosCheckBox();
               fieldSetTratamientoCheckBox();
               chOtrosColectivos();
               chOtrosTiposDeDatos();
               chOtrosDestCesiones();
               $("#fieldSetDerechosCheckBox").click(function() {fieldSetDerechosCheckBox()});               
               $("#fieldSetTratamientoCheckBox").click(function(){fieldSetTratamientoCheckBox()});               
               $("#chOtrosColectivos").click(function(){chOtrosColectivos()});               
               $("#chOtrosTiposDeDatos").click(function(){chOtrosTiposDeDatos()});               
               $("#chOtrosDestCesiones").click(function(){chOtrosDestCesiones()});               
               
               $( "#finalidadesDisponibles, #finalidadesHabilitadas" ).disableSelection();
               
               $("#finalidadesDisponibles,  #finalidadesHabilitadas, #colectivosDisponibles,  #colectivosHabilitadas, " +
            "#otrosDatosTipificadosDisponibles, #otrosDatosTipificadosHabilitadas, " +
            "#destCesionesDisponibles, #destCesionesHabilitadas").resize(function(event){
                   heightRecalc();                  
               });
               
               $(".tab").click(function(event){
                   heightRecalc();                  
               });
               
               $("#formEditDataCancel").click(function(){
                   window.location.href = "ficheros.jsp";
               });  
               
               $(".transPSelect").change(function(){
                   if ($(this).val() != "") {
                       $("#transCategoria" + $(this).attr("id").substr(12,2)).show("slide");
                   } else  {
                       //$("#transCategoria" + $(this).attr("id").substr(12,2)).val("");
                       $("#transCategoria" + $(this).attr("id").substr(12,2)).hide("slide");
                   }                   
               });
               
               
               $("#errContainer").dialog({
                   autoOpen: false,
                   modal:true,
                   buttons: {ok: function(){ $("#errContainer").dialog('close');   }}
               });
               //$.metadata.setType("attr", "validate");
               $("#formEditData").validate({
                   //errorPlacement: function(error, element) {
                   //    $("#errContainer").empty();
                   //    error.appendTo($("#errContainer"));
                   //},
                   //showErrors: function(errorMap, errorList) {
                   //     this.defaultShowErrors();
                   //     $("#errContainer").dialog('open');   
                   //}
               });
              $("#btAnterior").button("disable");
              $(".selProv").change(function(){
                  recalcPais();
              });
              recalcPais();
              $(".transCSelect").each(function(index, value){
                if($(value).val().length > 0) {
                    $(value).show();
                }
              });
              var addedFirmaConfigIFrame = false;
              $("#configFirmaLink").click(function() {
                  if (!addedFirmaConfigIFrame) {
                      $("#configFirmaDialog").append('<iframe style="border:none;" src="${appRoot}/publicArea/ConfCertificado.jsp" width="100%" height="350px"></iframe>');
                      addedFirmaConfigIFrame = !addedFirmaConfigIFrame;
                  }                  
                  $("#configFirmaDialog").dialog({ 
                      resizable: false,
                      height:470,
                      width: '70%',
                      modal: true,
                      buttons: {
                      "Cerrar": function() {
                            $( this ).dialog( "close" );
                        }
                      }                      
                  });
              });
            });
        </script>
        <style type="text/css">
            #finalidadesDisponibles,  #finalidadesHabilitadas, #colectivosDisponibles,  #colectivosHabilitadas, 
            #otrosDatosTipificadosDisponibles, #otrosDatosTipificadosHabilitadas, 
            #destCesionesDisponibles, #destCesionesHabilitadas { 
                list-style-type: none; margin: 0; padding: 0; float: left; 
                margin-right: 10px; background: #eee; padding: 5px; width: 443px;
            }
            
            #finalidadesDisponibles li, #finalidadesHabilitadas li, #colectivosDisponibles li, #colectivosHabilitadas li,
            #otrosDatosTipificadosDisponibles li, #otrosDatosTipificadosHabilitadas li, 
            #destCesionesDisponibles li, #destCesionesHabilitadas li{ 
                margin: 5px; padding: 5px;  width: 420px; 
            }
            
            #fieldSetDerechos, #fieldSetTratamiento{
                display:none;
            }
            
            .rightLabel {
                padding-right: 15px;
            }
            
            .transCSelect {display: none}
            
            .tabOrdinal {display: none}
            /*.tabText {display: none}*/
            .refTab {font-weight: bold; cursor: pointer;}
            
            <c:if test="${!cNota.titularidadPublica}">
            .titPublica {
                display:none;
            }
            </c:if>
            
            <c:choose>
                <c:when test="${cNota.accionAlta}">
                    .accAlta{}
                    .accModif{}
                    .accSupres{display:none;}
                </c:when>
                <c:when test="${cNota.accionModificacion}">
                    .accAlta{}
                    .accModif{}
                    .accSupres{display:none;}
                </c:when>
                <c:when test="${cNota.accionSupresion}">
                    .accAlta{display:none;}
                    .accModif{display:none;}
                    .accSupres{}
                </c:when>
                <c:otherwise>
                    <%--//TODO error en el acceso al form--%>
                    .accAlta{display:none;}
                    .accModif{display:none;}
                    .accSupres{display:none;}
                </c:otherwise>
            </c:choose>

                    #btPredefinido {float: right; right: 15px;}
        </style>
    </head>
    <body>
        <c:choose>
            <c:when test="${cNota.accionAlta}">
                <c:set var="altaRequired" value="required" />
                <c:set var="modifRequired" value="" />
                <c:set var="suprRequired" value="" />
            </c:when>
            <c:when test="${cNota.accionModificacion}">
                <c:set var="altaRequired" value="" />
                <c:set var="modifRequired" value="required" />
                <c:set var="suprRequired" value="" />
            </c:when>
            <c:when test="${cNota.accionSupresion}">
                <c:set var="altaRequired" value="" />
                <c:set var="modifRequired" value="" />
                <c:set var="suprRequired" value="required" />
            </c:when>
            <c:otherwise>
            </c:otherwise>
        </c:choose>

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
                    <div id="errContainer" title="Información" style="display:none;">
                        <p>Se produjeron errores de validación del formulario, estos
                        han sido marcados junto a cada uno de los campos en los que
                        se produjo el error, por favor revise el formulario y vuelva
                        a inteneralo.</p>
                    </div>
                    <div style="margin-top: 20px; padding: 0 .7em;" class="nota ui-state-highlight ui-corner-all">
                        <p><span style="float: left; margin-right: .3em;" class="ui-icon ui-icon-info"></span>
                            Para poder firmar digitalmente la solicitud primero tienes que tener configurado tu equipo, 
                            puedes ver el proceso pulsando <a id="configFirmaLink" href="#">aquí</a></p>
                    </div>
                    <div id="configFirmaDialog"></div>
                    <form id="formEditData" action="notaSave.jsp" method="POST"> 
                        <input type="hidden" name="registrar" value="${cNota.registrar}" />
                        <input type="hidden" name="titularidad" value="${cNota.titularidad}" />
                        <input type="hidden" name="accion" value="${cNota.accion}" />
                        <input type="hidden" name="id" value="${cNota.id}" />
                        <input type="hidden" name="predefinido" id="predefinido" value="" />
                        
                        <div id="tabs">
                            <ol>
                                <%-- 
                                     Los números de los tabs corresponden con los apartados del 
                                     PDF de notificación (NOTA) de la AGPD, se debe seguir
                                     utilizando esta nomenclatura en sucesivas actualizaciones.
                                --%>
                                <c:if test="${cNota.accionAlta || cNota.accionModificacion}">
                                <li class="tab" title="Responsable del fichero"><a href="#tabs-1"><span class="tabOrdinal">1.- </span><span class="tabText">Responsable del fichero</span></a></li>
                                </c:if>
                                <c:if test="${cNota.accionAlta || cNota.accionModificacion}">
                                <li class="tab" title="Derechos de oposición, acceso, rectificación y cancelación"><a href="#tabs-2"><span class="tabOrdinal">2.- </span><span class="tabText">Derechos ARCO</span></a></li>
                                </c:if>
                                <%-- // TODO: El 3 es solo de titularidad Pública. --%>
                                <c:if test="${cNota.titularidadPublica && (cNota.accionAlta || cNota.accionModificacion)}">
                                    <li class="tab" title="Disposición general de creación, modificación o supresión"><a href="#tabs-3"><span class="tabOrdinal">3.- </span><span class="tabText">Disposición general de creación, modificación o supresión</span></a></li>
                                </c:if>  
                                <c:if test="${cNota.accionAlta || cNota.accionModificacion}">                              
                                <li class="tab" title="Encargado del tratamiento"><a href="#tabs-4"><span class="tabOrdinal">4.- </span><span class="tabText">Encargado del tratamiento</span></a></li>
                                </c:if>
                                <c:if test="${cNota.accionAlta || cNota.accionModificacion}">
                                <li class="tab" title="Identificación y finalidad del fichero"><a href="#tabs-5"><span class="tabOrdinal">5.- </span><span class="tabText">Identificación y finalidad</span></a></li>
                                </c:if>
                                <c:if test="${cNota.accionAlta || cNota.accionModificacion}">
                                <li class="tab" title="Origen y procedencia de los datos"><a href="#tabs-6"><span class="tabOrdinal">6.- </span><span class="tabText">Origen y procedencia de los datos</span></a></li>
                                </c:if>
                                <c:if test="${cNota.accionAlta || cNota.accionModificacion}">
                                <li class="tab" title="Tipos de datos, estructura y organización del fichero"><a href="#tabs-7"><span class="tabOrdinal">7.- </span><span class="tabText">Tipos de datos, estructura y organización</span></a></li>
                                </c:if>
                                <c:if test="${cNota.accionAlta || cNota.accionModificacion}">
                                <li class="tab" title="Medidas de seguridad"><a href="#tabs-8"><span class="tabOrdinal">8.- </span><span class="tabText">Medidas de seguridad</span></a></li>
                                </c:if>
                                <c:if test="${cNota.accionAlta || cNota.accionModificacion}">
                                <li class="tab" title="Cesión o comunicación de datos"><a href="#tabs-9"><span class="tabOrdinal">9.- </span><span class="tabText">Cesión o comunicación de datos</span></a></li>
                                </c:if>
                                <c:if test="${cNota.accionAlta || cNota.accionModificacion}">
                                <li class="tab" title="Transferencias internacionales"><a href="#tabs-10"><span class="tabOrdinal">10.- </span><span class="tabText">Transferencias internacionales</span></a></li>
                                </c:if>
                                <c:if test="${cNota.accionSupresion}">
                                <li class="tab" title="Supresión del Fichero"><a href="#tabs-11"><span class="tabOrdinal">11.- </span><span class="tabText">Supresión del Fichero</span></a></li>
                                </c:if>
                                <%--<c:if test="${cSession.accessInfo.gestor}">--%>
                                <li class="tab" title="Persona física que actua en representación del responsable del fichero ante la AEPD"><a href="#tabs-0D"><span class="tabOrdinal">0D.- </span><span class="tabText">Declarante</span></a></li>
                                <%--</c:if>  --%>                              
                                <li class="tab" title="Dirección a efectos de notificación"><a href="#tabs-00"><span class="tabOrdinal">00.- </span><span class="tabText">Dirección de notificación</span></a></li>
                            </ol>
                            <div id="tabs-1" class="accAlta accModif">
                                <c:set var="sedeResponsable" scope="request" value="${cSession.accessInfo.subEmpresa.sedeLopd}" />
                                <h1>Responsable del fichero <span id="tabs-1-help" title="haz click para obtener ayuda"  class="help ui-icon ui-icon-lightbulb" 
                                         style="display: inline-block"></span></h1>
                                <label for="respDenominacionSocial">Denominación social del responsable del fichero</label>
                                <input type="text" name="respDenominacionSocial" value="${cSession.accessInfo.subEmpresa.razonSocial}"  readonly="readonly" maxlength="140" class="required" />
                                <label for="respActividad">Actividad</label>
                                <select name="respActividad" class="${altaRequired} ${modifRequired}" disabled="disabled">
                                    <option></option>
                                    <c:forEach var="actividad" items="${cSession.auxTables.tipoActividadPrincipal}">
                                        <c:choose>
                                            <c:when test="${actividad.id.equals(cSession.accessInfo.subEmpresa.actividad)}">
                                                <option value="${actividad.id}" selected="selected"><c:out value="${actividad.descripcion}"/></option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${actividad.id}"><c:out value="${actividad.descripcion}"/></option>
                                            </c:otherwise>
                                        </c:choose>                                        
                                    </c:forEach>
                                </select>
                                <label for="respCif">CIF/NIF</label>
                                <input type="text" name="respCif" class="required" value="${cSession.accessInfo.subEmpresa.cif}" readonly="readonly" maxlength="9"/>
                                <label for="respDomicilioSocial">Domicilio Social</label>                                
                                <input type="text" name="respDomicilioSocial" value="${sedeResponsable.direccion}" maxlength="100" class="${altaRequired} ${modifRequired}"/>
                                <label for="respLocalidad">Localidad</label>                                
                                <input type="text" name="respLocalidad" class="${altaRequired} ${modifRequired} autoLocalidad" value="${cSession.auxTables.getNombreLocalidad(sedeResponsable.localidad)}" maxlength="50" />
                                <label for="respCodigoPostal">Código Postal</label>                        
                                <input type="text" name="respCodigoPostal" class="${altaRequired} ${modifRequired}" value="${sedeResponsable.cp}" maxlength="5" />
                                <label for="respProvincia">Provincia</label>
                                <select name="respProvincia" id="respProvincia" class="${altaRequired} ${modifRequired} selProv" >
                                    <option></option>
                                    <c:forEach var="provincia" items="${cSession.auxTables.provincias}">
                                        <c:choose>
                                            <c:when test="${provincia.id.equals(sedeResponsable.provincia)}">
                                                <option value="${provincia.id}" selected="selected"><c:out value="${provincia.provincia}" /></option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${provincia.id}"><c:out value="${provincia.provincia}" /></option>
                                            </c:otherwise>
                                        </c:choose>                                       
                                    </c:forEach>
                                </select>
                                <label for="respPais">País</label>
                                <select name="respPais" id="respPais" class="${altaRequired} ${modifRequired}" >
                                    <option></option>
                                    <c:forEach var="pais" items="${cSession.auxTables.paisesAgpdResponsable}">
                                        <option value="${pais.agpdCode}"><c:out value="${pais.pais}" /></option>
                                    </c:forEach>
                                </select>
                                <label for="respTelefono">Teléfono</label>
                                <input type="text" name="respTelefono" value="${sedeResponsable.telefono}" minlength="9" maxlength="9" />
                                <label for="respFax">Fax</label>
                                <input type="text" name="respFax" value="${sedeResponsable.fax}" minlength="9" maxlength="9" />
                                <label for="respEmail">Correo Electrónico</label>
                                <input type="text" name="respEmail" value="${sedeResponsable.empresa.mailContacto}" maxlength="70"/>
                                
                                <%-- Ayuda para la cumplimentación de este apartado. --%>
                                <div id="tabs-1-help-d" title="Responsable del Fichero" class="dialog">
                                    <p>Se indicará en este apartado la persona física o jurídica responsable del fichero que decida sobre la finalidad, contenido y uso del fichero. Una persona que trabaja bajo la dependencia o autoridad directa del responsable del fichero, debido a una relación contractual dentro del ámbio del derecho laboral, no tiene la consideración de responsable del fichero a los efectos de la LOPD.</p>
                                    <p>Cumplimentese obligatoriamente la denominación social, el tipo de actividad el CIF/NIF y el domicilio social completo del responsable del fichero. El teléfono, fax y correo electrónico son de cumplimentación voluntaria.</p>
                                    <p>Cuando el responsable del fichero no esté establecido en el territorio de la Unión Europea y utilice en el tratamiento de datos medios situados en el territorio español, deberá designar, salvo que tales medios se utilicen con fines de tránsito, un representante en España, sin perjuicio de las acciones que pudieran emprenderse contra el propio responsable del tratamiento. En este caso, deberá cumplimentar obligatoriamente los datos de su representante en España en el apartado <span class="refTab" onclick="cambiarTab(1); $('#tabs-1-help-d').dialog('close');">Derechos ARCO.</span></p>
                                </div>
                            </div>
                            <div id="tabs-2" class="accAlta accModif">
                                <h1>Derechos de oposición, acceso, rectificación y cancelación <span id="tabs-2-help" title="haz click para obtener ayuda"  class="help ui-icon ui-icon-lightbulb" 
                                         style="display: inline-block"></span></h1>
                                <fieldSet style="border:0">
                                    <label for="fieldSetDerechosCheckBox">Utilizar los mismos datos del apartado "Responsable del Fichero"</label>
                                    <c:choose>
                                        <c:when test="${cNota.derechosCif.length() == 0}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" class="plegableCB"  name="fieldSetDerechosCheckBox" id="fieldSetDerechosCheckBox" value="ON" ${checked} />
                                </fieldSet>
                                <fieldset id="fieldSetDerechos" style="border:0">
                                <label for="derechosOficina">Nombre de la Oficina o dependencia</label>
                                <input type="text" name="derechosOficina" class="derechosRequired" value="${cNota.derechosOficina}" maxlength="70"/>
                                <label for="derechosCif">CIF/NIF</label>
                                <input type="text" name="derechosCif" class="derechosRequired" value="${cNota.derechosCif}" maxlength="9"/>
                                <label for="derechosDireccionPostal">Dirección postal / Apdo. de Correo</label>                                
                                <input type="text" name="derechosDireccionPostal" class="derechosRequired" value="${cNota.derechosDireccionPostal}" maxlength="100"/>
                                <label for="derechosLocalidad">Localidad</label>                                
                                <input type="text" name="derechosLocalidad" class="autoLocalidad" value="${cNota.derechosLocalidad}" maxlength="50"/>
                                <label for="derechosCodigoPostal">Código Postal</label>                        
                                <input type="text" name="derechosCodigoPostal" value="${cNota.derechosCodigoPostal}" maxlength="5"/>
                                <label for="derechosProvincia">Provincia</label>
                                <select name="derechosProvincia" id="derechosProvincia" class="selProv">
                                    <option></option>
                                    <c:forEach var="provincia" items="${cSession.auxTables.provincias}">
                                        <c:choose>
                                            <c:when test="${provincia.id.equals(cNota.derechosProvincia)}">
                                                <option value="${provincia.id}" selected="selected"><c:out value="${provincia.provincia}" /></option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${provincia.id}"><c:out value="${provincia.provincia}" /></option>
                                            </c:otherwise>
                                        </c:choose>                                       
                                    </c:forEach>
                                </select>
                                <label for="derechosPais">País</label>
                                <select name="derechosPais" id="derechosPais" >
                                    <option></option>
                                    <c:forEach var="pais" items="${cSession.auxTables.paisesAgpdAcceso}">
                                        <c:choose>
                                            <c:when test="${pais.agpdCode.equals(cNota.derechosPais)}">
                                                <option value="${pais.agpdCode}" selected="selected"><c:out value="${pais.pais}" /></option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${pais.agpdCode}"><c:out value="${pais.pais}" /></option>
                                            </c:otherwise>
                                        </c:choose>                                        
                                    </c:forEach>
                                </select>
                                <label for="derechosTelefono">Teléfono</label>
                                <input type="text" name="derechosTelefono" value="${cNota.derechosTelefono}"  minlength="9" maxlength="9" />
                                <label for="derechosFax">Fax</label>
                                <input type="text" name="derechosFax" value="${cNota.derechosFax}"   minlength="9" maxlength="9" />
                                <label for="derechosEmail">Correo Electrónico</label>
                                <input type="text" name="derechosEmail" value="${cNota.derechosEmail}" maxlength="70"/>
                                </fieldset>
                                <%-- Ayuda para la cumplimentación de este apartado. --%>
                                <div id="tabs-2-help-d" title="Derechos de Oposición, Acceso, Rectificación y Cancelación" class="dialog">
                                    <p>Este apartado únicamente deberá cumplimentarlo en el caso de que la dirección donde se prevea atender al ciudadano que desee ejercitar sus derechos de oposición, acceso, rectificación y cancelación sea diferente a la indicada en el apartado <span class="refTab" onclick="cambiarTab(0); $('#tabs-2-help-d').dialog('close');">Responsable del fichero.</span></p>
                                    <p>Deberá cumplimentar el nombre de la dependencia u oficina, el CIF/NIF y la dirección completa de la misma. El teléfono, fax y correo electrónico son de cumplimantación voluntaria.</p>
                                    <p>Si existen varias, indique la oficina principal o dependencia a la que se dirigirá el afectado para el ejercicio de sus derechos.</p>
                                </div>
                            </div>
                            <div id="tabs-3" class="titPublica accAlta accModif">
                                <label>Diario Oficial de Publicación</label>
                                <select name="">
                                    <option></option>
                                    <option></option>
                                </select>
                                <label>Número de Boletín</label>
                                <input type="text" name="" value="" />
                                <label>Fecha de publicación</label>
                                <input type="text" name="" value="" class="dateTimePicker"/>
                                <label>Nombre de la disposición</label>
                                <input type="text" name="" value="" />
                                <label>Localización de la disposición en Internet (URL)</label>
                                <input type="text" name="" value="" />
                            </div>
                            <div id="tabs-4" class="accAlta accModif">
                                <h1>Encargado del tratamiento <span id="tabs-4-help" title="haz click para obtener ayuda" class="help ui-icon ui-icon-lightbulb" 
                                         style="display: inline-block"></span></h1>
                                <fieldSet style="border:0">
                                    <label for="fieldSetTratamientoCheckBox">El responsable del fichero se encarga también del tratamiento de los datos</label>
                                    <c:choose>
                                        <c:when test="${cNota.encCif.length() == 0}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" class="plegableCB" name="fieldSetTratamientoCheckBox" id="fieldSetTratamientoCheckBox" value="ON" ${checked} />
                                </fieldSet>
                                <fieldset id="fieldSetTratamiento" style="border:0">
                                <label for="encDenominacionSocial">Denominación social del encargado del tratamiento</label>
                                <input type="text" name="encDenominacionSocial" class="encRequired" value="${cNota.encDenominacionSocial}" maxlength="140"/>
                                <label for="encCif">CIF/NIF</label>
                                <input type="text" name="encCif" value="${cNota.encCif}" maxlength="9"/>
                                <label for="encDomicilioSocial">Dirección postal</label>                                
                                <input type="text" name="encDomicilioSocial" class="encRequired" value="${cNota.encDomicilioSocial}" maxlength="100"/>
                                <label for="encLocalidad">Localidad</label>                                
                                <input type="text" name="encLocalidad" class="autoLocalidad encRequired" value="${cNota.encLocalidad}" maxlength="50"/>
                                <label for="encCodigoPostal">Código Postal</label>                        
                                <input type="text" name="encCodigoPostal" value="${cNota.encCodigoPostal}" minlength="5" maxlength="5"/>
                                <label for="encProvincia">Provincia</label>
                                <select name="encProvincia" id="encProvincia" class="selProv">
                                    <option></option>
                                    <c:forEach var="provincia" items="${cSession.auxTables.provincias}">
                                        <c:choose>
                                            <c:when test="${provincia.id.equals(cNota.encProvincia)}">
                                                <option value="${provincia.id}" selected="selected"><c:out value="${provincia.provincia}" /></option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${provincia.id}"><c:out value="${provincia.provincia}" /></option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                                <label for="encPais">País</label>
                                <select name="encPais" id="encPais" >
                                    <option></option>
                                    <c:forEach var="pais" items="${cSession.auxTables.paisesAgpdEncargado}">
                                        <c:choose>
                                            <c:when test="${pais.agpdCode.equals(cNota.encPais)}">
                                                <option value="${pais.agpdCode}" selected="selected"><c:out value="${pais.pais}" /></option>
                                            </c:when> 
                                            <c:otherwise>
                                                <option value="${pais.agpdCode}"><c:out value="${pais.pais}" /></option>
                                            </c:otherwise>
                                        </c:choose>                                        
                                    </c:forEach>
                                </select>
                                <label for="encTelefono">Teléfono</label>
                                <input type="text" name="encTelefono" value="${cNota.encTelefono}" minlength="9" maxlength="9" />
                                <label for="encFax">Fax</label>
                                <input type="text" name="encFax" value="${cNota.encFax}" minlength="9" maxlength="9" />
                                <label for="encEmail">Correo Electrónico</label>
                                <input type="text" name="encEmail" value="${cNota.encEmail}" maxlength="70"/>
                                </fieldset>
                                <%-- Ayuda para la cumplimentación de este apartado. --%>
                                <div id="tabs-4-help-d" title="Encargado de Tratamiento" class="dialog">
                                    <p>Este apartado únicamente habrá de cumplimentarse cuando un tercero realice el tratamiento por cuenta del responsable, indicando en el apartado <span class="refTab" onclick="cambiarTab(0); $('#tabs-4-help-d').dialog('close');">Responsable del fichero.</span> La realización de tratamientos por cuenta de terceros deberá estar regulada en un contrato que deberá constar por escrito o en alguna otra forma que permita acreditar su celebración y contenido, estableciéndose expresamente que el encargado del tratamiento tratará los datos conforme a las instrucciones del responsable del tratamiento, que no los aplicará o utilizará con fin distinto al que figure en dicho contrato, ni los comunicará, ni siquiera para su conservación a otras personas. Una persona que trabaja bajo la dependencia o autoridad directa del responsable del fichero, debido a una relación contratual dentro del ámbito del derecho laboral, no tiene la consideración de encargado del tratamiento a los efectos de la LOPD. Únicamente se consignarán en dicho apartado los datos de uno de los encargados del tratamiento. Se recomienda que se haga constar la denominación del encargado que realice el tratamiento de datos que pueda implicar una mayor duración en tiempo, o riesgos mayores según el tipo y la cantidad de datos tratados.</p>
                                    <p>Deberá cumplimentar la denominación social, el CIF/NIF, y el domicilio social completo del encargado del tratamiento. El teléfono, fax y correo electrónico son de cumplimentación voluntaria.</p>
                                    <p>Si el domicilio del encargado de tratamiento se encuentra fuera de la Unión Europea deberá cumplimentar obligatoriamente el apartado de <span class="refTab" onclick="cambiarTab(8); $('#tabs-4-help-d').dialog('close');">Transferencias internacionales.</span></p>
                                </div>
                            </div>
                            <div id="tabs-5" class="accAlta accModif">
                                <h1>Identificación y finalidad del fichero <span id="tabs-5-help" title="haz click para obtener ayuda" class="help ui-icon ui-icon-lightbulb" 
                                         style="display: inline-block"></span></h1>
                                <br>         
                                <fieldset>
                                    <legend>Denominación</legend>
                                <label>Denominación</label>
                                <input type="text" class="${altaRequired} ${modifRequired}" name="denominacion" value="${cNota.denominacion}" maxlength="70" />
                                <label>Descripción detallada de finalidad y usos previstos</label>
                                <textarea name="usosPrevistos" class="${altaRequired} ${modifRequired}" rows="3" cols="20" maxlength="350">${cNota.usosPrevistos}</textarea>
                                </fieldset><br>
                                <fieldset>
                                <legend>Tipificación correspondiente a la finalidad y usos previstos</legend>
                                <div class="divError">
                                    <input type="hidden" name="finalidades" id="finalidades" value="" class="required" />
                                </div>
                                <h2>Finalidades</h2>
                                <ul id="finalidadesDisponibles" class="droptrue">
                                    <c:forEach var="f" items="${cNota.getlistadoFinalidades()}" >
                                        <c:if test="${!cNota.finalidades.contains(f.toString().substring(11))}">
                                            <li class="ui-state-default" id="${f}">${f.getText(f)}</li>
                                        </c:if>
                                    </c:forEach>
                                </ul>
                                <ul id="finalidadesHabilitadas" class="droptrue">                                    
                                    <c:forEach var="f" items="${cNota.getlistadoFinalidades()}" >
                                        <c:if test="${cNota.finalidades.contains(f.toString().substring(11))}">
                                            <li class="ui-state-default" id="${f}">${f.getText(f)}</li>
                                        </c:if>
                                    </c:forEach>
                                </ul>
                                </fieldset>
                                <%-- Ayuda para la cumplimentación de este apartado. --%>
                                <div id="tabs-5-help-d" title="Identificación y finalidad del fichero" class="dialog">
                                    <p>Indique el nombre que identifique el fichero y describa la finalidad y usos previstos del mismo.</p>
                                    <p>Los datos de carácter personal sólo se podrán recoger para su tratamiento, así como someterlos a dicho tratamiento, cuando sean adecuados, pertinentes y no excesivos en relación con el ámbito y las finalidades determinadas, explícitas y legítimas para las que se hayan obtenido. Los datos de carácter personal objeto de tratamiento no podrán usarse para finalidades incompatibles con aquellas para las que los datos hubieran sido recogidos.</p>
                                    <p>Seleccione la/s tipificación/es que se corresponda con la descriptción facilidata. Se aconseja consultar toda la lista de finalidades del modelo con el fin de poder seleccionar aquellos valores definidos que mejor determinen la finalidad y usos del fichero. En caso de que ningún valor refleje las finalidades y usos previstos, seleccione el valor "OTRAS FINALIDADES".</p>
                                </div> 
                            </div>
                            <div id="tabs-6" class="accAlta accModif">                                
                                <h1>Origen y procedencia de los datos <span id="tabs-6-help" title="haz click para obtener ayuda" class="help ui-icon ui-icon-lightbulb" 
                                         style="display: inline-block"></span></h1>
                                <br>
                                <fieldset>
                                    <legend>Origen</legend>
                                    <div class="divError"><input type="hidden" name="origenHidden" id="origenHidden" value="" class="required" /></div>
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.origenInte)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" class="origen" name="origenInte" id="origenInte" value="ON" ${checked}/><label for="origenInte" class="rightLabel">El propio interesado o su representante legal</label>                
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.origenOtras)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" class="origen" name="origenOtras" id="origenOtras" value="ON" ${checked}/><label for="origenOtras" class="rightLabel">Otras personas físicas</label>
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.origenFap)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" class="origen" name="origenFap" id="origenFap" value="ON" ${checked}/><label for="origenFap" class="rightLabel">Fuentes accesibles al público</label>
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.origenRp)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" class="origen" name="origenRp" id="origenRp" value="ON" ${checked}/><label for="origenRp" class="rightLabel">Registros públicos</label>
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.origenEp)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" class="origen" name="origenEp" id="origenEp" value="ON" ${checked}/><label for="origenEp" class="rightLabel">Entidad privada</label>
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.origenAp)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" class="origen" name="origenAp" id="origenAp" value="ON" ${checked}/><label for="origenAp" class="rightLabel">Administraciones Públicas</label>
                                </fieldset><br>
                                <fieldset>
                                    <div class="divError">
                                        <input type="hidden" name="colectivos" id="colectivos" value="" class="required" />
                                    </div>
                                <legend>Colectivos o categorías de interesados</legend>
                                <fieldset style="border:0">
                                    <div><input type="hidden" name="colectivos" id="colectivos" value=""/></div>
                                    <ul id="colectivosDisponibles" class="droptrue">
                                        <c:forEach var="f" items="${cNota.getlistadoColectivos()}" >
                                            <c:if test="${!cNota.colectivos.contains(f.toString().substring(10))}">
                                                <li class="ui-state-default" id="${f}">${f.getText(f)}</li>
                                            </c:if>
                                        </c:forEach>
                                    </ul>
                                    <ul id="colectivosHabilitadas" class="droptrue">                                        
                                        <c:forEach var="f" items="${cNota.getlistadoColectivos()}" >
                                            <c:if test="${cNota.colectivos.contains(f.toString().substring(10))}">
                                                <li class="ui-state-default" id="${f}">${f.getText(f)}</li>
                                            </c:if>
                                        </c:forEach>
                                    </ul>
                                </fieldset>
                                <fieldset style="border:0">
                                    <c:choose>
                                        <c:when test="${cNota.otrosColectivos.length() != 0}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <label for="chOtrosColectivos">Otros colectivos</label><input type="checkbox" class="plegableCB" name="chOtrosColectivos" id="chOtrosColectivos" value="ON" ${checked} />
                                    <textarea name="otrosColectivos" id="otrosColectivos" rows="4" cols="20" style="display:none"><c:out value="${cNota.otrosColectivos}"/></textarea>
                                </fieldset>
                                </fieldset>
                                <%-- Ayuda para la cumplimentación de este apartado. --%>
                                <div id="tabs-6-help-d" title="Origen y procedencia de los datos" class="dialog">
                                    <h1>Procedencia de los datos</h1>
                                    <p>Se marcará al menos una de las casillas correspondientes al origen de los datos de carácter personal del fichero.</p>
                                    <h1>Fuentes accesibles al público</h1>
                                    <p>Se consideran fuentes accesibles al público: el censo promocional, regulado conforma a lo dispuesto en la Ley Orgánica 15/1999, las guías de servicios de telecomunicaciones electrónicas, en los términos previstos por su normativa específica, las listas de personas pertenecientes a grupos profesionales que contengan únicamente los datos de nombre, título, profesión, actividad, grado académico, dirección profesional e indicación de su pertenencia al grupo, los Diarios y Boletines oficiales, y los medios de comunicación social.</p>
                                    <h1>Colectivos o categorías de interesados</h1>
                                    <p>Seleccione de la lista los colectivos o personas origen de la información del fichero, tanto si el colectivo o grupo de personas está recogido explícita o implícitamente en el fichero. En el caso de que el colectivo no se encuentre identificado en la lista, señale la casilla correspondiente a "Otros colectivos" y descríbalo de forma breve.</p>
                                    <h1>Derecho de información en la recogida de los datos</h1>
                                    <p>Los interesados a los que se soliciten datos personales deberán ser previamente informados de modo expreso, preciso e inequívoco: de la existencia de un fichero o tratamiento de datos de carácter personal, de la finalidad de la recogida de éstos y de los destinatarios de la información, del carácter obligatorio o facultativo de la respuesta, de las consecuencias de la obtención de los datos o de la negatia a suministrarlos, de la posibilidad de ejercitar los derechos de acceso, rectificación, cancelación y oposición, y de la identidad y dirección del responsable del tratamiento, o en su caso, del representante.</p>
                                    <p>Si los datos no se obtienen directamente del interesado, éste deberá ser informado de forma expresa, precisa e inequívoca, por el responsable del fichero o su representante, dentro de los tres meses siguientes al momento del registro de los datos.</p>
                                </div>
                            </div>
                            <div id="tabs-7" class="accAlta accModif">
                                <h1>Tipos de datos, estructura y organización del fichero <span id="tabs-7-help" title="haz click para obtener ayuda" class="help ui-icon ui-icon-lightbulb" 
                                         style="display: inline-block"></span></h1><br>
                                <h2>Datos especialmente protegidos</h2>
                                <p>Los tratamientos de datos de carácter personal que revelen o hagan referencia a ideología, afiliación sindical, religión o creencias, deberán
ampararse en alguno de los supuestos que la Ley establece al efecto para poder tratarlos.
El tratamiento de estos datos sólo puede realizarse si se ha recabado el consentimiento expreso y por escrito del afectado. Para más
información consulte la ayuda del formulario.</p>
                                <fieldset>
                                    <legend>Datos especialmente protegidos</legend>
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.datEspProtIdeologia)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" name="datEspProtIdeologia" id="datEspProtIdeologia" value="ON" ${checked} /><label for="datEspProtIdeologia">Ideología</label>
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.datEspProtAfiliacionSindical)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" name="datEspProtAfiliacionSindical" id="datEspProtAfiliacionSindical" value="ON" ${checked} /><label for="datEspProtAfiliacionSindical">Afiliación sindical</label>
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.datEspProtReligion)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" name="datEspProtReligion" id="datEspProtReligion" value="ON" ${checked} /><label for="datEspProtReligion">Religión</label>
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.datEspProtCreencias)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" name="datEspProtCreencias" id="datEspProtCreencias" value="ON" ${checked} /><label for="datEspProtCreencias">Creencias</label>
                                </fieldset><br>
                                <h2>Otros Datos especialmente protegidos</h2>
                                <p>Los tratamientos de datos de carácter personal que revelen o hagan referencia al origen racial, la salud o la vida sexual deberán ampararse
en alguno de los supuestos que la Ley establece al efecto para poder tratarlos.
Para el tratamiento de estos datos será obligatorio recabar el consentimiento expreso del afectado o que, por razones de interés general,
así lo disponga una Ley.</p>
                                <fieldset>
                                    <legend>Otros Datos especialmente protegidos</legend>
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.otrosEspProtOrigenRacial)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" name="otrosEspProtOrigenRacial" id="otrosEspProtOrigenRacial" value="ON" ${checked} /><label for="otrosEspProtOrigenRacial">Origen racial o Étnico</label>
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.otrosEspProtSalud)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" name="otrosEspProtSalud" id="otrosEspProtSalud" value="ON" ${checked} /><label for="otrosEspProtSalud">Salud</label>
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.otrosEspprotVidaSexual)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" name="otrosEspprotVidaSexual" id="otrosEspprotVidaSexual" value="ON" ${checked} /><label for="otrosEspprotVidaSexual">Vida sexual</label>
                                </fieldset><br>
                                <fieldset>
                                    <legend>Datos de carácter identificativo</legend>
                                    <div class="divError"><input type="hidden" name="identifHidden" id="identifHidden" class="required" value="" /></div>
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.identifNIF)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" class="identif" name="identifNIF" id="identifNIF" value="ON" ${checked} /><label for="identifNIF">NIF / DNI</label>        
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.identifNSS)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" class="identif" name="identifNSS" id="identifNSS" value="ON" ${checked} /><label for="identifNSS">Nº SS / Mutualidad</label>                                  
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.identifNombreApp)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" class="identif" name="identifNombreApp" id="identifNombreApp" value="ON" ${checked} /><label for="identifNombreApp">Nombre y apellidos</label>
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.identifTarjSanitaria)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" class="identif" name="identifTarjSanitaria" id="identifTarjSanitaria" value="ON" ${checked} /><label for="identifTarjSanitaria">Tarjeta Sanitaria</label>
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.identifDireccion)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" class="identif" name="identifDireccion" id="identifDireccion" value="ON" ${checked} /><label for="identifDireccion">Dirección</label>                             
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.identifTel)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" class="identif" name="identifTel" id="identifTel" value="ON" ${checked} /><label for="identifTel">Teléfono</label>
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.identifFirmaManual)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" class="identif" name="identifFirmaManual" id="identifFirmaManual" value="ON" ${checked} /><label for="identifFirmaManual">Firma</label>
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.identifFirmaHuella)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" class="identif" name="identifFirmaHuella" id="identifFirmaHuella" value="ON" ${checked} /><label for="identifFirmaHuella">Huella</label>
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.identifOtrosDatosBio)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" class="identif" name="identifOtrosDatosBio" id="identifOtrosDatosBio" value="ON" ${checked} /><label for="identifOtrosDatosBio">Otros datos biométricos</label>
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.identifImagVoz)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" class="identif" name="identifImagVoz" id="identifImagVoz" value="ON" ${checked} /><label for="identifImagVoz">Imagen / voz</label>
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.identifMarcasFisicas)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" class="identif" name="identifMarcasFisicas" id="identifMarcasFisicas" value="ON" ${checked} /><label for="identifMarcasFisicas">Marcas físicas</label>
                                    <c:choose>
                                        <c:when test="${checkboxValue.equals(cNota.identifFirmaElectronica)}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="checkbox" class="identif" name="identifFirmaElectronica" id="identifFirmaElectronica" value="ON" ${checked} /><label for="identifFirmaElectronica">Firma electrónica</label>
                                    <br> <br>
                                    <label for="identifOtros">Otros datos de carácter identificativo</label>
                                    <textarea name="identifOtros" id="identifOtros" rows="2" cols="20" maxlength="100"><c:out value="${cNota.identifOtros}"/></textarea>
                                </fieldset><br>
                                <fieldset>
                                    <legend>Otros datos tipificados</legend>
                                    <fieldset style="border:0">
                                        <input type="hidden" name="otrosDatosTipificados" id="otrosDatosTipificados" value="" />
                                        <ul id="otrosDatosTipificadosDisponibles" class="droptrue">
                                            <c:forEach var="f" items="${cNota.getOtrosDatosTipificadosDisponibles()}" >
                                                <c:if test="${!cNota.otrosDatosTipificados.contains(f.toString().substring(21))}">
                                                    <li class="ui-state-default" id="${f}">${f.getTextByDesc(f)}</li>
                                                </c:if>
                                            </c:forEach>
                                        </ul>
                                        <ul id="otrosDatosTipificadosHabilitadas" class="droptrue">
                                            <c:forEach var="f" items="${cNota.getOtrosDatosTipificadosDisponibles()}" >
                                                <c:if test="${cNota.otrosDatosTipificados.contains(f.toString().substring(21))}">
                                                    <li class="ui-state-default" id="${f}">${f.getTextByDesc(f)}</li>
                                                </c:if>
                                            </c:forEach>
                                        </ul>
                                    </fieldset>
                                    <fieldset style="border:0">
                                        <c:choose>
                                            <c:when test="${cNota.otrosTiposDeDatos.length() != 0}">
                                                <c:set var="checked" value="checked=\"checked\""/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="checked" value=""/>
                                            </c:otherwise>
                                        </c:choose>
                                        <label for="chOtrosTiposDeDatos">Otros tipos de datos</label><input type="checkbox" class="plegableCB" name="chOtrosTiposDeDatos" id="chOtrosTiposDeDatos" value="ON" ${checked} />
                                        <textarea name="otrosTiposDeDatos" id="otrosTiposDeDatos" rows="4" cols="20" style="display:none"><c:out value="${cNota.otrosTiposDeDatos}"/></textarea>
                                    </fieldset> 
                                </fieldset><br>
                                <fieldset>
                                    <legend>Sistema de tratamiento</legend>
                                    <div class="divError">
                                        <input type="hidden" class="required" name="sistTratamientoHidden" id="sistTratamientoHidden" val="" />
                                    </div>
                                    <c:choose>
                                        <c:when test="${fn:contains(cNota.sistTratamiento,'1')}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <div style="display: inline-block; width: 25%; min-width: 140px"><input type="radio" name="sistTratamiento" id="sistTratamientoAut" value="1" ${checked} /><label for="sistTratamientoAut">Automatizado</label> </div>       
                                    <c:choose>
                                        <c:when test="${fn:contains(cNota.sistTratamiento,'2')}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <div style="display: inline-block; width: 25%; min-width: 140px"><input type="radio" name="sistTratamiento" id="sistTratamientoMan" value="2" ${checked} /><label for="sistTratamientoMan">Manual</label>  </div>                                
                                    <c:choose>
                                        <c:when test="${fn:contains(cNota.sistTratamiento,'3')}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <div style="display: inline-block; width: 25%; min-width: 140px"><input type="radio" name="sistTratamiento" id="sistTratamientoMix" value="3" ${checked} /><label for="sistTratamientoMix">Mixto</label></div>
                                </fieldset>
                                <div id="tabs-7-help-d" title="Tipos de datos, estructura y organización del fichero" class="dialog">
                                    <h1>Datos especialmente protegidos:</h1>
                                    <p>El tratamiento de datos especialmente protegidos de ideología, afiliación sindical, religión o creencias, sólo puede realizarse si se ha recabado el consentimiento expreso y por escrito del afectado. No obstante, la LOPD exceptúa de esta norma general a los ficheros mantenidos por los partidos políticos, sindicatos, iglesias, confesiones o comunidades religiosas y asociaciones, fundaciones y otras entidades sin ánimo de lucro, cuya finalidad sea política, filosófica, religiosa o sindical, en cuanto a los datos relativos a sus asociados o miembros, sin perjuicio de que la cesión de dichos datos precisará siempre el previo consentimiento del afectado.</p>
                                    <h1>Otros datos especialmente protegidos</h1>
                                    <p>Para tratar datos especialmente protegidos de origen racial, salud o vida sexual, será obligatorio recabar el consentimiento expreso del afectado o que, por razones de interés general, así lo disponga una Ley.</p>
                                    <h1>Datos de carácter identificativo</h1>
                                    <p>Marque todos y cada uno de los tipos de datos contenidos o tratados en el fichero. En caso de tratarse de datos no descritos expresamente en los tipos indicados en el modelo de notificación, señale la casilla "Otros tipos de datos" y especifíquelos de forma resumida. Cualquier fichero de datos de carácter personal debe contener al menos un dato de carácter identificativo.</p>
                                    <h1>Otros tipos de datos</h1>
                                    <p>Seleccione de la lista los tipos de datos incluidos en el fichero. En el caso de que el tipo de dato no se encuentre identificado en la lista, señale la casilla correspondiente a "Otros tipos de datos" y descríbalo de forma breve.</p>
                                    <h1>Sistema de tratamiento</h1>
                                    <p>Se entiende por sistema de tratamiento el modo en que se organiza la información o utiliza un sistema de información. Atendiendo al sistema de tratamiento, los sistemas de información podrían ser automatizados, no automatizados (manual) o parcialmente automatizados (mixto).</p>
                                </div>
                            </div>
                            <div id="tabs-8" class="accAlta accModif">
                                <h1>Medidas de seguridad <span id="tabs-8-help" title="haz click para obtener ayuda" class="help ui-icon ui-icon-lightbulb" 
                                         style="display: inline-block"></span></h1><br>
                                <fieldset>
                                    <legend>Medidas de seguridad</legend>
                                    <div class="divError">
                                        <input type="hidden" class="required" name="nivelHidden" id="nivelHidden" val="" />
                                    </div>
                                    <c:choose>
                                        <c:when test="${fn:contains(cNota.nivel,'0')}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <div style="display: inline-block; width: 25%; min-width: 140px"><input type="radio" validate="${altaRequired}:true ${modifRequired}" name="nivel" id="nivelBasico" value="0" ${checked} /><label for="nivelBasico">Nivel básico</label></div>
                                    <c:choose>
                                        <c:when test="${fn:contains(cNota.nivel,'1')}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <div style="display: inline-block; width: 25%; min-width: 140px"><input type="radio" name="nivel" id="nivelMedio" value="1" ${checked} /><label for="nivelMedio">Nivel Medio</label></div>
                                    <c:choose>
                                        <c:when test="${fn:contains(cNota.nivel,'2')}">
                                            <c:set var="checked" value="checked=\"checked\""/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="checked" value=""/>
                                        </c:otherwise>
                                    </c:choose>
                                    <div style="display: inline-block; width: 25%; min-width: 140px"><input type="radio" name="nivel" id="nivelAlto" value="2" ${checked} /><label for="nivelAlto">Nivel Alto</label></div>
                                </fieldset>
                                <%-- Ayuda para la cumplimentación de este apartado. --%>
                                <div id="tabs-8-help-d" title="Cesión o Comunicación de datos" class="dialog">
                                    <p>En este apartado se indicará el nivel de medidas de seguridad exigible al fichero. Estas medidas se clasifican en tras niveles: básico, medio y alto.</p>
                                    <p>Se señalará el nivel básico para cualquier fichero de datos de carácter personal, excepto aquellos que estén incluidos en alguno de los niveles siguientes.</p>
                                    <p>Deberán implantarse, además de las medidas de nivel básico, las medidas de nivel medio, cuando:</p>
                                    <ul>
                                        <li>Se trata de un fichero responsabilidad de las entidades financieras para finalidades realcionadas con la prestación de servicios financieros.</li>
                                        <li>Se trata de un fichero para la prestación de servicio de información de solvencia patrimonial o crédito.</li>
                                        <li>Tiene la finalidad de realizar tratamientos sobre cumplimiento/incumplimiento de obligaciones dinerarias.</li>
                                        <li>Se trata de ficheros responsabilidad de las mutuas de accidentes de trabajo y enfermedades profesionales de la Seguridad Social.</li>
                                        <li>Contengan un conjunto de datos de carácter personal que ofrezcan una definición de las carácterísticas o de la personalidad de los ciudadanos y que permitan evaluar determinados aspectos de la personalidad o del comportamiento de los mismos.</li>
                                    </ul>
                                    <p>Se señalará el nivel alto para cualquier fichero de datos de carácter personal que se refiera a datos de ideología, afiliación sindical, religión, creencias, origen racial, salud o vida sexual, así como los que contengan datos derivados de actos de violencia de género.</p>
                                    <p>Excepcionalmente podrán implantarse las medidas de nivel básico en ficheros que traten datos especialmente protegidos cuando:</p>
                                    <ul>
                                        <li>Dichos datos se utilicen con la única finalidad de realizar una transferencias dineraria a las entidades de las que los afectados sean asociados o miembros, o</li>
                                        <li>Se trate de ficheros en los que de forma incidental o accesoria se contengan datos especialmente protegidos sin guardar relación con su finalidad.</li>
                                    </ul>
                                    <p>También podrán implantarse las medidas de seguridad de nivel básico en los ficheros o tratamientos que contengan datos relativos a la salud referentes, exclusivamente, al grado de discapacidad o la simple declaración de la condición de discapacidad o invalidez del afectado, con motivo del cumplimento de deberes públicos.</p>
                                </div>
                            </div>
                            <div id="tabs-9" class="accAlta accModif">
                                <h1>Cesión o comunicación de datos <span id="tabs-9-help" title="haz click para obtener ayuda" class="help ui-icon ui-icon-lightbulb" 
                                         style="display: inline-block"></span></h1>
                                <p>Este apartado únicamente ha de cumplimentarse en el caso de que se prevea realizar cesiones o comunicaciones de datos. No se considerará
cesión de datos la prestación de un servicio al responsable del fichero por parte del encargado del tratamiento. La comunicación de los datos ha de
ampararse en alguno de los supuestos legales establecidos en la LOPD. Para mayor información consulte la ayuda de este formulario</p>
                                <fieldset>
                                    <legend>Categorías de destinatarios de cesiones</legend>
                                    <fieldset style="border:0"t>
                                        <input type="hidden" name="destCesiones" id="destCesiones" value="" />
                                        <ul id="destCesionesDisponibles" class="droptrue">
                                            <c:forEach var="f" items="${cNota.getlistadoDestCesiones()}" >
                                                <c:if test="${!cNota.destCesiones.contains(f.toString().substring(12))}">
                                                    <li class="ui-state-default" id="${f}">${f.getText(f)}</li>
                                                </c:if>
                                            </c:forEach>
                                        </ul>
                                        <ul id="destCesionesHabilitadas" class="droptrue">                                            
                                            <c:forEach var="f" items="${cNota.getlistadoDestCesiones()}" >
                                                <c:if test="${cNota.destCesiones.contains(f.toString().substring(12))}">
                                                    <li class="ui-state-default" id="${f}">${f.getText(f)}</li>
                                                </c:if>
                                            </c:forEach>
                                        </ul>
                                    </fieldset>
                                    <fieldset style="border:0"t>
                                        <c:choose>
                                            <c:when test="${cNota.otrosDestCesiones.length() != 0}">
                                                <c:set var="checked" value="checked=\"checked\""/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="checked" value=""/>
                                            </c:otherwise>
                                        </c:choose>                                    
                                        <input type="checkbox" class="plegableCB" name="chOtrosDestCesiones" id="chOtrosDestCesiones" value="ON" ${checked} /><label for="chOtrosDestCesiones">Otros destinatarios de cesiones</label>
                                        <textarea name="otrosDestCesiones" id="otrosDestCesiones" rows="2" cols="20" style="display:none" maxlength="100"><c:out value="${cNota.otrosDestCesiones}" /></textarea>
                                    </fieldset> 
                                </fieldset>
                                <%-- Ayuda para la cumplimentación de este apartado. --%>
                                <div id="tabs-9-help-d" title="Cesión o Comunicación de datos" class="dialog">
                                    <p>Este apartado únicamente ha de cumplimentarse en el caso de que se prevea realizar cesiones o comunicaciones de datos.</p>
                                    <p>No se considerará cesión de datos la prestación de un servicio al responsable del fichero por parte del encargado del tratamiento.</p>
                                    <p>La comunicación de los datos ha de ampararse en alguno de los supuestos legales establecedos en la LOPD.</p>
                                    <p>Para realizar cesiones o comunicaciones de datos se deberá contar con el consentimeinto de los afectados, o bien, que los datos hayan sido recogidos de fuentes accesibles al público, o bien, el tratamiento responda a la libre y legítima aceptación de una relación jurídica cuyo desarrollo, cumplimiento y control implique necesariamente la comunicación o, que exista una Ley que las autoriza.</p>
                                    <p>Seleccione de la lista los destinatarios o categorías de destinatarios de las cesiones. En el caso de que los destinatarios no se encuentren identificados en la lista, señale la casilla correspondiente a "Otros destinatarios" y descríbalo de forma breve.</p>
                                </div>
                            </div>
                            <div id="tabs-10" class="accAlta accModif">
                                <h1>Transferencias internacionales <span id="tabs-10-help" title="haz click para obtener ayuda" class="help ui-icon ui-icon-lightbulb" 
                                         style="display: inline-block"></span></h1>
                                <p>Este apartado únicamente ha de cumplimentarse en el caso de que se realice o esté previsto realizar un tratamiento de datos fuera del territorio del
Espacio Económico Europeo. En el caso de que la transferencia internacional tenga como destino un país que no preste un nivel de protección
adecuado al que presta la LOPD, deberá tener en cuenta que la LOPD establece que las previsiones para realizar transferencias internacionales son
diferentes, dependiendo de que los países destinatarios tengan un nivel de protección adecuado o no. Para más información consulte la ayuda de
este formulario.</p>
                               <fieldset>
                                    <legend>Países y destinatarios de la transferencia</legend>
                                    <div>
                                        <input type="hidden" name="transCodPais" id="transCodPais" value="" />
                                        <input type="hidden" name="transCategoria" id="transCategoria" value="" />
                                        <table style="width: 100%">
                                            <tr>
                                                <th>Países</th>
                                                <th>Categoría de destinatarios</th>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <c:set var="p" value="${cNota.getPaisTransferenciaP()}" />
                                                    <select name="transCodPais01" id="transCodPais01" class="transPSelect">
                                                        <option></option>
                                                        <c:forEach var="pais" items="${cSession.auxTables.paisesAgpdTransferInternacional}">
                                                            <c:choose>
                                                                <c:when test="${pais.agpdCode.equals(p)}">
                                                                    <option value="${pais.agpdCode}" selected="selected"><c:out value="${pais.pais}" /></option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value="${pais.agpdCode}"><c:out value="${pais.pais}" /></option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td>
                                                    <c:set var="t" value="${cNota.getCategoriaTransferenciaP()}" />
                                                    <select name="transCategoria01" id="transCategoria01" class="transCSelect">
                                                        <jsp:directive.include file="/WEB-INF/jspf/private/forms/destinatariosTransferenciasInternacionales.jspf" />
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <c:set var="p" value="${cNota.getPaisTransferenciaS()}" />
                                                    <select name="transCodPais02" id="transCodPais02"  class="transPSelect">
                                                        <option></option>
                                                        <c:forEach var="pais" items="${cSession.auxTables.paisesAgpdTransferInternacional}">
                                                            <c:choose>
                                                                <c:when test="${pais.agpdCode.equals(p)}">
                                                                    <option value="${pais.agpdCode}" selected="selected"><c:out value="${pais.pais}" /></option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value="${pais.agpdCode}"><c:out value="${pais.pais}" /></option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td>
                                                    <c:set var="t" value="${cNota.getCategoriaTransferenciaS()}" />
                                                    <select name="transCategoria02" id="transCategoria02" class="transCSelect">
                                                        <jsp:directive.include file="/WEB-INF/jspf/private/forms/destinatariosTransferenciasInternacionales.jspf" />
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <c:set var="p" value="${cNota.getPaisTransferenciaT()}" />
                                                    <select name="transCodPais03" id="transCodPais03"  class="transPSelect">
                                                        <option></option>
                                                        <c:forEach var="pais" items="${cSession.auxTables.paisesAgpdTransferInternacional}">
                                                            <c:choose>
                                                                <c:when test="${pais.agpdCode.equals(p)}">
                                                                    <option value="${pais.agpdCode}" selected="selected"><c:out value="${pais.pais}" /></option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value="${pais.agpdCode}"><c:out value="${pais.pais}" /></option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td>
                                                    <c:set var="t" value="${cNota.getCategoriaTransferenciaT()}" />
                                                    <select name="transCategoria03" id="transCategoria03" class="transCSelect">
                                                        <jsp:directive.include file="/WEB-INF/jspf/private/forms/destinatariosTransferenciasInternacionales.jspf" />
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <c:set var="p" value="${cNota.getPaisTransferenciaC()}" />
                                                    <select name="transCodPais04" id="transCodPais04"  class="transPSelect">
                                                        <option></option>
                                                        <c:forEach var="pais" items="${cSession.auxTables.paisesAgpdTransferInternacional}">
                                                            <c:choose>
                                                                <c:when test="${pais.agpdCode.equals(p)}">
                                                                    <option value="${pais.agpdCode}" selected="selected"><c:out value="${pais.pais}" /></option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value="${pais.agpdCode}"><c:out value="${pais.pais}" /></option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td>
                                                    <c:set var="t" value="${cNota.getCategoriaTransferenciaC()}" />
                                                    <select name="transCategoria04" id="transCategoria04" class="transCSelect">
                                                        <jsp:directive.include file="/WEB-INF/jspf/private/forms/destinatariosTransferenciasInternacionales.jspf" />
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr><td><br></td><td><br></td></tr>
                                            <tr>
                                                <th>Países</th>
                                                <th>Otras categorías de destinatarios</th>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <c:set var="p" value="${cNota.getPaisTransferenciaO()}" />
                                                    <select name="transCodPais05" id="transCodPais05"  class="transPSelect" >
                                                        <option></option>
                                                        <c:forEach var="pais" items="${cSession.auxTables.paisesAgpdTransferInternacional}">
                                                            <c:choose>
                                                                <c:when test="${pais.agpdCode.equals(p)}">
                                                                    <option value="${pais.agpdCode}" selected="selected"><c:out value="${pais.pais}" /></option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value="${pais.agpdCode}"><c:out value="${pais.pais}" /></option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <td>
                                                    <textarea name="transCategoriaOtros" id="transCategoria05" class="transCSelect" rows="2" cols="20" style="width: 100%" maxlength="100"><c:out value="${cNota.transCategoriaOtros}" /></textarea>                                                     
                                                </td>
                                            </tr>
                                        </table>
                                   </div>
                                </fieldset>
                                    
                                
                                <%-- Ayuda para la cumplimentación de este apartado. --%>
                                <div id="tabs-10-help-d" title="Transferencias internacionales" class="dialog">
                                    <p>Este apartado únicamente ha de cumplimentarse en el caso de que se realice o esté previsto realizar un tratamiento de datos fuera de la Unión Europea o del territorio del Espacio Económico Europeo.</p>
                                    <p>Se consideran países que proporcionan un nivel de protección adecuado, además de los estados miembros de la Unión Europea, Islandia, Liechtenstein, Noruega, Suiza, Argentina, las entidades estadounidenses adherida a los “principios de Puerto Seguro”, Guernsey, Isla de Man, Uruguay, Israel, Andorra, Islas Feroe y Canadá respecto de las entidades sujetas al ámbito de aplicación de la ley canadiense de protección de datos.</p>
                                    <p>En el caso de que la transferencia tenga como destino un país que no proporcione un nivel de protección adecuado al que presta la LOPD, dicha transferencia deberá ampararse en alguna de las excepciones previstas en el art. 34 LOPD: cuando la transferencia resulte de la aplicación de tratados o convenios en los que sea parte España, cuando la transferencia se haga a efectos de prestar o solicitar auxilio judicial internacional, cuando sea necesaria para la prevención o para el diagnóstico médico, la prestación de asistencia sanitaria o tratamiento médicos o la gestión de servicios sanitarios, cuando se refiera a trasferencias dinerarias conforme a su legislación específica, cuando el afectado haya dado su consentimiento inequívoco a la transferencia prevista, cuando la transferencia sea necesaria para la ejecución de un contrato entre el afectado y el responsable del fichero o para la adopción de medidas precontractuales adoptadas a petición del afectado, cuando sea necesaria para la celebración o ejecución de un contrato celebrado o por celebrar, en interés del afectado, por el responsable del fichero y un tercero, cuando la transferencia sea necesaria o legalmente exigida para la salvaguarda de un interés público, cuando sea precisa párale reconocimiento, ejercicio o defensa de un derecho en un proceso judicial o cuando la transferencia se efectúe, a petición de persona con interés legítimo, desde un Registro Público y aquella sea acorde con la finalidad del mismo.</p>
                                    <p>A los efectos de la existencia del consentimiento del afectado a la transferencia se deberá tener en cuenta que, para que dicho consentimiento tenga la consideración de inequívoco, exigencia prevista en el artículo 34 e) de la LOPD, será obligatorio que en la solicitud del mismo conste, además del destinatario de la transferencia, el país de destino, así como, la finalidad específica y determinada para la que se transfieren los datos de carácter personal.</p>
                                    <p>Si la transferencia internacional de datos no se encuentra amparada en ninguno de los supuestos citados en este partado, deberá solicitar la preceptiva autorización del Director de la Agencia Española de Protección de Datos.</p>
                                </div>
                            </div>
                            <div id="tabs-11" class="accSupres">
                                <fieldset>
                                    <legend>Denominación</legend>
                                    <label for="supresionCodInscripcion">Código de inscripción</label>
                                    <input type="text" name="supresionCodInscripcion" value="${cNota.codInscripcion}" class="${suprRequired}" readonly="readonly"/>
                                    <label for="supresionMotivos">Motivos de la supresión</label>
                                    <input type="text" name="supresionMotivos" value="" class="${suprRequired}" />
                                    <label for="supresionDestInfo">Destino de la información y previsiones adoptadas para su destrucción</label>
                                    <input type="text" name="supresionDestInfo" value="" class="${suprRequired}" />
                                </fieldset>
                            </div>
                                    <%--<c:if test="${cSession.accessInfo.gestor}">--%>
                                        <div id="tabs-0D">
                                            <h1>Identificación del declarante <span id="tabs-0D-help" title="haz click para obtener ayuda" class="help ui-icon ui-icon-lightbulb" 
                                                                                    style="display: inline-block"></span></h1>
                                            <div style="margin-top: 20px; padding: 0 .7em;" class="nota ui-state-highlight ui-corner-all">
                                                <p><span style="float: left; margin-right: .3em;" class="ui-icon ui-icon-info"></span>
                                                    Los datos indicados en esta pestaña deben coincidir con los utilizados en el
                                                    certificado digital con el que se firmará la notificación una vez pulsado
                                                    el botón enviar.</p>
                                            </div>
                                            <label for="nombreDeclarante">Nombre</label>
                                            <input type="text" class="required" name="nombreDeclarante" value="" minLength="1" maxLength="35"/>
                                            <label for="primerApellidoDeclarante">Primer Apellido</label>
                                            <input type="text" class="required" name="primerApellidoDeclarante" value="" minLength="1" maxLength="35"/>
                                            <label for="segundoApellidoDeclarante">Segundo Apellido</label>
                                            <input type="text" class="required" name="segundoApellidoDeclarante" value="" minLength="1" maxLength="35"/>
                                            <label for="cifFirma">NIF</label>
                                            <input type="text" class="required" name="cifFirma" value="" minLength="1" maxLength="9"/>
                                            <label for="cargoDeclarante">Cargo o condición del firmante en relación con el responsable del fichero</label>
                                            <input type="text" class="required" name="cargoDeclarante" value="" minLength="1" maxLength="70"/>

                                            <%-- Ayuda para la cumplimentación de este apartado. --%>
                                            <div id="tabs-0D-help-d" title="Declarante" class="dialog">
                                                <p>Indique los datos identificativos de la persona que firma la solicitud y el cargo o la condición del firmante de esta solicitud en relación con el responsable del fichero. Si el declarante es extranjero y carece de Número de Identificación de Extranjero, puede dejar el campo NIF sin cumplimentar.</p>
                                                <p>Asimismo, puede cumplimentar con un guión el campo correspondiente al segundo apellido en caso de que no lo tenga.</p>
                                                <p></p>
                                                <p></p>
                                                <p></p>
                                            </div>
                                        </div>
                                    <%--</c:if>--%>
                            <div id="tabs-00">
                                <fieldset>
                                    <legend>Dirección a Efectos de Notificación.</legend>
                                <c:set var="sedeNotificacion" scope="request" value="${cSession.accessInfo.subEmpresa.sedeLopd}" />
                                <label for="notifRazonSocial">Razón social o Nombre y apellidos</label>
                                <input type="text" name="notifRazonSocial" id="notifRazonSocial" value="${cSession.accessInfo.subEmpresa.razonSocial}" maxlength="70"/>
                                <label for="notifDirPostal">Dirección postal</label>
                                <input type="text" name="notifDirPostal" id="notifDirPostal" value="${sedeNotificacion.direccion}" maxlength="100"/>
                                <label for="notifLocalidad">Localidad</label>
                                <input type="text" name="notifLocalidad" id="notifLocalidad" class="autoLocalidad" value="${cSession.auxTables.getNombreLocalidad(sedeNotificacion.localidad)}" maxlength="50"/>
                                <label for="notifCodPostal">Código Postal</label>
                                <input type="text" name="notifCodPostal" id="notifCodPostal" value="${sedeNotificacion.cp}"  minlength="5" maxlength="5"/>
                                <label for="notifProvincia">Provincia</label>
                                <select name="notifProvincia" id="notifProvincia" class="selProv">
                                    <option></option>
                                    <c:forEach var="provincia" items="${cSession.auxTables.provincias}">
                                        <c:choose>
                                            <c:when test="${provincia.id.equals(sedeNotificacion.provincia)}">
                                                <option value="${provincia.id}" selected="selected"><c:out value="${provincia.provincia}" /></option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${provincia.id}"><c:out value="${provincia.provincia}" /></option>
                                            </c:otherwise>
                                        </c:choose>                                       
                                    </c:forEach>
                                </select>
                                <label for="notifPais">País</label>
                                <select name="notifPais" id="notifPais" >
                                    <option></option>
                                    <c:forEach var="pais" items="${cSession.auxTables.paisesAgpdResponsable}">
                                        <option value="${pais.agpdCode}"><c:out value="${pais.pais}" /></option>
                                    </c:forEach>
                                </select>
                                <label for="notifTel">Teléfono</label>
                                <input type="text" name="notifTel" id="notifTel" value="${sedeNotificacion.telefono}" minlength="9" maxlength="9" />
                                <label for="notifFax">Fax</label>
                                <input type="text" name="notifFax" id="notifFax" value="${sedeNotificacion.fax}" minlength="9" maxlength="9" />
                                <label for="notifEMail">Correo Electrónico</label>
                                <input type="text" name="notifEMail" id="notifEMail" value="${cSession.accessInfo.subEmpresa.mailContacto}" maxlength="70"/>
                                <%-- //TODO: Poner esto como mensaje antes del registro. --%>
                                <p>De conformidad con lo establecido en la Ley Orgánica 15/1999, de 13 de diciembre, de Protección de Datos de Carácter Personal, solicito la inscripción en el Registro General de Protección de Datos del fichero de datos de carácter personal al que hace referencia el presente formulario de notificación. Asimismo, bajo mi responsabilidad manifiesto que dispongo de representación suficiente para solicitar la inscripción de este fichero en nombre del responsable del fichero y que éste está informado del resto de obligaciones que se derivan de la LOPD. Igualmente, declaro que todos los datos consignados son ciertos y que el responsable del fichero ha sido informado de los supuestos legales que habilitan el tratamiento de datos especialmente protegidos, así como la cesión y la transferencia internacional de datos. La Agencia Española de Protección de Datos podrá requerir que se acredite la representación de la persona que formula la presente notificación.</p>
                                </fieldset><br>
                                <fieldset>
                                    <legend>Conocimiento de los deberes del declarante</legend>
                                    <div class="divError">
                                        <input type="checkbox" name="notifDeberes" value="1" class="required" />
                                        <label for="notifDeberes">Conocimiento de los deberes del declarante</label>
                                    </div>
                                    <p>En cumplimiento del artículo 5 de la Ley 15/1999, por el que se regula el derecho de información en la recogida de los datos, se advierte de los siguientes extremos: Los datos de carácter personal, que pudieran constar en esta notificación, se incluirán en el fichero de nombre “Registro General Protección de Datos”, creado por Resolución del Director de la Agencia Española de Protección de Datos (AEPD) de fecha 28 de abril de 2006, (B.O.E. nº 117) por la que se crean y modifican los ficheros de datos de carácter personal existentes en la AEPD. La finalidad del fichero es velar por la publicidad de la existencia de los ficheros que contengan datos de carácter personal con el fin de hacer posible el ejercicio de los derechos de información, oposición, acceso, rectificación y cancelación de los datos. Los datos relativos a la persona física que presenta la notificación de ficheros y solicita su inscripción en el Registro General de Protección de Datos se utilizarán en los términos previstos en los procedimientos administrativos que sean necesarios para la tramitación de la correspondiente solicitud y posteriores comunicaciones con la AEPD. Tendrán derecho a acceder a sus datos personales, rectificarlos o, en su caso, cancelarlos en la AEPD, órgano responsable del fichero.</p>
                                    <p>En caso de que en la notificación deban incluirse datos de carácter personal, referentes a personas físicas distintas de la que efectúa la solicitud o del responsable del fichero, deberá, con carácter previo a su inclusión, informarles de los extremos contenidos en el párrafo anterior.</p>
                                </fieldset>
                                <%-- TODO
                                <label>Dirección electrónica del servicio Notificaciones</label>--%>
                            </div>
                        </div>
                        
                        <br><hr>
                        <button id="formEditDataOk" onclick="submitForm('0')" type="submit" class="button">
                            <c:out value="${buttonEnviar}" />
                        </button>
                        <button id="formEditDataCancel" type="button" class="button">
                            <c:out value="${buttonCancelar}" />
                        </button> 
                        <div id="buttonBar">
                            <button id="btAnterior" class="button">Anterior</button>
                            <button id="btSiguiente" class="button">Siguiente</button>
                        </div>
                        <c:if test="${cSession.sysAdmin}">
                            <button id="btPredefinido" onclick="submitForm('1')" type="submit" class="button">
                                <c:out value="Guardar como Predefinido" />
                            </button>
                        </c:if>
                    </form>
                </div>
            </div>
            <jsp:directive.include file="/WEB-INF/jspf/common/standard/pieweb.jspf" />
        </div>
        <div id="extraFooter">
        </div>
        <jsp:directive.include file="/WEB-INF/jspf/common/standard/extraDivs.jspf" />
        
        <jsp:useBean id="cNotaSystem" class="com.openlopd.web.controllers.privatearea.ficheros.CNotaSystem" scope="request" />
        
        <%--<h1>Generando un nuevo XML de alta</h1>
        <c:out value="${cNotaSystem.altaFichero()}"/>--%>
        <%--
        <c:out value="${cNotaSystem.verify()}" />
        <h1>Doc firmado</h1>       
        <c:out value="${cNotaSystem.firmar()}" /> 
        <h1>Testing WS!</h1>        
        <c:out value="${cNotaSystem.test()}" />--%>
    </body>
</html>



        
