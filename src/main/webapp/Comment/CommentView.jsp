<%-- 
    Document   : login
    Created on : Sep 19, 2017, 3:37:43 PM
    Author     : Neel Patel
--%>

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
        <title>All Comments</title>
        <%
            AdminDetail admin=(AdminDetail)request.getSession().getAttribute("user");
            if(admin==null){
                response.sendRedirect("../login.jsp");
                return;
            }
            long commentId=0;
        %>
        
    </head>
    <body class="w3-light-grey">
        <%
            CommentService commentService= CommentService.getCommentService();
            String query=request.getQueryString();
            CommentDetail comment;
            try{
                Map<String,String[]> parm=HttpUtils.parseQueryString(query);
                String idParam=parm.get("commentid").length>0?parm.get("commentid")[0]:"";
                commentId=Integer.parseInt(idParam);
                comment = commentService.getCommentDetail(commentId);
            }catch(Exception e){
                return;
            }
            if(comment==null)
                return;
            
        %>
    <div class="w3-container">
        <h5>Comment id:- <i><%=comment.getCommentId()%></i></h5>
        <h5>Article id:- <i><%=comment.getArticleId()%></i></h5>
        <h5>Username :- <i><%=comment.getUserName()%></i></h5>
        <h5>Date :- <i><%=comment.getDate()%></i></h5>
        <h5>Time :- <i><%=comment.getTime()%></i></h5>
        <h5>Content :- <i><%=comment.getContent()%></i></h5>
        <hr>
    </div>
    </body>
</html>
