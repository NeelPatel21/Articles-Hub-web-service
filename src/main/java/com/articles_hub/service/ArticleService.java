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
import com.articles_hub.database.beans.Tag;
import com.articles_hub.database.beans.UserProfile;
import com.articles_hub.api.model.ArticleDetail;
import com.articles_hub.api.model.CommentDetail;
import com.articles_hub.api.model.ShortUserDetail;
import com.articles_hub.api.model.Util;
import java.util.List;
import java.util.logging.Logger;
//import javax.persistence.Query;
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
public class ArticleService {

    private static final Logger LOG = Logger.getLogger(ArticleService.class.getName());
    
    private static ArticleService obj=new ArticleService();
    
    public static ArticleService getArticleService(){
        return obj;
    }
    
    private DataBase db;
    
    private ArticleService(){
        db=DataBase.getDataBase();
//        System.err.println("Article service initialized");
    }
    
    public ArticleDetail getArticleDetail(long articleId){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Article article=(Article) session.get(Article.class, articleId);
            if(article==null){
                LOG.warning("ArticleService, getArticleDetail :- "+
                      "article not found, articleId :- "+articleId);
                return null;
            }
            LOG.info("ArticleService, getArticleDetail :- "+
                      "articleId :- "+article.getArticleId());
            return Util.makeArticleDetail(article);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return null;
    }
    
    public long addArticle(ArticleDetail articleDetail){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            if(articleDetail==null){
                LOG.warning("ArticleService, addArticle :- "+
                          "Null reference articleDetail");
                return -1;
            }
            Article article=Util.makeArticle(articleDetail);
            session.setFlushMode(FlushModeType.AUTO);
            Query q= session.getNamedQuery("UserProfile.byName");
            q.setParameter("name", articleDetail.getAuthor());
            List<UserProfile> list = q.list();
//            System.out.println("check 5 - "+articleDetail.getAuthor());
            if(list.size()!=1){
                LOG.warning("ArticleService, addArticle :- "+
                            "Invalid UserProfile, number of UserProfile found :- "
                            +list.size());
                return -1;
            }
            addTags(article, articleDetail);
//            session.save(article);
            list.get(0).addArticle(article);
            session.flush();
            t.commit();
            LOG.info("ArticleService, addArticle :- "+
                      "Article Created, articleId :- "+article.getArticleId());
            return article.getArticleId();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive())
                t.rollback();
//            if(session!=null)
//                session.flush();
        }
        return -1;
    }
    
    public boolean updateArticle(ArticleDetail articleDetail){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            if(articleDetail==null){
                LOG.warning("ArticleService, updateArticle :- "+
                          "Null reference articleDetail");
                return false;
            }
//            System.out.println("check 1");
            session.setFlushMode(FlushModeType.AUTO);
            Article article=session.get(Article.class, articleDetail.getArticleId());
            if(article==null){
                LOG.warning("ArticleService, updateArticle :- "+
                            "article not found, articleId :- "
                            +articleDetail.getArticleId());
                return false;
            }
            article.setTitle(articleDetail.getTitle());
            article.getArticleContent().clear();
            article.getArticleContent().addAll(articleDetail.getContent());
            addTags(article, articleDetail);
            session.flush();
            t.commit();
            LOG.info("ArticleService, updateArticle :- "+
                      "article updated, aricleId :- "+article.getArticleId());
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
    
    public CommentDetail[] getAllComments(long articleId){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Article article=(Article) session.get(Article.class, articleId);
            if(article==null){
                LOG.warning("ArticleService, getAllComments :- "+
                            "article not found, articleId :- "+articleId);
                return null;
            }
            LOG.info("ArticleService, getAllComments :- "+
                        "articleId :- "+article.getArticleId()
                        +", number of comments :- "+article.getComments().size());
            return article.getComments().stream()
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
    
    public ShortUserDetail[] getAllLikes(long articleId){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Article article=(Article) session.get(Article.class, articleId);
            if(article==null){
                LOG.warning("ArticleService, getAllLikes :- "+
                            "article not found, articleId :- "+articleId);
                return null;
            }
            LOG.info("ArticleService, getAllLikes :- "+
                        "articleId :- "+article.getArticleId()
                        +", number of Likes :- "+article.getLikes().size());
            return article.getLikes().stream()
                      .map(Util::makeShortUserDetail)
                      .toArray(ShortUserDetail[]::new);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return null;
    }
    
    public boolean removeArticleDetail(long articleId){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            session.setFlushMode(FlushModeType.AUTO);
            Article article=(Article) session.get(Article.class, articleId);
            if(article==null){
                LOG.warning("ArticleService, removeArticleDetail :- "+
                            "article not found, articleId :- "+articleId);
                return false;
            }
            session.delete(article);
            LOG.info("ArticleService, removeArticleDetail :- "+
                        "article removed, articleId :- "+article.getArticleId());
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return false;
    }
    
    private void addTags(Article article,ArticleDetail articleDetail){
        article.getTags().clear();
        articleDetail.getTag().forEach(tagName->{
            Session session=db.getSession();
            Transaction t=session.beginTransaction();
            try{
                Query q= session.getNamedQuery("Tag.byName");
                q.setParameter("name", tagName);
                List<Tag> list = q.list();
                if(list.size()==1)
                    article.getTags().add(list.get(0));
            }catch(Exception ex){
                ex.printStackTrace();
            }finally{
                if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                    t.commit();
            }
        });     
    }
    
    public Article[] getAllArticle(int start,int size){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Query q= session.getNamedQuery("Article.allArticle");
            q.setFirstResult(start);
            q.setMaxResults(size);
            List<Article> list = q.list();
            if(list.size()>=1){
                LOG.info("ArticleService, getAllArticle :- "+
                          "start :- "+start+", size :- "+size);
                return list.toArray(new Article[0]);
            }else{
                LOG.warning("ArticleService, getAllArticle :- "+
                          "no record found, start :- "+start+", size:-"+size);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return new Article[0];
    }
    
    public long getAllCount(){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Query q= session.getNamedQuery("Article.count");
            long count = (Long)q.uniqueResult();
            LOG.info("ArticleService, getAllCount :- "+
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
