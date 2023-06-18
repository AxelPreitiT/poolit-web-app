<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title><spring:message code="admin.title"/></title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/resources/css/admin/main.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body class="background-color">
<div id="navbar-container">
    <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
</div>
<div class="main-container-style container-color">
    <h1>administrador</h1>
</div>
</body>
</html>