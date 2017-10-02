<%-- 
    Document   : login
    Created on : Sep 19, 2017, 3:37:43 PM
    Author     : Neel Patel
--%>

<%@page import="com.articles_hub.service.AdminService"%>
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
            if(admin==null||!admin.getUserName().equals("superuser")){
                response.sendRedirect("../login.jsp");
                return;
            }
            long articleId=0;
        %>
        
    </head>
    <body class="w3-light-grey">
        <%
            AdminService adminService= AdminService.getAdminService();
            String query=request.getQueryString();
            AdminDetail user;
            try{
                Map<String,String[]> parm=HttpUtils.parseQueryString(query);
                String userName=parm.get("username").length>0?parm.get("username")[0]:"";
                user = adminService.getAdminDetail(userName);
            }catch(Exception e){
                return;
            }
            if(user==null)
                return;
        %>
    <div class="w3-container">
        <h5>UserName :- <i><%=user.getUserName()%></i></h5>
        <h5>First Name :- <i><%=user.getFirstName()%></i></h5>
        <h5>Last Name :- <i><%=user.getLastName()%></i></h5>
        <h5>Info :- <i><%=user.getInfo()%></i></h5>
        <hr>
    </div>
    </body>
</html>
