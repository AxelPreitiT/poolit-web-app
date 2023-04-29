<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title><spring:message code="selectTrip.title"/></title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/resources/css/select-trip/select-trip.css"/>" rel="stylesheet" type="text/css"/>
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
                            <span class="h3 text">Total:</span>
                        </div>
                        <div>
                            <span class="h2 secondary-color">$10.000</span>
                        </div>
                    </div>
                    <div class="trip-price-row items-to-end">
                        <span class="h6 italic-text">4 viajes</span>
                    </div>
                </div>
                <div id="button-container">
                    <c:url value="/trips/${trip.tripId}" var="joinUrl"/>
                    <form action="${joinUrl}" method="post">
                        <button id="join-button" type="submit" class="btn button-style button-color shadow-btn"
                                <c:if test="${trip.freeSeats == 0}">
                                    <c:out value="disabled"/>
                                </c:if>
                        >
                            <i class="bi bi-check2 light-text h3"></i>
                            <span class="button-text-style light-text h3">Reservar</span>
                        </button>
                    </form>
                    <c:if test="${trip.freeSeats == 0}">
                        <div class="no-seats-left-container">
                            <span class="h6 italic-text"><spring:message code="selectTrip.seats.cero"/></span>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>