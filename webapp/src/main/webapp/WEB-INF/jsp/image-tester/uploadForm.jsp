<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Upload Form</title>
</head>
<body>
    <h2>Upload Form</h2>
    <form method="POST" enctype="multipart/form-data" action="/upload">
        <table>
            <tr>
                <td>Select file to upload:</td>
                <td><input type="file" name="file" /></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Upload" /></td>
            </tr>
        </table>
    </form>
</body>
</html>
