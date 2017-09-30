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
import com.articles_hub.service.TagService;
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
public class TagRemove extends HttpServlet {
    private static final String LOGIN_URL = "./login.jsp";
    private static final String QUERY_ID = "tagname";
    private static final String USER_OBJ = "user";
    private static final AdminService adminService = AdminService.getAdminService();
    private static final TagService tagService = 
              TagService.getTagService();   

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDelete(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            HttpSession session = req.getSession();
            if(!isLoggedIn(session))
                resp.sendRedirect(LOGIN_URL);
            Map<String,String[]> parm=HttpUtils.parseQueryString(req.getQueryString());
            String tagName=parm.get(QUERY_ID).length>0?parm.get(QUERY_ID)[0]:"";
            if(removeTag(tagName))
                resp.setStatus(HttpStatus.OK_200);
            else
                resp.setStatus(HttpStatus.BAD_REQUEST_400);
        }catch(Exception e){
            resp.setStatus(HttpStatus.BAD_REQUEST_400);
        }
    }
    
    private boolean isLoggedIn(HttpSession session){
        // TODO implement authentication.
        AdminDetail admin=(AdminDetail)session.getAttribute(USER_OBJ);
        if(admin!=null)
            return true;
        return false;
    }
    
    private boolean removeTag(String tagName){
        return tagService.removeTag(tagName);
    }
}
