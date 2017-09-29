<%-- 
    Document   : login
    Created on : Sep 19, 2017, 3:37:43 PM
    Author     : Neel Patel
--%>

<%@page import="com.articles_hub.service.UserService"%>
<%@page import="com.articles_hub.service.CommentService"%>
<%@page import="java.util.Map"%>
<%@page import="com.articles_hub.service.ArticleService"%>
<%@page import="com.articles_hub.database.beans.Article"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.articles_hub.api.model.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
        <title>All Articles</title>
        <%
            AdminDetail admin=(AdminDetail)request.getSession().getAttribute("user");
            if(admin==null){
                response.sendRedirect("../login.jsp");
                return;
            }
            long articleId=0;
        %>
        
    </head>
    <body class="w3-light-grey">
        <%
            UserService userService= UserService.getUserService();
            String query=request.getQueryString();
            CommentDetail comments[];
            try{
                Map<String,String[]> parm=HttpUtils.parseQueryString(query);
                String userName=parm.get("username").length>0?parm.get("username")[0]:"";
                comments = userService.getAllComments(userName);
            }catch(Exception e){
                return;
            }
            if(comments==null)
                return;
        %>
        <table class="w3-table-all">
            <tr class="w3-dark-grey">
                <th>Comment Id</th>
                <th>Article Id</th>
                <th>Username</th>
                <th>Date</th>
                <th>Time</th>
            </tr>
            <%
                for(CommentDetail comment:comments){
            %>
            <tr>
                <td><%=comment.getCommentId()%></td>
                <td><%=comment.getArticleId()%></td>
                <td><%=comment.getUserName()%></td>
                <td><%=comment.getDate()%></td>
                <td><%=comment.getTime()%></td>
            </tr>
            <%
                }
            %>
        </table>
    </body>
</html>
