<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="trip" type="ar.edu.itba.paw.models.trips.Trip" scope="request"/>
<link href="<c:url value="/resources/css/users/travel-info.css"/>" rel="stylesheet" type="text/css"/>

<div>
    <c:url value="/trips/${trip.tripId}" var="tripUrl">
        <c:param name="startDate" value="${trip.queryStartDateString}"/>
        <c:param name="startTime" value="${trip.startTimeString}"/>
        <c:param name="endDate" value="${trip.queryEndDateString}"/>
    </c:url>
    <a href="${tripUrl}">
        <div class="card-info">
            <div class="data-container">
                <div class="route-container">
                    <i class="secondary-color bi bi-geo-alt icon-style"></i>
                    <div class="secondary-color horizontal_dotted_line"></div>
                    <i class="secondary-color bi bi-geo-alt-fill icon-style"></i>
                </div>
                <div class="address-container">
                    <div class="route-info-text">
                        <span class="secondary-color h3"><c:out value="${trip.originCity.name}"/></span>
                        <span class="text"><c:out value="${trip.originAddress}"/></span>
                    </div>
                    <div class="route-info-text">
                        <span class="secondary-color h3 align-right"><c:out value="${trip.destinationCity.name}"/></span>
                        <span class="text align-right"><c:out value="${trip.destinationAddress}"/></span>
                    </div>
                </div>
                <div class="extra-info-container">
                    <div class="line-container">
                        <i class="bi bi-calendar text"></i>
                        <c:choose>
                            <c:when test="${trip.queryIsRecurrent}">
                                <div class="format_date">
                                    <span class="text"><spring:message code="${trip.dayOfWeekString}"/></span>
                                    <span class="italic-text date-text"><spring:message code="profile.trevelInfo.dateFormat" arguments="${trip.startDateString}, ${trip.endDateString}"/></span>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="format_date">
                                    <span class="text"><spring:message code="${trip.dayOfWeekString}"/></span>
                                    <span class="italic-text date-text"><c:out value="${trip.startDateString}"/></span>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div>
                        <i class="bi bi-clock text"></i>
                        <span class="text"><c:out value="${trip.startTimeString}"/></span>
                    </div>
                    <div>
                        <h2 class="secondary-color"><spring:message code="format.price" arguments="${trip.integerQueryTotalPrice},${trip.decimalQueryTotalPrice}"/></h2>
                    </div>
                </div>
            </div>
            <div class="img-container">
                <c:url value="/image/${trip.car.image_id}" var="carImageUrl"/>
                <img class="car-container" src="${carImageUrl}" alt="<spring:message code="createCar.carImage"/>"/>
            </div>
        </div>
    </a>
</div>