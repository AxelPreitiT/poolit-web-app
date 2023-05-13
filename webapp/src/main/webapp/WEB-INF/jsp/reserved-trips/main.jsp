<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- Beans:
        - url: url to be used in the tabs
-->

<html>
<head>
  <title><spring:message code="reservedTrips.title"/></title>
  <jsp:include page="/resources/external-resources.jsp"/>
  <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
</head>
<body class="background-color">
  <div id="navbar-container">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
  </div>
  <div class="main-container-style container-color">
    <div id="main-header-row">
      <h1 class="secondary-color"><spring:message code="reservedTrips.title"/></h1>
      <hr class="secondary-color">
    </div>
    <div id="time-tabs-row">
      <jsp:include page="/WEB-INF/jsp/components/trip-nav-tabs.jsp">
        <jsp:param name="path" value="${url}"/>
        <jsp:param name="springMessagePrefix" value="reservedTrips"/>
      </jsp:include>
    </div>
    <div id="trip-card-date-list-container">
      <jsp:include page="/WEB-INF/jsp/components/trip-card-date-list.jsp">
        <jsp:param name="noTripsTitleCode" value="reservedTrips.notFound.title"/>
        <jsp:param name="noTripsSubtitleCode" value="reservedTrips.notFound.subtitle"/>
      </jsp:include>
    </div>
  </div>
</body>
</html>
