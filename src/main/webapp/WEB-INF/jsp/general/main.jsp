<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<fmt:setLocale value="${selectedLanguage}" />
<fmt:setBundle basename="properties.content" var="local" />

<fmt:message bundle="${local}" key="local.siteName" var="siteName"/>

<head>
    <title>${siteName}</title>

</head>
<body>

<jsp:include page="../template/header.jsp"/>
<jsp:include page="../template/general_content.jsp" />

<jsp:include page="../template/footer.jsp" />
</body>



