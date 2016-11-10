<%-- 
    Document   : example
    Created on : 17-may-2012, 9:07:19
    Author     : Eduardogg
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text" pageEncoding="UTF-8"%>
<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<jsp:useBean id="cIndex" class="com.openlopd.web.controllers.privatearea.regsalida.CJSonTable" scope="request" />
<jsp:setProperty name="cIndex" property="*" />
<jsp:setProperty name="cIndex" property="httpServletRequest"  value="<%= request %>"/>
<jsp:setProperty name="cIndex" property="session" value="${cSession}" />
<c:out escapeXml="false" value="${cIndex.json}" />

