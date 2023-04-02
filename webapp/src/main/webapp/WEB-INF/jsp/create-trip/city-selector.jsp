<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="form-floating">
  <select class="form-select h5 text" id="<c:out value="${param.id}"/>" name="<c:out value="${param.id}"/>">
    <jsp:useBean id="cities" scope="request" type="java.util.List"/>
    <c:forEach items="${cities}" var="city">
      <option value="<c:out value="${city.id}"/>"><c:out value="${city.name}"/></option>
    </c:forEach>
  </select>
  <label for="<c:out value="${param.id}"/>" class="placeholder-text h5">Barrio</label>
</div>
