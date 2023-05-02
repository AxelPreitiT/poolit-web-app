<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="trips" type="ar.edu.itba.paw.models.PagedContent" scope="request"/>

<!-- Beans:
        - trips: List of trips to be displayed
-->

<link href="<c:url value="/resources/css/components/trip-card-date-list.css"/>" rel="stylesheet"/>

<div id="trip-card-date-list">
  <div id="trip-card-list-container">
    <c:forEach items="${trips.elements}" var="trip">
      <c:url value="/trips/${trip.tripId}" var="tripUrl">
        <c:param name="startDate" value="${trip.startDateString}"/>
        <c:param name="startTime" value="${trip.startTimeString}"/>
        <c:param name="endDate" value="${trip.endDateString}"/>
      </c:url>
      <div class="trip-card-row">
        <div class="trip-card-row-header">
          <i class="bi bi-calendar secondary-color h3"></i>
          <c:choose>
            <c:when test="${trip.recurrent}">
              <span class="secondary-color h3"><spring:message code="tripDetails.card.formateDateRecurrent" arguments="${trip.startDateString}, ${trip.endDateString}"/></span>
            </c:when>
            <c:otherwise>
              <span class="secondary-color h3"><c:out value="${trip.startDateString}"/></span>
            </c:otherwise>
          </c:choose>
        </div>
        <div class="trip-car-row-body">
          <c:set var="trip" value="${trip}" scope="request"/>
          <jsp:include page="/WEB-INF/jsp/components/trip-card.jsp">
            <jsp:param name="tripUrl" value="${tripUrl}"/>
          </jsp:include>
        </div>
      </div>
    </c:forEach>
  </div>
  <c:url value="" var="baseUrl">
    <c:forEach var="p" items="${param}">
      <c:if test="${!(p.key eq 'page')}">
        <c:param name="${p.key}" value="${p.value}"/>
      </c:if>
    </c:forEach>
  </c:url>
  <div id="list-pagination">
    <jsp:include page="/WEB-INF/jsp/components/trip-card-list-pagination.jsp">
      <jsp:param name="currentPage" value="${trips.currentPage+1}"/>
      <jsp:param name="totalPages" value="${trips.totalPages}"/>
      <jsp:param name="baseUrl" value="${baseUrl}"/>
    </jsp:include>
  </div>
</div>
