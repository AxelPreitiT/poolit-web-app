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
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<div class="main-container">
  <c:url value="/users/profile" var="userProfileUrl"/>
  <jsp:include page="/WEB-INF/jsp/users/profile-container.jsp">
    <jsp:param name="user" value="${user}"/>
  </jsp:include>
  <div class="List-properties-container">
    <c:url value="/trips/reserved" var="reservedTripsUrl"/>
    <c:set var="trips" value="${futureTrips}" scope="request"/>
    <jsp:include page="/WEB-INF/jsp/users/info-container.jsp">
      <jsp:param name="title" value="nextTrips.reserved.title"/>
      <jsp:param name="btndesc" value="profile.nextTrips.btn"/>
      <jsp:param name="trips" value="${futureTrips}"/>
      <jsp:param name="url" value="${reservedTripsUrl}"/>
    </jsp:include>
    <c:url value="/trips/reserved/history" var="reservedTripsHistoryUrl"/>
    <c:set var="trips" value="${pastTrips}" scope="request"/>
    <jsp:include page="/WEB-INF/jsp/users/info-container.jsp">
      <jsp:param name="title" value="historyTrips.reserved.title"/>
      <jsp:param name="btndesc" value="profile.historyTrips.btn"/>
      <jsp:param name="trips" value="${pastTrips}"/>
      <jsp:param name="url" value="${reservedTripsHistoryUrl}"/>
    </jsp:include>
  </div>
</div>
</body>
</html>
