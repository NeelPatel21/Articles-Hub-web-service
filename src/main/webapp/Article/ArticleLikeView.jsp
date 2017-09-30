<%-- 
    Document   : login
    Created on : Sep 19, 2017, 3:37:43 PM
    Author     : Neel Patel
--%>

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
            ArticleService articleService= ArticleService.getArticleService();
            String query=request.getQueryString();
            ShortUserDetail users[];
            try{
                Map<String,String[]> parm=HttpUtils.parseQueryString(query);
                String idParam=parm.get("articleid").length>0?parm.get("articleid")[0]:"";
                articleId=Long.parseLong(idParam);
                users = articleService.getAllLikes(articleId);
            }catch(Exception e){
                return;
            }
            if(users==null)
                return;
        %>
        <table class="w3-table-all">
            <tr class="w3-dark-grey">
                <th>Username</th>
                <th>First name</th>
                <th>Last name</th>
            </tr>
            <%
                for(ShortUserDetail user:users){
            %>
            <tr>
                <td><%=user.getUserName()%></td>
                <td><%=user.getFirstName()%></td>
                <td><%=user.getLastName()%></td>
            </tr>
            <%
                }
            %>
        </table>
    </body>
</html>
