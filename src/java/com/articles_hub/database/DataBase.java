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
package com.articles_hub.database;

import com.articles_hub.database.beans.Article;
import com.articles_hub.database.beans.Tag;
import com.articles_hub.database.beans.UserProfile;
//import static com.articles_hub.database.beans.UserProfile_.userName;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

/**
 *
 * @author Neel Patel
 */
public class DataBase {
    private SessionFactory sf=new Configuration().configure().buildSessionFactory();
    private Session session;
    
    public DataBase(){
        session = sf.openSession();
        //session.setHibernateFlushMode(FlushMode.ALWAYS);
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            try{
                this.finalize();
            }catch(Throwable t){
                t.printStackTrace();
            }
        }));
        session.beginTransaction();
    }

    /**
     * this method return object of type UserProfile if user having userName
       specified exist in database.
     * object return by this method is persistent i.e. if any changes made in
       future on this object will be reflected to the database.
     * this method return {@code null} if there is no record of specified
       username or more then one record of specified username.
     * @param userName user name
     * @return object of UserProfile if available, null otherwise. 
     */
    public UserProfile getUserProfile(String userName){
        try{
            Query q= session.createNamedQuery("UserProfile.byName");
            q.setParameter("name", userName);
            List<UserProfile> list = q.list();
            if(list.size()==1)
                return list.get(0);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    /**
     * this method return object of type UserProfile if user having
       {@code userId} specified exist in database.
     * object return by this method is persistent i.e. if any changes made in
       future on this object will be reflected to the database.
     * this method return {@code null} if there is no record of specified
       userId or more then one record of specified userId.
     * @param userId user id
     * @return object of UserProfile if available, null otherwise. 
     */
    public UserProfile getUserProfile(long userId){
        try{
            return session.get(UserProfile.class, userId);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    /**
     * this method store object of UserProfile in database.
     * this method make any transient object persistent.
     * this method should be called only once for object otherwise
       there may be chance of duplicate record.
     * this method returns {@code userid} of the object.
     * this method returns -1 if there is any error occur while storing
       object in database.
     * @param user object to be stored.
     * @return id of the user.
     */
    public long storeUserProfile(UserProfile user){
        try{
            return (long)session.save(user);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return -1;
    }
    
    /**
     * this method return object of type Article if article having
       {@code articleId} specified exist in database.
     * object return by this method is persistent i.e. if any changes made in
       future on this object will be reflected to the database.
     * this method return {@code null} if there is no record of specified
       articleId or more then one record of specified articleId.
     * @param articleId article id
     * @return object of Article if available, null otherwise. 
     */
    public Article getArticle(long articleId){
        try{
            return session.get(Article.class, articleId);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    /**
     * this method store object of Article in database.
     * this method make any transient object persistent.
     * this method should be called only once for object otherwise
       there may be chance of duplicate record.
     * this method returns {@code articleid} of the object.
     * this method returns -1 if there is any error occur while storing
       object in database.
     * @param article object to be stored.
     * @return id of the article.
     */
    public long storeArticle(Article article){
        try{
            return (long)session.save(article);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return -1;
    }
    
    /**
     * this method return object of type Tag if Tag having tagName
       specified exist in database.
     * object return by this method is persistent i.e. if any changes made in
       future on this object will be reflected to the database.
     * this method return {@code null} if there is no record of specified
       tagName or more then one record of specified tagName.
     * @param tagName tag name
     * @return object of Tag if available, null otherwise. 
     */
    public Tag getTag(String tagName){
        try{
            Query q= session.createNamedQuery("Tag.byName");
            q.setParameter("name", tagName);
            List<Tag> list = q.list();
            if(list.size()==1)
                return list.get(0);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    /**
     * this method return object of type Tag if tag having
       {@code tagId} specified exist in database.
     * object return by this method is persistent i.e. if any changes made in
       future on this object will be reflected to the database.
     * this method return {@code null} if there is no record of specified
       tagName or more then one record of specified tagName.
     * @param tagId user id
     * @return object of Tag if available, null otherwise. 
     */
    public Tag getTag(long tagId){
        try{
            return session.get(Tag.class, tagId);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    /**
     * this method store object of Tag in database.
     * this method make any transient object persistent.
     * this method should be called only once for object otherwise
       there may be chance of duplicate record.
     * this method returns {@code tagId} of the object.
     * this method returns -1 if there is any error occur while storing
       object in database.
     * @param tag object to be stored.
     * @return id of the tag.
     */
    public long storeTag(Tag tag){
        try{
            return (long)session.save(tag);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return -1;
    }
    
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        session.flush();
        session.close();
        sf.close();
    }
    
}
