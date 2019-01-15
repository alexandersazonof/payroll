<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="../template/header.jsp"/>
<div class="text-center">
    <h1 class="text-capitalize">
        Users list
    </h1>
</div>
<table class="table table-striped table-striped">
    <thead>
    <tr>
        <th>#</th>
        <th>Login</th>
        <th>Accounts</th>
        <th>Total money</th>
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
