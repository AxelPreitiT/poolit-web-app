<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Búsqueda</title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/resources/css/search/search.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body class="background-color">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="main-container">
        <div class="row">
            <div class="col-md-4 col-lg-3">
                <c:url value="/search" var="searchUrl"/>
                <jsp:include page="/WEB-INF/jsp/components/search-filters.jsp">
                    <jsp:param name="url" value="${searchUrl}"/>
                </jsp:include>
            </div>
            <div class="col-md-8 col-lg-9">
                <jsp:include page="/WEB-INF/jsp/components/trip-order-by-selector.jsp"/>
                <jsp:include page="/WEB-INF/jsp/components/trip-card-list.jsp">
                    <jsp:param name="startDate" value="${searchTripForm.date}"/>
                    <jsp:param name="startTime" value="${searchTripForm.time}"/>
                    <jsp:param name="endDate" value="${searchTripForm.lastDate}"/>
                </jsp:include>
                <div id="total-results-row">
                    <span class="italic-text">
                        1-10 resultados de 100 encontrados
                    </span>
                </div>
                <jsp:include page="/WEB-INF/jsp/components/trip-card-list-pagination.jsp"/>
            </div>
        </div>
    </div>
</body>
</html>
