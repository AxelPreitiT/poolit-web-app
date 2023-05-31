<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="car" type="ar.edu.itba.paw.models.Car"  scope="request"/>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<link href="<c:url value="/resources/css/users/car-container.css"/>" rel="stylesheet" type="text/css"/>

<div class="car-card">
  <c:url value="/image/${car.image_id}" var="carImageUrl"/>
  <img class="img-car" src="${carImageUrl}">
  <div class="car-desc">
    <h5><c:out value="${car.infoCar}"/></h5>
    <h5><spring:message code="profile.plate" arguments="${car.plate}"/></h5>
    <c:forEach items="${car.features}" var="feature">
      <h5><c:out value="${feature}"/></h5>
    </c:forEach>
  </div>
</div>
