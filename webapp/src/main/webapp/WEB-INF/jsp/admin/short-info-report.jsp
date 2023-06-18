<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="report" type="ar.edu.itba.paw.models.reports.Report" scope="request"/>

<div class="row-report">
  <div class="profiles-info">
    <div class="info-profile-img">
      <div>
        <img src="/image/${report.trip.driver.userImageId}" alt="user image" class="image-photo-admin"/>
      </div>
      <div class="short-info-profile">
        <div class="inline-text">
          <h4><c:out value="${report.trip.driver.name}"/> <c:out value="${report.trip.driver.surname}"/></h4>
          <h6 class="italic-text">(usuario)</h6>
        </div>
        <c:set var="rating" value="${report.reporter.passengerRating}" scope="request"/>
        <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp"/>
      </div>
    </div>
    <div class="location-line">
      <div class="location-line-content">
        <i class="fa-solid fa-bounce secondary-color"></i>
        <div class="dotted-line"></div>
      </div>
    </div>
    <div class="info-profile-img">
      <div>
        <img src="/image/${report.trip.driver.userImageId}" alt="user image" class="image-photo-admin"/>
      </div>
      <div class="short-info-profile">
        <div class="inline-text">
          <h4><c:out value="${report.trip.driver.name}"/> <c:out value="${report.trip.driver.surname}"/></h4>
          <h6 class="italic-text">(usuario)</h6>
        </div>
        <c:set var="rating" value="${report.reporter.passengerRating}" scope="request"/>
        <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp"/>
      </div>
    </div>
  </div>
  <div class="trip-short-info">
    <h4>Motivo: <span class="primary-color italic-text"><spring:message code="${report.reason.springMessageCode}"/></span></h4>
    <h4>Fecha: <c:out value="${report.dateString}"/></h4>
  </div>
</div>