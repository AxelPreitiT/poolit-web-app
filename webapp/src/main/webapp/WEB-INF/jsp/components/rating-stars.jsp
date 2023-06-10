<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div>
    <c:forEach var="i" begin="1" end="${rating}">
        <i class="bi bi-star-fill secondary-color h4"></i>
    </c:forEach>
    <c:if test="${rating % 1 >= 0.5}">
        <i class="bi bi-star-half secondary-color h4"></i>
        <c:forEach var="i" begin="${rating + 2}" end="5">
            <i class="bi bi-star secondary-color h4"></i>
        </c:forEach>
    </c:if>
    <c:if test="${rating % 1 < 0.5}">
        <c:forEach var="i" begin="${rating + 1}" end="5">
            <i class="bi bi-star secondary-color h4"></i>
        </c:forEach>
    </c:if>
</div>
