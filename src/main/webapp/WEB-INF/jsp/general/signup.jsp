<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 12/6/18
  Time: 8:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message bundle="${local}" key="local.signup.siteName" var="siteName"/>
<fmt:message bundle="${local}" key="local.signup.formName" var="formName"/>
<fmt:message bundle="${local}" key="local.signup.name" var="name"/>
<fmt:message bundle="${local}" key="local.signup.email" var="email"/>
<fmt:message bundle="${local}" key="local.signup.password" var="password"/>
<fmt:message bundle="${local}" key="local.signup.confirmpassword" var="confirmPassword"/>
<fmt:message bundle="${local}" key="local.signup.firstName" var="firstName"/>
<fmt:message bundle="${local}" key="local.signup.lastName" var="lastName"/>
<fmt:message bundle="${local}" key="local.signup.button" var="button"/>
<fmt:message bundle="${local}" key="local.error.login" var="errorLogin"/>
<fmt:message bundle="${local}" key="local.error.password" var="errorPassword"/>
<fmt:message bundle="${local}" key="local.error.email" var="errorEmail"/>
<fmt:message bundle="${local}" key="local.error.value" var="errorValue"/>




<html>
<head>
    <title>${siteName}</title>
    <link rel="stylesheet" href="../../../css/bootstrap.min.css">

    <script src="../../../js/jquery-3.2.1.slim.min.js"></script>
    <script src="../../../js/popper.min.js"></script>
    <script src="../../../js/bootstrap.min.js"></script>

</head>
<body>

<%@ include file="../template/general_header.jsp" %>

<div class="container">
    <form class="form-horizontal" role="form" method="POST" action="/controller">

        <input type="hidden" name="command" value="signup" />

        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <h2>${formName}</h2>
                <hr>
            </div>
        </div>


        <div class="row">

            <div class="col-md-3 field-label-responsive">
                <label for="name">${name}</label>
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <div class="input-group mb-2 mr-sm-2 mb-sm-0">
                        <div class="input-group-addon" style="width: 2.6rem"><i class="fa fa-user"></i></div>
                        <input type="text" name="username" class="form-control" id="name"
                               value="${param.username}" required autofocus>
                    </div>
                </div>
            </div>


            <c:set var = "userNameError" scope = "session" value = "${param.wrongLogin}"/>
                <c:if test = "${userNameError != null}">
            <div class="col-md-3">
                <div class="form-control-feedback">
                        <span class="text-danger align-middle">
                            <i class="fa fa-close">${errorLogin}</i>
                        </span>
                </div>
            </div>
                </c:if>

        </div>

        <div class="row">

            <div class="col-md-3 field-label-responsive">
                <label for="email">${email}</label>
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <div class="input-group mb-2 mr-sm-2 mb-sm-0">
                        <div class="input-group-addon" style="width: 2.6rem"><i class="fa fa-at"></i></div>
                        <input type="text" name="email" class="form-control" id="email" value="${param.email}"
                                required autofocus>
                    </div>
                </div>
            </div>

            <c:set var = "emailError" scope = "session" value = "${param.wrongEmail}"/>

                <c:if test = "${emailError != null}">


            <div class="col-md-3">
                <div class="form-control-feedback">
                        <span class="text-danger align-middle">
                            <i class="fa fa-close"> ${errorEmail} </i>
                        </span>
                </div>
            </div>

            </c:if>
        </div>

        <div class="row">

            <div class="col-md-3 field-label-responsive">
                <label for="password">${password}</label>
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <div class="input-group mb-2 mr-sm-2 mb-sm-0">
                        <div class="input-group-addon" style="width: 2.6rem"><i class="fa fa-key"></i></div>
                        <input type="password" name="password" class="form-control" id="password"
                               placeholder="Password" required>
                    </div>
                </div>
            </div>

            <c:set var = "passwordError" scope = "session" value = "${param.wrongPassword}"/>
            <c:if test = "${passwordError != null}">
            <div class="col-md-3">
                <div class="form-control-feedback">
                        <span class="text-danger align-middle">
                            <i class="fa fa-close"> ${errorPassword}</i>
                        </span>
                </div>
            </div>

        </c:if>
        </div>
        <div class="row">
            <div class="col-md-3 field-label-responsive">
                <label for="password">${confirmPassword}</label>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <div class="input-group mb-2 mr-sm-2 mb-sm-0">
                        <div class="input-group-addon" style="width: 2.6rem">
                            <i class="fa fa-repeat"></i>
                        </div>
                        <input type="password" name="password_confirm" class="form-control"
                               id="password-confirm" placeholder="Password" required>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">

            <div class="col-md-3 field-label-responsive">
                <label for="name">${firstName}</label>
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <div class="input-group mb-2 mr-sm-2 mb-sm-0">
                        <div class="input-group-addon" style="width: 2.6rem"><i class="fa fa-user"></i></div>
                        <input type="text" name="firstName" class="form-control" id="firstName"
                               value="${param.firstName}" required autofocus>
                    </div>
                </div>
            </div>


        </div>

        <div class="row">

            <div class="col-md-3 field-label-responsive">
                <label for="name">${lastName}</label>
            </div>

            <div class="col-md-6">
                <div class="form-group">
                    <div class="input-group mb-2 mr-sm-2 mb-sm-0">
                        <div class="input-group-addon" style="width: 2.6rem"><i class="fa fa-user"></i></div>
                        <input type="text" name="lastName" class="form-control" id="lastName"
                               value="${param.lastName}" required autofocus>
                    </div>
                </div>
            </div>
        </div>


        <c:set var = "erroValue" scope = "session" value = "${param.wrongValue}"/>
        <c:if test = "${erroValue != null}">
            <div class="alert alert-danger" role="alert">
                ${errorValue}
            </div>
        </c:if>


        <div class="row">
            <div class="col-md-3"></div>
            <div class="col-md-6">
                <button type="submit" class="btn btn-success"><i class="fa fa-user-plus"></i> ${button}</button>
            </div>
        </div>
    </form>
</div>

<br><br><br><br><br><br>



<style>
    @media(min-width: 768px) {
        .field-label-responsive {
            padding-top: .5rem;
            text-align: right;
        }
    }
</style>

<%@ include file="../template/footer.jsp" %>
</body>
</html>
