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
        <title>all Articles</title>
        <%
            AdminDetail admin=(AdminDetail)request.getSession().getAttribute("user");
            int SIZE=10;
        %>
        
    </head>
    <body class="w3-light-grey">
        <%
            ArticleService articleService= ArticleService.getArticleService();
            int tabPage=0;
            String query=request.getQueryString();
            try{
                Map<String,String[]> parm=HttpUtils.parseQueryString(query);
                String pageParam=parm.get("page").length>0?parm.get("page")[0]:"";
                tabPage=Integer.parseInt(pageParam);
            }catch(Exception e){}
            Article articles[]=articleService.getAllArticle(tabPage*SIZE, SIZE);
        %>
    <center>
        <%
            if(tabPage>1)
                out.write("<a class=\"w3-button w3-black\" target=\"_self\" href=\"ArticleDisplay.jsp?page="
                          +(tabPage-1)+"\">Previous</a>");
        %>    
        <div class="w3-bar">
            <a href="<%="ArticleDisplay.jsp?page="+0%>" class="w3-button w3-black">First</a>
            <a href="<%=tabPage==0?"":"ArticleDisplay.jsp?page="+(tabPage-1)%>" class="w3-button <%=tabPage>0?"":"w3-disabled"%>">&laquo;</a>
            <a href="#" class="w3-button"><%=(tabPage+1)%></a>
            <a href="<%=articles.length!=SIZE?"":"ArticleDisplay.jsp?page="+(tabPage+1)%>" class="w3-button <%=articles.length==SIZE?"":"w3-disabled"%>">&raquo;</a>
            <a href="#" class="w3-button">Last</a>
        </div>
            
        <table class="w3-table-all">
            <tr class="w3-dark-grey">
                <th>Article Id</th>
                <th>Title</th>
                <th>Author</th>
                <th>Publish Date</th>
            </tr>
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
        <pre>
            


        </pre>    
    </center>
    </body>
</html>
