<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 12/20/18
  Time: 10:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message key="local.history.title" bundle="${local}" var="title"/>
<fmt:message key="local.history.from" bundle="${local}" var="from"/>
<fmt:message key="local.history.to" bundle="${local}" var="to"/>
<fmt:message key="local.history.count" bundle="${local}" var="count"/>
<fmt:message key="local.history.date" bundle="${local}" var="date"/>

<html>
<head>
    <title>${title}</title>
</head>
<body>

<%@ include file="../template/user_header.jsp" %>



<ul class="list-group list-group-flush">
    <c:forEach items="${historyList}" var="item">
        <li class="list-group-item">
            <form>
                <div class="form-group row">
                    <label for="name" class="col-sm-2 col-form-label" >${from} </label>
                    <div class="col-sm-10">
                        <input type="text" id="name" readonly class="form-control-plaintext" value="${item.getFromNumber()}">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="names" class="col-sm-2 col-form-label" >${to}</label>
                    <div class="col-sm-10">
                        <input type="text" id="names" readonly class="form-control-plaintext" value="${item.getToNumber()}">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="date" class="col-sm-2 col-form-label" >${date}</label>
                    <div class="col-sm-10">
                        <input type="text" id="date" readonly class="form-control-plaintext" value="${item.getDate()}">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="money" class="col-sm-2 col-form-label" >${count}</label>
                    <div class="col-sm-10">
                        <input type="text" id="money" readonly class="form-control-plaintext" value="${item.getCount()} BYN">
                    </div>
                </div>
            </form>
        </li>

</c:forEach>
</ul>


<jsp:include page="../template/footer.jsp" />

</body>
</html>
