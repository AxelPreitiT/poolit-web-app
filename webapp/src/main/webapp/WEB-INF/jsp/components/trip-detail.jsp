<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="trip" type="ar.edu.itba.paw.models.trips.Trip" scope="request"/>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<link href="<c:url value="/resources/css/components/trip-detail.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/resources/css/components/trip-detail-card.css"/>" rel="stylesheet" type="text/css"/>
<div id="trip-detail-card">
<div id="main-header-row">
  <div class="row-heading">
    <h1 class="secondary-color"><spring:message code="tripDetails.title"/></h1>
    <div class="status-row">
    <c:if test="${!trip.tripHasEnded}">
    <c:choose>
      <c:when test="${param.status eq 'ACCEPTED'}">
          <i class="bi bi-check-lg success h2"></i>
          <h2 class="success"><spring:message code="passengerState.accepted"/></h2>
      </c:when>
      <c:when test="${param.status eq 'REJECTED'}">
        <i class="bi bi-x-lg danger h2"></i>
        <h2 class="danger"><spring:message code="passengerState.rejected"/></h2>
      </c:when>
      <c:when test="${param.status eq 'PENDING'}">
        <i class="bi bi-clock primary-color h2"></i>
        <h2 class="primary-color"><spring:message code="passengerState.pending"/></h2>
      </c:when>
    </c:choose>
    </c:if>
    </div>
  </div>
  <hr class="secondary-color">
</div>
<div id="trip-route-container">
  <jsp:include page="/WEB-INF/jsp/components/trip-route.jsp"/>
</div>
<div id="trip-info-container">
  <div class="container">
    <div class="row">
      <div class="col-sm-6 col-md-5 col-lg-4">
        <div id="trip-info-text-container">
          <jsp:include page="/WEB-INF/jsp/components/trip-detail-card.jsp">
            <jsp:param name="showCarImage" value="${param.showPassengers}"/>
            <jsp:param name="showDriverInfo" value="${param.showDriverInfo}"/>
          </jsp:include>
        </div>
      </div>
      <div class="col-md-6 col-lg-5">
        <c:choose>
          <c:when test="${param.showPassengers}">
            <div id="trip-passengers">
              <jsp:include page="/WEB-INF/jsp/components/passengers-list.jsp"/>
            </div>
          </c:when>
          <c:otherwise>
            <div id="car-info-image">
              <c:url value="/image/${trip.car.image_id}" var="carImageUrl"/>
              <div class="placeholder-image">
                <img src="${carImageUrl}" alt="car image"/>
              </div>
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
  </div>
</div>
</div>


