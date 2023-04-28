<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Bean:
        - Trip: The info of the trip to show - Trip class
-->

<html>
<head>
    <title>Detalles del viaje</title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/resources/css/trip-detail/trip-detail.css"/>" rel="stylesheet" type="text/css"/>
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
                        <span class="h2 secondary-color">$10000</span>
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
</body>
</html>
