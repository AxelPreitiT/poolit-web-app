<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <form action="${postUrl}" method="post" enctype="multipart/form-data">
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
                    <label for="email" class="form-label">Email</label>
                    <input type="email" id="email" name="email" class="form-control text">
                </div>
                <div class="user-info-item">
                    <label for="phone" class="form-label">Teléfono</label>
                    <input type="tel" id="phone" name="phone" class="form-control text">
                </div>
            </div>
            <div class="user-info-row">
                <div class="user-info-item">
                    <label for="password" class="form-label">Contraseña</label>
                    <input type="password" id="password" name="password" class="form-control text">
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
        </form>
    </div>
</body>
</html>
