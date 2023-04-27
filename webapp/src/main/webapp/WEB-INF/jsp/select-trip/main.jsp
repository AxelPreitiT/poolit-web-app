<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title><spring:message code="selectTrip.title"/></title>
    <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.css.jsp" />
    <jsp:include page="/WEB-INF/jsp/bootstrap-cdn/bootstrap.icons.jsp" />
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <script src="https://kit.fontawesome.com/b5e2fa9f6d.js" crossorigin="anonymous"></script>
    <link href="<c:url value="/resources/css/select-trip/select.css"/>" rel="stylesheet" type="text/css"/>
</head>
    <body class="background-color">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
        <div class="trip-details-style container-color">
            <div class="trip-route secondary-color">
                <div class="location-container">
                    <div class="location-data">
                        <h4 class=" title-style"><c:out value="${trip.originCity.name}" escapeXml="true"/></h4>
                        <h6 class="fw-light  description-style"><c:out value="${trip.originAddress}" escapeXml="true "/></h6>
                        <h6 class="fw-light  description-style"><c:out value="${trip.originDateString}" escapeXml="true"/></h6>
                        <h6 class="fw-light  description-style"><c:out value="${trip.originTimeString}" escapeXml="true"/></h6>
                    </div>
                    <i class="bi bi-geo-alt  icon-style"></i>
                </div>
                <div class="location-line">
                    <div class="location-line-content">
                        <i class="fa-solid fa-car-side fa-bounce " id="animated-car"></i>
                        <div class="dotted-line"></div>
                    </div>
                </div>
                <div class="location-container">
                    <i class="bi bi-geo-alt-fill  icon-style"></i>
                    <div class="location-data">
                        <h4 class=" title-style"><c:out value="${trip.destinationCity.name}" escapeXml="true"/></h4>
                        <h6 class="fw-light  description-style"><c:out value="${trip.destinationAddress}" escapeXml="true "/></h6>
                    </div>
                </div>
            </div>
            <div class="data-container ">
                <div class="col-5">
                    <jsp:include page="/WEB-INF/jsp/components/trip-detail-card.jsp">
                        <jsp:param name="trip" value="${trip}"/>
                    </jsp:include>
                </div>
                <div class="col-3">
                    <div class=" placeholder-image-color">
                        <i class="bi bi-car-front-fill placeholder-image-icon-color h1"></i>

                    </div>
                </div>
            </div>
            <div class="data-container ">
                <div class=" secondary-color">
                    <h3> Total a pagar: $500 </h3>
                </div>
                <div >
                    <div class="confirm-btn">
                    <button type="submit" class="btn button-style button-color shadow-btn" <c:if test="${trip.occupiedSeats>=trip.maxSeats}"><c:out value="disabled='disabled'"/></c:if>>
                        <span class="button-text-style light-text h4"><spring:message code="selectTrip.btnJoin"/></span>
                    </button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>