<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="car" type="ar.edu.itba.paw.models.Car" scope="request"/>

<link href="<c:url value="/resources/css/users/profile-container.css"/>" rel="stylesheet" type="text/css"/>

<div class="user-info-container">
  <div class="avatar-img">
    <div class="circular--landscape">
      <c:url value="/image/${car.image_id}" var="carImageUrl"/>
      <img class="circular--square" src="${carImageUrl}" alt="<spring:message code="updateCar.image"/>">
    </div>
  </div>
  <h3 id="user-name"><c:out value="${car.brand}"/></h3>
  <div class="row-info">
    <h6><spring:message code="createCar.carInfo"/></h6>
    <h4><c:out value="${car.infoCar}"/></h4>
  </div>
  <div class="row-info">
    <h6><spring:message code="createCar.seats"/></h6>
    <h4><c:out value="${car.seats}"/></h4>
  </div>
  <div class="row-info">
    <h6><spring:message code="createCar.plate"/></h6>
    <h4><c:out value="${car.plate}"/></h4>
  </div>
  <div class="row-info">
    <div class="no-edit">
        <h6><spring:message code="updateCar.features"/></h6>
        <c:forEach items="${car.features}" var="feature">
            <label class="btn btn-custom">
              <checkbox value="${feature}"></checkbox>
            <spring:message code="${feature.toString()}"/>
            </label>
        </c:forEach>
    </div>
  </div>

    <jsp:useBean id="rating" type="java.lang.Double" scope="request"/>
    <div class="row-info">
      <h6><spring:message code="user.rating"/></h6>
      <div class="d-flex justify-content-between align-items-center">
        <div class="ratings">
          <c:set var="rating" value="${rating}" scope="request"/>
          <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp"/>
        </div>
      </div>
    </div>
</div>
