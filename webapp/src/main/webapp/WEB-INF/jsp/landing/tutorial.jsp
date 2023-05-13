<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link href="<c:url value="/resources/css/landing/tutorial.css"/>" rel="stylesheet" type="text/css"/>

<div id="tutorial-container">
  <div class="tutorial-row">
    <div class="text-container">
      <h3 class="secondary-color"><spring:message code="landing.tutorial.principalTitle"/></h3>
      <span class="text"><spring:message code="landing.tutorial.principalText"/>
          <strong class="secondary-color"><spring:message code="landing.tutorial.recurrentTrips"/></strong>
      </span>
    </div>
    <div class="image-container">
      <img src="<c:url value="/resources/images/landing/landing-tutorial-one.jpg"/>" alt="<spring:message code="landing.tutorial.searchTrips"/>">
    </div>
  </div>
  <hr class="secondary-color"/>
  <div class="tutorial-row">
    <div class="text-container">
      <h3 class="secondary-color"><spring:message code="landing.tutorial.secondTitle"/></h3>
      <span class="text"><spring:message code="landing.tutorial.secondText"/></span>
    </div>
    <div class="image-container">
      <img src="<c:url value="/resources/images/landing/landing-tutorial-two.jpg"/>" alt="<spring:message code="landing.tutorial.bookTrips"/>">
    </div>
  </div>
  <hr class="secondary-color"/>
  <div class="tutorial-row">
    <div class="text-container">
      <h3 class="secondary-color"><spring:message code="landing.tutorial.thirdTitle"/></h3>
      <span class="text"><spring:message code="landing.tutorial.thirdText"/></span>
    </div>
    <div class="image-container">
      <img src="<c:url value="/resources/images/landing/landing-tutorial-three.jpg"/>" alt="<spring:message code="landing.tutorial.createTrips"/>">
    </div>
  </div>
</div>
