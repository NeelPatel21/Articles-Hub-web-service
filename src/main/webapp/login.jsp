<%-- 
    Document   : login
    Created on : Sep 19, 2017, 3:37:43 PM
    Author     : Neel Patel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <form action="/Authentication" method="post">
            user name :- <input value="" name="userName">
            password :- <input value="" name="passwd">
            <input type="submit">
            <h1>Hello World!</h1>
        </form>
    </body>
</html>
