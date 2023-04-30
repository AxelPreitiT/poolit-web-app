<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link href="<c:url value="/resources/css/components/trip-detail.css"/>" rel="stylesheet" type="text/css"/>

<div id="main-header-row">
  <h1 class="secondary-color">Detalles del viaje</h1>
  <hr class="secondary-color">
</div>
<div id="trip-route-container">
  <jsp:include page="/WEB-INF/jsp/components/trip-route.jsp"/>
</div>
<div id="trip-info-container">
  <div class="container">
    <div class="row">
      <div class="col-sm-6 col-md-5 col-lg-4">
        <div id="trip-info-text-container">
          <jsp:include page="/WEB-INF/jsp/components/trip-detail-card.jsp"/>
        </div>
      </div>
      <div class="col-sm-6 col-md-5 col-lg-4">
        <div id="car-info-image">
          <div class="placeholder-image-color">
            <i class="bi bi-car-front-fill placeholder-image-icon-color"></i>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>


