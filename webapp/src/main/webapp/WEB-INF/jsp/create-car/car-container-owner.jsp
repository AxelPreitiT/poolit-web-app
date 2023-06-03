<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="car" type="ar.edu.itba.paw.models.Car"  scope="request"/>

<link href="<c:url value="/resources/css/users/profile-container.css"/>" rel="stylesheet" type="text/css"/>

<div class="user-info-container">
  <c:url value="/cars/${car.carId}" var="updateCarUrl"/>
  <form:form modelAttribute="updateCarForm" action="${updateCarUrl}" method="post" cssClass="form-style">
  <div class="avatar-img">
    <div class="circular--landscape">
      <c:url value="/image/${car.image_id}" var="carImageUrl"/>
      <img class="circular--square" src="${carImageUrl}" alt="<spring:message code="updateCar.image"/>">
    </div>
  </div>
  <h3 id="user-name">"${car.brand}"</h3>
  <div class="row-info">
    <h6><spring:message code="createCar.carInfo"/></h6>
    <h4 class="no-edit"><c:out value="${car.infoCar}"/></h5>
    <form:input path="carInfo" id="carInfo" cssClass="form-control hidden edit"  />
  </div>
  <div class="row-info">
    <h6><spring:message code="createCar.seats"/></h6>
    <h4 class="no-edit"><c:out value="${car.seats}"/></h5>
    <form:input path="seats" cssClass="form-control hidden edit" id="seats" />
  </div>
  <div class="row-info">
    <h6><spring:message code="createCar.plate"/></h6>
    <h4><c:out value="${car.plate}"/></h4>
  </div>
  <div class="row-info">
    <h6><spring:message code="createCar.plate"/></h6>
    <div data-toggle="buttons">
      <label class="btn btn-custom">
        <input type="checkbox" id="air-conditioning" autocomplete="off">
        <spring:message code="createCar.airConditioning"/>
      </label>
      <label class="btn btn-custom">
        <input type="checkbox" id="pet-friendly" autocomplete="off">
        <spring:message code="createCar.petFriendly"/>
      </label>
      <label class="btn btn-custom">
        <input type="checkbox" id="trunk-space" autocomplete="off">
        <spring:message code="createCar.trunkSpace"/>
      </label>
      <label class="btn btn-custom">
        <input type="checkbox" id="music" autocomplete="off">
        <spring:message code="createCar.music"/>
      </label>
    </div>
  </div>

    <jsp:useBean id="rating" type="java.lang.Double" scope="request"/>
    <div class="row-info">
      <h6><spring:message code="user.rating"/></h6>
      <div class="d-flex justify-content-between align-items-center">
        <div class="ratings">
          <c:forEach var="i" begin="1" end="${5}">
            <i class="bi bi-star-fill secondary-color h4"></i>
          </c:forEach>

        </div>
      </div>
    </div>
    <div class="row-info hidden edit">
      <button id="update-car" type="submit" class="btn button-style button-color shadow-btn hidden edit">
          <i class="bi bi-check2 light-text h3"></i>
          <span class="button-text-style light-text h3"><spring:message code="updateCar.save"/></span>
      </button>


    </div>
    <div class="row-info">
        <button id="edit-car" type="button" class="btn button-style button-color shadow-btn no-edit">
              <i class="bi bi-pencil-square light-text h3"></i>
              <span class="button-text-style light-text h3" id="editButton" onclick="toggleEdit()">Editar</span>
          </button>
    </div>
  </form:form>
  <script src="<c:url value="/resources/js/cars/editCar.js"/>" type="application/javascript"></script>


</div>
