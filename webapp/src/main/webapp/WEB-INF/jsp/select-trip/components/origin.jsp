<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="location-data">
    <jsp:useBean id="trip" scope="request" type="ar.edu.itba.paw.models.Trip"/>
    <h4>
        <c:out value="${trip.originCity.name}" escapeXml="true"/></h4>
    <h6 class="fw-light"><c:out value="${trip.originAddress}" escapeXml="true "/></h6>
    <h6 class="fw-light"><c:out value="${trip.date}" escapeXml="true"/></h6>
    <h6 class="fw-light"><c:out value="${trip.time}" escapeXml="true"/></h6>

</div>
