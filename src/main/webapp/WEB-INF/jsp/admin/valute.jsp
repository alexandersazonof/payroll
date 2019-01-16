<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 1/15/19
  Time: 2:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message bundle="${local}" key="local.valute.title" var="title"/>
<html>
<head>
    <title>${title}</title>
</head>
<body>
<jsp:include page="../template/header.jsp"/>
<div class="container">
    <div class="text-center">
        <h2>${title}</h2>
        <table class="table table-striped">
            <thead>
            <tr>
                <th scope="col">#</th>
                <c:forEach var="item" items="${valuteList}">
                    <th scope="col">${item.getName()}</th>
                </c:forEach>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${courseList}">
            <tr>
                <th scope="col">${item.getNameValute()}</th>
                <c:forEach var="course" items="${item.getListExchenge()}">
                    <th><a href="/controller?command=EDITVALUTEPAGE&vid=${course.getId()}">${course.getCourse()}</a></th>
                </c:forEach>
            </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="../template/footer.jsp"/>
</body>
</html>
