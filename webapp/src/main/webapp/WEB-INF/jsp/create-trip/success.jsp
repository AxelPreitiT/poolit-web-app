<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Viaje creado</title>
    <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.css.jsp" />
    <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.icons.jsp" />
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/css/create-trip/success.css"/>" rel="stylesheet">
</head>
<body class="background-bg-color">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="container-bg-color main-container-style">
        <div class="success-container">
            <i class="fa-solid fa-circle-check success h1"></i>
            <h1 class="success">Su viaje se ha creado con éxito</h1>
        </div>
        <jsp:include page="/WEB-INF/jsp/components/trip-route.jsp"/>
        <hr>
        <div class="info-container">
            <div class="car-container">
                <i class="bi bi-car-front-fill text h5"></i>
                <div class="car-text-container">
                    <div class="car-title-container">
                        <h4 class="text">Datos del auto</h4>
                    </div>
                    <div class="car-info-container">
                        <div class="car-info-item">
                            <h6>Descripción</h6>
                            <p><c:out value="${trip.infoCar}"/></p>
                        </div>
                        <div class="car-info-item">
                            <h6>Patente</h6>
                            <p><c:out value="${trip.plate}"/></p>
                        </div>
                        <div class="car-info-item">
                            <h6>Disponibilidad</h6>
                            <p>Quedan <c:out value="${trip.freeSeats}"/> asientos libres</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="contact-container">
                <i class="bi bi-telephone-fill text h5"></i>
                <div class="contact-text-container">
                    <div class="contact-title-container">
                        <h4 class="text">Contacto</h4>
                    </div>
                    <div class="contact-info-container">
                        <div class="contact-info-item">
                            <h6>Email</h6>
                            <p><c:out value="${trip.driver.email}"/></p>
                        </div>
                        <div class="contact-info-item">
                            <h6>Teléfono</h6>
                            <p><c:out value="${trip.driver.phone}"/></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="button-container">
            <a href="<c:url value="/"/>">
                <button class="btn button-style button-bg-color">
                    <span class="light-text h5">Volver a inicio</span>
                </button>
            </a>
        </div>
    </div>
</body>
</html>
