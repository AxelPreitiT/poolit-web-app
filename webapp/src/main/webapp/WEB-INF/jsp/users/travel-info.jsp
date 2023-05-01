<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="trip" type="ar.edu.itba.paw.models.trips.Trip" scope="request"/>
<link href="<c:url value="/resources/css/users/travel-info.css"/>" rel="stylesheet" type="text/css"/>

<div>
    <a href="/test">
        <div class="card-info">
            <div class="data-container">
                <div class="route-container">
                    <i class="secondary-color bi bi-geo-alt icon-style"></i>
                    <div class="secondary-color horizontal_dotted_line"></div>
                    <i class="secondary-color bi bi-geo-alt-fill icon-style"></i>
                </div>
                <div class="adress-container">
                    <div class="route-info-text">
                        <span class="secondary-color h3"><c:out value="${trip.originCity.name}"/></span>
                        <span class="text"><c:out value="${trip.originAddress}"/></span>
                    </div>
                    <div class="route-info-text">
                        <span class="secondary-color h3 aling-right"><c:out value="${trip.destinationCity.name}"/></span>
                        <span class="text aling-right"><c:out value="${trip.destinationAddress}"/></span>
                    </div>
                </div>
                <div class="extra-info-container">
                    <div class="line-container">
                        <i class="bi bi-calendar text"></i>
                        <c:choose>
                            <c:when test="${trip.recurrent}">
                                <div class="format_date">
                                    <div><span class="text"><c:out value="${trip.dayOfWeekString}"/></span></div>
                                    <div><span class="text"><spring:message code="profile.trevelInfo.dateFormat" arguments="${trip.startDateString}, ${trip.endDateString}"/></span></div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="format_date">
                                    <div><span class="text"><c:out value="${trip.dayOfWeekString}"/></span></div>
                                    <div><span class="text"><c:out value="${trip.startDateString}"/></span></div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div>
                        <i class="bi bi-clock text"></i>
                        <span class="text"><c:out value="${trip.startTimeString}"/></span>
                    </div>
                    <div>
                        <h5 class="text"><spring:message code="format.price" arguments="${trip.price}"/></h5>
                    </div>
                </div>
            </div>
            <div class="img-container">
                <img class="car-container"  src="https://lumiere-a.akamaihd.net/v1/images/og_cars_lightningmcqueenday_18244_4435f27a.jpeg?region=40,0,1120,630">
            </div>
        </div>
    </a>
</div>