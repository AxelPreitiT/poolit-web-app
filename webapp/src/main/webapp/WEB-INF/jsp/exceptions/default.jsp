<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
    <title><spring:message code="errors.heading"/></title>
    <jsp:include page="/resources/external-resources.jsp"/>
    <jsp:include page="/WEB-INF/jsp/base/base.css.jsp"/>
    <link href="<c:url value="/resources/css/static/not-found.css"/>" rel="stylesheet">
</head>
<body class="background-bg-color">
<jsp:include page="/WEB-INF/jsp/components/navbar.jsp">
    <jsp:param name="showOnlyLogo" value="true"/>
</jsp:include>
<div class="container-bg-color main-container-style">
    <div class="title-container">
        <svg class="svg-style" viewBox="0 0 512 512"><path d="M410.023 21.232c-2.7.031-3.172.607-3.218.66-.047.054-.552.605-.198 3.282.355 2.677 1.76 6.855 4.356 11.627 5.191 9.544 14.921 21.467 27.59 32.441 12.668 10.975 25.856 18.905 36.043 22.682 5.093 1.888 9.43 2.683 12.13 2.652 2.7-.03 3.173-.606 3.22-.66.046-.054.549-.604.195-3.281-.355-2.677-1.76-6.855-4.356-11.627-5.19-9.544-14.921-21.467-27.59-32.442-12.668-10.974-25.858-18.904-36.045-22.681-5.093-1.889-9.426-2.684-12.127-2.653zM256 59C126.213 59 21 164.213 21 294c0 62.949 24.75 120.116 65.05 162.303C-5.246 505.069 189.163 486 256 486c66.665 0 253.486 27.16 198.918-54.678C483.806 380.45 491 357.122 491 294c0-64.833-26.256-123.532-68.713-166.049l-71.119 42.166 30.816-74.52C345.572 72.428 302.356 59 256 59zm156.018 11.07l-23.58 57.024 53.078-31.471-14.74-12.768-.008-.007-.008-.008-14.742-12.77zM256 141c84.393 0 153 68.607 153 153s-68.607 153-153 153-153-68.607-153-153 68.607-153 153-153zm0 18c-20.417 0-39.757 4.52-57.09 12.602C210.457 204.482 230.218 246 256 246c25.823 0 44.926-41.65 56.752-74.555C295.505 163.462 276.284 159 256 159zm98.752 42.88c-27.714 21.143-61.142 52.79-53.17 77.327 7.981 24.564 53.508 29.858 88.459 30.936.628-5.294.959-10.678.959-16.143 0-35.642-13.755-68.012-36.248-92.12zm-197.729.243C134.663 226.204 121 258.477 121 294c0 5.55.34 11.018.988 16.39 34.833-.825 80.381-6.793 88.344-31.3 7.974-24.542-25.68-55.553-53.309-76.967zm70.188 43.643a9 9 0 0 0-7.025 14.285 9 9 0 1 0 7.025-14.285zm57.578 0a9 9 0 1 0 7.025 14.285 9 9 0 0 0-7.025-14.285zM256 262c-17.673 0-32 14.327-32 32 0 17.673 14.327 32 32 32 17.673 0 32-14.327 32-32 0-17.673-14.327-32-32-32zm-46.297 38.037a9.001 9.001 0 0 0-8.432 11.781 9 9 0 0 0 11.34 5.778 9 9 0 0 0-2.908-17.559zm92.104.006a9 9 0 1 0 3.143.434 9 9 0 0 0-3.143-.434zm-91.391 27.715c-21.59.104-50.24 16.832-72.424 31.928 19.029 34.168 52.46 59.164 92.143 66.837 9.99-33.39 18.42-78.618-2.446-93.777-4.854-3.527-10.737-5.02-17.273-4.988zm91.016.02c-6.58 0-12.492 1.516-17.346 5.042-20.895 15.181-11.863 60.106-2.088 93.678 39.687-7.715 73.108-32.76 92.1-66.973-22.006-15.224-50.935-31.747-72.666-31.748zM256 333.58a9 9 0 1 0 0 18 9 9 0 0 0 0-18z"></path></svg>
        <h1 class="danger title"><spring:message code="errors.title"/></h1>
    </div>
    <h5 class="danger"><spring:message code="errors.subtitle"/></h5>
    <h5 class="danger"><spring:message code="${errorDescription}"/></h5>
    <h6><spring:message code="errors.recommendation"/> </h6>
    <div class="footer-container">
        <div class="error-code-container error-code">
            <p class="error-code-style"><spring:message code="${errorMessage}"/></p>
        </div>
        <a class="anchor-style" href="<c:url value="/"/>">
            <button class="btn primary-button button-style">
                <span class="light-text h5"><spring:message code="errors.button"/></span>
            </button>
        </a>
    </div>
</div>
</body>
</html>
