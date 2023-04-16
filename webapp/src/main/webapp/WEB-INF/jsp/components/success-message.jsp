<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<c:url value="/css/components/success-message.css"/>" rel="stylesheet"/>
<div class="success-container">
    <i class="fa-solid fa-circle-check success h1"></i>
    <h1 class="success"><c:out value="${param.title}"/></h1>
</div>
