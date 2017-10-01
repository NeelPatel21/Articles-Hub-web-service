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
import com.articles_hub.api.model.ShortArticleDetail;
import com.articles_hub.api.model.TagDetail;
import com.articles_hub.api.model.Util;
import com.articles_hub.database.beans.TagStatus;
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
public class TagService {

    private static final Logger LOG = Logger.getLogger(TagService.class.getName());
    
    private static TagService obj=new TagService();
    
    public static TagService getTagService(){
        return obj;
    }
    
    private DataBase db;
    
    private TagService(){
        db=DataBase.getDataBase();
//        System.err.println("tag service initialized");
    }
    
    public TagDetail getTagDetail(String tagName){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Query q= session.getNamedQuery("Tag.byName");
            q.setParameter("name", tagName);
            List<Tag> list = q.list();
            if(list.size()==1){
                LOG.info("TagService, getTagDetail :- "+
                          "tagName :- "+tagName);
                return Util.makeTagDetail(list.get(0));
            }else if(list.size()<1){
                LOG.warning("TagService, getTagDetail :- "+
                          "tag not found, tagName :- "+tagName);
            }else{
                LOG.warning("TagService, getTagDetail :- "+
                          "multiple tag found, tagName :- "+tagName);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return null;
    }
    
    public boolean addTag(TagDetail tagDetail, TagStatus status){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            if(tagDetail==null){
                LOG.warning("TagService, addTag :- "+
                          "null reference tagDetail");
                return false;
            }
            session.setFlushMode(FlushModeType.AUTO);
            Tag tag=Util.makeTag(tagDetail);
            tag.setTagStatus(status);
            session.save(tag);
            session.flush();
            t.commit();
            LOG.info("TagService, addTag :- "+
                      "tag added, tagName :- "+tagDetail.getTagName());
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive())
                t.rollback();
        }
        return false;
    }
    
    public ShortArticleDetail[] getAllArticles(String... tags){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Query q= session.createNamedQuery("Article.byTag");
//            System.out.println("scsacacawcacsc afffafwfafafafw"+tags[0]);
            q.setParameterList("tags",tags);
            List<Article> list = q.list();
//            System.out.println("hvjvbjbjkbknkjnknknknk   "+list.size());
            LOG.info("TagService, getAllArticles :- "+
                      "number of articles :- "+list.size());
            return list.stream()
//                      .peek(x->System.out.println("njsdnjcskscccawdawadad "+x))
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
    
    public ShortArticleDetail[] getArticles(int first, int size, String ... tags){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Query q= session.createNamedQuery("Article.byTag");
//            System.out.println("scsacacawcacsc afffafwfafafafw"+tags[0]);
            q.setParameterList("tags",tags);
            q.setFirstResult(first);
            q.setMaxResults(size);
            List<Article> list = q.list();
//            System.out.println("hvjvbjbjkbknkjnknknknk   "+list.size());
            LOG.info("TagService, getAllArticles :- "+
                      "number of articles :- "+list.size());
            return list.stream()
//                      .peek(x->System.out.println("njsdnjcskscccawdawadad "+x))
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

    public boolean removeTag(String tagName){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            session.setFlushMode(FlushModeType.AUTO);
            Query q= session.getNamedQuery("Tag.byName");
            q.setParameter("name", tagName);
            List<Tag> list = q.list();
            if(list.size()<1){
                LOG.warning("TagService, removeTagDetail :- "+
                          "tag not found, tagName :- "+tagName);
                return false;
            }else if(list.size()>1){
                LOG.warning("TagService, removeTagDetail :- "+
                          "multiple tag found, tagName :- "+tagName);
            }
            list.forEach(tag->{
                try{
                    tag.removeAll();
//                    LOG.info("TagService, getAllArticles :- ",
//                              "number of articles :- "+list.size());
//                    return list.stream()
//                              .map(Util::makeShortArticleDetail)
//                              .toArray(ShortArticleDetail[]::new);
                    session.delete(tag);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            });
            LOG.info("TagService, removeTagDetail :- "+
                      "tag removed successfully, tagName :- "+tagName);
            return true;
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return false;
    }
    
    public Tag[] getAllTag(int start,int size){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Query q= session.getNamedQuery("Tag.allTag");
            q.setFirstResult(start);
            q.setMaxResults(size);
            List<Tag> list = q.list();
            if(list.size()>=1){
                LOG.info("TagService, getAllTag :- "+
                          "start :- "+start+", size :- "+size);
                return list.toArray(new Tag[0]);
            }else{
                LOG.warning("TagService, getAllTag :- "+
                          "no record found, start :- "+start+", size:-"+size);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return new Tag[0];
    }
    
    public Tag[] getAllTag(int start,int size, TagStatus status){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Query q= session.getNamedQuery("Tag.byStatus");
            q.setParameter("status", status.name());
            q.setFirstResult(start);
            q.setMaxResults(size);
            List<Tag> list = q.list();
            if(list.size()>=1){
                LOG.info("TagService, getAllTag :- "+
                          "start :- "+start+", size :- "+size+", status :- "+status);
                return list.toArray(new Tag[0]);
            }else{
                LOG.warning("TagService, getAllTag :- "+
                          "no record found, start :- "+start+", size:-"+size
                          +", status :- "+status);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return new Tag[0];
    }
    
    public boolean updateTagStatus(String tagName,TagStatus status){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Query q= session.getNamedQuery("Tag.byName_all");
            q.setParameter("name", tagName);
            List<Tag> list = q.list();
            if(list.size()==1){
                LOG.info("TagService, updateTagStatus :- "+
                          "tagName :- "+tagName);
                list.get(0).setTagStatus(status);
            }else if(list.size()<1){
                LOG.warning("TagService, updateTagStatus :- "+
                          "tag not found, tagName :- "+tagName);
                return false;
            }else{
                LOG.warning("TagService, updateTagStatus :- "+
                          "multiple tag found, tagName :- "+tagName);
                return false;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return true;
    }
    
    public Tag getTag(String tagName){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Query q= session.getNamedQuery("Tag.byName_all");
            q.setParameter("name", tagName);
            List<Tag> list = q.list();
            if(list.size()==1){
                LOG.info("TagService, getTag :- "+
                          "tagName :- "+tagName);
                return list.get(0);
            }else if(list.size()<1){
                LOG.warning("TagService, getTagDetail :- "+
                          "tag not found, tagName :- "+tagName);
            }else{
                LOG.warning("TagService, getTagDetail :- "+
                          "multiple tag found, tagName :- "+tagName);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return null;
    }
    
    public long getAllCount(){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Query q= session.getNamedQuery("Tag.count");
            long count = (Long)q.uniqueResult();
            LOG.info("TagService, getAllCount :- "+
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
    
    public long getRequestCount(){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Query q= session.getNamedQuery("Tag.request_count");
            long count = (Long)q.uniqueResult();
            LOG.info("TagService, getRequestCount :- "+
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
