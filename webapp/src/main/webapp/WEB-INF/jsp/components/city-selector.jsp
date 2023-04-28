<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!-- Params:
        - id: id of the select element
        - defaultText: text to be displayed when no option is selected
-->

<!-- Beans:
        - cities: list of cities
-->

<div>
    <select class="form-select form-select-sm" id="<c:out value="${param.id}"/>" name="<c:out value="${param.id}"/>">
        <option value="" selected><c:out value="${param.defaultText}"/></option>
        <jsp:useBean id="cities" scope="request" type="java.util.List"/>
        <c:forEach items="${cities}" var="city">
            <option value="<c:out value="${city.id}"/>"><c:out value="${city.name}"/></option>
        </c:forEach>
    </select>
</div>

