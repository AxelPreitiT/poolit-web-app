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
    <script src="<c:url value="/resources/js/pages/createTrip.js"/>" type="module"></script>
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
                <div class="input-container">
                    <div class="input-row">
                        <div class="container">
                            <div class="row">
                                <div class="col-6">
                                    <div class="inline_container">
                                        <div class="col-3 form_container">
                                            <jsp:include page="/WEB-INF/jsp/components/city-selector.jsp">
                                                <jsp:param name="id" value="originCityId"/>
                                                <jsp:param name="defaultText" value="Ciudad"/>
                                            </jsp:include>
                                        </div>
                                        <div class="error_container">
                                            <form:errors path="originCityId" cssClass="formError" element="p"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="inline_container">
                                        <div class="col-3 form_container">
                                            <form:input path="originAddress" minlength="50" cssClass="form-control form-control-sm" id="originAddress" placeholder="Dirección"/>
                                        </div>
                                        <div class="error_container">
                                            <form:errors path="originAddress" cssClass="formError" element="p"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="input-row">
                        <div class="container">
                            <div class="row">
                                <div class="col-6">
                                    <div class="input-group input-group-sm" id="first-date-picker">
                                        <button type="button" class="btn button-color">
                                            <i class="bi bi-calendar-fill light-text"></i>
                                        </button>
                                        <form:input path="date" cssClass="form-control form-control-sm" id="first-date" placeholder="Fecha"/>
                                    </div>
                                    <div class="error_container">
                                        <form:errors path="date" cssClass="formError" element="p"/>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="input-group input-group-sm" id="time-picker">
                                        <button type="button" class="btn button-color">
                                            <i class="bi bi-clock-fill light-text"></i>
                                        </button>
                                        <form:input path="time" cssClass="form-control form-control-sm" id="time" placeholder="Hora"/>
                                    </div>
                                    <div class="error_container">
                                        <form:errors path="time" cssClass="formError" element="p"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="collapse" id="day-repeat-container">
                        <div class="input-row" id="day-repeat-row">
                            <i class="bi bi-arrow-repeat secondary-color"></i>
                            <span class="italic-text secondary-color" id="day-repeat-text"></span>
                        </div>
                    </div>
                    <div class="input-row">
                        <div class="container">
                            <div class="row">
                                <div class="col-6">
                                    <div class="input-group input-group-sm" id="last-date-picker">
                                        <button type="button" class="btn button-color" id="last-date-button" disabled>
                                            <i class="bi bi-calendar-fill light-text"></i>
                                        </button>
                                        <form:input path="lastDate" cssClass="form-control form-control-sm" id="last-date" placeholder="Última fecha" disabled="true"/>
                                    </div>
                                    <div class="error_container">
                                        <form:errors cssClass="formError" element="p"/>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div class="input-group input-group-sm">
                                        <div class="input-group-text" id="is-multitrip-container">
                                            <form:checkbox path="multitrip" cssClass="form-check-input mt-0" id="is-multitrip"/>
                                            <span class="mb-0 ml-1 placeholder-text"><spring:message code="trip.recurrent"/></span>
                                        </div>
                                    </div>
                                </div>
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
                <div class="input-container">
                    <div class="input-row">
                        <div class="container">
                            <div class="row">
                                <div class="col-6">
                                    <div class="inline_container">
                                        <div class="col-3 form_container">
                                            <jsp:include page="/WEB-INF/jsp/components/city-selector.jsp">
                                                <jsp:param name="id" value="destinationCityId"/>
                                                <jsp:param name="defaultText" value="Ciudad"/>
                                            </jsp:include>                                        </div>
                                        <div class="error_container">
                                            <form:errors path="originAddress" cssClass="formError" element="p"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-6">
                                    <div>
                                    </div>
                                    <div class="inline_container">
                                        <div class="col-3 form_container">
                                            <form:input path="destinationAddress" maxlength="50" cssClass="form-control form-control-sm" id="destinationAddress" placeholder="Dirección"/>
                                        </div>
                                        <div class="error_container">
                                            <form:errors path="destinationAddress" cssClass="formError" element="p"/>
                                        </div>
                                    </div>
                                </div>
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
                <div class="input-container">
                    <div class="input-row">
                        <div class="container">
                            <div class="row">
                                <div class="col-6">
                                    <div id="car-info-text">
                                        <div class="input-group input-group-sm">
                                            <div class="input-group-text">
                                                <i class="bi bi-car-front-fill text"></i>
                                            </div>
                                            <form:select path="carId" cssClass="form-select form-select-sm" id="car-select" disabled="${empty cars}">
                                                <form:option value="-1" label="Seleccionar auto"/>
                                                <form:options items="${cars}" itemValue="carId" itemLabel="infoCar"/>
                                            </form:select>
                                        </div>
                                        <c:forEach items="${cars}" var="car">
                                            <div class="collapse" id="car-info-details-<c:out value="${car.carId}"/>">
                                                <div class="primary-bg-color" id="car-info-details-container">
                                                    <div class="car-info-details-row">
                                                        <i class="bi bi-caret-right-fill light-text"></i>
                                                        <span class="light-text"><spring:message code="profile.plate" arguments="${car.plate}"/></span>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>
                                <c:if test="${empty cars}">
                                    <div class="col-6">
                                        <div id="no-cars-container">
                                            <h5 class="danger"><spring:message code="createTrip.carsError"/></h5>
                                            <a href="<c:url value="${createCarUrl}"/>">
                                                <button type="button" class="button-style button-color shadow-btn">
                                                    <i class="bi bi-plus light-text h5"></i>
                                                    <span class="button-text-style light-text h5"><spring:message code="createTrip.addCar"/></span>
                                                </button>
                                            </a>
                                        </div>
                                    </div>
                                </c:if>
                                <div class="col-6" id="car-image-container">
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
                        </div>
                    </div>
                    <div class="input-row" id="seats-container">
                        <div class="container">
                            <div class="row">
                                <div class="col-6">
                                    <div class="input-group input-group-sm">
                                        <div class="input-group-text">
                                            <i class="bi bi-people-fill text"></i>
                                        </div>
                                        <form:input path="maxSeats" cssClass="form-control form-control-sm" id="seats" placeholder="Cantidad de asientos"/>
                                        <div class="error_container">
                                            <form:errors path="maxSeats" cssClass="formError" element="p"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="input-row" id="price-container">
                        <div class="container">
                            <div class="row">
                                <div class="col-6">
                                    <div class="input-group input-group-sm">
                                        <div class="input-group-text">
                                            <i class="bi bi-currency-dollar text"></i>
                                        </div>
                                        <form:input path="price" cssClass="form-control form-control-sm" id="price" placeholder="Precio por viaje"/>
                                        <div class="input-group-text">
                                            <span class="text"><spring:message code="createTrip.money"/></span>
                                        </div>
                                        <div class="error_container">
                                            <form:errors path="maxSeats" cssClass="formError" element="p"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="confirm-button-container">
                <button id="create-trip-button" type="submit" class="btn button-style button-color shadow-btn" disabled>
                    <i class="bi bi-check2 light-text h3"></i>
                    <span class="button-text-style light-text h3"><spring:message code="createTrip.btn"/></span>
                </button>
            </div>
        </form:form>
    </div>
</body>
</html>
