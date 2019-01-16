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

<fmt:message bundle="${local}" key="local.showaccount.account" var="localAccount"/>
<fmt:message bundle="${local}" key="local.showaccount.accountname" var="localAccountName"/>
<fmt:message bundle="${local}" key="local.showaccount.action" var="localAction"/>
<fmt:message bundle="${local}" key="local.showaccount.action" var="localNumber"/>
<fmt:message bundle="${local}" key="local.showaccount.title" var="title"/>
<fmt:message bundle="${local}" key="local.showaccount.card" var="localCards"/>
<fmt:message bundle="${local}" key="local.showaccount.date" var="localDate"/>
<fmt:message bundle="${local}" key="local.showaccount.freemoney" var="localFreeMoney"/>
<fmt:message bundle="${local}" key="local.showaccount.history" var="localHistory"/>
<fmt:message bundle="${local}" key="local.showaccount.search" var="localSearch"/>
<fmt:message bundle="${local}" key="local.showaccount.searchby" var="localSearchBy"/>
<fmt:message bundle="${local}" key="local.showaccount.totalmoney" var="localTotalMoney"/>
<fmt:message bundle="${local}" key="local.showaccount.msg.block" var="localBlock"/>
<fmt:message bundle="${local}" key="local.showaccount.msg.delete" var="localDelete"/>
<fmt:message bundle="${local}" key="local.showaccount.msg.unblock" var="localUnblock"/>
<fmt:message bundle="${local}" key="local.showaccount.active" var="localActive"/>
<fmt:message bundle="${local}" key="local.showaccount.block" var="localBlockStatus"/>




<html>
<head>
    <title>${title}</title>

    <link rel="stylesheet" href="../css/index.css">
</head>
<body>
<jsp:include page="../template/header.jsp"/>




<div class="container">
    <div class="text-center">
        <h1><strong>${localAccount} : </strong>${bankAccount.getName()}</h1>
        <hr>
    </div>
    <div class="row">
        <div class="col-sm-6">
            <h2 class="text-center">${localHistory}</h2>
            <div class="form-group row">
            <div class="col-sm-6">
                <div class="input-group mb-3">
                    <input type="text" name="keyWord" id="keyWord" class="form-control" placeholder="${localSearchBy}" aria-label="Recipient's username" aria-describedby="search">
                    <div class="input-group-append">
                        <button class="btn btn-outline-secondary" type="button" id="search">${localSearch}</button>
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
                        <th scope="col">${localAction}</th>
                     <th scope="col">${localDate}</th>
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
                <label for="accountNumber" class="col-sm-2 col-form-label">${localAccount}:</label>
                <div class="col-sm-4">
                    <input type="text" readonly class="form-control-plaintext" id="accountNumber" value="${bankAccount.getNumber()}" name="accountNumber">
                </div>
                <div class="col-sm-2">
                    <c:choose>
                        <c:when test = "${bankAccount.isStatus() == true}">
                            <span class="badge badge-success">${localActive}</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge badge-danger">${localBlockStatus}</span>
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
                <label for="accountName" class="col-sm-2 col-form-label">${localAccountName}</label>
                <div class="col-sm-4">
                    <input type="text" readonly class="form-control-plaintext" id="accountName" value="${bankAccount.getName()}" name="accountName">
                </div>
            </div>
            <div class="form-group row">
                <label for="accountName" class="col-sm-2 col-form-label">${localCards}</label>
                    <div class="from-group">
                    <c:forEach items="${bankAccount.getCardList()}" var="item">
                        <div class="list-group">
                             <a href="/controller?command=showcardpage&cid=${item.getId()}" class="list-group-item list-group-item-light"><img src="../img/${item.getCompany()}.png" width="30" height="25">${item.getNumber()}</a>
                        </div>
                    </c:forEach>
                    </div>
            </div>
            <div class="form-group row">
                <label for="freeMoney" class="col-sm-2 col-form-label">${localFreeMoney}</label>
                <div class="col-sm-4">
                    <a href="/controller?command=TRANSFERACCOUNTMONEY&accountNumber=${bankAccount.getNumber()}" id="freeMoney">
                        ${bankAccount.getCountOfMoney()}  ${bankAccount.getValute()}
                    </a>
                </div>
            </div>
            <div class="form-group row">
                <label for="allMoney" class="col-sm-2 col-form-label">${localTotalMoney}</label>
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
        var msg = '${localBlock}';
        bootbox.confirm(msg, function(result) {
            if (result) {
                location.href = "/controller?command=BLOCKACCOUNT&accountNumber=${bankAccount.getNumber()}";
            }
        });
    });

    $('#unblock').click(function(e) {
        e.preventDefault();
        var msg = '${localUnblock}';
        bootbox.confirm(msg, function(result) {
            if (result) {
                location.href = "/controller?command=UNBLOCKACCOUNT&accountNumber=${bankAccount.getNumber()}";
            }
        });
    });
    $('#delete').click(function(e) {
        e.preventDefault();
        var msg = '${localDelete}';
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
