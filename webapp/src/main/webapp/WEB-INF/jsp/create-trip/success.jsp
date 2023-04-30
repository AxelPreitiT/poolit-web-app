<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!-- Bean:
        - trip: The information of the trip to show - Trip class
-->

<jsp:useBean id="trip" type="ar.edu.itba.paw.models.trips.Trip" scope="request"/>

<html>
<head>
    <title><spring:message code="createTrip.success.pageTitle"/></title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/resources/css/create-trip/success.css"/>" rel="stylesheet">
</head>
<body class="background-color">
    <div id="navbar-container">
        <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    </div>
    <div class="main-container-style container-color">
        <div id="trip-detail-container">
            <jsp:include page="/WEB-INF/jsp/components/trip-detail.jsp"/>
        </div>
        <div id="footer-container">
            <div id="trip-price-container">
                <div class="trip-price-row">
                    <div>
                        <span class="h3 text no-bold">Precio por viaje:</span>
                    </div>
                    <div>
                        <span class="h2 secondary-color">$<c:out value="${trip.price}"/></span>
                    </div>
                </div>
            </div>
            <div id="button-container">
                <c:url value="/" var="homeUrl"/>
                <a href="${homeUrl}">
                    <button id="home-button" type="submit" class="btn button-style button-color shadow-btn">
                        <i class="bi bi-house-fill light-text h4"></i>
                        <span class="button-text-style light-text h3">Inicio</span>
                    </button>
                </a>
            </div>
        </div>
    </div>
    <div id="toast-container">
        <jsp:include page="/WEB-INF/jsp/components/success-toast.jsp">
            <jsp:param name="title" value="¡Viaje creado!"/>
            <jsp:param name="message" value="Tu viaje ha sido creado exitosamente"/>
        </jsp:include>
    </div>
</body>
</html>
