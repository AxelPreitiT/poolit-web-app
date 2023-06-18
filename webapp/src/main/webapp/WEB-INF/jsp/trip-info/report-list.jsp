<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" href="<c:url value="/resources/css/trip-info/report-list.css"/>" type="text/css">

<jsp:useBean id="tripReportCollection" scope="request" type="ar.edu.itba.paw.models.reports.TripReportCollection" />
<jsp:useBean id="trip" scope="request" type="ar.edu.itba.paw.models.trips.Trip" />

<div id="report-list-container">
    <c:if test="${tripReportCollection.canReportDriver}">
        <div class="list-header">
            <i class="bi bi-person-fill text h3"></i>
            <h3 class="text"><spring:message code="tripDetails.driver"/></h3>
        </div>
        <div class="list-content">
            <c:set var="driverReportItem" value="${tripReportCollection.driver}"/>
            <c:set var="driver" value="${driverReportItem.item}"/>
            <c:set var="reportState" value="${driverReportItem.state}"/>
            <div class="list-item">
                <c:url value="/image/${driver.userImageId}" var="driverImageUrl"/>
                <button
                        class="btn shadow-btn button-style <c:choose><c:when test="${driverReportItem.available}">button-color</c:when><c:when test="${driverReportItem.inRevision}">primary-button</c:when><c:when test="${driverReportItem.approved}">success-bg-color</c:when><c:when test="${driverReportItem.rejected}">placeholder-image-color</c:when><c:when test="${driverReportItem.disabled}">danger-bg-color</c:when></c:choose>"
                        data-bs-target="#report-driver"
                        data-bs-toggle="modal"
                        <c:if test="${!driverReportItem.available}">
                        disabled
                        </c:if>
                >
                    <img src="${driverImageUrl}" alt="driver image" class="image driver-image">
                    <div class="list-item-text">
                        <spring:message code="user.nameFormat" var="driverName" arguments="${driver.name}, ${driver.surname}"/>
                        <span class="light-text h4"><c:out value="${driverName}"/></span>
                    </div>
                </button>
                <c:if test="${!driverReportItem.available}">
                    <p class="italic-text disabled-text">
                        <spring:message code="report.state.${reportState}"/>
                    </p>
                </c:if>
            </div>
        </div>
    </c:if>
    <c:if test="${tripReportCollection.canReportPassengers}">
        <div class="list-header">
            <i class="bi bi-people-fill text h3"></i>
            <h3 class="text"><spring:message code="tripDetails.passengers"/></h3>
        </div>
        <div class="list-content">
            <c:forEach items="${tripReportCollection.passengers}" var="passengerReportItem">
                <c:set var="passenger" value="${passengerReportItem.item}"/>
                <c:set var="reportState" value="${passengerReportItem.state}"/>
                <div class="list-item">
                    <c:url value="/image/${passenger.userImageId}" var="passengerImageUrl"/>
                    <button
                            class="btn shadow-btn button-style <c:choose><c:when test="${driverReportItem.available}">button-color</c:when><c:when test="${driverReportItem.inRevision}">primary-button</c:when><c:when test="${driverReportItem.approved}">success-bg-color</c:when><c:when test="${driverReportItem.rejected}">placeholder-image-color</c:when><c:when test="${driverReportItem.disabled}">danger-bg-color</c:when></c:choose>"
                            data-bs-target="#report-passenger-${passenger.userId}"
                            data-bs-toggle="modal"
                            <c:if test="${!passengerReportItem.available}">
                                disabled
                            </c:if>
                    >
                        <img src="${passengerImageUrl}" alt="passenger image" class="image passenger-image">
                        <div class="list-item-text">
                            <spring:message code="user.nameFormat" var="passengerName" arguments="${passenger.name}, ${passenger.surname}"/>
                            <span class="light-text h4"><c:out value="${passengerName}"/></span>
                            <c:choose>
                                <c:when test="${passenger.recurrent}">
                                    <div class="passenger-dates-container">
                                        <i class="bi bi-calendar light-text"></i>
                                        <span class="light-text">
                                          <spring:message code="format.dates" var="passengerDate" arguments="${passenger.startDateString}, ${passenger.endDateString}"/>
                                          <c:out value="${passengerDate}"/>
                                        </span>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="passenger-dates-container">
                                        <i class="bi bi-calendar light-text"></i>
                                        <span class="light-text"><c:out value="${passenger.startDateString}"/></span>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </button>
                    <c:if test="${!passengerReportItem.available}">
                        <p class="italic-text disabled-text">
                            <spring:message code="report.state.${reportState}"/>
                        </p>
                    </c:if>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>
