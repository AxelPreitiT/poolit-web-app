<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
    <title><spring:message code="review.title"/></title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/resources/css/users/profile.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body class="background-color">
    <div id="navbar-container">
        <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    </div>
    <div class="main-container">
        <div id="profile-container">
            <jsp:include page="/WEB-INF/jsp/users/public-profile-container.jsp">
                <jsp:param name="hasBeenRatedAsDriver" value="${hasReviewsAsDriver}"/>
                <jsp:param name="hasBeenRatedAsPassenger" value="${reviewsAsPassengerPaged.totalPages > 0}"/>
            </jsp:include>
            <c:if test="${hasReviewsAsDriver}">
                <a id="driver-reviews-button" href="<c:url value="/reviews/drivers/${user.userId}"/>">
                    <button class="btn button-style shadow-btn button-color">
                        <span class="light-text h5"><spring:message code="driver.review.title"/></span>
                    </button>
                </a>
            </c:if>
        </div>
        <div class="list-properties-container">
            <c:set var="reviews" value="${reviewsAsPassengerPaged.elements}" scope="request"/>
            <jsp:include page="/WEB-INF/jsp/users/review-container.jsp">
                <jsp:param name="type" value="passenger"/>
            </jsp:include>
            <c:if test="${reviewsAsPassengerPaged.moreThanOnePage}">
                <c:url value="" var="baseUrl">
                    <c:forEach var="p" items="${param}">
                        <c:if test="${!(p.key eq 'page')}">
                            <c:param name="${p.key}" value="${p.value}"/>
                        </c:if>
                    </c:forEach>
                </c:url>
                <jsp:include page="/WEB-INF/jsp/components/pagination.jsp">
                    <jsp:param name="currentPage" value="${reviewsAsPassengerPaged.currentPage+1}"/>
                    <jsp:param name="totalPages" value="${reviewsAsPassengerPaged.totalPages}"/>
                    <jsp:param name="baseUrl" value="${baseUrl}"/>
                </jsp:include>
            </c:if>
        </div>
    </div>
</body>
</html>
