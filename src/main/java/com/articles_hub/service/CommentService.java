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
import com.articles_hub.api.model.CommentDetail;
import com.articles_hub.api.model.Util;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.FlushModeType;
import org.hibernate.query.Query;
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

    private static final Logger LOG = Logger.getLogger(CommentService.class.getName());
    
    private static CommentService obj=new CommentService();
    
    public static CommentService getCommentService(){
        return obj;
    }
    
    private DataBase db;
    
    private CommentService(){
        db=DataBase.getDataBase();
//        System.err.println("comment service initialized");
    }
    
    public CommentDetail getCommentDetail(long commentId){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Comment comment=(Comment) session.get(Comment.class, commentId);
            if(comment!=null){
                LOG.info("CommentService, getCommentDetail :- "+
                          "commentId :- "+commentId);
                return Util.makeCommentDetail(comment);
            }else{
                LOG.warning("CommentService, getCommentDetail :- "+
                          "comment not found, commentId :- "+commentId);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return null;
    }
    
    public long addComment(CommentDetail commentDetail){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            if(commentDetail==null){
                LOG.warning("CommentService, addComment :- "+
                          "null reference commentDetail");
                return -1;
            }
            Comment comment=Util.makeComment(commentDetail);
            session.setFlushMode(FlushModeType.AUTO);
            Query q= session.getNamedQuery("UserProfile.byName");
            q.setParameter("name", commentDetail.getUserName());
            List<UserProfile> list = q.list();
            if(list.size()<1){
                LOG.warning("CommentService, addComment :- "+
                            "UserProfile not found, userName :- "+
                            commentDetail.getUserName());
                return -1;
            }else if(list.size()>1){
                LOG.warning("CommentService, addComment :- "+
                            "multiple UserProfile found, userName :- "+
                            commentDetail.getUserName());
                return -1;
            }
            Article article=(Article) session.get(Article.class, commentDetail.getArticleId());
            if(article == null){
                LOG.warning("CommentService, addComment :- "+
                            "Article not found, articleId :- "+
                            commentDetail.getArticleId());
                return -1;
            }
            if(list.get(0)==null||article==null)
                return -1;
//            session.save(comment);
            article.addComment(comment);
            list.get(0).addComment(comment);
            session.flush();
            t.commit();
            LOG.info("CommentService, addComment :- "+
                      "comment added, commentId :- "+comment.getCommentId());
            return comment.getCommentId();
        }catch(Exception ex){
            ex.printStackTrace();
            t.rollback();
        }finally{
            if(t!=null&&t.isActive())
                t.rollback();
        }
        return -1;
    }
   
    public boolean updateComment(CommentDetail commentDetail){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            if(commentDetail==null){
                LOG.warning("CommentService, updateComment :- "+
                            "null reference commentDetail");
                return false;
            }
//            System.out.println("check 1");
            session.setFlushMode(FlushModeType.AUTO);
            Comment comment=session.get(Comment.class, commentDetail.getCommentId());
            if(comment==null){
                LOG.warning("CommentService, updateComment :- "+
                            "Comment not found, commentId :- "+
                            commentDetail.getCommentId());
                return false;
            }
            comment.setCommentBody(commentDetail.getContent());
            session.flush();
            t.commit();
            LOG.info("CommentService, updateComment :- "+
                        "Comment updated, commentId :- "+commentDetail.getCommentId());
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
    
    public boolean removeCommentDetail(long commentId){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            session.setFlushMode(FlushModeType.AUTO);
            Comment comment=session.get(Comment.class, commentId);
            if(comment==null){
                LOG.warning("CommentService, removeCommentDetail :- "+
                            "Comment not found, commentId :- "+commentId);
                return false;
            }
            UserProfile user=comment.getAuthor();
            Article article=comment.getArticle();
            if(user!=null)
                user.removeComment(comment);
            if(article!=null)
                article.removeComment(comment);
            session.delete(comment);
            LOG.info("CommentService, removeCommentDetail :- "+
                        "Comment removed, commentId :- "+commentId);
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return false;
    }
    
    public Comment[] getAllComment(int start,int size){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Query q= session.getNamedQuery("Comment.allComment");
            q.setFirstResult(start);
            q.setMaxResults(size);
            List<Comment> list = q.list();
            if(list.size()>=1){
                LOG.info("CommentService, getAllComment :- "+
                          "start :- "+start+", size :- "+size);
                return list.toArray(new Comment[0]);
            }else{
                LOG.warning("CommentService, getAllComment :- "+
                          "no record found, start :- "+start+", size:-"+size);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return new Comment[0];
    }
    
    public long getAllCount(){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Query q= session.getNamedQuery("Comment.count");
            long count = (Long)q.uniqueResult();
            LOG.info("CommentService, getAllCount :- "+
                      "count :- "+count);
            return count;
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return 0;
    }
}
