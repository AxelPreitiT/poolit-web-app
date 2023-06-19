<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title><spring:message code="admin.title"/></title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/resources/css/admin/details.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body class="background-color">
    <div id="navbar-container">
        <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    </div>
    <div class="main-container-style container-color">
        <div id="main-header-row">
            <h1 class="secondary-color"><spring:message code="admin.details.title"/></h1>
            <hr class="secondary-color">
        </div>
        <div id="report-content-container">
            <div id="report-users-content-container">
                <c:set value="${report.reporter}" var="reporter"/>
                <spring:message code="user.nameFormat" arguments="${reporter.name}, ${reporter.surname}" var="reporterName"/>
                <c:set value="${report.reported}" var="reported"/>
                <spring:message code="user.nameFormat" arguments="${reported.name}, ${reported.surname}" var="reportedName"/>
                <div id="users-image-container">
                    <div class="user-container">
                        <c:url value="/image/${reporter.userImageId}" var="reporterImageUrl"/>
                        <img src="${reporterImageUrl}" class="user-image" alt="${reporterName}">
                    </div>
                    <div id="report-arrow-container">
                        <div id="report-arrow-content">
                            <div id="report-arrow-text">
                                <i class="bi bi-megaphone-fill secondary-color h3"></i>
                                <h3 class="secondary-color">Reportó a</h3>
                            </div>
                            <div id="report-arrow-draw">
                                <div id="report-arrow-line"></div>
                                <div id="report-arrow-head"></div>
                            </div>
                        </div>
                    </div>
                    <div class="user-container">
                        <c:url value="/image/${reported.userImageId}" var="reportedImageUrl"/>
                        <img src="${reportedImageUrl}" class="user-image" alt="${reportedName}">
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
