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
package com.articles_hub.resource;


import com.articles_hub.model.ArticleDetail;
import com.articles_hub.model.CommentDetail;
import com.articles_hub.model.ShortUserDetail;
import com.articles_hub.service.ArticleService;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Neel Patel
 */
@Path("/article")
@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
public class ArticleResource {
    
    private ArticleService service;
    
    public ArticleResource(){
        try{
            service=ArticleService.getArticleService();
//            System.out.println(" dccewcahcajhcabcjabcajcbac "+service);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        System.out.println("user service request");
    }
//    @GET
//    public String getUserDetail(){
//        return "service :- ";
//    }
    
    @GET
    @Path("/{articleId}")
//    @Produces(MediaType.APPLICATION_XML)
    public ArticleDetail getArticleDetail(@PathParam("articleId") long articleId){
        ArticleDetail article=service.getArticleDetail(articleId);
        System.out.println("article request "+article);
        return article;
    }
    
    @POST
    public void createArticleDetail(ArticleDetail article){
        service.addArticle(article);
    }
    
    @PUT
    @Path("/{articleId}")
    public void updateUserDetail(@PathParam("articleId") long articleId,
              ArticleDetail articleDetail){
        if(articleDetail.getArticleId() == articleId)
            service.updateArticle(articleDetail);
    }
    
    @GET
    @Path("/{articleId}/comments")
//    @Produces(MediaType.APPLICATION_XML)
    public CommentDetail[] getAllComments(@PathParam("articleId") long articleId){
        return service.getAllComments(articleId);
    }
    
    @GET
    @Path("/{articleId}/likes")
//    @Produces(MediaType.APPLICATION_XML)
    public ShortUserDetail[] getAllLikes(@PathParam("articleId") long articleId){
        return service.getAllLikes(articleId);
    }
    
    @DELETE
    @Path("/{articleId}")
    public void removeArticle(@PathParam("articleId") long articleId){
        service.removeArticleDetail(articleId);
    }
    
    
}
    
    
    
    
    
    
    
    
