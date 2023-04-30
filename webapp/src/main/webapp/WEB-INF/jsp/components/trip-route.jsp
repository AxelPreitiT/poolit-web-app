<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="trip" type="ar.edu.itba.paw.models.Trip" scope="request"/>
<link href="<c:url value="/resources/css/components/trip-route.css"/>" rel="stylesheet">

<!-- Bean:
        - Trip: Information about the trip - Trip class
-->

<div class="trip-route">
  <div class="location-container">
    <div class="location-data items-to-end">
      <h3 class="secondary-color title-style"><c:out value="${trip.originCity.name}"/></h3>
      <h5 class="text description-style"><c:out value="${trip.originAddress}"/></h5>
    </div>
    <i class="bi bi-geo-alt secondary-color icon-style"></i>
  </div>
  <div class="location-line">
    <div class="location-line-content">
      <i class="fa-solid fa-car-side fa-bounce secondary-color" id="animated-car"></i>
      <div class="dotted-line"></div>
    </div>
  </div>
  <div class="location-container">
    <i class="bi bi-geo-alt-fill secondary-color icon-style"></i>
    <div class="location-data">
      <h3 class="secondary-color title-style"><c:out value="${trip.destinationCity.name}" escapeXml="true"/></h3>
      <h5 class="text description-style"><c:out value="${trip.destinationAddress}" escapeXml="true "/></h5>
    </div>
  </div>
</div>
