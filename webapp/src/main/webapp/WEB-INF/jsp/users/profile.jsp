<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/resources/css/users/profile.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body class="background-color">
  <jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
  <div class="main-container">
    <div class="col-md-4 col-lg-3 d-none d-md-block">
      <div class="titulo">
        Hola pepe
      </div>
    </div>
    <div class="col-md-8 col-lg-9 ">
      <div class="titulo">
        Hola pepe
      </div>
    </div>
  </div>
</body>
</html>
