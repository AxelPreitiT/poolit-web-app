<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" href="<c:url value="/resources/css/trip-info/car-review-form.css"/>" type="text/css">

<jsp:useBean id="car" type="ar.edu.itba.paw.models.Car" scope="request" />
<jsp:useBean id="tripReviewCollection" scope="request" type="ar.edu.itba.paw.models.reviews.TripReviewCollection" />
<jsp:useBean id="carReviewForm" scope="request" type="ar.edu.itba.paw.webapp.form.CarReviewForm" />

<div id="car-review-form-container">
    <c:url value="/image/${car.imageId}" var="carImageUrl"/>
    <div id="car-review-form-header">
        <img src="${carImageUrl}" alt="${car.plate}" id="car-image"/>
        <div id="car-review-form-title">
            <h3 class="secondary-color">${car.infoCar}</h3>
            <hr class="secondary-color">
        </div>
    </div>
    <div id="car-review-form">
        <div>
            <label for="car-review-rating" class="car-review-rating-label">
                <strong class="text"><spring:message code="car.review.rating.label"/></strong>
            </label>
            <form:select path="rating" cssClass="car-review-rating-select form-select secondary-color" id="car-review-rating">
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
                    id="car-${ratingOption}-container"
                    class="car-review-option-container"
                    <c:if test="${ratingOption != 3}">
                        hidden="hidden"
                    </c:if>
            >
                <label for="car-${ratingOption}" class="car-review-option-label">
                    <strong class="text"><spring:message code="car.review.option.label"/></strong>
                </label>
                <form:select
                        path="option"
                        cssClass="car-review-option form-select"
                        id="car-${ratingOption}"
                        disabled="${ratingOption != 3}"
                >
                    <c:forEach items="${tripReviewCollection.getCarReviewOptionsByRating(ratingOption)}" var="option">
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
            <label for="car-review-comment" class="car-review-comment-label">
                <strong class="text">
                    <spring:message code="car.review.comment.label"/>
                </strong>
                <span class="italic-text">
                  <spring:message code="review.optional"/>
                </span>
            </label>
            <form:textarea path="comment" cssClass="form-control" id="car-review-comment"/>
        </div>
    </div>
</div>

<script src="<c:url value="/resources/js/pages/trip-info/car-review-form.js"/>"></script>
