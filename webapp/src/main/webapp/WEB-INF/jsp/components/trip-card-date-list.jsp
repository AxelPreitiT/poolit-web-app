<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="trips" type="ar.edu.itba.paw.models.PagedContent" scope="request"/>

<!-- Beans:
        - trips: List of trips to be displayed
-->

<!-- Params:
        - noTripsTitleCode: Spring message code for the title to be displayed when there are no trips
        - noTripsSubtitleCode: Spring message code for the subtitle to be displayed when there are no trips
-->

<link href="<c:url value="/resources/css/components/trip-card-date-list.css"/>" rel="stylesheet"/>

<div id="trip-card-date-list">
  <c:choose>
    <c:when test="${empty trips.elements}">
      <div id="no-results-container">
        <i class="fa-solid fa-car-side secondary-color h2"></i>
        <h3><spring:message code="${param.noTripsTitleCode}"/></h3>
        <h6><spring:message code="${param.noTripsSubtitleCode}"/></h6>
      </div>
    </c:when>
    <c:otherwise>
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
              <div class="date-container">
                <h2 class="secondary-color"><spring:message code="${trip.dayOfWeekString}"/></h2>
                <c:choose>
                  <c:when test="${trip.queryIsRecurrent}">
                    <h5 class="italic-text"><spring:message code="tripDetails.card.formatRecurrentDate" arguments="${trip.queryStartDateString}, ${trip.queryEndDateString}"/></h5>
                  </c:when>
                  <c:otherwise>
                    <h5 class="italic-text"><c:out value="${trip.queryStartDateString}"/></h5>
                  </c:otherwise>
                </c:choose>
              </div>
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
    </c:otherwise>
  </c:choose>
  <c:if test="${trips.moreThanOnePage}">
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
  </c:if>
</div>
