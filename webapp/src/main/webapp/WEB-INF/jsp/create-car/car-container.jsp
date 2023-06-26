<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="car" type="ar.edu.itba.paw.models.Car" scope="request"/>

<link href="<c:url value="/resources/css/users/profile-container.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/resources/css/create-car/car-container.css"/>" rel="stylesheet" type="text/css"/>

<div class="user-info-container-car">
  <div class="avatar-img">
    <div class="circular--landscape">
      <c:url value="/image/${car.imageId}" var="carImageUrl"/>
      <img class="circular--square" src="${carImageUrl}" alt="<spring:message code="updateCar.image"/>">
    </div>
  </div>
  <h3 id="user-name">
    <c:if test="${car.hasInfoCar()}">
      <c:out value="${car.infoCar}"/>
    </c:if>
  </h3>
  <c:if test="${car.hasBrand()}">
    <div class="row-info">
      <h6><spring:message code="createCar.brand"/></h6>
      <h4><c:out value="${car.brand}"/></h4>
    </div>
  </c:if>
  <div class="row-info">
    <h6><spring:message code="createCar.seats"/></h6>
    <h4><c:out value="${car.seats}"/></h4>
  </div>
  <div class="row-info">
    <h6><spring:message code="createCar.plate"/></h6>
    <h4><c:out value="${car.plate}"/></h4>
  </div>
  <c:if test="${!(empty car.features)}">
    <div class="no-edit row-info rows">
      <h6><spring:message code="updateCar.features"/></h6>
      <div id="feature-options-container edit">
        <c:forEach items="${car.features}" var="feature">
          <label class="btn-custom active">
            <spring:message code="${feature.code}"/>
          </label>
        </c:forEach>
      </div>
    </div>
  </c:if>

    <jsp:useBean id="rating" type="java.lang.Double" scope="request"/>
    <div class="row-info">
      <h6><spring:message code="user.rating"/></h6>
      <div class="d-flex justify-content-between align-items-center">
        <div class="ratings">
          <c:set var="rating" value="${rating}" scope="request"/>
          <c:choose>
            <c:when test="${rating == 0}">
              <h4 class="text"><spring:message code="review.none"/></h4>
            </c:when>
            <c:otherwise>
              <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp">
                <jsp:param name="fontSize" value="h4"/>
                <jsp:param name="fontColor" value="secondary-color"/>
              </jsp:include>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
</div>