<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<link href="<c:url value="/resources/css/components/search-filters.css"/>" rel="stylesheet" type="text/css"/>
<script src="<c:url value="/resources/js/components/searchFilters.js"/>" type="module"></script>

<!-- Beans:
        - searchTripForm: form backing object
-->

<!-- Params:
        - url: url to send the form
-->

<jsp:useBean id="searchTripForm" type="ar.edu.itba.paw.webapp.form.SearchTripForm" scope="request"/>

<form:form modelAttribute="searchTripForm" method="get" action="${param.url}" cssClass="form-style primary-bg-color">
    <div class="filter-container">
        <div class="route-filter">
            <div class="title-row">
                <i class="bi bi-geo-alt-fill light-text h5"></i>
                <h5 class="light-text"><spring:message code="searchFilters.route"/></h5>
            </div>
            <div class="input-container">
                <div class="input-row">
                    <div class="input-plus-error-row">
                        <div id="origin-city-selector">
                            <spring:message code="searchFilters.origin" var="originDefaultText"/>
                            <jsp:include page="/WEB-INF/jsp/components/city-selector.jsp">
                                <jsp:param name="id" value="originCityId"/>
                                <jsp:param name="defaultText" value="${originDefaultText}"/>
                            </jsp:include>
                        </div>
                        <form:errors path="originCityId" cssClass="formError" element="p"/>
                    </div>
                    <div class="route-arrow">
                        <i class="bi bi-arrow-right light-text"></i>
                    </div>
                    <div class="input-plus-error-row">
                        <div id="destination-city-selector">
                            <spring:message code="searchFilters.destination" var="destinationDefaultText"/>
                            <jsp:include page="/WEB-INF/jsp/components/city-selector.jsp">
                                <jsp:param name="id" value="destinationCityId"/>
                                <jsp:param name="defaultText" value="${destinationDefaultText}"/>
                            </jsp:include>
                        </div>
                        <form:errors path="destinationCityId" cssClass="formError" element="p"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="date-filter">
            <div class="title-row">
                <i class="bi bi-calendar light-text h5"></i>
                <h5 class="light-text"><spring:message code="searchFilters.date"/></h5>
            </div>
            <div class="input-container">
                <div class="input-row">
                    <div class="input-plus-error-row">
                        <div class="input-group input-group-sm" id="first-date-picker">
                            <button type="button" class="btn button-color">
                                <i class="bi bi-calendar-fill light-text"></i>
                            </button>
                            <spring:message code="searchFilters.date" var="dateText"/>
                            <form:input path="date" cssClass="form-control form-control-sm" id="date" name="date" placeholder="${dateText}"/>
                        </div>
                        <form:errors path="date" cssClass="formError" element="p"/>
                    </div>
                    <div class="input-plus-error-row">
                        <div class="input-group input-group-sm" id="time-picker">
                            <button type="button" class="btn button-color">
                                <i class="bi bi-clock-fill light-text"></i>
                            </button>
                            <spring:message code="searchFilters.time" var="timeText"/>
                            <form:input path="time" cssClass="form-control form-control-sm" id="time" name="time" placeholder="${timeText}"/>
                        </div>
                        <form:errors path="time" cssClass="formError" element="p"/>
                    </div>
                </div>
                <div class="input-row collapse" id="day-repeat-container">
                    <i class="bi bi-arrow-repeat light-text"></i>
                    <span class="italic-text light-text" id="day-repeat-text"></span>
                </div>
                <div class="input-row">
                    <div class="input-plus-error-row">
                        <div class="input-group input-group-sm" id="last-date-picker">
                            <button type="button" class="btn button-color" id="last-date-button" disabled>
                                <i class="bi bi-calendar-fill light-text"></i>
                            </button>
                            <spring:message code="searchFilters.lastDate" var="lastDateText"/>
                            <form:input path="lastDate" cssClass="form-control form-control-sm" id="last-date" name="last-date" placeholder="${lastDateText}" disabled="true"/>
                        </div>
                        <form:errors path="lastDate" cssClass="formError" element="p"/>
                    </div>
                    <div class="input-group input-group-sm">
                        <div class="input-group-text" id="is-multitrip-container">
                            <form:checkbox path="multitrip" id="is-multitrip" cssClass="form-check-input mt-0"/>
                            <div id="is-multitrip-text">
                                <spring:message code="searchFilters.recurrentTrip"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="price-filter">
            <div class="title-row">
                <i class="bi bi-currency-dollar light-text h5"></i>
                <h5 class="light-text"><spring:message code="searchFilters.price"/></h5>
            </div>
            <div class="input-container">
                <div class="input-row">
                    <div class="input-plus-error-row">
                        <spring:message code="searchFilters.minimum" var="minPriceText"/>
                        <form:input path="minPrice" cssClass="form-control form-control-sm" id="min-price" name="min-price" placeholder="${minPriceText}"/>
                        <form:errors cssClass="formError" element="p"/>
                    </div>
                    <span class="light-text h6">-</span>
                    <div class="input-plus-error-row">
                        <spring:message code="searchFilters.maximum" var="maxPriceText"/>
                        <form:input path="maxPrice" cssClass="form-control form-control-sm" id="max-price" name="max-price" placeholder="${maxPriceText}"/>
                        <form:errors cssClass="formError" element="p"/>
                    </div>
                    <span class="light-text h6"><spring:message code="searchFilters.currency"/></span>
                </div>
            </div>
        </div>
        <div class="search-button-container">
            <button type="submit" class="btn button-style button-color shadow-btn">
                <i class="bi bi-search light-text h4"></i>
                <span class="button-text-style light-text h4"><spring:message code="searchFilters.btn"/></span>
            </button>
        </div>
    </div>
</form:form>
