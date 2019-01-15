<%--
  Created by IntelliJ IDEA.
  User: alexander
  Date: 12/17/18
  Time: 11:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <link href="/../css/font-awesome.min.css" rel="stylesheet" integrity="sha384-T8Gy5hrqNKT+hzMclPo118YTQO6cYprQmhrYwIiQ/3axmI1hQomh7Ud2hPOy8SP1" crossorigin="anonymous">

    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <script src="../js/jquery-3.2.1.slim.min.js"></script>
    <script src="../js/popper.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>

</head>
<body>
<div id="carouselExampleSlidesOnly" class="carousel slide" data-ride="carousel">
    <div class="carousel-inner">
        <div class="carousel-item active" >
            <img class="d-block w-100" id="img1" src="../../../img/first.jpg" alt="First slide">
        </div>
        <div class="carousel-item">
            <img class="d-block w-100" id="img2" src="../../../img/second.jpg" alt="Second slide">
        </div>
        <div class="carousel-item">
            <img class="d-block w-100" id="img3" src="../../../img/three.jpg" alt="Third slide">
        </div>
    </div>
</div>


<script >
    $('.carousel slide').carousel({
        interval: 500
    })
</script>
</body>
</html>
