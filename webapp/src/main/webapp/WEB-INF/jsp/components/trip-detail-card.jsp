<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link href="<c:url value="/resources/css/components/trip-detail-card.css"/>" rel="stylesheet" type="text/css"/>
<script src="<c:url value="/resources/js/components/searchFilters.js"/>" type="module"></script>

<!-- Params:
        - url: url to submit the form
-->

<div class="main-container primary-bg-color">
    <div class="details-container">
        <div class="title-row">
            <h4 class="light-text detail">Detalles del viaje</h5>
        </div>
        <div class="show-row" >
            <i class="bi bi-clock light-text h5"></i>
            <h6 class="light-text detail"><c:out value="${trip.originTimeString}" escapeXml="true"/></h5>
        </div>
        <div class="show-row" >
            <i class="bi bi-calendar light-text h5"></i>
            <div class="align-self-center">
                <h6 class="light-text detail"><c:out value="${trip.originDateString}" escapeXml="true"/></h5>
            </div>
        </div>
        <div class="show-row" >
            <i class="bi bi-car-front-fill light-text h5"></i>
            <div class="align-self-center">
                <h6 class="light-text detail"><c:out value="${trip.car.infoCar}" escapeXml="true"/></h5>
                <h6 class="light-text sub-detail"><c:out value="${trip.car.plate}" escapeXml="true"/></h5>
            </div>
        </div>
        <div class="show-row" >
            <i class="bi bi-people light-text h5"></i>
            <h6 class="light-text detail">
                <c:if test="${trip.maxSeats-trip.occupiedSeats==0}"><spring:message code="selectTrip.seats.cero"/></c:if>
                <c:if test="${trip.maxSeats-trip.occupiedSeats==1}"><spring:message code="selectTrip.seats.one"/></c:if>
                <c:if test="${trip.maxSeats-trip.occupiedSeats>1}"><spring:message code="selectTrip.seats.many" arguments="${trip.maxSeats-trip.occupiedSeats}"/></c:if>
            </h5>
        </div>
        <hr/>
        <div class="show-row" >
            <i class="bi bi-person-circle light-text h5"></i>
            <h6 class="light-text detail"><c:out value="${trip.driver.email}" escapeXml="true"/></h5>
        </div>
        <div class="show-row" >
            <i class="bi bi-envelope-fill light-text h5"></i>
            <h6 class="light-text detail"><c:out value="${trip.driver.email}" escapeXml="true"/></h5>
        </div>
        <div class="show-row" >
            <i class="bi bi-telephone-fill light-text h5"></i>
            <h6 class="light-text detail"><c:out value="${trip.driver.phone}" escapeXml="true"/></h5>
        </div>
    </div>
</div>
