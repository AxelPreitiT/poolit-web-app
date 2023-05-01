<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
    <title><spring:message code="createCar.title"/></title>

    <jsp:include page="/resources/external-resources.jsp"/>

    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>

    <link href="<c:url value="/resources/css/create-auto/create-auto.css"/>" rel="stylesheet" type="text/css"/>
    <script src="<c:url value="/resources/js/cars/car.js"/>" type="module"></script>


</head>

<body class="background-color">

    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <form:form modelAttribute="createCarForm" action="${createCarUrl}" method="post" cssClass="form-style"  enctype="multipart/form-data">
    <div class="main-container-style container-color">

        <div id="main-header-row">

            <h1 class="secondary-color"><spring:message code="createCar.title"/></h1>

            <hr class="secondary-color">

        </div>

        <div class="row my-container">

            <div class=" container ">


                <div class="image-container">
                    <label class="btn light-text button-color shadow-btn" for="imageFile"><spring:message code="createCar.upload"/></label>
                    <form:input path="imageFile" type="file" accept="image/*" id="imageFile" name="imageFile" onChange="updateImageDisplay"/>

                        <div class="preview">
                          <p><spring:message code="createCar.noFiles"/></p>
                        </div>
                        <form:errors path="imageFile" cssClass="error" element="p"/>

                </div>
            </div>


            <div class=" input-container">

                <div >

                    <h3 class="secondary-color title-container"> <spring:message code="createCar.carModel"/> </h3>
                    <spring:message code="createCar.carModelPlaceholder" var="carInfoHolder"/>
                    <form:input path="carInfo" cssClass="form-control form-control-sm" id="carInfo" placeholder='${carInfoHolder}'/>
                    <form:errors path="carInfo" cssClass="error" element="p"/>
                </div>

                <div >

                    <h3 class="secondary-color title-container"> <spring:message code="createCar.plate"/> </h3>
                    <spring:message code="createCar.platePlaceholder" var="carPlateHolder"/>
                    <form:input path="plate" cssClass="form-control form-control-sm" id="plate" placeholder='${carPlateHolder}'/>
                    <form:errors path="plate" cssClass="error" element="p"/>

                </div>

            </div>

        </div>

        <div class="confirm-button-container">

            <button id="create-trip-button" type="submit" class="btn button-style button-color shadow-btn">

                <i class="bi bi-check2 light-text h3"></i>
                <span class="button-text-style light-text h3"><spring:message code="selectTrip.btnConfirm"/></span>

            </button>

        </div>

    </div>
    </form:form>

</body>
</html>