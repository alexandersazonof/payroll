<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 1/12/19
  Time: 3:28 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="../template/user_header.jsp" %>
<h1>Hello</h1>
${cardList.size()}

<jsp:include page="../template/footer.jsp" />
</body>
</html>
