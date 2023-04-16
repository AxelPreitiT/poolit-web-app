<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<c:url value="/css/components/detail.css"/>" rel="stylesheet"/>
<jsp:useBean id="trip" type="ar.edu.itba.paw.models.Trip" scope="request"/>
<div class="car-container">
    <i class="bi bi-car-front-fill text h5"></i>
    <div class="car-text-container">
        <div class="car-title-container">
            <h4 class="text">Datos del auto</h4>
        </div>
        <div class="car-info-container">
            <div class="car-info-item">
                <h6>Descripción</h6>
                <p><c:out value="${trip.car.infoCar}"/></p>
            </div>
            <div class="car-info-item">
                <h6>Patente</h6>
                <p><c:out value="${trip.car.plate}"/></p>
            </div>
            <div class="car-info-item">
                <h6>Disponibilidad</h6>
                <p><c:out value="${trip.maxSeats - trip.occupiedSeats -1 }"/> asientos libres</p>
            </div>
        </div>
    </div>
</div>
