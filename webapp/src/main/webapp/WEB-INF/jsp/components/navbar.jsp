<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link href="<c:url value="/resources/css/components/navbar.css"/>" rel="stylesheet" type="text/css"/>

<nav class="navbar navbar-expand-md primary-bg-color">
    <div class="container-fluid navbar-style">
        <a class="navbar-brand" href="<c:url value="/"/>">
            <img src="<c:url value="/resources/images/poolit/poolit.svg"/>" alt="POOLIT" class="brand-logo">
        </a>
        <div class="collapse navbar-collapse navbar-link-items">
            <ul class="navbar-nav nav-items">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/"/>">
                        <h4 class="light-text"><spring:message code="navbar.begin"/></h4>
                    </a>
                </li>
                <sec:authorize access="isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/trips/reserved"/>">
                            <h4 class="light-text"><spring:message code="navbar.reservados"/></h4>
                        </a>
                    </li>
                    <sec:authorize access="hasRole('ROLE_DRIVER')">
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value="/trips/created"/>">
                                <h4 class="light-text"><spring:message code="navbar.created"/></h4>
                            </a>
                        </li>
                    </sec:authorize>
                </sec:authorize>
            </ul>
            <sec:authorize access="isAuthenticated()">
                <sec:authorize access="hasRole('ROLE_DRIVER')">
                    <div class="create-trip-btn">
                        <a href="<c:url value="/trips/create"/>">
                            <button class="btn button-style button-color shadow-btn">
                                <i class="bi bi-plus light-text h3"></i>
                                <span class="button-text-style light-text h3"><spring:message code="navbar.btnCreated"/></span>
                            </button>
                        </a>
                    </div>
                </sec:authorize>
            </sec:authorize>
        </div>
        <sec:authorize access="isAuthenticated()">
            <div class="profile-container dropdown">
                <button class="btn rounded-circle shadow-btn" data-bs-toggle="dropdown">
                    <c:url value="/image/${loggedUser.userImageId}" var="imageUrl"/>
                    <img class="image-photo" src="${imageUrl}" alt="userImage">
<%--                    <i class="bi bi-person-circle light-text h1 profile-btn"></i>--%>
                </button>
                <ul class="dropdown-menu dropdown-menu-end primary-bg-color">
                    <li>
                        <a class="dropdown-item" href="<c:url value="/users/profile"/>">
                            <div class="container text-center">
                                <div class="row dropdown-row">
                                    <div class="col-sm-2">
                                        <img class="image-photo" src="${imageUrl}" alt="userImage">
                                    </div>
                                    <div class="col-sm-10 dropdown-row-text">
                                        <span class="button-text-style light-text h1">
                                            <c:choose>
                                                <c:when test="${loggedUser == null}">
                                                    Mi perfil
                                                </c:when>
                                                <c:otherwise>
                                                    ${loggedUser.name} ${loggedUser.surname}
                                                </c:otherwise>
                                            </c:choose>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </li>
                    <li><hr class="dropdown-divider light-text"></li>
                    <li>
                        <a class="dropdown-item" href="<c:url value="/logout"/>">
                            <div class="container text-center">
                                <div class="row dropdown-row">
                                    <div class="col-sm-2">
                                        <i class="bi bi-box-arrow-right light-text h5"></i>
                                    </div>
                                    <div class="col-sm-10 dropdown-row-text">
                                        <span class="button-text-style light-text h5"><spring:message code="navbar.logout"/></span>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </li>
                </ul>
        </div>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <div id="not-authenticated-container">
                <a href="<c:url value="/users/create"/>">
                    <button class="btn button-style button-color shadow-btn">
                        <i class="bi bi-person-circle light-text h5"></i>
                        <span class="button-text-style light-text h5">Registrarse</span>
                    </button>
                </a>
                <a href="<c:url value="/users/login"/>">
                    <button class="btn button-style button-color shadow-btn">
                        <i class="bi bi-box-arrow-in-right light-text h5"></i>
                        <span class="button-text-style light-text h5">Iniciar sesión</span>
                    </button>
                </a>
            </div>
        </sec:authorize>
    </div>
</nav>

<script src="<c:url value="/resources/js/components/navbar.js"/>"></script>

