<%-- 
    Document   : Localidades
    Created on : 10-mar-2011, 19:45:15
    Author     : Eduardo L. García Glez.
    versión    : 0.0.0
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<fmt:setBundle basename="msgbundles/standard/Web" scope="session" var="StandardBundle" />
<jsp:useBean id="localidadesBean" class="com.openlopd.common.ajax.localidades" scope="request" />
<jsp:setProperty property="*" name="localidadesBean" />

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Localidades</title>
    </head>
    <body>
        <select name="localidad" id="localidad" style="width: 200px;">
            <option selected="selected" value="-1"><fmt:message bundle="${StandardBundle}" key="option.sinAsignar" /></option>
            <c:forEach items="${localidadesBean.localidades}" var="localidad" >
                <c:out escapeXml="false" value="<option value=\"${localidad.id}\">${localidad.localidad}</option>" />
            </c:forEach>
        </select>
    </body>
</html>
