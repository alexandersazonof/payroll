<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${sessionScope.user == null}">
        <jsp:include page="general_header.jsp"/>
    </c:when>
    <c:when test="${sessionScope.user.getRole().equals('Admin')}">
        <jsp:include page="admin_header.jsp"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="user_header.jsp"/>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${param.msg != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Новый счёт успешно создан
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.successcard != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Новая карта успешно создана
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.useraccess != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            Нет прав доступа
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.incorrectcard != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            Некорректный ид карты , пожалуйсто обратитесь к администратору
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.wrongquery != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            Некорректный запрос
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.scdrop != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Карта успешно удалена , <strong>деньги переведены на счёт</strong>
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.sctran != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Перевод прошёл успешно
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.successdelete != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Аккаунт успешно удалён
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.sucmonacc != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Деньги успешно переведены
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.wrongPassword != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            Не правильный пароль
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.wrongNumber != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            Не правильный номер
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.wrongCount != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            Не верное значение money
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.wrongBlock != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            Карта №${param.wrongBlock} заблокирована
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.wrongcount != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            Не правильное количество денег
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.blockaccount != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            Нельзя разблокировать карту , потому что заблокирован счёт
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.unblocksuccess != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Карта успешно разблокирована
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.blocksuccess != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Карта успешно зблокирована
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.sucblock != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Аккаунт и все карты заблокированы
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.sucunblock != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Заявка отправленна администратору
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.incmoney != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            На счёту не должно быть денег
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.wrongName != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            Некоректоное название
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.incnamerate != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            Некоректоное значение
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.successaddrate != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Успешно добавлен тариф
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.incorrectvaluerate != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            Некоректоное значение
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.successeditrate != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Успешно изменён тариф
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.successdeleterate != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Успешно удалён тариф
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.successeditvalute != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Курсы успешно изменены
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.unblockaccount != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            Аккаунт успешно разблокирован
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>

</c:choose>