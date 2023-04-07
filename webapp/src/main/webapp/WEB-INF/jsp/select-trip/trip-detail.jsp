<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <title>Reservar</title>
    <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.css.jsp" />
    <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.icons.jsp" />
    <link href="<c:url value="/css/base/font-sizes.css"/>" rel="stylesheet">
    <script src="https://kit.fontawesome.com/b5e2fa9f6d.js" crossorigin="anonymous"></script>
    <link href="<c:url value="/css/base/colors.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/select-trip/main.css"/>" rel="stylesheet">
</head>
    <body class="background-bg-color">
        <div class="trip-details-style container-bg-color">
            <div class="trip-route">
                <div class="location-container">
                    <jsp:include page="components/origin.jsp"/>
                    <i class="bi bi-geo-alt text h5-size"></i>
                </div>
                <div class="location-line">
                    <div class="location-line-content">
                        <i class="fa-solid fa-car-side fa-bounce h5-size" id="animated-car"></i>
                        <div class="dotted-line"></div>
                    </div>
                </div>
                <div class="location-container">
                    <i class="bi bi-geo-alt-fill text h5-size"></i>
                    <jsp:include page="components/destination.jsp"/>
                </div>
            </div>
            <div class="data-container">
                <div class="driver-data">
                    <h3>Datos del conductor</h3>
                    <div class="driver-data-item">
                        <div class="driver-data-item-title">
                            <i class="bi bi-envelope text h6-size"></i>
                            <h5>Email</h5>
                        </div>
                        <p class="fw-light p-size"><c:out value="${trip.driver.email}"/></p>
                    </div>
                    <div class="driver-data-item">
                        <div class="driver-data-item-title">
                            <i class="bi bi-telephone-fill h6-size"></i>
                            <h5>Teléfono</h5>
                        </div>
                        <p class="fw-light p-size"><c:out value="${trip.driver.phone}"/></p>
                    </div>
                    <div class="driver-data-item">
                        <div class="driver-data-item-title">
                            <i class="bi bi-car-front-fill h6-size"></i>
                            <h5>Info. del auto</h5>
                        </div>
<%--                        <p class="fw-light p-size"><c:out value="${trip.carInfo}"/></p>--%>
                        <p class="fw-light p-size">TODO</p>
                    </div>
                </div>
                <div class="passenger-data">
                    <h3>Tus datos</h3>
                    <c:url value="/trips/${trip.id}" var="bookTripUrl" />
                    <form class="passenger-form" action="${bookTripUrl}" method="post">
                        <div class="passenger-data-item">
                            <i class="bi bi-envelope text h6-size"></i>
                            <div class="form-floating">
                                <input type="email" class="form-control text input-style" id="email" name="email">
                                <label for="email" class="placeholder-text">Email</label>
                            </div>
                        </div>
                        <div class="passenger-data-item">
                            <i class="bi bi-telephone-fill text h6-size"></i>
                            <div class="form-floating">
                                <input type="tel" class="form-control text input-style" id="phone" name="phone">
                                <label for="phone" class="placeholder-text">Teléfono</label>
                            </div>
                        </div>
                        <div class="confirm-btn">
                            <button type="submit" class="btn button-bg-color">
                                <span class="light-text">Confirmar</span>
                            </button>
                            <p class="placeholder-text">Quedan <c:out value="${trip.freeSeats}"/> asientos disponibles</p>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
