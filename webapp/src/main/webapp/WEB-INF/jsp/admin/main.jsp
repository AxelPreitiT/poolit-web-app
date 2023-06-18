<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title><spring:message code="admin.title"/></title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/resources/css/admin/main.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body class="background-color">
<div id="navbar-container">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
</div>
<div class="main-container-style container-color">
    <div id="main-header-row">
        <h1 class="secondary-color"><spring:message code="admin.titleView"/></h1>
        <hr class="secondary-color">
    </div>
    <c:forEach var="report" items="${reports}">
        <c:set var="report" value="${report}" scope="request"/>
        <jsp:include page="short-info-report.jsp"/>
    </c:forEach>
</div>
<c:if test="${!(empty acceptReport) && acceptReport}">
    <div id="toast-container">
        <jsp:include page="/WEB-INF/jsp/components/success-toast.jsp">
            <jsp:param name="title" value="admin.acceptReport.Title"/>
            <jsp:param name="message" value="admin.acceptReport.mnsj"/>
        </jsp:include>
    </div>
</c:if>
<c:if test="${!(empty rejectReport) && rejectReport}">
    <div id="toast-container">
        <jsp:include page="/WEB-INF/jsp/components/success-toast.jsp">
            <jsp:param name="title" value="admin.rejectReport.Title"/>
            <jsp:param name="message" value="admin.rejectReport.mnsj"/>
        </jsp:include>
    </div>
</c:if>
</body>
</html>