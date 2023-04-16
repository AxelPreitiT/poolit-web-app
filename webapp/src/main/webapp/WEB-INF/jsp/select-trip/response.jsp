<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
    <title><spring:message code="selectTrip.title"/></title>
</head>
<body>
<h1><c:out value="${response}" escapeXml="true"/> </h1>
<c:if test="${response}">
    <h1><spring:message code="selectTrip.success"/></h1>
    <p>Origen: <c:out value="${trip.originCity.name}" escapeXml="true"/> en <c:out value="${trip.originAddress}" escapeXml="true"/></p>
    <p>Destino: <c:out value="${trip.destinationCity.name}" escapeXml="true"/> en <c:out value="${trip.destinationAddress}" escapeXml="true"/></p>
    <p>Fecha: <c:out value="${trip.originDateTime}" escapeXml="true"/></p>
<%--    <p>Hora: <c:out value="${trip.time}" escapeXml="true"/></p>--%>
    <p>Plazas: <c:out value="${trip.occupiedSeats}" escapeXml="true"/></p>
    <p>Email: <c:out value="${trip.driver.email}" escapeXml="true"/></p>
    <p>Teléfono: <c:out value="${trip.driver.phone}" escapeXml="true"/></p>
</c:if>
</body>
</html>
