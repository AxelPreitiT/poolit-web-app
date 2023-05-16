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
    <jsp:include page="/WEB-INF/jsp/users/public-profile-container.jsp">
      <jsp:param name="user" value="${user}"/>
      <jsp:param name="role" value="DRIVER"/>
      <jsp:param name="path" value="${userProfileUrl}"/>
      <jsp:param name="rating" value="${rating}"/>
      <jsp:param name="countTrips" value="${countTrips}"/>
    </jsp:include>
    <div class="List-properties-container">
      <c:set var="reviews" value="${reviews}" scope="request"/>
      <jsp:include page="/WEB-INF/jsp/users/review-container.jsp">
        <jsp:param name="reviews" value="${reviews}"/>
        <jsp:param name="role" value="${user.role}"/>
      </jsp:include>
    </div>
  </div>
</body>
</html>
