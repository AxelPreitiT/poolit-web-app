<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link href="<c:url value="/resources/css/components/trip-order-by-selector.css"/>" rel="stylesheet" type="text/css"/>

<div id="order-by-row">
  <div class="input-group input-group-sm">
    <label for="order-by-select" class="input-group-text italic-text">Ordenar por:</label>
    <select id="order-by-select" name="order-by-select" class="form-select form-select-sm">
      <option value="price">Precio</option>
      <option value="date">Fecha</option>
    </select>
    <button type="button" class="btn button-color">
      <i class="bi bi-arrow-down-up light-text"></i>
    </button>
  </div>
</div>
