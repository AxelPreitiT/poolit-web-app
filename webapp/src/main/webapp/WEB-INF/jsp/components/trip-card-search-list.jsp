<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link href="<c:url value="/resources/css/components/trip-card-search-list.css"/>" rel="stylesheet" type="text/css"/>

<!-- Beans:
    - trips: The list of trips to display
-->

<jsp:useBean id="trips" type="java.util.List" scope="request"/>

<div id="results-list" class="container">
    <div class="row">
        <c:forEach items="${trips}" var="trip">
            <c:url value="/trips/${trip.tripId}" var="tripUrl">
                <c:param name="startDate" value="${param.startDate}"/>
                <c:param name="startTime" value="${param.startTime}"/>
                <c:param name="endDate" value="${param.endDate}"/>
            </c:url>
            <div class="col-xl-6 col-sm-12">
                <c:set var="trip" value="${trip}" scope="request"/>
                <jsp:include page="/WEB-INF/jsp/components/trip-card.jsp">
                    <jsp:param name="tripUrl" value="${tripUrl}"/>
                </jsp:include>
            </div>
        </c:forEach>
    </div>
</div>
