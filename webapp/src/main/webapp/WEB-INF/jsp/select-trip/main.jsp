<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title><spring:message code="selectTrip.title"/></title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/resources/css/select-trip/select-trip.css"/>" rel="stylesheet" type="text/css"/>
</head>
    <body class="background-color">
        <div id="navbar-container">
            <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
        </div>
        <div class="main-container-style container-color">
            <div id="trip-detail-container">
                <jsp:include page="/WEB-INF/jsp/components/trip-detail.jsp">
                    <jsp:param name="showDriverInfo" value="false"/>
                    <jsp:param name="status" value="driver"/>
                    <jsp:param name="showPassengers" value="false"/>
                </jsp:include>
            </div>
            <div id="footer-container">
                <div id="trip-price-container">
                    <div class="trip-price-row">
                        <div>
                            <span class="h3 text"><spring:message code="selectTrip.price"/></span>
                        </div>
                        <div>
                            <span class="h2 secondary-color"><spring:message code="selectTrip.priceFormat" arguments="${trip.integerQueryTotalPrice},${trip.decimalQueryTotalPrice}"/></span>
                        </div>
                    </div>
                    <div class="trip-price-row items-to-end">
                    <c:choose>
                        <c:when test="${trip.queryIsRecurrent}">
                            <spring:message code="tripInfo.multipleTrips" arguments="${trip.queryTotalTrips}" var="totalTripsString"/>
                            <c:out value="${totalTripsString}"/>
                        </c:when>
                        <c:otherwise>
                            <span class="h6 italic-text"><spring:message code="tripInfo.singleTrip"/></span>
                        </c:otherwise>
                    </c:choose>
                    </div>
                </div>
                <div id="button-container">
                    <c:url value="/trips/${trip.tripId}/join" var="joinUrl"/>
                    <form:form action="${joinUrl}" modelAttribute="selectForm" method="post">
                        <form:input path="startDate" cssClass="hidden-input"/>
                        <form:input path="startTime" cssClass="hidden-input"/>
                        <form:input path="endDate" cssClass="hidden-input"/>
                        <button id="join-button" type="submit" class="btn button-style button-color shadow-btn"
                                <c:if test="${trip.freeSeats == 0}">
                                    <c:out value="disabled"/>
                                </c:if>>
                            <i class="bi bi-check2 light-text h3"></i>
                            <span class="button-text-style light-text h3"><spring:message code="selectTrip.btnJoin"/></span>
                        </button>
                    </form:form>
                    <c:if test="${trip.freeSeats == 0}">
                        <div class="no-seats-left-container">
                            <span class="h6 italic-text"><spring:message code="selectTrip.seats.cero"/></span>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>