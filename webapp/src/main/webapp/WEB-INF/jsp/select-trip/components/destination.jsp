<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="location-data">
  <jsp:useBean id="trip" scope="request" type="ar.edu.itba.paw.models.Trip"/>
  <h3 class="h5-size">
    <c:out value="${trip.destinationCity.name}" escapeXml="true"/></h3>
  <h6 class="fw-light p-size"><c:out value="${trip.destinationAddress}" escapeXml="true "/></h6>
</div>
