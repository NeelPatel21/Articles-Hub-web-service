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
package com.articles_hub.api.model;

import com.articles_hub.database.beans.Article;
import com.articles_hub.database.beans.Comment;
import com.articles_hub.database.beans.Tag;
import com.articles_hub.database.beans.UserProfile;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Collectors;

/**
 *
 * @author Neel Patel
 */
public class Util {
    private Util(){}
    
    public static UserDetail makeUserDetail(UserProfile user){
        try{
            if(user==null)
                return null;
            UserDetail userDetail=new UserDetail();
            userDetail.setUserName(user.getUserName());
            userDetail.setFirstName(user.getFirstName());
            userDetail.setLastName(user.getLastName());
            userDetail.setInfo(user.getInfo());
            userDetail.setEmailId(user.getEmailId());
            return userDetail;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    public static ShortUserDetail makeShortUserDetail(UserProfile user){
        try{
            if(user==null)
                return null;
            ShortUserDetail userDetail=new ShortUserDetail();
            userDetail.setUserName(user.getUserName());
            userDetail.setFirstName(user.getFirstName());
            userDetail.setLastName(user.getLastName());
//            userDetail.setEmailId(user.getEmailId());
            return userDetail;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    public static UserProfile makeUserProfile(UserDetail user){
        try{
            if(user==null)
                return null;
            UserProfile userProfile=new UserProfile();
            userProfile.setUserName(user.getUserName());
            userProfile.setFirstName(user.getFirstName());
            userProfile.setLastName(user.getLastName());
            userProfile.setInfo(user.getInfo());
            userProfile.setEmailId(user.getEmailId());
            userProfile.setPass(user.getPass());
            return userProfile;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    public static ArticleDetail makeArticleDetail(Article article){
        try{
            if(article==null)
                return null;
            ArticleDetail articleDetail=new ArticleDetail();
            articleDetail.setArticleId(article.getArticleId());
            articleDetail.setContent(article.getArticleContent());
            articleDetail.setDate(article.getPublishDate());
            articleDetail.setTitle(article.getTitle());
            articleDetail.setAuthor(article.getAuthor().getUserName());
//            System.out.println("article tags "+article.getTags());
            articleDetail.setTag(article.getTags().stream()
//                      .peek(x->System.out.println("tags found :- "+x))
                      .map(x->x.getTagName())
                        .collect(Collectors.toSet()));
            return articleDetail;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    public static Article makeArticle(ArticleDetail articleDetail){
        try{
            if(articleDetail==null)
                return null;
            Article article=new Article();
            article.setTitle(articleDetail.getTitle());
//            article.setPublishDate(articleDetail.getDate());
            article.setPublishDate(LocalDate.now());
//            article.getTags(articleDetail.getTag().stream().map());
            article.getArticleContent().clear();
            article.getArticleContent().addAll(articleDetail.getContent());
            return article;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    public static CommentDetail makeCommentDetail(Comment comment){
        try{
            if(comment==null)
                return null;
            CommentDetail commentDetail=new CommentDetail();
            commentDetail.setArticleId(comment.getArticle().getArticleId());
            commentDetail.setCommentId(comment.getCommentId());
            commentDetail.setContent(comment.getCommentBody());
            commentDetail.setUserName(comment.getAuthor().getUserName());
            commentDetail.setDate(comment.getDate());
            commentDetail.setTime(comment.getTime());
            return commentDetail;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    public static Comment makeComment(CommentDetail commentDetail){
        try{
            if(commentDetail==null)
                return null;
            Comment comment=new Comment();
            comment.setCommentBody(commentDetail.getContent());
//            comment.setDate(commentDetail.getDate());
            comment.setDate(LocalDate.now());
            comment.setTime(LocalTime.now());
            return comment;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    public static TagDetail makeTagDetail(Tag tag){
        try{
            if(tag==null)
                return null;
            TagDetail tagDetail=new TagDetail();
            tagDetail.setTagName(tag.getTagName());
            return tagDetail;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    public static Tag makeTag(TagDetail tagDetail){
        try{
            if(tagDetail==null)
                return null;
            Tag tag=new Tag();
            tag.setTagName(tagDetail.getTagName());
            return tag;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    public static ShortArticleDetail makeShortArticleDetail(Article article){
        try{
            if(article==null)
                return null;
            ShortArticleDetail articleDetail=new ShortArticleDetail();
            articleDetail.setArticleId(article.getArticleId());
            articleDetail.setDate(article.getPublishDate());
            articleDetail.setTitle(article.getTitle());
            articleDetail.setAuthor(article.getAuthor().getUserName());
//            System.out.println("article tags "+article.getTags());
            return articleDetail;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    
}
