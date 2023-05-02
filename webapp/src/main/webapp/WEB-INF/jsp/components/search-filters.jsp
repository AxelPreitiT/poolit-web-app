<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
                    <h5 class="light-text">Ruta</h5>
                </div>
                <div class="input-container">
                    <div class="input-row">
                        <div class="input-plus-error-row">
                            <div id="origin-city-selector">
                                <jsp:include page="/WEB-INF/jsp/components/city-selector.jsp">
                                    <jsp:param name="id" value="originCityId"/>
                                    <jsp:param name="defaultText" value="Origen"/>
                                </jsp:include>
                            </div>
                            <form:errors path="originCityId" cssClass="formError" element="p"/>
                        </div>
                        <div class="route-arrow">
                            <i class="bi bi-arrow-right light-text"></i>
                        </div>
                        <div class="input-plus-error-row">
                            <div id="destination-city-selector">
                                <jsp:include page="/WEB-INF/jsp/components/city-selector.jsp">
                                    <jsp:param name="id" value="destinationCityId"/>
                                    <jsp:param name="defaultText" value="Destino"/>
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
                    <h5 class="light-text">Fecha</h5>
                </div>
                <div class="input-container">
                    <div class="input-row">
                        <div class="input-plus-error-row">
                            <div class="input-group input-group-sm" id="first-date-picker">
                                <button type="button" class="btn button-color">
                                    <i class="bi bi-calendar-fill light-text"></i>
                                </button>
                                <form:input path="date" cssClass="form-control form-control-sm" id="date" name="date" placeholder="Fecha"/>
                            </div>
                            <form:errors path="date" cssClass="formError" element="p"/>
                        </div>
                        <div class="input-plus-error-row">
                            <div class="input-group input-group-sm" id="time-picker">
                                <button type="button" class="btn button-color">
                                    <i class="bi bi-clock-fill light-text"></i>
                                </button>
                                <form:input path="time" cssClass="form-control form-control-sm" id="time" name="time" placeholder="Hora"/>
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
                                <form:input path="lastDate" cssClass="form-control form-control-sm" id="last-date" name="last-date" placeholder="Última fecha" disabled="true"/>
                            </div>
                            <form:errors path="lastDate" cssClass="formError" element="p"/>
                        </div>
                        <div class="input-group input-group-sm">
                            <div class="input-group-text" id="is-multitrip-container">
                                <form:checkbox path="multitrip" id="is-multitrip" cssClass="form-check-input mt-0"/>
                                <div id="is-multitrip-text">
                                    <span class="placeholder-text">Viaje</span>
                                    <span class="placeholder-text">recurrente</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="price-filter">
                <div class="title-row">
                    <i class="bi bi-currency-dollar light-text h5"></i>
                    <h5 class="light-text">Precio</h5>
                </div>
                <div class="input-container">
                    <div class="input-row">
                        <div class="input-plus-error-row">
                            <form:input path="minPrice" cssClass="form-control form-control-sm" id="min-price" name="min-price" placeholder="Mínimo"/>
                            <form:errors cssClass="formError" element="p"/>
                        </div>
                        <span class="light-text h6">-</span>
                        <div class="input-plus-error-row">
                            <form:input path="maxPrice" cssClass="form-control form-control-sm" id="max-price" name="max-price" placeholder="Máximo"/>
                            <form:errors cssClass="formError" element="p"/>
                        </div>
                        <span class="light-text h6">ARS</span>
                    </div>
                </div>
            </div>
            <div class="search-button-container">
                <button type="submit" class="btn button-style button-color shadow-btn">
                    <i class="bi bi-search light-text h4"></i>
                    <span class="button-text-style light-text h4">Buscar</span>
                </button>
            </div>
    </div>
</form:form>
