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
import com.articles_hub.api.model.TagDetail;
import com.articles_hub.database.beans.TagStatus;
import com.articles_hub.service.AdminService;
import com.articles_hub.service.AuthenticationService;
import com.articles_hub.service.TagService;
import com.articles_hub.service.UserService;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUtils;
import org.eclipse.jetty.http.HttpStatus;

/**
 *
 * @author Neel Patel
 */
public class AdminUpdate extends HttpServlet {
    private static final String LOGIN_URL = "./login.jsp";
    private static final String HOME_URL = "./Home/AdminHome.jsp";
    private static final String QUERY_ID = "name";
    private static final String PARAM_PASS = "pass";
    private static final String PARAM_FIRST = "first";
    private static final String PARAM_LAST = "last";
    private static final String PARAM_INFO = "info";
    private static final String PARAM_PASS_OLD = "oldpass";
    private static final String PARAM_MESSAGE = "message";
    private String message = "";
    private static final String USER_OBJ = "user";
    private static final AdminService adminService = AdminService.getAdminService();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try{
            System.out.println(req.getParameterMap());
            String userName=req.getParameter(QUERY_ID);
            if(!isLoggedIn(session,userName)){
                resp.sendRedirect(LOGIN_URL);
                return;
            }
            if(userName!=null&&!userName.equals("")&&updateAdmin(req, resp, userName)){
                message="personal details updated successfully";
            }else{
                message="error in updating personal details";
                resp.setStatus(HttpStatus.BAD_REQUEST_400);
            }
        }catch(Exception e){
        }
        session.setAttribute(PARAM_MESSAGE,message);
//        resp.sendRedirect(HOME_URL);
    }
    
    private boolean isLoggedIn(HttpSession session, String userName){
        // TODO implement authentication.
        AdminDetail admin=(AdminDetail)session.getAttribute(USER_OBJ);
        if(admin!=null && admin.getUserName().equals(userName))
            return true;
        return false;
    }
    
    private boolean updateAdmin(HttpServletRequest req, HttpServletResponse resp, String userName){
        try{
            AdminDetail adminDetail=adminService.getAdminDetail(userName);
            String passNew=req.getParameter(PARAM_PASS);
            String passOld=req.getParameter(PARAM_PASS_OLD);
            if(passNew!=null&&!passNew.equals("")){
                if(passOld==null||passOld.equals("")){
                    message="old password required";
                    return false;
                }
                String token=AuthenticationService.getAuthenticationService()
                      .userLogin(adminDetail.getUserName(), passOld);
                if(token!=null&&!token.equals("")){
                    adminDetail.setPass(passNew);
                }else{
                    message="invalid old password";
                    return false;
                }
            }
            String first=req.getParameter(PARAM_FIRST);
            String last=req.getParameter(PARAM_LAST);
            String info=req.getParameter(PARAM_INFO);
            adminDetail.setFirstName(first);
            adminDetail.setLastName(last);
            adminDetail.setInfo(info);
            System.out.println("name :- "+userName+", first :- "+first+", last :- "+last+", info :- "+info);
            System.out.println(req.getParameterMap());
            return adminService.updateAdmin(adminDetail);
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
