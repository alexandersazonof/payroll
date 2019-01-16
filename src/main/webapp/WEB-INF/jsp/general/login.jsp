<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 12/19/18
  Time: 11:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message bundle="${local}" key="local.signin.siteName" var="siteName"/>
<fmt:message bundle="${local}" key="local.signin.formName" var="formName"/>
<fmt:message bundle="${local}" key="local.error.login" var="errorLogin"/>
<fmt:message bundle="${local}" key="local.error.password" var="errorPassword"/>
<fmt:message bundle="${local}" key="local.signin.button" var="button" />
<html>
<head>
    <title>${siteName}</title>
    <link rel="stylesheet" href="../../../css/font-awesome.min.css">
    <link rel="stylesheet" href="../../../css/bootstrap.min.css">
    <script src="../../../js/bootstrap.min.js"></script>
    <script src="../../../js/jquery-1.11.1.min.js"></script>

</head>
<body>
<%@ include file="../template/general_header.jsp" %>

<c:set var = "wrongLogin" scope = "session" value = "${param.wrongLogin}"/>
<c:set var="wrongPassword" scope="session" value="${param.wrongPassword}"/>


<div class="container">
    <form class="form-horizontal" role="form" method="POST" action="/controller">
        <input type="hidden" name="command" value="login" />

        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <h2>${formName}</h2>
                <hr>
            </div>
        </div>
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <div class="form-group">
                    <label class="sr-only" for="login">Text</label>
                    <div class="input-group mb-2 mr-sm-2 mb-sm-0">
                        <div class="input-group-addon" style="width: 2.6rem"><i class="fa fa-user"></i></div>
                        <input type="text" name="login" class="form-control" id="login"
                               placeholder="Login" required autofocus
                                value="${param.login}">
                    </div>
                </div>
            </div>
            <c:if test="${wrongLogin != null}">

            <div class="col-md-3">
                <div class="form-control-feedback">
                        <span class="text-danger align-middle">
                            ${errorLogin}
                        </span>
                </div>
            </div>
            </c:if>
        </div>
        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <div class="form-group">
                    <label class="sr-only" for="password">Password</label>
                    <div class="input-group mb-2 mr-sm-2 mb-sm-0">
                        <div class="input-group-addon" style="width: 2.6rem"><i class="fa fa-key"></i></div>
                        <input type="password" name="password" class="form-control" id="password"
                               placeholder="Password" required>
                    </div>
                </div>
            </div>

            <c:if test="${wrongPassword != null}">
            <div class="col-md-3">
                <div class="form-control-feedback">
                        <span class="text-danger align-middle">
                            ${errorPassword}
                        </span>
                </div>
            </div>
            </c:if>
        </div>
        <div class="row" style="padding-top: 1rem">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <button type="submit" class="btn btn-success"><i class="fa fa-sign-in"></i> ${button}</button>
            </div>
        </div>
    </form>
</div>


<%@ include file="../template/footer.jsp" %>

</body>
</html>
