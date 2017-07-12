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
import com.articles_hub.database.beans.Comment;
import com.articles_hub.database.beans.UserProfile;
import com.articles_hub.model.CommentDetail;
import com.articles_hub.model.Util;
import java.util.List;
import javax.persistence.FlushModeType;
import org.hibernate.Query;
//import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
//import org.hibernate.resource.transaction.spi.TransactionStatus;
//import org.hibernate.query.Query;

/**
 *
 * @author Neel Patel
 */
public class CommentService {
    
    private static CommentService obj=new CommentService();
    
    public static CommentService getCommentService(){
        return obj;
    }
    
    private DataBase db;
    
    private CommentService(){
        db=DataBase.getDataBase();
        System.err.println("comment service initialized");
    }
    
    public CommentDetail getCommentDetail(long commentId){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Comment comment=(Comment) session.get(Comment.class, commentId);
            return Util.makeCommentDetail(comment);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive())
                t.commit();
        }
        return null;
    }
    
    public boolean addComment(CommentDetail commentDetail){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            if(commentDetail==null)
                return false;
            Comment comment=Util.makeComment(commentDetail);
            session.setFlushMode(FlushModeType.AUTO);
            Query q= session.getNamedQuery("UserProfile.byName");
            q.setParameter("name", commentDetail.getUserName());
            List<UserProfile> list = q.list();
            if(list.size()!=1)
                return false;
            Article article=(Article) session.get(Article.class, commentDetail.getArticleId());
            if(list.get(0)==null||article==null)
                return false;
//            session.save(comment);
            article.addComment(comment);
            list.get(0).addComment(comment);
            session.flush();
            t.commit();
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
            t.rollback();
        }finally{
            if(t!=null&&t.isActive())
                t.rollback();
        }
        return false;
    }
   
}
