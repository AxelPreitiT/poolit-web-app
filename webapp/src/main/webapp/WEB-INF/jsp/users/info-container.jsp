<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link href="<c:url value="/resources/css/users/info-container.css"/>" rel="stylesheet" type="text/css"/>

<div class="list-container">
  <div class="row-data">
    <h2>${param.title}</h2>
  </div>
  <div>
    <jsp:include page="/WEB-INF/jsp/users/travel-info.jsp"/>
    <jsp:include page="/WEB-INF/jsp/users/travel-info.jsp"/>
    <jsp:include page="/WEB-INF/jsp/users/travel-info.jsp"/>
  </div>
  <a href="/test">
    <div class="plus-btn">
      <h3 class="text">${param.btndesc}</h3>
      <i class="h3 bi text bi-box-arrow-in-up-right"></i>
    </div>
  </a>
</div>
