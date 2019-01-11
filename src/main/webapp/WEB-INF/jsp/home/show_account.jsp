<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 12/7/18
  Time: 8:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message bundle="${local}" key="local.showcard.block" var="block"/>
<fmt:message bundle="${local}" key="local.showcard.count" var="count"/>
<fmt:message bundle="${local}" key="local.showcard.delete" var="delete"/>
<fmt:message bundle="${local}" key="local.showcard.edit" var="edit"/>
<fmt:message bundle="${local}" key="local.showcard.name" var="name"/>
<fmt:message bundle="${local}" key="local.showcard.number" var="number"/>
<fmt:message bundle="${local}" key="local.showcard.title" var="title"/>
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

<c:forEach items="${listAccount}" var="item">
    <ul class="list-group list-group-flush">
        <li class="list-group-item">

            <form action="/controller" method="post">
                <input type="hidden" name="command" value="editcard" />
                <div class="form-group row">
                    <label for="name" class="col-sm-2 col-form-label" >${name} </label>
                    <div class="col-sm-10">
                        <input type="text" id="name" readonly class="form-control-plaintext" name="Name" value="<c:out value="${item.getName()}"/>">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="number" class="col-sm-2 col-form-label">${number} </label>
                    <div class="col-sm-10">
                        <input type="text" id="number" readonly class="form-control-plaintext" name="Number" value="<c:out value="${item.getNumber()}"/>">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="count" class="col-sm-2 col-form-label">${count} </label>
                    <div class="col-sm-10">
                        <input type="text" id="count" readonly class="form-control-plaintext" name="Count" value="<c:out value="${item.getCountOfMoney()} BYN"/>">
                    </div>
                </div>
                <p/>
                <c:set var = "blockStatus" scope = "session" value = "${item.isStatus()}"/>
                <c:if test = "${blockStatus == false}">
                    <div class="alert alert-danger" role="alert">
                        ${block}
                    </div>
                </c:if>
                <div class="col-sm-10">
                    <button type="submit" class="btn btn-dark">${edit}</button>
                    <button type="button" class="btn btn-danger" onClick='location.href="/controller?command=deleteaccount&Number=${item.getNumber()}"'>${delete}</button>
                </div>

            </form>
        </li>
    </ul>
</c:forEach>




<jsp:include page="../template/footer.jsp" />

</body>
</html>
