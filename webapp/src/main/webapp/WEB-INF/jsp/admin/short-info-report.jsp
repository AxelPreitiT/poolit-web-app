<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="row-report">
  <div class="short-info-profile">
    <h4><c:out value="${report.trip.driver.name}"/> <c:out value="${report.trip.driver.surname}"/> (usuario)</h4>
    <c:set var="rating" value="${report.reporter.passengerRating}" scope="request"/>
    <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp"/>
  </div>
  <div class="location-line">
    <div class="location-line-content">
      <i class="fa-solid fa-bounce secondary-color"></i>
      <div class="dotted-line"></div>
    </div>
  </div>
  <div class="short-info-profile">
    <h4><c:out value="${report.trip.driver.name}"/> <c:out value="${report.trip.driver.surname}"/> (usuario)</h4>
    <c:set var="rating" value="${report.reporter.passengerRating}" scope="request"/>
    <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp"/>
  </div>
</div>