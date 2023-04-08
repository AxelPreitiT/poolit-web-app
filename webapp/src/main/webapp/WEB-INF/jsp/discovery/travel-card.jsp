<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<c:url value="/css/discovery/travel-card.css"/>" rel="stylesheet">

<div class="travel-card inline-container2">
    <div class="location-column">
        <div class="address-row">
            <div class="icon-container">
                <i class="bi bi-geo-alt h3"></i>
            </div>
            <jsp:useBean id="trip" scope="request" type="java.util.Map"/>
            <h3 class="not-wrapp">${trip.get("begin")}</h3>
        </div>
        <div class="icon-container ">
            <div class="vertical-dotted-line"></div>
        </div>
        <div class="address-row">
            <div class="icon-container">
                <i class="bi bi-geo-alt-fill h3"></i>
            </div>
            <h3  class="not-wrapp">${trip.get("end")}</h3>
        </div>
    </div>
    <div class="data-column">
        <div class="data-row">
            <h5>${trip.get("places_ocuppied")}/${trip.get("places")}</h5>
            <i class="bi bi-person-fill text h5"></i>
        </div>
        <div class="data-row">
            <h5>${trip.get("date")}-${trip.get("time")}</h5>
            <i class="bi bi-calendar text h5"></i>
        </div>
        <div class="data-row">
            <h3><strong>${trip.get("price")}</strong></h3>
        </div>
    </div>
</div>
