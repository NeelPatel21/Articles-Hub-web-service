<%-- 
    Document   : login
    Created on : Sep 19, 2017, 3:37:43 PM
    Author     : Neel Patel
--%>

<%@page import="com.articles_hub.service.UserService"%>
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
            UserDetail user;
            TagDetail tags[];
            ShortArticleDetail articles[];
            try{
                Map<String,String[]> parm=HttpUtils.parseQueryString(query);
                String userName=parm.get("username").length>0?parm.get("username")[0]:"";
                user = userService.getUserDetail(userName);
                tags = userService.getFavoriteTags(userName);
                articles = userService.getAllArticles(userName);
            }catch(Exception e){
                return;
            }
            if(user==null)
                return;
            String tag="";
            int index=0;
            int ntags=tags.length;
            for(TagDetail s:tags){
                index++;
                tag+=s.getTagName()+(index==ntags?"":", ");
            }
        %>
    <div class="w3-container">
        <h5>UserName :- <i><%=user.getUserName()%></i></h5>
        <h5>First Name :- <i><%=user.getFirstName()%></i></h5>
        <h5>Last Name :- <i><%=user.getLastName()%></i></h5>
        <h5>Email Id :- <i><%=user.getEmailId()%></i></h5>
        <h5>Info :- <i><%=user.getInfo()%></i></h5>
        <h5>Favorite Tags :- <i><%=tag%></i></h5><br>
        <h4>List Articles Written by <%=user.getUserName()%></h4>
        <table class="w3-table-all">
            <tr class="w3-dark-grey">
                <th>Article Id</th>
                <th>Title</th>
                <th>Author</th>
                <th>Publish Date</th>
            </tr>
            <%
                for(ShortArticleDetail article:articles){
            %>
            <tr>
                <td><%=article.getArticleId()%></td>
                <td><%=article.getTitle()%></td>
                <td><%=article.getAuthor()%></td>
                <td><%=article.getDate()%></td>
            </tr>
            <%
                }
            %>
        </table>
        <hr>
    </div>
    </body>
</html>
