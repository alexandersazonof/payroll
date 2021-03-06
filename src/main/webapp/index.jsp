<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 12/4/18
  Time: 12:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message bundle="${local}" key="local.siteName" var="siteName"/>

<head>
    <title>${siteName}</title>

</head>
    <body>
    <c:redirect url = "/controller?command=mainpage"/>

    </body>
</html>

