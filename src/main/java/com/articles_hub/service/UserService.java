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

import com.articles_hub.database.DataBase;
import com.articles_hub.database.beans.Article;
import com.articles_hub.database.beans.UserProfile;
import com.articles_hub.model.CommentDetail;
import com.articles_hub.model.ShortArticleDetail;
import com.articles_hub.model.UserDetail;
import com.articles_hub.model.Util;
import java.util.List;
import javax.persistence.FlushModeType;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
//import org.hibernate.resource.transaction.spi.TransactionStatus;
//import org.hibernate.query.Query;

/**
 *
 * @author Neel Patel
 */
public class UserService {
    
    private static UserService obj;
    
    public static UserService getUserService(){
        if(obj==null)
        synchronized(UserService.class){
            if(obj==null)
                obj=new UserService();
        }    
        return obj;
    }
    
    private DataBase db;
    
    private UserService(){
        db=DataBase.getDataBase();
//        System.err.println("user service initialized");
    }
    
    public UserDetail getUserDetail(String userName){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Query q= session.getNamedQuery("UserProfile.byName");
            q.setParameter("name", userName);
            List<UserProfile> list = q.list();
            if(list.size()==1)
                return Util.makeUserDetail(list.get(0));
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return null;
    }
    
    public boolean addUser(UserDetail user){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            if(user==null)
                return false;
//            System.out.println("check 1");
            session.setFlushMode(FlushModeType.AUTO);
            session.save(Util.makeUserProfile(user));
            session.flush();
            t.commit();
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
    
    public boolean updateUser(UserDetail user){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            if(user==null)
                return false;
//            System.out.println("check 1");
            session.setFlushMode(FlushModeType.AUTO);
            Query q= session.getNamedQuery("UserProfile.byName");
            q.setParameter("name", user.getUserName());
            List<UserProfile> list = q.list();
            if(list.size()!=1)
                return false;
            UserProfile userProfile=list.get(0);
            userProfile.setPass(user.getPass());
            userProfile.setInfo(user.getInfo());
            userProfile.setEmailId(user.getEmailId());
            session.flush();
            t.commit();
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
    
    public CommentDetail[] getAllComments(String userName){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Query q= session.getNamedQuery("UserProfile.byName");
            q.setParameter("name", userName);
            List<UserProfile> list = q.list();
            if(list.size()!=1)
                return null;
            UserProfile user = list.get(0);
            return user.getComments().stream()
                      .map(Util::makeCommentDetail)
                      .toArray(CommentDetail[]::new);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return null;
    }
    
    public ShortArticleDetail[] getAllArticles(String userName){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Query q= session.getNamedQuery("UserProfile.byName");
            q.setParameter("name", userName);
            List<UserProfile> list = q.list();
            if(list.size()!=1)
                return null;
            UserProfile user = list.get(0);
            return user.getArticles().stream()
                      .map(Util::makeShortArticleDetail)
                      .toArray(ShortArticleDetail[]::new);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return null;
    }
    
    public ShortArticleDetail[] getAllLikes(String userName){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Query q= session.getNamedQuery("UserProfile.byName");
            q.setParameter("name", userName);
            List<UserProfile> list = q.list();
            if(list.size()!=1)
                return null;
            UserProfile user = list.get(0);
            return user.getLikes().stream()
                      .map(Util::makeShortArticleDetail)
                      .toArray(ShortArticleDetail[]::new);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return null;
    }
    
    public boolean addLike(String userName, long articleId){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Query q= session.getNamedQuery("UserProfile.byName");
            q.setParameter("name", userName);
            List<UserProfile> list = q.list();
            if(list.size()!=1)
                return false;
            UserProfile user = list.get(0);
            Article article = (Article) session.get(Article.class, articleId);
            article.addLike(user);
            session.flush();
            t.commit();
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return false;
    }
    
    public boolean removeLike(String userName, long articleId){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Query q= session.getNamedQuery("UserProfile.byName");
            q.setParameter("name", userName);
            List<UserProfile> list = q.list();
            if(list.size()!=1)
                return false;
            UserProfile user = list.get(0);
            Article article = (Article) session.get(Article.class, articleId);
            article.removeLike(user);
            session.flush();
            t.commit();
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return false;
    }
    
}
