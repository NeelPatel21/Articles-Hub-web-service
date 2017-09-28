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
        <title>Article</title>
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
                height: 80%;
                /*padding-left: 20px;*/
            }
            /* Change the link color on hover */
        </style>
        <%
            AdminDetail admin=(AdminDetail)request.getSession().getAttribute("user");
            if(admin==null){
                response.sendRedirect("login.jsp");
                return;
            }
        %>
    </head>
    <body class="w3-light-grey">  
        <div class="menu">
            Id:- <input type="number" id="articleid">
            <button id="b_show">show</button>
        </div><br>
        <div class="frame-window">
            <iframe height="100%" width="100%" style="border:none" id="f_window" name="frame"></iframe>
        </div>
        <script type="text/javascript">
            window.onload = (function(){
//                alert("clicked");
//                alert(document.getElementById("b_show"));
//                alert(document.getElementById("articleid"));
//                alert(document.getElementById("f_window"));
//                var w=document.getElementById('f_window');
//                w.src='./ArticleView.jsp?articleid='+36;
                document.getElementById("b_show").addEventListener('click',
                    function (){
//                        alert("clicked new");
                        var id=document.getElementById('articleid').value;
                        var w=document.getElementById('f_window');
                        w.src='./ArticleView.jsp?articleid='+id;
                    }
                );
            });
        </script>
    </body>
</html>
