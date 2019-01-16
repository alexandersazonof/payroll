<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 12/29/18
  Time: 12:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message bundle="${local}" key="local.newcard.title" var="localTitle"/>
<fmt:message bundle="${local}" key="local.newcard.account" var="localAccount"/>
<fmt:message bundle="${local}" key="local.newcard.address" var="localAddress"/>
<fmt:message bundle="${local}" key="local.newcard.button" var="button"/>
<fmt:message bundle="${local}" key="local.newcard.city" var="localCity"/>
<fmt:message bundle="${local}" key="local.newcard.company" var="localCompany"/>
<fmt:message bundle="${local}" key="local.newcard.confirm" var="localConfrim"/>
<fmt:message bundle="${local}" key="local.newcard.firstname" var="localFirst"/>
<fmt:message bundle="${local}" key="local.newcard.lastname" var="localLast"/>
<fmt:message bundle="${local}" key="local.newcard.rate" var="localRate"/>
<fmt:message bundle="${local}" key="local.newcard.rules" var="localRules"/>
<fmt:message bundle="${local}" key="local.newcard.rules1" var="rules1"/>
<fmt:message bundle="${local}" key="local.newcard.rules2" var="rules2"/>
<fmt:message bundle="${local}" key="local.newcard.rules3" var="rules3"/>
<html>
<head>
    <title>${localTitle}</title>

</head>
<body>
<jsp:include page="../template/header.jsp"/>

<div class="text-center">
    <h2>
        ${localTitle}
    </h2>
    <hr>
</div>

<form method="post" action="/controller">
    <input type="hidden" name="command" value="newcard">
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="inputEmail4">${localFirst}</label>
            <input type="text" class="form-control" id="inputEmail4" placeholder="${localFirst}" name="firstName" value="${param.firstName}">
        </div>
        <div class="form-group col-md-6">
            <label for="inputPassword4">${localLast}</label>
            <input type="text" class="form-control" id="inputPassword4" placeholder="${localLast}" name="lastName" value="${param.lastName}">
        </div>
    </div>
    <div class="form-group">
        <label for="inputAddress">${localAddress}</label>
        <input type="text" class="form-control" id="inputAddress" placeholder="1234 Main St" name="address" value="${param.address}">
    </div>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="inputCity">${localCity}</label>
            <input type="text" class="form-control" id="inputCity" name="${localCity}" value="${param.city}">
        </div>
        <div class="form-group col-md-6">
            <label for="inputState">${localCompany}</label>
            <select id="inputState" class="form-control" name="company">
                <c:forEach items="${listCompany}" var = "item">
                    <option>${item.name}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="form-row">
        <div class="form-group col-md-4">
            <label for="inputAccount">${localAccount}</label>
            <select id="inputAccount" class="form-control" name="account">
                <c:forEach items="${listBankAccount}" var = "item">
                    <option>${item.number}</option>
                    </c:forEach>
            </select>
        </div>
        <div class="form-group col-md-4">
            <label for="inputRate">${localRate}</label>
            <select id="inputRate" class="form-control" name="rate">
                <c:forEach items="${listRate}" var = "itemsa">
                    <option>${itemsa.name}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="form-group">
        <div class="form-check">
            <input class="form-check-input" type="checkbox" id="gridCheck" name="rules">
            <label class="form-check-label" for="gridCheck">
                ${localConfrim}
            </label>
            <a data-toggle="modal" data-target="#myModal" href="">rules</a>
        </div>
    </div>
    <button type="submit" class="btn btn-primary">${button}</button>
</form>



<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">${localRules}</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>

            </div>
            <div class="modal-body">
                <p>${rules1}</p>
                <p>
                ${rules2}
                </p>
                <p>
                ${rules3}
                </p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Ok</button>
            </div>
        </div>
    </div>
</div>


<jsp:include page="../template/footer.jsp" />

</body>
</html>
