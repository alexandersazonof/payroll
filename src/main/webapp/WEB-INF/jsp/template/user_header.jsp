<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 12/17/18
  Time: 11:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message key="local.menu.newcard" bundle="${local}" var="newCard"/>
<fmt:message key="local.menu.showcard" bundle="${local}" var="showCard"/>
<fmt:message key="local.menu.transfer" bundle="${local}" var="transfer"/>
<fmt:message key="local.menu.welcome" bundle="${local}" var="welcome"/>
<fmt:message key="local.menu.logout" bundle="${local}" var="logout"/>
<fmt:message key="local.siteName" bundle="${local}" var="nameBank"/>
<fmt:message key="local.menu.history" bundle="${local}" var="history"/>

<html>
<head>
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="../../css/bootstrap.css">
    <script src="../../js/jquery-3.2.1.slim.min.js"></script>
    <script src="../../js/popper.min.js"></script>
    <script src="../../js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/controller?command=mainPage">
        <img src="../../../img/logo.png" width="30" height="30" class="d-inline-block align-top" alt="">
        ${nameBank}
    </a>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/controller?command=newcardpage">${newCard}</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/controller?command=transfermoney">${transfer}</a>
            </li>
        </ul>
    </div>
    <span class="nav-item active">
        ${welcome} , ${user.getLastName()}  ${user.getFirstName()}
    </span>
    <span class="navbar-text">
      <a class="nav-link" href="/controller?command=Logout">${logout}</a>
    </span>
</nav>
</body>
</html>
