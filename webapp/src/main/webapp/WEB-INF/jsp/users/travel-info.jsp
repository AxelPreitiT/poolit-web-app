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
                <div class="adress-container">
                    <div class="route-info-text">
                        <span class="secondary-color h3">${trip.originCity.name}</span>
                        <span class="text">${trip.originAddress}</span>
                    </div>
                    <div class="route-info-text">
                        <span class="secondary-color h3 aling-right">${trip.destinationCity.name}</span>
                        <span class="text aling-right">${trip.destinationAddress}</span>
                    </div>
                </div>
                <div class="extra-info-container">
                    <div class="line-container">
                        <i class="bi bi-calendar text"></i>
                        <c:choose>
                            <c:when test="${trip.recurrent}">
                                <div class="format_date">
                                    <div><span class="text">${trip.dayOfWeekString}</span></div>
                                    <div><span class="text">${trip.startDateString} - ${trip.endDateString}</span></div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="format_date">
                                    <div><span class="text">${trip.dayOfWeekString}</span></div>
                                    <div><span class="text"> ${trip.startDateString}</span></div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div>
                        <i class="bi bi-clock text"></i>
                        <span class="text">${trip.startTimeString}</span>
                    </div>
                    <div>
                        <h5 class="text">$ ${trip.price}</h5>
                    </div>
                </div>
            </div>
            <div class="img-container">
                <img class="car-container"  src="https://lumiere-a.akamaihd.net/v1/images/og_cars_lightningmcqueenday_18244_4435f27a.jpeg?region=40,0,1120,630">
            </div>
        </div>
    </a>
</div>