<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title><spring:message code="register.title"/></title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/resources/css/users/register.css"/>" rel="stylesheet">
    <script src="<c:url value="/resources/js/users/image-preview.js"/>" type="module"></script>
</head>
<body class="background-bg-color">
    <div class="main-container-style">
        <div id="banner-container">
            <img src="<c:url value="/resources/images/register/register-banner.jpg"/>" alt="banner" id="banner-image">
            <a id="banner-content" href="<c:url value="/"/>">
                <img src="<c:url value="/resources/images/poolit/poolit.svg"/>" alt="poolit" id="banner-logo">
            </a>
        </div>
        <div id="form-container" class="primary-bg-color">
            <c:url value="${postUrl}" var="createUser" />
            <form:form modelAttribute="createUserForm" cssClass="passenger-form" action="${createUser}" method="post" enctype="multipart/form-data">
                <div class="container" id="profile-image-container">
                    <div id="profile-image-title">
                        <h4 class="light-text"><spring:message code="register.profileImage"/></h4>
                    </div>
                    <div id="profile-image-display">
                        <label for="image-file" id="image-label"  class="secondary-bg-color shadow-btn button-style">
                            <i class="bi bi-image light-text h1"></i>
                        </label>
                        <form:input path="imageFile" type="file" id="image-file" name="image-file" class="form-control" accept="image/*"/>
                    </div>
                    <div class="error-row error-row-center mt-1">
                        <form:errors path="imageFile" cssClass="error-style danger max-width-fit" element="span"/>
                    </div>
                </div>
                <hr class="light-text">
                <div class="container" id="user-info-container">
                    <div id="user-info-title">
                        <h4 class="light-text"><spring:message code="register.personalInfo"/></h4>
                    </div>
                    <div id="user-info-input">
                        <div class="input-row">
                            <div class="input-group">
                                <spring:message code="user.name" var="namePlaceholder"/>
                                <form:input path="username" type="text" class="form-control form-control" id="username" name="username" placeholder="${namePlaceholder}"/>
                            </div>
                            <div class="input-group">
                                <spring:message code="user.surname" var="surnamePlaceholder"/>
                                <form:input path="surname" type="tel" class="form-control form-control" id="surname" placeholder='${surnamePlaceholder}'/>
                            </div>
                        </div>
                        <div class="error-row">
                            <form:errors path="username" cssClass="error-style danger" element="span"/>
                            <form:errors path="surname" cssClass="error-style danger error-style-right" element="span"/>
                        </div>
                        <div class="input-row">
                            <div class="input-group">
                                <spring:message code="user.email" var="emailPlaceholder"/>
                                <form:input path="email" type="text" class="form-control form-control" id="email" name="email" placeholder="${emailPlaceholder}"/>
                            </div>
                            <div class="input-group">
                                <spring:message code="user.phone" var="phonePlaceholder"/>
                                <form:input path="phone" type="text" class="form-control form-control" id="phone" name="phone" placeholder="${phonePlaceholder}"/>
                            </div>
                        </div>
                        <div class="error-row">
                            <form:errors path="email" cssClass="error-style danger" element="span"/>
                            <form:errors path="phone" cssClass="error-style danger error-style-right" element="span"/>
                        </div>
                        <div class="input-row">
                            <div class="input-group">
                                <spring:message code="register.password" var="passwordPlaceholder"/>
                                <form:input path="password" type="password" class="form-control form-control" id="password" name="password" placeholder="${passwordPlaceholder}"/>
                            </div>
                            <div class="input-group">
                                <spring:message code="register.secondPassword" var="repeatPasswordPlaceholder"/>
                                <form:input path="repeatPassword" type="password" class="form-control form-control" id="repeat-password" name="repeat-password" placeholder="${repeatPasswordPlaceholder}"/>
                            </div>
                        </div>
                        <div class="error-row">
                            <form:errors path="password" cssClass="error-style danger" element="span"/>
                            <form:errors path="repeatPassword" cssClass="error-style danger error-style-right" element="span"/>
                        </div>
                        <div class="error-row error-row-center">
                            <form:errors cssClass="error-style danger" element="span"/>
                        </div>
                        <div class="input-row input-row-start">
                            <spring:message code="user.district" var="districtPlaceholder"/>
                            <jsp:include page="/WEB-INF/jsp/components/city-selector.jsp">
                                <jsp:param name="id" value="bornCityId"/>
                                <jsp:param name="defaultText" value="${districtPlaceholder}"/>
                            </jsp:include>
                        </div>
                        <div class="error-row">
                            <form:errors path="bornCityId" cssClass="error-style danger" element="span"/>
                        </div>
                    </div>
                </div>
                <hr class="light-text">
                <div class="container" id="preferences-container">
                    <div id="preferences-title">
                        <h4 class="light-text"><spring:message code="register.preferences"/></h4>
                    </div>
                    <div class="preference-item">
                        <label class="light-text"><spring:message code="register.mailLanguage"/></label>
                        <div class="input-row">
                            <div class="input-group">
                                <form:select path="mailLocale" class="form-select" id="mail-locale" name="mail-locale">
                                    <form:option value="es"><spring:message code="register.spanish"/></form:option>
                                    <form:option value="en"><spring:message code="register.english"/></form:option>
                                </form:select>
                            </div>
                        </div>
                        <div class="error-row">
                            <form:errors path="mailLocale" cssClass="error-style danger" element="span"/>
                        </div>
                    </div>
                </div>
                <div class="user-info-row">
                    <div class="submit-row">
                        <form:button type="submit" class="btn button-color btn-lg"><span class="light-text"><spring:message code="register.btnString"/></span></form:button>
                    </div>
                </div>
            </form:form>
            <hr class="light-text">
            <div class="login-container">
                <h4 class="light-text"><spring:message code="register.toLogin"/></h4>
                <a href="<c:url value="/users/login"/>">
                    <h5 class="secondary-color"><spring:message code="register.toLogin.btn"/></h5>
                </a>
            </div>
        </div>
    </div>
    <script src="<c:url value="/resources/js/pages/register.js"/>" type="application/javascript"></script>
</body>
</html>
