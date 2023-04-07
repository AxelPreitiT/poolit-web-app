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
                    <h2 class="h5-size">Datos del conductor</h2>
                    <div class="driver-data-item">
                        <div class="driver-data-item-title">
                            <i class="bi bi-envelope text h6-size"></i>
                            <h6 class="h6-size">Email</h6>
                        </div>
                        <p class="fw-light p-size"><c:out value="${trip.driver.email}"/></p>
                    </div>
                    <div class="driver-data-item">
                        <div class="driver-data-item-title">
                            <i class="bi bi-telephone-fill h6-size"></i>
                            <h6 class="h6-size">Teléfono</h6>
                        </div>
                        <p class="fw-light p-size"><c:out value="${trip.driver.phone}"/></p>
                    </div>
                    <div class="driver-data-item">
                        <div class="driver-data-item-title">
                            <i class="bi bi-car-front-fill h6-size"></i>
                            <h6 class="h6-size">Info. del auto</h6>
                        </div>
<%--                        <p class="fw-light p-size"><c:out value="${trip.carInfo}"/></p>--%>
                        <p class="fw-light p-size">TODO</p>
                    </div>
                </div>
                <div class="passenger-data">
                    <h2 class="h5-size">Tus datos</h2>
                    <c:url value="/trips/${trip.id}" var="bookTripUrl" />
                    <form class="passenger-form" action="${bookTripUrl}" method="post">
                        <div class="passenger-data-item">
                            <i class="bi bi-envelope text h6-size"></i>
                            <div class="form-floating">
                                <input type="email" class="form-control text input-style p-size" id="email" name="email" placeholder="paw@itba.edu.ar">
                                <label for="email" class="placeholder-text">Email</label>
                            </div>
                        </div>
                        <div class="passenger-data-item">
                            <i class="bi bi-telephone-fill text h6-size"></i>
                            <div class="form-floating">
                                <input type="tel" class="form-control text input-style p-size" id="phone" name="phone" placeholder="11 1234 5678">
                                <label for="phone" class="placeholder-text">Teléfono</label>
                            </div>
                        </div>
                        <div class="confirm-btn">
                            <button type="submit" class="btn button-bg-color">
                                <span class="p-size light-text">Confirmar</span>
                            </button>
                            <label class="placeholder-text">Quedan <c:out value="${trip.freeSeats}"/> asientos disponibles</label>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
