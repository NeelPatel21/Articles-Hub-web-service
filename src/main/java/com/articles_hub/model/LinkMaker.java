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
package com.articles_hub.model;

import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Neel Patel
 */
public class LinkMaker {
    private LinkMaker(){}
    
    public static <T> void popLinks(UriInfo urif,T ... ar){
        for(T obj:ar){
            if(obj instanceof ArticleDetail)
                popLinks(urif,(ArticleDetail)obj);
            else if(obj instanceof UserDetail)
                popLinks(urif,(UserDetail)obj);
            else if(obj instanceof CommentDetail)
                popLinks(urif,(CommentDetail)obj);
            else if(obj instanceof TagDetail)
                popLinks(urif,(TagDetail)obj);
            else if(obj instanceof ShortArticleDetail)
                popLinks(urif,(ShortArticleDetail)obj);
            else if(obj instanceof ShortUserDetail)
                popLinks(urif,(ShortUserDetail)obj);
        }
    }
    
    public static void popLinks(UriInfo urif,ArticleDetail articleDetail){
        if(urif==null|| articleDetail==null)
            return;
        Link self=new Link("self",urif.getBaseUri()+"article/"
                  +articleDetail.getArticleId());
        Link comments=new Link("comments",self.getUrl()+"/comments");
        Link likes=new Link("likes",self.getUrl()+"/likes");
        Link author=new Link("author",urif.getBaseUri()+"user/"
                  +articleDetail.getAuthor());
        articleDetail.getLinks().add(self);
        articleDetail.getLinks().add(comments);
        articleDetail.getLinks().add(likes);
        articleDetail.getLinks().add(author);
    }
    
    public static void popLinks(UriInfo urif,UserDetail userDetail){
        if(urif==null|| userDetail==null)
            return;
        Link self=new Link("self",urif.getBaseUri()+"user/"
                  +userDetail.getUserName());
        Link articles=new Link("articles",self.getUrl()+"/articles");
        Link comments=new Link("comments",self.getUrl()+"/comments");
        Link likes=new Link("likes",self.getUrl()+"/likes");
        userDetail.getLinks().add(self);
        userDetail.getLinks().add(articles);
        userDetail.getLinks().add(comments);
        userDetail.getLinks().add(likes);
    }
    
    public static void popLinks(UriInfo urif,CommentDetail commentDetail){
        if(urif==null|| commentDetail==null)
            return;
        Link self=new Link("self",urif.getBaseUri()+"comment/"
                  +commentDetail.getCommentId());
        Link article=new Link("article",urif.getBaseUri()+"article/"
                  +commentDetail.getArticleId());
        Link user=new Link("user",urif.getBaseUri()+"user/"
                  +commentDetail.getUserName());
        commentDetail.getLinks().add(self);
        commentDetail.getLinks().add(article);
        commentDetail.getLinks().add(user);
    }
    
    public static void popLinks(UriInfo urif,TagDetail tagDetail){
        if(urif==null|| tagDetail==null)
            return;
        Link self=new Link("self",urif.getBaseUri()+"tag/"
                  +tagDetail.getTagName());
        Link articles=new Link("articles",self.getUrl()+"/articles");
        tagDetail.getLinks().add(self);
        tagDetail.getLinks().add(articles);
    }
    
    public static void popLinks(UriInfo urif,ShortArticleDetail articleDetail){
        if(urif==null|| articleDetail==null)
            return;
        articleDetail.setLink(urif.getBaseUri()+"article/"
                  +articleDetail.getArticleId());
    }
    
    public static void popLinks(UriInfo urif,ShortUserDetail userDetail){
        if(urif==null|| userDetail==null)
            return;
        userDetail.setLink(urif.getBaseUri()+"user/"
                  +userDetail.getUserName());
    }
    
}
