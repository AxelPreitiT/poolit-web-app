<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<link href="<c:url value="/resources/css/users/travel-info.css"/>" rel="stylesheet" type="text/css"/>

<div>
    <a href="/test">
        <div class="card-info">
            <div class="data-container">
                <div class="route-container">
                    <i class="secondary-color bi bi-geo-alt icon-style"></i>
                    <div class="secondary-color horizontal_dotted_line"></div>
                    <i class="secondary-color bi bi-geo-alt-fill icon-style"></i>
                </div>
                <div class="adress-container">
                    <div class="route-info-text">
                        <span class="secondary-color h3">Nuñez</span>
                        <span class="text">Estadio Monumental</span>
                    </div>
                    <div class="route-info-text">
                        <span class="secondary-color h3 aling-right">Nuñez</span>
                        <span class="text aling-right">Estadio Monumental</span>
                    </div>
                </div>
                <div class="extra-info-container">
                    <div class="line-container">
                        <i class="bi bi-calendar text"></i>
                        <h6 class="text">23/05/2000</h6>
                    </div>
                    <div>
                        <i class="bi bi-clock text"></i>
                        <span class="text">10:00</span>
                    </div>
                    <div>
                        <h5 class="text">$2.500</h5>
                    </div>
                </div>
            </div>
            <div class="img-container">
                <img class="car-container"  src="https://lumiere-a.akamaihd.net/v1/images/og_cars_lightningmcqueenday_18244_4435f27a.jpeg?region=40,0,1120,630">
            </div>
        </div>
    </a>
</div>