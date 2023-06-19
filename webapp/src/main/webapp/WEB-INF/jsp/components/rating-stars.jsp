<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<jsp:useBean id="rating" scope="request" type="java.lang.Number"/>

<div>
    <c:choose>
        <c:when test="${rating == 0}">
            <span class="${param.fontSize} ${param.fontColor} italic-text"><spring:message code="review.none"/></span>
        </c:when>
        <c:otherwise>
            <c:forEach var="i" begin="1" end="${rating}">
                <i class="bi bi-star-fill ${param.fontColor} ${param.fontSize}"></i>
            </c:forEach>
            <c:if test="${rating % 1 >= 0.5}">
                <i class="bi bi-star-half ${param.fontColor} ${param.fontSize}"></i>
                <c:forEach var="i" begin="${rating + 2}" end="5">
                    <i class="bi bi-star ${param.fontColor} ${param.fontSize}"></i>
                </c:forEach>
            </c:if>
            <c:if test="${rating % 1 < 0.5}">
                <c:forEach var="i" begin="${rating + 1}" end="5">
                    <i class="bi bi-star ${param.fontColor} ${param.fontSize}"></i>
                </c:forEach>
            </c:if>
        </c:otherwise>
    </c:choose>
</div>
