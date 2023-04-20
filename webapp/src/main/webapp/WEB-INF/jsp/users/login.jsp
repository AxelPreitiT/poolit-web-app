<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Iniciar sesión</title>
    <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.css.jsp" />
    <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.icons.jsp" />
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/css/users/login.css"/>" rel="stylesheet">
</head>
<body class="background-bg-color">
    <div class="main-container-style container-bg-color">
        <h1 class="text">Iniciar sesión</h1>
        <hr>
        <form action="${postUrl}" method="post">
            <div class="user-info-row">
                <div class="user-info-item">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" id="email" name="email" class="form-control text">
                </div>
            </div>
            <div class="user-info-row">
                <div class="user-info-item">
                    <label for="password" class="form-label">Contraseña</label>
                    <input type="password" id="password" name="password" class="form-control text">
                </div>
            </div>
            <div class="user-info-row">
                <div class="user-info-item form-check">
                    <input class="form-check-input" type="checkbox" id="keepLoggedIn">
                    <label class="form-check-label" for="keepLoggedIn">
                        Mantener la sesión iniciada
                    </label>
                </div>
            </div>
            <div class="submit-row">
                <button type="submit" class="btn button-bg-color button-style">
                    <span class="light-text">Iniciar sesión</span>
                </button>
            </div>
        </form>
    </div>
</body>
</html>
