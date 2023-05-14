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
        <jsp:param name="rating" value="${rating}"/>
    </jsp:include>
    <div class="List-properties-container">
        <ul class="nav nav-justified-pills mb-3" id="pills-tab" role="tablist">
          <li class="nav-item" role="presentation">
            <button class="nav-link active" id="pills-driver-tab" data-bs-toggle="pill" data-bs-target="#pills-driver" type="button" role="tab" aria-controls="pills-driver" aria-selected="true"><spring:message code="profile.dataDriver"/></button>
          </li>
          <li class="nav-item" role="presentation">
            <button class="nav-link" id="pills-passanger-tab" data-bs-toggle="pill" data-bs-target="#pills-passanger" type="button" role="tab" aria-controls="pills-passanger" aria-selected="false"><spring:message code="profile.dataPassanger"/></button>
          </li>
        </ul>
        <div class="tab-content" id="pills-tabContent">
          <div class="tab-pane fade show active" id="pills-driver" role="tabpanel" aria-labelledby="pills-driver-tab" tabindex="0">
            <c:url value="/trips/created" var="createdTripsUrl"/>
                    <c:set var="trips" value="${futureTrips}" scope="request"/>
                    <jsp:include page="/WEB-INF/jsp/users/info-container.jsp">
                        <jsp:param name="title" value="profile.nextTrips"/>
                        <jsp:param name="btndesc" value="profile.nextTrips.btn"/>
                        <jsp:param name="trips" value="${futureTrips}"/>
                        <jsp:param name="url" value="${createdTripsUrl}"/>
                    </jsp:include>
                    <c:url value="/trips/created/history" var="createdHistoryTripsUrl"/>
                    <c:set var="trips" value="${pastTrips}" scope="request"/>
                    <jsp:include page="/WEB-INF/jsp/users/info-container.jsp">
                        <jsp:param name="title" value="profile.historyTrips"/>
                        <jsp:param name="btndesc" value="profile.historyTrips.btn"/>
                        <jsp:param name="trips" value="${pastTrips}"/>
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
                    </div></div>
          <div class="tab-pane fade" id="pills-passanger" role="tabpanel" aria-labelledby="pills-passanger-tab" tabindex="0">
                <c:url value="/trips/reserved" var="reservedTripsUrl"/>
                <c:set var="trips" value="${futureTripsPassanger}" scope="request"/>
                <jsp:include page="/WEB-INF/jsp/users/info-container.jsp">
                  <jsp:param name="title" value="nextTrips.reserved.title"/>
                  <jsp:param name="btndesc" value="profile.nextTrips.btn"/>
                  <jsp:param name="trips" value="${futureTripsPassanger}"/>
                  <jsp:param name="url" value="${reservedTripsUrl}"/>
                </jsp:include>
                <c:url value="/trips/reserved/history" var="reservedTripsHistoryUrl"/>
                <c:set var="trips" value="${pastTrips}" scope="request"/>
                <jsp:include page="/WEB-INF/jsp/users/info-container.jsp">
                  <jsp:param name="title" value="historyTrips.reserved.title"/>
                  <jsp:param name="btndesc" value="profile.historyTrips.btn"/>
                  <jsp:param name="trips" value="${pastTrips}"/>
                  <jsp:param name="url" value="${reservedTripsHistoryUrl}"/>
                </jsp:include>
            </div>
        </div>


    </div>
    <c:if test="${!(empty carAdded) && carAdded}">
        <div id="toast-container">
            <jsp:include page="/WEB-INF/jsp/components/success-toast.jsp">
                <jsp:param name="title" value="createCar.success.toast.title"/>
                <jsp:param name="message" value="createCar.success.toast.message"/>
            </jsp:include>
        </div>
    </c:if>
</body>
</html>

