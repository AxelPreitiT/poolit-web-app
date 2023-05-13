<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="reviews" type="java.util.List<ar.edu.itba.paw.models.Review>" scope="request"/>

<link href="<c:url value="/resources/css/users/info-container.css"/>" rel="stylesheet" type="text/css"/>

<div class="list-container">
  <div class="row-data">
    <c:if test="${param.role eq 'USER'}">
      <h2><spring:message code="review.publicProfile.title.user"/></h2>
    </c:if>
    <c:if test="${param.role eq 'DRIVER'}">
      <h2><spring:message code="review.publicProfile.title.driver"/></h2>
    </c:if>
  </div>
  <div>
  <c:choose>
    <c:when test="${reviews.size() == 0}">
      <div class="review-empty-container">
          <i class="bi-solid bi-book secondary-color h2"></i>
          <h3 class="italic-text placeholder-text"><spring:message code="review.none"/></h3>
      </div>
    </c:when>
    <c:otherwise>
        <c:forEach items="${reviews}" var="review">
          <c:set var="review" value="${review}" scope="request"/>
          <jsp:include page="/WEB-INF/jsp/users/review-info.jsp"/>
        </c:forEach>
    </c:otherwise>
    </c:choose>
  </div>
</div>
