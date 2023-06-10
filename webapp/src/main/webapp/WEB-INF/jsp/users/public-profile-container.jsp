<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="user" type="ar.edu.itba.paw.models.User"  scope="request"/>

<link href="<c:url value="/resources/css/users/profile-container.css"/>" rel="stylesheet" type="text/css"/>

<div class="user-info-container">
  <div class="avatar-img">
    <div class="circular--landscape">
      <c:url value="/image/${user.userImageId}" var="profileImageUrl"/>
      <img class="circular--square" src="${profileImageUrl}" alt="<spring:message code="register.profileImage"/>">
    </div>
  </div>
  <h3 id="user-name"><spring:message code="user.nameFormat" arguments="${user.name}, ${user.surname}"/></h3>
  <c:if test="${user.role eq 'DRIVER'}">
    <div class="row-info">
      <h6><spring:message code="user.countTrips"/></h6>
      <h4><c:out value="${param.countTrips}"/></h4>
    </div>
    <div class="row-info">
      <h6><spring:message code="driver.review.rating"/></h6>
      <div class="d-flex justify-content-between align-items-center">
        <div class="ratings">
          <jsp:useBean id="driverRating" type="java.lang.Double" scope="request"/>
          <c:set var="rating" value="${driverRating}" scope="request"/>
          <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp"/>
        </div>
      </div>
    </div>
  </c:if>
  <div class="row-info">
    <h6><spring:message code="passenger.review.rating"/></h6>
    <div class="d-flex justify-content-between align-items-center">
      <div class="ratings">
        <jsp:useBean id="passengerRating" type="java.lang.Double" scope="request"/>
        <c:set var="rating" value="${passengerRating}" scope="request"/>
        <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp"/>
      </div>
    </div>
  </div>
</div>

