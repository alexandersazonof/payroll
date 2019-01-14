<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 12/8/18
  Time: 10:36 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message bundle="${local}" key="local.showcard.name" var="cardName"/>
<fmt:message bundle="${local}" key="local.showcard.number" var="cardNumber"/>
<fmt:message bundle="${local}" key="local.newcard.add" var="addButton"/>
<fmt:message bundle="${local}" key="local.error.name" var="errorName"/>
<fmt:message bundle="${local}" key="local.error.number" var="errorNumber"/>
<fmt:message bundle="${local}" key="local.newcard.title" var="title"/>



<html>
<head>
    <title>${title}</title>

</head>
<body>


<jsp:include page="../template/header.jsp"/>

<form action="/controller" method="post" name="createForm" id="createForm" class="createForm">
    <input type="hidden" name="command" value="newaccount" />
    <div class="text-center">
        <div class="container">
            <h1>Create new account</h1>
            <hr>
            <div class="form-group row">
                <label for="exampleName" class="col-sm-2 col-form-label">${cardName} :</label>
                <div class="col-sm-6">
                    <input type="text" class="form-control" id="exampleName" name="Name"  value="${param.name}">
                </div>
            </div>

        <div class="form-group row">
            <label for="exampleFormControlSelect1" class="col-sm-2 col-form-label">Valute :</label>
                <div class="col-sm-6">
                    <select class="form-control" id="exampleFormControlSelect1" name="valute">
                        <c:forEach items="${valuteList}" var = "item">
                            <option>${item.getName()}</option>
                        </c:forEach>
                    </select>
                </div>
        </div>
        <button type="submit" id="myButton" class="btn btn-dark">${addButton}</button>
        </div>
    </div>
</form>

<script src="../js/bootbox.min.js"></script>
<script type="text/javascript">
    $('#myButton').click(function(e) {
        e.preventDefault();
        var msg = 'Создать новый счёт ?';
        bootbox.confirm(msg, function(result) {
            if (result) {
                $('#createForm').submit();
            }
        });
    });
</script>



<jsp:include page="../template/footer.jsp" />
</body>
</html>
