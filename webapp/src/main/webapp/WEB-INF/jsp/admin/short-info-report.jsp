<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="report" type="ar.edu.itba.paw.models.reports.Report" scope="request"/>
<link href="<c:url value="/resources/css/admin/main.css"/>" rel="stylesheet" type="text/css"/>


<div>
  <a class="text-black" href="<c:url value="/admin/${report.reportId}"/>">
    <div class="row-report">
      <div class="profiles-info">
        <div class="info-profile-img">
          <div>
            <img src="/image/${report.trip.driver.userImageId}" alt="user image" class="image-photo-admin"/>
          </div>
          <div class="short-info-profile">
            <div class="inline-text">
              <h4><spring:message code="user.nameFormat" arguments="${report.reporter.name}, ${report.reporter.surname}"/></h4>
            </div>
            <h6 class="italic-text"><spring:message code="${param.reporterRole}"/></h6>
            <c:set var="rating" value="${report.reporter.passengerRating}" scope="request"/>
            <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp"/>
          </div>
        </div>
        <div class="location-line">
          <div class="location-line-content">
            <i class="fa-solid fa-bounce secondary-color"></i>
            <div class="dotted-line"></div>
          </div>
        </div>
        <div class="info-profile-img">
          <div class="short-info-profile-right">
            <div class="inline-text">
              <h4><spring:message code="user.nameFormat" arguments="${report.reported.name}, ${report.reported.surname}"/></h4>
            </div>
            <h6 class="italic-text"><spring:message code="${param.reportedRole}"/></h6>
            <c:set var="rating" value="${report.reporter.passengerRating}" scope="request"/>
            <jsp:include page="/WEB-INF/jsp/components/rating-stars.jsp"/>
          </div>
          <div>
            <img src="/image/${report.trip.driver.userImageId}" alt="user image" class="image-photo-admin"/>
          </div>
        </div>
      </div>
      <div class="trip-short-info">
        <h4><spring:message code="report.reason"/><span class="primary-color italic-text"><spring:message code="${report.reason.springMessageCode}"/></span></h4>
        <h5><spring:message code="report.date" arguments="${report.dateString}"/></h5>
      </div>
    </div>
  </a>
</div>

