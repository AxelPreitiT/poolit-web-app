<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title><spring:message code="searchTrip.title"/></title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/resources/css/search/search.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body class="background-color">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="main-container">
        <div class="row">
            <div class="col-md-4 col-lg-3">
                <c:url value="/search" var="searchUrl"/>
                <jsp:include page="/WEB-INF/jsp/components/search-filters.jsp">
                    <jsp:param name="url" value="${searchUrl}"/>
                </jsp:include>
            </div>
            <div class="col-md-8 col-lg-9">
                <c:choose>
                    <c:when test="${tripsContent.totalCount>0}">
                        <jsp:include page="/WEB-INF/jsp/components/trip-order-by-selector.jsp"/>
                        <c:set value="${tripsContent.elements}" var="trips" scope="request"/>
                        <jsp:include page="/WEB-INF/jsp/components/trip-card-search-list.jsp">
                            <jsp:param name="startDate" value="${searchTripForm.date}"/>
                            <jsp:param name="startTime" value="${searchTripForm.time}"/>
                            <jsp:param name="endDate" value="${searchTripForm.lastDate}"/>
                        </jsp:include>
                        <div id="total-results-row">
                            <span class="italic-text">
                                <spring:message code="searchTrip.foundFormat" arguments="${tripsContent.startNumber+1}, ${tripsContent.endNumber+1}, ${tripsContent.totalCount}"/>
                            </span>
                        </div>
                        <c:url value="" var="baseUrl">
                            <c:forEach var="p" items="${param}">
                                <c:if test="${!(p.key eq 'page')}">
                                    <c:param name="${p.key}" value="${p.value}"/>
                                </c:if>
                            </c:forEach>
                        </c:url>
                        <jsp:include page="/WEB-INF/jsp/components/trip-card-list-pagination.jsp">
                            <jsp:param name="totalPages" value="${tripsContent.totalPages}"/>
                            <jsp:param name="currentPage" value="${tripsContent.currentPage+1}"/>
                            <jsp:param name="baseUrl" value="${baseUrl}"/>
                        </jsp:include>
                    </c:when>
                    <c:otherwise>
                        <div class="no-results-container">
                            <i class="fa-solid fa-car-side secondary-color fa-2xl"></i>
                            <h3><spring:message code="searchTrip.dontFound"/></h3>
                            <h5><spring:message code="searchTrip.tryAgain"/></h5>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</body>
</html>
