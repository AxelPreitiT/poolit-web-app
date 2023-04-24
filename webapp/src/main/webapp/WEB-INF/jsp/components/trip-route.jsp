<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="trip" type="ar.edu.itba.paw.models.trips.Trip" scope="request"/>
<link href="<c:url value="/css/components/trip-route.css"/>" rel="stylesheet">
<div class="trip-route">
  <div class="location-container">
    <div class="location-data items-to-end">
      <h4 class="text title-style"><c:out value="${trip.originCity.name}" escapeXml="true"/></h4>
      <h6 class="fw-light text description-style"><c:out value="${trip.originAddress}" escapeXml="true "/></h6>
      <h6 class="fw-light text description-style"><c:out value="${trip.startDateString}" escapeXml="true"/> - <c:out value="${trip.startTimeString}" escapeXml="true"/> hs</h6>
    </div>
    <i class="bi bi-geo-alt text icon-style"></i>
  </div>
  <div class="location-line">
    <div class="location-line-content">
      <i class="fa-solid fa-car-side fa-bounce text" id="animated-car"></i>
      <div class="dotted-line"></div>
    </div>
  </div>
  <div class="location-container">
    <i class="bi bi-geo-alt-fill text icon-style"></i>
    <div class="location-data">
      <h4 class="text title-style"><c:out value="${trip.destinationCity.name}" escapeXml="true"/></h4>
      <h6 class="fw-light text description-style"><c:out value="${trip.destinationAddress}" escapeXml="true "/></h6>
    </div>
  </div>
</div>
