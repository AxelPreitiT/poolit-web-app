<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<link href="<c:url value="/resources/css/components/trip-order-by-selector.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/resources/css/components/navbar.css"/>" rel="stylesheet" type="text/css"/>


<div id="order-by-row">
  <c:set value="${param.status eq 'waiting'}" var="waiting"/>
  <c:set value="${param.status eq 'accept'}" var="accept"/>
  <c:set value="${param.status eq 'reject'}" var="reject"/>
  <c:set value="${ !waiting and !accept and !reject}" var="all"/>

  <div id="order-by-select" class="dropdown">
    <button class="btn btn-secondary dropdown-toggle secondary-bg-color" type="button" data-bs-toggle="dropdown" aria-expanded="false">
      <spring:message code="driver.passangers.statusFilter.title"/> <span>
      <c:choose>
        <c:when test="${all}">
          <spring:message code="driver.passangers.statusFilter.all"/>
        </c:when>
        <c:when test="${accept}">
          <spring:message code="driver.passangers.statusFilter.accept"/>
        </c:when>
        <c:when test="${reject}">
          <spring:message code="driver.passangers.statusFilter.reject"/>
        </c:when>
        <c:otherwise>
          <spring:message code="driver.passangers.statusFilter.waiting"/>
        </c:otherwise>
      </c:choose>
    </span>
    </button>
    <ul class="dropdown-menu dropdown-sort">
      <c:url value="${param.baseUrl}" var="allUrl">
        <c:param name="status" value=""/>
      </c:url>
      <li><a class="sort-item <c:if test="${all}">active</c:if>" href="${allUrl}"><spring:message code="driver.passangers.statusFilter.all"/></a></li>

      <c:url value="${param.baseUrl}" var="acceptUrl">
        <c:param name="status" value="accept"/>
      </c:url>
      <li><a class="sort-item <c:if test="${accept}">active</c:if>" href="${acceptUrl}"><spring:message code="driver.passangers.statusFilter.accept"/></a></li>

      <c:url value="${param.baseUrl}" var="waitingUrl">
        <c:param name="status" value="waiting"/>
      </c:url>
      <li><a class="sort-item <c:if test="${waiting}">active</c:if>" href="${waitingUrl}"><spring:message code="driver.passangers.statusFilter.waiting"/></a></li>

      <c:url value="${param.baseUrl}" var="rejectUrl">
        <c:param name="status" value="reject"/>
      </c:url>
      <li><a class="sort-item <c:if test="${reject}">active</c:if>" href="${rejectUrl}"><spring:message code="driver.passangers.statusFilter.reject"/></a></li>

    </ul>
  </div>
</div>

