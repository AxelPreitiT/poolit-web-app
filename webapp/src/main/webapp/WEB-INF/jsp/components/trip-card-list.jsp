<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link href="<c:url value="/resources/css/components/trip-card-list.css"/>" rel="stylesheet" type="text/css"/>

<div id="results-list" class="container">
    <div class="row">
        <div class="col-xl-6 col-sm-12">
            <c:url value="/trips/1" var="tripUrl"/>
            <jsp:include page="/WEB-INF/jsp/components/trip-card.jsp">
                <jsp:param name="url" value="${tripUrl}"/>
            </jsp:include>
        </div>
        <div class="col-xl-6 col-sm-12">
            <c:url value="/trips/2" var="tripUrl"/>
            <jsp:include page="/WEB-INF/jsp/components/trip-card.jsp">
                <jsp:param name="url" value="${tripUrl}"/>
            </jsp:include>
        </div>
    </div>
</div>
