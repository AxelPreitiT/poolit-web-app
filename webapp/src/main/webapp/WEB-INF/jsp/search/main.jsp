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
  <link href="<c:url value="/css/search/main.css"/>" rel="stylesheet">
</head>
<body>
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<div class="body-style">
  <c:url value="/search" var="postPath"/>
  <div class="form-container">
    <form:form modelAttribute="searchForm" action="${postPath}" method="get" class="full-width">
      <div class="search-container inline-container">
        <div class="location-container">
          <div class="location-input">
            <div class="form-floating">
              <form:select path="originCityId" id="originCity" class="form-select h6 text" name="Origen">
                <c:forEach items="${cities}" var="city">
                  <form:option value="${city.id}"><c:out value="${city.name}"/></form:option>
                </c:forEach>
              </form:select>
              <form:label path="originCityId" for="originCity" cssClass="placeholder-text h5"><spring:message code="trip.origin"/></form:label>
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
              <form:label path="destinationCityId" for="destinationCity" class="placeholder-text h5"><spring:message code="trip.destination"/></form:label>
              <form:errors path="destinationCityId" cssClass="formError" element="p"/>
            </div>
          </div>
        </div>
        <div>
          <div class="time-container">
            <!-- <i class="bi bi-calendar h2"></i> -->
            <div class="form-floating date-selector">
              <form:input path="dateTime" type="datetime-local" id="date" cssClass="form-control h5 text"/>
              <form:label path="dateTime" for="date" cssClass="placeholder-text h5"><spring:message code="trip.date"/></form:label>
            </div>
<%--            <div class="form-floating time-selector">--%>
<%--              <form:input path="time" type="time" id="time" cssClass="form-control h5 text"/>--%>
<%--              <form:label path="time" for="time" cssClass="placeholder-text h5"><spring:message code="trip.time"/></form:label>--%>
<%--            </div>--%>
            <div class="error-container">
              <form:errors path="dateTime" cssClass="formError" element="p"/>
<%--              <form:errors path="date" cssClass="formError" element="p"/>--%>
              <form:errors cssClass="formError" element="p"/>
            </div>
          </div>
            <%--                        Error for dateAndTime--%>
        </div>
      </div>
      <div class="button-container">
        <form:button type="submit" class="btn btn-primary btn-lg btn-search"><spring:message code="discovery.btnSearch"/></form:button>
      </div>
      </div>
    </form:form>
  </div>
  <c:if test="${trips.size()==0}">
    <h4 class="no-results">Ups! Parece que no hay resultados para tu busqueda</h4>
  </c:if>
  <c:if test="${trips.size()>0}">
    <h2>Resultados:</h2>
    <div class="result-container">
      <c:forEach items="${trips}" var="trip">
        <c:set var="trip" value="${trip}" scope="request"/>
        <jsp:include page="../discovery/travel-card.jsp">
          <jsp:param name="id" value="card"/>
        </jsp:include>
      </c:forEach>
    </div>
      <c:url value="" var="baseUrl">
        <c:forEach var="p" items="${param}">
          <c:if test="${!(param.key eq 'page')}">
            <c:param name="${p.key}" value="${p.value}"/>
          </c:if>
        </c:forEach>
      </c:url>
      <jsp:include page="/WEB-INF/jsp/components/page-selector.jsp">
        <jsp:param name="totalPages" value="10"/>
        <jsp:param name="currentPage" value="1"/>
        <jsp:param name="baseUrl" value="${baseUrl}"/>
      </jsp:include>
  </c:if>
</div>
</body>
</html>