<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
  <title><spring:message code="selectTrip.success.pageTitle"/></title>
  <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.css.jsp" />
  <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.icons.jsp" />
  <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
  <link href="<c:url value="/css/select-trip/success.css"/>" rel="stylesheet">
</head>
<body class="background-bg-color">
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<div class="container-bg-color main-container-style">
  <spring:message code="selectTrip.success.title" var="title"/>
  <jsp:include page="/WEB-INF/jsp/components/success-message.jsp">
    <jsp:param name="title" value="${title}"/>
  </jsp:include>
  <jsp:include page="/WEB-INF/jsp/components/trip-route.jsp"/>
  <hr>
  <div class="info-container">
    <jsp:include page="/WEB-INF/jsp/components/car-info-detail.jsp"/>
    <c:set value="${trip.driver}" var="user" scope="request"/>
    <spring:message code="selectTrip.success.driverData" var="driverData"/>
    <jsp:include page="/WEB-INF/jsp/components/user-info-detail.jsp">
      <jsp:param name="title" value="${driverData}"/>
    </jsp:include>
    <c:set value="${passenger}" var="user" scope="request"/>
    <spring:message code="selectTrip.success.userData" var="userData"/>
    <jsp:include page="/WEB-INF/jsp/components/user-info-detail.jsp">
      <jsp:param name="title" value="${userData}"/>
    </jsp:include>
  </div>
  <div class="button-container">
    <a href="<c:url value="/"/>">
      <button class="btn button-style button-bg-color">
        <span class="light-text h5"><spring:message code="success.btnBack"/></span>
      </button>
    </a>
  </div>
</div>
</body>
</html>
