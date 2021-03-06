<%-- 
    Document   : login
    Created on : Sep 19, 2017, 3:37:43 PM
    Author     : Neel Patel
--%>

<%@page import="com.articles_hub.database.beans.TagStatus"%>
<%@page import="com.articles_hub.database.beans.Tag"%>
<%@page import="com.articles_hub.service.TagService"%>
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
        %>
        
    </head>
    <body class="w3-light-grey">
        <%
            TagService tagService= TagService.getTagService();
            String query=request.getQueryString();
            Tag tag;
            try{
                Map<String,String[]> parm=HttpUtils.parseQueryString(query);
                String tagName=parm.get("tagname").length>0?parm.get("tagname")[0]:"";
                tag = tagService.getTag(tagName);
            }catch(Exception e){
                return;
            }
            if(tag==null)
                return;
        %>
    <div class="w3-container">
        <h5>Tag Id:- <i><%=tag.getTagId()%></i></h5>
        <h5>Tag Name:- <i><%=tag.getTagName()%></i></h5>
        <h5>Status:- <i><%=tag.getTagStatus()%></i> 
            <div class="w3-dropdown-hover">
                <button class="w3-button">select</button>
                <div class="w3-dropdown-content w3-bar-block w3-card-4">
                    <%
                        for(TagStatus status:TagStatus.values()){
                    %>
                    <a href="javascript:<%=status.name()%>()" class="w3-bar-item w3-button"><%=status.name()%></a>
                    <%
                        }
                    %>
                </div>
            </div>    
        </h5>
        
        <hr>
    </div>
    <script>
        <%
            for(TagStatus status:TagStatus.values()){
        %>       
            function <%=status.name()%>(){
                var xhttp = new XMLHttpRequest();
                xhttp.open('get','../TagUpdate?tagname=<%=tag.getTagName()%>&status=<%=status.name()%>&mode=update',true);
                xhttp.send();
                xhttp.onreadystatechange = function() {
                    if (this.readyState === 4){
                        if(this.status === 200){
                            alert("tag status updated successfully");
                        }else{
                            alert("error in tag status update");
                        }
                        location.reload(true);
                    }
                };
            };
        <%
            }
        %>
    </script>
    </body>
</html>
