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
            <a id="img-container" href="<c:url value="/"/>">
                <img src="<c:url value="/resources/images/poolit/poolit.svg"/>" alt="POOLIT" class="brand-logo">
            </a>
        </div>
        <div class="main-container-style primary-bg-color">
            <h2 class="light-text"><spring:message code="login.title"/></h2>
            <hr class="light-text" id="title-hr">
            <c:url value="/users/login" var="loginUrl"/>
            <form action="${loginUrl}" method="post">
                <div class="user-info-row">
                    <div class="user-info-item">
                        <div class="form-floating">
                            <input type="email" id="email" name="email" class="form-control" placeholder="Email">
                            <label for="email" class="placeholder-text"><spring:message code="user.email"/></label>
                        </div>
                    </div>
                </div>
                <div class="user-info-row">
                    <div class="user-info-item">
                        <div class="form-floating">
                            <input type="password" id="password" name="password" class="form-control" placeholder=".">
                            <label for="password" class="placeholder-text"><spring:message code="register.password"/></label>
                        </div>
                    </div>
                </div>
                <div class="user-info-row">
                    <div class="user-info-item form-check">
                        <input class="form-check-input" type="checkbox" id="keepLoggedIn" name="rememberme">
                        <label class="form-check-label light-text" for="keepLoggedIn">
                            <spring:message code="login.rememberMe"/>
                        </label>
                    </div>
                </div>
                <c:if test="${param.error != null}">
                    <div class="user-info-row">
                        <div class="error-container">
                            <i class="bi bi-exclamation-circle-fill warning"></i>
                            <span class="warning"><spring:message code="login.error"/></span>
                        </div>
                    </div>
                </c:if>
                <div class="d-grid gap-2 submit-row">
                    <button type="submit" class="btn button-color btn-lg">
                        <span class="light-text h5"><spring:message code="login.btnString"/></span>
                    </button>
                </div>
            </form>
            <hr class="light-text">
            <div class="create-container">
                <h4 class="light-text"><spring:message code="login.register"/></h4>
                <a href="<c:url value="/users/create"/>">
                    <h5 class="secondary-color"><spring:message code="login.registerBtn"/></h5>
                </a>
            </div>
        </div>
        <div class="sent-email">
            <h6><spring:message code="login.sentToken"/></h6>
            <a href="<c:url value="/users/sendToken"/>">
                <h6 class="secondary-color"><spring:message code="login.SentToken.btn"/></h6>
            </a>
        </div>
    </div>
    <c:if test="${!(empty sentToken) && sentToken}">
        <div id="toast-container">
            <jsp:include page="/WEB-INF/jsp/components/success-toast.jsp">
                <jsp:param name="title" value="sendToken.success.title"/>
                <jsp:param name="message" value="sendToken.success.message"/>
            </jsp:include>
        </div>
    </c:if>
    <c:if test="${!(empty alreadyValidation) && alreadyValidation}">
        <div id="toast-container">
            <jsp:include page="/WEB-INF/jsp/components/success-toast.jsp">
                <jsp:param name="title" value="sendToken.alReadyValidation.title"/>
                <jsp:param name="message" value="sendToken.alReadyValidation.message"/>
            </jsp:include>
        </div>
    </c:if>
</body>
</html>
