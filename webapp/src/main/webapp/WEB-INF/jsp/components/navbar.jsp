<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<header class="navbar-style">
    <div class="flex-row">
        <div class="navbar-logo-style">
            <h1 class="navbar-logo-style"><spring:message code="navbar.poolit"/></h1>
        </div>
        <div class="navbar-list-style">
            <c:url value="/trips/" var="searchTripsUrl" />
            <a class="navbar-link-style h4" href="${searchTripsUrl}"><spring:message code="navbar.searchTrip"/></a>

            <c:url value="/trips/create" var="createTripUrl" />
            <a class="navbar-link-style h4" href="${createTripUrl}"><spring:message code="navbar.createTrip"/></a>
        </div>
    </div>
<%--<div class="navbar-profile-container">
           <h2>Profile</h2>
    </div>--%>
</header>

