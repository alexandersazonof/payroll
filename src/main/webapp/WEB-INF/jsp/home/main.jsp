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
<jsp:include page="../template/user_header.jsp" />
<c:choose>
    <c:when test="${param.msg != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Новый счёт успешно создан
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.successcard != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Новая карта успешно создана
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.useraccess != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            Нет прав доступа
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.incorrectcard != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            Некорректный ид карты , пожалуйсто обратитесь к администратору
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.wrongquery != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            Некорректный запрос
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.scdrop != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Карта успешно удалена , <strong>деньги переведены на счёт</strong>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
</c:choose>


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
                <a href="/controller?command=editcard&Number=${item.getNumber()}">${item.getName()}</a>
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
