<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Image Details</title>
</head>
<body>
    <h2>Image Details</h2>
    <img alt="my_image" src="<c:url value="/image/${image.imageId}"/>">





    <h2>${image.imageId}</h2>
</body>
</html>
