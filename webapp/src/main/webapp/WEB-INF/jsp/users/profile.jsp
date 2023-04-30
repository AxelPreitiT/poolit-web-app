<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Profile</title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/resources/css/users/profile.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body class="background-color">
  <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
  <div class="main-container">
    <jsp:include page="/WEB-INF/jsp/users/profile-container.jsp">
        <jsp:param name="user" value="${userLogged}"/>
    </jsp:include>
    <div class="List-properties-container">
        <div class="list-container">
            <div class="row-data">
                <h2>Mis Autos</h2>
            </div>
            <div class="data-content">
                <jsp:include page="/WEB-INF/jsp/users/car-container.jsp"/>
                <jsp:include page="/WEB-INF/jsp/users/car-container.jsp"/>
                <jsp:include page="/WEB-INF/jsp/users/car-container.jsp"/>
                <jsp:include page="/WEB-INF/jsp/users/car-container.jsp"/>
                <jsp:include page="/WEB-INF/jsp/users/car-container.jsp"/>
            </div>
        </div>
        <jsp:include page="/WEB-INF/jsp/users/info-container.jsp">
            <jsp:param name="title" value="Proximos viajes"/>
            <jsp:param name="btndesc" value="Ver todos los proximos viajes"/>
        </jsp:include>
        <jsp:include page="/WEB-INF/jsp/users/info-container.jsp">
            <jsp:param name="title" value="Viajes realizados"/>
            <jsp:param name="btndesc" value="Ver todos los viajes realizados"/>
        </jsp:include>
    </div>
  </div>
</body>
</html>
