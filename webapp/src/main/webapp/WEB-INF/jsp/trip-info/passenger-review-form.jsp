<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" href="<c:url value="/resources/css/trip-info/passenger-review-form.css"/>" type="text/css">

<jsp:useBean id="passenger" type="ar.edu.itba.paw.models.Passenger" scope="request" />
<jsp:useBean id="tripReviewCollection" scope="request" type="ar.edu.itba.paw.models.reviews.TripReviewCollection" />
<jsp:useBean id="passengerReviewForm" scope="request" type="ar.edu.itba.paw.webapp.form.PassengerReviewForm" />

<div class="passenger-review-form-container">
  <c:url value="/image/${passenger.user.userImageId}" var="passengerImageUrl"/>
  <spring:message code="user.nameFormat" var="passengerName" arguments="${passenger.name}, ${passenger.surname}"/>
  <div class="passenger-review-form-header">
    <img src="${passengerImageUrl}" alt="passenger image" class="passenger-image">
    <div class="passenger-review-form-title">
      <h3 class="secondary-color">${passengerName}</h3>
      <hr class="secondary-color">
    </div>
  </div>
  <c:url value="/reviews/trip/${passenger.trip.tripId}/passengers/${passenger.user.userId}" var="passengerReviewUrl"/>
  <div class="passenger-review-form">
    <form:form modelAttribute="passengerReviewForm" method="post" action="${passengerReviewUrl}">
      <form:select path="rating" class="passenger-review-rating" id="${passenger.userId}">
        <c:forEach items="${tripReviewCollection.ratingOptions}" var="ratingOption">
          <c:choose>
            <c:when test="${ratingOption == 3}">
              <form:option
                      value="${ratingOption}"
                      label="${tripReviewCollection.getRatingOptionLabel(ratingOption)}"
                      selected="selected"
                />
            </c:when>
            <c:otherwise>
                <form:option
                        value="${ratingOption}"
                        label="${tripReviewCollection.getRatingOptionLabel(ratingOption)}"
                />
            </c:otherwise>
          </c:choose>
        </c:forEach>
      </form:select>
      <c:forEach items="${tripReviewCollection.ratingOptions}" var="ratingOption">
        <form:select
                path="option"
                class="passenger-review-option"
                id="${passenger.userId}-${ratingOption}"
                disabled="${ratingOption != 3}"
        >
          <c:forEach items="${tripReviewCollection.getPassengerReviewOptionsByRating(ratingOption)}" var="option">
            <spring:message var="label" code="${option.springMessageCode}"/>
            <form:option
                    value="${option.name}"
                    label="${label}"
            />
          </c:forEach>
        </form:select>
      </c:forEach>
      <form:input path="comment"/>
    </form:form>
  </div>
</div>

<script src="<c:url value="/resources/js/pages/trip-info/passenger-review-form.js"/>"></script>
