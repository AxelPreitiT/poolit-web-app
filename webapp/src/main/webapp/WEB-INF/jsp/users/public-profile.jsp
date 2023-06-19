<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
  <title><spring:message code="profile.title"/></title>
  <jsp:include page="/resources/external-resources.jsp"/>
  <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
  <link href="<c:url value="/resources/css/users/profile.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body class="background-color">
  <div id="navbar-container">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
  </div>
  <div class="main-container">
    <jsp:include page="/WEB-INF/jsp/users/public-profile-container.jsp">
      <jsp:param name="hasBeenRatedAsDriver" value="${!(empty reviewsAsDriver)}"/>
      <jsp:param name="hasBeenRatedAsPassenger" value="${!(empty reviewsAsPassenger)}"/>
      <jsp:param name="isBlocked" value="${isBlocked}"/>
    </jsp:include>
    <div class="list-properties-container">
      <c:set var="reviews" value="${reviewsAsDriver}" scope="request"/>
      <c:url value="/reviews/drivers/${user.userId}" var="driverReviewsUrl"/>
      <jsp:include page="/WEB-INF/jsp/users/review-container.jsp">
        <jsp:param name="type" value="driver"/>
        <jsp:param name="url" value="${driverReviewsUrl}"/>
      </jsp:include>
      <c:set var="reviews" value="${reviewsAsPassenger}" scope="request"/>
      <c:url value="/reviews/passengers/${user.userId}" var="passengerReviewsUrl"/>
      <jsp:include page="/WEB-INF/jsp/users/review-container.jsp">
        <jsp:param name="type" value="passenger"/>
        <jsp:param name="url" value="${passengerReviewsUrl}"/>
      </jsp:include>
    </div>
  </div>
</body>
</html>
