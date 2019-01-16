<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 1/12/19
  Time: 3:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message bundle="${local}" key="local.transfercard.title" var="title"/>
<fmt:message bundle="${local}" key="local.transfercard.selectcard" var="localSelect"/>
<fmt:message bundle="${local}" key="local.transfercard.cardNumber" var="localNumber"/>
<fmt:message bundle="${local}" key="local.transfercard.countMoney" var="localMoney"/>
<fmt:message bundle="${local}" key="local.transfercard.password" var="localPassword"/>
<fmt:message bundle="${local}" key="local.transfercard.button" var="button"/>
<fmt:message bundle="${local}" key="local.transfercard.doTransfer" var="localAsk"/>


<html>
<head>
    <title>${title}</title>
</head>
<body>
<jsp:include page="../template/header.jsp"/>

<form method="post" action="/controller" id="form">
    <input type="hidden" name="command" value="sendcardmoney">
<div class="container">
    <div class="text-center">
        <h1>${title}</h1>
        <hr>
        <div class="form-group row">
            <label for="listCard" class="col-sm-2 col-form-label">${localSelect}</label>
            <div class="col-sm-10">
                <select id="listCard" class="form-control" name="fromCardNumber">
                    <c:forEach items="${cardList}" var = "item">
                        <option>${item.getNumber()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="form-group row">
            <label for="inputCard" class="col-sm-2 col-form-label">${localNumber}</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="inputCard"  name="toCardNumber" value="${param.toCard}">
            </div>
        </div>
        <div class="form-group row">
            <label for="money" class="col-sm-2 col-form-label">${localMoney}</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="money"  name="money" value="${param.count}">
            </div>
        </div>
        <div class="form-group row">
            <label for="password" class="col-sm-2 col-form-label">${localPassword}</label>
            <div class="col-sm-4">
                <input type="password" class="form-control" id="password"  name="password">
            </div>
        </div>
        <div class="form-group">
            <button type="button" class="btn btn-light" id="button">${button}</button>
        </div>
    </div>
</div>
</form>

<script src="../js/bootbox.min.js"></script>
<script type="text/javascript">
    $('#button').click(function(e) {
        e.preventDefault();
        var msg = '${localAsk}';
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
