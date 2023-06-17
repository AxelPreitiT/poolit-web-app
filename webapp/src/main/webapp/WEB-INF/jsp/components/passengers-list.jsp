<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="<c:url value="/resources/css/components/passengers-list.css"/>" type="text/css">

<jsp:useBean id="passengers" scope="request" type="java.util.List" />
<jsp:useBean id="trip" scope="request" type="ar.edu.itba.paw.models.trips.Trip" />

<div id="passengers-list-container">
  <c:choose>
    <c:when test="${!(empty passengers)}">
      <div class="secondary-bg-color passenger-container">
        <div id="passenger-list-header">
          <i class="bi bi-people-fill light-text h2"></i>
          <h2 class="light-text"><spring:message code="tripDetails.passengers"/></h2>
        </div>
        <div class="passenger-list">
          <c:forEach items="${passengers}" var="passenger">
              <c:url value="/profile/${passenger.userId}" var="userUrl"/>
              <c:url value="/image/${passenger.userImageId}" var="userImageId"/>
              <div class="individual-profile">
                <div class="individual-profile-text">
                  <a href="${userUrl}" class="show-row profile-link">
                    <div>
                      <img src="${userImageId}" alt="user image" class="image-photo"/>
                    </div>
                  </a>
                  <div>
                    <div class="passenger-name-container">
                      <a href="${userUrl}">
                        <span class="light-text detail"><spring:message code="user.nameFormat" arguments="${passenger.name}, ${passenger.surname}"/></span>
                      </a>
                    </div>
                    <c:choose>
                      <c:when test="${passenger.recurrent}">
                        <div class="passenger-dates-container">
                          <i class="bi bi-calendar light-text"></i>
                          <span class="light-text">
                            <spring:message code="format.dates" var="passengerDate" arguments="${passenger.startDateString}, ${passenger.endDateString}"/>
                            <c:out value="${passengerDate}"/>
                          </span>
                        </div>
                      </c:when>
                      <c:otherwise>
                        <div class="passenger-dates-container">
                          <i class="bi bi-calendar light-text"></i>
                          <span class="light-text"><c:out value="${passenger.startDateString}"/></span>
                        </div>
                      </c:otherwise>
                    </c:choose>
                  </div>
                </div>
                <div>
                  <c:set var="rating" value="${passenger.user.passengerRating}" scope="request"/>
                  <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp">
                    <jsp:param name="fontSize" value="h6"/>
                    <jsp:param name="fontColor" value="light-text"/>
                  </jsp:include>
                </div>
              </div>
          </c:forEach>
        </div>
      </div>
    </c:when>
    <c:otherwise>
      <div class="details-container secondary-bg-color no-passengers-text passenger-container">
        <i class="bi bi-car-front-fill light-text h1"></i>
        <c:choose>
          <c:when test="${trip.tripHasEnded}">
            <h4 class="light-text"><spring:message code="tripInfo.driver.noPassengers.ended"/></h4>
          </c:when>
          <c:otherwise>
            <h4 class="light-text"><spring:message code="tripInfo.driver.noPassengers"/></h4>
            <h6 class="light-text"><spring:message code="tripInfo.driver.noPassengers.message"/></h6>
          </c:otherwise>
        </c:choose>
      </div>
    </c:otherwise>
  </c:choose>
</div>
