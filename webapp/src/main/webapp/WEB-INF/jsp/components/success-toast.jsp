<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link href="<c:url value="/resources/css/components/success-toast.css"/>" rel="stylesheet" type="text/css"/>

<!-- Params:
        - title: The title of the toast
        - message: The text of the toast
-->

<div id="toast">
    <div class="toast fade show success-bg-color" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="toast-header success-bg-color">
            <strong class="light-text h3"><c:out value="${param.title}"/></strong>
            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div class="toast-body">
            <span class="light-text h5"><c:out value="${param.message}"/></span>
        </div>
    </div>
</div>
