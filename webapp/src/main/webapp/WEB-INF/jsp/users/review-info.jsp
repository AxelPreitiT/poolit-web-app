<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="review" type="ar.edu.itba.paw.models.Review" scope="request"/>
<link href="<c:url value="/resources/css/users/review-info.css"/>" rel="stylesheet" type="text/css"/>

<div class="review-cointainer">
    <div class="rating-container">
        <div class="d-flex justify-content-between align-items-center">
            <div class="ratings">
                <c:forEach var="i" begin="1" end="${review.rating}">
                    <i class="bi bi-star-fill secondary-color h4"></i>
                </c:forEach>
                <c:if test="${review.rating % 1 >= 0.5}">
                    <i class="bi bi-star-half secondary-color h4"></i>
                    <c:forEach var="i" begin="${review.rating + 2}" end="5">
                        <i class="bi bi-star secondary-color h4"></i>
                    </c:forEach>
                </c:if>
                <c:if test="${review.rating % 1 < 0.5}">
                    <c:forEach var="i" begin="${review.rating + 1}" end="5">
                        <i class="bi bi-star secondary-color h4"></i>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </div>
    <div class="review-container">
        <h4>${review.review}</h4>
    </div>
</div>
