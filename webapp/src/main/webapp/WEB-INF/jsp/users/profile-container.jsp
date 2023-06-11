<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:useBean id="user" type="ar.edu.itba.paw.models.User" scope="request"/>

<link href="<c:url value="/resources/css/users/profile-container.css"/>" rel="stylesheet" type="text/css"/>

<div class="user-info-container">
  <div class="avatar-img">
    <div class="circular--landscape">
      <c:url value="/image/${user.userImageId}" var="profileImageUrl"/>
      <img class="circular--square" src="${profileImageUrl}" alt="<spring:message code="register.profileImage"/>">
    </div>
  </div>
  <h3 id="user-name"><spring:message code="user.nameFormat" arguments="${user.name}, ${user.surname}"/></h3>
  <div class="row-info">
    <h6><spring:message code="user.email"/></h6>
    <h4><c:out value="${user.email}"/></h4>
  </div>
  <div class="row-info">
    <h6><spring:message code="user.phone"/></h6>
    <h4><c:out value="${user.phone}"/></h4>
  </div>
  <div class="row-info">
    <h6><spring:message code="user.district"/></h6>
    <h4><c:out value="${user.bornCity.name}"/></h4>
  </div>
  <div class="row-info">
    <h6><spring:message code="user.locale"/></h6>
    <c:if test="${(user.mailLocale eq 'es')}">
      <h4><spring:message code="user.locale.es"/></h4>
    </c:if>
    <c:if test="${(user.mailLocale eq 'en')}">
      <h4><spring:message code="user.locale.en"/></h4>
    </c:if>
  </div>
  <sec:authorize access="hasRole('ROLE_DRIVER')">
    <div class="row-info">
      <jsp:useBean id="countTrips" type="java.lang.Integer" scope="request"/>
      <h6><spring:message code="user.countTrips"/></h6>
      <h4><c:out value="${countTrips}"/></h4>
    </div>
    <div class="row-info">
      <h6><spring:message code="driver.review.rating"/></h6>
      <c:choose>
        <c:when test="${param.hasBeenRatedAsDriver}">
          <div class="d-flex justify-content-between align-items-center">
            <div class="ratings">
              <jsp:useBean id="driverRating" type="java.lang.Double" scope="request"/>
              <c:set var="rating" value="${driverRating}" scope="request"/>
              <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp"/>
            </div>
          </div>
        </c:when>
        <c:otherwise>
          <h4><spring:message code="review.none"/></h4>
        </c:otherwise>
      </c:choose>
    </div>
  </sec:authorize>
  <div class="row-info">
    <h6><spring:message code="passenger.review.rating"/></h6>
    <c:choose>
      <c:when test="${param.hasBeenRatedAsPassenger}">
        <div class="d-flex justify-content-between align-items-center">
          <div class="ratings">
            <jsp:useBean id="passengerRating" type="java.lang.Double" scope="request"/>
            <c:set var="rating" value="${passengerRating}" scope="request"/>
            <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp"/>
          </div>
        </div>
      </c:when>
      <c:otherwise>
        <h4><spring:message code="review.none"/></h4>
      </c:otherwise>
    </c:choose>
  </div>
</div>

