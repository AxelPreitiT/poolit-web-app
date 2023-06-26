<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<link href="<c:url value="/resources/css/components/trip-order-by-selector.css"/>" rel="stylesheet" type="text/css"/>

<div id="order-by-row">
  <c:set value="${ (empty param.sortType and empty param.descending) || ( param.sortType eq 'price' && param.descending eq false)}" var="priceAsc"/>
  <c:set value="${param.sortType eq 'price' && param.descending eq true}" var="priceDesc"/>
  <c:set value="${param.sortType eq 'time' && param.descending eq false}" var="timeAsc"/>
  <c:set value="${param.sortType eq 'time' && param.descending eq true}" var="timeDesc"/>
  <c:set value="${param.sortType eq 'driverRating'}" var="driverRatingDesc"/>
  <c:set value="${param.sortType eq 'carRating'}" var="carRatingDesc"/>
  <div id="order-by-select" class="dropdown">
    <button class="btn btn-secondary dropdown-toggle secondary-bg-color" type="button" data-bs-toggle="dropdown" aria-expanded="false">
      <spring:message code="component.orderFilter.title"/> <span>
      <c:choose>
        <c:when test="${priceDesc}">
          <spring:message code="component.orderFilter.priceDesc"/>
        </c:when>
        <c:when test="${timeAsc}">
          <spring:message code="component.orderFilter.dateAsc"/>
        </c:when>
        <c:when test="${timeDesc}">
          <spring:message code="component.orderFilter.dateDesc"/>
        </c:when>
        <c:when test="${driverRatingDesc}">
          <spring:message code="component.orderFilter.driverRatingDesc"/>
        </c:when>
        <c:when test="${carRatingDesc}">
          <spring:message code="component.orderFilter.carRatingDesc"/>
        </c:when>
        <c:otherwise>
          <spring:message code="component.orderFilter.priceAsc"/>
        </c:otherwise>
      </c:choose>
    </span>
    </button>
    <ul class="dropdown-menu dropdown-sort">
      <c:url value="${param.baseUrl}" var="priceAscUrl">
        <c:param name="sortType" value="price"/>
        <c:param name="descending" value="false"/>
      </c:url>
      <li><a class="sort-item <c:if test="${priceAsc}">active</c:if>" href="${priceAscUrl}"><spring:message code="component.orderFilter.priceAsc"/></a></li>
      <c:url value="${param.baseUrl}" var="priceDescUrl">
        <c:param name="sortType" value="price"/>
        <c:param name="descending" value="true"/>
      </c:url>
      <li><a class="sort-item <c:if test="${priceDesc}">active</c:if>" href="${priceDescUrl}"><spring:message code="component.orderFilter.priceDesc"/></a></li>
      <c:url value="${param.baseUrl}" var="timeAscUrl">
        <c:param name="sortType" value="time"/>
        <c:param name="descending" value="false"/>
      </c:url>
      <li><a class="sort-item <c:if test="${timeAsc}">active</c:if>" href="${timeAscUrl}"><spring:message code="component.orderFilter.dateAsc"/></a></li>
      <c:url value="${param.baseUrl}" var="timeDescUrl">
        <c:param name="sortType" value="time"/>
        <c:param name="descending" value="true"/>
      </c:url>
      <li><a class="sort-item <c:if test="${timeDesc}">active</c:if>" href="${timeDescUrl}"><spring:message code="component.orderFilter.dateDesc"/></a></li>
      <c:url value="${param.baseUrl}" var="driverRatingDescUrl">
        <c:param name="sortType" value="driverRating"/>
      </c:url>
      <li><a class="sort-item <c:if test="${driverRatingDesc}">active</c:if>" href="${driverRatingDescUrl}"><spring:message code="component.orderFilter.driverRatingDesc"/></a></li>
      <c:url value="${param.baseUrl}" var="carRatingDescUrl">
        <c:param name="sortType" value="carRating"/>
      </c:url>
      <li><a class="sort-item <c:if test="${carRatingDesc}">active</c:if>" href="${carRatingDescUrl}"><spring:message code="component.orderFilter.carRatingDesc"/></a></li>
    </ul>
  </div>
</div>
