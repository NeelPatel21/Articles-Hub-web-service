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
package com.articles_hub.service;

import com.articles_hub.api.model.AdminDetail;
import com.articles_hub.database.DataBase;
import com.articles_hub.api.model.Util;
import com.articles_hub.database.beans.AdminProfile;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.FlushModeType;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
//import org.hibernate.resource.transaction.spi.TransactionStatus;
//import org.hibernate.query.Query;

/**
 *
 * @author Neel Patel
 */
public class AdminService {

    private static final Logger LOG = Logger.getLogger(AdminService.class.getName());
    
    private static AdminService obj;
    
    public static AdminService getAdminService(){
        if(obj==null)
        synchronized(AdminService.class){
            if(obj==null)
                obj=new AdminService();
        }    
        return obj;
    }
    
    private DataBase db;
    
    private AdminService(){
        db=DataBase.getDataBase();
//        System.err.println("user service initialized");
    }
    
    public AdminDetail getAdminDetail(String userName){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Query q= session.getNamedQuery("AdminProfile.byName");
            q.setParameter("name", userName);
            List<AdminProfile> list = q.list();
            if(list.size()==1){
                LOG.info("AdminService, getAdminDetail :- "+
                          " userName :- "+userName);
                return Util.makeAdminDetail(list.get(0));
            }else if(list.size()>1){
                LOG.warning("AdminService, getAdminDetail :- "+
                          "multiple AdminProfile found, userName :- "+userName);
            }else{
                LOG.warning("AdminService, getAdminDetail :- "+
                          "AdminProfile not found, userName :- "+userName);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return null;
    }
    
    public boolean addAdmin(AdminDetail admin){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            if(admin==null){
                LOG.warning("AdminService, addAdmin :- "+
                          "null reference admin");
                return false;
            }
//            System.out.println("check 1");
            session.setFlushMode(FlushModeType.AUTO);
            session.save(Util.makeAdminProfile(admin));
            session.flush();
            t.commit();
            LOG.info("AdminService, addAdmin :- "+
                      "userName :- "+admin.getUserName());
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null && t.isActive())
                t.rollback();
//            if(session!=null)
//                session.flush();
        }
        return false;
    }
    
    public boolean updateAdmin(AdminDetail admin){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            if(admin==null){
                LOG.warning("AdminService, updateAdmin :- "+
                          "null reference admin");
                return false;
            }
            session.setFlushMode(FlushModeType.AUTO);
            Query q= session.getNamedQuery("AdminProfile.byName");
            q.setParameter("name", admin.getUserName());
            List<AdminProfile> list = q.list();
            if(list.size()!=1){
                LOG.warning("AdminService, updateAdmin :- "+
                            "multiple AdminProfile found, userName :- "
                            +admin.getUserName());
                return false;
            }
            AdminProfile adminProfile=list.get(0);
            adminProfile.setFirstName(admin.getFirstName());
            adminProfile.setLastName(admin.getLastName());
            adminProfile.setPass(admin.getPass());
            adminProfile.setInfo(admin.getInfo());
            adminProfile.setEmailId(admin.getEmailId());
            session.flush();
            t.commit();
            LOG.info("AdminService, updateAdmin :- "+
                        "AdminProfile updated, userName :- "
                        +admin.getUserName());
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null && t.isActive())
                t.rollback();
//            if(session!=null)
//                session.flush();
        }
        return false;
    }
    
}
