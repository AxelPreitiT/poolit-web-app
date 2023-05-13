<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="trip" type="ar.edu.itba.paw.models.trips.Trip" scope="request"/>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<link href="<c:url value="/resources/css/review-trip/trip-review-detail.css"/>" rel="stylesheet" type="text/css"/>
<jsp:useBean id="reviewForm" type="ar.edu.itba.paw.webapp.form.ReviewForm" scope="request"/>

<div id="main-header-row">
  <h1 class="secondary-color"><spring:message code="reviewTrip.view.title"/></h1>
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
          <jsp:include page="/WEB-INF/jsp/components/trip-detail-card.jsp"/>
        </div>
      </div>
      <div class="col-sm-6 col-md-5 col-lg-6 border-style">
        <div id="trip-info-text-container2">
          <div class="review-form">
          <form:form modelAttribute="reviewForm" method="post" action="${param.url}" cssClass="form-style primary-bg-color">
            <h2>Reseña del viaje:</h2>
            <div class="rating-container">
              <h3>Rating:</h3>
              <div class="rating">
                <input type="radio" name="rating" value="5" id="5"><label for="5">☆</label>
                <input type="radio" name="rating" value="4" id="4"><label for="4">☆</label>
                <input type="radio" name="rating" value="3" id="3"><label for="3">☆</label>
                <input type="radio" name="rating" value="2" id="2"><label for="2">☆</label>
                <input type="radio" name="rating" value="1" id="1"><label for="1">☆</label>
              </div>
            </div>
            <div class="form-group">
              <label for="exampleFormControlTextarea1"></label><textarea class="form-control" id="exampleFormControlTextarea1" rows="3" placeholder="Escriba su reseña aqui"></textarea>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>


