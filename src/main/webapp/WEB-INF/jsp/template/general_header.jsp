<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 12/17/18
  Time: 11:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message key="local.login" bundle="${local}" var="loginName"/>
<fmt:message key="local.signup" bundle="${local}" var="signupName"/>
<fmt:message bundle="${local}" key="local.siteName" var="siteName"/>
<html>
<head>

    <link href="../../../css/font-awesome.min.css">

    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="../../../css/bootstrap.css">

    <link rel="stylesheet" href="../../css/bootstrap.min.css">
    <script src="../../js/jquery-3.2.1.slim.min.js"></script>
    <script src="../../js/popper.min.js"></script>
    <script src="../../js/bootstrap.min.js"></script>

</head>
<body>
<!-- Image and text -->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/controller?command=mainPage">
        <img src="../../../img/logo.png" width="30" height="30" class="d-inline-block align-top" alt="">
        ${siteName}
    </a>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item active">
                <a class="nav-link" href="/controller?command=loginPage">${loginName}</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/controller?command=signupPage">${signupName}</a>
            </li>
        </ul>
    </div>
</nav>
</body>
</html>
