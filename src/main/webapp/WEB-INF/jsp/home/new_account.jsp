<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 12/8/18
  Time: 10:36 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message bundle="${local}" key="local.showcard.name" var="cardName"/>
<fmt:message bundle="${local}" key="local.showcard.number" var="cardNumber"/>
<fmt:message bundle="${local}" key="local.newcard.add" var="addButton"/>
<fmt:message bundle="${local}" key="local.error.name" var="errorName"/>
<fmt:message bundle="${local}" key="local.error.number" var="errorNumber"/>
<fmt:message bundle="${local}" key="local.newcard.title" var="title"/>



<html>
<head>
    <title>${title}</title>

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
    <link rel="stylesheet" href="../../../css/index.css">
</head>
<body>


<%@ include file="../template/user_header.jsp" %>
<c:choose>
    <c:when test="${param.wrongName != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            Некоректоное название
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
</c:choose>
<form action="/controller" method="post" name="createForm" id="createForm" class="createForm">
    <input type="hidden" name="command" value="newaccount" />
    <div class="text-center">
        <div class="container">
            <h1>Create new account</h1>
            <hr>
            <div class="form-group row">
                <label for="exampleName" class="col-sm-2 col-form-label">${cardName} :</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control" id="exampleName" name="Name"  value="${param.name}">
                </div>
            </div>

        <div class="form-group row">
            <label for="exampleFormControlSelect1" class="col-sm-2 col-form-label">Valute :</label>
                <div class="col-sm-6">
                    <select class="form-control" id="exampleFormControlSelect1" name="valute">
                        <c:forEach items="${valuteList}" var = "item">
                            <option>${item.getName()}</option>
                        </c:forEach>
                    </select>
                </div>
        </div>
        <button type="submit" id="myButton" class="btn btn-dark">${addButton}</button>
        </div>
    </div>
</form>

<script src="../js/bootbox.min.js"></script>
<script type="text/javascript">
    $('#myButton').click(function(e) {
        e.preventDefault();
        var msg = 'Создать новый счёт ?';
        bootbox.confirm(msg, function(result) {
            if (result) {
                $('#createForm').submit();
            }
        });
    });
</script>



<jsp:include page="../template/footer.jsp" />
</body>
</html>
