<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- Beans:
        - isLoggedIn: boolean that indicates if the user is logged in or not
        - searchUrl: url to search trips
        - trips: list of trips to show
-->

<html>
<head>
    <title>POOLIT</title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/resources/css/landing/landing.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body class="background-color">
    <div id="navbar-container">
        <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    </div>
    <div id="banner-container">
        <div class="container">
            <div class="row">
                <div class="col-lg-5" id="banner-text-column">
                    <span class="light-text title">¡Viaja con<span class="secondary-color">POOLIT</span>!</span>
                    <span class="light-text subtitle">Con <span class="secondary-color">POOLIT</span> podrás compartir tus viajes con otros usuarios que tengan el mismo destino, reduciendo los costos de transporte, el tráfico y la emisión de gases contaminantes. Además, podrás conocer a nuevas personas y hacer conexiones mientras viajas.</span>
                    <span class="light-text subtitle">¡Unite a la comunidad y comenzá a viajar de manera inteligente!</span>
                </div>
                <div class="col-lg-5 col-xl-4" id="banner-search-column">
                    <jsp:include page="/WEB-INF/jsp/components/search-filters.jsp">
                        <jsp:param name="url" value="${searchUrl}"/>
                    </jsp:include>
                </div>
            </div>
        </div>
    </div>
    <div id="content-container">
        <div class="main-container-style container-color">
            <sec:authorize access="isAuthenticated()">
                <div id="trips-container">
                    <h2 class="title secondary-color">Viajes disponibles</h2>
                    <div id="trip-cards-container" class="container">
                        <div class="row">
                            <c:forEach items="${trips}" var="trip">
                                <c:url value="/trips/${trip.tripId}" var="tripUrl">
                                    <c:param name="startDate" value="${trip.startDateString}"/>
                                    <c:param name="startTime" value="${trip.startTimeString}"/>
                                    <c:param name="endDate" value="${trip.endDateString}"/>
                                </c:url>
                                <div class="col-sm-12 col-xl-6">
                                    <c:set var="trip" value="${trip}" scope="request"/>
                                    <jsp:include page="/WEB-INF/jsp/components/trip-card.jsp">
                                        <jsp:param name="tripUrl" value="${tripUrl}"/>
                                    </jsp:include>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </sec:authorize>
            <sec:authorize access="!isAuthenticated()">
                <div id="intro-container">
                    <div class="intro-row">
                        <div class="text-container">
                            <h3 class="secondary-color">Encontrá tu viaje en CABA</h3>
                            <span class="text">Podrás buscar viajes que se ajusten a tus necesidades. Simplemente, ingresá tus puntos de partida y llegada, la fecha y la hora, y te mostraremos los viajes disponibles que se ajusten a tu horario. Además, podrás filtrar por precio e incluso, si lo necesitás realizar todas las semanas, podrás buscar <strong
                                    class="secondary-color">viajes recurrentes</strong>.</span>
                        </div>
                        <div class="image-container">
                            <img src="<c:url value="/resources/images/landing/landing-tutorial-one.jpg"/>"
                                 alt="Buscar viajes">
                        </div>
                    </div>
                    <hr class="secondary-color"/>
                    <div class="intro-row">
                        <div class="text-container">
                            <h3 class="secondary-color">Reservá tu viaje</h3>
                            <span class="text">Cuando tengas decidido la mejor opción, hacé click en el botón "Reservar". Una vez que se confirme tu reserva, enviaremos un correo electrónico tanto a ti como al conductor con la información de contacto del otro. Esto hace que sea fácil coordinar los detalles de tu viaje y garantiza una experiencia fluida tanto para ti como para el conductor.</span>
                        </div>
                        <div class="image-container">
                            <img src="<c:url value="/resources/images/landing/landing-tutorial-two.jpg"/>"
                                 alt="Buscar viajes">
                        </div>
                    </div>
                    <hr class="secondary-color"/>
                    <div class="intro-row">
                        <div class="text-container">
                            <h3 class="secondary-color">Manejá y compartí</h3>
                            <span class="text">No solo podrás encontrar viajes, sino que también podrás convertirte en conductor y compartir tus propios viajes. Solo necesitarás agregar la información de tu auto y ya podrás ofrecer viajes a otros. Establecé tu propio horario y ruta, y los usuarios podrán unirse a tu viaje si se ajusta a sus necesidades de viaje. De esta manera, estarás ayudando a reducir el tráfico y las emisiones, al mismo tiempo que ahorrarás dinero en gasolina y peajes.</span>
                        </div>
                        <div class="image-container">
                            <img src="<c:url value="/resources/images/landing/landing-tutorial-three.jpg"/>"
                                 alt="Crear viajes">
                        </div>
                    </div>
                </div>
            </sec:authorize>
        </div>
    </div>
</body>
</html>
