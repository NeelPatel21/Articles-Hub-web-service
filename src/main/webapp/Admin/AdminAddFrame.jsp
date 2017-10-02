<%-- 
    Document   : login
    Created on : Sep 19, 2017, 3:37:43 PM
    Author     : Neel Patel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.articles_hub.api.model.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <title>User</title>
        <style>
            * {
                box-sizing: border-box;
            }

            body {
                margin: 0;
            }

            /*body*/
            .menu {
/*                float: left;
                width: 200px;
                height: 100%;*/
                /*padding: 15px;*/
                padding: 8px 16px;
                width: 100%;
                position: fixed;
                top: 0;
                left: 0;
            }
            
            .frame-window {
                float: left;
                width: 100%;
                /*padding: 15px;*/
                position: fixed;
                margin-top: 20px;
                height: calc(100% - 50px);
                /*padding-left: 20px;*/
            }
            /* Change the link color on hover */
        </style>
        <%
            AdminDetail admin=(AdminDetail)request.getSession().getAttribute("user");
            if(admin==null||!admin.getUserName().equals("superuser")){
                response.sendRedirect("../login.jsp");
                return;
            }
        %>
    </head>
    <body class="w3-light-grey">  
        <div class="menu">
            <form action="../AdminAdd" class="w3-container" onsubmit="return validateForm()" method="post">
                Username:- <input class="w3-input w3-light-grey" type="text" id="username" name="name">
                Password:- <input class="w3-input w3-light-grey" type="text" id="passwd" name="pass">
                First Name:- <input class="w3-input w3-light-grey" type="text" name="first">
                Last Name:- <input class="w3-input w3-light-grey" type="text" name="last">
                Info :- <input class="w3-input w3-light-grey" type="text" name="info">
                <input class="w3-button w3-black" type="submit" value="Add">
            </form>
        </div><br>
        <script type="javascript">
            function validateForm() {
                var x = document..getElementById("username").value;
                var y = document..getElementById("passwd").value;
                if (x == "" || y == "") {
                    alert("UserName & Password required");
                    return false;
                }
                return true;
            }
        </script>
    </body>
</html>
