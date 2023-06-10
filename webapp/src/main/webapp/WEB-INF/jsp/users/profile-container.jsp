<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="user" type="ar.edu.itba.paw.models.User"  scope="request"/>

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
            <c:url value="/image/${user.userImageId}" var="profileImageUrl"/>
            <div class="circular--landscape">
            <img class="circular--square" src="${profileImageUrl}" alt="<spring:message code="register.profileImage"/>">
            </div>
        </label>
        <form:input path="imageFile" type="file" accept="image/*" id="image-file" name="image-file"/>
        <span class="edit-icon"><i class="icon bi bi-pencil-fill secondary-color h1"></i></span>
    </div>
  </div>
  <h3 id="user-name" class="no-edit"><spring:message code="user.nameFormat" arguments="${user.name}, ${user.surname}"/></h3>
  <div class="row-info rows edit hidden">
    <h6><spring:message code="user.name"/></h6>
    <form:input path="username" cssClass="form-control " id="username" />
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
  <c:if test="${(user.role eq 'DRIVER')}">
    <jsp:useBean id="rating" type="java.lang.Double" scope="request"/>
    <div class="row-info rows">
      <h6><spring:message code="user.rating"/></h6>
      <div class="d-flex justify-content-between align-items-center">
        <div class="ratings">
          <c:forEach var="i" begin="1" end="${rating}">
            <i class="bi bi-star-fill secondary-color h4"></i>
          </c:forEach>
          <c:if test="${rating % 1 >= 0.5}">
            <i class="bi bi-star-half secondary-color h4"></i>
            <c:forEach var="i" begin="${rating + 2}" end="5">
              <i class="bi bi-star secondary-color h4"></i>
            </c:forEach>
          </c:if>
          <c:if test="${rating % 1 < 0.5}">
            <c:forEach var="i" begin="${rating + 1}" end="5">
              <i class="bi bi-star secondary-color h4"></i>
            </c:forEach>
          </c:if>
        </div>
      </div>
    </div>
  </c:if>
  <div class="row-info rows hidden edit">
    <button id="update-car" type="submit" class="btn button-style button-color shadow-btn hidden edit">
        <i class="bi bi-check2 light-text h3"></i>
        <span class="button-text-style light-text h3"><spring:message code="updateCar.save"/></span>
    </button>
  </div>
  <div class="row-info rows no-edit">
      <button id="edit-car" type="button" class="btn button-style button-color shadow-btn">
            <i class="bi bi-pencil-square light-text h3"></i>
            <span class="button-text-style light-text h3" id="editButton" onclick="toggleEdit()">Editar</span>
        </button>
  </div>
  </form:form>
  <script src="<c:url value="/resources/js/cars/editCar.js"/>" type="application/javascript"></script>
</div>

