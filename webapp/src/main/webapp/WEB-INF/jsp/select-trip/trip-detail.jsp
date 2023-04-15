<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <title>Reserva</title>
    <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.css.jsp" />
    <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.icons.jsp" />
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/css/select-trip/main.css"/>" rel="stylesheet">
</head>
    <body class="background-bg-color">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
        <div class="trip-details-style container-bg-color">
            <div class="trip-route">
                <div class="location-container">
                    <div class="location-data">
                        <h4 class="text title-style"><c:out value="${trip.originCity.name}" escapeXml="true"/></h4>
                        <h6 class="fw-light text description-style"><c:out value="${trip.originAddress}" escapeXml="true "/></h6>
                        <h6 class="fw-light text description-style"><c:out value="${trip.originDateTime}" escapeXml="true"/></h6>
<%--                        <h6 class="fw-light text description-style"><c:out value="${trip.time}" escapeXml="true"/></h6>--%>
                    </div>
                    <i class="bi bi-geo-alt text icon-style"></i>
                </div>
                <div class="location-line">
                    <div class="location-line-content">
                        <i class="fa-solid fa-car-side fa-bounce text" id="animated-car"></i>
                        <div class="dotted-line"></div>
                    </div>
                </div>
                <div class="location-container">
                    <i class="bi bi-geo-alt-fill text icon-style"></i>
                    <div class="location-data">
                        <h4 class="text title-style"><c:out value="${trip.destinationCity.name}" escapeXml="true"/></h4>
                        <h6 class="fw-light text description-style"><c:out value="${trip.destinationAddress}" escapeXml="true "/></h6>
                    </div>
                </div>
            </div>
            <div class="data-container">
                <div class="driver-data">
                    <h3 class="text">Datos del conductor</h3>
                    <div class="driver-data-item">
                        <div class="driver-data-item-title">
                            <i class="bi bi-envelope text h5"></i>
                            <h5 class="text">Email</h5>
                        </div>
                        <p class="fw-light text fs-5"><c:out value="${trip.driver.email}"/></p>
                    </div>
                    <div class="driver-data-item">
                        <div class="driver-data-item-title">
                            <i class="bi bi-telephone-fill text h5"></i>
                            <h5 class="text">Teléfono</h5>
                        </div>
                        <p class="fw-light text fs-5"><c:out value="${trip.driver.phone}"/></p>
                    </div>
                    <div class="driver-data-item">
                        <div class="driver-data-item-title">
                            <i class="bi bi-car-front-fill text h5"></i>
                            <h5 class="text">Info. del auto</h5>
                        </div>
<%--                        <p class="fw-light text fs-5"><c:out value="${trip.carInfo}"/></p>--%>
                        <p class="fw-light text fs-5"><c:out value="${trip.car.plate}" escapeXml="true" /> (<c:out value="${trip.car.infoCar}" escapeXml="true"/>)</p>
                    </div>
                </div>
                <div class="passenger-data">
                    <h3 class="text">Tus datos</h3>
                    <c:url value="/trips/${trip.tripId}" var="bookTripUrl" />
                    <form class="passenger-form" action="${bookTripUrl}" method="post">
                        <div class="passenger-data-item">
                            <i class="bi bi-envelope text h3"></i>
                            <div class="form-floating">
                                <input type="email" class="form-control text h5 input-style" id="email" name="email" placeholder="paw@itba.edu.ar">
                                <label for="email" class="placeholder-text">Email</label>
                            </div>
                        </div>
                        <div class="passenger-data-item">
                            <i class="bi bi-telephone-fill text h3"></i>
                            <div class="form-floating">
                                <input type="tel" class="form-control text h5 input-style" id="phone" name="phone" placeholder="123456789">
                                <label for="phone" class="placeholder-text">Teléfono</label>
                            </div>
                        </div>
                        <div class="confirm-btn">
                            <button type="submit" class="btn button-bg-color">
                                <span class="light-text h4">Confirmar</span>
                            </button>
<%--                            TODO: deshabilitar boton si no hay asientos--%>
                            <p class="placeholder-text fs-6">Quedan <c:out value="${trip.maxSeats-trip.occupiedSeats}"/> asientos disponibles</p>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
