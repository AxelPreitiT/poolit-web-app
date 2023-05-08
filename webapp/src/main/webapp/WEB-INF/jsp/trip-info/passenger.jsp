<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title><spring:message code="selectTrip.title"/></title>
  <jsp:include page="/resources/external-resources.jsp"/>
  <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
  <link href="<c:url value="/resources/css/trip-info/driver.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body class="background-color">
<div id="navbar-container">
  <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
</div>
<div class="main-container-style container-color">
  <div id="trip-detail-container">
    <jsp:include page="/WEB-INF/jsp/components/trip-detail.jsp"/>
  </div>
  <div id="footer-container">
    <div id="trip-price-container">
      <div class="trip-price-row">
        <div>
          <span class="h3 text"><spring:message code="selectTrip.price"/></span>
        </div>
        <div>
          <span class="h2 secondary-color"><spring:message code="selectTrip.priceFormat" arguments="${trip.price}"/></span>
        </div>
      </div>
      <div class="trip-price-row items-to-end">
        <c:choose>
          <c:when test="${trip.recurrent}">
            <span class="h6 italic-text"><c:out value="${trip.queryTotalTrips}"/> viajes</span>
          </c:when>
          <c:otherwise>
            <span class="h6 italic-text">Viaje único</span>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
    <div id="button-container">
      <div class="delete-trip-container">
        <button type="submit" class="btn button-style  danger-bg-color shadow-btn" data-bs-toggle="modal" data-bs-target="#modal-<c:out value="${trip.tripId}"/>">
          <i class="bi bi-x light-text h3"></i>
          <span class="button-text-style light-text h3"><spring:message code="tripInfo.passenger.deleteButton"/></span>
        </button>
      </div>
      <div class="modal fade" id="modal-<c:out value="${trip.tripId}"/>" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
          <div class="modal-content">
            <div class="modal-header">
              <h3 class="modal-title danger"><spring:message code="tripCard.user.cancel"/></h3>
              <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
              <!-- Avisar a axel que toque un poco el formato con el de hasta -->
              <span class="text"><spring:message code="tripCard.user.cancel.warning.title" arguments="${trip.originCity.name}, ${trip.destinationCity.name}"/></span>
              <span class="text"><spring:message code="tripCard.user.cancel.warning.message"/></span>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn primary-bg-color" data-bs-dismiss="modal">
                <span class="light-text"><spring:message code="tripCard.btn.cancel"/></span>
              </button>
              <c:url value="/trips/${trip.tripId}/cancel" var="cancelTripUrl"/>
              <form:form method="DELETE" action="${cancelTripUrl}">
                <button type="submit" class="btn danger-bg-color">
                  <span class="light-text"><spring:message code="tripCard.user.btn.cancel"/></span>
                </button>
              </form:form>
            </div>
          </div>
        </div>
      </div>
      <c:url value="/trips/reserved" var="joinUrl"/>
      <a class="btn button-style button-bg-color shadow-btn">
        <i class="bi bi-car-front-fill light-text h3"></i>
        <span class="button-text-style light-text h3"><spring:message code="tripInfo.passenger.button"/></span>
      </a>
    </div>
  </div>
</div>
</body>
</html>