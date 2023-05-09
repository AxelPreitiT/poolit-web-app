<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="trip" type="ar.edu.itba.paw.models.trips.Trip" scope="request"/>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<link href="<c:url value="/resources/css/components/trip-detail.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/resources/css/components/trip-detail-card.css"/>" rel="stylesheet" type="text/css"/>

<div id="main-header-row">
  <h1 class="secondary-color"><spring:message code="tripDetails.title"/></h1>
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
      <div class="col-sm-6 col-md-5 col-lg-5">
        <div id="car-info-image">
          <c:url value="/image/${trip.car.image_id}" var="carImageUrl"/>
          <div class="placeholder-image">
            <img src="${carImageUrl}" alt="car image"/>
          </div>
<%--          <div class="details-container primary-bg-color passenger-container">--%>
<%--          <div class="light-text h4"><spring:message code="tripDetails.passengers"/></div>--%>
<%--          <c:forEach items="${passengers}" var="user">--%>
<%--                <c:url value="/profile/${user.userId}" var="userUrl"/>--%>
<%--                <c:url value="/image/${user.userImageId}" var="userImageId"/>--%>
<%--                <div class="col-sm-12 col-xl-6">--%>
<%--                  <div>--%>
<%--                      <a href="${userUrl}" class="show-row profile-link">--%>
<%--                          <div>--%>
<%--                              <img src="${userImageId}" alt="user image" class="image-photo"/>--%>
<%--                          </div>--%>
<%--                          <div class="show-row-content">--%>
<%--                              <span class="light-text detail"><spring:message code="user.nameFormat" arguments="${user.name}, ${user.surname}"/></span>--%>
<%--                          </div>--%>
<%--                      </a>--%>
<%--                  </div>--%>
<%--                </div>--%>
<%--            </c:forEach>--%>
<%--          </div>--%>
        </div>
      </div>
    </div>
  </div>
</div>


