<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<link rel="stylesheet" href="<c:url value="/resources/css/trip-info/report-form.css"/>" type="text/css">

<jsp:useBean id="reportItem" type="ar.edu.itba.paw.models.reports.ItemReport" scope="request" />
<jsp:useBean id="reportForm" scope="request" type="ar.edu.itba.paw.webapp.form.ReportForm" />
<jsp:useBean id="userReported" scope="request" type="ar.edu.itba.paw.models.User" />

<div class="report-form-container">
    <c:url value="/image/${userReported.userImageId}" var="userReportedImageUrl"/>
    <spring:message code="user.nameFormat" var="userReportedName" arguments="${passenger.name}, ${passenger.surname}"/>
    <div class="report-form-header">
        <img src="${userReportedImageUrl}" alt="${userReportedName}" class="passenger-image">
        <div class="report-form-title">
            <h3 class="secondary-color">${userReportedName}</h3>
            <hr class="secondary-color">
        </div>
    </div>
    <div class="report-form">
        <form:hidden path="relation" value="${reportItem.relation}" />
        <div>
            <label for="user-reason-${userReported.userId}" class="report-option-label">
                <strong class="text"><spring:message code="report.modal.option.label"/></strong>
            </label>
            <form:select path="reason" cssClass="form-select" id="user-reason-${userReported.userId}">
                <c:forEach items="${reportItem.relation.reportOptions}" var="reportOption">
                    <spring:message code="${reportOption.springMessageCode}" var="springMessageCode"/>
                    <form:option value="${reportOption.name}" label="${springMessageCode}"/>
                </c:forEach>
            </form:select>
        </div>
        <div>
            <label for="user-comment-${userReported.userId}" class="report-comment-label">
                <strong class="text"><spring:message code="report.modal.comment.label"/></strong>
            </label>
            <form:textarea path="comment" cssClass="form-control" id="user-comment-${userReported.userId}"/>
        </div>
    </div>
</div>
