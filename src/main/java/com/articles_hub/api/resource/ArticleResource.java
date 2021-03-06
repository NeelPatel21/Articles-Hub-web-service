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
package com.articles_hub.api.resource;


import com.articles_hub.api.model.ArticleDetail;
import com.articles_hub.api.model.CommentDetail;
import com.articles_hub.api.model.LinkMaker;
import com.articles_hub.api.model.ShortUserDetail;
import com.articles_hub.api.providers.Secured;
import com.articles_hub.service.ArticleService;
import java.net.URI;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Neel Patel
 */
@Path("/article")
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class ArticleResource {
    
    private ArticleService service;
    
    public ArticleResource(){
        try{
            service=ArticleService.getArticleService();
//            System.out.println(" dccewcahcajhcabcjabcajcbac "+service);
        }catch(Exception ex){
            ex.printStackTrace();
        }
//        System.out.println("article service request");
    }
    
    @Context
    private UriInfo urif;
    
//    @GET
//    public String getUserDetail(){
//        return "service :- ";
//    }
    
    @GET
    @Path("/{articleId}")
//    @Produces(MediaType.APPLICATION_XML)
    public ArticleDetail getArticleDetail(@PathParam("articleId") long articleId){
        ArticleDetail article=service.getArticleDetail(articleId);
//        System.out.println("article request "+article);
        LinkMaker.popLinks(urif,article);
        return article;
    }
    
    @GET
    @Path("/{articleId}/likes")
//    @Produces(MediaType.APPLICATION_XML)
    public ShortUserDetail[] getAllLikes(@PathParam("articleId") long articleId){
        ShortUserDetail ar[]=service.getAllLikes(articleId);
        LinkMaker.popLinks(urif,ar);
        return ar;
    }
    
    @GET
    @Path("/{articleId}/comments")
//    @Produces(MediaType.APPLICATION_XML)
    public CommentDetail[] getAllComments(@PathParam("articleId") long articleId){
        CommentDetail ar[]=service.getAllComments(articleId);
        LinkMaker.popLinks(urif,ar);
        return ar;
    }
    
//secure
    @POST
    @Secured
    public Response createArticleDetail(ArticleDetail article,
              @Context SecurityContext secure){
        if(!secure.getUserPrincipal().getName().equals(article.getAuthor()))
            return Response.status(Response.Status.BAD_REQUEST).build();
        long id=service.addArticle(article);
        if(id>=0){
            ArticleDetail newArticle=service.getArticleDetail(id);
            LinkMaker.popLinks(urif, newArticle);
            return Response.created(URI.create(newArticle.getLinks().stream()
                      .filter(x->x.getName().equalsIgnoreCase("self"))
                      .findAny().get().getUrl())).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @PUT
    @Path("/{articleId}")
    @Secured
    public Response updateArticleDetail(@PathParam("articleId") long articleId,
              ArticleDetail articleDetail, @Context SecurityContext secure){
        if(articleDetail.getArticleId() != articleId)
            return Response.status(Response.Status.BAD_REQUEST).build();
        if(!secure.getUserPrincipal().getName()
                  .equals(service.getArticleDetail(articleId).getAuthor()))
            return Response.status(Response.Status.UNAUTHORIZED).build();
        if(service.updateArticle(articleDetail))
            return Response.status(Response.Status.ACCEPTED).build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @DELETE
    @Secured
    @Path("/{articleId}")
    public Response removeArticle(@PathParam("articleId") long articleId,
              @Context SecurityContext secure){
        if(!secure.getUserPrincipal().getName().equals(service
                  .getArticleDetail(articleId).getAuthor()))
            return Response.status(Response.Status.UNAUTHORIZED).build();
        if(service.removeArticleDetail(articleId))
            return Response.status(Response.Status.OK).build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    
}
    
    
    
    
    
    
    
    
