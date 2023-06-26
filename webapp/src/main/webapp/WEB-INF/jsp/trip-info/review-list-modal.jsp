<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" href="<c:url value="/resources/css/trip-info/review-list-modal.css"/>" type="text/css">

<jsp:useBean id="tripReviewCollection" scope="request" type="ar.edu.itba.paw.models.reviews.TripReviewCollection" />
<jsp:useBean id="trip" scope="request" type="ar.edu.itba.paw.models.trips.Trip" />


<div id="review-list-modal-container">
  <button class="btn button-color button-style shadow-btn" data-bs-target="#review-list-modal" data-bs-toggle="modal">
    <i class="bi bi-pencil-square light-text h4"></i>
    <span class="light-text h4"><spring:message code="review.review"/></span>
  </button>
  <div class="modal fade <c:if test="${(!(empty param.reviewed) && param.reviewed) || (!(empty param.error) && param.error)}">show-on-load</c:if>" id="review-list-modal">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
      <div class="modal-content">
        <div class="modal-header">
          <h2 class="modal-title secondary-color"><spring:message code="review.modalTitle"/></h2>
          <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div class="modal-body">
          <div id="review-published-container" class="collapse <c:if test="${!(empty param.reviewed) && param.reviewed}">show</c:if>">
            <i class="bi bi-check-circle-fill success h6"></i>
            <span class="success h6" id="review-published-text"><spring:message code="review.toast.message"/></span>
          </div>
          <div id="review-published-container-error" class="collapse <c:if test="${!(empty param.error) && param.error}">show</c:if>">
            <i class="bi bi-x-circle-fill danger h6"></i>
            <span class="danger h6" id="review-published-text-error"><spring:message code="review.toast.errorMessage"/></span>
          </div>
          <jsp:include page="/WEB-INF/jsp/trip-info/review-list.jsp"/>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn shadow-btn button-style primary-button" data-bs-dismiss="modal">
            <span class="light-text"><spring:message code="tripCard.btn.back"/></span>
          </button>
        </div>
      </div>
    </div>
  </div>
  <div id="review-form-modals">
    <c:if test="${tripReviewCollection.canReviewDriver}">
      <div id="driver-review-form-modal">
        <c:set var="driverReviewItem" value="${tripReviewCollection.driver}"/>
        <c:set var="driver" value="${driverReviewItem.item}" scope="request"/>
        <div class="modal fade" id="review-driver">
          <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
            <c:url value="/reviews/trips/${trip.tripId}/drivers/${driver.userId}" var="driverReviewUrl"/>
            <form:form modelAttribute="driverReviewForm" method="post" action="${driverReviewUrl}">
              <div class="modal-content">
                <div class="modal-body">
                  <jsp:include page="/WEB-INF/jsp/trip-info/driver-review-form.jsp"/>
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn shadow-btn button-style primary-button" data-bs-target="#review-list-modal" data-bs-toggle="modal">
                    <span class="light-text"><spring:message code="tripCard.btn.back"/></span>
                  </button>
                  <button type="submit" class="btn shadow-btn button-style button-color">
                    <span class="light-text"><spring:message code="review.submit"/></span>
                  </button>
                </div>
              </div>
            </form:form>
          </div>
        </div>
      </div>
    </c:if>
    <c:if test="${tripReviewCollection.canReviewCar}">
      <div id="car-review-form-modal">
        <c:set var="carReviewItem" value="${tripReviewCollection.car}"/>
        <c:set var="car" value="${carReviewItem.item}" scope="request"/>
        <div class="modal fade" id="review-car">
          <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
            <c:url value="/reviews/trips/${trip.tripId}/cars/${car.carId}" var="carReviewUrl"/>
            <form:form modelAttribute="carReviewForm" method="post" action="${carReviewUrl}">
              <div class="modal-content">
                <div class="modal-body">
                  <jsp:include page="/WEB-INF/jsp/trip-info/car-review-form.jsp"/>
                </div>
                <div class="modal-footer">
                  <button type="button" class="btn shadow-btn button-style primary-button" data-bs-target="#review-list-modal" data-bs-toggle="modal">
                    <span class="light-text"><spring:message code="tripCard.btn.back"/></span>
                  </button>
                  <button type="submit" class="btn shadow-btn button-style button-color">
                    <span class="light-text"><spring:message code="review.submit"/></span>
                  </button>
                </div>
              </div>
            </form:form>
          </div>
        </div>
      </div>
    </c:if>
    <c:if test="${tripReviewCollection.canReviewPassengers}">
      <div id="passenger-review-form-modals">
        <c:forEach items="${tripReviewCollection.passengers}" var="passengerReviewItem">
          <c:set var="passenger" value="${passengerReviewItem.item}"/>
          <div class="modal fade" id="review-passenger-${passenger.userId}">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
              <c:url value="/reviews/trips/${trip.tripId}/passengers/${passenger.user.userId}" var="passengerReviewUrl"/>
              <form:form modelAttribute="passengerReviewForm" method="post" action="${passengerReviewUrl}">
                <div class="modal-content">
                  <div class="modal-body">
                    <c:set var="passenger" value="${passenger}" scope="request"/>
                    <jsp:include page="/WEB-INF/jsp/trip-info/passenger-review-form.jsp"/>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn shadow-btn button-style primary-button" data-bs-target="#review-list-modal" data-bs-toggle="modal">
                      <span class="light-text"><spring:message code="tripCard.btn.back"/></span>
                    </button>
                    <button type="submit" class="btn shadow-btn button-style button-color">
                      <span class="light-text"><spring:message code="review.submit"/></span>
                    </button>
                  </div>
                </div>
              </form:form>
            </div>
          </div>
        </c:forEach>
      </div>
    </c:if>
  </div>
  <script src="<c:url value="/resources/js/pages/trip-info/review-list-modal.js"/>"></script>
</div>

