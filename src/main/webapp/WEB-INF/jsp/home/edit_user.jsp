<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 12/25/18
  Time: 3:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message bundle="${local}" key="local.error.login" var="errorLogin"/>
<fmt:message bundle="${local}" key="local.error.email" var="errorEmail"/>
<fmt:message bundle="${local}" key="local.error.name" var="errorname"/>
<fmt:message bundle="${local}" key="local.error.password" var="errorPassword"/>
<fmt:message bundle="${local}" key="local.error.password_confirm" var="errorPasswordConfirm"/>

<fmt:message bundle="${local}" key="local.signup.name" var="name"/>
<fmt:message bundle="${local}" key="local.signup.email" var="email"/>
<fmt:message bundle="${local}" key="local.signup.password" var="password"/>
<fmt:message bundle="${local}" key="local.signup.confirmpassword" var="confirmPassword"/>
<fmt:message bundle="${local}" key="local.signup.firstName" var="firstName"/>
<fmt:message bundle="${local}" key="local.signup.lastName" var="lastName"/>
<fmt:message bundle="${local}" key="local.edituser.newpassword" var="newPassword"/>
<fmt:message bundle="${local}" key="local.edituser.save" var="save"/>
<fmt:message bundle="${local}" key="local.edituser.title" var="title"/>
<html>
<head>
    <title>${title}</title>
</head>
<body>
<c:set var = "wrongLogin" scope = "session" value = "${param.wrongLogin}"/>
<c:set var="wrongPassword" scope="session" value="${param.wrongPassword}"/>
<c:set var = "wrongName" scope = "session" value = "${param.wrongName}"/>
<c:set var="wrongPasswordConfirm" scope="session" value="${param.wrongPasswordConfirm}"/>
<c:set var="wrongEmail" scope="session" value="${param.wrongEmail}"/>

<%@ include file="../template/user_header.jsp" %>



<form method="post" action="/controller">
    <input type="hidden" name="command" value="edituser">
    <div class="form-group">
        <label for="name">${name}</label>
        <input type="text" class="form-control" id="name" name="login" value="${user.getLogin()}">
    </div>
    <div class="form-group">
        <label for="email">${email}</label>
        <input type="text" class="form-control" id="email" name="email" value="${user.getEmail()}">
    </div>
    <div class="form-group">
        <label for="first-name">${firstName}</label>
        <input type="text" class="form-control" id="first-name" name="firstName" value="${user.getFirstName()}">
    </div>
    <div class="form-group">
        <label for="last-name">${lastName}</label>
        <input type="text" class="form-control" id="last-name" name="lastName"  value="${user.getLastName()}">
    </div>
    <div class="form-group">
        <label for="new-password">${newPassword}</label>
        <input type="password" class="form-control" id="new-password" name="newPassword">
    </div>
    <div class="form-group">
        <label for="confirm-password">${confirmPassword}</label>
        <input type="password" class="form-control" id="confirm-password" name="confirmPassword">
    </div>
    <div class="form-group">
        <label for="password">${password}</label>
        <input type="password" class="form-control" id="password" name="password">
    </div>
    <c:choose>
        <c:when test="${wrongLogin != null}">
            <div class="alert alert-danger" role="alert">
                ${errorLogin}
            </div>
        </c:when>
        <c:when test="${wrongEmail != null}">
            <div class="alert alert-danger" role="alert">
                ${errorEmail}
            </div>
        </c:when>
        <c:when test="${wrongName != null}">
            <div class="alert alert-danger" role="alert">
                ${errorname}
            </div>
        </c:when>
        <c:when test="${wrongPasswordConfirm != null}">
            <div class="alert alert-danger" role="alert">
                ${errorPasswordConfirm}
            </div>
        </c:when>
        <c:when test="${wrongPassword != null}">
            <div class="alert alert-danger" role="alert">
                ${errorPassword}
            </div>
        </c:when>
    </c:choose>
    <input type="submit" value="${save}">

</form>


<jsp:include page="../template/footer.jsp" />

</body>
</html>
