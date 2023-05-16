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
    <c:url value="/cars/create" var="createCarUrl"/>
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
                    <spring:message code="createCar.carInfoPlaceholder" var="carInfoHolder"/>
                    <form:input path="carInfo" cssClass="form-control" id="carInfo" placeholder='${carInfoHolder}'/>
                    <form:errors path="carInfo" cssClass="danger error-style" element="span"/>
                </div>
                <div class="car-data-row">
                    <span class="h3 secondary-color title-container"><spring:message code="createCar.plate"/></span>
                    <spring:message code="createCar.platePlaceholder" var="carPlateHolder"/>
                    <form:input path="plate" cssClass="form-control" id="plate" placeholder='${carPlateHolder}'/>
                    <form:errors path="plate" cssClass="danger error-style" element="span"/>
                    <form:errors cssClass="danger error-style" element="span"/>
                </div>
            </div>
            <div id="car-image-container">
                <div id="car-image-title">
                    <span class="h3 secondary-color title-container"><spring:message code="createCar.carImage"/></span>
                </div>
                <div id="car-image-display">
                    <label for="image-file" id="image-label" class="secondary-bg-color shadow-btn button-style">
                        <i class="bi bi-car-front-fill light-text h1"></i>
                    </label>
                    <form:input path="imageFile" type="file" accept="image/*" id="image-file" name="image-file"/>
                </div>
                <div id="car-image-error">
                    <form:errors path="imageFile" cssClass="danger error-style" element="span"/>
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