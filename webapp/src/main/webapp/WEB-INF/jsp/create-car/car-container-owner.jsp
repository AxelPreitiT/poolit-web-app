<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="car" type="ar.edu.itba.paw.models.Car"  scope="request"/>

<link href="<c:url value="/resources/css/users/profile-container.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/resources/css/create-car/car-container.css"/>" rel="stylesheet" type="text/css"/>

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
            <span class="edit-icon"><i class="icon bi bi-pencil-fill secondary-color h1"></i></span>
            <c:url value="/image/${car.image_id}" var="carImageUrl"/>
            <div class="circular--landscape">
            <img class="circular--square" src="${carImageUrl}" alt="<spring:message code="updateCar.image"/>">
            </div>
        </label>
        <form:input path="imageFile" type="file" accept="image/*" id="image-file" name="image-file"/>
    </div>
  </div>
  <div id="user-name">
      <h3 class="no-edit">
          <c:if test="${car.hasInfoCar()}">
              <c:out value="${car.infoCar}"/>
          </c:if>
      </h3>
      <form:input path="carInfo" id="carInfo" cssClass="form-control hidden edit"  />
      <div class="error-item">
          <i class="bi bi-exclamation-circle-fill danger"></i>
          <form:errors path="carInfo" cssClass="danger error-style" element="span"/>
      </div>
  </div>
  <c:if test="${car.hasBrand()}">
      <div class="row-info">
          <h6><spring:message code="createCar.brand"/></h6>
          <h4><c:out value="${car.brand}"/></h4>
      </div>
  </c:if>
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
  <div class="row-info rows <c:if test="${(empty car.features)}">hidden edit</c:if>">
    <div class="hidden edit">
      <h6><spring:message code="updateCar.features"/></h6>
      <div class="feature-options-container edit">
          <c:forEach items="${allFeatures}" var="feature">
              <label class="btn-custom feature-option <c:if test="${car.hasFeature(feature)}">active</c:if>" id="label-${feature}">
                  <form:hidden path="features" value="${feature}" id="input-${feature}" disabled="${!car.hasFeature(feature)}"/>
                  <spring:message code="${feature.code}"/>
              </label>
          </c:forEach>
      </div>
    </div>
    <c:if test="${!(empty car.features)}">
        <div class="no-edit row-info rows">
            <h6><spring:message code="updateCar.features"/></h6>
            <div id="feature-options-container edit">
                <c:forEach items="${car.features}" var="feature">
                    <label class="btn-custom active">
                        <spring:message code="${feature.code}"/>
                    </label>
                </c:forEach>
            </div>
        </div>
    </c:if>
  </div>

    <jsp:useBean id="rating" type="java.lang.Double" scope="request"/>
    <div class="row-info rows">
      <h6><spring:message code="user.rating"/></h6>
      <div class="d-flex justify-content-between align-items-center">
        <div class="ratings">
            <c:set var="rating" value="${rating}" scope="request"/>
            <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp">
                <jsp:param name="fontSize" value="h4"/>
                <jsp:param name="fontColor" value="secondary-color"/>
            </jsp:include>
        </div>
      </div>
    </div>
    <div class="row-info hidden edit">
        <div class="edit-button-container">
            <button type="button" class="btn button-style primary-button shadow-btn" onclick="toggleEdit()">
                <i class="bi bi-pencil-square light-text h5"></i>
                <span class="button-text-style light-text h5"><spring:message code="review.cancel"/></span>
            </button>
            <button id="update-car" type="submit" class="btn button-style button-color shadow-btn hidden edit">
                <i class="bi bi-check2 light-text h5"></i>
                <span class="button-text-style light-text h5"><spring:message code="updateCar.save"/></span>
            </button>
        </div>
    </div>
    <div class="row-info no-edit">
        <div class="edit-button-container">
            <button id="edit-car" type="button" class="btn button-style button-color shadow-btn no-edit" onclick="toggleEdit()">
                <i class="bi bi-pencil-square light-text h5"></i>
                <span class="button-text-style light-text h5" id="editButton"><spring:message code="profile.edit"/></span>
            </button>
        </div>
    </div>
  </form:form>
  <script src="<c:url value="/resources/js/cars/editCar.js"/>" type="application/javascript"></script>


</div>
