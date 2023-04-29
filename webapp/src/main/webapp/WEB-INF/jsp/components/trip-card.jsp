<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link href="<c:url value="/resources/css/components/trip-card.css"/>" rel="stylesheet" type="text/css"/>

<!-- Params:
        - url: url to redirect
-->

<div class="card">
    <a href="${param.url}">
        <div class="row g-0">
            <div class="col-8">
                <div class="card-body">
                    <div class="route-info">
                        <div class="route-info-row">
                            <i class="bi bi-geo-alt secondary-color route-info-icon h4"></i>
                            <div class="route-info-text">
                                <span class="secondary-color h4">Nuñez</span>
                                <span class="text">Estadio Monumental</span>
                            </div>
                        </div>
                        <div class="vertical-dotted-line"></div>
                        <div class="route-info-row">
                            <i class="bi bi-geo-alt-fill secondary-color route-info-icon h4"></i>
                            <div class="route-info-text">
                                <span class="secondary-color h4">Parque Patricios</span>
                                <span class="text">Iguazú 341</span>
                            </div>
                        </div>
                    </div>
                    <div class="footer-info">
                        <div class="footer-date-container">
                            <i class="bi bi-calendar text"></i>
                            <div class="date-info-column">
                                <span class="text">Todos los lunes</span>
                            </div>
                        </div>
                        <div class="footer-time-container">
                            <i class="bi bi-clock text"></i>
                            <span class="text">10:00</span>
                        </div>
                        <div class="footer-price-container">
                            <h2 class="secondary-color">$2.500</h2>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-4 placeholder-image-color">
                <i class="bi bi-car-front-fill placeholder-image-icon-color h1"></i>
            </div>
        </div>
    </a>
</div>
