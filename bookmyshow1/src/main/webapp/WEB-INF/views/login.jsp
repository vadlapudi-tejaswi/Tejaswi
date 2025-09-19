<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="login" method="POST">
    <label for="username">Username:</label>
    <input type="text" name="username" required/><br/>
    <label for="password">Password:</label>
    <input type="password" name="password" required/><br/>
    <label for="email">email:</label>
    <input type="email" name="email" required/><br/>
    
    <input type="submit" value="Login"/>
</form>

</body>
</html>