<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="user" type="ar.edu.itba.paw.models.User"  scope="request"/>

<link href="<c:url value="/resources/css/users/profile-container.css"/>" rel="stylesheet" type="text/css"/>

<div class="user-info-container">
  <div class="avatar-img">
    <div class="circular--landscape">
      <c:url value="/image/${user.userImageId}" var="profileImageUrl"/>
      <img class="circular--square" src="${profileImageUrl}" alt="<spring:message code="register.profileImage"/>">
    </div>
  </div>
  <spring:message code="user.nameFormat" arguments="${user.name}, ${user.surname}" var="userNameFormatString"/>
  <h3 id="user-name"><c:out value="${userNameFormatString}"/> </h3>
  <c:if test="${user.isDriver}">
    <div class="row-info">
      <jsp:useBean id="countTrips" type="java.lang.Integer" scope="request"/>
      <h6><spring:message code="user.countTrips"/></h6>
      <h4><c:out value="${countTrips}"/></h4>
    </div>
    <div class="row-info">
      <h6><spring:message code="driver.review.rating"/></h6>
      <c:choose>
        <c:when test="${param.hasBeenRatedAsDriver}">
          <div class="d-flex justify-content-between align-items-center">
            <div class="ratings">
              <jsp:useBean id="driverRating" type="java.lang.Double" scope="request"/>
              <c:set var="rating" value="${driverRating}" scope="request"/>
              <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp">
                <jsp:param name="fontSize" value="h4"/>
                <jsp:param name="fontColor" value="secondary-color"/>
              </jsp:include>
            </div>
          </div>
        </c:when>
        <c:otherwise>
          <h4><spring:message code="review.none"/></h4>
        </c:otherwise>
      </c:choose>
    </div>
  </c:if>
  <div class="row-info">
    <h6><spring:message code="passenger.review.rating"/></h6>
    <c:choose>
      <c:when test="${param.hasBeenRatedAsPassenger}">
        <div class="d-flex justify-content-between align-items-center">
          <div class="ratings">
            <jsp:useBean id="passengerRating" type="java.lang.Double" scope="request"/>
            <c:set var="rating" value="${passengerRating}" scope="request"/>
            <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp">
              <jsp:param name="fontSize" value="h4"/>
              <jsp:param name="fontColor" value="secondary-color"/>
            </jsp:include>
          </div>
        </div>
      </c:when>
      <c:otherwise>
        <h4><spring:message code="review.none"/></h4>
      </c:otherwise>
    </c:choose>
  </div>
<%--    <div class="row-info avatar-img">--%>
<%--      <c:choose>--%>
<%--        <c:when test="${!isBlocked}">--%>
<%--          <form action="<c:url value="/profile/${user.userId}/block"/>" method="post">--%>
<%--            <button id="block" type="submit" class="btn button-style danger-button shadow-btn no-edit">--%>
<%--              <i class="bi bi-lock-fill light-text h3"></i>--%>
<%--              <span class="button-text-style light-text h3" id="blockButton"><spring:message code="profile.block"/></span>--%>
<%--            </button>--%>
<%--          </form>--%>
<%--        </c:when>--%>
<%--        <c:otherwise>--%>
<%--          <form action="<c:url value="/profile/${user.userId}/unblock"/>" method="post">--%>
<%--            <button id="unblock" type="submit" class="btn button-style button-color shadow-btn no-edit">--%>
<%--              <i class="bi bi-unlock-fill light-text h3"></i>--%>
<%--              <span class="button-text-style light-text h3" id="unblockButton"><spring:message code="profile.unblock"/></span>--%>
<%--            </button>--%>
<%--          </form>--%>
<%--        </c:otherwise>--%>
<%--      </c:choose>--%>
<%--    </div>--%>
</div>

