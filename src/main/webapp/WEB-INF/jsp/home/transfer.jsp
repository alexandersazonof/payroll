<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 12/8/18
  Time: 1:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message bundle="${local}" key="local.transfer.account" var="accountLocale"/>
<fmt:message bundle="${local}" key="local.transfer.choose" var="chooseLocale"/>
<fmt:message bundle="${local}" key="local.transfer.count" var="countLocale"/>
<fmt:message bundle="${local}" key="local.transfer.description" var="descriptionLocale"/>
<fmt:message bundle="${local}" key="local.transfer.send" var="sendLocale"/>
<fmt:message bundle="${local}" key="local.error.number" var="errorNumberLocale"/>
<fmt:message bundle="${local}" key="local.error.count" var="errorCountLocale"/>
<fmt:message bundle="${local}" key="local.error.status" var="errorStatusLocale"/>
<fmt:message bundle="${local}" key="local.transfer.title" var="title"/>

<html>
<head>
    <title>${title}</title>

    <link rel="stylesheet" href="../../../css/index.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css"
          integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
            integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js"
            integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh"
            crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"
            integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ"
            crossorigin="anonymous"></script>
</head>
<body>
<%@ include file="../template/user_header.jsp" %>


<form action="/controller" method="post">
    <input hidden name="command" value="sendmoney">
    <div class="form-group">
        <label for="exampleNumber">${accountLocale}</label>
        <input type="text" class="form-control" name="ToNumber" id="exampleNumber" placeholder="BY1234567"
            value="${param.ToNumber}">


    </div>
    <div class="form-group">
        <label for="exampleFormControlSelect1">${chooseLocale}</label>
        <select class="form-control" id="exampleFormControlSelect1" name="FromNumber">
            <c:forEach items="${listBankAccount}" var="item">
                <option ><c:out value="${item.getNumber()}"/> </option>
            </c:forEach>
        </select>
    </div>
    <div class="form-group">
        <label for="exampleCount">${countLocale}</label>
        <input type="text" name="Count" class="form-control" id="exampleCount" value="${param.Count}">

    </div>
    <div class="form-group">
        <label for="exampleFormControlTextarea1">${descriptionLocale}</label>
        <textarea class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
    </div>
    <c:choose>
        <c:when test="${param.wrongNumber !=null}">
            <div class="alert alert-danger" role="alert">
                ${errorNumberLocale}
            </div>
        </c:when>
        <c:when test="${param.wrongCount !=null}">
            <div class="alert alert-danger" role="alert">
                ${errorCountLocale}
            </div>
        </c:when>
        <c:when test="${param.wrongBlock !=null}">
            <div class="alert alert-danger" role="alert">
                ${errorStatusLocale}
            </div>
        </c:when>
    </c:choose>

    <button type="submit" class="btn btn-dark">${sendLocale}</button>
    

</form>

<br><br><br><br>

<jsp:include page="../template/footer.jsp" />
</body>
</html>
