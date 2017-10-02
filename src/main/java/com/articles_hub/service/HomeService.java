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
import com.articles_hub.api.model.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Neel Patel
 */
public class HomeService {

    private static final Logger LOG = Logger.getLogger(HomeService.class.getName());
    
    private static HomeService obj;
    private List<Long> articleIds=Collections.emptyList();
    private final int FATCH_SIZE=100;
    private final int LIST_SIZE=20;
    private final int REFRESH_DELAY_SECONDS=150;
    
    public static HomeService getHomeService(){
        if(obj==null)
        synchronized(HomeService.class){
            if(obj==null)
                obj=new HomeService();
        }    
        return obj;
    }
    
    private DataBase db;
    
    private HomeService(){
        db=DataBase.getDataBase();
//        System.err.println("user service initialized");
        refresh();
        ScheduledExecutorService executor = DefaultExecutorService.getExecutorService();
        executor.scheduleWithFixedDelay(this::refresh,
                REFRESH_DELAY_SECONDS, REFRESH_DELAY_SECONDS, TimeUnit.SECONDS);
    }
    
    public ShortArticleDetail[] getArticles(){
        List<Long> articleIds;
        try{
            for(articleIds = this.articleIds; articleIds == null;
                      articleIds = this.articleIds);
            articleIds = new ArrayList<>(articleIds);
            Collections.shuffle(articleIds);
            LOG.info("HomeService, getArticles :- "+
                      "number of articleIds :- "+articleIds.size());
            return articleIds.parallelStream()
                      .unordered()
                      .map(x->getShortArticleDetail(x))
                      .filter(x->x!=null)
                      .limit(LIST_SIZE)
                      .toArray(ShortArticleDetail[]::new);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    public ShortArticleDetail[] getArticles(String userName){
        List<Long> articleIds;
        try{
            articleIds = getArticleIds(Arrays.asList(UserService.getUserService()
                            .getFavoriteTags(userName)).stream()
                            .map(x->x.getTagName()).toArray(String[]::new));
            articleIds = new ArrayList<>(articleIds);
//            Collections.shuffle(articleIds);
            LOG.info("HomeService, getArticles :- "+
                        "userName :- "+userName+", number of articleIds :- "
                        +articleIds.size());
            return articleIds.parallelStream()
                      .unordered()
                      .map(x->getShortArticleDetail(x))
                      .filter(x->x!=null)
                      .limit(LIST_SIZE)
                      .toArray(ShortArticleDetail[]::new);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    
    private void refresh(){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            List<Tag> tags = session.createCriteria(Tag.class).list();
            List<Long> articleIds=getArticleIds(tags.stream()
                      .map(x->x.getTagName()).toArray(String[]::new));
            if(articleIds!=null&&articleIds.size()>0){
                this.articleIds=Collections.unmodifiableList(articleIds);
                LOG.info("HomeService, refresh :- "+
                        "default list updated, number of articleIds :- "
                        +articleIds.size());
            }else{
                LOG.warning("HomeService, refresh :- "+
                        "default list not updated, list of articleIds :- "
                        +articleIds);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
    }
    
    private List<Long> getArticleIds(String ... tags){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
//            List<String> tagNames = Arrays.asList(tags);
            Collections.shuffle(Arrays.asList(tags));
            List<Long> articleIds = Arrays.stream(TagService.getTagService()
                        .getArticles(0, FATCH_SIZE,tags))
                        .parallel()
                        .map(x->x.getArticleId()).collect(Collectors.toList());
            if(articleIds!=null){
                LOG.info("HomeService, getArticleIds :- "+
                        "number of articleIds :- "+articleIds.size());
                Collections.shuffle(articleIds);
                return articleIds;
            }else{
                LOG.warning("HomeService, getArticleIds :- "+
                        "list not updated, List of articleId :- "+articleIds);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return null;
    }
    
    
    private ShortArticleDetail getShortArticleDetail(long articleId){
        Session session=db.getSession();
        Transaction t=session.beginTransaction();
        try{
            Article article=(Article) session.get(Article.class, articleId);
            if(article==null){
                LOG.warning("HomeService, getShortArticleDetail :- "+
                      "article not found, articleId :- "+articleId);
                return null;
            }
//            LOG.info("HomeService, getShortArticleDetail :- ",
//                      "articleId :- "+article.getArticleId());
            return Util.makeShortArticleDetail(article);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(t!=null&&t.isActive()&&!t.getRollbackOnly())
                t.commit();
        }
        return null;
    }
    
}
