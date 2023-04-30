<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>Creados - Próximos</title>
  <jsp:include page="/resources/external-resources.jsp"/>
  <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
</head>
<body class="background-color">
  <div id="navbar-container">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
  </div>
  <div class="main-container-style container-color">
    <div id="main-header-row">
      <h1 class="secondary-color">Viajes creados - Próximos</h1>
      <hr class="secondary-color">
    </div>
    <div id="trip-card-date-list-container">
      <c:set var="allowDelete" value="true" scope="request"/>
      <jsp:include page="/WEB-INF/jsp/components/trip-card-date-list.jsp"/>
    </div>
  </div>
</body>
</html>
