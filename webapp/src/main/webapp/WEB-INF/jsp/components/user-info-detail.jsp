<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="<c:url value="/css/components/detail.css"/>" rel="stylesheet"/>
<jsp:useBean id="user" type="ar.edu.itba.paw.models.User" scope="request"/>
<div class="contact-container">
    <i class="bi bi-telephone-fill text h5"></i>
    <div class="contact-text-container">
        <div class="contact-title-container">
            <h4 class="text"><c:out value="${param.title}"/></h4>
        </div>
        <div class="contact-info-container">
            <div class="contact-info-item">
                <h6>Email</h6>
                <p><c:out value="${user.email}"/></p>
            </div>
            <div class="contact-info-item">
                <h6>Teléfono</h6>
                <p><c:out value="${user.phone}"/></p>
            </div>
        </div>
    </div>
</div>
