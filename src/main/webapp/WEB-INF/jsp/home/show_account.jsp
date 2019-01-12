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

<div class="container">
    <div class="text-center">
        <h1><strong>Account : </strong>${bankAccount.getName()}</h1>
    </div>
    <div class="row">
        <div class="col-sm-6">
            <h2 class="text-center">Account history</h2>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Action</th>
                     <th scope="col">Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${operationList}" var="item" >
                    <tr>
                        <th scope="row">${operationList.indexOf(item)+1}</th>
                        <td>${item.getAction()}</td>
                        <td>${item.getDate()}</td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="col-sm-6">
            <div class="form-group row">
                <label for="accountNumber" class="col-sm-2 col-form-label">Account number:</label>
                <div class="col-sm-4">
                    <input type="text" readonly class="form-control-plaintext" id="accountNumber" value="${bankAccount.getNumber()}" name="accountNumber">
                </div>
            </div>
            <div class="form-group row">
                <label for="accountName" class="col-sm-2 col-form-label">Account name:</label>
                <div class="col-sm-4">
                    <input type="text" readonly class="form-control-plaintext" id="accountName" value="${bankAccount.getName()}" name="accountName">
                </div>
            </div>
            <div class="form-group row">
                <label for="accountName" class="col-sm-2 col-form-label">Cards :</label>
                    <div class="from-group">
                    <c:forEach items="${bankAccount.getCardList()}" var="item">
                        <div class="list-group">
                             <a href="/controller?command=showcardpage&cid=${item.getId()}" class="list-group-item list-group-item-light"><img src="../img/${item.getCompany()}.png" width="30" height="25">${item.getNumber()}</a>
                        </div>
                    </c:forEach>
                    </div>
            </div>
            <div class="form-group row">
                <label for="freeMoney" class="col-sm-2 col-form-label">Free money:</label>
                <div class="col-sm-4">
                    <input type="text" readonly class="form-control-plaintext" id="freeMoney" value="${bankAccount.getCountOfMoney()} ${bankAccount.getValute()}" name="accountCount">
                </div>
            </div>
            <div class="form-group row">
                <label for="allMoney" class="col-sm-2 col-form-label">Total money:</label>
                <div class="col-sm-4">
                    <input type="text" readonly class="form-control-plaintext" id="allMoney" value="${totalMoney} ${bankAccount.getValute()}" name="totalMoney">
                </div>
            </div>
        </div>

    </div>
</div>

<jsp:include page="../template/footer.jsp" />

</body>
</html>
