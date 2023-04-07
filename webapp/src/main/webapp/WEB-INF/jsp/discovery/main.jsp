<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>

<html>
<head>
    <head>
        <title>Poolit</title>
        <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.css.jsp" />
        <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.icons.jsp" />
        <link href="<c:url value="/css/base/font-sizes.css"/>" rel="stylesheet">
        <link href="<c:url value="/css/base/colors.css"/>" rel="stylesheet">
        <link href="<c:url value="/css/discovery/main.css"/>" rel="stylesheet">
    </head>
</head>
<body>
    <div class="center-container">
        <div class="search-container">
            <h1>Hola</h1>
        </div>
        <div>
            <div class="location-input">
                <jsp:include page="city-selector.jsp">
                    <jsp:param name="id" value="originCity" />
                </jsp:include>
                <div class="form-floating">
                    <input type="text" class="form-control h5 text" id="originAddress" name="originAddress" placeholder="Av. del Libertador 1234">
                    <label for="originAddress" class="placeholder-text h5">Dirección</label>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
