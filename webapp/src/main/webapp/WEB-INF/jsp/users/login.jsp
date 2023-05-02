<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

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
            <div class="img-container">
                <img src="<c:url value="/resources/images/poolit/poolit.svg"/>" alt="POOLIT" class="brand-logo">
            </div>
        </div>
        <div class="main-container-style container-bg-color">
            <h1 class="text"><spring:message code="login.title"/></h1>
            <hr>
            <form action="${postUrl}" method="post">
                <div class="user-info-row">
                    <div class="user-info-item">
                        <div class="form-floating">
                            <input type="email" id="email" name="email" class="form-control text" placeholder="Email">
                            <label for="email" class="placeholder-text"><spring:message code="user.email"/></label>
                        </div>
                    </div>
                </div>
                <div class="user-info-row">
                    <div class="user-info-item">
                        <div class="form-floating">
                            <input type="password" id="password" name="password" class="form-control text" placeholder=".">
                            <label for="password" class="placeholder-text"><spring:message code="register.password"/></label>
                        </div>
                    </div>
                </div>
                <div class="user-info-row">
                    <div class="user-info-item form-check">
                        <input class="form-check-input" type="checkbox" id="keepLoggedIn" name="rememberme">
                        <label class="form-check-label" for="keepLoggedIn">
                            <spring:message code="login.rememberMe"/>
                        </label>
                    </div>
                </div>
                <div class="user-info-row">
                    <div class="error-container">
                        <c:if test="${param.error != null}"><span class="error"><spring:message code="login.error"/></span></c:if>
                    </div>
                </div>
                <div class="d-grid gap-2 submit-row">
                    <button type="submit" class="btn button-color btn-lg">
                        <span class="light-text"><spring:message code="login.title"/></span>
                    </button>
                </div>
            </form>
            <hr>
            <div class="create-container">
                <h4><spring:message code="login.register"/></h4>
                <a href="<c:url value="/users/create"/>">
                    <h5><spring:message code="login.registerBtn"/></h5>
                </a>
            </div>
        </div>
    </div>
</body>
</html>
