<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 1/15/19
  Time: 8:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message bundle="${local}" key="local.application.title" var="title"/>
<fmt:message bundle="${local}" key="local.application.account" var="localAccount"/>
<fmt:message bundle="${local}" key="local.application.action" var="localAction"/>

<html>
<head>
    <title>${title}</title>
</head>
<body>
<jsp:include page="../template/header.jsp"/>
<div class="text-center">
    <h2>${title}</h2>
</div>

<table class="table table-striped">
    <thead>
    <tr>
        <th scope="row">#</th>
        <th scope="row">${localAction}</th>
        <th scope="row">${localAccount}</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${applicationList}">
        <tr>
            <th>${applicationList.indexOf(item)+1}</th>
            <th>${item.getAction()}</th>
            <th>
                <p><a href="/controller?command=showaccount&number=${item.getAccountNumber()}">${item.getAccountNumber()}</a></p>
            </th>
        </tr>
    </c:forEach>
    </tbody>
</table>
<jsp:include page="../template/footer.jsp"/>
</body>
</html>
