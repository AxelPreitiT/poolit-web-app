<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<jsp:useBean id="user" type="ar.edu.itba.paw.models.User" scope="request"/>

<link href="<c:url value="/resources/css/users/profile-container.css"/>" rel="stylesheet" type="text/css"/>

<div class="user-info-container">
  <c:url value="/users/profile" var="updateUserUrl"/>
  <form:form modelAttribute="updateUserForm" action="${updateUserUrl}" method="post" cssClass="form-style" enctype="multipart/form-data">
  <div class="avatar-img">
    <div class="circular--landscape no-edit">
      <c:url value="/image/${user.userImageId}" var="profileImageUrl"/>
      <img class="circular--square" src="${profileImageUrl}" alt="<spring:message code="register.profileImage"/>">
    </div>
    <div class="circular--landscape-container hidden edit" id="user-image-display">
        <label for="image-file" id="image-label" class="secondary-bg-color shadow-btn button-style">
            <div class="edit-icon"><i class="icon bi bi-pencil-fill secondary-color h1"></i></div>
            <c:url value="/image/${user.userImageId}" var="profileImageUrl"/>
            <div class="circular--landscape">
            <img class="circular--square" src="${profileImageUrl}" alt="<spring:message code="register.profileImage"/>">
            </div>
        </label>
        <form:input path="imageFile" type="file" accept="image/*" id="image-file" name="image-file"/>
    </div>
  </div>
      <spring:message code="user.nameFormat" arguments="${user.name}, ${user.surname}" var="userNameString"/>
  <h3 id="user-name" class="no-edit"><c:out value="${userNameString}"/></h3>
  <div class="row-info rows edit hidden">
    <h6><spring:message code="user.name"/></h6>
    <form:input path="username" cssClass="form-control " id="username" />
  </div>
  <div class="row-info rows edit hidden">
      <h6><spring:message code="user.surname"/></h6>
      <form:input path="surname" cssClass="form-control " id="surname" />
  </div>
  <div class="row-info rows">
    <h6><spring:message code="user.email"/></h6>
    <h4><c:out value="${user.email}"/></h4>
  </div>
  <div class="row-info rows">
    <h6><spring:message code="user.phone"/></h6>
    <h4 class="no-edit"><c:out value="${user.phone}"/></h4>
    <form:input path="phone" cssClass="form-control hidden edit" id="phone" />
    <div class="error-item">
        <i class="bi bi-exclamation-circle-fill danger"></i>
        <form:errors path="phone" cssClass="danger error-style" element="span"/>
    </div>
  </div>
  <div class="row-info rows">
    <h6><spring:message code="user.district"/></h6>
    <h4 class="no-edit"><c:out value="${user.bornCity.name}"/></h4>
    <div class="hidden edit">
        <spring:message code="user.district" var="districtPlaceholder"/>
        <jsp:include page="/WEB-INF/jsp/components/city-selector.jsp">
            <jsp:param name="id" value="bornCityId"/>
            <jsp:param name="defaultText" value="${districtPlaceholder}"/>
        </jsp:include>
        <div class="error-row">
            <div class="error-item">
                <i class="bi bi-exclamation-circle-fill danger"></i>
                <form:errors path="bornCityId" cssClass="error-style danger" element="span"/>
            </div>
        </div>
    </div>
  </div>
  <div class="row-info rows">
    <h6><spring:message code="user.locale"/></h6>
    <c:if test="${(user.mailLocale eq 'es')}">
      <h4 class="no-edit"><spring:message code="user.locale.es"/></h4>
    </c:if>
    <c:if test="${(user.mailLocale eq 'en')}">
      <h4 class="no-edit"><spring:message code="user.locale.en"/></h4>
    </c:if>
    <div class="input-group hidden edit">
        <form:select path="mailLocale" class="form-select" id="mail-locale" name="mail-locale">
            <form:option value="es"><spring:message code="register.spanish"/></form:option>
            <form:option value="en"><spring:message code="register.english"/></form:option>
        </form:select>
    </div>
  </div>
  <sec:authorize access="hasRole('ROLE_DRIVER')">
    <div class="row-info rows">
      <jsp:useBean id="countTrips" type="java.lang.Integer" scope="request"/>
      <h6><spring:message code="user.countTrips"/></h6>
      <h4><c:out value="${countTrips}"/></h4>
    </div>
    <div class="row-info rows">
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
  </sec:authorize>
  <div class="row-info rows">
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
    <div class="row-info rows hidden edit">
        <div class="edit-button-container">
            <button type="button" onclick="toggleEdit()" class="btn button-style primary-button shadow-btn">
                <i class="bi bi-pencil-square light-text h5"></i>
                <span class="button-text-style light-text h5"><spring:message code="review.cancel"/></span>
            </button>
            <button id="update-car" type="submit" class="btn button-style button-color shadow-btn hidden edit">
                <i class="bi bi-check2 light-text h5"></i>
                <span class="button-text-style light-text h5"><spring:message code="updateCar.save"/></span>
            </button>
        </div>
    </div>
    <div class="row-info rows no-edit">
        <div class="edit-button-container">
            <button id="edit-car" type="button" onclick="toggleEdit()" class="btn button-style button-color shadow-btn">
                <i class="bi bi-pencil-square light-text h5"></i>
                <span class="button-text-style light-text h5" id="editButton" ><spring:message code="profile.edit"/></span>
            </button>
        </div>
    </div>
  </form:form>
  <script src="<c:url value="/resources/js/cars/editCar.js"/>" type="application/javascript"></script>
</div>

