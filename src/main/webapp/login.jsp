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
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <title>Login</title>
        <%
            String message=(String)session.getAttribute("message");
            if(message!=null)
                out.print("<script>"+
                        "alert('"+message+"');"+
                        "</script>");
            session.removeAttribute("message");
        %>
        <style>
            div.loginbox {
                background-color: black;
                color: white;
                /*margin: 20px 0 20px 0;*/
                padding: 30px;
/*                align-self: center;
                align-content: center;*/
                /*background-attachment: fixed;*/
                position: absolute;
                width: fit-content;
                height: fit-content;
                z-index: 15;
                top: 50%;
                left: 50%;
                margin: -200px 0 0 -250px;
                /*font-size: 25px;*/
            }
            table, th, td {
                border: 10px solid black;
                padding: 5px;
            }
        </style>
    </head>
    <body class="w3-light-grey">
        <center>
            <div class="loginbox">
                <form action="./Authentication" method="post">
                    <table >
                        <tr>
                            <td>user name </td>
                            <td><input value="" name="userName"></td>
                        </tr>
                        <tr>
                            <td>password </td> 
                            <td><input type="password" value="" name="passwd"></td>
                        </tr>
                        <tr>
                            <td><input type="submit"></td>
                        </tr>
                    </table>
                </form>
            </div>
        </center>
    </body>
</html>
