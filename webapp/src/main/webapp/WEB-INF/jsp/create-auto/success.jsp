<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<html>
<head>
    <title>Success!</title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/resources/css/select-trip/success.css"/>" rel="stylesheet">
</head>
<body class="background-color">
    <div id="navbar-container">
        <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    </div>
    <div class="main-container-style container-color">
        <div id="footer-container">
            <div id="button-container">
                <c:url value="/" var="homeUrl"/>
                <a href="${homeUrl}">
                    <button id="home-button" type="submit" class="btn button-style button-color shadow-btn">
                        <i class="bi bi-house-fill light-text h4"></i>
                        <span class="button-text-style light-text h3">Inicio</span>
                    </button>
                </a>
            </div>
        </div>
    </div>
    <div id="toast-container">
        <jsp:include page="/WEB-INF/jsp/components/success-toast.jsp">
            <jsp:param name="title" value="¡POOLIT!"/>
            <jsp:param name="message" value="Creaste un auto exitosamente!"/>
        </jsp:include>
    </div>
</body>
</html>