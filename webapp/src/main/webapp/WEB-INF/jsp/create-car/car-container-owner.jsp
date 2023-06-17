<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="car" type="ar.edu.itba.paw.models.Car"  scope="request"/>

<link href="<c:url value="/resources/css/users/profile-container.css"/>" rel="stylesheet" type="text/css"/>

<div class="user-info-container-car">
  <c:url value="/cars/${car.carId}" var="updateCarUrl"/>
  <form:form modelAttribute="updateCarForm" action="${updateCarUrl}" method="post" cssClass="form-style" enctype="multipart/form-data">
  <div class="avatar-img">
    <div class="circular--landscape no-edit">
      <c:url value="/image/${car.image_id}" var="carImageUrl"/>
      <img class="circular--square" src="${carImageUrl}" alt="<spring:message code="updateCar.image"/>">
    </div>
    <div class="circular--landscape-container hidden edit" id="car-image-display">
        <label for="image-file" id="image-label" class="secondary-bg-color shadow-btn button-style">
            <c:url value="/image/${car.image_id}" var="carImageUrl"/>
            <div class="circular--landscape">
            <img class="circular--square" src="${carImageUrl}" alt="<spring:message code="updateCar.image"/>">
            </div>
        </label>
        <form:input path="imageFile" type="file" accept="image/*" id="image-file" name="image-file"/>
        <span class="edit-icon"><i class="icon bi bi-pencil-fill secondary-color h1"></i></span>
    </div>
  </div>
  <h3 id="user-name"><c:out value="${car.brand}"/></h3>
  <div class="row-info rows">
    <h6><spring:message code="createCar.carInfo"/></h6>
    <h4 class="no-edit"><c:out value="${car.infoCar}"/></h4>
    <form:input path="carInfo" id="carInfo" cssClass="form-control hidden edit"  />
    <div class="error-item">
        <i class="bi bi-exclamation-circle-fill danger"></i>
        <form:errors path="carInfo" cssClass="danger error-style" element="span"/>
    </div>
  </div>
  <div class="row-info rows">
    <h6><spring:message code="createCar.seats"/></h6>
    <h4 class="no-edit"><c:out value="${car.seats}"/></h4>
    <form:input path="seats" cssClass="form-control hidden edit" id="seats" />
    <div class="error-item">
        <i class="bi bi-exclamation-circle-fill danger"></i>
        <form:errors path="seats" cssClass="danger error-style" element="span"/>
    </div>
  </div>
  <div class="row-info rows">
    <h6><spring:message code="createCar.plate"/></h6>
    <h4><c:out value="${car.plate}"/></h4>
  </div>
  <div class="row-info rows">
    <div class="hidden edit" data-toggle="buttons">
      <h6><spring:message code="updateCar.features"/></h6>
      <c:forEach items="${allFeatures}" var="feature">
          <label class="btn btn-custom">
          <form:checkbox path="features" value="${feature}" />
          <spring:message code="${feature.toString()}"/>
          </label>
      </c:forEach>
    </div>
    <c:if test="${!(empty car.features)}">
        <h6><spring:message code="updateCar.features"/></h6>
        <div class="no-edit row-info rows">
            <c:forEach items="${car.features}" var="feature">
                <label class="btn btn-custom">
                    <checkbox value="${feature}"></checkbox>
                    <spring:message code="${feature.toString()}"/>
                </label>
            </c:forEach>
        </div>
    </c:if>
  </div>

    <jsp:useBean id="rating" type="java.lang.Double" scope="request"/>
    <div class="row-info rows">
      <h6><spring:message code="user.rating"/></h6>
      <div class="d-flex justify-content-between align-items-center">
        <div class="ratings">
            <c:set var="rating" value="${rating}" scope="request"/>
            <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp"/>
        </div>
      </div>
    </div>
    <div class="row-info hidden edit">
      <button id="update-car" type="submit" class="btn button-style button-color shadow-btn hidden edit">
          <i class="bi bi-check2 light-text h3"></i>
          <span class="button-text-style light-text h3"><spring:message code="updateCar.save"/></span>
      </button>


    </div>
    <div class="row-info no-edit">
        <button id="edit-car" type="button" class="btn button-style button-color shadow-btn no-edit" onclick="toggleEdit()">
              <i class="bi bi-pencil-square light-text h3"></i>
              <span class="button-text-style light-text h3" id="editButton"><spring:message code="profile.edit"/></span>
          </button>
    </div>
  </form:form>
  <script src="<c:url value="/resources/js/cars/editCar.js"/>" type="application/javascript"></script>


</div>
