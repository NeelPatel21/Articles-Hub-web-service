<%-- 
    Document   : login
    Created on : Sep 19, 2017, 3:37:43 PM
    Author     : Neel Patel
--%>

<%@page import="com.articles_hub.database.beans.Comment"%>
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
        <title>all Articles</title>
        <%
            AdminDetail admin=(AdminDetail)request.getSession().getAttribute("user");
            if(admin==null){
                response.sendRedirect("../login.jsp");
                return;
            }
            int SIZE=10;
        %>
        
    </head>
    <body class="w3-light-grey">
        <%
            CommentService commentService= CommentService.getCommentService();
            int tabPage=0;
            String query=request.getQueryString();
            try{
                Map<String,String[]> parm=HttpUtils.parseQueryString(query);
                String pageParam=parm.get("page").length>0?parm.get("page")[0]:"";
                tabPage=Integer.parseInt(pageParam);
            }catch(Exception e){}
            Comment comments[]=commentService.getAllComment(tabPage*SIZE, SIZE);
        %>
    <center>
        <div class="w3-bar">
            <a href="<%="./CommentListAll.jsp?page="+0%>" class="w3-button w3-black">First</a>
            <a href="<%=tabPage==0?"":"./CommentListAll.jsp?page="+(tabPage-1)%>" class="w3-button <%=tabPage>0?"":"w3-disabled"%>">&laquo;</a>
            <a href="#" class="w3-button"><%=(tabPage+1)%></a>
            <a href="<%=comments.length!=SIZE?"":"./CommentListAll.jsp?page="+(tabPage+1)%>" class="w3-button <%=comments.length==SIZE?"":"w3-disabled"%>">&raquo;</a>
            <a href="<%="./CommentListAll.jsp?page="+(commentService.getAllCount()/SIZE)%>" class="w3-button w3-black">Last</a>
        </div>
            
        <table class="w3-table-all">
            <tr class="w3-dark-grey">
                <th>Comment Id</th>
                <th>Article Id</th>
                <th>Username</th>
                <th>Date</th>
                <th>Time</th>
            </tr>
            <%
                for(Comment comment:comments){
            %>
            <tr>
                <td><%=comment.getCommentId()%></td>
                <td><%=comment.getArticle().getArticleId()%></td>
                <td><%=comment.getAuthor().getUserName()%></td>
                <td><%=comment.getDate()%></td>
                <td><%=comment.getTime()%></td>
            </tr>
            <%
                }
            %>
        </table>
        
    </center>
    </body>
</html>
