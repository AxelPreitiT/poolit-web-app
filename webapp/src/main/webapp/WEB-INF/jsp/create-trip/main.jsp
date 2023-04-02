<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
    <head>
        <title>Crear un viaje</title>
        <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.css.jsp" />
        <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.icons.jsp" />
        <link href="/css/base/font-sizes.css" rel="stylesheet">
        <link href="/css/base/colors.css" rel="stylesheet">
        <link href="/css/create-trip/main.css" rel="stylesheet">
    </head>
    <body>
        <div class="body-style">
            <h1 class="h1 text title-style">Crear un viaje</h1>

            <div class="form-style">
                <c:url value="/create-trip" var="createTripUrl" />
                <form action="${createTripUrl}" method="post">
                    <div class="route-container">
                        <div class="location-container">
                            <div class="location-title">
                                <i class="bi bi-geo-alt text h2"></i>
                                <span class="h2 text">Origen</span>
                            </div>
                            <div class="location-input">
                                <jsp:include page="city-selector.jsp">
                                    <jsp:param name="id" value="originCity" />
                                </jsp:include>
                                <div class="form-floating">
                                    <input type="text" class="form-control h5 text" id="originAddress" name="originAddress" placeholder="Av. del Libertador 1234">
                                    <label for="originAddress" class="placeholder-text h5">Dirección</label>
                                </div>
                            </div>
                        </div>
                        <div class="vertical-dotted-line"></div>
                        <div class="location-container">
                            <div class="location-title">
                                <i class="bi bi-geo-alt-fill text h2"></i>
                                <span class="h2 text">Destino</span>
                            </div>
                            <div class="location-input">
                                <jsp:include page="city-selector.jsp">
                                    <jsp:param name="id" value="destinationCity" />
                                </jsp:include>
                                <div class="form-floating">
                                    <input type="text" class="form-control h5 text" id="destinationAddress" name="destinationAddress" placeholder="Av. del Libertador 1234">
                                    <label for="destinationAddress" class="placeholder-text h5">Dirección</label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr>
                    <div>
                        <h3 class="h3 text subtitles">Detalles del viaje</h3>
                        <div class="trip-settings">
                            <div class="setting">
                                <i class="bi bi-calendar text"></i>
                                <div class="form-floating">
                                    <input type="date" class="form-control h5 text" id="date" name="date" placeholder="02/04/23">
                                    <label for="date" class="placeholder-text h5">Fecha</label>
                                </div>
                            </div>
                            <div class="setting">
                                <i class="bi bi-clock text"></i>
                                <div class="form-floating">
                                    <input type="time" class="form-control h5 text" id="time" name="time" placeholder="11:25">
                                    <label for="time" class="placeholder-text h5">Hora</label>
                                </div>
                            </div>
                            <div class="setting">
                                <i class="bi bi-car-front-fill text"></i>
                                <div class="form-floating">
                                    <input type="number" min="1" class="form-control h5 text" id="seats" name="seats" placeholder="4">
                                    <label for="seats" class="placeholder-text h5">Cantidad de asientos</label>
                                </div>
                            </div>
                        </div>
                        <hr>
                        <h3 class="h3 text subtitles">Contacto</h3>
                        <div class="driver-info">
                            <div class="info">
                                <i class="bi bi-envelope text h3"></i>
                                <div class="form-floating">
                                    <input type="email" class="form-control h5 text" id="email" name="email" placeholder="paw@itba.edu.ar">
                                    <label for="email" class="placeholder-text h5">Email</label>
                                </div>
                            </div>
                            <div class="info">
                                <i class="bi bi-telephone text h3"></i>
                                <div class="form-floating">
                                    <input type="tel" class="form-control h5 text" id="phone" name="phone" placeholder="11 1234 5678">
                                    <label for="phone" class="placeholder-text h5">Teléfono</label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <button type="submit" class="btn primary submit-style">
                        <i class="bi bi-plus h2 light-text"></i>
                        <span class="h2 light-text">Crear viaje</span>
                    </button>
                </form>
            </div>
        </div>

        <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.js.jsp" />
    </body>
</html>
