<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
  <title><spring:message code="tripInfo.driver.title"/></title>
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
  <div id="main-header-row">
    <h1 class="secondary-color"><spring:message code="tripDetails.title"/></h1>
    <hr class="secondary-color">
  </div>
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
    <div class="footer-row">
      <div id="trip-price-container">
        <div class="trip-price-row">
          <div>
            <span class="h3 text"><spring:message code="tripInfo.driver.price"/></span>
          </div>
          <div>
          <span class="h2 secondary-color">
            <spring:message code="tripInfo.driver.priceFormat" arguments="${totalIncome}" var="priceString" argumentSeparator=";"/>
            <c:out value="${priceString}"/>
          </span>
          </div>
        </div>
        <div class="trip-price-row items-to-end">
          <c:choose>
            <c:when test="${trip.recurrent}">
            <span class="h6 italic-text">
              <spring:message code="tripInfo.multipleTrips" arguments="${trip.queryTotalTrips}" var="totalTripsString"/>
              <c:out value="${totalTripsString}"/>
            </span>
            </c:when>
            <c:otherwise>
              <span class="h6 italic-text"><spring:message code="tripInfo.singleTrip"/></span>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
      <div id="button-container">
        <div id="review-trip-container">
          <c:if test="${tripReviewCollection.canReview}">
            <jsp:include page="/WEB-INF/jsp/trip-info/review-list-modal.jsp">
              <jsp:param name="reviewed" value="${reviewed}"/>
            </jsp:include>
          </c:if>
        </div>
        <c:if test="${!trip.tripHasEnded and !trip.deleted }">
          <div class="delete-trip-container">
            <button class="btn button-style shadow-btn danger-button" data-bs-toggle="modal" data-bs-target="#modal-<c:out value="${trip.tripId}"/>">
              <i class="bi bi-trash-fill light-text h4"></i>
              <span class="light-text h4"><spring:message code="tripInfo.driver.deleteButton"/></span>
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
          <i class="bi bi-car-front-fill light-text h4"></i>
          <span class="light-text h4"><spring:message code="tripInfo.driver.button"/></span>
        </a>
      </div>
    </div>
    <div id="report-container">
      <c:if test="${tripReportCollection.canReport}">
        <jsp:include page="/WEB-INF/jsp/trip-info/report-list-modal.jsp">
          <jsp:param name="reported" value="${reported}"/>
        </jsp:include>
      </c:if>
    </div>
  </div>
</div>
<div class="main-container-style container-color">
  <div class="info-container-passenger">
    <h3 class="secondary-color title-style"><spring:message code="driver.passangers.title"/></h3>
    <hr class="text">

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

    <c:if test="${passengersContent.totalCount>0}">
      <div class="container-flex">
        <div class="list-container">
          <c:forEach items="${passengersContent.elements}" var="passenger">
            <c:url value="/profile/${passenger.userId}" var="userUrl"/>
            <c:url value="/image/${passenger.userImageId}" var="userImageId"/>
            <div>
              <div class="individual-profile">
                <div>
                  <img src="${userImageId}" alt="user image" class="image-photo-list"/>
                </div>
                <div class="show-row-content-passangers">
                  <div class="column-data-pass">
                    <a href="${userUrl}" class="show-row profile-link">
                      <span class="text detail h4"><spring:message code="user.nameFormat" arguments="${passenger.name}, ${passenger.surname}"/> </span>
                    </a>
                    <c:if test="${trip.recurrent}">
                      <c:if test="${passenger.recurrent}">
                        <h6 class="show-row italic-text"><spring:message code="dates.recurrentDates" arguments="${passenger.startDateString}, ${passenger.endDateString}"/></h6>
                      </c:if>
                      <c:if test="${!(passenger.recurrent)}">
                        <h6 class="show-row italic-text"><spring:message code="dates.unique" arguments="${passenger.startDateString}"/></h6>
                      </c:if>
                    </c:if>
                  </div>
                  <div class="btn-section">
                    <div class="row-info">
                      <div class="d-flex justify-content-between align-items-center">
                        <div class="ratings">
                          <c:set value='${passenger.user.passengerRating}' var="rating" scope="request"/>
                          <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp">
                            <jsp:param name="fontSize" value="h5"/>
                            <jsp:param name="fontColor" value="secondary-color"/>
                          </jsp:include>
                        </div>
                      </div>
                    </div>

                    <c:url value="/trips/${trip.tripId}/deletePas/${passenger.userId}" var="deletePasUrl"/>
                    <form:form method="POST" action="${deletePasUrl}" cssClass="form-bn-class">
                      <c:if test="${passenger.passengerState eq 'REJECTED'}">
                        <button type="submit" class="btn disabled btn-danger btn-sm">
                          <span class="light-text"><spring:message code="driver.passangers.delete"/></span>
                        </button>
                      </c:if>
                      <c:if test="${!(passenger.passengerState eq 'REJECTED')}">
                        <button type="submit" class="btn btn-danger btn-sm" <c:if test="${passenger.tripStarted}">
                          <c:out value="disabled"/>
                        </c:if>>
                          <span class="light-text"><spring:message code="driver.passangers.delete"/></span>
                        </button>
                      </c:if>
                    </form:form>
                    <c:url value="/trips/${trip.tripId}/AceptPas/${passenger.userId}" var="acceptPasUrl"/>
                    <form:form method="POST" action="${acceptPasUrl}" cssClass="form-bn-class">
                      <c:if test="${passenger.passengerState eq 'ACCEPTED'}">
                        <button type="submit" class="btn disabled success-bg-color btn-sm">
                          <span class="light-text"><spring:message code="driver.passangers.accept"/></span>
                        </button>
                      </c:if>
                      <c:if test="${!(passenger.passengerState eq 'ACCEPTED')}">
                        <button type="submit" class="btn btn-primary success-bg-color btn-sm" <c:if test="${passenger.tripStarted}">
                          <c:out value="disabled"/>
                        </c:if>>
                          <span class="light-text"><spring:message code="driver.passangers.accept"/></span>
                        </button>
                      </c:if>
                    </form:form>
                  </div>
                </div>
              </div>
              <c:if test="${passenger.tripStarted}">
                <div class="end-line">
                  <h6><spring:message code="driver.passangers.tripStarted"/></h6>
                </div>
              </c:if>
            </div>
          </c:forEach>
        </div>
      </div>



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
    </c:if>

    <c:if test="${passengersContent.totalCount == 0}">
      <div class="review-empty-container">
        <i class="bi bi-people-fill secondary-color h2"></i>
        <h3 class="italic-text placeholder-text"><spring:message code="driver.passangers.none"/></h3>
      </div>
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
<c:if test="${!(empty created) && created}">
  <div id="toast-container">
    <jsp:include page="/WEB-INF/jsp/components/success-toast.jsp">
      <jsp:param name="title" value="createTrip.success.toast.title"/>
      <jsp:param name="message" value="createTrip.success.toast.message"/>
    </jsp:include>
  </div>
</c:if>
</body>
</html>