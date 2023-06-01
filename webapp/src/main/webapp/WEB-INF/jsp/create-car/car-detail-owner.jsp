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
    <c:url value="/cars/${car.carId}" var="carUrl"/>
    <jsp:include page="/WEB-INF/jsp/create-car/car-container-owner.jsp">
      <jsp:param name="car" value="${car}"/>
      <jsp:param name="path" value="${carUrl}"/>
      <jsp:param name="rating" value="${rating}"/>
    </jsp:include>
  </div>
</body>
</html>
