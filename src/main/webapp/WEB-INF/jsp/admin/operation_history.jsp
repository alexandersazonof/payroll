<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 1/14/19
  Time: 9:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="../template/header.jsp"/>
<div class="text-center">
    <h1>Operation list</h1>
    <hr>
</div>

<table class="table table-striped">
    <thead>
    <tr>
        <th scope="row">#</th>
        <th scope="row">Action</th>
        <th scope="row">Date</th>
        <th scope="row">Number</th>
    </tr>
    </thead>
    <tbody>

        <c:forEach var="item" items="${operationList}">
        <tr>
            <td scope="row">${operationList.indexOf(item)+1}</td>
            <td>${item.getAction()}</td>
            <td>${item.getDate()}</td>
            <td><a href="/controller?command=showaccount&number=${item.getNumber()}">${item.getNumber()}</a></td>
        </tr>
        </c:forEach>

    </tbody>
</table>

<jsp:include page="../template/footer.jsp"/>
</body>
</html>
