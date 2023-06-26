<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
    <title><spring:message code="createCar.title"/></title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/resources/css/create-car/create-car.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body class="background-color">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <c:url value="/cars/create" var="createCarUrl">
        <c:param name="firstCar" value="${firstCar}"/>
    </c:url>
    <form:form modelAttribute="createCarForm" action="${createCarUrl}" method="post" cssClass="form-style"  enctype="multipart/form-data">
    <div class="main-container-style container-color">
        <div id="main-header-row">
            <h1 class="secondary-color"><spring:message code="createCar.title"/></h1>
            <hr class="secondary-color">
        </div>
        <div id="form-container">
            <div id="car-data-container">
                <div class="car-data-row">
                    <span class="h3 secondary-color title-container"><spring:message code="createCar.carInfo"/></span>
                    <form:select path="carBrand" cssClass="form-select form-select-sm" id="carBrand">
                        <form:option value="-1"><spring:message code="createCar.brand"/></form:option>
                        <c:forEach items="${brands}" var="brand">
                            <form:option value="${brand}" label="${brand.toString()}"/>
                        </c:forEach>
                    </form:select>
                    <spring:message code="createCar.carInfoPlaceholder" var="carInfoHolder"/>
                    <form:input path="carInfo" cssClass="form-control" id="carInfo" placeholder='${carInfoHolder}'/>
                    <div class="error-item">
                        <i class="bi bi-exclamation-circle-fill danger"></i>
                        <form:errors path="carInfo" cssClass="danger error-style" element="span"/>
                    </div>
                </div>
                <div class="car-data-row">
                    <span class="h3 secondary-color title-container"><spring:message code="createCar.plate"/></span>
                    <spring:message code="createCar.platePlaceholder" var="carPlateHolder"/>
                    <form:input path="plate" cssClass="form-control" id="plate" placeholder='${carPlateHolder}'/>
                    <div class="error-item">
                        <i class="bi bi-exclamation-circle-fill danger"></i>
                        <form:errors path="plate" cssClass="danger error-style" element="span"/>
                    </div>
                    <div class="error-item">
                        <i class="bi bi-exclamation-circle-fill danger"></i>
                        <form:errors cssClass="danger error-style" element="span"/>
                    </div>
                </div>
                <div class="car-data-row">
                    <div class="h3 secondary-color title-container"><div><spring:message code="createCar.seats"/></div></div>
                    <spring:message code="createCar.seatsChange" var="seatsChange"/>
                    <form:input path="seats" cssClass="form-control" id="seats" placeholder='${seatsChange}'/>
                    <div class="error-item">
                        <i class="bi bi-exclamation-circle-fill danger"></i>
                        <form:errors path="seats" cssClass="danger error-style" element="span"/>
                    </div>
                </div>
            </div>
            <div id="car-image-container">
                <div id="car-image-title">
                    <span class="h3 secondary-color title-container"><spring:message code="createCar.carImage"/></span>
                </div>
                <div id="car-image-display">
                    <label for="image-file" id="image-label" class="secondary-bg-color shadow-btn button-style">
                        <i class="bi bi-image light-text h1"></i>
                    </label>
                    <form:input path="imageFile" type="file" accept="image/*" id="image-file" name="image-file"/>
                </div>
                <div id="car-image-error" class="error-item">
                    <i class="bi bi-exclamation-circle-fill danger"></i>
                    <form:errors path="imageFile" cssClass="danger error-style" element="span"/>
                </div>
            </div>
            <div class="feature-options-container">
                <div class="feature-options-title">
                    <span class="h3 secondary-color title-container"><spring:message code="searchFilters.carFeatures"/></span>
                </div>
                <div class="feature-options-input">
                    <c:forEach items="${allFeatures}" var="feature">
                        <label class="btn-custom feature-option <c:if test="${createCarForm.hasCarFeature(feature)}">active</c:if>" id="label-${feature}">
                            <form:hidden path="features" value="${feature}" id="input-${feature}" disabled="${!createCarForm.hasCarFeature(feature)}"/>
                            <spring:message code="${feature.code}"/>
                        </label>
                    </c:forEach>
                </div>
            </div>
        </div>
        <div id="confirm-button-container">
            <button id="create-trip-button" type="submit" class="btn button-style button-color shadow-btn">
                <i class="bi bi-check2 light-text h3"></i>
                <span class="button-text-style light-text h3"><spring:message code="createCar.btnCreate"/></span>
            </button>
        </div>
    </div>
    </form:form>
    <script src="<c:url value="/resources/js/cars/car.js"/>" type="application/javascript"></script>
</body>
</html>