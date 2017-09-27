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
        <title>HeaderTemp</title>
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
                background-color: #333;
                font-size: 20px;
            }

            ul.titlebar li{
                float: right;
                border-left:1px solid #bbb;
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
        </style>
        <%
            AdminDetail admin=(AdminDetail)request.getSession().getAttribute("user");
        %>
    </head>
    <body>
        <ul class="titlebar">
            <%
                if(admin==null){
                    response.sendRedirect("login.jsp");
                    return;
                }
            %>
            <li style="color: #5ffc4b">Hi, <%=admin.getFirstName()%> <%=admin.getLastName()%></li>
            <li><a style="color: #f7ee56" href="Authentication?method=logout">Logout</a></li>
            
        </ul>
    </body>
</html>
