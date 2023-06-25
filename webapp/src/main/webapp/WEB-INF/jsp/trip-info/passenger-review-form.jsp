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
    <img src="${passengerImageUrl}" alt="${passengerName}" class="passenger-image">
    <div class="passenger-review-form-title">
      <h3 class="secondary-color"><c:out value="${passengerName}"/></h3>
      <hr class="secondary-color">
    </div>
  </div>
  <div class="passenger-review-form">
    <div>
      <label for="passenger-${passenger.userId}" class="passenger-review-rating-label">
        <strong class="text"><spring:message code="passenger.review.rating.label"/></strong>
      </label>
      <form:select path="rating" cssClass="passenger-review-rating form-select secondary-color" id="passenger-${passenger.userId}">
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
    </div>
    <c:forEach items="${tripReviewCollection.ratingOptions}" var="ratingOption">
      <div
              id="passenger-${passenger.userId}-${ratingOption}-container"
              class="passenger-review-option-container"
              <c:if test="${ratingOption != 3}">
                hidden="hidden"
              </c:if>
      >
        <label for="passenger-${passenger.userId}-${ratingOption}" class="passenger-review-option-label">
          <strong class="text"><spring:message code="passenger.review.option.label"/></strong>
        </label>
        <form:select
                path="option"
                cssClass="passenger-review-option form-select"
                id="passenger-${passenger.userId}-${ratingOption}"
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
      </div>
    </c:forEach>
    <div>
      <label for="passenger-${passenger.userId}-comment" class="passenger-review-comment-label">
        <strong class="text">
          <spring:message code="passenger.review.comment.label"/>
        </strong>
        <span class="italic-text">
          <spring:message code="review.optional"/>
        </span>
      </label>
      <form:textarea path="comment" cssClass="form-control passenger-review-comment" id="passenger-${passenger.userId}-comment"/>
    </div>
  </div>
</div>

<script src="<c:url value="/resources/js/pages/trip-info/passenger-review-form.js"/>"></script>
