<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title><spring:message code="tripInfo.driver.title"/></title>
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
    <jsp:include page="/WEB-INF/jsp/components/trip-detail.jsp">
      <jsp:param name="showDriverInfo" value="true"/>
      <jsp:param name="showPassengers" value="true"/>
    </jsp:include>
  </div>
  <div id="footer-container">
    <div id="trip-price-container">
      <div class="trip-price-row">
        <div>
          <span class="h3 text"><spring:message code="selectTrip.price"/></span>
        </div>
        <div>
          <span class="h2 secondary-color">
            <spring:message code="selectTrip.priceFormat" arguments="${trip.integerQueryTotalPrice},${trip.decimalQueryTotalPrice}"/>
          </span>
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
      <div id="review-trip-container">
        <jsp:include page="/WEB-INF/jsp/trip-info/review-list-modal.jsp">
          <jsp:param name="reviewed" value="${reviewed}"/>
        </jsp:include>
      </div>
      <c:if test="${!trip.tripHasEnded}">
        <div class="delete-trip-container">
          <button class="btn button-style shadow-btn danger-button" data-bs-toggle="modal" data-bs-target="#modal-<c:out value="${trip.tripId}"/>">
            <i class="bi bi-trash-fill light-text h5"></i>
            <span class="light-text h3"><spring:message code="tripInfo.driver.deleteButton"/></span>
          </button>
        </div>
        <div class="modal fade" id="modal-<c:out value="${trip.tripId}"/>" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
              <div class="modal-header">
                <h3 class="modal-title danger"><spring:message code="tripCard.delete"/></h3>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
              </div>
              <div class="modal-body">
                <span class="text"><spring:message code="tripCard.warning.title"/>
                  <strong class="secondary-color"><c:out value="${trip.originCity.name}"/></strong>
                  <strong class="secondary-color">-</strong>
                  <strong class="secondary-color"><c:out value="${trip.destinationCity.name}"/></strong>?
                </span>
                <span class="text"><spring:message code="tripCard.warning.messege"/></span>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn primary-button" data-bs-dismiss="modal">
                  <span class="light-text"><spring:message code="tripCard.btn.cancel"/></span>
                </button>
                <c:url value="/trips/${trip.tripId}/delete" var="deleteTripUrl"/>
                <form:form method="DELETE" action="${deleteTripUrl}">
                  <button type="submit" class="btn danger-button">
                    <span class="light-text"><spring:message code="tripCard.btn.delete"/></span>
                  </button>
                </form:form>
              </div>
            </div>
          </div>
        </div>
      </c:if>
      <c:url value="/trips/created" var="myTripsUrl"/>
      <a href="${myTripsUrl}" class="btn button-style primary-button shadow-btn">
        <i class="bi bi-car-front-fill light-text h3"></i>
        <span class="light-text h3"><spring:message code="tripInfo.driver.button"/></span>
      </a>
    </div>
  </div>
</div>
</body>
</html>