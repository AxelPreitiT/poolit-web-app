<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Crear viaje</title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/resources/css/create-trip/create-trip.css"/>" rel="stylesheet" type="text/css"/>
    <script src="<c:url value="/resources/js/pages/createTrip.js"/>" type="module"></script>
</head>
<body class="background-color">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    <div class="main-container-style container-color">
        <div id="main-header-row">
            <h1 class="secondary-color">Crear viaje</h1>
            <hr class="secondary-color">
        </div>
        <c:url value="/trips/create" var="createTripUrl"/>
        <form action="${createTripUrl}" method="post" class="form-style">
            <div class="info-container" id="origin-info-container">
                <div class="header-row">
                    <i class="bi bi-geo-alt h2 secondary-color"></i>
                    <h2 class="secondary-color">Origen</h2>
                </div>
                <div class="input-container">
                    <div class="input-row">
                        <jsp:include page="/WEB-INF/jsp/components/city-selector.jsp">
                            <jsp:param name="id" value="originCity"/>
                            <jsp:param name="defaultText" value="Ciudad"/>
                        </jsp:include>
                        <div>
                            <input type="text" min="0" class="form-control form-control-sm" id="originAddress" name="originAddress" placeholder="Dirección">
                        </div>
                    </div>
                    <div class="input-row">
                        <div class="input-group input-group-sm" id="first-date-picker">
                            <button type="button" class="btn button-color">
                                <i class="bi bi-calendar-fill light-text"></i>
                            </button>
                            <input type="text" class="form-control form-control-sm" id="first-date" name="first-date" placeholder="Fecha">
                        </div>
                        <div class="input-group input-group-sm" id="time-picker">
                            <button type="button" class="btn button-color">
                                <i class="bi bi-clock-fill light-text"></i>
                            </button>
                            <input type="text" class="form-control form-control-sm" id="time" name="time" placeholder="Hora">
                        </div>
                    </div>
                    <div class="collapse" id="day-repeat-container">
                        <div class="input-row" id="day-repeat-row">
                            <i class="bi bi-arrow-repeat secondary-color"></i>
                            <span class="italic-text secondary-color" id="day-repeat-text"></span>
                        </div>
                    </div>
                    <div class="input-row">
                        <div class="input-group input-group-sm" id="last-date-picker">
                            <button type="button" class="btn button-color" id="last-date-button" disabled>
                                <i class="bi bi-calendar-fill light-text"></i>
                            </button>
                            <input type="text" class="form-control form-control-sm" id="last-date" name="last-date" placeholder="Última fecha" disabled>
                        </div>
                        <div class="input-group input-group-sm">
                            <div class="input-group-text" id="is-multitrip-container">
                                <input class="form-check-input mt-0" type="checkbox" id="is-multitrip">
                                <span class="mb-0 ml-1 placeholder-text">Viaje recurrente</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div id="vertical-dotted-line"></div>
            <div class="info-container" id="destination-info-container">
                <div class="header-row">
                    <i class="bi bi-geo-alt-fill h2 secondary-color"></i>
                    <h2 class="secondary-color">Destino</h2>
                </div>
                <div class="input-container">
                    <div class="input-row">
                        <jsp:include page="/WEB-INF/jsp/components/city-selector.jsp">
                            <jsp:param name="id" value="destinationCity"/>
                            <jsp:param name="defaultText" value="Ciudad"/>
                        </jsp:include>
                        <div>
                            <input type="text" min="0" class="form-control form-control-sm" id="destinationAddress" name="destinationAddress" placeholder="Dirección">
                        </div>
                    </div>
                </div>
            </div>
            <div class="info-container" id="detail-info-container">
                <div class="header-row">
                    <i class="bi bi-info-circle-fill h2 secondary-color"></i>
                    <h2 class="secondary-color">Detalles</h2>
                </div>
                <div class="input-container">
                    <div class="input-row">
                        <div>
                            <div id="car-info-text">
                                <div class="input-group input-group-sm">
                                    <div class="input-group-text">
                                        <i class="bi bi-car-front-fill text"></i>
                                    </div>
                                    <select id="car-select" name="order-by-select" class="form-select form-select-sm">
                                        <option value="">Seleccionar auto</option>
                                        <option value="date">Ford Ka azul</option>
                                    </select>
                                </div>
                                <div class="collapse" id="car-info-details">
                                    <div class="primary-bg-color" id="car-info-details-container">
                                        <div class="car-info-details-row">
                                            <i class="bi bi-caret-right-fill light-text"></i>
                                            <span class="light-text">Patente: TIA039</span>
                                        </div>
                                        <hr class="text"/>
                                        <div class="car-info-details-row">
                                            <i class="bi bi-caret-right-fill light-text"></i>
                                            <span class="light-text">3 asientos disponibles</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div>
                            <div id="car-info-image" class="collapse collapse-horizontal">
                                <div class="col-4 placeholder-image-color">
                                    <i class="bi bi-car-front-fill placeholder-image-icon-color"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="input-row" id="price-container">
                        <div class="input-group input-group-sm">
                            <div class="input-group-text">
                                <i class="bi bi-currency-dollar text"></i>
                            </div>
                            <input type="number" min="0" class="form-control form-control-sm" id="price" name="price" placeholder="Precio por viaje">
                            <div class="input-group-text">
                                <span class="text">ARS</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="confirm-button-container">
                <button id="create-trip-button" type="submit" class="btn button-style button-color shadow-btn" disabled>
                    <i class="bi bi-check2 light-text h3"></i>
                    <span class="button-text-style light-text h3">Confirmar</span>
                </button>
            </div>
        </form>
    </div>
</body>
</html>
