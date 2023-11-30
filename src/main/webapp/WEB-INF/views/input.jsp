<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Information Form</title>
</head>
<body>
<h2>User Information Form</h2>
<form action="info" method="post">
    <label for="username">Name:</label><br>
    <input type="text" id="username" name="username" required><br><br>

    <input type="submit" value="Submit">
</form>
</body>
</html>