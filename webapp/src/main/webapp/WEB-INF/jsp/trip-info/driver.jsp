<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title><spring:message code="selectTrip.title"/></title>
  <jsp:include page="/resources/external-resources.jsp"/>
  <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
  <link href="<c:url value="/resources/css/trip-info/driver.css"/>" rel="stylesheet" type="text/css"/>
  <link href="<c:url value="/resources/css/components/trip-detail.css"/>" rel="stylesheet" type="text/css"/>
  <link href="<c:url value="/resources/css/components/trip-detail-card.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body class="background-color">
<div id="navbar-container">
  <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
</div>
<div class="main-container-style container-color">
  <div class="info-container">
  <div id="trip-route-container">
    <jsp:include page="/WEB-INF/jsp/components/trip-route.jsp"/>
  </div>
    <div id="trip-info-container">
      <div class="container">
        <div class="row">
          <div class="col-sm-6 col-md-5 col-lg-4">
            <div id="trip-info-text-container">
              <jsp:include page="/WEB-INF/jsp/components/trip-detail-card.jsp">
                <jsp:param name="showDriverInfo" value="true"/>
              </jsp:include>
            </div>
          </div>
          <div class="col-sm-6 col-md-5 col-lg-5">
            <div id="car-info-image">
              <c:url value="/image/${trip.car.image_id}" var="carImageUrl"/>
              <div class="placeholder-image">
                <img src="${carImageUrl}" alt="car image"/>
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
          <span class="h3 text"><spring:message code="selectTrip.price"/></span>
        </div>
        <div>
          <span class="h2 secondary-color">
            <spring:message code="selectTrip.priceFormat" arguments="${trip.integerQueryTotalPrice},${trip.decimalQueryTotalPrice}"/>
          </span>
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
      <c:if test="${!trip.tripHasEnded}">
      <div class="delete-trip-container">
        <button class="btn button-style shadow-btn danger-button" data-bs-toggle="modal" data-bs-target="#modal-<c:out value="${trip.tripId}"/>">
          <i class="bi bi-trash-fill light-text h5"></i>
          <span class="button-text-style light-text h3"><spring:message code="tripInfo.driver.deleteButton"/></span>
        </button>
      </div>
      <div class="modal fade" id="modal-<c:out value="${trip.tripId}"/>" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
          <div class="modal-content">
            <div class="modal-header">
              <h3 class="modal-title danger"><spring:message code="tripCard.delete"/></h3>
              <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
              <span class="text"><spring:message code="tripCard.warning.title"/>
                <strong class="secondary-color"><c:out value="${trip.originCity.name}"/></strong>
                <strong class="secondary-color">-</strong>
                <strong class="secondary-color"><c:out value="${trip.destinationCity.name}"/></strong>?
              </span>
              <span class="text"><spring:message code="tripCard.warning.messege"/></span>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn primary-button" data-bs-dismiss="modal">
                <span class="light-text"><spring:message code="tripCard.btn.cancel"/></span>
              </button>
              <c:url value="/trips/${trip.tripId}/delete" var="deleteTripUrl"/>
              <form:form method="DELETE" action="${deleteTripUrl}">
                <button type="submit" class="btn danger-button">
                  <span class="light-text"><spring:message code="tripCard.btn.delete"/></span>
                </button>
              </form:form>
            </div>
          </div>
        </div>
      </div>
      </c:if>
      <c:url value="/trips/created" var="myTripsUrl"/>
      <a href="${myTripsUrl}" class="btn button-style primary-button shadow-btn">
        <i class="bi bi-car-front-fill light-text h3"></i>
        <span class="button-text-style light-text h3"><spring:message code="tripInfo.driver.button"/></span>
      </a>
    </div>
  </div>
</div>
<div class="main-container-style container-color">
  <div class="info-container">
    <h3 class="secondary-color title-style"><spring:message code="driver.passangers.title"/></h3>
    <c:url value="" var="baseStatusUrl">
      <c:forEach var="p" items="${param}">
        <c:if test="${!(p.key eq 'status')}">
          <c:param name="${p.key}" value="${p.value}"/>
        </c:if>
      </c:forEach>
    </c:url>
    <jsp:include page="/WEB-INF/jsp/components/passangers-order-by.jsp">
      <jsp:param name="baseUrl" value="${baseStatusUrl}"/>
    </jsp:include>

    <c:forEach items="${passengersContent.elements}" var="user">
      <c:url value="/profile/${user.userId}" var="userUrl"/>
      <c:url value="/image/${user.userImageId}" var="userImageId"/>
      <div class="individual-profile">
        <div>
          <img src="${userImageId}" alt="user image" class="image-photo"/>
        </div>
        <div class="show-row-content-passangers">
          <a href="${userUrl}" class="show-row profile-link">
            <span class="detail"><spring:message code="user.nameFormat" arguments="${user.name}, ${user.surname}"/></span>
          </a>
          <div class="btn-section">
            <c:url value="/trips/${trip.tripId}/deletePas/${user.userId}" var="deletePasUrl"/>
            <form:form method="POST" action="${deletePasUrl}">
              <button type="submit" class="btn btn-danger little-btn">
                <span class="light-text"><spring:message code="driver.passangers.delete"/></span>
              </button>
            </form:form>
            <c:url value="/trips/${trip.tripId}/AceptPas/${user.userId}" var="acceptPasUrl"/>
            <form:form method="POST" action="${acceptPasUrl}">
              <button type="submit" class="btn btn-primary little-btn">
                <span class="light-text"><spring:message code="driver.passangers.accept"/></span>
              </button>
            </form:form>
          </div>
        </div>

        <c:if test="${trip.recurrent}">
          <div class="dates light-text detail">
            <c:out value="${user.startDateString}"/> - <c:out value="${user.endDateString}"/>
          </div>
        </c:if>
      </div>
    </c:forEach>


    <c:if test="${passengersContent.moreThanOnePage}">
      <c:url value="" var="basePaginationUrl">
        <c:forEach var="p" items="${param}">
          <c:if test="${!(p.key eq 'page')}">
            <c:param name="${p.key}" value="${p.value}"/>
          </c:if>
        </c:forEach>
      </c:url>
      <jsp:include page="/WEB-INF/jsp/components/trip-card-list-pagination.jsp">
        <jsp:param name="totalPages" value="${passengersContent.totalPages}"/>
        <jsp:param name="currentPage" value="${passengersContent.currentPage+1}"/>
        <jsp:param name="baseUrl" value="${basePaginationUrl}"/>
      </jsp:include>
    </c:if>

  </div>
</div>
<c:if test="${!(empty deletePass) && deletePass}">
  <div id="toast-container">
    <jsp:include page="/WEB-INF/jsp/components/success-toast.jsp">
      <jsp:param name="title" value="driver.passangers.delete.toast.title"/>
      <jsp:param name="message" value="driver.passangers.delete.toast.text"/>
    </jsp:include>
  </div>
</c:if>

<c:if test="${!(empty notAvailableSeats) && notAvailableSeats}">
    <div id="toast-container">
        <jsp:include page="/WEB-INF/jsp/components/failure-toast.jsp">
            <jsp:param name="title" value="driver.passangers.delete.toastFail.title"/>
            <jsp:param name="message" value="driver.passangers.delete.toastFail.text"/>
        </jsp:include>
    </div>
</c:if>

<c:if test="${!(empty acceptPass) && acceptPass}">
  <div id="toast-container">
    <jsp:include page="/WEB-INF/jsp/components/success-toast.jsp">
      <jsp:param name="title" value="driver.passangers.accept.toast.title"/>
      <jsp:param name="message" value="driver.passangers.accept.toast.text"/>
    </jsp:include>
  </div>
</c:if>
</body>
</html>