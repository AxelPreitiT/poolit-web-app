<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <h1>Nombre: ${user.username}</h1>
  <h1>PASSWORD: ${user.password}</h1>
  <h1>CUMPLE: ${user.birthdate}</h1>
  <h1>CIUDAD: ${city.name}</h1>
  <h1>APELLIDO: ${user.surname}</h1>
  <h1>EMAIL: ${user.email}</h1>
  <h1>USER ID: ${user.userId}</h1>
  <h1>CELULAR: ${user.phone}</h1>
  <form:form method = "POST" action = "/profile">
    <input type = "submit" value = "Redirect Page"/>
  </form:form>
</body>
</html>
