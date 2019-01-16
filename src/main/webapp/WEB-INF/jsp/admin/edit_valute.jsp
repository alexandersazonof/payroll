<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 1/15/19
  Time: 3:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message bundle="${local}" key="local.editvalute.title" var="title"/>
<fmt:message bundle="${local}" key="local.editvalute.from" var="localForm"/>
<fmt:message bundle="${local}" key="local.editvalute.to" var="localTo"/>
<fmt:message bundle="${local}" key="local.editvalute.course" var="localCourse"/>
<fmt:message bundle="${local}" key="local.editvalute.ask" var="localAsk"/>

<html>
<head>
    <title>${title}</title>
</head>
<body>
<jsp:include page="../template/header.jsp"/>

<div class="container">
    <div class="text-center">
        <h2>${localForm} ${fromValute} ${localTo} ${toValute}</h2>
        <hr>
        <form action="/controller" method="post" id="myform">
            <input type="hidden" name="id" value="${exachangeRate.getId()}">
            <input type="hidden" name="command" value="EDITVALUTE">
            <div class="form-group row">
                <label for="course" class="col-sm-2 col-form-label">${localCourse}</label>
                <div class="col-sm-4">
                    <input type="text" class="form-control" id="course" name="course" value="${exachangeRate.getCourse()}">
                </div>
            </div>
            <a href="#" id="save"><img src="../img/save.png" width="30" height="30"></a>
        </form>
    </div>

</div>

<script src="../js/bootbox.min.js"></script>
<script type="text/javascript">
    $('#save').click(function(e) {
        e.preventDefault();
        var msg = '${localAsk}';
        bootbox.confirm(msg, function(result) {
            if (result) {
                $('#myform').submit();
            }
        });
    });
</script>

<jsp:include page="../template/footer.jsp"/>
</body>
</html>
