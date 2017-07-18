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
import com.articles_hub.model.ShortArticleDetail;
import com.articles_hub.model.TagDetail;
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
public class TagService {
    
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
            if(list.size()==1)
                return Util.makeTagDetail(list.get(0));
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return null;
    }
    
    public boolean addTag(TagDetail tagDetail){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            if(tagDetail==null)
                return false;
            session.setFlushMode(FlushModeType.AUTO);
            session.save(Util.makeTag(tagDetail));
            session.flush();
            t.commit();
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
    
}
