<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="location-container">
  <div class="location-title" >
    <i class="bi bi-geo-alt text h2"></i>
    <h3><c:out value="${param.originCity}" escapeXml="true"/></h3>
  </div>
  <h6 class="fw-light"><c:out value="${param.originAddress}" escapeXml="true "/></h6>
  <h6 class="fw-light"><c:out value="${param.originDate}" escapeXml="true"/></h6>
  <h6 class="fw-light"><c:out value="${param.originTime}" escapeXml="true"/></h6>
</div>
