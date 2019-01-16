<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 1/11/19
  Time: 12:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message bundle="${local}" key="local.showcard.card" var="localCard"/>
<fmt:message bundle="${local}" key="local.showcard.title" var="title"/>
<fmt:message bundle="${local}" key="local.showcard.cardnumber" var="localCardNumber"/>
<fmt:message bundle="${local}" key="local.showcard.money" var="localMoney"/>
<fmt:message bundle="${local}" key="local.showcard.customer" var="localCustomer"/>
<fmt:message bundle="${local}" key="local.showcard.rate" var="localRate"/>
<fmt:message bundle="${local}" key="local.showcard.accountnumber" var="localAccountNumber"/>
<fmt:message bundle="${local}" key="local.showcard.msg.block" var="localMsgBlock"/>
<fmt:message bundle="${local}" key="local.showcard.msg.unblock" var="localMsgUnblock"/>
<fmt:message bundle="${local}" key="local.showcard.msg.delete" var="localMsgDelete"/>
<fmt:message bundle="${local}" key="local.showaccount.active" var="localActive"/>
<fmt:message bundle="${local}" key="local.showaccount.block" var="localBlock"/>
<fmt:message bundle="${local}" key="local.showaccount.date" var="localDate"/>



<html>
<head>
    <title>${title}</title>
</head>
<body>
<jsp:include page="../template/header.jsp"/>

<h1 class="text-center">${localCard} №${card.getNumber()}</h1>
<hr>
<div class="container">
    <div class="row">
        <div class="col-sm-6">
            <img src="../img/${company.getName()}${rate.getName()}.png" width="300" height="150">
        </div>
        <div class="col-sm-6">
            <div class="form-group row">
                <label for="card" class="col-sm-2 col-form-label">${localCardNumber}</label>
                <div class="col-sm-4">
                    <input type="text" readonly class="form-control-plaintext" id="card" value="${card.getNumber()}" name="card">
                </div>
                <div class="col-sm-1">
                    <img src="../img/${company.getId()}.png" width="30" height="25">
                </div>
                <div class="col-sm-2">
                    <c:choose>
                        <c:when test = "${status == true}">
                            <span class="badge badge-success">${localActive}</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge badge-danger">${localBlock}</span>
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
                <label for="money" class="col-sm-2 col-form-label">${localMoney}</label>
                <div class="col-sm-4">
                    <p id="money">${card.getMoney()} <strong>${valute.getName()}</strong>  </p>
                </div>
            </div>
            <div class="form-group row">
                <label for="date" class="col-sm-2 col-form-label">${localDate}</label>
                <div class="col-sm-4">
                    <input type="text" readonly class="form-control-plaintext" id="date" value="${card.getDate()}">
                </div>
            </div>
            <div class="form-group row">
                <label for="customer" class="col-sm-2 col-form-label">${localCustomer}</label>
                <div class="col-sm-6">
                    <input type="text" readonly class="form-control-plaintext" id="customer" value="${card.getCustomer()}">
                </div>
            </div>
            <div class="form-group row">
                <label for="rate" class="col-sm-2 col-form-label">${localRate}</label>
                <div class="col-sm-4">
                    <input type="text" readonly class="form-control-plaintext" id="rate" value="${rate.getName()}">
                </div>
            </div>
            <div class="form-group row">
                <label for="account" class="col-sm-2 col-form-label">${localAccountNumber}</label>
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
        var msg = '${localMsgBlock}';
        bootbox.confirm(msg, function(result) {
            if (result) {
                location.href = "/controller?command=blockcard&account=${account.getNumber()}&card=${card.getNumber()}";
            }
        });
    });
    $('#unblock').click(function(e) {
        e.preventDefault();
        var msg = '${localMsgUnblock}';
        bootbox.confirm(msg, function(result) {
            if (result) {
                location.href = "/controller?command=unblockcard&account=${account.getNumber()}&card=${card.getNumber()}&cid=${card.getId()}";
            }
        });
    });
    $('#delete').click(function(e) {
        e.preventDefault();
        var msg = '${localMsgDelete}';
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
