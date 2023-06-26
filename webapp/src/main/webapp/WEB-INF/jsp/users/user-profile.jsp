<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <jsp:include page="/WEB-INF/jsp/users/profile-container.jsp">
      <jsp:param name="hasBeenRatedAsDriver" value="${false}"/>
      <jsp:param name="hasBeenRatedAsPassenger" value="${!(empty reviewsAsPassenger)}"/>
      <jsp:param name="formHasErrors" value="${formHasErrors}"/>
    </jsp:include>
    <div class="list-properties-container">
      <c:set var="reviews" value="${reviewsAsPassenger}" scope="request"/>
      <c:url value="/reviews/passengers/${user.userId}" var="reviewsUrl"/>
      <jsp:include page="/WEB-INF/jsp/users/review-container.jsp">
        <jsp:param name="type" value="passenger"/>
        <jsp:param name="url" value="${reviewsUrl}"/>
      </jsp:include>
      <c:url value="/trips/reserved" var="reservedTripsUrl"/>
      <c:set var="trips" value="${futureTripsAsPassenger}" scope="request"/>
      <jsp:include page="/WEB-INF/jsp/users/info-container.jsp">
        <jsp:param name="title" value="nextTrips.reserved.title"/>
        <jsp:param name="btndesc" value="profile.nextTrips.btn"/>
        <jsp:param name="url" value="${reservedTripsUrl}"/>
      </jsp:include>
      <c:url value="/trips/reserved" var="reservedTripsHistoryUrl">
        <c:param name="time" value="past"/>
      </c:url>
      <c:set var="trips" value="${pastTripsAsPassenger}" scope="request"/>
      <jsp:include page="/WEB-INF/jsp/users/info-container.jsp">
        <jsp:param name="title" value="historyTrips.reserved.title"/>
        <jsp:param name="btndesc" value="profile.historyTrips.btn"/>
        <jsp:param name="url" value="${reservedTripsHistoryUrl}"/>
      </jsp:include>
    </div>
  </div>
</body>
</html>
