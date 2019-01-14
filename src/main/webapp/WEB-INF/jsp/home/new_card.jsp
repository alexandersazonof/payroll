<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 12/29/18
  Time: 12:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
    <title>Title</title>

</head>
<body>
<jsp:include page="../template/header.jsp"/>

<c:choose>
    <c:when test="${param.msgbox != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            Ознакомтесь с правилами
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.msgvalue != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            Некоректные данные
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.block != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            Счёт заблокирован
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
</c:choose>

<form method="post" action="/controller">
    <input type="hidden" name="command" value="newcard">
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="inputEmail4">First name</label>
            <input type="text" class="form-control" id="inputEmail4" placeholder="First name" name="firstName" value="${param.firstName}">
        </div>
        <div class="form-group col-md-6">
            <label for="inputPassword4">Last name</label>
            <input type="text" class="form-control" id="inputPassword4" placeholder="Last name" name="lastName" value="${param.lastName}">
        </div>
    </div>
    <div class="form-group">
        <label for="inputAddress">Address</label>
        <input type="text" class="form-control" id="inputAddress" placeholder="1234 Main St" name="address" value="${param.address}">
    </div>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="inputCity">City</label>
            <input type="text" class="form-control" id="inputCity" name="city" value="${param.city}">
        </div>
        <div class="form-group col-md-6">
            <label for="inputState">Company</label>
            <select id="inputState" class="form-control" name="company">
                <c:forEach items="${listCompany}" var = "item">
                    <option>${item.name}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="form-row">
        <div class="form-group col-md-4">
            <label for="inputAccount">Account</label>
            <select id="inputAccount" class="form-control" name="account">
                <c:forEach items="${listBankAccount}" var = "item">
                    <option>${item.number}</option>
                    </c:forEach>
            </select>
        </div>
        <div class="form-group col-md-4">
            <label for="inputRate">Rate</label>
            <select id="inputRate" class="form-control" name="rate">
                <c:forEach items="${listRate}" var = "itemsa">
                    <option>${itemsa.name}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="form-group">
        <div class="form-check">
            <input class="form-check-input" type="checkbox" id="gridCheck" name="rules">
            <label class="form-check-label" for="gridCheck">
                Confirm with
            </label>
            <a data-toggle="modal" data-target="#myModal" href="">rules</a>
        </div>
    </div>
    <button type="submit" class="btn btn-primary">Get card</button>
</form>



<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">Rules</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>

            </div>
            <div class="modal-body">
                <p>Charge only what you can afford
                This rule is simple. With a credit card, you can walk out of a store with an extravagant item that you really can't afford. Don't do that. Live within or below your means.
                </p>
                <p>
                Aim to pay each bill in full
                Making only partial payments will leave you with growing debt. That can be very hazardous to your wealth.
                </p>
                <p>
                Pay each credit card bill on time
                You can pay your bills late, but there are lots of reasons not to. Make it a personal rule to always pay your bill on time. Being late can hurt your credit score and can result in interest charges, too.
                </p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Ok</button>
            </div>
        </div>
    </div>
</div>


<jsp:include page="../template/footer.jsp" />

</body>
</html>
