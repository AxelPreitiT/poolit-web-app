<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Confirmación de reserva de viaje</title>
    <link href="<c:url value="/css/main.css"/>" rel="stylesheet">
</head>
<body>
    <div class="email-container">
        <div class="logo">
            <img src="https://picsum.photos/100/100" alt="Logo de Mi Proyecto">
        </div>
        <h1 class="text-center mb-4">¡Tienes un nuevo pasajero!</h1>
        <p>Hola <c:out value="${trip.driver.email}" escapeXml="true"/>,</p>
        <p>Te escribimos para avisarte que [usuario pasajero] se unio a tu  viaje del <c:out value="${trip.date}" escapeXml="true"/>. Aquí está la información del viaje y del usuario:</p>
        <ul>
            <li>Origen:  <c:out value="${trip.originCity.name}" escapeXml="true"/> en <c:out value="${trip.originAddress}" escapeXml="true"/></li>
            <li>Destino:   <c:out value="${trip.destinationCity.name}" escapeXml="true"/> en <c:out value="${trip.destinationAddress}" escapeXml="true"/></li>
            <li>Horario de salida:  <c:out value="${trip.time}" escapeXml="true"/></li>
            <li>Telefono del pasajero: [Telefono del pasajero]</li>
        </ul>
        <div class="btn-container">
            <a href="#" class="btn">Confirmar pasajero</a>
            <a href="#" class="btn">Cancelar pasajero</a>
        </div>
    </div>
</body>
</html>