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
import com.articles_hub.service.LogService;
import com.articles_hub.service.LogService.Logs;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Neel Patel
 */
public class LogUpdate extends HttpServlet {
    private static final String USER_OBJ = "user";
    private static final String LOG_OBJ = "log";
    private static final String CACHE_OBJ = "cache";
    private static final int CACHE_SIZE = 100;
    private static final AdminService adminService = AdminService.getAdminService();
    private static final LogService logService = LogService.getLogService();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try{
            if(!isLoggedIn(session)){
                return;
            }
            Logs sc = (Logs)session.getAttribute(LOG_OBJ);
            if(sc==null||sc.isClosed()){
                sc=logService.getListner();
                session.setAttribute(LOG_OBJ, sc);
            }
            List<String> cache=(List<String>)session.getAttribute(CACHE_OBJ);
            if(cache==null){
                cache=new ArrayList<>();
                session.setAttribute(CACHE_OBJ, cache);
            }
            cache.addAll(sc.readAll());
            cache.forEach(log->{
                try {
                    resp.getOutputStream().println(log);
                } catch(IOException ex) {
                }
            });
            if(cache.size()>CACHE_SIZE)
                for(int i=0;i<10&&i<cache.size();i++)
                    cache.remove(i);
        }catch(Exception e){
        }
//        resp.sendRedirect(HOME_URL);
    }
    
    private boolean isLoggedIn(HttpSession session){
        // TODO implement authentication.
        AdminDetail admin=(AdminDetail)session.getAttribute(USER_OBJ);
        if(admin!=null)
            return true;
        return false;
    }
}    