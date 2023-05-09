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
      <img class="circular--square" src="${profileImageUrl}">
    </div>
  </div>
  <div class="row-info">
    <h6><spring:message code="user.fullname"/></h6>
    <h4><spring:message code="user.nameFormat" arguments="${user.name}, ${user.surname}"/></h4>
  </div>
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
  <c:if test="${(param.role eq 'USER')}">
    <jsp:useBean id="rating" type="java.lang.Double" scope="request"/>
    <div class="row-info">
      <h6><spring:message code="user.rating"/></h6>
      <h4><c:out value="${rating}"/></h4>
    </div>
  </c:if>
  <form:form method = "POST" action = "${param.path}">
    <button type="submit" class="btn btn-primary btn-lg"><spring:message code="user.btnAction" arguments="${param.role}"/></button>
  </form:form>
</div>

