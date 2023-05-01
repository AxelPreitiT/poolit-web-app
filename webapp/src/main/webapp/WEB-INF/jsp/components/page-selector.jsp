<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="trip" type="ar.edu.itba.paw.models.Trip" scope="request"/>
<div class="pagination-container">
    <ul class="pagination">
<%--        Previous button--%>
        <c:choose>
            <c:when test="${param.currentPage>=2}">
                <li class="page-item">
                    <a class="page-link" href="<c:url value="${param.baseUrl}">
                        <c:param name="page" value="${param.currentPage-1}"/>
                        </c:url>"
                       aria-label="Previous">
                        <span aria-hidden="true"><i class="bi bi-arrow-left"></i></span>
                    </a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item disabled">
                    <a class="page-link" href="#" aria-label="Previous">
                        <span aria-hidden="true"><i class="bi bi-arrow-left"></i></span>
                    </a>
                </li>
            </c:otherwise>
        </c:choose>
<%--    Page list--%>
        <c:forEach var="page" begin="${param.currentPage-1}" end="${param.currentPage+1}">
            <c:if test="${page>=1 && page<=param.totalPages}">
                <c:choose>
                    <c:when test="${param.currentPage == page}">
                        <li class="page-item active">
                            <a class="page-link" href="<c:url value="${param.baseUrl}">
                            <c:param name="page" value="${page}"/>
                            </c:url>"><c:out value="${page}"/>
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item">
                            <a class="page-link" href="<c:url value="${param.baseUrl}">
                            <c:param name="page" value="${page}"/>
                            </c:url>"><c:out value="${page}"/>
                        </a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </c:forEach>
<%--    Next button--%>
        <c:choose>
            <c:when test="${param.currentPage<param.totalPages}">
                <li class="page-item">
                    <a class="page-link" href="<c:url value="${param.baseUrl}">
                        <c:param name="page" value="${param.currentPage+1}"/>
                        </c:url>" aria-label="Next">
                        <span aria-hidden="true"><i class="bi bi-arrow-right-short"></i></span>
                    </a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item disabled">
                    <a class="page-link" href="#" aria-label="Next">
                        <span aria-hidden="true"><i class="bi bi-arrow-right-short"></i></span>
                    </a>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>
</div>
