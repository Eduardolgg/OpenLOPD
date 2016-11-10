<%-- 
    Document   : privacidad
    Created on : 29-dic-2012, 18:53:32
    Author     : edu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />

<html>
    <head>
        <jsp:directive.include file="/WEB-INF/jspf/common/standard/headSection.jspf" />
        <link rel="stylesheet" type="text/css" href="${cssPath}/logincontrol.min.css" />
        <link rel="stylesheet" type="text/css" href="${cssPath}/index.min.css" />
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
                            <li><a href="index.jsp"><fmt:message bundle="${StandardBundle}" key="label.inicio" /> ></a></li>
                            <li><a href="index.jsp"><fmt:message bundle="${StandardBundle}" key="label.privacidad" /></a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div id="contentArea">
                <div id="contentRow">
                    <jsp:directive.include file="/WEB-INF/jspf/public/linkList.jspf" />
                    <div id="appArea">
                        <fmt:message var="nombreEmpresa" bundle="${StandardBundle}" key="nombreEmpresa" scope="page" />
                        <div id="politicaUsoPrivacidad">
                            <h1>Política de Uso</h1>
                            <h2>1. Aviso e información legales y su aceptación</h2>

                            El presente aviso e información legales, en adelante, “Aviso Legal”, regula el uso de los servicios de ${nombreEmpresa} y que pone a disposición de los usuarios del sitio.

                            La utilización del sitio web implica la aceptación plena y sin reservas de todas y cada una de las disposiciones incluidas en este aviso legal, publicada por ${nombreEmpresa} en su sitio web, en el mismo momento en que el usuario acceda al mismo. ${nombreEmpresa} se reserva el derecho a modificar cualquier tipo de información que pudiera aparecer en el sitio web, sin que exista obligación de preavisar o poner en conocimiento de los usuarios dichas obligaciones, entendiéndose como suficiente con la publicación en el sitio web.

                            <h2>2. Condiciones de acceso y utilización</h2>

                            ${nombreEmpresa} no asume ninguna responsabilidad derivada del uso incorrecto, inapropiado o ilícito de la información aparecida en su página. En cuanto a los enlaces que contiene el sitio web, ${nombreEmpresa}, no asume responsabilidades de ningún tipo.

                            <h3>2.1. Registro de usuario</h3>

                            Con carácter general la prestación de los servicios no exige la previa suscripción o registro de usuarios. No obstante, ${nombreEmpresa} condiciona la utilización de algunos de los servicios a la previa cumplimentación del correspondiente formulario, que se encuentra disponible en el sitio web.

                            En el caso de que para el acceso o utilización de cualquier servicio resulte necesario que el usuario suministre datos personales, la recogida y tratamiento de los mismos así como el ejercicio de los derechos sobre dichos datos, se regirán por la Política de Privacidad.

                            <h3>2.2. Veracidad de la información</h3>

                            Toda la información que facilite el usuario a través de los servicios deberá ser veraz. A estos efectos, el usuario garantiza la autenticidad de todos aquellos datos que comunique como consecuencia de la cumplimentación del formulario indicado. En todo caso, el usuario será el único responsable de las manifestaciones falsas o inexactas que realice y de los perjuicios que cause a ${nombreEmpresa} o a terceros por la información que facilite.

                            El usuario se compromete a utilizar el sitio web y los servicios de conformidad con la ley, el presente aviso legal, las condiciones particulares, reglamentos de uso e instrucciones puestos en su conocimiento.

                            A tal efecto, el usuario se abstendrá de utilizar cualquiera de los servicios con fines o efectos ilícitos, prohibidos en el presente aviso legal, lesivos de los derechos e intereses de terceros, o que de cualquier forma puedan dañar, inutilizar, sobrecargar,  deteriorar o impedir la normal utilización de los servicios, los equipos informáticos, archivos y toda clase de contenidos almacenados de ${nombreEmpresa}.

                            En particular, el usuario se compromete a no transmitir, difundir o poner a disposición informaciones, datos, contenidos, fotografías y, en general, cualquier clase de material que:
                            a)sea falso e inexacto, de forma que induzca o pueda inducir a error sobre su objeto o propósitos del sitio web
                            b)se encuentre protegido por cualquiera de los derechos de propiedad intelectual o industrial pertenecientes a terceros, sin que el usuario haya obtenido previamente de sus titulares la autorización necesaria para el uso que pretende efectuar
                            c)de cualquier forma deteriore el crédito de ${nombreEmpresa} o de terceros
                            d)incorpore virus u otros elementos que puedan dañar o impedir el normal funcionamiento del sitio web de ${nombreEmpresa} o de terceros, así como el contenido incluido en el sitio web.

                            <h3>2.3. Obligación de hacer un uso correcto de los contenidos</h3>
                            El usuario se compromete a abstenerse de:
                            a) reproducir, copiar, distribuir, poner a disposición o de cualquier otra forma comunicar públicamente, transformar o modificar los contenidos.
                            b) el acceso o la utilización del sitio web y de su contenido con fines ilegales o no autorizados.
                            c) suprimir, manipular o de cualquier forma alterar el contenido y/o demás datos identificativos de ${nombreEmpresa}, su sitio web o de sus titulares, o de cualesquiera otros medios técnicos establecidos para su reconocimiento.
                            d) El usuario deberá abstenerse de obtener e incluso de intentar obtener los contenidos empleando para ello medios o procedimientos distintos de los que, según los casos, se hayan puesto a su disposición a este efecto o se hayan indicado a este efecto en las páginas web donde se encuentren los contenidos o, en general, de los que se empleen habitualmente en internet a este efecto siempre que no entrañen un riesgo de daño o inutilización de los servicios y/o de los contenidos.

                            <h2>3. Régimen de Responsabilidad</h2>
                            <h3>3.1. Responsabilidad por el uso de la web</h3>

                            El usuario es el único responsable de las infracciones en las que pueda incurrir o de los perjuicios que pueda causar por la utilización de la web, quedando ${nombreEmpresa} exonerada de cualquier clase de responsabilidad que se pudiera derivar por las acciones del usuario.
                            ${nombreEmpresa} no asumirá ninguna responsabilidad, ya sea directa o indirecta, derivada del mal uso del sitio web o sus contenidos por parte del usuario asumiendo éste, en todo caso bajo su exclusiva responsabilidad, las consecuencias, daños o acciones que pudieran derivarse de su acceso o uso del sitio web o de los contenidos alojados, así como de su reproducción o comunicación.
                            ${nombreEmpresa} no asume ninguna garantía en relación con la ausencia de errores, o de posibles inexactitudes y/u omisiones en ninguno de los contenidos accesibles a través de esta web.
                            El usuario está obligado a hacer uso razonable del sitio web y de sus contenidos, según las posibilidades y fines para los que está concebido.
                            Propiedad intelectual
                            Sin perjuicio de la cesión de derechos no exclusiva referida más adelante, ${nombreEmpresa}, como autor de la obra colectiva en que consiste este sitio web, es el titular de todos los derechos de propiedad industrial e intelectual sobre la misma. Está prohibida cualquier forma de reproducción, distribución, comunicación pública, puesta a disposición, modificación, transformación y, en general, cualquier acto de explotación de la totalidad o parte de los contenidos (imágenes, textos, diseño, índices, formas, etc.) que integran el sitio web, así como de las bases de datos y del software necesario para la visualización o el funcionamiento del mismo, que no cuente con la expresa y previa autorización escrita de ${nombreEmpresa}.
                            El usuario garantiza que es el legítimo titular, o ha sido debidamente autorizado al efecto, de los derechos de explotación de las obras y/o contenidos que ponga a disposición de ${nombreEmpresa}.
                            Al enviar contenidos y nueva información, el usuario autoriza a ${nombreEmpresa}  a reproducirlos y comunicarlos públicamente  y, en su caso, distribuir los contenidos a través del sitio web. Asimismo, es objeto de esta cesión el derecho de transformación a fin de que ${nombreEmpresa} pueda editar el contenido de conformidad a las  necesidades formales del soporte, y podrá utilizarse para co el propósito, entre otros, de reproducción, divulgación, transmisión, publicación o colocación.  Esta es una cesión no exclusiva, por todo el tiempo permitido legalmente, y con posibilidad de sublicencia a terceros.
                            El usuario deberá notificar a ${nombreEmpresa} cualquier modificación que se produzca en los datos facilitados, respondiendo en cualquier caso de la veracidad y exactitud de los datos suministrados en cada momento.
                            Ello no obstante, dicha verificación previa no implica en ningún caso la asunción de responsabilidad alguna por los daños o perjuicios que pudieran derivarse de la falsedad o inexactitud de los datos suministrados, de las que responderá únicamente el usuario.
                            ${nombreEmpresa} se reserva el derecho, y el usuario expresamente lo acepta, a modificar en cualquier momento las presentes condiciones de uso sin necesidad de notificarlo previamente a los usuarios registrados. 

                            <h3>3.2. Responsabilidad por el funcionamiento de la web</h3>
                            ${nombreEmpresa} excluye toda responsabilidad que se pudiera derivar de interferencias, omisiones, interrupciones, virus informáticos, averías o desconexiones en el funcionamiento operativo del sistema, motivado por causas ajenas a la misma.
                            Asimismo, ${nombreEmpresa} también excluye cualquier responsabilidad que pudiera derivarse por retrasos o bloqueos en el funcionamiento operativo de este sistema causado por deficiencias o sobre carga en Internet, así como de daños causados por terceras personas mediante intromisiones ilegitimas fuera del control de la titularidad de la web.
                            ${nombreEmpresa} esta facultada para suspender temporalmente, y sin previo aviso, la accesibilidad al sitio web con motivo de operaciones de mantenimiento, reparación, actualización o mejora.
                            <h3>3.3. Responsabilidad por enlaces</h3>
                            Los enlaces o links contenidos en el sitio web pueden conducir al usuario a otras páginas gestionadas por terceros.
                            ${nombreEmpresa} declina cualquier responsabilidad respecto a la información que se halle fuera del sitio web, ya que la función de los enlaces que aparecen es únicamente la de informar al usuario sobre la existencia de otras fuentes de información sobre un tema en concreto.
                            ${nombreEmpresa} queda exonerado de toda responsabilidad por el correcto funcionamiento de tales enlaces, del resultado obtenido a través de dichos enlaces, de la veracidad y licitud del contenido o información a la que se puede acceder, así como de los perjuicios que pueda sufrir el usuario en virtud de la información encontrada en la web enlazada.
                            <h2>4. Privacidad</h2>
                            Puede leerse nuestra Política de Privacidad.
                            <h2>5. Vigencia</h2>
                            Se podrá modificar los términos y condiciones aquí estipuladas, total o parcialmente, publicando cualquier cambio en la misma forma en que aparecen estas condiciones generales. La vigencia temporal de estas condiciones generales coincide, por lo tanto, con el tiempo de su exposición, hasta que sean modificadas total o parcialmente, momento en el cual pasarán a tener vigencia las condiciones generales modificadas.
                            <h2>6. Legislación aplicable</h2>
                            Todas las controversias o reclamaciones surgidas de la interpretación o ejecución del presente Aviso Legal se regirán por la legislación española.


                            <h1>POLÍTICA DE PRIVACIDAD</h1>

                            <h2>1. Declaración de Privacidad</h2>
                            A través de nuestra política de privacidad, ${nombreEmpresa} pone en conocimiento de las debidas condiciones de uso establecidas en este sitio. El acceso a este sitio implica la aceptación plena y sin reservas de todas y cada una de las disposiciones incluidas en esta Declaración de Privacidad.
                            Si tienes dudas acerca de nuestra Política de Privacidad puedes ponerte en contacto con nosotros a través del formulario de contacto disponible en nuestro sitio web.

                            <h2>2. Acceso a la información del navegador (Cookies)</h2>
                            ${nombreEmpresa} hace uso de cookies, pequeños ficheros de datos que se generan en su ordenador,  y envían información sin proporcionar referencias que permitan deducir datos personales, con el fin de poder realizar un servicio personalizado para cada búsqueda. No se utilizan para el seguimiento del usuario fuera de los límites del buscador.
                            Cuando accedes a los servicios de ${nombreEmpresa}  se puede obtener información acerca del   navegador, la dirección IP, idioma, ubicación así como de las búsquedas que se realizan en el sitio.

                            <h2>3. Enlaces a otras páginas web</h2>
                            La presente Política de Privacidad se aplica sólo a esta página web y no a las páginas web propiedad de terceros. Facilitaremos enlaces a otras páginas web que consideramos de interés para nuestros visitantes. Nuestro deseo es garantizar que dichas páginas mantienen los estándares más elevados. Sin embargo, debido a la propia naturaleza de Internet, no podemos garantizar los niveles de privacidad de las páginas web con las cuales enlazamos, así como tampoco responsabilizarnos de los contenidos de las páginas que no sean la nuestra propia. La presente Política de Privacidad no está dirigida a su aplicación en cualquier página con la que mantengamos un enlace y que no pertenezca a ${nombreEmpresa}.
                            ${nombreEmpresa} no ejerce ningún control sobre las webs enlazadas en este sitio; no nos hacemos responsables del contenido de dichas webs, además de que éstas pueden tener su propia política de privacidad y normas de uso. Te animamos a que leas las políticas de privacidad de las páginas que enlazamos.
                            <h2>4. Publicidad</h2>
                            La publicidad expuesta en el sitio es responsabilidad de Google. ${nombreEmpresa} únicamente dispone de un espacio de publicidad desde el que Google puede personalizar la publicidad mostrada.

                            <h2>5. Compromiso de Privacidad</h2>
                            <h3>5.1. Información de recopilación de datos</h3>
                            Para utilizar algunos de los servicios, deberá proporcionar previamente ciertos datos de carácter personal, que solo serán utilizados para el propósito que fueron recopilados.
                            El tipo de la posible información que sea solicitada incluye, de manera enunciativa más no limitativa, el nombre, dirección de correo electrónico (e-mail), motivo de la comunicación, es decir, si se pone en contacto con nosotros para remitirnos una sugerencia, queja, etc.

                            <h3>5.2. Utilización de datos personales</h3>
                            Tal y como se enuncia en el punto anterior, 5.1. Información de recopilación de datos,  en el formulario se le pide que nos indique su nombre y dirección de correo electrónico. Es posible que los formularios transmitan automáticamente información acerca de su navegador, su sistema operativo, todo con el fin de atenderle mejor. Sus datos tan sólo se utilizarán para contactar con usted acerca de sus comentarios en caso de que fuese necesario.
                            <h3>5.3. Confidencialidad de datos</h3>
                            Como principio general, este sitio no comparte ni revela sus datos personales, excepto cuando haya sido autorizado por usted, o en los siguientes casos:
                            a) Cuando el uso, la conservación o divulgación de esta información sea requerida por una autoridad competente y previo el cumplimiento del trámite legal correspondiente, para cumplimiento de la legislación vigente y exigencias gubernamentales.
                            b) Cuando sea necesario para hacer cumplir las condiciones de uso y demás términos de esta página, o para salvaguardar la integridad de los demás usuarios o del sitio, o en apoyo a investigación en procesos legales.

                            Si voluntariamente revela información personal, dicha información puede ser recogida y usada por nosotros dentro de los límites establecidos en la Política de Privacidad. De igual forma, no controlamos las acciones de nuestros visitantes y usuarios.
                            <h2>6. Condiciones de uso y responsabilidades</h2>
                            <h3>6.1. Exclusión de garantías</h3>
                            Al utilizar ${nombreEmpresa} reconoce y Acepta que:
                            1.${nombreEmpresa} no se hace responsable de los daños o perdidas ocasionadas por la utilización de los datos que se muestran en mismo o del mismo buscador, usted utiliza los datos y el buscador por su propia cuenta y riesgo.
                            2.${nombreEmpresa} no garantiza la disponibilidad del servicio y este puede ser cesado en el momento que sea necesario.
                            <h3>6.2. Limitación de responsabilidad</h3>
                            1.${nombreEmpresa} no se hace responsable de ninguno de los daños directos o indirectos ocasionados por la utilización del buscador o por la información ofrecida en este.
                            2.Usted manifiesta y adminte que utiliza el uso del servicio lo realiza bajo su propia responsabilidad.
                            3.${nombreEmpresa} no se hace responsable del mal uso del servicio.
                            <h2>7. Modificación de la Política de Privacidad</h2>
                            ${nombreEmpresa} se reserva el derecho a modificar el contenido de esta Política de Privacidad según se considere oportuno, con objeto, en su caso, de adaptarlo a las novedades legislativas. En esta página se publicarán todas las modificaciones que se realicen a la Política de Privacidad, por lo que el uso de esta página tras las modificaciones constituirá la aceptación de tales cambios.

                            <h2>8. Contacto</h2>
                            Para cualquier consulta o reclamación sobre el cumplimiento de esta Política de Privacidad, o si desea dirigir alguna recomendación o comentario destinado a mejorar la calidad de la misma, por favor rellene el <a href="contacto.jsp">formulario de contacto</a>

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