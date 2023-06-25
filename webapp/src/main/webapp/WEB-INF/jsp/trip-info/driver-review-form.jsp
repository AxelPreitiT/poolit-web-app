<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" href="<c:url value="/resources/css/trip-info/driver-review-form.css"/>" type="text/css">

<jsp:useBean id="driver" type="ar.edu.itba.paw.models.User" scope="request" />
<jsp:useBean id="tripReviewCollection" scope="request" type="ar.edu.itba.paw.models.reviews.TripReviewCollection" />
<jsp:useBean id="driverReviewForm" scope="request" type="ar.edu.itba.paw.webapp.form.DriverReviewForm" />

<div id="driver-review-form-container">
    <c:url value="/image/${driver.userImageId}" var="driverImageUrl"/>
    <spring:message code="user.nameFormat" var="driverName" arguments="${driver.name}, ${driver.surname}"/>
    <div id="driver-review-form-header">
        <img src="${driverImageUrl}" alt="${driverName}" id="driver-image"/>
        <div id="driver-review-form-title">
            <h3 class="secondary-color"><c:out value="${driverName}"/></h3>
            <hr class="secondary-color">
        </div>
    </div>
    <div id="driver-review-form">
        <div>
            <label for="driver-review-rating" class="driver-review-rating-label">
                <strong class="text"><spring:message code="driver.review.rating.label"/></strong>
            </label>
            <form:select path="rating" cssClass="driver-review-rating-select form-select secondary-color" id="driver-review-rating">
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
                    id="driver-${ratingOption}-container"
                    class="driver-review-option-container"
                    <c:if test="${ratingOption != 3}">
                        hidden="hidden"
                    </c:if>
            >
                <label for="driver-${ratingOption}" class="driver-review-option-label">
                    <strong class="text"><spring:message code="driver.review.option.label"/></strong>
                </label>
                <form:select
                        path="option"
                        cssClass="driver-review-option form-select"
                        id="driver-${ratingOption}"
                        disabled="${ratingOption != 3}"
                >
                    <c:forEach items="${tripReviewCollection.getDriverReviewOptionsByRating(ratingOption)}" var="option">
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
            <label for="driver-review-comment" class="driver-review-comment-label">
                <strong class="text">
                    <spring:message code="driver.review.comment.label"/>
                </strong>
                <span class="italic-text">
                  <spring:message code="review.optional"/>
                </span>
            </label>
            <form:textarea path="comment" cssClass="form-control" id="driver-review-comment"/>
        </div>
    </div>
</div>

<script src="<c:url value="/resources/js/pages/trip-info/driver-review-form.js"/>"></script>
