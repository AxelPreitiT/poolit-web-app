<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.css.jsp" />
    <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.icons.jsp" />
    <link href="/css/base/font-sizes.css" rel="stylesheet">
    <link href="/css/base/colors.css" rel="stylesheet">
    <link href="/css/select-trip/main.css" rel="stylesheet">
</head>
    <body>
        <div class="trip-details">
            <div class="trip-places">
                <jsp:include page="components/location.jsp">
                    <jsp:param name="originCity" value="${trip.originCity.name}"/>
                    <jsp:param name="originAddress" value="${trip.originAddress}"/>
                    <jsp:param name="originDate" value="${trip.date}"/>
                    <jsp:param name="originTime" value="${trip.time}"/>
                </jsp:include>
                <div class="location-line">
                    <i class="bi bi-car-front h3"></i>
                </div>
                <jsp:include page="components/location.jsp">
                    <jsp:param name="originCity" value="${trip.destinationCity.name}"/>
                    <jsp:param name="originAddress" value="${trip.destinationAddress}"/>
                    <jsp:param name="originDate" value="${trip.date}"/>
                    <jsp:param name="originTime" value="${trip.time}"/>
                </jsp:include>
            </div>
            <div>
                <h2>Conductor</h2>
<%--                <div class="d-flex flex-column flex-lg-row justify-content-around">--%>
                <div class="driver-info">
                    <div class="flex-row">
                        <i class="bi bi-envelope text h5"></i>
                        <h5 class="fw-light"><c:out value="${trip.email}"/></h5>
                    </div>
                    <div class="flex-row">
                        <i class="bi bi-telephone-fill h5"></i>
                        <h5 class="fw-light"><c:out value="${trip.phone}"/></h5>
                    </div>
                </div>
            </div>
            <c:url value="/trips/${tripId}" var="bookTripUrl" />
            <form class="passenger-info" action="${bookTripUrl}" method="post">
                <h2>Datos del pasajero</h2>
                <div class="passenger-data">
                    <div class="flex-row">
                        <i class="bi bi-envelope text h3"></i>
                        <div class="form-floating">
                            <input type="email" class="form-control h5 text" id="email" name="email" placeholder="paw@itba.edu.ar">
                            <label for="email" class="placeholder-text h5">Email</label>
                        </div>
                    </div>
                    <div class="flex-row">
                        <i class="bi bi-telephone text h3"></i>
                        <div class="form-floating">
                            <input type="tel" class="form-control h5 text" id="phone" name="phone" placeholder="11 1234 5678">
                            <label for="phone" class="placeholder-text h5">Teléfono</label>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn primary submit-style">
                    <i class="bi bi-plus h2 light-text"></i>
                    <span class="h2 light-text">Crear viaje</span>
                </button>
            </form>
    </body>
</html>
