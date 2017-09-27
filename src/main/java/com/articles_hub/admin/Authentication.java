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
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpUtils;

/**
 *
 * @author Neel Patel
 */
public class Authentication extends HttpServlet {
    private static final String LOGIN_SUCCESS_URL = "/AdminHome.jsp";
    private static final String LOGIN_FAIL_URL = "/login.jsp";
    private static final String LOGIN_URL = "/login.jsp";
    private static final String QUERY_VAR = "method";
    private static final String QUERY_LOGIN = "login";
    private static final String QUERY_LOGOUT = "logout";
    private static final String TOKEN = "token";
    private static final String MESSAGE = "message";
    private static final String USER_OBJ = "user";
    private static final AdminService adminService = AdminService.getAdminService();
    private static final AuthenticationService authService = 
              AuthenticationService.getAuthenticationService();   

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
              throws ServletException, IOException {
        Map<String,String[]> parm=HttpUtils.parseQueryString(req.getQueryString());
        String meth=parm.get(QUERY_VAR).length>0?parm.get(QUERY_VAR)[0]:"";
        if(meth.equalsIgnoreCase(QUERY_LOGOUT)){
            doDelete(req, resp);
        }else if(meth.equalsIgnoreCase(QUERY_LOGIN)){
            doPost(req, resp);
        }
    }
    
    
    
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
              throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String passwd = request.getParameter("passwd");
        if(authenticate(userName, passwd, request.getSession())){
            //TODO 
            setAdminDetail(userName, request.getSession());
            response.sendRedirect(LOGIN_SUCCESS_URL);
        }else{
            //TODO
            request.getSession().setAttribute(MESSAGE, "incorrect Username or password");
            response.sendRedirect(LOGIN_FAIL_URL);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String token = (String) session.getAttribute(TOKEN);
        authService.userLogout(token);
        session.removeAttribute(TOKEN);
        session.removeAttribute(USER_OBJ);
        session.setAttribute(MESSAGE, "logout successful");
        resp.sendRedirect(LOGIN_URL);
    }
    
    private boolean authenticate(String userName,String passwd, HttpSession session){
        // TODO implement authentication.
        String token=authService.userLogin(userName, passwd);
        if(token==null||token.trim().equals(""))
            return false;
        session.setAttribute(TOKEN, token);
        return true;
    }
    
    private void setAdminDetail(String userName, HttpSession session){
        AdminDetail admin=adminService.getAdminDetail(userName);
        if(admin==null)
            return;
        session.setAttribute(USER_OBJ, admin);
    }
}
