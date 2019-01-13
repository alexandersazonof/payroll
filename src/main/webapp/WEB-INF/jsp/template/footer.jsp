<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 12/17/18
  Time: 11:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message bundle="${local}" key="local.language" var="language"/>
<fmt:message bundle="${local}" key="local.russian" var="selectRussian"/>
<fmt:message bundle="${local}" key="local.english" var="selectEnglish"/>
<fmt:message bundle="${local}" key="local.copyright" var="copyright"/>
<html>
<head>
    <!-- Bootstrap -->
    <link rel="stylesheet" href="../../../css/bootstrap.min.css" integrity="sha384-y3tfxAZXuh4HwSYylfB+J125MxIs6mR5FOHamPBG064zB+AFeWH94NdvaCBm8qnd" crossorigin="anonymous">
    <!-- font awesome -->
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-T8Gy5hrqNKT+hzMclPo118YTQO6cYprQmhrYwIiQ/3axmI1hQomh7Ud2hPOy8SP1" crossorigin="anonymous">

    <link rel="stylesheet" href="../../css/index.css">
    <link rel="stylesheet" href="../../css/bootstrap.min.css">

    <script src="../../js/jquery-3.2.1.slim.min.js"></script>
    <script src="../../js/popper.min.js"></script>
    <script src="../../js/bootstrap.min.js"></script>
</head>
<body>
<footer class="footer" >
    <div class="container">
        <div class="pull-right">
            <div class="gj-margin-top-10">
                <form class="display-inline" method="post" action="/controller">
                    <input hidden name="command" value="changeLanguage">
                    <label for="ddlLanguage">${language}:</label>
                    <select id="ddlLanguage" name="selectLanguage">
                        <option value="RU" selected="selected">${selectRussian}</option>
                        <option value="EN" >${selectEnglish}</option>
                    </select>
                    <input type="submit" value="OK">
                </form>
            </div>
        </div>

        <a href="#"><i class="fa fa-facebook-official fa-2x"></i></a>
        <a href="#"><i class="fa fa-pinterest-p fa-2x"></i></a>
        <a href="#"><i class="fa fa-twitter fa-2x"></i></a>
        <a href="#"><i class="fa fa-flickr fa-2x"></i></a>
        <a href="#"><i class="fa fa-linkedin fa-2x"></i></a>

        <div class="footer-copyright text-center py-3">© 2018 ${copyright}:
            <a href="/home"> FutureBank.com</a>
        </div>
    </div>
</footer>

<style type="text/css">
    .footer {
        position: fixed;
        left: 0;
        bottom: 0;
        width: 100%;
    }
</style>
</body>
</html>
