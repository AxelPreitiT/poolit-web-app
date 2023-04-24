<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link href="<c:url value="/resources/css/components/trip-card-list-pagination.css"/>" rel="stylesheet" type="text/css"/>

<div id="result-pagination">
    <nav>
        <ul class="pagination pagination-sm justify-content-end">
            <li class="page-item disabled">
                <a class="page-link button-color" href="#">
                    <i class="light-text bi bi-caret-left-fill"></i>
                </a>
            </li>
            <li class="page-item">
                <a class="page-link button-color" href="#">
                    <span class="light-text">1</span>
                </a>
            </li>
            <li class="page-item">
                <a class="page-link button-color" href="#">
                    <span class="light-text">2</span>
                </a>
            </li>
            <li class="page-item">
                <a class="page-link button-color" href="#">
                    <span class="light-text">3</span>
                </a>
            </li>
            <li class="page-item">
                <a class="page-link button-color" href="#">
                    <i class="light-text bi bi-caret-right-fill"></i>
                </a>
            </li>
        </ul>
    </nav>
</div>
