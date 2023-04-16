<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link href="<c:url value="/css/components/detail.css"/>" rel="stylesheet"/>
<jsp:useBean id="trip" type="ar.edu.itba.paw.models.Trip" scope="request"/>
<div class="car-container">
    <i class="bi bi-car-front-fill text h5"></i>
    <div class="car-text-container">
        <div class="car-title-container">
            <h4 class="text"><spring:message code="success.carInfo.title"/></h4>
        </div>
        <div class="car-info-container">
            <div class="car-info-item">
                <h6><spring:message code="success.carInfo.description"/></h6>
                <p><c:out value="${trip.car.infoCar}"/></p>
            </div>
            <div class="car-info-item">
                <h6><spring:message code="success.carInfo.plate"/></h6>
                <p><c:out value="${trip.car.plate}"/></p>
            </div>
            <div class="car-info-item">
                <h6><spring:message code="success.carInfo.availability"/></h6>
                <p><spring:message code="success.carInfo.availability.info" arguments="${trip.maxSeats - trip.occupiedSeats -1}"/></p>
            </div>
        </div>
    </div>
</div>
