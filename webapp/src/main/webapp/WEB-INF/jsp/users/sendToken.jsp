<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title><spring:message code="login.title"/></title>
  <jsp:include page="/resources/external-resources.jsp"/>
  <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
  <link href="<c:url value="/resources/css/users/login.css"/>" rel="stylesheet">
</head>
<body class="background-bg-color">
<div class="full-container">
  <div class="favicon_container">
    <a id="img-container" href="<c:url value="/"/>">
      <img src="<c:url value="/resources/images/poolit/poolit.svg"/>" alt="POOLIT" class="brand-logo">
    </a>
  </div>
  <div class="main-container-style primary-bg-color">
    <h2 class="light-text"><spring:message code="sendToken.title"/></h2>
    <hr class="light-text" id="title-hr">
    <c:url value="/register/sendToken" var="postUrl"/>
    <form:form modelAttribute="emailForm" action="${postUrl}" method="post" cssClass="form-style"  enctype="multipart/form-data">
      <div class="user-info-row">
        <div class="user-info-item">
          <div class="form-floating">
            <spring:message code="user.email" var="email"/>
            <form:input path="email" cssClass="form-control" id="plate" placeholder='${email}'/>
          </div>
        </div>
      </div>
      <div class="d-grid gap-2 submit-row">
        <button type="submit" class="btn button-color btn-lg">
          <span class="light-text h5"><spring:message code="sendToken.btn"/></span>
        </button>
      </div>
    </form:form>
    <hr class="light-text">
    <div class="create-container">
      <h4 class="light-text"><spring:message code="sendToken.question"/></h4>
      <a href="<c:url value="/users/login"/>">
        <h5 class="secondary-color"><spring:message code="sendToken.link"/></h5>
      </a>
    </div>
  </div>
</div>
</body>
</html>