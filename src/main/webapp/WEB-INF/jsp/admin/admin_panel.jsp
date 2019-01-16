<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message bundle="${local}" key="local.adminmain.title" var="title"/>
<fmt:message bundle="${local}" key="local.adminmain.nameMain" var="localNameMain"/>
<fmt:message bundle="${local}" key="local.adminmain.login" var="localLogin"/>
<fmt:message bundle="${local}" key="local.adminmain.Account" var="localAccount"/>
<fmt:message bundle="${local}" key="local.adminmain.money" var="localMoney"/>

<html>
<head>
    <title>${title}</title>
</head>
<body>
<jsp:include page="../template/header.jsp"/>
<div class="text-center">
    <h1 class="text-capitalize">
        ${localNameMain}
    </h1>
</div>
<table class="table table-striped table-striped">
    <thead>
    <tr>
        <th>#</th>
        <th>${localLogin}</th>
        <th>${localAccount}</th>
        <th>${localMoney}</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${userWithBankAccountList}">
        <tr>
            <th scope="row">${userWithBankAccountList.indexOf(item)+1}</th>
            <th>
                <div class="row">
                    <div class="col-sm-1">
                        <img src="../img/user.png" alt="Smiley face" height="42" width="42">
                    </div>
                    <div class="col-sm-2">
                        <h3>${item.getLogin()}</h3>
                    </div>
                </div>

            </th>
            <th>
                <c:forEach var="account" items="${item.getBankAccountList()}">
                    <p><a href="/controller?command=showaccount&number=${account.getNumber()}">${account.getNumber()}</a></p>
                </c:forEach>
            </th>
            <th>
                <c:forEach var="account" items="${item.getBankAccountList()}">
                    <p>${account.getCountOfMoney()} ${account.getValute()}</p>
                </c:forEach>
            </th>
        </tr>
    </c:forEach>
    </tbody>
</table>


<jsp:include page="../template/footer.jsp"/>

</body>
</html>
