<%-- 
    Document   : login
    Created on : Sep 19, 2017, 3:37:43 PM
    Author     : Neel Patel
--%>

<%@page import="com.articles_hub.database.beans.Tag"%>
<%@page import="com.articles_hub.database.beans.Comment"%>
<%@page import="com.articles_hub.database.beans.UserProfile"%>
<%@page import="com.articles_hub.database.beans.Article"%>
<%@page import="com.articles_hub.api.model.CommentDetail"%>
<%@page import="com.articles_hub.service.CommentService"%>
<%@page import="com.articles_hub.service.CommentService"%>
<%@page import="com.articles_hub.api.model.ArticleDetail"%>
<%@page import="com.articles_hub.service.ArticleService"%>
<%@page import="com.articles_hub.api.model.UserDetail"%>
<%@page import="com.articles_hub.service.UserService"%>
<%@page import="com.articles_hub.api.model.TagDetail"%>
<%@page import="com.articles_hub.service.TagService"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <%
            String message=(String)session.getAttribute("message");
            if(message!=null)
                out.print("<script>"+
                        "alert('"+message+"');"+
                        "</script>");
        %>
        <style>
            div.loginbox {
                background-color: black;
                color: white;
                /*margin: 20px 0 20px 0;*/
                padding: 30px;
/*                align-self: center;
                align-content: center;*/
                /*background-attachment: fixed;*/
                position: absolute;
                width: fit-content;
                height: fit-content;
                z-index: 15;
                top: 50%;
                left: 50%;
                margin: -200px 0 0 -250px;
                font-size: 25px;
            }
            table, th, td {
                border: 10px solid black;
                padding: 5px;
            }
        </style>
    </head>
    <body>
        <%
            UserService userService= UserService.getUserService();
            UserProfile users[]=userService.getAllUserProfiles(2, 2);
        %>
        <table>
            <%
                for(UserProfile user:users){
            %>
            <tr>
                <td><%=user.getUserId()%></td>
                <td><%=user.getUserName()%></td>
                <td><%=user.getFirstName()%></td>
                <td><%=user.getLastName()%></td>
                <td><%=user.getEmailId()%></td>
            </tr>
            <%
                }
            %>
        </table>
        <%
            ArticleService articleService= ArticleService.getArticleService();
            Article articles[]=articleService.getAllArticle(2, 5);
        %>
        <table>
            <%
                for(Article article:articles){
            %>
            <tr>
                <td><%=article.getArticleId()%></td>
                <td><%=article.getTitle()%></td>
                <td><%=article.getAuthor().getUserName()%></td>
                <td><%=article.getPublishDate()%></td>
            </tr>
            <%
                }
            %>
        </table>
        <%
            CommentService commentService= CommentService.getCommentService();
            Comment comments[]=commentService.getAllComment(2, 2);
        %>
        <table>
            <%
                for(Comment comment:comments){
            %>
            <tr>
                <td><%=comment.getCommentId()%></td>
                <td><%=comment.getArticle().getArticleId()%></td>
                <td><%=comment.getAuthor().getUserName()%></td>
                <td><%=comment.getCommentBody()%></td>
                <td><%=comment.getTime()%></td>
                <td><%=comment.getDate()%></td>
            </tr>
            <%
                }
            %>
        </table>
        <%
            TagService tagService= TagService.getTagService();
            Tag tags[]=tagService.getAllTag(2, 10);
        %>
        <table>
            <%
                for(Tag tag:tags){
            %>
            <tr>
                <td><%=tag.getTagId()%></td>
                <td><%=tag.getTagName()%></td>
            </tr>
            <%
                }
            %>
        </table>
    </body>
</html>
