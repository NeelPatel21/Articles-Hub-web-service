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

            ul.titlebar {
                list-style-type: none;
                margin: 0;
                padding: 0;
                overflow: hidden;
                background-color: #222;
                font-size: 20px;
            }

            ul.titlebar li{
                float: right;
                /*border-left:1px solid #bbb;*/
                display: block;
                color: white;
                text-align: center;
                padding: 14px 16px;
                text-decoration: none;
                /*min-width: 140px;*/
            }

            ul.titlebar li:first-child {
                float: left;
                border-left: none;
                /*border-right:1px solid #bbb;*/
            }
            
            ul.titlebar li:first-child:hover {
                float: left;
                border-left: none;
                background-color: #222;
                /*border-right:1px solid #bbb;*/
            }

            ul.titlebar li:hover {
                background-color: #444;
                /*border-right: none;*/
            }

            ul.titlebar a{
                display: block;
                color: white;
                text-align: center;
                /*padding: 14px 16px;*/
                text-decoration: none;
            }

            /*            ul.titlebar a:hover:not(.active) {
                            background-color: #111;
                        }*/

            .active {
                background-color: #4CAF50;
            }
            
            /*body*/
            .row{
                height: 100%;
            }
            .row:after {
                content: "";
                display: table;
                clear: both;
                height: 100%;
                
            }
            
            .menu {
/*                float: left;
                width: 200px;
                height: 100%;*/
                /*padding: 15px;*/
                height: 100%;
                width: 20%;
                position: fixed;
                z-index: 1;
                /*top: 0;*/
                left: 0;
                background-color: #001aaf;
                color:white;
                overflow-x: hidden;
            }
            
            .frame-window {
                float: left;
                width: 80%;
                /*padding: 15px;*/
                position: fixed;
                margin-left: 20%;
                height: calc(100% - 53px);
                
                z-index: 1;
                /*padding-left: 20px;*/
                
            }
            
            ul.menubar {
                list-style-type: none;
                margin: 0;
                padding: 0;
                /*width: 200px;*/
                background-color: #001aaf;
            }

            ul.menubar li a {
                display: block;
                color: white;
                padding: 8px 16px;
                text-decoration: none;
            }

            /* Change the link color on hover */
            ul.menubar li a:hover {
                background-color: #0040ff;
                color: white;
            }

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
        <ul class="titlebar">
            <li style="color: #3eff3e">Hi, <%=admin.getFirstName()%> <%=admin.getLastName()%></li>
            <li><a style="color: #f7ee56" href="../Authentication?method=logout">Logout</a></li>
            <li><a href="../Tag/TagHome.jsp">Tag</a></li>
            <li><a href="../Comment/CommentHome.jsp">Comment</a></li>
            <li class="w3-green"><a href="../Article/ArticleHome.jsp">Article</a></li>
            <li><a href="../User/UserHome.jsp">User</a></li>
            <li><a href="../Home/AdminHome.jsp">Home</a></li>
        </ul>
           
        <div class="row">
            <div class="menu">
                <ul class="menubar">
                    <li><a href="./TagListAll.jsp" target="window">Show all Tags</a></li>
                    <li><a href="./TagViewFrame.jsp" target="window">View Tag</a></li>
                    <li><a href="./TagArticleFrame.jsp" target="window">View Articles by Tag</a></li>
                    <li><a href="./TagRemoveFrame.jsp" target="window">Remove Tag</a></li>
                </ul>
            </div>
                <iframe class="frame-window" src="./TagListAll.jsp" style="border:none" name="window"></iframe>
        </div>
    </body>
</html>
