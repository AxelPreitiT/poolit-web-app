<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<link href="<c:url value="/resources/css/components/trip-order-by-selector.css"/>" rel="stylesheet" type="text/css"/>

<div id="order-by-row">
<%--  <label for="order-by-select" class="input-group-text italic-text"><spring:message code="component.orderFilter.title"/></label>--%>
  <c:set value="${ (empty param.sortType and empty param.descending) || ( param.sortType eq 'price' && param.descending eq false)}" var="priceAsc"/>
  <c:set value="${param.sortType eq 'price' && param.descending eq true}" var="priceDesc"/>
  <c:set value="${param.sortType eq 'time' && param.descending eq false}" var="timeAsc"/>
  <c:set value="${param.sortType eq 'time' && param.descending eq true}" var="timeDesc"/>
  <div id="order-by-select" class="dropdown">
    <button class="btn btn-secondary dropdown-toggle secondary-bg-color" type="button" data-bs-toggle="dropdown" aria-expanded="false">
      Ordenar por: <span>
      <c:choose>
        <c:when test="${priceDesc}">
          Precio descendente
        </c:when>
        <c:when test="${timeAsc}">
          Tiempo ascendente
        </c:when>
        <c:when test="${timeDesc}">
          Tiempo descendente
        </c:when>
        <c:otherwise>
          Precio ascendente
        </c:otherwise>
      </c:choose>
    </span>
    </button>
    <ul class="dropdown-menu dropdown-sort">
      <c:url value="${param.baseUrl}" var="priceAscUrl">
        <c:param name="sortType" value="price"/>
        <c:param name="descending" value="false"/>
      </c:url>
      <li><a class="sort-item <c:if test="${priceAsc}">active</c:if>" href="${priceAscUrl}">Precio ascendente</a></li>
      <c:url value="${param.baseUrl}" var="priceDescUrl">
        <c:param name="sortType" value="price"/>
        <c:param name="descending" value="true"/>
      </c:url>
      <li><a class="sort-item <c:if test="${priceDesc}">active</c:if>" href="${priceDescUrl}">Precio descendente</a></li>
      <c:url value="${param.baseUrl}" var="timeAscUrl">
        <c:param name="sortType" value="time"/>
        <c:param name="descending" value="false"/>
      </c:url>
      <li><a class="sort-item <c:if test="${timeAsc}">active</c:if>" href="${timeAscUrl}">Tiempo ascendente</a></li>
      <c:url value="${param.baseUrl}" var="timeDescUrl">
        <c:param name="sortType" value="time"/>
        <c:param name="descending" value="true"/>
      </c:url>
      <li><a class="sort-item <c:if test="${timeDesc}">active</c:if>" href="${timeDescUrl}">Tiempo descendente</a></li>
    </ul>
  </div>
<%--  <div class="input-group input-group-sm">--%>
<%--    <label for="order-by-select" class="input-group-text italic-text"><spring:message code="component.orderFilter.title"/></label>--%>
<%--    <select onchange="alert(this.val())" id="order-by-select" name="order-by-select" class="form-select form-select-sm">--%>
<%--      <option value="price"><spring:message code="trip.price"/></option>--%>
<%--      <option value="date"><spring:message code="trip.date"/></option>--%>
<%--    </select>--%>
<%--    <button type="button" class="btn button-color">--%>
<%--      <i class="bi bi-arrow-down-up light-text"></i>--%>
<%--    </button>--%>
<%--  </div>--%>
</div>
