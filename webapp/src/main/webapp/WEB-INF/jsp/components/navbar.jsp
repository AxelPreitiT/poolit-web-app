<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="navbar-style">
    <div class="flex-row">
        <div class="navbar-logo-style">
            <h1>Poolit</h1>
        </div>
        <ul class="navbar-list-style">
            <li class="navbar-list-item-style">
                <c:url value="/trips/" var="searchTripsUrl" />
                <a class="navbar-link-style" href="${searchTripsUrl}">Buscar viajes</a>
            </li>
            <li class="navbar-list-item-style">
                <c:url value="/create-trip" var="createTripUrl" />
                <a class="navbar-link-style" href="${createTripUrl}">Crear viaje</a>
            </li>
        </ul>
    </div>
    <div class="navbar-profile-container">
        <h2>Profile</h2>
    </div>
</header>

