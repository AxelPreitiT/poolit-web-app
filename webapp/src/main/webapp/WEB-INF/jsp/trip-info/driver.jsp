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
</head>
<body class="background-color">
<div id="navbar-container">
  <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
</div>
<div class="main-container-style container-color">
  <div class="info-container">
    <jsp:include page="/WEB-INF/jsp/components/trip-route.jsp"/>
    <div class="trip-info-container">
      <div class="trip-info">
        <jsp:include page="/WEB-INF/jsp/components/trip-detail-card.jsp">
          <jsp:param name="showDriverInfo" value="true"/>
        </jsp:include>
      </div>
      <div class="trip-passengers">
        <c:choose>
          <c:when test="${passengers.size()>0}">
            <div class="secondary-bg-color passenger-container">
              <div class="h2 light-text"><spring:message code="tripDetails.passengers"/></div>
              <div class="passenger-list">
                <c:forEach items="${passengers}" var="user">
                  <c:url value="/profile/${user.userId}" var="userUrl"/>
                  <c:url value="/image/${user.userImageId}" var="userImageId"/>
                  <div class="individual-profile">
                    <a href="${userUrl}" class="show-row profile-link">
                      <div>
                        <img src="${userImageId}" alt="user image" class="image-photo"/>
                      </div>
                      <div class="show-row-content">
                        <span class="light-text detail"><spring:message code="user.nameFormat" arguments="${user.name}, ${user.surname}"/></span>
                      </div>
                    </a>
                    <c:if test="${trip.recurrent}">
                        <div class="dates light-text detail">
                            <c:out value="${user.startDateString}"/> - <c:out value="${user.endDateString}"/>
                        </div>
                    </c:if>
                  </div>
                </c:forEach>
              </div>
            </div>
          </c:when>
          <c:otherwise>
            <div class="details-container secondary-bg-color no-passengers-text passenger-container">
              <i class="bi bi-car-front-fill light-text h1"></i>
              <h4 class="light-text"><spring:message code="tripInfo.driver.noPassengers"/></h4>
              <h6 class="light-text"><spring:message code="tripInfo.driver.noPassengers.message"/></h6>
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
  </div>
  <div id="footer-container">
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
</body>
</html>