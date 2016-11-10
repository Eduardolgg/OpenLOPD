/* 
 * Security Technologies 2011
 * Apollo visual
 * Versión 0.0.3 06 de marzo de 2011
 * Modificaciones
 *     14 de mar de 2011 modificado el método checkContenido para evitar devolver
 *     true cuando el método val produce como resultado indefinido.
 *     16 de mar de 2011 nuevo método de verificación de claves, además se eliminan
 *     campos no obligatorios.
 *     16 de mar de 2011, nuevo método checkCamposFormaPago.
 */

/**
 * Verifica que el campo recibido tiene contenido, devolverá tru si el campo
 * tiene contenido y false en caso contrario, además resalta el campo.
 */
function checkContenido (id, length) {
    if ($(id).val() == undefined) {
        $(id).addClass("formBoxRed");
        return false;
    }
        
    if ($(id).is("input")) {
        if ($(id).val().length < length) {
            $(id).addClass("formBoxRed");
            return false
        }        
    } else {
        if ($(id).val() == "-1") {
            $(id).addClass("formBoxRed");
            return false
        }
    }
    $(id).removeClass("formBoxRed");
    return true
}

/**
 * Verifica que los campos tienen contenido.
 */
function checkCamposDatosEmpresa(){
    var estado;
    estado = checkContenido("#cif", 9);
    estado = checkContenido("#razonSocial", 3) && estado;
    estado = checkContenido("#tel", 9) && estado;
    estado = checkContenido("#dir", 5) && estado;
    estado = checkContenido("#cp", 5) && estado;
    estado = checkContenido("#provincia", 1) && estado;
    estado = checkContenido("#localidad", 1) && estado;
    return estado;
}

function checkCamposFormaPago(){
    var estado;
    estado = checkContenido("#SelectformaPago", 1);
    return estado;
}

/**
 * Verifica si los campos clave tienen contenido y si son iguales, devuelve
 * true en caso de que el contenido sea correcto false en caso contrario.
 */
function checkCamposClave(){
    if ($("#clave").val().length > 1) {
        if ($("#clave").val() == $("#claveconfirmacion").val()) {
            $("#clave").removeClass("formBoxRed");
            $("#claveconfirmacion").removeClass("formBoxRed");
            return true;
        }
    }
    $("#clave").addClass("formBoxRed");
    $("#claveconfirmacion").addClass("formBoxRed");
    return false;
}

/**
 * Verifica que los campos tienen contenido.
 */
function checkCamposPersonaContacto(){
    var estado;

    estado = checkContenido("#perNombre", 3);
    estado = checkContenido("#perApellido1", 3) && estado;
    estado = checkContenido("#perCargo", 2) && estado;
    estado = checkContenido("#perMail", 6) && estado;
    return estado;
}

/**
 * Verifica si un campo de tipo checkBox está seleccionado.
 */
function checkSend(id){
    if($(id).is(":checked"))
        return true;
    return false;
}

/**
 * Obtiene información adicional para las tablas.
 * @param tableName nombre de la tabla sobre la que operar.
 * @param id identificador único de la entidad.
 * @param row fila a la que pertenece el id.
 */           
function getExtraInfo(tableName, id, row) {
    appStatusLoading();
    var text = "";
    
    $(".oExtraInfoDiv").hide('blind');
    $(".oExtraInfo").remove();
            
    numCols = $(tableName).find('th').length / 2;
    var jqxhr = $.getJSON("./extraInfo.jsp", {
        "id": id
    })
    .success(function(jdata) { 
        $.each(jdata, function(clave, valor){ 
            text += clave + ": " + valor + "<br>"; 
        });
         
        if (text.length > 2) {
            $(row).after('<tr class="oExtraInfo"><td colspan="'
                + numCols 
                + '" class="details"><div class="oExtraInfoDiv" style="display:none">' 
                + text + '</div></td></tr>');
            $(".oExtraInfoDiv").show('blind');
        }         
    })
    .error(function(error) {
        appStatusError();
    })
    .complete(function() {
        appStatusComplete();
    });                               
    
    //    return id + ':' + jqxhr.responseText;
    return "";
}

function getDateTimePickerConfig() {
    return {
                   hourGrid: 4,	
                   minuteGrid: 10, 
                   addSliderAccess: true, 
                   sliderAccessArgs: { touchonly: false }};
}

function setUlHeight(ul1Id, ul2Id){
    $(ul1Id).height("auto");
    $(ul2Id).height("auto");
    if($(ul1Id).height() >  $(ul2Id).height()) {
        $(ul2Id).height($(ul1Id).height());
    } else {
        $(ul1Id).height($(ul2Id).height());
    }
}

function ciValidateRequest(formData, jqForm, options) {
    
}

function ciShowResponse(responseText, statusText, xhr, $form) {
    if (responseText.length > 0 || statusText == "error") {
        alert(responseText);
    } else {
        oTable.fnPageChange( 'first' );
    }
    $form.resetForm();
}

function initCodInscripcionControl(table) {
    var formOptions = {
        beforeSubmit:  ciValidateRequest,
        success:       ciShowResponse,
        table: table
    };
    
    $('#ciForm').ajaxForm(formOptions);
    
    $("#ciDialog").dialog({
        autoOpen: false,
        buttons: [
            {text: "Ok", click: function() {
                    //Actualizar tabla
                    $('#ciForm').ajaxSubmit(formOptions);
                    $(this).dialog("close");
                }},
            {text: "Canelar", click: function() {
                    $(this).dialog("close");
                }}
        ]
    });

    $("#addCiButton").click(function() {
        var rowSelected = $(".row_selected");
        if (rowSelected.length < 1) {
            alert("Seleccione una fila en la tabla.");
            return;
        }
        $("#ciFichero").val($(rowSelected.find("td")[0]).text());
        $("#codInscripcion").val($(rowSelected.find("td")[5]).text());
        $("#ciDialog").dialog("open");
    });
}

/*
 * Métodos de control de procedimientos.
 */

function addProc(proc) {
    var jsonProc = $.parseJSON(proc);
    if (jsonProc.status != "ok") {
        return "Imposible Crear el Procedimiento."
    }
    var htmlCode = 
      '        <div id ="' + jsonProc.id + '" class="ui-widget-content selectableItem">'
    + '            <div class="hiddenID">'+ jsonProc.id + '</div>'
    + '            <div class="Description">' + jsonProc.descripcion
    + '            <div class="level ' + jsonProc.nivel + '">'
    + '                 <img alt="Nivel de seguridad ' + jsonProc.nivel + '" '
    + '                     src="../../images/bola' + jsonProc.nivel + '.png" />'
    + '            </div>'
    + '            </div>'
    + '            <div class="hiddenProc">' + jsonProc.procedimiento + '</div>'
    + '        </div>';
    var addedObject = $("#availableProtocols .dropme").append(htmlCode);
    return "Procedimiento Creado";
}

function protocolClick(element, ui) {
    var option = "";
    var param = "";
                    
    appStatusLoading();
    
    option = $(element.target).parent().attr('id');
//    param = $(ui.item).find(".hiddenID").text();
    param = $(ui.draggable).find(".hiddenID").text();
    
    switch(option) {                        
        case "availableProtocols":
            $.getJSON("./manage.jsp", {a:0, p:param})
            .done(function() { /*alert("Borrado");*/ })
            .fail(function(jqxhr, textStatus, error) {
                appStatusError();
                showAppMsgError("Error desactivando el protocolo.");
            })
            .always(function() {
                appStatusComplete();
            });
            break;
        case "enabledProtocols":
            $.getJSON("./manage.jsp", {a:1, p:param})
            .done(function() { /*alert("Insertado"); */})
            .fail(function(jqxhr, textStatus, error) {
                appStatusError();
                showAppMsgError("Error activando el protocolo.");
            })
            .always(function() {
                appStatusComplete();
            });
            break;
        default:
            alert("No se puede procesar la petición, por favor "
                + " vuelva a intentarlo.");
    }
}


function cargarProvincias(appRoot) {
    $("#provincia").change(function(evento){
        evento.preventDefault();
        $("#cargando").css("display", "inline");
        $("#destino").load(appRoot + "/common/ajax/localidades.jsp", {
            idProvincia: $("#provincia").val()
        }, function(){
            $("#cargando").css("display", "none");
        });
    });
}

/*
 * Métodos para el control del estado de la aplicación. 
 */

/**
 * Limpia el estado de la aplicación.
 */
function appStatusClean() {
    $('#appStatus').empty().hide();
}

/**
 * Añade el mensaje de estado.
 */
function appStatusMessage(msg) {
    $('#appStatus').append(msg).show();
}

/**
 * Marca la aplicación en Loading.
 */
function appStatusLoading() {
    appStatusClean();
    appStatusMessage("Cargando...");
}

/**
 * Marca la aplicación en error.
 */
function appStatusError() {    
    appStatusClean();
    appStatusMessage("Error");
}

/**
 * Cambia el estado a operación completada.
 */
function appStatusComplete() {      
    appStatusClean();
    appStatusMessage("OK");      
    appStatusClean();
}

function showAppMsgError(msg) {
    showAppMsg(msg, "ui-state-error");
}

/**
 * Muestra un mensaje sobre alguna operación realizada en la aplicación.
 * @param msg Mensaje a mostrar al usuario.
 * @param objectClass Opcional, clase a utilizar en el mensaje. Solo usar
 * este parámetro para emitir errores. ver método showAppMsgError
 */
function showAppMsg(msg, objectClass) {
    var effect = 'blind';
    var delay = 1000;
    var objectIcon = "ui-icon-info";
    if (objectClass == null) {
        objectClass = "ui-state-highlight";
    } else {
        objectIcon = "ui-icon-error";
    }
    var divText =     
    '<div class="ui-widget">'
	+ '<div style="margin-top: 20px; padding: 0 .7em;" class="' + objectClass + ' ui-corner-all">'
		+ '<p><span style="float: left; margin-right: .3em;" class="ui-icon ' + objectIcon + '"></span>'
		+ msg + '</p>'
	+ '</div>'
    + '</div>'
    $("#appMessages").empty().append(divText).show(effect).delay(delay).hide(effect);
}

/**
 * Muestra información sobre un fichero cargado en el sistema.
 */
function addFileInfo() {
    $("#infofile").hide('blind');
    $("#file_upload").show('blind');
    $("#msgfile").remove("Fichero añadido: " + file.name 
        + " " + obj.size + " bytes"
        + "<br> MD5: "+ obj.md5);
}

/**
 * Obtiene la configuración de los tooltips.
 */
function getTooltipConfig() {
    return {
        position: {
            my: "center bottom-20",
            at: "center top"//,
            //using: function( position, feedback ) {
              //  $( this ).css( position );
                //$( "<div>" )
                //.addClass( "arrow" )
                //.addClass( feedback.vertical )
                //.addClass( feedback.horizontal )
                //.appendTo( this );
            //}
        }
    };
}

/*
 * Obtiene la zona horaria del cliente con estilo GMT-04:00 
 * y GMT+07:00 ver TimeZone.getTimeZone()
 */
function getTimeZone() {
    var tzo = new Date().getTimezoneOffset(); //returns timezone offset in minutes
    function pad(num, digits) {
        num = String(num);
        while (num.length < digits) {
            num = "0" + num;
        }
        return num;    
    }
    return "GMT" + (tzo > 0 ? "-" : "+") + pad(Math.floor(Math.abs(tzo)/60), 2) + ":" + pad(Math.abs(tzo) % 60, 2);
}

