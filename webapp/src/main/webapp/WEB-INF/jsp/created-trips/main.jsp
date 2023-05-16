<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!-- Beans:
        - tripDeleted: boolean that indicates if a trip was deleted before rendering this page
        - url: url to be used in the tabs
-->

<html>
<head>
  <title><spring:message code="createdTrips.title"/></title>
  <jsp:include page="/resources/external-resources.jsp"/>
  <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
</head>
<body class="background-color">
  <div id="navbar-container">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
  </div>
  <div class="main-container-style container-color">
    <div id="main-header-row">
      <h1 class="secondary-color"><spring:message code="createdTrips.title"/></h1>
      <hr class="secondary-color">
    </div>
    <div id="time-tabs-row">
      <jsp:include page="/WEB-INF/jsp/components/trip-nav-tabs.jsp">
        <jsp:param name="path" value="${url}"/>
        <jsp:param name="springMessagePrefix" value="createdTrips"/>
      </jsp:include>
    </div>
    <div id="trip-card-date-list-container">
      <c:if test="${(empty param.time) || (param.time eq 'future')}">
        <c:set var="allowDelete" value="true" scope="request"/>
      </c:if>
      <jsp:include page="/WEB-INF/jsp/components/trip-card-date-list.jsp">
        <jsp:param name="noTripsTitleCode" value="createdTrips.notFound.title"/>
        <jsp:param name="noTripsSubtitleCode" value="createdTrips.notFound.subtitle"/>
      </jsp:include>
    </div>
  </div>
  <c:if test="${!(empty tripDeleted) && tripDeleted}">
    <div id="toast-container">
      <jsp:include page="/WEB-INF/jsp/components/success-toast.jsp">
        <jsp:param name="title" value="selectTrip.delete.Title"/>
        <jsp:param name="message" value="selectTrip.deleteTitle"/>
      </jsp:include>
    </div>
  </c:if>
</body>
</html>