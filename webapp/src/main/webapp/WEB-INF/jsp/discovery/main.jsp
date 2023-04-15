<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
        <title><spring:message code="discovery.title"/></title>
        <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.css.jsp" />
        <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.icons.jsp" />
        <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
        <link href="<c:url value="/css/discovery/main.css"/>" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="body-style">
        <div class="full-width">
            <div class="search-container inline-container">
                <div class="location-container">
                    <div class="location-input">
                        <jsp:include page="/WEB-INF/jsp/components/city-selector.jsp">
                            <jsp:param name="id" value="originCity" />
                        </jsp:include>
                    </div>
                    <div class="dotten-line">
                        <hr>
                    </div>
                    <div class="location-input">
                        <jsp:include page="/WEB-INF/jsp/components/city-selector.jsp">
                            <jsp:param name="id" value="destinationCity" />
                        </jsp:include>
                    </div>
                </div>
                <div class="time-container">
                    <!-- <i class="bi bi-calendar h2"></i> -->
                    <div class="form-floating date-selector">
                        <input type="date" class="form-control h5 text" id="date" name="date">
                        <label for="date" class="placeholder-text h5"><spring:message code="trip.date"/></label>
                    </div>
                    <div class="form-floating time-selector">
                        <input type="time" class="form-control h5 text" id="time" name="time">
                        <label for="time" class="placeholder-text h5"><spring:message code="trip.time"/></label>
                    </div>
                </div>
            </div>
            <div class="button-container">
                <button type="button" class="btn btn-primary btn-lg btn-search"><spring:message code="discovery.btnSearch"/></button>
            </div>
        </div>
        <div class="result-container">
            <c:forEach items="${trips}" var="trip">
                <c:set var="trip" value="${trip}" scope="request"/>
                <jsp:include page="travel-card.jsp">
                    <jsp:param name="id" value="card"/>
                </jsp:include>
            </c:forEach>
        </div>
    </div>
</body>
</html>
