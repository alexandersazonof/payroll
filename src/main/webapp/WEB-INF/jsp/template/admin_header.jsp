
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message bundle="${local}" key="local.admin.application" var="application"/>
<fmt:message bundle="${local}" key="local.admin.rate" var="rate"/>
<fmt:message bundle="${local}" key="local.admin.operation" var="operation"/>
<fmt:message bundle="${local}" key="local.admin.valute" var="valute"/>
<fmt:message bundle="${local}" key="local.menu.logout" var="logout"/>
<fmt:message bundle="${local}" key="local.siteName" var="site"/>

<html>
<head>
    <title>Title</title>
    <link href="/../css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/jquery-3.2.1.slim.min.js"></script>
    <script src="../js/popper.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/controller?command=mainPage">
        <img src="../img/logo.png" width="30" height="30" class="d-inline-block align-top" alt="">
        ${site}
    </a>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/controller?command=VALUTEPAGE">${valute}</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/controller?command=RATEPAGE">${rate}</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/controller?command=HISTORYTRANSFER">${operation}</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/controller?command=APPLICATION">
                    ${application}<span class="badge badge-danger">${sessionScope.applicationSize}</span>
                </a>
            </li>
        </ul>
    </div>
    <span class="navbar-text">
      <a class="nav-link" href="/controller?command=Logout">${logout}</a>
    </span>
</nav>
</body>
</html>
