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
    <div class="container-bg-color main-container-style">
        <h1 class="text"><spring:message code="register.viewTitle"/></h1>
        <hr>
        <c:url value="${postUrl}" var="createUser" />
        <form:form modelAttribute="createUserForm" cssClass="passenger-form" action="${createUser}" method="post" enctype="multipart/form-data">
            <div class="user-info-row">
                <div class="user-info-item form-floating">
                    <form:input path="username" type="text" class="form-control text" id="username" name="username" placeholder="Av. del Libertador 1234"/>
                    <form:label path="username" for="username" class="placeholder-text"><spring:message code="user.name"/></form:label>
                    <div class="error-container">
                        <form:errors path="username" cssClass="formError" element="p"/>
                    </div>
                </div>
                <div class="user-info-item form-floating">
                    <form:input path="surname" type="tel" class="form-control text h5 input-style" id="surname" placeholder='<spring:message code="trip.phone"/>'/>
                    <form:label path="surname" for="surname" class="placeholder-text"><spring:message code="user.surname"/></form:label>
                    <div class="error-container">
                        <form:errors path="surname" cssClass="formError" element="p"/>
                    </div>
                </div>
            </div>
            <div class="user-info-row">
                <div class="user-info-item form-floating">
                    <form:input path="email" class="form-control text h5 input-style" id="email" placeholder='<spring:message code="trip.email"/>'/>
                    <form:label path="email" for="email" class="placeholder-text"><spring:message code="trip.email"/></form:label>
                    <div class="error-container">
                        <form:errors path="email" cssClass="formError" element="p"/>
                    </div>
                </div>
                <div class="user-info-item form-floating">
                    <form:input path="phone" type="tel" class="form-control text h5 input-style" id="phone" placeholder='<spring:message code="trip.phone"/>'/>
                    <form:label path="phone" for="phone" class="placeholder-text"><spring:message code="trip.phone"/></form:label>
                    <div class="error-container">
                        <form:errors path="phone" cssClass="formError" element="p"/>
                    </div>
                </div>
            </div>
            <div class="user-info-row">
                <div class="user-info-item form-floating">
                    <form:input path="password" type="password" class="form-control text h5 input-style" id="password" placeholder='<spring:message code="trip.phone"/>'/>
                    <form:label path="password" for="password" class="placeholder-text"><spring:message code="register.password"/></form:label>
                    <div class="error-container">
                        <form:errors path="password" cssClass="formError" element="p"/>
                        <form:errors cssClass="formError" element="p"/>
                    </div>
                </div>
                <div class="user-info-item">
                    <div class="user-info-item form-floating">
                        <form:input path="repeatPassword" type="password" class="form-control text h5 input-style" id="repeatPassword" placeholder='<spring:message code="trip.phone"/>'/>
                        <form:label path="repeatPassword" for="repeatPassword" class="placeholder-text"><spring:message code="register.secondPassword"/></form:label>
                        <div class="error-container">
                            <form:errors path="repeatPassword" cssClass="formError text-sm text-red-500" element="p"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="user-info-row">
                <div class="user-info-item form-floating">
                    <form:input path="birthdate" type="date" id="birthdate" cssClass="form-control h5 text"/>
                    <form:label path="birthdate" for="birthdate" cssClass="placeholder-text h5"><spring:message code="user.birthday"/></form:label>
                    <div class="error-container">
                        <form:errors path="birthdate" cssClass="formError" element="p"/>
                    </div>
                </div>
                <div class="user-info-item form-floating">
                    <form:select path="bornCityId" id="bornCityId" class="form-select h6 text" name="Origen">
                        <c:forEach items="${cities}" var="city">
                            <form:option value="${city.id}"><c:out value="${city.name}"/></form:option>
                        </c:forEach>
                    </form:select>
                    <form:label path="bornCityId" for="bornCityId" class="placeholder-text h5"><spring:message code="user.district"/></form:label>
                    <div class="error-container">
                        <form:errors path="bornCityId" cssClass="formError" element="p"/>
                    </div>
                </div>
            </div>
            <div class="user-info-row">
                <div class="user-info-item">
                    <label for="imageFile" class="form-label"><spring:message code="user.photo"/></label>
                    <form:input path="imageFile" type="file" id="imageFile" name="imageFile" class="form-control" accept="image/*" onChange="updateImageDisplay"/>
                    <div class="preview">
                        <p></p>
                    </div>
                    <form:errors path="imageFile" cssClass="formError" element="p"/>
                </div>
            </div>
            <div class="user-info-row">
                <div class="submit-row">
                    <form:button type="submit" class="btn button-color btn-lg"><span class="light-text"><spring:message code="register.btnString"/></span></form:button>
                </div>
            </div>
        </form:form>
        <hr>
        <div class="login-container">
                <h4><spring:message code="register.toLogin"/></h4>
                <a href="<c:url value="/users/login"/>">
                    <h5><spring:message code="register.toLogin.btn"/></h5>
                </a>
        </div>
    </div>
</body>
</html>