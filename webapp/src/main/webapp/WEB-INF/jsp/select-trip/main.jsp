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
                            <span class="button-text-style light-text h3">Unirse</span>
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
    <%--<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
        <div class="trip-details-style container-color">
            <div class="trip-route secondary-color">
                <div class="location-container">
                    <div class="location-data">
                        <h4 class=" title-style"><c:out value="${trip.originCity.name}" escapeXml="true"/></h4>
                        <h6 class="fw-light  description-style"><c:out value="${trip.originAddress}" escapeXml="true "/></h6>
                        <h6 class="fw-light  description-style"><c:out value="${trip.originDateString}" escapeXml="true"/></h6>
                        <h6 class="fw-light  description-style"><c:out value="${trip.originTimeString}" escapeXml="true"/></h6>
                    </div>
                    <i class="bi bi-geo-alt  icon-style"></i>
                </div>
                <div class="location-line">
                    <div class="location-line-content">
                        <i class="fa-solid fa-car-side fa-bounce " id="animated-car"></i>
                        <div class="dotted-line"></div>
                    </div>
                </div>
                <div class="location-container">
                    <i class="bi bi-geo-alt-fill  icon-style"></i>
                    <div class="location-data">
                        <h4 class=" title-style"><c:out value="${trip.destinationCity.name}" escapeXml="true"/></h4>
                        <h6 class="fw-light  description-style"><c:out value="${trip.destinationAddress}" escapeXml="true "/></h6>
                    </div>
                </div>
            </div>
            <div class="data-container ">
                <div class="col-5">
                    <jsp:include page="/WEB-INF/jsp/components/trip-detail-card.jsp">
                        <jsp:param name="trip" value="${trip}"/>
                    </jsp:include>
                </div>
                <div class="col-3">
                    <div class=" placeholder-image-color">
                        <i class="bi bi-car-front-fill placeholder-image-icon-color h1"></i>

                    </div>
                </div>
            </div>
            <div class="data-container ">
                <div class=" secondary-color">
                    <h3> Total a pagar: $500 </h3>
                </div>
                <div >
                    <div class="confirm-btn">
                    <button type="submit" class="btn button-style button-color shadow-btn" <c:if test="${trip.occupiedSeats>=trip.maxSeats}"><c:out value="disabled='disabled'"/></c:if>>
                        <span class="button-text-style light-text h4"><spring:message code="selectTrip.btnJoin"/></span>
                    </button>
                    </div>
                </div>
            </div>
        </div>--%>
    </body>
</html>