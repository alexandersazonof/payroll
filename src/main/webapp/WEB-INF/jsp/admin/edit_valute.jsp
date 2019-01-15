<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 1/15/19
  Time: 3:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="../template/header.jsp"/>

<div class="container">
    <div class="text-center">
        <h2>From ${fromValute} to ${toValute}</h2>
        <hr>
        <form action="/controller" method="post" id="myform">
            <input type="hidden" name="id" value="${exachangeRate.getId()}">
            <input type="hidden" name="command" value="EDITVALUTE">
            <div class="form-group row">
                <label for="course" class="col-sm-2 col-form-label">Course</label>
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
        var msg = 'Изменить валюту ?';
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
