<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
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
                <c:url value="/create-trip" var="createTripUrl" />
                <form action="${createTripUrl}" method="post">
                    <div class="route-container">
                        <div class="location-container">
                            <div class="row-title">
                                <div class="icon-container">
                                    <i class="bi bi-geo-alt text h4-size"></i>
                                </div>
                                <h3 class="h3 text">Origen</h3>
                            </div>
                            <div class="row-input">
                                <jsp:include page="/WEB-INF/jsp/components/city-selector.jsp">
                                    <jsp:param name="id" value="originCity" />
                                </jsp:include>
                                <div class="form-floating">
                                    <input type="text" class="form-control text" id="originAddress" name="originAddress" placeholder="Av. del Libertador 1234">
                                    <label for="originAddress" class="placeholder-text">Dirección</label>
                                </div>
                            </div>
                        </div>
                        <div class="time-trip-container">
                            <div class="container-line">
                                <div class="vertical-dotted-line"></div>
                            </div>
                            <div class="row-input row-time">
                                <div class="setting">
                                    <div class="form-floating">
                                        <input type="date" class="form-control text" id="date" name="date" placeholder="02/04/23">
                                        <label for="date" class="placeholder-text">Fecha</label>
                                    </div>
                                </div>
                                <div class="setting">
                                    <div class="form-floating">
                                        <input type="time" class="form-control text" id="time" name="time" placeholder="11:25">
                                        <label for="time" class="placeholder-text">Hora</label>
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
                                <jsp:include page="/WEB-INF/jsp/components/city-selector.jsp">
                                    <jsp:param name="id" value="destinationCity" />
                                </jsp:include>
                                <div class="form-floating">
                                    <input type="text" class="form-control text" id="destinationAddress" name="destinationAddress" placeholder="Av. del Libertador 1234">
                                    <label for="destinationAddress" class="placeholder-text">Dirección</label>
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
                                    <div class="form-floating">
                                        <input type="text" class="form-control text" id="carInfo" name="carInfo" placeholder="Info del auto">
                                        <label for="carInfo" class="placeholder-text">Info. del auto</label>
                                    </div>
                                </div>
                                <div class="setting">
                                    <div class="form-floating">
                                        <input type="number" min="1" class="form-control text" id="seats" name="seats" placeholder="4">
                                        <label for="seats" class="placeholder-text">Cantidad de asientos</label>
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
                                    <div class="form-floating">
                                        <input type="email" class="form-control text" id="email" name="email" placeholder="paw@itba.edu.ar">
                                        <label for="email" class="placeholder-text">Email</label>
                                    </div>
                                </div>
                                <div class="info">
                                    <div class="form-floating">
                                        <input type="tel" class="form-control text" id="phone" name="phone" placeholder="11 1234 5678">
                                        <label for="phone" class="placeholder-text">Teléfono</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="create-trip-btn">
                        <button type="submit" class="btn btn-lg button-bg-color">
                            <div class="inline-btn">
                                <h3 class="h3">Crear viaje</h3>
                            </div>
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.js.jsp" />
    </body>
</html>
