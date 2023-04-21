<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Crear cuenta</title>
    <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.css.jsp" />
    <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.icons.jsp" />
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/css/users/register.css"/>" rel="stylesheet">
</head>
<body class="background-bg-color">
    <div class="container-bg-color main-container-style">
        <h1 class="text">Crear cuenta</h1>
        <hr>
        <c:url value="${postUrl}" var="bookTripUrl" />
        <form:form modelAttribute="createUserForm" cssClass="passenger-form" action="${bookTripUrl}" method="post">
            <div class="user-info-row">
                <div class="user-info-item">
                    <label for="name" class="form-label">Nombre</label>
                    <input type="text" id="name" name="name" class="form-control text">
                </div>
                <div class="user-info-item">
                    <label for="surname" class="form-label">Apellido</label>
                    <input type="text" id="surname" name="surname" class="form-control text">
                </div>
            </div>
            <div class="user-info-row">
                <div class="user-info-item">
                    <form:input path="email" class="form-control text h5 input-style" id="email" placeholder='<spring:message code="trip.email"/>'/>
                    <form:label path="email" for="email" class="placeholder-text"><spring:message code="trip.email"/></form:label>
                    <form:errors path="email" cssClass="formError" element="p"/>
                </div>
                <div class="user-info-item">
                    <form:input path="phone" type="tel" class="form-control text h5 input-style" id="phone" placeholder='<spring:message code="trip.phone"/>'/>
                    <form:label path="phone" for="phone" class="placeholder-text"><spring:message code="trip.phone"/></form:label>
                    <form:errors path="phone" cssClass="formError" element="p"/>
                </div>
            </div>
            <div class="user-info-row">
                <div class="user-info-item">
                    <form:input path="password" type="password" class="form-control text h5 input-style" id="password" placeholder='<spring:message code="trip.phone"/>'/>
                    <form:label path="password" for="password" class="placeholder-text">Contraseña</form:label>
                    <form:errors path="password" cssClass="formError" element="p"/>
                </div>
                <div class="user-info-item">
                    <label for="repeatPassword" class="form-label">Repetir contraseña</label>
                    <input type="password" id="repeatPassword" name="repeatPassword" class="form-control text">
                </div>
            </div>
            <div class="user-info-row">
                <div class="user-info-item">
                    <label for="birthDate" class="form-label">Fecha de nacimiento</label>
                    <input type="date" id="birthDate" name="birthDate"  class="form-control text">
                </div>
                <div class="user-info-item">
                    <label for="city" class="form-label">Ciudad</label>
                    <select class="form-select text" id="city" name="city">
                        <option value="" selected>Seleccionar</option>
                        <jsp:useBean id="cities" scope="request" type="java.util.List"/>
                        <c:forEach items="${cities}" var="city">
                            <option value="<c:out value="${city.id}"/>"><c:out value="${city.name}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="user-info-row">
                <div class="user-info-item">
                    <label for="profilePicture" class="form-label">Foto de perfil</label>
                    <input type="file" id="profilePicture" name="profilePicture" class="form-control" accept="image/jpeg, image/png, image/gif">
                </div>
            </div>
            <div class="submit-row">
                <button type="submit" class="btn button-bg-color button-style">
                    <span class="light-text">Registrarse</span>
                </button>
            </div>
        </form:form>
    </div>
</body>
</html>
