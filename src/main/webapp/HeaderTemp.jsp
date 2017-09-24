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
        <title>Login</title>
        <style>
            nav.headerbox {
                background-color: black;
                color: white;
                /*margin: 20px 0 20px 0;*/
                
/*                align-self: center;
                align-content: center;*/
                /*background-attachment: fixed;*/
                position: fixed;
                width: inherit;
                height: fit-content;
                font-size: 25px;
            }
            table.headerbox{
                border: 10px solid black;
                padding: 5px;
                width: 100%;
            }
            td.headerbox_left{
                border: 10px solid black;
                padding: 5px;
                text-align: left;
            }
            td.headerbox_right{
                border: 10px solid black;
                padding: 5px;
                text-align: right;
            }
            #logout{
                background-color: black;
                color: white;
                font-size: 25px;
            }
        </style>
        <%
            AdminDetail admin=(AdminDetail)request.getSession().getAttribute("user");
        %>
    </head>
    <body>
        <nav class="headerbox">
            <form action="/Authentication" method="delete">
                <table class="headerbox">
                    <tr>
                        <td class="headerbox_left">
                            Articles Hub
                        </td>
                        <td class="headerbox_right">
                            hi, <%=admin.getUserName()%><button type="submit" value="logout"/>
                        </td>
                    </tr>
                </table>
            </form>
        </nav>
    </body>
</html>
