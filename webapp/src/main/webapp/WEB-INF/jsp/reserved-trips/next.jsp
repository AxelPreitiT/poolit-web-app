<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
  <title><spring:message code="nextTrips.reserved.title"/></title>
  <jsp:include page="/resources/external-resources.jsp"/>
  <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
</head>
<body class="background-color">
  <div id="navbar-container">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
  </div>
  <div class="main-container-style container-color">
    <div id="main-header-row">
      <h1 class="secondary-color"><spring:message code="nextTrips.reserved.titleView"/></h1>
      <hr class="secondary-color">
    </div>
    <div id="trip-card-date-list-container">
      <c:set var="allowCancel" value="true" scope="request"/>
      <jsp:include page="/WEB-INF/jsp/components/trip-card-date-list.jsp"/>
    </div>
  </div>
  <c:if test="${!(empty tripCancelled) && tripCancelled}">
    <div id="toast-container">
      <jsp:include page="/WEB-INF/jsp/components/success-toast.jsp">
        <jsp:param name="title" value="deleteTrip.success.toast.title"/>
        <jsp:param name="message" value="deleteTrip.success.toast.message"/>
      </jsp:include>
    </div>
  </c:if>
</body>
</html>
