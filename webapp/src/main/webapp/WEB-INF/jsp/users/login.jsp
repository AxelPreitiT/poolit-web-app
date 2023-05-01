<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Iniciar sesión</title>
    <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.css.jsp" />
    <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.icons.jsp" />
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
            <h1 class="text">Iniciar sesión</h1>
            <hr>
            <form action="${postUrl}" method="post">
                <div class="user-info-row">
                    <div class="user-info-item">
                        <div class="form-floating">
                            <input type="email" id="email" name="email" class="form-control text" placeholder="Email">
                            <label for="email" class="placeholder-text">Email</label>
                        </div>
                    </div>
                </div>
                <div class="user-info-row">
                    <div class="user-info-item">
                        <div class="form-floating">
                            <input type="password" id="password" name="password" class="form-control text" placeholder="Contraseña">
                            <label for="password" class="placeholder-text">Contraseña</label>
                        </div>
                    </div>
                </div>
                <div class="user-info-row">
                    <div class="user-info-item form-check">
                        <input class="form-check-input" type="checkbox" id="keepLoggedIn" name="rememberme">
                        <label class="form-check-label" for="keepLoggedIn">
                            Mantener la sesión iniciada
                        </label>
                    </div>
                </div>
                <div class="user-info-row">
                    <div class="error-container">
                        <c:if test="${param.error != null}"><span class="error">Usuario y contraseña invalidos</span></c:if>
                    </div>
                </div>
                <div class="d-grid gap-2 submit-row">
                    <button type="submit" class="btn button-color btn-lg">
                        <span class="light-text">Iniciar sesión</span>
                    </button>
                </div>
            </form>
            <hr>
            <div class="create-container">
                <h4>¿No tienes una cuenta aún?</h4>
                <a href="/users/create">
                    <h5>Regístrate</h5>
                </a>
            </div>
        </div>
    </div>
</body>
</html>
