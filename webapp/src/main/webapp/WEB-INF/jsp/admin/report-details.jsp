<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title><spring:message code="admin.title"/></title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/resources/css/admin/report-details.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body class="background-color">
    <div id="navbar-container">
        <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    </div>
    <div class="main-container-style container-color">
        <div id="main-header-row">
            <h1 class="secondary-color"><spring:message code="admin.report.details.title"/></h1>
            <hr class="secondary-color">
        </div>
        <div id="report-content-container">
            <div id="report-users-content-container">
                <c:set value="${report.reporter}" var="reporter"/>
                <spring:message code="user.nameFormat" arguments="${reporter.name}, ${reporter.surname}" var="reporterName"/>
                <c:url value="/profile/${reporter.userId}" var="reporterProfileUrl"/>
                <c:set value="${report.reported}" var="reported"/>
                <spring:message code="user.nameFormat" arguments="${reported.name}, ${reported.surname}" var="reportedName"/>
                <c:url value="/profile/${reported.userId}" var="reportedProfileUrl"/>
                <div id="users-image-container" class="users-container">
                    <div class="user-container">
                        <c:url value="/image/${reporter.userImageId}" var="reporterImageUrl"/>
                        <a href="${reporterProfileUrl}">
                            <img src="${reporterImageUrl}" class="user-image" alt="${reporterName}">
                        </a>
                    </div>
                    <div id="report-arrow-container">
                        <div id="report-arrow-content">
                            <div id="report-arrow-text">
                                <i class="bi bi-megaphone-fill secondary-color h3"></i>
                                <h3 class="secondary-color"><spring:message code="admin.report.details.reported"/></h3>
                            </div>
                            <div id="report-arrow-draw">
                                <div id="report-arrow-line"></div>
                                <div id="report-arrow-head"></div>
                            </div>
                        </div>
                    </div>
                    <div class="user-container">
                        <c:url value="/image/${reported.userImageId}" var="reportedImageUrl"/>
                        <a href="${reportedProfileUrl}">
                            <img src="${reportedImageUrl}" class="user-image" alt="${reportedName}">
                        </a>
                    </div>
                </div>
                <div id="users-content-container" class="users-container">
                    <div class="user-container">
                        <div class="user-container-item">
                            <a href="${reporterProfileUrl}">
                                <h3 class="secondary-color">${reporterName}</h3>
                            </a>
                            <h6 class="italic-text"><spring:message code="${report.relation.reporterSpringMessageCode}"/></h6>
                        </div>
                        <c:if test="${report.relation.reporterADriver}">
                            <div class="user-container-item">
                                <strong><spring:message code="report.role.driver.rating"/></strong>
                                <c:set value="${report.reporter.driverRating}" var="rating" scope="request"/>
                                <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp">
                                    <jsp:param name="fontSize" value="h4"/>
                                    <jsp:param name="fontColor" value="secondary-color"/>
                                </jsp:include>
                            </div>
                        </c:if>
                        <div class="user-container-item">
                            <strong><spring:message code="report.role.passenger.rating"/></strong>
                            <c:set value="${report.reporter.passengerRating}" var="rating" scope="request"/>
                            <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp">
                                <jsp:param name="fontSize" value="h4"/>
                                <jsp:param name="fontColor" value="secondary-color"/>
                            </jsp:include>
                        </div>
                        <div class="user-container-item user-container-row">
                            <strong><spring:message code="report.reportsPublished" arguments=":"/></strong>
                            <strong class="secondary-color">${report.reporter.reportsPublished}</strong>
                        </div>
                        <div class="user-container-item user-container-row">
                            <strong><spring:message code="report.reportsReceived" arguments=":"/></strong>
                            <strong class="secondary-color">${report.reporter.reportsReceived}</strong>
                        </div>
                        <div class="user-container-item user-container-row">
                            <strong><spring:message code="report.reportsApproved" arguments=":"/></strong>
                            <strong class="secondary-color">${report.reporter.reportsApproved}</strong>
                        </div>
                        <div class="user-container-item user-container-row">
                            <strong><spring:message code="report.reportsRejected" arguments=":"/></strong>
                            <strong class="secondary-color">${report.reporter.reportsRejected}</strong>
                        </div>
                    </div>
                    <div class="user-container">
                        <div class="user-container-item">
                            <a href="${reportedProfileUrl}">
                                <h3 class="secondary-color">${reportedName}</h3>
                            </a>
                            <h6 class="italic-text"><spring:message code="${report.relation.reportedSpringMessageCode}"/></h6>
                        </div>
                        <c:if test="${report.relation.reportedADriver}">
                            <div class="user-container-item">
                                <strong><spring:message code="report.role.driver.rating"/></strong>
                                <c:set value="${report.reported.driverRating}" var="rating" scope="request"/>
                                <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp">
                                    <jsp:param name="fontSize" value="h4"/>
                                    <jsp:param name="fontColor" value="secondary-color"/>
                                </jsp:include>
                            </div>
                        </c:if>
                        <div class="user-container-item">
                            <strong><spring:message code="report.role.passenger.rating"/></strong>
                            <c:set value="${report.reported.passengerRating}" var="rating"/>
                            <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp">
                                <jsp:param name="fontSize" value="h4"/>
                                <jsp:param name="fontColor" value="secondary-color"/>
                            </jsp:include>
                        </div>
                        <div class="user-container-item user-container-row">
                            <strong><spring:message code="report.reportsPublished" arguments=":"/></strong>
                            <strong class="secondary-color">${report.reported.reportsPublished}</strong>
                        </div>
                        <div class="user-container-item user-container-row">
                            <strong><spring:message code="report.reportsReceived" arguments=":"/></strong>
                            <strong class="secondary-color">${report.reported.reportsReceived}</strong>
                        </div>
                        <div class="user-container-item user-container-row">
                            <strong><spring:message code="report.reportsApproved" arguments=":"/></strong>
                            <strong class="secondary-color">${report.reported.reportsApproved}</strong>
                        </div>
                        <div class="user-container-item user-container-row">
                            <strong><spring:message code="report.reportsRejected" arguments=":"/></strong>
                            <strong class="secondary-color">${report.reported.reportsRejected}</strong>
                        </div>
                    </div>
                </div>
            </div>
            <div class="report-content-container">
                <div class="content-container-header">
                    <h3 class="secondary-color"><spring:message code="admin.report.details.trip"/></h3>
                    <hr class="secondary-color"/>
                </div>
                <div class="content-container-info">
                    <c:set var="trip" value="${report.trip}" scope="request"/>
                    <jsp:include page="/WEB-INF/jsp/users/travel-info.jsp"/>
                </div>
            </div>
            <div class="report-content-container">
                <div class="content-container-header">
                    <h3 class="secondary-color"><spring:message code="admin.report.details.reason"/></h3>
                    <hr class="secondary-color"/>
                </div>
                <div class="content-container-info" id="report-content-container-info">
                    <div id="reporter-image">
                        <img src="${reporterImageUrl}" alt="${reporterName}"/>
                        <span class="italic-text"><c:out value="${reporter.name}"/></span>
                    </div>
                    <div id="report-content" class="bubble">
                        <div id="report-info">
                            <h4 class="secondary-color"><spring:message code="${report.reason.springMessageCode}"/></h4>
                            <span class="text"><c:out value="${report.description}"/></span>
                        </div>
                        <div id="report-date">
                            <span class="italic-text"><c:out value="${report.dateTimeString}"/></span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="report-content-container">
                <div id="decision-content-container">
                    <h5 class="secondary-color"><spring:message code="admin.report.details.decision"/></h5>
                    <div id="button-container">
                        <a href="<c:url value="/admin"/>">
                            <button class="primary-button btn button-style shadow-btn">
                                <span class="light-text h5"><spring:message code="admin.report.details.later"/></span>
                            </button>
                        </a>
                        <div id="reject-container">
                            <button class="danger-button btn button-style shadow-btn" data-bs-toggle="modal" data-bs-target="#reject-modal">
                                <span class="light-text h5"><spring:message code="admin.report.details.reject"/></span>
                            </button>
                            <div class="modal fade <c:if test="${rejectReportFailed}">show-on-load</c:if>" id="reject-modal">
                                <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h4 class="danger"><spring:message code="admin.report.details.reject.title"/></h4>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <c:url value="/admin/reports/${report.reportId}/reject" var="rejectReportUrl"/>
                                        <form:form modelAttribute="reportAdminForm" method="post" action="${rejectReportUrl}">
                                            <div class="modal-body">
                                                <label for="reject-reason" class="form-label">
                                                    <strong><spring:message code="admin.report.details.reject.label.title"/></strong>
                                                    <span class="italic-text"><spring:message code="admin.report.details.approve.label.message"/></span>
                                                </label>
                                                <form:textarea path="reason" cssClass="form-control" id="reject-reason"/>
                                                <div class="error-item">
                                                    <i class="bi bi-exclamation-circle-fill danger"></i>
                                                    <form:errors path="reason" cssClass="danger error-style" element="span"/>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn primary-button button-style shadow-btn" data-bs-dismiss="modal">
                                                    <span class="light-text"><spring:message code="tripCard.btn.back"/></span>
                                                </button>
                                                <button type="submit" class="btn danger-button button-style shadow-btn">
                                                    <span class="light-text"><spring:message code="sendToken.btn"/></span>
                                                </button>
                                            </div>
                                        </form:form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="approve-container">
                            <button class="button-color btn button-style shadow-btn" data-bs-toggle="modal" data-bs-target="#approve-modal">
                                <span class="light-text h5"><spring:message code="admin.report.details.approve"/></span>
                            </button>
                            <div class="modal fade <c:if test="${acceptReportFailed}">show-on-load</c:if>" id="approve-modal">
                                <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <h4 class="secondary-color"><spring:message code="admin.report.details.approve.title"/></h4>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                        </div>
                                        <c:url value="/admin/reports/${report.reportId}/accept" var="acceptReportUrl"/>
                                        <form:form modelAttribute="reportAdminForm" method="post" action="${acceptReportUrl}">
                                            <div class="modal-body">
                                                <label for="reject-reason" class="form-label">
                                                    <strong><spring:message code="admin.report.details.approve.label.title"/></strong>
                                                    <span class="italic-text"><spring:message code="admin.report.details.approve.label.message"/></span>
                                                </label>
                                                <form:textarea path="reason" cssClass="form-control" id="reject-reason"/>
                                                <div class="error-item">
                                                    <i class="bi bi-exclamation-circle-fill danger"></i>
                                                    <form:errors path="reason" cssClass="danger error-style" element="span"/>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn primary-button button-style shadow-btn" data-bs-dismiss="modal">
                                                    <span class="light-text"><spring:message code="tripCard.btn.back"/></span>
                                                </button>
                                                <button type="submit" class="btn button-color button-style shadow-btn">
                                                    <span class="light-text"><spring:message code="sendToken.btn"/></span>
                                                </button>
                                            </div>
                                        </form:form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="<c:url value="/resources/js/admin/report-details.js"/>" type="text/javascript"></script>
</body>
</html>
