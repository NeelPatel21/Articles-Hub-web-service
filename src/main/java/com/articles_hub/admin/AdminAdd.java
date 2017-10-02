/*
 * The MIT License
 *
 * Copyright 2017 Neel Patel.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.articles_hub.admin;

import com.articles_hub.api.model.AdminDetail;
import com.articles_hub.service.AdminService;
import com.articles_hub.service.AuthenticationService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.eclipse.jetty.http.HttpStatus;

/**
 *
 * @author Neel Patel
 */
public class AdminAdd extends HttpServlet {
    private static final String LOGIN_URL = "./login.jsp";
    private static final String QUERY_ID = "name";
    private static final String PARAM_PASS = "pass";
    private static final String PARAM_FIRST = "first";
    private static final String PARAM_LAST = "last";
    private static final String PARAM_INFO = "info";
    private static final String USER_OBJ = "user";
    private static final AdminService adminService = AdminService.getAdminService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try{
            if(!isLoggedIn(session)){
                resp.sendRedirect(LOGIN_URL);
                return;
            }
            if(addAdmin(req, resp)){
                resp.setStatus(HttpStatus.CREATED_201);
                resp.getOutputStream().println("admin is added successfully");
                return;
            }else{
                resp.setStatus(HttpStatus.BAD_REQUEST_400);
                resp.getOutputStream().println("admin is not added");
                return;
            }
        }catch(Exception e){
        }
        resp.setStatus(HttpStatus.BAD_REQUEST_400);
    }
    
    private boolean isLoggedIn(HttpSession session){
        // TODO implement authentication.
        AdminDetail admin=(AdminDetail)session.getAttribute(USER_OBJ);
        if(admin!=null && admin.getUserName().equals("superuser"))
            return true;
        return false;
    }
    
    private boolean addAdmin(HttpServletRequest req, HttpServletResponse resp){
        try{
            String userName=req.getParameter(QUERY_ID);
            String pass=req.getParameter(PARAM_PASS);
            String first=req.getParameter(PARAM_FIRST);
            String last=req.getParameter(PARAM_LAST);
            String info=req.getParameter(PARAM_INFO);
            AdminDetail adminDetail = new AdminDetail();
            adminDetail.setUserName(userName);
            adminDetail.setFirstName(first);
            adminDetail.setLastName(last);
            adminDetail.setPass(pass);
            adminDetail.setInfo(info);
            return adminService.addAdmin(adminDetail);
        }catch(Exception e){}
        return false;
    }
    
//    private boolean createTag(String tagName, String status){
//        try{
//            TagDetail tag = new TagDetail();
//            tag.setTagName(tagName);
//            return tagService.addTag(tag, TagStatus.valueOf(status));
//        }catch(Exception e){}
//        return false;
//    }
}
