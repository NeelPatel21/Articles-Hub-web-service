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
import com.articles_hub.api.model.CommentDetail;
import com.articles_hub.api.model.ShortArticleDetail;
import com.articles_hub.api.model.TagDetail;
import com.articles_hub.api.model.UserDetail;
import com.articles_hub.api.model.Util;
import java.util.Arrays;
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
public class UserService {

    private static final Logger LOG = Logger.getLogger(UserService.class.getName());
    
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
            if(list.size()==1){
                LOG.info("UserService, getUserDetail :- "+
                          "userName :- "+userName);
                return Util.makeUserDetail(list.get(0));
            }else if(list.size()>1){
                LOG.warning("UserService, getUserDetail :- "+
                          "multiple UserProfile found, userName :- "+userName);
            }else{
                LOG.warning("UserService, getUserDetail :- "+
                          "UserProfile not found, userName :- "+userName);
            }
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
            if(user==null){
                LOG.warning("UserService, addUser :- "+
                          "null reference user");
                return false;
            }
//            System.out.println("check 1");
            session.setFlushMode(FlushModeType.AUTO);
            session.save(Util.makeUserProfile(user));
            session.flush();
            t.commit();
            LOG.info("UserService, addUser :- "+
                      "userName :- "+user.getUserName());
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
            if(user==null){
                LOG.warning("UserService, updateUser :- "+
                          "null reference user");
                return false;
            }
            session.setFlushMode(FlushModeType.AUTO);
            Query q= session.getNamedQuery("UserProfile.byName");
            q.setParameter("name", user.getUserName());
            List<UserProfile> list = q.list();
            if(list.size()!=1){
                LOG.warning("UserService, updateUser :- "+
                            "multiple UserProfile found, userName :- "
                            +user.getUserName());
                return false;
            }
            UserProfile userProfile=list.get(0);
            userProfile.setFirstName(user.getFirstName());
            userProfile.setLastName(user.getLastName());
            userProfile.setPass(user.getPass());
            userProfile.setInfo(user.getInfo());
            userProfile.setEmailId(user.getEmailId());
            session.flush();
            t.commit();
            LOG.info("UserService, updateUser :- "+
                        "UserProfile updated, userName :- "
                        +user.getUserName());
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
            if(list.size()<1){
                LOG.warning("UserService, getAllComments :- "+
                          "UserProfile not found, userName :- "+userName);
                return null;
            }else if(list.size()>1){
                LOG.warning("UserService, getAllComments :- "+
                          "multiple UserProfile found, userName :- "+userName);
                return null;
            }
            UserProfile user = list.get(0);
            LOG.info("UserService, getAllComments :- "+
                        "userName :- "+userName+", number of comments :- "+
                        user.getComments().size());
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
            if(list.size()<1){
                LOG.warning("UserService, getAllArticles :- "+
                          "UserProfile not found, userName :- "+userName);
                return null;
            }else if(list.size()>1){
                LOG.warning("UserService, getAllArticles :- "+
                          "multiple UserProfile found, userName :- "+userName);
                return null;
            }
            UserProfile user = list.get(0);
            LOG.info("UserService, getAllArticles :- "+
                        "userName :- "+userName+", number of articles :- "+
                        user.getArticles().size());
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
            if(list.size()<1){
                LOG.warning("UserService, getAllLikes :- "+
                          "UserProfile not found, userName :- "+userName);
                return null;
            }else if(list.size()>1){
                LOG.warning("UserService, getAllLikes :- "+
                          "multiple UserProfile found, userName :- "+userName);
                return null;
            }
            UserProfile user = list.get(0);
            LOG.info("UserService, getAllLikes :- "+
                        "userName :- "+userName+", number of Likes :- "+
                        user.getLikes().size());
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
            if(list.size()<1){
                LOG.warning("UserService, addLike :- "+
                          "UserProfile not found, userName :- "+userName);
                return false;
            }else if(list.size()>1){
                LOG.warning("UserService, addLike :- "+
                          "multiple UserProfile found, userName :- "+userName);
                return false;
            }
            UserProfile user = list.get(0);
            Article article = (Article) session.get(Article.class, articleId);
            article.addLike(user);
            session.flush();
            t.commit();
            LOG.info("UserService, addLike :- "+
                        "like added, userName :- "+userName
                        +", articleId :- "+articleId);
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
            if(list.size()<1){
                LOG.warning("UserService, removeLike :- "+
                          "UserProfile not found, userName :- "+userName);
                return false;
            }else if(list.size()>1){
                LOG.warning("UserService, removeLike :- "+
                          "multiple UserProfile found, userName :- "+userName);
                return false;
            }
            UserProfile user = list.get(0);
            Article article = (Article) session.get(Article.class, articleId);
            article.removeLike(user);
            session.flush();
            t.commit();
            LOG.info("UserService, removeLike :- "+
                        "like removed, userName :- "+userName+", articleId :- "+articleId);
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return false;
    }
    
    public boolean setFavoriteTags(String userName,TagDetail[] tags){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            session.setFlushMode(FlushModeType.AUTO);
            Query q= session.getNamedQuery("UserProfile.byName");
            q.setParameter("name", userName);
            List<UserProfile> list = q.list();
            if(list.size()<1){
                LOG.warning("UserService, setFavoriteTags :- "+
                          "UserProfile not found, userName :- "+userName);
                return false;
            }else if(list.size()>1){
                LOG.warning("UserService, setFavoriteTags :- "+
                          "multiple UserProfile found, userName :- "+userName);
                return false;
            }
            UserProfile user = list.get(0);
            
            // logic
            user.getFavoriteTag().clear(); // clear favorite tags
            
            // add new favorite tags 
            Arrays.stream(tags).forEach(tagName->{
                try{
                    // get tag from hibernate
                    Query q2= session.getNamedQuery("Tag.byName");
                    q2.setParameter("name", tagName.getTagName());
                    List<Tag> list2 = q2.list();
                    if(list.size()==1)
                        user.getFavoriteTag().add(list2.get(0)); // add new tag
                }catch(Exception ex){
//                    System.err.println("test 1");
                    ex.printStackTrace();
//                }finally{
//                    if(t2!=null&&t2.isActive()&&!t2.getRollbackOnly())
//                        t2.commit();
                }
            });
            session.flush();
            t.commit();
            LOG.info("UserService, setFavoriteTags :- "+
                        "favorite tags updated, userName :- "+userName);
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return false;
    }
    
    public TagDetail[] getFavoriteTags(String userName){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Query q= session.getNamedQuery("UserProfile.byName");
            q.setParameter("name", userName);
            List<UserProfile> list = q.list();
            if(list.size()<1){
                LOG.warning("UserService, getFavoriteTags :- "+
                          "UserProfile not found, userName :- "+userName);
                return null;
            }else if(list.size()>1){
                LOG.warning("UserService, getFavoriteTags :- "+
                          "multiple UserProfile found, userName :- "+userName);
                return null;
            }
            UserProfile user = list.get(0);
            LOG.info("UserService, setFavoriteTags :- "+
                        "userName :- "+userName+", number of favorite tags :- "
                        +user.getFavoriteTag().size());
            return user.getFavoriteTag().stream()
                      .map(Util::makeTagDetail)
                      .toArray(TagDetail[]::new);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return null;
    }
    
    public UserProfile[] getAllUserProfiles(int start,int size){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Query q= session.getNamedQuery("UserProfile.allUser");
            q.setFirstResult(start);
            q.setMaxResults(size);
            List<UserProfile> list = q.list();
            if(list.size()>=1){
                LOG.info("UserService, getAllUserProfiles :- "+
                          "start :- "+start+", size :- "+size);
                return list.toArray(new UserProfile[0]);
            }else{
                LOG.warning("UserService, getAllUserProfiles :- "+
                          "no record found, start :- "+start+", size:-"+size);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return new UserProfile[0];
    }
    
    public boolean removeUser(String userName){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            session.setFlushMode(FlushModeType.AUTO);
            Query q= session.getNamedQuery("UserProfile.byName");
            q.setParameter("name", userName);
            List<UserProfile> list = q.list();
            if(list.size()==1){
                LOG.info("UserService, removeUser :- "+
                          "userName :- "+userName);
            }else if(list.size()>1){
                LOG.warning("UserService, removeUser :- "+
                          "multiple UserProfile found, userName :- "+userName);
            }else{
                LOG.warning("UserService, removeUser :- "+
                          "UserProfile not found, userName :- "+userName);
                return false;
            }
            AuthenticationService authService=AuthenticationService.getAuthenticationService();
            
            //remove authentication records of user.
            list.stream().map(user->authService.getToken(user.getUserName()))
                      .filter(token->token!=null)
                      .forEach(token->authService.userLogout(token));
            //remove article, comments, likes done by user.
            list.stream().peek(user->{
                //remove articles
                user.getArticles().forEach(article->ArticleService
                    .getArticleService()
                    .removeArticleDetail(article.getArticleId()));
                session.flush();
                //remove comments
                user.getComments().forEach(comment->CommentService
                    .getCommentService()
                    .removeCommentDetail(comment.getCommentId()));
                session.flush();
                //remove likes
                user.getLikes().forEach(like->
                    removeLike(user.getUserName(), like.getArticleId()));
                session.flush();
                //remove favorite tags
                setFavoriteTags(user.getUserName(), new TagDetail[0]);
                session.flush();
                System.out.println("before refresh :- "+user.getFavoriteTag());
//                session.refresh(user);
                System.out.println("after refresh :- "+user.getFavoriteTag());
            }).count();
            list.forEach(user->session.delete(user));
            LOG.info("UserService, removeUser :- "+
                      "user removed successfully, userName :- "+userName);
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return false;
    }
    
    public long getAllCount(){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Query q= session.getNamedQuery("UserProfile.count");
            long count = (Long)q.uniqueResult();
            LOG.info("UserService, getAllCount :- "+
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
