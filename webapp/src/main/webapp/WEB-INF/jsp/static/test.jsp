<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Viajes reservados</title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
</head>
<body class="background-color">
    <div id="navbar-container">
        <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    </div>
    <div class="main-container-style container-color">
        <div id="main-header-row">
            <h1 class="secondary-color">Viajes reservados - Pasados</h1>
            <hr class="secondary-color">
        </div>
        <div id="trip-card-list-container">
            <c:forEach items="${trips}" var="trip">
                <c:url value="/trips/${trip.tripId}" var="tripUrl">
                    <c:param name="startDate" value="${trip.startDateString}"/>
                    <c:param name="startTime" value="${trip.startTimeString}"/>
                    <c:param name="endDate" value="${trip.endDateString}"/>
                </c:url>
                <div class="trip-card-row">
                    <c:set var="trip" value="${trip}" scope="request"/>
                    <jsp:include page="/WEB-INF/jsp/components/trip-card.jsp">
                        <jsp:param name="tripUrl" value="${tripUrl}"/>
                    </jsp:include>
                </div>
            </c:forEach>
        </div>
    </div>
</body>
</html>
