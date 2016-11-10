<%-- 
    Document   : updatePermiso
    Created on : 31-dic-2012, 15:35:32
    Author     : Eduardo L. García Glez.
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<jsp:useBean id="cSession" class="com.openlopd.web.controllers.privatearea.CSession" scope="session" />
<jsp:useBean id="cUpdatePermiso" class="com.openlopd.web.controllers.privatearea.admin.grupos.CUpdatePermiso" scope="request" />
<jsp:setProperty name="cUpdatePermiso" property="*" />
<jsp:setProperty name="cUpdatePermiso" property="session" value="${cSession}" />
<c:out value="${cUpdatePermiso.update()}" />