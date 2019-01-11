<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 12/7/18
  Time: 9:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message bundle="${local}" key="local.editcard.lock" var="lock"/>
<fmt:message bundle="${local}" key="local.editcard.unlock" var="unlock"/>
<fmt:message bundle="${local}" key="local.editcard.title" var="title"/>
<fmt:message bundle="${local}" key="local.editcard.save" var="save"/>
<fmt:message bundle="${local}" key="local.showcard.name" var="name"/>
<fmt:message bundle="${local}" key="local.showcard.number" var="number"/>
<fmt:message bundle="${local}" key="local.showcard.count" var="count"/>
<fmt:message bundle="${local}" key="local.editcard.status" var="status"/>
<fmt:message bundle="${local}" key="local.error.name" var="erroName"/>

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
<c:set var = "wrongName" scope = "session" value = "${param.wrongName}"/>

<form action="/controller" method="post">
    <input type="hidden" name="command" value="savecard" />
    <div class="form-group row">
        <label for="inlineFormInputName" class="col-sm-2 col-form-label" >${name}</label>
        <div class="col-sm-10">
            <input type="text" id="inlineFormInputName" class="form-control-plaintext" name="Name" value="<c:out value="${wrongName==null ? Account.getName() : param.name}"/> ">
        </div>


    </div>

    <c:if test = "${wrongName != null}">
        <div class="alert alert-danger" role="alert">
            <strong >${erroName}</strong>
        </div>
    </c:if>

    <div class="form-group row">
        <label for="number" class="col-sm-2 col-form-label">${number} </label>
        <div class="col-sm-10">
            <input type="text" id="number" readonly class="form-control-plaintext" name="Number" value="<c:out value="${Account.getNumber()}"/>">
        </div>
    </div>
    <div class="form-group row">
        <label for="count" class="col-sm-2 col-form-label">${count} </label>
        <div class="col-sm-10">
            <input type="text" id="count" readonly class="form-control-plaintext" name="Count" value="<c:out value="${Account.getCountOfMoney()} BYN"/>">
        </div>
    </div>
    <div class="form-group row">
        <label for="inlineFormCustomSelect" class="col-sm-2 col-form-label">${status} </label>
        <div class="col-sm-10">
            <c:set var = "isBlock" scope = "session" value = "${Account.isStatus()}"/>
            <c:choose>

                <c:when test = "${isBlock}">
                    <select class="custom-select mr-sm-2" id="inlineFormCustomSelect" name="Status">
                        <option selected>${unlock}</option>
                        <option >${lock}</option>
                    </select>
                </c:when>

                <c:otherwise>
                    <select class="custom-select mr-sm-2" id="inlineFormCustomSelect" name="Status">
                        <option selected>${lock}</option>
                        <option disabled>${unlock}</option>
                    </select>
                </c:otherwise>

            </c:choose>
        </div>
    </div>
    <p/>
    <div class="col-sm-10">
        <button type="submit" class="btn btn-dark">${save}</button>
    </div>

</form>
<br><br><br><br><br><br><br><br><br>
<jsp:include page="../template/footer.jsp" />
</body>
</html>
