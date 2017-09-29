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
            ArticleDetail article;
            try{
                Map<String,String[]> parm=HttpUtils.parseQueryString(query);
                String idParam=parm.get("articleid").length>0?parm.get("articleid")[0]:"";
                articleId=Integer.parseInt(idParam);
                article = articleService.getArticleDetail(articleId);
            }catch(Exception e){
                return;
            }
            if(article==null)
                return;
            String tags="";
            int index=0;
            int ntags=article.getTag().size();
            for(String tag:article.getTag()){
                index++;
                tags+=tag+(index==ntags?"":", ");
            }
        %>
    <div class="w3-container">
        <h5>Title:- <i><%=article.getTitle()%></i></h5>
        <h5>ID:- <i><%=article.getArticleId()%></i></h5>
        <h5>Author:- <i><%=article.getAuthor()%></i></h5>
        <h5>Date:- <i><%=article.getDate()%></i></h5>
        <button class="w3-button w3-red"id="b_remove">Permanently Remove</button>
        <hr>
    </div>
    <script>
        window.onload = (function(){
            document.getElementById("b_remove").addEventListener('click',
                function (){
                    var xhttp = new XMLHttpRequest();
                    xhttp.open('get','../ArticleRemove?articleid=<%=article.getArticleId()%>',true);
                    xhttp.send();
                    xhttp.onreadystatechange = function() {
                        if (this.readyState === 4){
                            if(this.status === 200){
                                alert("article removed successfully");
                            }else{
                                alert("error in article remove");
                            }
                        }
                    };
                }
            );
        });
    </script>
    </body>
</html>
