<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
  <title><spring:message code="profile.title"/></title>
  <jsp:include page="/resources/external-resources.jsp"/>
  <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
  <link href="<c:url value="/resources/css/users/profile.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body class="background-color">
  <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
  <div class="main-container">
    <jsp:include page="/WEB-INF/jsp/create-car/car-container-owner.jsp">
      <jsp:param name="formHasErrors" value="${formHasErrors}"/>
    </jsp:include>
    <div class="list-properties-container">
      <c:set var="reviews" value="${carReviewsPaged.elements}" scope="request"/>
      <jsp:include page="/WEB-INF/jsp/users/review-container.jsp">
        <jsp:param name="type" value="car"/>
      </jsp:include>
      <c:if test="${carReviewsPaged.moreThanOnePage}">
        <c:url value="" var="baseUrl">
          <c:forEach var="p" items="${param}">
            <c:if test="${!(p.key eq 'page')}">
              <c:param name="${p.key}" value="${p.value}"/>
            </c:if>
          </c:forEach>
        </c:url>
        <jsp:include page="/WEB-INF/jsp/components/pagination.jsp">
          <jsp:param name="currentPage" value="${carReviewsPaged.currentPage+1}"/>
          <jsp:param name="totalPages" value="${carReviewsPaged.totalPages}"/>
          <jsp:param name="baseUrl" value="${baseUrl}"/>
        </jsp:include>
      </c:if>
    </div>
  </div>
</body>
</html>
