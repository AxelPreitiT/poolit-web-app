<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
    <head>
        <title><spring:message code="createTrip.title"/></title>
        <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.css.jsp" />
        <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.icons.jsp" />
        <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
        <link href="<c:url value="/css/create-trip/main.css"/>" rel="stylesheet">
    </head>
    <body class="background-bg-color">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
        <div>
            <div class="form-style container-bg-color">
                <c:url value="/trips/create" var="createTripUrl" />
                <form action="${createTripUrl}" method="post">
                    <div class="route-container">
                        <div class="location-container">
                            <div class="row-title">
                                <div class="icon-container">
                                    <i class="bi bi-geo-alt text h4-size"></i>
                                </div>
                                <h3 class="h3 text"><spring:message code="trip.origin"/></h3>
                            </div>
                            <div class="row-input">
                                <jsp:include page="/WEB-INF/jsp/components/city-selector.jsp">
                                    <jsp:param name="id" value="originCity" />
                                </jsp:include>
                                <div class="form-floating">
                                    <input type="text" class="form-control text" id="originAddress" name="originAddress" placeholder='<spring:message code="trip.address"/>'>
                                    <label for="originAddress" class="placeholder-text"><spring:message code="trip.address"/></label>
                                </div>
                            </div>
                        </div>
                        <div class="time-trip-container">
                            <div class="container-line">
                                <div class="vertical-dotted-line"></div>
                            </div>
                            <div class="row-input row-time">
                                <div class="setting">
                                    <div class="form-floating">
                                        <input type="date" class="form-control text" id="date" name="date" placeholder='<spring:message code="trip.date"/>'>
                                        <label for="date" class="placeholder-text"><spring:message code="trip.date"/></label>
                                    </div>
                                </div>
                                <div class="setting">
                                    <div class="form-floating">
                                        <input type="time" class="form-control text" id="time" name="time">
                                        <label for="time" class="placeholder-text"><spring:message code="trip.time"/></label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="location-container">
                            <div class="row-title">
                                <div class="icon-container">
                                    <i class="bi bi-geo-alt-fill text h4-size"></i>
                                </div>
                                <h3 class="h3 text"><spring:message code="trip.destination"/></h3>
                            </div>
                            <div class="row-input">
                                <jsp:include page="/WEB-INF/jsp/components/city-selector.jsp">
                                    <jsp:param name="id" value="destinationCity" />
                                </jsp:include>
                                <div class="form-floating">
                                    <input type="text" class="form-control text" id="destinationAddress" name="destinationAddress" placeholder='<spring:message code="trip.address"/>'>
                                    <label for="destinationAddress" class="placeholder-text"><spring:message code="trip.address"/></label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div>
                        <div class="trip-settings">
                            <div class="row-title">
                                <div class="icon-container">
                                    <i class="bi bi-car-front-fill text h6-size"></i>
                                </div>
                                <h4 class="h4 text"><spring:message code="trip.myCar"/></h4>
                            </div>
                            <div class="row-input">
                                <div class="setting">
                                    <div class="infoCar">
                                        <div class="form-floating">
                                            <input type="text" class="form-control text" id="infoCar" name="infoCar" placeholder='<spring:message code="trip.carInfo"/>'>
                                            <label for="infoCar" class="placeholder-text"><spring:message code="trip.carInfo"/></label>
                                        </div>
                                    </div>
                                    <div class="plate">
                                        <div class="form-floating">
                                            <input type="text" class="form-control text" id="plate" name="plate" placeholder='<spring:message code="trip.plate"/>'>
                                            <label for="plate" class="placeholder-text"><spring:message code="trip.plate"/></label>
                                        </div>
                                    </div>
                                </div>
                                <div class="setting">
                                    <div class="form-floating">
                                        <input type="number" min="1" class="form-control text" id="seats" name="seats" placeholder='<spring:message code="trip.seatsNumbers"/>'>
                                        <label for="seats" class="placeholder-text"><spring:message code="trip.seatsNumbers"/></label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="driver-info">
                            <div class="row-title">
                                <div class="icon-container">
                                    <i class="bi bi-person-fill text h6-size"></i>
                                </div>
                                <h4 class="h4 text"><spring:message code="trip.contact"/></h4>
                            </div>
                            <div class="row-input">
                                <div class="info">
                                    <div class="form-floating">
                                        <input type="email" class="form-control text" id="email" name="email" placeholder='<spring:message code="trip.email"/>'>
                                        <label for="email" class="placeholder-text"><spring:message code="trip.email"/></label>
                                    </div>
                                </div>
                                <div class="info">
                                    <div class="form-floating">
                                        <input type="tel" class="form-control text" id="phone" name="phone" placeholder='<spring:message code="trip.email"/>'>
                                        <label for="phone" class="placeholder-text"><spring:message code="trip.phone"/></label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="create-trip-btn">
                        <button type="submit" class="btn btn-lg button-bg-color">
                            <div class="inline-btn">
                                <h3 class="h3"><spring:message code="createTrip.btnCreate"/></h3>
                            </div>
                        </button>
                    </div>
                </form>
            </div>
        </div>
        <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.js.jsp" />
    </body>
</html>
