<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" href="<c:url value="/resources/css/trip-info/review-list.css"/>" type="text/css">

<jsp:useBean id="tripReviewCollection" scope="request" type="ar.edu.itba.paw.models.reviews.TripReviewCollection" />
<jsp:useBean id="trip" scope="request" type="ar.edu.itba.paw.models.trips.Trip" />

<div id="review-list-container">
  <c:if test="${tripReviewCollection.canReviewDriver}">
    <div class="list-header">
      <i class="bi bi-person-fill text h3"></i>
      <h3 class="text"><spring:message code="tripDetails.driver"/></h3>
    </div>
    <div class="list-content">
      <c:set var="driverReviewItem" value="${tripReviewCollection.driver}"/>
      <c:set var="driver" value="${driverReviewItem.item}"/>
      <c:set var="reviewState" value="${driverReviewItem.state}"/>
      <div class="list-item">
        <c:url value="/image/${driver.userImageId}" var="driverImageUrl"/>
        <button
            class="btn shadow-btn button-style <c:choose><c:when test="${driverReviewItem.pending}">button-color</c:when><c:when test="${driverReviewItem.done}">success-bg-color</c:when><c:when test="${driverReviewItem.disabled}">danger-bg-color</c:when></c:choose>"
            data-bs-target="#review-driver"
            data-bs-toggle="modal"
            <c:if test="${!driverReviewItem.pending}">
              disabled
            </c:if>>
          <img src="${driverImageUrl}" alt="driver image" class="image driver-image">
          <div class="list-item-text">
            <spring:message code="user.nameFormat" var="driverName" arguments="${driver.name}, ${driver.surname}"/>
            <span class="light-text h4"><c:out value="${driverName}"/></span>
          </div>
        </button>
        <c:if test="${!driverReviewItem.pending}">
          <p class="italic-text disabled-text">
            <spring:message code="driver.review.state.${reviewState}"/>
          </p>
        </c:if>
      </div>
    </div>
  </c:if>
  <c:if test="${tripReviewCollection.canReviewCar}">
    <div class="list-header">
      <i class="bi bi-car-front-fill text h3"></i>
      <h3 class="text"><spring:message code="tripDetails.car"/></h3>
    </div>
    <div class="list-content">
      <c:set var="carReviewItem" value="${tripReviewCollection.car}"/>
      <c:set var="car" value="${carReviewItem.item}"/>
      <c:set var="reviewState" value="${carReviewItem.state}"/>
      <div class="list-item">
        <c:url value="/image/${trip.car.image_id}" var="carImageUrl"/>
        <button
                class="btn shadow-btn button-style <c:choose><c:when test="${carReviewItem.pending}">button-color</c:when><c:when test="${carReviewItem.done}">success-bg-color</c:when><c:when test="${carReviewItem.disabled}">danger-bg-color</c:when></c:choose>"
                data-bs-target="#review-car"
                data-bs-toggle="modal"
                <c:if test="${!carReviewItem.pending}">
                  disabled
                </c:if>>
          <img src="${carImageUrl}" alt="car image" class="image car-image">
          <div class="list-item-text">
            <span class="light-text h4"><c:out value="${car.infoCar}"/></span>
          </div>
        </button>
        <c:if test="${!carReviewItem.pending}">
          <p class="italic-text disabled-text">
            <spring:message code="car.review.state.${reviewState}"/>
          </p>
        </c:if>
      </div>
    </div>
  </c:if>
  <c:if test="${tripReviewCollection.canReviewPassengers}">
    <div class="list-header">
      <i class="bi bi-people-fill text h3"></i>
      <h3 class="text"><spring:message code="tripDetails.passengers"/></h3>
    </div>
    <div class="list-content">
      <c:forEach items="${tripReviewCollection.passengers}" var="passengerReviewItem">
        <c:set var="passenger" value="${passengerReviewItem.item}"/>
        <c:set var="reviewState" value="${passengerReviewItem.state}"/>
        <div class="list-item">
          <c:url value="/image/${passenger.userImageId}" var="passengerImageUrl"/>
          <button
                  class="btn shadow-btn button-style <c:choose><c:when test="${passengerReviewItem.pending}">button-color</c:when><c:when test="${passengerReviewItem.done}">success-bg-color</c:when><c:when test="${passengerReviewItem.disabled}">danger-bg-color</c:when></c:choose>"
                  data-bs-target="#review-passenger-${passenger.userId}"
                  data-bs-toggle="modal"
                  <c:if test="${!passengerReviewItem.pending}">
                    disabled
                  </c:if>>
            <img src="${passengerImageUrl}" alt="passenger image" class="image passenger-image">
            <div class="list-item-text">
              <spring:message code="user.nameFormat" var="passengerName" arguments="${passenger.name}, ${passenger.surname}"/>
              <span class="light-text h4"><c:out value="${passengerName}"/></span>
              <c:if test="${trip.recurrent}">
                <div class="list-item-date">
                  <i class="bi bi-calendar light-text"></i>
                  <span class="light-text">
                    <spring:message code="format.dates" var="passengerDate" arguments="${passenger.startDateString}, ${passenger.endDateString}"/>
                    <c:out value="${passengerDate}"/>
                  </span>
                </div>
              </c:if>
            </div>
          </button>
          <c:if test="${!passengerReviewItem.pending}">
            <p class="italic-text disabled-text">
              <spring:message code="passenger.review.state.${reviewState}"/>
            </p>
          </c:if>
        </div>
      </c:forEach>
    </div>
  </c:if>
</div>
