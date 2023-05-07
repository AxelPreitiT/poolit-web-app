<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<!-- Params:
      - path: the url path
      - springMessagePrefix: the prefix for the spring messages
-->

<link href="<c:url value="/resources/css/components/trip-nav-tabs.css"/>" rel="stylesheet">

<nav class="nav nav-pills nav-fill">
  <c:url value="${param.path}" var="futureReservedUrl">
    <c:param name="time" value="future"/>
  </c:url>
  <a class="nav-link" href="${futureReservedUrl}" id="default-time-tab">
    <h4 class="nav-link-text secondary-color">
      <spring:message code="${param.springMessagePrefix}.futureTab"/>
    </h4>
  </a>
  <c:url value="${param.path}" var="pastReservedUrl">
    <c:param name="time" value="past"/>
  </c:url>
  <a class="nav-link" href="${pastReservedUrl}">
    <h4 class="nav-link-text secondary-color">
      <spring:message code="${param.springMessagePrefix}.pastTab"/>
    </h4>
  </a>
</nav>

<script src="<c:url value="/resources/js/components/tripNavTabs.js"/>" type="text/javascript"></script>
