<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
        <c:url value="/trips" var="postPath"/>
        <form:form modelAttribute="registerForm" action="${postPath}" method="post" class="full-width">
            <div class="search-container inline-container">
                <div class="location-container">
                    <div class="location-input">
                        <div class="form-floating">
                            <form:select path="originCityId" id="originCity" class="form-select h6 text" name="Origen">
                                <c:forEach items="${cities}" var="city">
                                    <form:option value="${city.id}"><c:out value="${city.name}"/></form:option>
                                </c:forEach>
                            </form:select>
                            <form:label path="originCityId" for="originCity" cssClass="placeholder-text h5">Origen</form:label>
                            <form:errors path="originCityId" cssClass="formError" element="p"/>
                        </div>
                    </div>
                    <div class="dotten-line">
                        <hr>
                    </div>
                    <div class="location-input ">
                        <div class="form-floating">
                            <form:select path="destinationCityId" id="destinationCity" class="form-select h6 text" name="Origen">
                                <c:forEach items="${cities}" var="city">
                                    <form:option value="${city.id}"><c:out value="${city.name}"/></form:option>
                                </c:forEach>
                            </form:select>
                            <form:label path="destinationCityId" for="destinationCity" class="placeholder-text h5">Destino</form:label>
                            <form:errors path="destinationCityId" cssClass="formError" element="p"/>
                        </div>
                    </div>
                </div>
                <div>
                    <div class="time-container">
                        <!-- <i class="bi bi-calendar h2"></i> -->
                        <div class="form-floating date-selector">
                            <form:input path="date" type="date" id="date" cssClass="form-control h5 text"/>
                            <form:label path="date" for="date" cssClass="placeholder-text h5">Fecha</form:label>
                            <form:errors path="date" cssClass="formError" element="p"/>

                        </div>
                        <div class="form-floating time-selector">
                            <form:input path="time" type="time" id="time" cssClass="form-control h5 text"/>
                            <form:label path="time" for="time" cssClass="placeholder-text h5">Horario</form:label>
                            <form:errors path="time" cssClass="formError" element="p"/>
                        </div>
                    </div>
                        <%--                        Error for dateAndTime--%>
                    <form:errors cssClass="formError" element="p"/>
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
