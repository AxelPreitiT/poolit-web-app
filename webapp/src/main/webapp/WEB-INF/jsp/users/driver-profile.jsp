<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
    <title><spring:message code="profile.title"/></title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/resources/css/users/profile.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body class="background-color">
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<div class="main-container">
    <c:url value="/users/profile" var="userProfileUrl"/>
    <jsp:include page="/WEB-INF/jsp/users/profile-container.jsp">
        <jsp:param name="user" value="${user}"/>
        <jsp:param name="role" value="USER"/>
        <jsp:param name="path" value="${userProfileUrl}"/>
    </jsp:include>
    <div class="List-properties-container">
        <c:url value="/users/created" var="createdTripsUrl"/>
        <jsp:include page="/WEB-INF/jsp/users/info-container.jsp">
            <jsp:param name="title" value="profile.nextTrips"/>
            <jsp:param name="btndesc" value="profile.nextTrips.btn"/>
            <jsp:param name="trips" value="${trips}"/>
            <jsp:param name="url" value="${createdTripsUrl}"/>
        </jsp:include>
        <c:url value="/users/created/history" var="createdHistoryTripsUrl"/>
        <jsp:include page="/WEB-INF/jsp/users/info-container.jsp">
            <jsp:param name="title" value="profile.historyTrips"/>
            <jsp:param name="btndesc" value="profile.historyTrips.btn"/>
            <jsp:param name="trips" value="${trips}"/>
            <jsp:param name="url" value="${createdHistoryTripsUrl}"/>
        </jsp:include>
        <div class="list-container">
            <div class="row-data">
                <h2><spring:message code="profile.myCars"/></h2>
            </div>
            <div class="data-content">
                <c:forEach items="${cars}" var="car">
                    <c:set var="car" value="${car}" scope="request"/>
                    <jsp:include page="/WEB-INF/jsp/users/car-container.jsp"/>
                </c:forEach>
            </div>
            <a href="<c:url value="/cars/create"/>">
                <div class="plus-btn">
                    <h3 class="text"><spring:message code="profile.createCar"/></h3>
                    <i class="h3 bi text bi-box-arrow-in-up-right"></i>
                </div>
            </a>
        </div>
    </div>
</div>
</body>
</html>

