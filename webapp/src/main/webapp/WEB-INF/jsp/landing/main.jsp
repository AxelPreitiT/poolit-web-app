<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- Beans:
        - searchUrl: url to search trips
        - trips: list of trips to show
-->

<html>
<head>
    <title><spring:message code="titlePage"/></title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/resources/css/landing/landing.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body class="background-color">
    <div id="navbar-container">
        <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    </div>
    <div id="banner-container">
        <div class="container">
            <div class="row">
                <div class="col-lg-5" id="banner-text-column">
                    <div id="banner-title-container">
                        <span class="light-text title"><spring:message code="landing.travelWith"/>
                            <img src="<c:url value="/resources/images/poolit/poolit.svg"/>" alt="Poolit" id="banner-logo"/>
                            !
                        </span>
                    </div>
                    <span class="light-text subtitle"><spring:message code="landing.with"/><span class="secondary-color"><spring:message code="landing.title"/></span><spring:message code="landing.principalText"/></span>
                    <span class="light-text subtitle"><spring:message code="landing.segundText"/></span>
                </div>
                <div class="col-lg-5 col-xl-4" id="banner-search-column">
                    <c:url value="/search" var="searchUrl"/>
                    <jsp:include page="/WEB-INF/jsp/components/search-filters.jsp">
                        <jsp:param name="url" value="${searchUrl}"/>
                    </jsp:include>
                </div>
            </div>
        </div>
    </div>
    <div id="content-container">
        <div class="main-container-style container-color">
            <sec:authorize access="isAuthenticated()">
                <c:choose>
                    <c:when test="${empty trips}">
                        <div id="tutorial-container">
                            <jsp:include page="/WEB-INF/jsp/landing/tutorial.jsp"/>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div id="trips-container">
                            <h2 class="title secondary-color"><spring:message code="landing.availablesTrips"/></h2>
                            <div id="trip-cards-container" class="container">
                                <div class="row">
                                    <c:forEach items="${trips}" var="trip">
                                        <c:url value="/trips/${trip.tripId}" var="tripUrl">
                                            <c:param name="startDate" value="${trip.startDateString}"/>
                                            <c:param name="startTime" value="${trip.startTimeString}"/>
                                            <c:param name="endDate" value="${trip.endDateString}"/>
                                        </c:url>
                                        <div class="col-sm-12 col-xl-6">
                                            <c:set var="trip" value="${trip}" scope="request"/>
                                            <jsp:include page="/WEB-INF/jsp/components/trip-card.jsp">
                                                <jsp:param name="tripUrl" value="${tripUrl}"/>
                                            </jsp:include>
                                        </div>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </sec:authorize>
            <sec:authorize access="!isAuthenticated()">
                <div id="tutorial-container">
                    <jsp:include page="/WEB-INF/jsp/landing/tutorial.jsp"/>
                </div>
            </sec:authorize>
        </div>
    </div>
</body>
</html>
