<%-- 
    Document   : login
    Created on : Sep 19, 2017, 3:37:43 PM
    Author     : Neel Patel
--%>

<%@page import="com.articles_hub.service.AdminService"%>
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
                if(userName.equals("superuser"))
                    return;
                user = adminService.getAdminDetail(userName);
            }catch(Exception e){
                return;
            }
            if(user==null)
                return;
            
        %>
    <div class="w3-container">
        <h5>User name:- <i><%=user.getUserName()%></i></h5>
        <h5>First name:- <i><%=user.getFirstName()%></i></h5>
        <h5>Last name:- <i><%=user.getLastName()%></i></h5>
        <button class="w3-button w3-red"id="b_remove">Permanently Remove</button>
        <hr>
    </div>
    <script>
        window.onload = (function(){
            document.getElementById("b_remove").addEventListener('click',
                function (){
                    var xhttp = new XMLHttpRequest();
                    xhttp.open('get','../AdminRemove?username=<%=user.getUserName()%>',true);
                    xhttp.send();
                    xhttp.onreadystatechange = function() {
                        if (this.readyState === 4){
                            if(this.status === 200){
                                alert("admin removed successfully");
                            }else{
                                alert("error in admin remove");
                            }
                        }
                    };
                }
            );
        });
    </script>
    </body>
</html>
