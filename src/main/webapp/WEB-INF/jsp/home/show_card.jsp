<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 1/11/19
  Time: 12:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="../template/header.jsp"/>

<h1 class="text-center">Card №${card.getNumber()}</h1>
<hr>
<div class="container">
    <div class="row">
        <div class="col-sm-6">
            <img src="../img/${company.getName()}${rate.getName()}.png" width="300" height="150">
        </div>
        <div class="col-sm-6">
            <div class="form-group row">
                <label for="card" class="col-sm-2 col-form-label">Card number:</label>
                <div class="col-sm-4">
                    <input type="text" readonly class="form-control-plaintext" id="card" value="${card.getNumber()}" name="card">
                </div>
                <div class="col-sm-1">
                    <img src="../img/${company.getId()}.png" width="30" height="25">
                </div>
                <div class="col-sm-2">
                    <c:choose>
                        <c:when test = "${status == true}">
                            <span class="badge badge-success">Active</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge badge-danger">Blocking</span>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="col-sm-1">
                    <a href="/controller?command=deletecard&cid=${card.getId()}" id="delete"><img src="../img/delete.png" width="25" height="25"></a>
                </div>
                <div class="col-sm-1">
                    <c:choose>
                        <c:when test="${status == true}">
                            <a href="/controller?command=blockcard&account=${account.getNumber()}&card=${card.getNumber()}" id="block"><img src="../img/block.png" width="25" height="25"></a>
                        </c:when>
                        <c:otherwise>
                            <a href="/controller?command=unblockcard&account=${account.getNumber()}&card=${card.getNumber()}" id="unblock"><img src="../img/unblock.png" width="25" height="25"></a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="form-group row">
                <label for="money" class="col-sm-2 col-form-label">Money:</label>
                <div class="col-sm-4">
                    <p id="money">${card.getMoney()} <strong>${valute.getName()}</strong>  </p>
                </div>
            </div>
            <div class="form-group row">
                <label for="date" class="col-sm-2 col-form-label">Date:</label>
                <div class="col-sm-4">
                    <input type="text" readonly class="form-control-plaintext" id="date" value="${card.getDate()}">
                </div>
            </div>
            <div class="form-group row">
                <label for="customer" class="col-sm-2 col-form-label">Customer:</label>
                <div class="col-sm-6">
                    <input type="text" readonly class="form-control-plaintext" id="customer" value="${card.getCustomer()}">
                </div>
            </div>
            <div class="form-group row">
                <label for="rate" class="col-sm-2 col-form-label">Rate:</label>
                <div class="col-sm-4">
                    <input type="text" readonly class="form-control-plaintext" id="rate" value="${rate.getName()}">
                </div>
            </div>
            <div class="form-group row">
                <label for="account" class="col-sm-2 col-form-label">Number account:</label>
                <div class="col-sm-4">
                    <kbd><a id="account" href="/controller?command=showaccount&number=${account.getNumber()}">${account.getNumber()}</a></kbd>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="../js/bootbox.min.js"></script>
<script type="text/javascript">
    $('#block').click(function(e) {
        e.preventDefault();
        var msg = 'Заблокировать карту ?';
        bootbox.confirm(msg, function(result) {
            if (result) {
                location.href = "/controller?command=blockcard&account=${account.getNumber()}&card=${card.getNumber()}";
            }
        });
    });
    $('#unblock').click(function(e) {
        e.preventDefault();
        var msg = 'Разблокировать карту ?';
        bootbox.confirm(msg, function(result) {
            if (result) {
                location.href = "/controller?command=unblockcard&account=${account.getNumber()}&card=${card.getNumber()}&cid=${card.getId()}";
            }
        });
    });
    $('#delete').click(function(e) {
        e.preventDefault();
        var msg = 'Удалить карту ?';
        bootbox.confirm(msg, function(result) {
            if (result) {
                location.href = "/controller?command=deletecard&cid=${card.getId()}";
            }
        });
    });
</script>


<jsp:include page="../template/footer.jsp" />
</body>
</html>
