<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message bundle="${local}" key="local.message.success.account" var="successNewAccount"/>
<fmt:message bundle="${local}" key="local.message.success.new.card" var="successNewCard"/>
<fmt:message bundle="${local}" key="local.message.unsuccess.access" var="incorrectAccess"/>
<fmt:message bundle="${local}" key="local.message.incorrect.id.card" var="incorrectIdCard"/>
<fmt:message bundle="${local}" key="local.message.incorrect.query" var="incorrectQuery"/>
<fmt:message bundle="${local}" key="local.message.success.delete.card" var="successDeleteCard"/>
<fmt:message bundle="${local}" key="local.message.success.transfer" var="successTransfer"/>
<fmt:message bundle="${local}" key="local.message.success.delete" var="successDeleteAccount"/>
<fmt:message bundle="${local}" key="local.message.success.transfer.money" var="successTransferMoney"/>
<fmt:message bundle="${local}" key="local.message.incorrect.password" var="incorrectPassword"/>
<fmt:message bundle="${local}" key="local.message.incorrect.number" var="incorrectNumber"/>
<fmt:message bundle="${local}" key="local.message.incorrect.money" var="incorrectMoneyField"/>
<fmt:message bundle="${local}" key="local.message.success.block.card" var="successBlockCard"/>
<fmt:message bundle="${local}" key="local.message.incorrect.count.money" var="incorrectCountMoney"/>
<fmt:message bundle="${local}" key="local.message.block.account.for.card" var="incorrectBlockCardForAccount"/>
<fmt:message bundle="${local}" key="local.message.success.unblock.card" var="successUnblockCard"/>
<fmt:message bundle="${local}" key="local.message.success.block.too" var="successBlockCardToo"/>
<fmt:message bundle="${local}" key="local.message.success.block.account" var="successBlockAccountToo"/>
<fmt:message bundle="${local}" key="local.message.send.to.admin" var="sendApplicationToSupport"/>
<fmt:message bundle="${local}" key="local.message.need.empty.account" var="emptyMoney"/>
<fmt:message bundle="${local}" key="local.message.incorrect.value" var="incorrectValue"/>
<fmt:message bundle="${local}" key="local.message.success.add.rate" var="successAddRate"/>
<fmt:message bundle="${local}" key="local.message.success.edit.rate" var="successEditRate"/>
<fmt:message bundle="${local}" key="local.message.success.delete.rate" var="successDeleteRate"/>
<fmt:message bundle="${local}" key="local.message.success.edit.course" var="successEditValute"/>
<fmt:message bundle="${local}" key="local.message.success.block.admin" var="successUnblockAccount"/>
<fmt:message bundle="${local}" key="local.message.rules" var="incorrectRules"/>
<fmt:message bundle="${local}" key="local.message.sorry.block" var="incorrectSorryAccount"/>


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
            ${successNewAccount}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.successcard != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${successNewCard}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.useraccess != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${incorrectAccess}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.incorrectcard != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${incorrectIdCard}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.wrongquery != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${incorrectQuery}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.scdrop != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${successDeleteCard}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.sctran != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${successTransfer}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.successdelete != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${successDeleteAccount}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.sucmonacc != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${successTransferMoney}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.wrongPassword != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${incorrectPassword}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.wrongNumber != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${incorrectNumber}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.wrongCount != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${incorrectMoneyField}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.wrongBlock != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${successBlockCard}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.wrongcount != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${incorrectCountMoney}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.blockaccount != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${incorrectBlockCardForAccount}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.unblocksuccess != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${successUnblockCard}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.blocksuccess != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${successBlockCardToo}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.sucblock != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${successBlockAccountToo}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.sucunblock != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${sendApplicationToSupport}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.incmoney != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${emptyMoney}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.wrongName != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${incorrectValue}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.incnamerate != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${incorrectValue}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.successaddrate != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${successAddRate}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.incorrectvaluerate != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${incorrectValue}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.successeditrate != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${successEditRate}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.successdeleterate != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${successDeleteRate}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.successeditvalute != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${successEditValute}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.unblockaccount != null}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${successUnblockAccount}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.msgbox != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${incorrectRules}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.msgvalue != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${incorrectValue}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
    <c:when test="${param.block != null}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${incorrectSorryAccount}
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
    </c:when>
</c:choose>