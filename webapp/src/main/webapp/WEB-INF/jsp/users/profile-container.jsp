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
  <c:if test="${(user.role eq 'DRIVER')}">
    <jsp:useBean id="rating" type="java.lang.Double" scope="request"/>
    <div class="row-info">
      <h6><spring:message code="user.rating"/></h6>
      <div class="d-flex justify-content-between align-items-center">
        <div class="ratings">
          <c:forEach var="i" begin="1" end="${rating}">
            <i class="bi bi-star-fill secondary-color h4"></i>
          </c:forEach>
          <c:if test="${rating % 1 >= 0.5}">
            <i class="bi bi-star-half secondary-color h4"></i>
            <c:forEach var="i" begin="${rating + 2}" end="5">
              <i class="bi bi-star secondary-color h4"></i>
            </c:forEach>
          </c:if>
          <c:if test="${rating % 1 < 0.5}">
            <c:forEach var="i" begin="${rating + 1}" end="5">
              <i class="bi bi-star secondary-color h4"></i>
            </c:forEach>
          </c:if>
        </div>
      </div>
    </div>
  </c:if>
</div>

