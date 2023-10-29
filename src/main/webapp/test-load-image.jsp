<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>Input path</h2>
<form method="post" action="test-load-image">
    <input type="text" name="appendPath" id="imgPath">
    <input type="submit" value="Submit">
</form>

<h2>Displaying Image</h2>
<c:if test="${not empty base64Image}">
    <img src="data:image/jpg;base64, ${base64Image}" />
</c:if>
<c:if test="${empty base64Image}">
    <p>Image not found</p>
</c:if>
</body>
</html>