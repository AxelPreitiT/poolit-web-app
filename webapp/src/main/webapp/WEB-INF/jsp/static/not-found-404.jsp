<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
  <head>
    <title>Página no encontrada</title>
    <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.css.jsp" />
    <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.icons.jsp" />
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/css/static/not-found.css"/>" rel="stylesheet">
  </head>
  <body class="background-bg-color">
    <div class="container-bg-color not-found-container">
      <div class="title-container">
        <i class="fa-solid fa-car-burst h1 danger"></i>
        <h1 class="danger title">¡Ups! Página no encontrada</h1>
      </div>
      <h3>No hemos podido hallar la parada, intente tomando otra ruta.</h3>
      <p>Código de error: <em>404 Not Found</em></p>
      <a class="button-style" href="<c:url value="/"/>">
        <button class="btn button-bg-color">
          <span class="light-text">Volver a la página principal</span>
        </button>
      </a>
    </div>
  </body>
</html>
