<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                        <h4 class="light-text">Inicio</h4>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/reserved"/>">
                        <h4 class="light-text">Reservados</h4>
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value="/created"/>">
                        <h4 class="light-text">Creados</h4>
                    </a>
                </li>
            </ul>
            <div class="create-trip-btn">
                <a href="<c:url value="/trips/create"/>">
                    <button class="btn button-style button-color shadow-btn">
                        <i class="bi bi-plus light-text h3"></i>
                        <span class="button-text-style light-text h3">Crear</span>
                    </button>
                </a>
            </div>
        </div>
        <div class="profile-container dropdown">
            <button class="btn rounded-circle shadow-btn" data-bs-toggle="dropdown">
                <i class="bi bi-person-circle light-text h1 profile-btn"></i>
            </button>
            <ul class="dropdown-menu dropdown-menu-end primary-bg-color">
                <li>
                    <a class="dropdown-item" href="<c:url value="/profile/${}"/>">
                        <div class="container text-center">
                            <div class="row dropdown-row">
                                <div class="col-sm-2">
                                    <i class="bi bi-person-circle light-text h1"></i>
                                </div>
                                <div class="col-sm-10 dropdown-row-text">
                                    <span class="button-text-style light-text h1">Jorge Perez</span>
                                </div>
                            </div>
                        </div>
                    </a>
                </li>
                <li><hr class="dropdown-divider light-text"></li>
                <li>
                    <a class="dropdown-item" href="<c:url value="/cars"/>">
                        <div class="container text-center">
                            <div class="row dropdown-row">
                                <div class="col-sm-2">
                                    <i class="bi bi-car-front-fill light-text h5"></i>
                                </div>
                                <div class="col-sm-10 dropdown-row-text">
                                    <span class="button-text-style light-text h5">Mis autos</span>
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
                                    <span class="button-text-style light-text h5">Cerrar sesión</span>
                                </div>
                            </div>
                        </div>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<script src="<c:url value="/resources/js/components/navbar.js"/>"></script>

