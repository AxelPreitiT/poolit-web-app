<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Profile</title>
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
    <jsp:param name="role" value="DRIVER"/>
    <jsp:param name="path" value="${userProfileUrl}"/>
  </jsp:include>
  <div class="List-properties-container">
    <c:url value="/users/reserved" var="reservedTripsUrl"/>
    <jsp:include page="/WEB-INF/jsp/users/info-container.jsp">
      <jsp:param name="title" value="Proximos viajes"/>
      <jsp:param name="btndesc" value="Ver todos los proximos viajes"/>
      <jsp:param name="trips" value="${trips}"/>
      <jsp:param name="url" value="${reservedTripsUrl}"/>
    </jsp:include>
    <c:url value="/users/reserved/history" var="reservedTripsHistoryUrl"/>
    <jsp:include page="/WEB-INF/jsp/users/info-container.jsp">
      <jsp:param name="title" value="Viajes realizados"/>
      <jsp:param name="btndesc" value="Ver todos los viajes realizados"/>
      <jsp:param name="trips" value="${trips}"/>
      <jsp:param name="url" value="${reservedTripsHistoryUrl}"/>
    </jsp:include>
  </div>
</div>
</body>
</html>
