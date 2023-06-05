<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" href="<c:url value="/resources/css/trip-info/review-list-modal.css"/>" type="text/css">

<jsp:useBean id="tripReviewCollection" scope="request" type="ar.edu.itba.paw.models.reviews.TripReviewCollection" />

<div id="review-list-modal-container">
  <button class="btn button-color button-style shadow-btn" data-bs-target="#review-modal" data-bs-toggle="modal">
    <i class="bi bi-pencil-square light-text h3"></i>
    <span class="light-text h3"><spring:message code="review.review"/></span>
  </button>
  <div class="modal fade" id="review-modal">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
      <div class="modal-content">
        <div class="modal-header">
          <h2 class="modal-title secondary-color">¿Sobre quién desea opinar?</h2>
          <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div class="modal-body">
          <jsp:include page="/WEB-INF/jsp/trip-info/review-list.jsp"/>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn shadow-btn button-style primary-button" data-bs-dismiss="modal">
            <span class="light-text"><spring:message code="tripCard.btn.cancel"/></span>
          </button>
        </div>
      </div>
    </div>
  </div>
  <div id="review-form-modals">
    <c:forEach items="${tripReviewCollection.passengers}" var="passenger">
      <div class="modal fade" id="review-passenger-${passenger.userId}">
        <div class="modal-dialog modal-dialog-centered">
          <div class="modal-content">
            <div class="modal-body">
              <c:set var="passenger" value="${passenger}" scope="request"/>
              <jsp:include page="/WEB-INF/jsp/trip-info/passenger-review-form.jsp"/>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn shadow-btn button-style primary-button" data-bs-target="#review-modal" data-bs-toggle="modal">
                <span class="light-text"><spring:message code="tripCard.btn.cancel"/></span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </c:forEach>
  </div>
</div>
