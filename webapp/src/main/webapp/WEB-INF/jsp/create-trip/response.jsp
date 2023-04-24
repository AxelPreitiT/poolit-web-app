<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
  <head>
      <title><spring:message code="createTrip.success"/></title>
  </head>
  <body>
    <h1><spring:message code="createTrip.success"/></h1>
    <p><spring:message code="createTrip.success.description"/></p>
    <p>Id: <c:out value="${trip.tripId}" escapeXml="true"/></p>
    <p>Origen: <c:out value="${trip.originCity.name}" escapeXml="true"/> en <c:out value="${trip.originAddress}" escapeXml="true"/></p>
    <p>Destino: <c:out value="${trip.destinationCity.name}" escapeXml="true"/> en <c:out value="${trip.destinationAddress}" escapeXml="true"/></p>
    <p>Fecha: <c:out value="${trip.startDateString}" escapeXml="true"/>-<c:out value="${trip.startTimeString}" escapeXml="true"/> </p>
    <p>Coche: <c:out value="${trip.car}" escapeXml="true"/></p>
    <p>Plazas: <c:out value="${trip.maxSeats}" escapeXml="true"/></p>
    <p>Email: <c:out value="${trip.driver.email}" escapeXml="true"/></p>
    <p>Teléfono: <c:out value="${trip.driver.phone}" escapeXml="true"/></p>
  </body>
</html>
