<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<link href="<c:url value="/css/discovery/travel-card.css"/>" rel="stylesheet">
<jsp:useBean id="trip" scope="request" type="ar.edu.itba.paw.models.Trip"/>
<c:url value="/trips/${trip.tripId}" var="tripDetailUrl" />

<a href="${tripDetailUrl}" class="card-link">
    <div class="travel-card inline-container2">
        <div class="location-column">
            <div class="address-row">
                <div class="icon-container">
                    <i class="bi bi-geo-alt h3"></i>
                </div>

                <h3 class="not-wrapp"><c:out value="${trip.originCity.name}" escapeXml="true"/></h3>
            </div>
            <div class="icon-container ">
                <div class="vertical-dotted-line"></div>
            </div>
            <div class="address-row">
                <div class="icon-container">
                    <i class="bi bi-geo-alt-fill h3"></i>
                </div>
                <h3  class="not-wrapp"><c:out value="${trip.destinationCity.name}" escapeXml="true"/> </h3>
            </div>
        </div>
        <div class="data-column">
            <div class="data-row">
                <h5><spring:message code="format.seats" arguments="${trip.occupiedSeats},${trip.maxSeats}"/></h5>
                <i class="bi bi-person-fill text h5"></i>
            </div>
            <div class="data-row">
                <h6><c:out value="${trip.originDateString}" escapeXml="true"/></h6>
                <i class="bi bi-calendar text h5"></i>
            </div>
            <div class="data-row">
                <h6><c:out value="${trip.originTimeString}" escapeXml="true"/> hs</h6>
                <i class="bi bi-clock text h5"></i>
            </div>
            <%--        TODO: agregar cuando tengamos el precio--%>
            <%--        <div class="data-row">--%>
            <%--            <h3><strong>120</strong></h3>--%>
            <%--        </div>--%>
        </div>
    </div>
</a>

