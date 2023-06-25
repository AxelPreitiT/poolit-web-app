<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="report" type="ar.edu.itba.paw.models.reports.Report" scope="request"/>
<link href="<c:url value="/resources/css/admin/main.css"/>" rel="stylesheet" type="text/css"/>


<div>
  <a class="text-black" href="<c:url value="/admin/reports/${report.reportId}"/>">
    <div class="row-report">
      <div class="profiles-info">
        <div class="info-profile-img">
          <div>
            <c:url value="/image/${report.reporter.userImageId}" var="imageUrlReporter"/>
            <img src="${imageUrlReporter}" alt="user image" class="image-photo-admin"/>
          </div>
          <div class="short-info-profile">
            <div class="inline-text">
              <spring:message code="user.nameFormat" arguments="${report.reporter.name}, ${report.reporter.surname}" var="reporterNameString"/>
              <h4><c:out value="${reporterNameString}"/></h4>
            </div>
            <h6 class="italic-text"><spring:message code="${param.reporterRole}"/></h6>
          </div>
        </div>
        <div>
          <i class="bi bi-megaphone-fill secondary-color h1"></i>
        </div>
        <div class="info-profile-img">
          <div class="short-info-profile-right">
            <div class="inline-text">
              <spring:message code="user.nameFormat" arguments="${report.reported.name}, ${report.reported.surname}" var="reportedNameString"/>
              <h4><c:out value="${reportedNameString}"/></h4>
            </div>
            <h6 class="italic-text"><spring:message code="${param.reportedRole}"/></h6>
          </div>
          <div>
            <c:url value="/image/${report.reporter.userImageId}" var="imageUrlReported"/>
            <img src="${imageUrlReported}" alt="user image" class="image-photo-admin"/>
          </div>
        </div>
      </div>
      <div class="trip-short-info">
        <h4><spring:message code="report.reason"/><span class="secondary-color italic-text"><spring:message code="${report.reason.springMessageCode}"/></span></h4>
        <spring:message code="report.date" arguments="${report.dateString}" var="reportDateString"/>
        <h5><c:out value="${reportDateString}"/></h5>
      </div>
    </div>
  </a>
</div>

