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
        <title>all Articles</title>
        <%
            AdminDetail admin=(AdminDetail)request.getSession().getAttribute("user");
        %>
    </head>
    <body>
        <%
            ArticleService articleService= ArticleService.getArticleService();
            int tabPage=0;
            String query=request.getQueryString();
            try{
                Map<String,String[]> parm=HttpUtils.parseQueryString(query);
                String pageParam=parm.get("page").length>0?parm.get("page")[0]:"";
                tabPage=Integer.parseInt(pageParam);
            }catch(Exception e){}
            Article articles[]=articleService.getAllArticle(tabPage, 15);
        %>
    <center>
        <%
            if(tabPage>1)
                out.write("<a target=\"_self\" href=\"ArticleDisplay.jsp?page="+(tabPage-1)+"\">Previous</a>");
        %>    
        <%=(tabPage+1)%>
        <%
            if(articles.length==15)
                out.write("<a target=\"_self\" href=\"ArticleDisplay.jsp?page="+(tabPage+1)+"\">Next</a>");
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
            
    </center>
    </body>
</html>
