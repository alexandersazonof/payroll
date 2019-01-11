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


    <c:set var = "salary" scope = "session" value = "user"/>
    <c:choose>

        <c:when test = "${user == null}">
            <jsp:include page="WEB-INF/jsp/template/general_header.jsp" />
            <jsp:include page="WEB-INF/jsp/template/general_content.jsp" />
        </c:when>

        <c:otherwise>
            <jsp:include page="WEB-INF/jsp/template/user_header.jsp" />
            <jsp:include page="WEB-INF/jsp/template/general_content.jsp" />
        </c:otherwise>

    </c:choose>



    <jsp:include page="WEB-INF/jsp/template/footer.jsp" />
    </body>
</html>

