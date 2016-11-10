<%-- 
    Document   : ConfCertificado
    Created on : 17-may-2014, 11:46:31
    Author     : edu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />
<!DOCTYPE html>
<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/common/standard/headSection.jspf" />
        <link rel="stylesheet" type="text/css" href="${cssPath}/confcertificado.min.css" />
        <title>Configuración del Certificado digital</title>
    </head>
    <body>
        <div id="content">
            <h1>Configuración de java</h1>
            <p>Las nuevas versiones de java incluyen un sistema de seguridad que impide
                la ejecución de java en el navegador evitando un uso malintencionado. Para
                poder utilizar la notificación de ficheros es necesario configurar java,
                y puedes hacerlo siguiendo estos sencillos pasos:</p>
            <ol>
                <li>
                    Inicia el panel de control de java
                    <ul>
                        <li>Pulsa el botón de inicio de windos.</li>
                        <li>En la caja de texto escribe java.</li>
                        <li>Y abre el panel de control, puedes verlo en la imagen.</li>
                    </ul>
                    <img src="${imagePath}/tutoriales/c001.png" alt="Iniciar el panel de control de java." >
                </li>
                <li>
                    <p>Accede a la pestaña seguridad</p>
                    <img src="${imagePath}/tutoriales/c002.png" alt="Panel de control de java - Pestaña seguridad" >
                </li>
                <li>
                    <p>Añade los sitios http://lopd.jkingii.es y https://lopd.jkingii.es
                        pulsando el botón Editar lista de sitios.</p>
                    <img src="${imagePath}/tutoriales/c003.png" alt="Panel de control de java - nuevos sitios de confianza" >
                </li>
            </ol>
            <p>Y así ya tienes configurado java para la firma de las notificaciones a la Agencia
                de protección de datos.</p>

            <h1>Importar certificados digitales en Windows</h1>   
            <p>NOTA: Esta configuración es válida para IE y Chrome.</p>
            <p>Para importar certificados digitales previamente exportados, siga estos pasos:</p>
            <ol>
                <li>Haga clic en Inicio, seleccione Configuración, haga clic en Panel de control y, a continuación, haga doble clic en Internet.</li>
                <li>En la ficha Contenido, haga clic en Personal y, a continuación, en Importar.</li>
                <li>En el cuadro Contraseña, escriba su contraseña:<p>NOTA: puede que la contraseña se le pida varias veces.</p></li>
                <li>En el cuadro Archivo del certificado que desea importar, escriba el nombre de archivo del certificado que quiere importar y, a continuación, haga clic en Aceptar.</li>
                <li>Haga clic en Cerrar y, a continuación, en Aceptar. </li>
            </ol>

            <h1>Importar certificados digitales en Firefox</h1>
            <p>Para importar certificados digitales previamente exportados, siga estos pasos:</p>
            <ol>
                <li>
                    <p>Acceda a las opciones.</p>
                    <img src="${imagePath}/tutoriales/c004.png" alt="Acceso a las opciones de Firefox.">
                </li>
                <li><p>Acceda a las propiedades Avanzadas y allí a la pestaña Certificados
                        y pulse el botón Ver certificados.</p>
                    <img src="${imagePath}/tutoriales/c005.png" alt="Pestaña Certificados Firefox.">
                </li>
                <li><p>Haga clic en importar y siga los pasos para importar su certificado.</p>
                    <img src="${imagePath}/tutoriales/c006.png" alt="Listado de certificados importados en Firefox."
                </li>
            </ol>
        </div>
    </body>
</html>
