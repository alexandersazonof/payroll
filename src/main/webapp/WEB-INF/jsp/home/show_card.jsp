<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 1/11/19
  Time: 12:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="../template/user_header.jsp" %>

<h1 class="text-center">Card №${card.getNumber()}</h1>
<hr>
<div class="container">
    <div class="row">
        <div class="col-sm-6">
            <img src="../img/${company.getName()}${rate.getName()}.png" width="300" height="150">
            <br>
            <div class="form-group row">
                <div class="col-sm-1">
                    <img src="../img/delete.png" width="25" height="25">
                </div>
                <div class="col-sm-1">
                    <img src="../img/edit.svg" width="25" height="25">
                </div>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="form-group row">
                <label for="card" class="col-sm-2 col-form-label">Card number:</label>
                <div class="col-sm-4">
                    <input type="text" readonly class="form-control-plaintext" id="card" value="${card.getNumber()}">
                </div>
                <div class="col-sm-1">
                    <img src="../img/${company.getName()}.png" width="30" height="25">
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
                    <input type="text" readonly class="form-control-plaintext" id="account" value="${account.getNumber()}">
                </div>
            </div>
        </div>
    </div>
    <div class="row">

    </div>
</div>

<jsp:include page="../template/footer.jsp" />
</body>
</html>
