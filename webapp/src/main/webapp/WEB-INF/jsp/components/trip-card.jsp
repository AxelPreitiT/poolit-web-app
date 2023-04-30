<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
        <a href="${param.tripUrl}">
            <div class="row g-0">
                <div class="col-8">
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
                            <div class="footer-date-container">
                                <i class="bi bi-calendar text"></i>
                                <div class="date-info-column">
                                    <c:choose>
                                        <c:when test="${trip.recurrent}">
                                            <span class="text text-capitalize"><c:out value="${trip.dayOfWeekString}"/></span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text"><c:out value="${trip.startDateString}"/></span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                            <div class="footer-time-container">
                                <i class="bi bi-clock text"></i>
                                <span class="text"><c:out value="${trip.startTimeString}"/></span>
                            </div>
                            <div class="footer-price-container">
                                <h2 class="secondary-color">$<c:out value="${trip.price}"/></h2>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-4 placeholder-image-color">
                    <i class="bi bi-car-front-fill placeholder-image-icon-color h1"></i>
                </div>
            </div>
        </a>
    </div>
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
                        <h3 class="modal-title danger">Eliminar viaje</h3>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                    </div>
                    <div class="modal-body">
                        <span class="text">¿Está seguro que desea eliminar el viaje <strong class="secondary-color"><c:out value="${trip.originCity.name}"/> - <c:out value="${trip.destinationCity.name}"/></strong>?</span>
                        <span class="text">Se enviará una notificación a todos los pasajeros anotados.</span>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn primary-bg-color" data-bs-dismiss="modal">
                            <span class="light-text">Cancelar</span>
                        </button>
                        <form:form method="DELETE" action="${param.tripUrl}">
                            <button type="submit" class="btn danger-bg-color">
                                <span class="light-text">Eliminar</span>
                            </button>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</div>
