<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 1/12/19
  Time: 3:28 PM
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

<form method="post" action="/controller" id="form">
    <input type="hidden" name="command" value="sendcardmoney">
<div class="container">
    <div class="text-center">
        <h1>Transfer money</h1>
        <hr>
        <div class="form-group row">
            <label for="listCard" class="col-sm-2 col-form-label">Select card</label>
            <div class="col-sm-10">
                <select id="listCard" class="form-control" name="fromCardNumber">
                    <c:forEach items="${cardList}" var = "item">
                        <option>${item.getNumber()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group row">
            <label for="inputCard" class="col-sm-2 col-form-label">Card number</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="inputCard"  name="toCardNumber" value="${param.toCard}">
            </div>
        </div>
        <div class="form-group row">
            <label for="money" class="col-sm-2 col-form-label">Transfer amount</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="money"  name="money" value="${param.count}">
            </div>
        </div>
        <div class="form-group row">
            <label for="password" class="col-sm-2 col-form-label">Your password</label>
            <div class="col-sm-4">
                <input type="password" class="form-control" id="password"  name="password">
            </div>
        </div>
        <div class="form-group">
            <button type="button" class="btn btn-light" id="button">Transfer</button>
        </div>
    </div>
</div>
</form>

<script src="../js/bootbox.min.js"></script>
<script type="text/javascript">
    $('#button').click(function(e) {
        e.preventDefault();
        var msg = 'Перевести деньги ?';
        bootbox.confirm(msg, function(result) {
            if (result) {
                $('#form').submit();
            }
        });
    });
</script>

<jsp:include page="../template/footer.jsp" />
</body>
</html>
