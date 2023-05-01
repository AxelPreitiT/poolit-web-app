<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="car" type="ar.edu.itba.paw.models.Car"  scope="request"/>


<link href="<c:url value="/resources/css/users/car-container.css"/>" rel="stylesheet" type="text/css"/>

<div class="car-card">
  <c:url value="/image/${car.image_id}" var="carImageUrl"/>
  <img class="img-car" src="${carImageUrl}">
  <div class="car-desc">
    <h5>${car.infoCar}</h5>
    <h5>Patente: <span class="model-car">${car.plate}</span></h5>
  </div>
</div>
