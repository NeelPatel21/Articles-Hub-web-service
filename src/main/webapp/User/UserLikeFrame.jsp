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
            if(admin==null){
                response.sendRedirect("../login.jsp");
                return;
            }
        %>
    </head>
    <body class="w3-light-grey">  
        <div class="menu">
            Username:- <input type="text" id="username">
            <button id="b_show">show</button>
        </div><br>
        <iframe class="frame-window" style="border:none" id="f_window" name="frame"></iframe>
        <script type="text/javascript">
            window.onload = (function(){
                document.getElementById("b_show").addEventListener('click',
                    function (){
                        var uname=document.getElementById('username').value;
                        var w=document.getElementById('f_window');
                        w.src='./UserLikeView.jsp?username='+uname;
                    }
                );
            });
        </script>
    </body>
</html>
