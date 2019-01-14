<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 1/14/19
  Time: 10:17 PM
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
        <div class="form-group row">
            <div class="col-sm-8">
                <h2>Rates</h2>
            </div>
            <div class="col-sm-1">
                <img src="../img/new.png" width="30" height="30" href="/controller?command=newratepage"/>
            </div>
            <hr>
        </div>

        <c:forEach var="item" items="${rateList}">
            <form action="/controller" method="post" id="myform">
                <input type="hidden" name="command" value="EDITRATEPAGE">
            <div class="form-group row">
                <label for="name" class="col-sm-7 col-form-label">Name: </label>
                <div class="col-sm-4">
                    <input type="text" readonly class="form-control-plaintext" id="name" value="${item.getName()}" name="name">
                </div>
            </div>
            <div class="form-group row">
                <label for="description" class="col-sm-7 col-form-label">Description: </label>
                <div class="col-sm-4">
                    <input type="text" readonly id="field1" class="form-control-plaintext" id="description" name="description" value="${item.getDescription()}">
                </div>
                <div class="col-sm-1">
                    <img src="../img/edit.svg" width="30" height="30" class="submitableimage">
                </div>
            </div>
            <hr>
            </form>
        </c:forEach>
    </div>
</div>
<script>
    $('.submitableimage').click(function(){
        $('#myform').submit();
    });
</script>

<jsp:include page="../template/footer.jsp"/>
</body>
</html>
