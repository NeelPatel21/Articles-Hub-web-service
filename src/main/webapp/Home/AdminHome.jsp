<%-- 
    Document   : login
    Created on : Sep 19, 2017, 3:37:43 PM
    Author     : Neel Patel
--%>

<%@page import="com.articles_hub.service.TagService"%>
<%@page import="com.articles_hub.service.CommentService"%>
<%@page import="com.articles_hub.service.ArticleService"%>
<%@page import="com.articles_hub.service.UserService"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.articles_hub.api.model.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <title>Home</title>
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
            }

            ul.titlebar li:first-child {
                float: left;
                border-left: none;
                /*border-right:1px solid #bbb;*/
            }

            ul.titlebar li:last-child {
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
            
            .panel{
                width: calc(100%-40);
                height: calc(100% - 20px);
                margin-top: 20px;
                margin-left: 20px;
                margin-right: 20px;
            }
            
            .panel .status{
                float: left;
                width: calc(100% - 400px);
            }
            
            .panel .logdiv{
                float: left;
                margin-top: 20px;
                height: calc(100% - 20px);
                width: calc(100% - 400px);
            }
            
            .panel .logdiv #log{
                height: calc(100%);
            }
            
            .panel .profile{
                margin-top: 20px;
                float: right;
                width: 350px;
            }
        </style>
        <%
            AdminDetail admin=(AdminDetail)request.getSession().getAttribute("user");
            if(admin==null){
                response.sendRedirect("../login.jsp");
                return;
            }
            // Set standard HTTP/1.1 no-cache headers.
            response.setHeader("Cache-Control", "private, no-store, no-cache, must-revalidate");

            // Set standard HTTP/1.0 no-cache header.
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "Sat, 6 May 1995 12:00:00 GMT");
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Cache-Control","no-store"); //HTTP 1.1
        %>
    </head>
    <body class="w3-light-grey">
        <ul class="titlebar">
            <li style="color: #3eff3e">Hi, <%=admin.getFirstName()%> <%=admin.getLastName()%></li>
            <li><a style="color: #f7ee56" href="../Authentication?method=logout">Logout</a></li>
            <li><a href="../Tag/TagHome.jsp">Tag</a></li>
            <li><a href="../Comment/CommentHome.jsp">Comment</a></li>
            <li><a href="../Article/ArticleHome.jsp">Article</a></li>
            <li><a href="../User/UserHome.jsp">User</a></li>
            <li class="w3-green"><a href="../Home/AdminHome.jsp" class="w3-green">Home</a></li>
        </ul>
        <div class="panel w3-container">
            <%
                UserService userService=UserService.getUserService();
                ArticleService articleService=ArticleService.getArticleService();
                CommentService commentService=CommentService.getCommentService();
                TagService tagService=TagService.getTagService();
            %>
            <div class="status w3-card-4">
                <header class="w3-container w3-indigo">
                    <h3>Statistics</h3>
                </header>
                <h5> 
                <table class="w3-table w3-striped">
                    <tr>
                        <td>Users</td>
                        <td><i><%=userService.getAllCount()%></i></td>
                        <td>Articles</td>
                        <td><i><%=articleService.getAllCount()%></i></td>
                    </tr>
<!--                    <tr>
                    </tr>-->
                    <tr>
                        <td>Comments</td>
                        <td><i><%=commentService.getAllCount()%></i></td>
                        <td>Tags</td>
                        <td><i><%=tagService.getAllCount()%></i></td>
                    </tr>
                    <tr>
                        <td>Tag Requests</td>
                        <td><i><%=tagService.getRequestCount()%></i></td>
                    </tr>
<!--                    <tr>
                    </tr>-->
                </table>
                </h5>
            </div>
            <div class="profile w3-card-4">
                <header class="w3-container w3-indigo">
                    <h3>Profile</h3>
                </header>
                <form class="w3-container" action="../AdminUpdate" method="post">
                    UserName :- <i><%=admin.getUserName()%></i><br><br>
                    <input name="name" value="<%=admin.getUserName()%>" hidden="true">
                    First Name :- <input class="w3-input w3-light-grey" name="first" type="text" value="<%=admin.getFirstName()%>">
                    Last Name :- <input class="w3-input w3-light-grey" name="last" type="text" value="<%=admin.getLastName()%>">
                    Info :- <input class="w3-input w3-light-grey" name="info" type="text" value="<%=admin.getInfo()%>">
                    Old Password :- <input class="w3-input w3-light-grey" name="oldpass" type="password">
                    New Password :- <input class="w3-input w3-light-grey" name="pass" type="password">
                    <input type="submit" value="update" class="w3-button w3-dark-grey"><br>
                    <i>if you don't want to change password, leave password & old password fields blank</i>
                </form>
            </div>
                        
            <div class="log-con">
                <div class="w3-card-4 logdiv">
                    <header class="w3-container w3-indigo">
                        <h3>System Logs</h3>
                    </header>
                    <div class="w3-container w3-black w3-text-lime" id="logwin" style="height:250px; overflow-y:auto">
                        <pre id="log"></pre>
                    </div>
                    <label><input type="checkbox" id="scroll" checked="true">Auto Scroll</label>
                </div>
            </div>
        </div>
        <script>
            function logupdate(){
                var xhttp = new XMLHttpRequest();
                xhttp.open('get','../LogUpdate',true);
                xhttp.send();
                xhttp.onreadystatechange = function() {
                    if (this.readyState === 4){
                        document.getElementById("log").innerHTML = this.responseText;
                        if(document.getElementById("scroll").checked===true){
                            var element = document.getElementById("logwin");
                            element.scrollTop = element.scrollHeight;
                        }
                    }
                };
            }
            setInterval(logupdate,1000);
        </script>
    </body>
</html>
