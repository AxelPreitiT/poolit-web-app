<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
        <title>Poolit</title>
        <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.css.jsp" />
        <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.icons.jsp" />
        <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
        <link href="<c:url value="/css/discovery/main.css"/>" rel="stylesheet">
</head>
<body>
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="body-style">
        <c:url value="/trips" var="postPath"/>
        <form:form modelAttribute="registerForm" action="${postPath}" method="post" class="full-width">
            <div class="search-container inline-container">
                <div class="location-container">
                    <div class="location-input">
                        <form:select path="originCityId" id="orignCity" class="form-select h6 text" name="Origen">
                            <c:forEach items="${cities}" var="city">
                                <form:option value="${city.id}"><c:out value="${city.name}"/></form:option>
                            </c:forEach>
                        </form:select>
<%--                        <form:label path="originCityId" for="orignCity" class="placeholder-text h5">Origen</form:label>--%>
                        <form:errors path="originCityId" cssClass="formError" element="p"/>
                    </div>
                    <div class="dotten-line">
                        <hr>
                    </div>
                    <div class="location-input">
                        <form:select path="destinationCityId" id="orignCity" class="form-select h6 text" name="Origen">
                            <c:forEach items="${cities}" var="city">
                                <form:option value="${city.id}"><c:out value="${city.name}"/></form:option>
                            </c:forEach>
                        </form:select>
                            <%--                        <form:label path="originCityId" for="orignCity" class="placeholder-text h5">Origen</form:label>--%>
                        <form:errors path="destinationCityId" cssClass="formError" element="p"/>
                    </div>
                </div>
                <div class="time-container">
                    <!-- <i class="bi bi-calendar h2"></i> -->
                    <div class="form-floating date-selector">
                        <form:label path="date" class="placeholder-text h5">Fecha</form:label>
                        <form:input path="date" type="date" class="form-control h5 text"/>
                        <form:errors path="date" cssClass="formError" element="p"/>
                    </div>
                    <div class="form-floating time-selector">
                        <form:label path="time" class="placeholder-text h5">Horario</form:label>
                        <form:input path="time" type="time" class="form-control h5 text"/>
                        <form:errors path="time" cssClass="formError" element="p"/>
                    </div>
                </div>
            </div>
            <div class="button-container">
                <form:button type="submit" class="btn btn-primary btn-lg btn-search">Buscar</form:button>
            </div>
            </form:form>
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
