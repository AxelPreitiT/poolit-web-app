<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- Params:
        - id: id of the select element
        - defaultText: text to be displayed when no option is selected
-->

<!-- Beans:
        - cities: list of cities
-->

<jsp:useBean id="cities" scope="request" type="java.util.List"/>

<div>
    <form:select path="${param.id}" cssClass="form-select form-select-sm">
        <form:option value="-1" label="${param.defaultText}"/>
        <form:options items="${cities}" itemValue="id" itemLabel="name"/>
    </form:select>
</div>

