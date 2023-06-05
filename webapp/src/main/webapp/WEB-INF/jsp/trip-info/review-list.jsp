<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" href="<c:url value="/resources/css/trip-info/review-list.css"/>" type="text/css">

<jsp:useBean id="tripReviewCollection" scope="request" type="ar.edu.itba.paw.models.reviews.TripReviewCollection" />
<jsp:useBean id="trip" scope="request" type="ar.edu.itba.paw.models.trips.Trip" />

<div id="review-list-container">
  <c:if test="${tripReviewCollection.canReviewDriver}">

  </c:if>
  <c:if test="${tripReviewCollection.canReviewCar}">

  </c:if>
  <c:if test="${tripReviewCollection.canReviewPassengers}">
    <div class="review-list-header">
      <i class="bi bi-people-fill italic-text h3"></i>
      <h3 class="italic-text"><spring:message code="tripDetails.passengers"/></h3>
    </div>
    <div class="review-list-content">
      <c:forEach items="${tripReviewCollection.passengers}" var="passenger">
        <div class="review-list-item">
          <c:url value="/image/${passenger.userImageId}" var="passengerImageUrl"/>
          <button class="btn shadow-btn button-style button-color" data-bs-target="#review-passenger-${passenger.userId}" data-bs-toggle="modal">
            <img src="${passengerImageUrl}" alt="passenger image" class="passenger-image">
            <div class="review-list-item-text">
              <spring:message code="user.nameFormat" var="passengerName" arguments="${passenger.name}, ${passenger.surname}"/>
              <span class="light-text h4"><c:out value="${passengerName}"/></span>
              <c:if test="${trip.recurrent}">
                <div class="review-list-item-date">
                  <i class="bi bi-calendar light-text"></i>
                  <span class="light-text">
                    <spring:message code="format.dates" var="passengerDate" arguments="${passenger.startDateString}, ${passenger.endDateString}"/>
                    <c:out value="${passengerDate}"/>
                  </span>
                </div>
              </c:if>
            </div>
          </button>
        </div>
      </c:forEach>
    </div>
  </c:if>
</div>
