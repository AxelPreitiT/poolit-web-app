<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:useBean id="user" type="ar.edu.itba.paw.models.User"  scope="request"/>

<link href="<c:url value="/resources/css/users/profile-container.css"/>" rel="stylesheet" type="text/css"/>

<div class="user-info-container">
  <div class="avatar-img">
    <div class="circular--landscape">
      <c:url value="/image/${user.user_image_id}" var="profileImageUrl"/>
      <img class="circular--square" src="${profileImageUrl}">
    </div>
  </div>
  <div class="row-info">
    <h6>Nombre y apellido</h6>
    <h4>${user.username} ${user.surname}</h4>
  </div>
  <div class="row-info">
    <h6>Email</h6>
    <h4>${user.email}</h4>
  </div>
  <div class="row-info">
    <h6>Telefono</h6>
    <h4>${user.phone}</h4>
  </div>
  <div class="row-info">
    <h6>Localidad origen</h6>
    <h4>${user.bornCity.name}</h4>
  </div>
  <form:form method = "POST" action = "${param.path}">
    <button type="submit" class="btn btn-primary btn-lg">Cambiar a ${param.role}</button>
  </form:form>
</div>

