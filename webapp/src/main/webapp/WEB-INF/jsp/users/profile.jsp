<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="cars" type="java.util.List<ar.edu.itba.paw.models.Car>" scope="request"/>

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
    <jsp:include page="/WEB-INF/jsp/users/profile-container.jsp"/>
    <div class="List-properties-container">
        <div class="list-container">
            <div class="row-data">
                <h2>Mis Autos</h2>
            </div>
            <div class="data-content">
                <c:forEach items="${cars}" var="car">
                    <c:set var="car" value="${car}" scope="request"/>
                    <jsp:include page="/WEB-INF/jsp/users/car-container.jsp"/>
                </c:forEach>
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
