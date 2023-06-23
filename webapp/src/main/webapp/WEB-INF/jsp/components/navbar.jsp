<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link href="<c:url value="/resources/css/components/navbar.css"/>" rel="stylesheet" type="text/css"/>

<nav class="navbar navbar-expand-md primary-bg-color" id="main-navbar">
    <div class="container-fluid navbar-style">
        <a class="navbar-brand" href="<c:url value="/"/>">
            <img src="<c:url value="/resources/images/poolit/poolit.svg"/>" alt="<spring:message code="navbar.poolit"/>" class="brand-logo">
        </a>
        <c:if test="${(empty param.showOnlyLogo) || !param.showOnlyLogo}">
        <div class="collapse navbar-collapse navbar-link-items">
            <ul class="navbar-nav nav-items">
                <sec:authorize access="isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/trips/reserved"/>">
                            <h4 class="light-text"><spring:message code="navbar.reservados"/></h4>
                        </a>
                    </li>
                    <sec:authorize access="hasRole('ROLE_DRIVER') or hasRole('ROLE_ADMIN')">
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value="/trips/created"/>">
                                <h4 class="light-text"><spring:message code="navbar.created"/></h4>
                            </a>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value="/admin"/>">
                                <h4 class="light-text"><spring:message code="navbar.admin"/></h4>
                            </a>
                        </li>
                    </sec:authorize>
                </sec:authorize>
            </ul>
            <sec:authorize access="isAuthenticated()">
                <sec:authorize access="hasRole('ROLE_DRIVER') or hasRole('ROLE_ADMIN')">
                    <div class="create-trip-btn">
                        <a href="<c:url value="/trips/create"/>">
                            <button class="btn button-style button-color shadow-btn">
                                <i class="bi bi-plus light-text h4"></i>
                                <span class="button-text-style light-text h4"><spring:message code="navbar.btnCreated"/></span>
                            </button>
                        </a>
                    </div>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_USER')">
                    <div class="create-trip-btn" data-bs-toggle="modal" data-bs-target="#modal-create">
                        <button class="btn button-style button-color shadow-btn">
                            <i class="bi bi-plus light-text h4"></i>
                            <span class="button-text-style light-text h4"><spring:message code="navbar.btnCreated"/></span>
                        </button>
                    </div>
                    <div class="modal fade" id="modal-create" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h4 class="modal-title secondary-color"><spring:message code="navbar.modal.title"/></h4>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                </div>
                                <div class="modal-body">
                                    <div>
                                        <span class="text"><spring:message code="navbar.modal.info"/></span>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <c:url value="/changeRole" var="changeRole"/>
                                    <form:form method="POST" action="${changeRole}">
                                        <button type="submit" class="btn secondary-bg-color">
                                            <span class="light-text"><spring:message code="navbar.modal.btnContinue"/></span>
                                        </button>
                                    </form:form>
                                </div>
                            </div>
                        </div>
                    </div>

                </sec:authorize>
            </sec:authorize>
        </div>
        <sec:authorize access="isAuthenticated()">
            <div class="profile-container dropdown">
                <button class="btn rounded-circle shadow-btn" data-bs-toggle="dropdown">
                    <c:url value="/image/${loggedUser.userImageId}" var="imageUrl"/>
                    <img class="image-photo" src="${imageUrl}" alt="<spring:message code="navbar.profile"/>">
                </button>
                <ul class="dropdown-menu dropdown-menu-end primary-bg-color modal-profile">
                    <li>
                        <a class="dropdown-item" href="<c:url value="/users/profile"/>">
                            <div class="container text-center">
                                <div class="row dropdown-row">
                                    <div class="col-sm-2 dropdown-row-media">
                                        <img class="image-photo" src="${imageUrl}" alt="<spring:message code="navbar.profile"/>">
                                        <img class="image-photo with-title" src="${imageUrl}" alt="<spring:message code="navbar.profile"/>" title="<spring:message code="navbar.profile"/>">
                                    </div>
                                    <div class="col-sm-10 dropdown-row-text">
                                        <span class="button-text-style light-text h1">
                                            <c:choose>
                                                <c:when test="${loggedUser == null}">
                                                    <spring:message code="navbar.profile"/>
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
                                    <div class="col-sm-2 dropdown-row-media">
                                        <i class="bi bi-box-arrow-right light-text h5"></i>
                                        <i class="bi bi-box-arrow-right light-text h5 with-title" title="<spring:message code="navbar.logout"/>"></i>
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
                        <span class="button-text-style light-text h5"><spring:message code="navbar.register"/></span>
                    </button>
                    <button class="btn button-style button-color shadow-btn with-title" title="<spring:message code="navbar.register"/>">
                        <i class="bi bi-person-circle light-text h5"></i>
                    </button>
                </a>
                <a href="<c:url value="/users/login"/>">
                    <button class="btn button-style button-color shadow-btn">
                        <i class="bi bi-box-arrow-in-right light-text h5"></i>
                        <span class="button-text-style light-text h5"><spring:message code="navbar.login"/></span>
                    </button>
                    <button class="btn button-style button-color shadow-btn with-title" title="<spring:message code="navbar.login"/>">
                        <i class="bi bi-box-arrow-in-right light-text h5"></i>
                    </button>
                </a>
            </div>
        </sec:authorize>
        </c:if>
    </div>
</nav>

<script src="<c:url value="/resources/js/components/navbar.js"/>"></script>

