<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="<c:url value="/resources/css/users/review-info.css"/>" rel="stylesheet" type="text/css"/>

<div class="review-info-container">
    <div class="review-info-content">
        <div class="review-info-text">
            <div class="review-info-title">
                <h4 class="secondary-color"><spring:message code="${param.type}.review.option.${review.option}"/></h4>
            </div>
            <div class="review-info-body">
                <span class="text review-info-comment"><c:out value="${review.comment}"/></span>
            </div>
        </div>
        <div class="rating-container">
            <div class="d-flex justify-content-between align-items-center">
                <div class="rating-container">
                    <c:set var="rating" value="${review.rating}" scope="request"/>
                    <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp">
                        <jsp:param name="fontSize" value="h4"/>
                        <jsp:param name="fontColor" value="secondary-color"/>
                    </jsp:include>
                </div>
            </div>
        </div>
    </div>
    <div class="date-container">
        <span class="italic-text"><c:out value="${review.formattedDate}"/></span>
    </div>
</div>
