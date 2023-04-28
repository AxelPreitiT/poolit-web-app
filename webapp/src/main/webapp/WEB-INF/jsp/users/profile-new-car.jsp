<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
  <title>Profile</title>
  <jsp:include page="/resources/external-resources.jsp"/>
  <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
  <link href="<c:url value="/resources/css/users/profile.css"/>" rel="stylesheet" type="text/css"/>
</head>
<body class="background-color">
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp"/>
<div class="main-container">
  <jsp:include page="/WEB-INF/jsp/users/profile-container.jsp"/>
  <c:url value="/profile" var="createCarUrl" />
  <form:form modelAttribute="createCarForm" action="${createCarUrl}" method="get">
  <div class="List-properties-container">
    <div class="list-container">
      <div class="row-data">
        <h2>Mis Autos</h2>
      </div>
      <div class="data-content">
        <jsp:include page="/WEB-INF/jsp/users/car-container.jsp"/>
        <jsp:include page="/WEB-INF/jsp/users/car-container.jsp"/>
        <jsp:include page="/WEB-INF/jsp/users/car-container.jsp"/>
        <jsp:include page="/WEB-INF/jsp/users/car-container.jsp"/>
        <jsp:include page="/WEB-INF/jsp/users/car-container.jsp"/>
        <div class="add-card">
          <div class="infoCar">
            <div class="form-floating">
              <form:input path="" type="text" class="form-control text" id="infoCar" name="infoCar" placeholder="Descripcion"/>
              <form:label path="" for="infoCar" class="placeholder-text">IMAGEN</form:label>
            </div>
            <div class="error-container">
              <form:errors path="carInfo" cssClass="formError" element="p"/>
            </div>
          </div>
          <div class="infoCar">
              <div class="form-floating">
                <form:input path="carInfo" type="text" class="form-control text" id="infoCar" name="infoCar" placeholder="Descripcion"/>
                <form:label path="carInfo" for="infoCar" class="placeholder-text">Descripcion del auto</form:label>
              </div>
              <div class="error-container">
                <form:errors path="carInfo" cssClass="formError" element="p"/>
              </div>
          </div>
          <div class="infoCar">
            <div>
              <div class="form-floating">
                <form:input path="carPlate" type="text" class="form-control text" id="carPlate" name="carPlate" placeholder="Descripcion"/>
                <form:label path="carPlate" for="carPlate" class="placeholder-text">Patente</form:label>
              </div>
              <div class="error-container">
                <form:errors path="carInfo" cssClass="formError" element="p"/>
              </div>
            </div>
          </div>
          <div class="btn-container">
            <button class="btn btn-primary" type="submit">Guardar</button>
            <button class="btn btn-danger" type="submit">Cancelar</button>
          </div>
        </div>
      </div>
    </div>
    <jsp:include page="/WEB-INF/jsp/users/info-container.jsp">
      <jsp:param name="title" value="Proximos viajes"/>
      <jsp:param name="btndesc" value="Ver todos los proximos viajes"/>
    </jsp:include>
    <jsp:include page="/WEB-INF/jsp/users/info-container.jsp">
      <jsp:param name="title" value="Viajes realizados"/>
      <jsp:param name="btndesc" value="Ver todos los viajes realizados"/>
    </jsp:include>
  </div>
  </form:form>
</div>
</body>
</html>
