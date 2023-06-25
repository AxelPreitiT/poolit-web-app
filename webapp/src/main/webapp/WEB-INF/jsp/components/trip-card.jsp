<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<link href="<c:url value="/resources/css/components/trip-card.css"/>" rel="stylesheet" type="text/css"/>

<!-- Params:
        - tripUrl: url to join the trip
-->

<!-- Beans:
        - trip: Info of the trip
-->

<jsp:useBean id="trip" type="ar.edu.itba.paw.models.trips.Trip" scope="request"/>

<div class="card-container">
    <div class="card">
        <c:url value="/trips/${trip.tripId}" var="tripUrl">
            <c:param name="startDate" value="${trip.queryStartDateString}"/>
            <c:param name="startTime" value="${trip.startTimeString}"/>
            <c:param name="endDate" value="${trip.queryEndDateString}"/>
        </c:url>
        <a href="${tripUrl}">
            <div class="row g-0">
                <c:url value="/image/${trip.car.imageId}" var="carImageUrl"/>
                <div class="col-5">
                    <div id="car-image">
                        <img src="${carImageUrl}" alt="car image"/>
                    </div>
                    <div id="rating-container" class="secondary-bg-color">
                        <div class="rating-row" id="car-rating">
                            <i class="bi bi-car-front-fill light-text h6" id="car-rating-icon"></i>
                            <c:set var="rating" value="${trip.carRating}" scope="request"/>
                            <jsp:include page="rating-stars.jsp">
                                <jsp:param name="fontSize" value="h6"/>
                                <jsp:param name="fontColor" value="light-text"/>
                            </jsp:include>
                        </div>
                        <div class="rating-row" id="driver-rating">
                            <c:url value="/image/${trip.driver.userImageId}" var="driverImageUrl"/>
                            <img src="${driverImageUrl}" alt="user image" class="driver-image-photo"/>
                            <c:set var="rating" value="${trip.driverRating}" scope="request"/>
                            <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp">
                                <jsp:param name="fontSize" value="h6"/>
                                <jsp:param name="fontColor" value="light-text"/>
                            </jsp:include>
                        </div>
                    </div>
                </div>
                <div class="col-7">
                    <div class="card-body">
                        <div class="route-info">
                            <div class="route-info-row">
                                <i class="bi bi-geo-alt secondary-color route-info-icon h4"></i>
                                <div class="route-info-text">
                                    <span class="secondary-color h4"><c:out value="${trip.originCity.name}"/></span>
                                    <span class="text"><c:out value="${trip.originAddress}"/></span>
                                </div>
                            </div>
                            <div class="vertical-dotted-line"></div>
                            <div class="route-info-row">
                                <i class="bi bi-geo-alt-fill secondary-color route-info-icon h4"></i>
                                <div class="route-info-text">
                                    <span class="secondary-color h4"><c:out value="${trip.destinationCity.name}"/></span>
                                    <span class="text"><c:out value="${trip.destinationAddress}"/></span>
                                </div>
                            </div>
                        </div>
                        <div class="footer-info">
                            <div id="footer-date-container" class="footer-container">
                                <i class="bi bi-calendar text"></i>
                                <div class="show-row-content">
                                    <c:choose>
                                        <c:when test="${trip.queryIsRecurrent}">
                                            <span class="text text-capitalize"><spring:message code="${trip.dayOfWeekString}"/></span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text"><c:out value="${trip.queryStartDateString}"/></span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div id="footer-time-container" class="footer-container">
                                <i class="bi bi-clock text"></i>
                                <div class="show-row-content">
                                    <span class="text"><c:out value="${trip.startTimeString}"/></span>
                                </div>
                            </div>
                            <div id="footer-price-container" class="footer-container">
                                <h3 class="secondary-color">$<c:out value="${trip.queryTotalPrice}"/></h3>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </a>
    </div>
    <c:if test="${!(empty allowReview) && allowReview}">
        <div class="review-trip-container">
            <a href="<c:url value="/review/${trip.tripId}"/>">
                <button type="submit" class="btn button-style button-color shadow-btn">
                    <i class="bi bi-plus-lg light-text h4"></i>
                    <span class="button-text-style light-text h4">Review</span>
                </button>
            </a>
        </div>
    </c:if>
    <c:if test="${!(empty allowDelete) && allowDelete}">
        <div class="delete-trip-container">
            <button type="submit" class="btn rounded-circle button-style button-color shadow-btn" data-bs-toggle="modal" data-bs-target="#modal-<c:out value="${trip.tripId}"/>">
                <i class="bi bi-trash-fill light-text h5"></i>
            </button>
        </div>
        <div class="modal fade" id="modal-<c:out value="${trip.tripId}"/>" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3 class="modal-title danger"><spring:message code="tripCard.delete"/></h3>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <span class="text"><spring:message code="tripCard.warning.title"/>
                            <strong class="secondary-color"><c:out value="${trip.originCity.name}"/></strong>
                            <strong class="secondary-color">-</strong>
                            <strong class="secondary-color"><c:out value="${trip.destinationCity.name}"/></strong>?
                          </span>
                        <span class="text"><spring:message code="tripCard.warning.messege"/></span>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn primary-button" data-bs-dismiss="modal">
                            <span class="light-text"><spring:message code="tripCard.btn.cancel"/></span>
                        </button>
                        <c:url value="/trips/${trip.tripId}/delete" var="deleteTripUrl"/>
                        <form:form method="DELETE" action="${deleteTripUrl}">
                            <button type="submit" class="btn danger-button">
                                <span class="light-text"><spring:message code="tripCard.btn.delete"/></span>
                            </button>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${!(empty allowCancel) && allowCancel}">
        <div class="delete-trip-container">
            <button type="submit" class="btn rounded-circle button-style button-color shadow-btn" data-bs-toggle="modal" data-bs-target="#modal-<c:out value="${trip.tripId}"/>">
                <i class="bi bi-x-lg light-text h5"></i>
            </button>
        </div>
        <div class="modal fade" id="modal-<c:out value="${trip.tripId}"/>" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3 class="modal-title danger"><spring:message code="tripCard.user.cancel"/></h3>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <span class="text"><spring:message code="tripCard.user.cancel.warning.title"/>
                          <strong class="secondary-color"><c:out value="${trip.originCity.name}"/></strong>
                          <strong class="secondary-color">-</strong>
                          <strong class="secondary-color"><c:out value="${trip.destinationCity.name}"/></strong>?
                        </span>
                        <span class="text"><spring:message code="tripCard.user.cancel.warning.message"/></span>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn primary-button" data-bs-dismiss="modal">
                            <span class="light-text"><spring:message code="tripCard.btn.cancel"/></span>
                        </button>
                        <c:url value="/trips/${trip.tripId}/cancel" var="cancelTripUrl"/>
                        <form:form method="DELETE" action="${cancelTripUrl}">
                            <button type="submit" class="btn danger-button">
                                <span class="light-text"><spring:message code="tripCard.user.btn.cancel"/></span>
                            </button>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</div>
