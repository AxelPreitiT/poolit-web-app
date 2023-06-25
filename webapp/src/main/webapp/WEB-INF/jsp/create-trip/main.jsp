<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title><spring:message code="createTrip.title"/></title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/resources/css/create-trip/create-trip.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body class="background-color">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="main-container-style container-color">
        <div id="main-header-row">
            <h1 class="secondary-color"><spring:message code="createTrip.title"/></h1>
            <hr class="secondary-color">
        </div>
        <c:url value="/trips/create" var="createTripUrl"/>
        <form:form modelAttribute="createTripForm" action="${createTripUrl}" method="post" cssClass="form-style">
            <div class="info-container" id="origin-info-container">
                <div class="header-row">
                    <i class="bi bi-geo-alt h2 secondary-color"></i>
                    <h2 class="secondary-color"><spring:message code="trip.origin"/></h2>
                </div>
                <div class="section-container">
                    <div class="input-container">
                        <div class="input-row">
                            <spring:message code="createTrip.city" var="city"/>
                            <jsp:include page="/WEB-INF/jsp/components/city-selector.jsp">
                                <jsp:param name="id" value="originCityId"/>
                                <jsp:param name="defaultText" value="${city}"/>
                            </jsp:include>
                            <spring:message code="createTrip.address" var="address"/>
                            <form:input path="originAddress" cssClass="form-control form-control-sm" id="originAddress" placeholder="${address}"/>
                        </div>
                        <div class="error-row">
                            <div class="error-item">
                                <i class="bi bi-exclamation-circle-fill danger"></i>
                                <form:errors path="originCityId" cssClass="error-style danger" element="span"/>
                            </div>
                            <div class="error-item right-item">
                                <i class="bi bi-exclamation-circle-fill danger"></i>
                                <form:errors path="originAddress" cssClass="error-style danger" element="span"/>
                            </div>
                        </div>
                    </div>
                    <div class="input-container">
                        <div class="input-row">
                            <div class="input-group input-group-sm" id="date-picker">
                                <button type="button" class="btn button-color">
                                    <i class="bi bi-calendar-fill light-text"></i>
                                </button>
                                <spring:message code="createTrip.date" var="date"/>
                                <form:input path="date" cssClass="form-control form-control-sm" id="date" placeholder="${date}"/>
                            </div>
                            <div class="input-group input-group-sm" id="time-picker">
                                <button type="button" class="btn button-color">
                                    <i class="bi bi-clock-fill light-text"></i>
                                </button>
                                <spring:message code="createTrip.time" var="time"/>
                                <form:input path="time" cssClass="form-control form-control-sm" id="time" placeholder="${time}"/>
                            </div>
                        </div>
                        <div class="error-row">
                            <div class="error-item">
                                <i class="bi bi-exclamation-circle-fill danger align-to-input-icon"></i>
                                <form:errors path="date" cssClass="error-style danger" element="span"/>
                            </div>
                            <div class="error-item right-item">
                                <i class="bi bi-exclamation-circle-fill danger align-to-input-icon"></i>
                                <form:errors path="time" cssClass="error-style danger" element="span"/>
                            </div>
                        </div>
                    </div>
                    <div class="input-container">
                        <div class="input-row">
                            <div class="secondary-bg-color"  id="multitrip-toggle">
                                <form:checkbox path="multitrip" cssClass="form-check-input" id="multitrip-checkbox"/>
                                <span class="light-text"><spring:message code="createTrip.recurrentTrip"/></span>
                            </div>
                        </div>
                        <div class="collapse" id="multitrip-info">
                            <div class="input-row">
                                <div>
                                    <i class="bi bi-arrow-repeat secondary-color" id="repeat-icon"></i>
                                    <span class="secondary-color italic-text"><spring:message code="createTrip.every"/></span>
                                    <strong id="multitrip-text" class="secondary-color italic-text"></strong>
                                    <span class="secondary-color italic-text"><spring:message code="createTrip.until"/></span>
                                </div>
                                <div class="input-group input-group-sm" id="last-date-picker">
                                    <button type="button" class="btn button-color" id="last-date-button">
                                        <i class="bi bi-calendar-fill light-text"></i>
                                    </button>
                                    <spring:message code="createTrip.lastDate" var="lastDate"/>
                                    <form:input path="lastDate" cssClass="form-control form-control-sm" id="last-date" placeholder="${lastDate}"/>
                                </div>
                            </div>
                        </div>
                        <div class="error-row">
                            <div class="error-item">
                                <i class="bi bi-exclamation-circle-fill danger align-to-input-icon"></i>
                                <form:errors cssClass="error-style danger error-style-line" element="span"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="vertical-dotted-line"></div>
            <div class="info-container" id="destination-info-container">
                <div class="header-row">
                    <i class="bi bi-geo-alt-fill h2 secondary-color"></i>
                    <h2 class="secondary-color"><spring:message code="trip.destination"/></h2>
                </div>
                <div class="section-container">
                    <div class="input-container">
                        <div class="input-row">
                            <jsp:include page="/WEB-INF/jsp/components/city-selector.jsp">
                                <jsp:param name="id" value="destinationCityId"/>
                                <jsp:param name="defaultText" value="${city}"/>
                            </jsp:include>
                            <form:input path="destinationAddress" cssClass="form-control form-control-sm" id="destinationAddress" placeholder="${address}"/>
                        </div>
                        <div class="error-row">
                            <div class="error-item">
                                <i class="bi bi-exclamation-circle-fill danger"></i>
                                <form:errors path="destinationCityId" cssClass="error-style danger" element="span"/>
                            </div>
                            <div class="error-item right-item">
                                <i class="bi bi-exclamation-circle-fill danger"></i>
                                <form:errors path="destinationAddress" cssClass="error-style danger" element="span"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="info-container" id="detail-info-container">
                <div class="header-row">
                    <i class="bi bi-info-circle-fill h2 secondary-color"></i>
                    <h2 class="secondary-color"><spring:message code="trip.details"/></h2>
                </div>
                <div class="section-container">
                    <div class="input-container">
                        <div class="input-row">
                            <div id="car-info-text">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text">
                                        <i class="bi bi-car-front-fill text"></i>
                                    </div>
                                    <spring:message code="createTrip.selectCar" var="selectCar"/>
                                    <form:select path="carId" cssClass="form-select form-select-sm" id="car-select"  disabled="${empty cars}">
                                        <form:option value="-1" label="${selectCar}"/>
                                        <c:forEach items="${cars}" var="car">
                                            <form:option value="${car.carId}" label="${car.infoCar}" itemAttributes="${car.seats}"/>
                                        </c:forEach>
                                    </form:select>
                                </div>
                                <c:forEach items="${cars}" var="car">
                                    <div class="collapse" id="car-info-details-<c:out value="${car.carId}"/>">
                                        <div class="primary-bg-color" id="car-info-details-container">
                                            <div class="car-info-details-row">
                                                <i class="bi bi-caret-right-fill light-text"></i>
                                                <spring:message code="profile.plate" arguments="${car.plate}" var="carPlateString"/>
                                                <span class="light-text"><c:out value="${carPlateString}"/> </span>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                            <c:if test="${empty cars}">
                                <div id="no-cars-container">
                                    <h5 class="danger"><spring:message code="createTrip.carsError"/></h5>
                                    <a href="<c:url value="${createCarUrl}"/>">
                                        <button type="button" class="button-style button-color shadow-btn">
                                            <i class="bi bi-plus light-text h5"></i>
                                            <span class="button-text-style light-text h5"><spring:message code="createTrip.addCar"/></span>
                                        </button>
                                    </a>
                                </div>
                            </c:if>
                            <div id="car-image-container">
                                <c:forEach items="${cars}" var="car">
                                    <div class="collapse collapse-horizontal" id="car-info-image-<c:out value="${car.carId}"/>">
                                        <c:url value="/image/${car.image_id}" var="carImageUrl"/>
                                        <div class="image-container">
                                            <img src="${carImageUrl}" alt="car image"/>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="error-row">
                            <div class="error-item">
                                <i class="bi bi-exclamation-circle-fill danger align-to-input-icon"></i>
                                <form:errors path="carId" cssClass="error-style danger" element="span"/>
                            </div>
                        </div>
                    </div>
                    <div class="input-container">
                        <div class="input-row">
                            <div class="input-group input-group-sm">
                                <div class="input-group-text">
                                    <i class="bi bi-people-fill text"></i>
                                </div>
                                <spring:message code="createTrip.numberSeats" var="numberSeats"/>
                                <form:input path="maxSeats" cssClass="form-control form-control-sm" id="seats" placeholder="${numberSeats}"/>
                            </div>
                        </div>
                        <div class="error-row">
                            <div class="error-item">
                                <i class="bi bi-exclamation-circle-fill danger align-to-input-icon"></i>
                                <form:errors path="maxSeats" cssClass="error-style danger" element="span"/>
                            </div>
                        </div>
                    </div>
                    <div class="input-container">
                        <div class="input-row">
                            <div class="input-group input-group-sm">
                                <div class="input-group-text">
                                    <i class="bi bi-currency-dollar text"></i>
                                </div>
                                <spring:message code="createTrip.price" var="price"/>
                                <form:input path="price" cssClass="form-control form-control-sm" id="price" placeholder="${price}"/>
                                <div class="input-group-text">
                                    <span class="text"><spring:message code="createTrip.money"/></span>
                                </div>
                            </div>
                        </div>
                        <div class="error-row">
                            <div class="error-item">
                                <i class="bi bi-exclamation-circle-fill danger align-to-input-icon"></i>
                                <form:errors path="price" cssClass="error-style danger" element="span"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="confirm-button-container">
                <button id="create-trip-button" type="submit" class="btn button-style button-color shadow-btn" disabled>
                    <i class="bi bi-check2 light-text h3"></i>
                    <span class="button-text-style light-text h3"><spring:message code="createTrip.btnCreate"/></span>
                </button>
            </div>
        </form:form>
    </div>
    <script src="<c:url value="/resources/js/pages/createTrip.js"/>" type="module"></script>
    <script src="<c:url value="/resources/js/trips/maxSeats.js"/>" type="module"></script>

</body>
</html>
