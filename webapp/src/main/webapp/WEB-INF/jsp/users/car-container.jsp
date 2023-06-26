<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="car" type="ar.edu.itba.paw.models.Car"  scope="request"/>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<link href="<c:url value="/resources/css/users/car-container.css"/>" rel="stylesheet" type="text/css"/>

<div class="car-card">
  <c:url value="/image/${car.imageId}" var="carImageUrl"/>
  <c:url value="/cars/${car.carId}" var="carUrl"/>
  <a href="${carUrl}">
    <img class="img-car" src="${carImageUrl}">
  </a>
  <div class="car-desc">
    <h5><c:out value="${car.infoCar}"/></h5>
    <spring:message code="profile.plate" arguments="${car.plate}" var="carPlateString"/>
    <h5><c:out value="${carPlateString}"/> </h5>
  </div>
</div>
