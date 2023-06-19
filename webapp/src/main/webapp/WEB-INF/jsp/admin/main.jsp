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
    <c:forEach var="report" items="${reports.elements}">
        <c:set var="report" value="${report}" scope="request"/>
        <c:if test="${report.relation eq 'DRIVER_2_PASSENGER'}">
            <jsp:include page="short-info-report.jsp">
                <jsp:param name="reporterStars" value="${report.reporter.passengerRating}"/>
                <jsp:param name="reporterRole" value="report.role.driver"/>
                <jsp:param name="reportedStars" value="${report.reported.passengerRating}"/>
                <jsp:param name="reportedRole" value="report.role.passenger"/>
            </jsp:include>
        </c:if>
        <c:if test="${report.relation eq 'PASSENGER_2_DRIVER'}">
            <jsp:include page="short-info-report.jsp">
                <jsp:param name="reporterStars" value="${report.reporter.passengerRating}"/>
                <jsp:param name="reporterRole" value="report.role.passenger"/>
                <jsp:param name="reportedStars" value="${report.reported.passengerRating}"/>
                <jsp:param name="reportedRole" value="report.role.driver"/>
            </jsp:include>
        </c:if>
        <c:if test="${report.relation eq 'PASSENGER_2_PASSENGER'}">
            <jsp:include page="short-info-report.jsp">
                <jsp:param name="reporterStars" value="${report.reporter.passengerRating}"/>
                <jsp:param name="reporterRole" value="report.role.passenger"/>
                <jsp:param name="reportedStars" value="${report.reported.passengerRating}"/>
                <jsp:param name="reportedRole" value="report.role.passenger"/>
            </jsp:include>
        </c:if>
    </c:forEach>
    <c:if test="${reports.moreThanOnePage}">
        <c:url value="" var="baseUrl">
            <c:forEach var="p" items="${param}">
                <c:if test="${!(p.key eq 'page')}">
                    <c:param name="${p.key}" value="${p.value}"/>
                </c:if>
            </c:forEach>
        </c:url>
        <jsp:include page="/WEB-INF/jsp/components/pagination.jsp">
            <jsp:param name="currentPage" value="${reports.currentPage+1}"/>
            <jsp:param name="totalPages" value="${reports.totalPages}"/>
            <jsp:param name="baseUrl" value="${baseUrl}"/>
        </jsp:include>
    </c:if>
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