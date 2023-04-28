<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title><spring:message code="selectTrip.title"/></title>
    <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.css.jsp" />
    <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.icons.jsp" />
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <script src="https://kit.fontawesome.com/b5e2fa9f6d.js" crossorigin="anonymous"></script>
    <link href="<c:url value="/resources/css/select-trip/main.css"/>" rel="stylesheet">
</head>
    <body class="background-bg-color">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
        <div class="trip-details-style container-bg-color">
            <div class="trip-route">
                <div class="location-container">
                    <div class="location-data">
                        <h4 class="text title-style"><c:out value="${trip.originCity.name}" escapeXml="true"/></h4>
                        <h6 class="fw-light text description-style"><c:out value="${trip.originAddress}" escapeXml="true "/></h6>
                        <h6 class="fw-light text description-style"><c:out value="${trip.startDateString}" escapeXml="true"/></h6>
                        <h6 class="fw-light text description-style"><c:out value="${trip.startTimeString}" escapeXml="true"/></h6>
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
                    <h3 class="text"><spring:message code="selectTrip.driverData"/></h3>
                    <div class="driver-data-item">
                        <div class="driver-data-item-title">
                            <i class="bi bi-envelope text h5"></i>
                            <h5 class="text"><spring:message code="trip.email"/></h5>
                        </div>
                        <p class="fw-light text fs-5"><c:out value="${trip.driver.email}"/></p>
                    </div>
                    <div class="driver-data-item">
                        <div class="driver-data-item-title">
                            <i class="bi bi-telephone-fill text h5"></i>
                            <h5 class="text"><spring:message code="trip.phone"/></h5>
                        </div>
                        <p class="fw-light text fs-5"><c:out value="${trip.driver.phone}"/></p>
                    </div>
                    <div class="driver-data-item">
                        <div class="driver-data-item-title">
                            <i class="bi bi-car-front-fill text h5"></i>
                            <h5 class="text"><spring:message code="trip.carInfo"/></h5>
                        </div>
                        <p class="fw-light text fs-5"><spring:message code="format.carInfo" arguments="${trip.car.infoCar},${trip.car.plate}"/></p>
                    </div>
                </div>
                <div class="passenger-data">
                    <h3 class="text"><spring:message code="selectTrip.userData"/></h3>
                    <c:url value="/trips/${trip.tripId}" var="bookTripUrl" />
                    <form:form modelAttribute="selectForm" cssClass="passenger-form" action="${bookTripUrl}" method="post">
                        <div class="passenger-data-item">
                            <div class="icon-container">
                                <i class="bi bi-envelope text h3"></i>
                            </div>
                            <div class="form-floating">
                                <form:input path="email" class="form-control text h5 input-style" id="email" placeholder='<spring:message code="trip.email"/>'/>
                                <form:label path="email" for="email" class="placeholder-text"><spring:message code="trip.email"/></form:label>
                                <div class="error-container">
                                    <form:errors path="email" cssClass="formError" element="p"/>
                                </div>
                            </div>
                        </div>
                        <div class="passenger-data-item">
                            <div class="icon-container">
                                <i class="bi bi-telephone-fill text h3"></i>
                            </div>
                            <div class="form-floating">
                                <form:input path="phone" type="tel" class="form-control text h5 input-style" id="phone" placeholder='<spring:message code="trip.phone"/>'/>
                                <form:label path="phone" for="phone" class="placeholder-text"><spring:message code="trip.phone"/></form:label>
                                <div class="error-container">
                                    <form:errors path="phone" cssClass="formError" element="p"/>
                                </div>
                            </div>
                        </div>
                        <div class="confirm-btn">
                            <button type="submit" class="btn btn-primary btn-lg btn-search" <c:if test="${trip.occupiedSeats>=trip.maxSeats}"><c:out value="disabled='disabled'"/></c:if>>
                                <span class="light-text h4"><spring:message code="selectTrip.btnConfirm"/></span>
                            </button>
                            <p class="placeholder-text fs-6">
                                <c:if test="${trip.maxSeats-trip.occupiedSeats==0}"><spring:message code="selectTrip.seats.cero"/></c:if>
                                <c:if test="${trip.maxSeats-trip.occupiedSeats==1}"><spring:message code="selectTrip.seats.one"/></c:if>
                                <c:if test="${trip.maxSeats-trip.occupiedSeats>1}"><spring:message code="selectTrip.seats.many" arguments="${trip.maxSeats-trip.occupiedSeats}"/></c:if>
                            </p>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </body>
</html>
