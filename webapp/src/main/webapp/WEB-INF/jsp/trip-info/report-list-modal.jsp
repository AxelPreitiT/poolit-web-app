<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" href="<c:url value="/resources/css/trip-info/report-list-modal.css"/>" type="text/css">

<jsp:useBean id="tripReportCollection" scope="request" type="ar.edu.itba.paw.models.reports.TripReportCollection" />
<jsp:useBean id="trip" scope="request" type="ar.edu.itba.paw.models.trips.Trip" />
<jsp:useBean id="reportForm" scope="request" type="ar.edu.itba.paw.webapp.form.ReportForm" />

<div id="report-list-modal-container">
    <span class="h6 italic-text"><spring:message code="report.didYouHaveAnyProblem"/></span>
    <a data-bs-target="#report-list-modal" data-bs-toggle="modal">
        <i class="bi bi-megaphone-fill primary-color h6"></i>
        <span class="h6 primary-color"><spring:message code="report.report"/></span>
    </a>
    <div class="modal fade <c:if test="${!(empty param.reported) && param.reported}">show-on-load</c:if>" id="report-list-modal">
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
            <div class="modal-content">
                <div class="modal-header">
                    <h2 class="modal-title primary-color"><spring:message code="report.modalTitle"/></h2>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div id="report-published-container" class="collapse <c:if test="${!(empty param.reported) && param.reported}">show</c:if>">
                        <i class="bi bi-check-circle-fill success h6"></i>
                        <span class="success h6" id="published-text"><spring:message code="review.toast.message"/></span>
                    </div>
                    <jsp:include page="/WEB-INF/jsp/trip-info/report-list.jsp"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn shadow-btn button-style primary-button" data-bs-dismiss="modal">
                        <span class="light-text"><spring:message code="tripCard.btn.back"/></span>
                    </button>
                </div>
            </div>
        </div>
    </div>
    <div id="report-form-modals">
        <c:if test="${tripReportCollection.canReportDriver}">
            <div id="driver-report-form-modal">
                <c:set var="driverReportItem" value="${tripReportCollection.driver}"/>
                <c:set var="driver" value="${driverReportItem.item}" scope="request"/>
                <div class="modal fade" id="report-driver">
                    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                        <c:url value="/reports/trips/${trip.tripId}/drivers/${driver.userId}" var="driverReportUrl"/>
                        <form:form modelAttribute="reportForm" method="post" action="${driverReportUrl}">
                            <div class="modal-content">
                                <div class="modal-body">
                                    <c:set scope="request" var="reportItem" value="${driverReportItem}"/>
                                    <c:set scope="request" var="userReported" value="${driver}"/>
                                    <jsp:include page="/WEB-INF/jsp/trip-info/report-form.jsp"/>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn shadow-btn button-style primary-button" data-bs-target="#report-list-modal" data-bs-toggle="modal">
                                        <span class="light-text"><spring:message code="tripCard.btn.back"/></span>
                                    </button>
                                    <button type="submit" class="btn shadow-btn button-style button-color">
                                        <span class="light-text"><spring:message code="review.submit"/></span>
                                    </button>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${tripReportCollection.canReportPassengers}">
            <div id="passenger-report-form-modals">
                <c:forEach items="${tripReportCollection.passengers}" var="passengerReportItem">
                    <c:set var="passenger" value="${passengerReportItem.item}"/>
                    <div class="modal fade" id="report-passenger-${passenger.userId}">
                        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                            <c:url value="/reports/trips/${trip.tripId}/passengers/${passenger.userId}" var="passengerReportUrl"/>
                            <form:form modelAttribute="reportForm" method="post" action="${passengerReportUrl}">
                                <div class="modal-content">
                                    <div class="modal-body">
                                        <c:set var="userReported" value="${passenger.user}" scope="request"/>
                                        <c:set scope="request" var="reportItem" value="${passengerReportItem}"/>
                                        <jsp:include page="/WEB-INF/jsp/trip-info/report-form.jsp"/>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn shadow-btn button-style primary-button" data-bs-target="#report-list-modal" data-bs-toggle="modal">
                                            <span class="light-text"><spring:message code="tripCard.btn.back"/></span>
                                        </button>
                                        <button type="submit" class="btn shadow-btn button-style button-color">
                                            <span class="light-text"><spring:message code="review.submit"/></span>
                                        </button>
                                    </div>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>
    <script src="<c:url value="/resources/js/pages/trip-info/report-list-modal.js"/>"></script>
</div>