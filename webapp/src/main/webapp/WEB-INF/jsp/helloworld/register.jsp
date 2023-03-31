<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<body>
<%--Usamos esto y no solo ${} para evitar XSS --%>
<%--<h2>Register <c:out value="${username}" escapeXml="true"/> !</h2>--%>
<h2>Register</h2>

<c:url var="registerUrl" value="/register"/>
<form action="${registerUrl}" method="post">
    <div>
        <label>Email:
            <input type="text" name="email">
        </label>
    </div>
    <div>
        <label>Password:
            <input type="password" name="password">
        </label>
    </div>
    <div>
        <input type="submit" value="Let's go!">
    </div>
</form>
</body>
</html>
