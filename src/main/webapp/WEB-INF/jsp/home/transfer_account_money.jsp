<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 1/13/19
  Time: 6:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message bundle="${local}" key="local.transferaccount.title" var="title"/>
<fmt:message bundle="${local}" key="local.transferaccount.number" var="localNumber"/>
<fmt:message bundle="${local}" key="local.transferaccount.cards" var="localCards"/>
<fmt:message bundle="${local}" key="local.transferaccount.freemoney" var="localFreeMoney"/>
<fmt:message bundle="${local}" key="local.transferaccount.sendmoney" var="localSendMoney"/>
<fmt:message bundle="${local}" key="local.transferaccount.transfer" var="localTransfer"/>
<html>
<head>
    <title>${title}</title>
</head>
<body>
<jsp:include page="../template/header.jsp"/>


<form action="/controller" method="post" name="createForm" id="createForm" class="createForm">
<input type="hidden" name="command" value="sendaccountmoney">
    <div class="text-center">
        <div class="form-group">
            <h1 class="text-capitalize">${title}</h1>
            <hr>
        </div>
        <div class="form-group row">
            <label for="accountNumber" class="col-sm-4 col-from-label">${localNumber}</label>
            <div class="col-sm-6">
                <input type="text" readonly id="accountNumber" class="form-control-plaintext" value="${accountNumber.getNumber()}" name="accountNumber">
            </div>
        </div>
        <div class="form-group row">
            <label for="cardList" class="col-sm-4 col-form-label">${localCards}</label>
            <div class="col-sm-6">
                <select id="cardList" class="form-control" name="cardNumber">
                    <c:forEach var="card" items="${cardList}">
                        <option>${card.getNumber()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group row">
            <label for="freeMoney" class="col-sm-4 col-form-label">${localFreeMoney}</label>
            <div class="col-sm-6">
                <input type="text" id="freeMoney" readonly class="form-control-plaintext" name="freeMoney" value="${accountNumber.getCountOfMoney()} ${accountNumber.getValute()}">
            </div>
        </div>

        <div class="form-group row">
            <label for="money" class="col-sm-4 col-form-label">${localSendMoney}</label>
            <div class="col-sm-6">
                <input type="text" id="money" class="form-control" name="money">
            </div>
        </div>

        <button type="submit" class="btn btn-light" id="submit">${localTransfer}</button>

</div>
</form>


<jsp:include page="../template/footer.jsp" />
</body>
</html>
