<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
          <c:forEach items="${passengers}" var="user">
            <c:url value="/profile/${user.userId}" var="userUrl"/>
            <c:url value="/image/${user.userImageId}" var="userImageId"/>
            <div class="individual-profile">
              <a href="${userUrl}" class="show-row profile-link">
                <div>
                  <img src="${userImageId}" alt="user image" class="image-photo"/>
                </div>
              </a>
              <div>
                <div class="passenger-name-container">
                  <a href="${userUrl}">
                    <span class="light-text detail"><spring:message code="user.nameFormat" arguments="${user.name}, ${user.surname}"/></span>
                  </a>
                </div>
                <c:if test="${trip.recurrent}">
                  <div class="passenger-dates-container">
                    <i class="bi bi-calendar light-text"></i>
                    <span class="light-text">
                      <spring:message code="format.dates" var="passengerDate" arguments="${user.startDateString}, ${user.endDateString}"/>
                      <c:out value="${passengerDate}"/>
                    </span>
                  </div>
                </c:if>
              </div>
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
