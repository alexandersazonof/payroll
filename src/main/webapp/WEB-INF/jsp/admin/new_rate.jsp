<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 1/15/19
  Time: 12:16 AM
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
        <div class="form-group">
            <h2>New rate</h2>
            <hr>
        </div>
        <div class="form-group">
            <form action="/controller" method="post" id="myform">
                <input type="hidden" name="command" value="ADDRATE">
                <div class="form-group row">
                    <label for="name" class="col-sm-4 col-form-label">Name: </label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="name"  name="name">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="description" class="col-sm-4 col-form-label">Description: </label>
                    <div class="col-sm-4">
                        <input type="text" id="field1" class="form-control" id="description" name="description">
                    </div>
                </div>
                <img src="../img/save.png" width="30" height="30" id="save"/>
            </form>
        </div>
    </div>
</div>

<script src="../js/bootbox.min.js"></script>
<script type="text/javascript">
    $('#save').click(function(e) {
        e.preventDefault();
        var msg = 'Добавить тариф ?';
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
