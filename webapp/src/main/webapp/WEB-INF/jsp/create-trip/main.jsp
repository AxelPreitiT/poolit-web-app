<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
    <head>
        <title>Crear un viaje</title>
        <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.css.jsp" />
        <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.icons.jsp" />
        <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
        <link href="<c:url value="/css/create-trip/main.css"/>" rel="stylesheet">
    </head>
    <body class="background-bg-color">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
        <div>
            <div class="form-style container-bg-color">
                <c:url value="/trips/create" var="createTripUrl" />
                <form:form modelAttribute="createTripForm" action="${createTripUrl}" method="post">
                    <div class="route-container">
                        <div class="location-container">
                            <div class="row-title">
                                <div class="icon-container">
                                    <i class="bi bi-geo-alt text h4-size"></i>
                                </div>
                                <h3 class="h3 text">Origen</h3>
                            </div>
                            <div class="row-input">
                                <div class="form-floating">
                                    <form:select path="originCityId" id="originCity" class="form-select h6 text" name="Origen">
                                        <c:forEach items="${cities}" var="city">
                                            <form:option value="${city.id}"><c:out value="${city.name}"/></form:option>
                                        </c:forEach>
                                    </form:select>
                                    <form:label path="originCityId" for="originCity" cssClass="placeholder-text h5">Origen</form:label>
                                    <form:errors path="originCityId" cssClass="formError" element="p"/>
                                </div>
                                <div class="error-container">
                                    <div class="form-floating">
                                        <form:input path="originAddress" type="text" class="form-control text" id="originAddress" name="originAddress" placeholder="Av. del Libertador 1234"/>
                                        <form:label path="originAddress" for="originAddress" class="placeholder-text">Dirección</form:label>
                                    </div>
                                    <form:errors path="originAddress" cssClass="formError" element="p"/>
                                </div>
                            </div>
                        </div>
                        <div class="time-trip-container">
                            <div class="container-line">
                                <div class="vertical-dotted-line"></div>
                            </div>
                            <div class="row-input row-time">
                                <div class="setting">
                                    <div class="error-container">
                                        <div class="form-floating">
                                            <form:input path="originDate" type="date" class="form-control text" id="date" name="date" placeholder="02/04/23"/>
                                            <form:label path="originDate" for="date" class="placeholder-text">Fecha</form:label>
                                        </div>
                                        <form:errors path="originDate" cssClass="formError" element="p"/>
                                    </div>

                                </div>
                                <div class="setting">
                                    <div class="error-container">
                                        <div class="form-floating">
                                            <form:input path="originTime" type="time" class="form-control text" id="time" name="time" placeholder="11:25"/>
                                            <form:label path="originTime" for="time" class="placeholder-text">Hora</form:label>
                                        </div>
                                        <form:errors path="originTime" cssClass="formError" element="p"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="location-container">
                            <div class="row-title">
                                <div class="icon-container">
                                    <i class="bi bi-geo-alt-fill text h4-size"></i>
                                </div>
                                <h3 class="h3 text">Destino</h3>
                            </div>
                            <div class="row-input">
                                <div class="form-floating">
                                    <form:select path="destinationCityId" id="destinationCity" class="form-select h6 text" name="Origen">
                                        <c:forEach items="${cities}" var="city">
                                            <form:option value="${city.id}"><c:out value="${city.name}"/></form:option>
                                        </c:forEach>
                                    </form:select>
                                    <form:label path="destinationCityId" for="destinationCity" cssClass="placeholder-text h5">Origen</form:label>
                                    <form:errors path="destinationCityId" cssClass="formError" element="p"/>
                                </div>
                                <div class="error-container">
                                    <div class="form-floating">
                                        <form:input path="destinationAddress" type="text" class="form-control text" id="destinationAddress" name="destinationAddress" placeholder="Av. del Libertador 1234"/>
                                        <form:label path="destinationAddress" for="destinationAddress" class="placeholder-text">Dirección</form:label>
                                    </div>
                                    <form:errors path="destinationAddress" cssClass="formError" element="p"/>
                                </div>

                            </div>
                        </div>
                    </div>
                    <div>
                        <div class="trip-settings">
                            <div class="row-title">
                                <div class="icon-container">
                                    <i class="bi bi-car-front-fill text h6-size"></i>
                                </div>
                                <h4 class="h4 text">Mi auto</h4>
                            </div>
                            <div class="row-input">
                                <div class="setting">
                                    <div class="infoCar">
                                        <div>
                                            <div class="form-floating">
                                                <form:input path="carInfo" type="text" class="form-control text" id="infoCar" name="infoCar" placeholder="Descripcion"/>
                                                <form:label path="carInfo" for="infoCar" class="placeholder-text">Descripcion</form:label>
                                            </div>
                                            <form:errors path="carInfo" cssClass="formError" element="p"/>
                                        </div>

                                    </div>
                                    <div class="plate">
                                        <div class="error-container">
                                            <div class="form-floating">
                                                <form:input path="carPlate" type="text" class="form-control text" id="plate" name="plate" placeholder="Patente"/>
                                                <form:label path="carPlate" for="plate" class="placeholder-text">Patente</form:label>
                                            </div>
                                            <form:errors path="carPlate" cssClass="formError" element="p"/>
                                        </div>
                                    </div>
                                </div>
                                <div class="setting">
                                    <div class="error-container">
                                        <div class="form-floating">
                                            <form:input path="maxSeats" class="form-control text" id="seats" name="seats" placeholder="4"/>
                                            <form:label path="maxSeats" for="seats" class="placeholder-text">Cantidad de asientos</form:label>
                                        </div>
                                        <form:errors path="maxSeats" cssClass="formError" element="p"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="driver-info">
                            <div class="row-title">
                                <div class="icon-container">
                                    <i class="bi bi-person-fill text h6-size"></i>
                                </div>
                                <h4 class="h4 text">Contacto</h4>
                            </div>
                            <div class="row-input">
                                <div class="info">
                                    <div class="error-container">
                                        <div class="form-floating">
                                            <form:input path="email" type="email" class="form-control text" id="email" name="email" placeholder="paw@itba.edu.ar"/>
                                            <form:label path="email" for="email" class="placeholder-text">Email</form:label>
                                        </div>
                                        <form:errors path="email" cssClass="formError" element="p"/>
                                    </div>

                                </div>
                                <div class="info">
                                    <div class="error-container">
                                        <div class="form-floating">
                                            <form:input path="phone" type="tel" class="form-control text" id="phone" name="phone" placeholder="11 1234 5678"/>
                                            <form:label path="phone" for="phone" class="placeholder-text">Teléfono</form:label>
                                        </div>
                                        <form:errors path="phone" cssClass="formError" element="p"/>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="create-trip-btn">
                        <button type="submit" class="btn btn-primary btn-lg">
                            <div class="inline-btn">
                                <h3 class="h3">Crear viaje</h3>
                            </div>
                        </button>
                    </div>
                </form:form>
            </div>
        </div>

        <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.js.jsp" />
    </body>
</html>
