
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="/../css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/jquery-3.2.1.slim.min.js"></script>
    <script src="../js/popper.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/controller?command=mainPage">
        <img src="../img/logo.png" width="30" height="30" class="d-inline-block align-top" alt="">
        Future bank
    </a>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/controller?command=VALUTEPAGE">Valutes</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/controller?command=RATEPAGE">Rates</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/controller?command=HISTORYTRANSFER">Operation</a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/controller?command=APPLICATION">
                    Application<span class="badge badge-danger">${sessionScope.applicationSize}</span>
                </a>
            </li>
        </ul>
    </div>
    <span class="navbar-text">
      <a class="nav-link" href="/controller?command=Logout">Logout</a>
    </span>
</nav>
</body>
</html>
