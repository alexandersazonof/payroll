<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 12/28/18
  Time: 12:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="../template/header.jsp"/>




<div class="container">
    <div class="row">
        <div class="col">
            <h3>Счета</h3>
        </div>
        <div class="col">
            <a href="/controller?command=newaccountpage"><img src="../img/plus.png" width="15" height="15">Открыть</a>
        </div>
    </div>
<ul class="list-group-flush">
<c:forEach items="${listBankAccount}" var="item">
    <li class="list-group-item">
        <div class="row">
            <div class="col">
                <a href="/controller?command=showaccount&number=${item.getNumber()}">${item.getName()}</a>
            </div>

            <div class="col">
            <c:forEach items="${item.getCardList()}" var="card">
                <div class="row">
                    <div class="col">
                        <a href="/controller?command=showcardpage&cid=${card.getId()}"><img src="../img/${card.getCompany()}.png" width="30" height="25" data-toggle="tooltip" data-placement="top" title="${card.getNumber()}"></a>
                    </div>
                    <div class="col">
                            ${card.getMoney()}
                        <strong>${card.getValute()}</strong>
                    </div>
                </div>
            </c:forEach>
            </div>
            <div class="row">
                Всего ${item.getCountOfMoney()} <strong>${item.getValute()}</strong>
            </div>
        </div>
    </li>
</c:forEach>
</ul>
</div>

<div class="container">
    <div class="row">
        <div class="col">
            <h3>Кредиты</h3>
        </div>
        <div class="col">
            <a href="/controller?command=newcardpage"><img src="../img/plus.png" width="15" height="15">Оформить</a>
        </div>
    </div>
    <ul class="list-group-flush">
    </ul>
</div>




<jsp:include page="../template/footer.jsp" />

</body>
</html>
