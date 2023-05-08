<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title><spring:message code="reviewTrip.title"/></title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/resources/css/review-trip/review-trip.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body class="background-color">
    <div id="navbar-container">
        <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
    </div>
    <div class="main-container-style container-color">
        <c:url value="/review/${trip.tripId}" var="sucessUrl"/>
        <form:form modelAttribute="reviewForm" method="post" action="${param.url}">
        <div id="trip-detail-container">
            <div id="main-header-row">
                <h1 class="secondary-color"><spring:message code="reviewTrip.view.title"/></h1>
                <hr class="secondary-color">
            </div>
            <div id="trip-route-container">
                <jsp:include page="/WEB-INF/jsp/components/trip-route.jsp"/>
            </div>
            <div id="trip-info-container">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-6 col-md-5 col-lg-4">
                            <div id="trip-info-text-container">
                                <jsp:include page="/WEB-INF/jsp/components/trip-detail-card.jsp"/>
                            </div>
                        </div>
                        <div class="col-sm-6 col-md-5 col-lg-6 border-style">
                            <div id="trip-info-text-container2">
                                <div class="review-form">
                                    <h2>Reseña del viaje:</h2>
                                    <div class="rating-container">
                                        <h3>Rating:</h3>
                                        <div class="rating">
                                            <input type="radio" name="rating" value="5" id="5"><label for="5">☆</label>
                                            <input type="radio" name="rating" value="4" id="4"><label for="4">☆</label>
                                            <input type="radio" name="rating" value="3" id="3"><label for="3">☆</label>
                                            <input type="radio" name="rating" value="2" id="2"><label for="2">☆</label>
                                            <input type="radio" name="rating" value="1" id="1"><label for="1">☆</label>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="exampleFormControlTextarea1"></label><textarea class="form-control" id="exampleFormControlTextarea1" rows="3" placeholder="Escriba su reseña aqui"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="footer-container">
            <div id="trip-price-container">
                <div class="trip-price-row">
                    <div>
                        <span class="h3 text"><spring:message code="reviewTrip.price"/></span>
                    </div>
                    <div>
                        <span class="h2 secondary-color"><spring:message code="reviewTrip.priceFormat" arguments="${trip.price}"/></span>
                    </div>
                </div>
                <div class="trip-price-row items-to-end">
                    <c:choose>
                        <c:when test="${trip.recurrent}">
                            <span class="h6 italic-text"><c:out value="${trip.queryTotalTrips}"/> viajes</span>
                        </c:when>
                        <c:otherwise>
                            <span class="h6 italic-text">Viaje único</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div id="button-container">
                <button id="join-button" type="submit" class="btn button-style button-color shadow-btn">
                    <i class="bi bi-check2 light-text h3"></i>
                    <span class="button-text-style light-text h3"><spring:message code="reviewTrip.btnConfirm"/></span>
                </button>
            </div>
        </div>
        </form:form>
    </div>
</body>
</html>
