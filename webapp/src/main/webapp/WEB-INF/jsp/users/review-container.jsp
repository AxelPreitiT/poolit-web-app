<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<link href="<c:url value="/resources/css/users/info-container.css"/>" rel="stylesheet" type="text/css"/>

<div class="list-container">
  <div class="row-data">
    <c:choose>
      <c:when test="${param.type eq 'passenger'}">
        <c:set var="title" value="passenger.review.title"/>
      </c:when>
      <c:when test="${param.type eq 'driver'}">
        <c:set var="title" value="driver.review.title"/>
      </c:when>
      <c:otherwise>
          <c:set var="title" value="car.review.title"/>
      </c:otherwise>
    </c:choose>
    <h2><spring:message code="${title}"/></h2>
  </div>
  <div>
    <c:choose>
      <c:when test="${empty reviews}">
        <div class="review-empty-container">
            <i class="bi-solid bi-book secondary-color h2"></i>
            <h3 class="italic-text placeholder-text"><spring:message code="review.none"/></h3>
        </div>
      </c:when>
      <c:otherwise>
        <div id="reviews-container" class="reviews-container">
          <c:forEach items="${reviews}" var="review">
            <div class="revs">
              <c:set var="review" value="${review}" scope="request"/>
              <jsp:include page="/WEB-INF/jsp/users/review-info.jsp">
                <jsp:param name="type" value="${param.type}"/>
              </jsp:include>
            </div>
          </c:forEach>
          <a href="${param.url}">
            <div class="plus-btn">
              <h3 class="text"><spring:message code="review.more"/></h3>
            </div>
          </a>
        </div>
      </c:otherwise>
    </c:choose>
  </div>
</div>
