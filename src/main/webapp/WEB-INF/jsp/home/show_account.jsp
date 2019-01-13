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

<c:choose>
    <c:when test="${param.sucblock != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Аккаунт и все карты заблокированы
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.sucunblock != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Заявка отправленна администратору
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.incmoney != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            На счёту не должно быть денег
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
</c:choose>


<div class="container">
    <div class="text-center">
        <h1><strong>Account : </strong>${bankAccount.getName()}</h1>
        <hr>
    </div>
    <div class="row">
        <div class="col-sm-6">
            <h2 class="text-center">Account history</h2>
            <div class="form-group row">
            <div class="col-sm-6">
                <div class="input-group mb-3">
                    <input type="text" name="keyWord" id="keyWord" class="form-control" placeholder="Search by action..." aria-label="Recipient's username" aria-describedby="search">
                    <div class="input-group-append">
                        <button class="btn btn-outline-secondary" type="button" id="search">Search</button>
                    </div>
                </div>
            </div>
            <div class="col-sm-2">
                <a href="#" id="export"><img src="../img/export.png" width="25" height="25" data-toggle="tooltip" data-placement="top" title="Export to PDF"></a>
            </div>
            </div>
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
                <div class="col-sm-2">
                    <c:choose>
                        <c:when test = "${bankAccount.isStatus() == true}">
                            <span class="badge badge-success">Active</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge badge-danger">Blocking</span>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col-sm-1">
                    <a href="#" id="delete"><img src="../img/delete.png" width="25" height="25"></a>
                </div>
                <div class="col-sm-1">
                    <c:choose>
                        <c:when test="${bankAccount.isStatus() == true}">
                            <a href="#" id="block"><img src="../img/block.png" width="25" height="25"></a>
                        </c:when>
                        <c:otherwise>
                            <a href="#" id="unblock"><img src="../img/unblock.png" width="25" height="25"></a>
                        </c:otherwise>
                    </c:choose>
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
                    <a href="/controller?command=TRANSFERACCOUNTMONEY&accountNumber=${bankAccount.getNumber()}" id="freeMoney">
                        ${bankAccount.getCountOfMoney()}  ${bankAccount.getValute()}
                    </a>
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

<script src="../js/bootbox.min.js"></script>
<script type="text/javascript">
    $('#block').click(function(e) {
        e.preventDefault();
        var msg = 'Заблокировать счёт ?';
        bootbox.confirm(msg, function(result) {
            if (result) {
                location.href = "/controller?command=BLOCKACCOUNT&accountNumber=${bankAccount.getNumber()}";
            }
        });
    });

    $('#unblock').click(function(e) {
        e.preventDefault();
        var msg = 'Заблокировать счёт ?';
        bootbox.confirm(msg, function(result) {
            if (result) {
                location.href = "/controller?command=UNBLOCKACCOUNT&accountNumber=${bankAccount.getNumber()}";
            }
        });
    });
    $('#delete').click(function(e) {
        e.preventDefault();
        var msg = 'Удалить счёт ?';
        bootbox.confirm(msg, function(result) {
            if (result) {
                location.href = "/controller?command=DELETEACCOUNT&accountNumber=${bankAccount.getNumber()}";
            }
        });
    });
    $('#search').click(function(e) {
        e.preventDefault();
        var key = document.getElementById("keyWord").value;
        location.href = "/controller?command=showaccount&number=${bankAccount.getNumber()}&search=" + key;
    });


</script>

<jsp:include page="../template/footer.jsp" />

</body>
</html>
