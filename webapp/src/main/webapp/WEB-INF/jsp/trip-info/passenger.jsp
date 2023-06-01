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
  <link href="<c:url value="/resources/css/trip-info/passenger.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body class="background-color">
<div id="navbar-container">
  <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
</div>
<div class="main-container-style container-color">
  <div id="trip-detail-container">
    <jsp:include page="/WEB-INF/jsp/components/trip-detail.jsp">
      <jsp:param name="showDriverInfo" value="true"/>
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
      <c:if test="${!done}">
        <div class="delete-trip-container">
          <button type="submit" class="btn button-style danger-button shadow-btn" data-bs-toggle="modal" data-bs-target="#modal-<c:out value="${trip.tripId}"/>">
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
                <span class="text"><spring:message code="tripCard.user.cancel.warning.title"/>
                  <strong class="secondary-color"><c:out value="${trip.originCity.name}"/></strong>
                  <strong class="secondary-color">-</strong>
                  <strong class="secondary-color"><c:out value="${trip.destinationCity.name}"/></strong>?
                </span>
                <span class="text"><spring:message code="tripCard.user.cancel.warning.message"/></span>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn primary-button" data-bs-dismiss="modal">
                  <span class="light-text"><spring:message code="tripCard.btn.cancel"/></span>
                </button>
                <c:url value="/trips/${trip.tripId}/cancel" var="cancelTripUrl"/>
                <form:form method="DELETE" action="${cancelTripUrl}">
                  <button type="submit" class="btn danger-button">
                    <span class="light-text"><spring:message code="tripCard.user.btn.cancel"/></span>
                  </button>
                </form:form>
              </div>
            </div>
          </div>
        </div>
      </c:if>
      <c:if test="${done}">
        <div class="delete-trip-container">
          <c:if test="${!reviewed}">
            <button type="submit" class="btn button-style  secondary-bg-color shadow-btn" data-bs-toggle="modal" data-bs-target="#modal-review">
              <i class="bi bi-plus-lg light-text h3"></i>
              <span class="button-text-style light-text h3"><spring:message code="review.btn"/></span>
            </button>
          </c:if>
          <c:if test="${reviewed}">
            <button type="submit" disabled class="btn button-style success-bg-color shadow-btn" data-bs-toggle="modal" data-bs-target="#modal-review">
              <i class="bi bi-check-lg light-text h3"></i>
              <span class="button-text-style light-text h3"><spring:message code="review.btnDisable"/></span>
            </button>
          </c:if>
        </div>
        <div class="modal fade <c:if test="${failedReview}">show-on-load</c:if>" id="modal-review" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-hidden="true">
          <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
              <div class="modal-header">
                <h3 class="modal-title secondary-color"><spring:message code="review.title"/></h3>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
              </div>
              <div class="modal-body">
                <c:url value="/trips/${trip.tripId}/review" var="reviewTripUrl"/>
                <form:form modelAttribute="reviewForm" method="POST" action="${reviewTripUrl}">
                <div class="review-form">
                  <div class="rating-container">
                    <h3><spring:message code="review.rating"/></h3>
                    <div class="rating">
                      <form:select path="rating" cssClass="form-select form-select-sm">
                        <form:option value="1" label="☆"/>
                        <form:option value="2" label="☆☆"/>
                        <form:option value="3" label="☆☆☆"/>
                        <form:option value="4" label="☆☆☆☆"/>
                        <form:option value="5" label="☆☆☆☆☆"/>
                      </form:select>
                    </div>
                    <div class="error-item">
                      <i class="bi bi-exclamation-circle-fill danger"></i>
                      <form:errors path="rating" cssClass="danger" element="span"/>
                    </div>
                  </div>
                  <div class="form-group">
                    <h3><spring:message code="review.review"/></h3>
                    <spring:message code="review.placeholder" var="reviewPlaceholder"/>
                    <form:input path="review" cssClass="form-control" id="date" name="date" placeholder="${reviewPlaceholder}"/>
                    <div class="error-item">
                      <i class="bi bi-exclamation-circle-fill danger"></i>
                      <form:errors path="review" cssClass="danger" element="span"/>
                    </div>
                  </div>
                  <div id="warning-message">
                    <h6 class="italic-text"><spring:message code="review.textInfo"/></h6>
                  </div>
                </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn danger-button" data-bs-dismiss="modal">
                  <span class="light-text"><spring:message code="review.cancel"/></span>
                </button>
                <button type="submit" class="btn primary-button">
                  <span class="light-text"><spring:message code="review.submit"/></span>
                </button>
                </form:form>
              </div>
            </div>
          </div>
        </div>
      </c:if>
      <c:url value="/trips/reserved" var="reservedUrl"/>
      <a class="btn button-style primary-button shadow-btn" href="${reservedUrl}">
        <i class="bi bi-car-front-fill light-text h3"></i>
        <span class="button-text-style light-text h3"><spring:message code="tripInfo.passenger.button"/></span>
      </a>
    </div>
  </div>
</div>
<c:if test="${!(empty tripReviewed) && tripReviewed}">
  <div id="toast-container">
    <jsp:include page="/WEB-INF/jsp/components/success-toast.jsp">
      <jsp:param name="title" value="review.toast.title"/>
      <jsp:param name="message" value="review.toast.message"/>
    </jsp:include>
  </div>
</c:if>
<c:if test="${!(empty successInscription) && successInscription}">
  <div id="toast-container">
    <jsp:include page="/WEB-INF/jsp/components/success-toast.jsp">
      <jsp:param name="title" value="selectTrip.success.toast.title"/>
      <jsp:param name="message" value="selectTrip.success.toast.message"/>
    </jsp:include>
  </div>
</c:if>
<script src="<c:url value="/resources/js/pages/trip-info/passenger.js"/>" type="text/javascript"></script>
</body>
</html>