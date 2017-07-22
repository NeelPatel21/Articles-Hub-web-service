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


import com.articles_hub.model.CommentDetail;
import com.articles_hub.model.ShortArticleDetail;
import com.articles_hub.model.UserDetail;
import com.articles_hub.providers.Secured;
import com.articles_hub.service.UserService;
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
import javax.ws.rs.core.SecurityContext;

/**
 *
 * @author Neel Patel
 */
@Path("/user")
@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
public class UserResource {
    
    private UserService service;
    
    public UserResource(){
        try{
            service=UserService.getUserService();
//            System.out.println(" dccewcahcajhcabcjabcajcbac "+service);
        }catch(Exception ex){
            ex.printStackTrace();
        }
//        System.out.println("user service request");
    }
//    @GET
//    public String getUserDetail(){
//        return "service :- ";
//    }
    
    @GET
    @Path("/{userName}")
//    @Produces(MediaType.APPLICATION_XML)
    public UserDetail getUserDetail(@PathParam("userName") String userName){
        UserDetail user=service.getUserDetail(userName);
        return user;
    }
    
    @POST
    public void createUserDetail(UserDetail user){
        service.addUser(user);
    }
    
//secure
    @PUT
    @Secured
    @Path("/{userName}")
    public void updateUserDetail(@PathParam("userName") String userName,
              UserDetail user, @Context SecurityContext secure){
        if(!secure.getUserPrincipal().getName().equals(userName))
            return;
        if(user.getUserName().equals(userName))
            service.updateUser(user);
    }
    
    @GET
    @Path("/{userName}/comments")
    @Secured
//    @Produces(MediaType.APPLICATION_XML)
    public CommentDetail[] getAllComments(@PathParam("userName") String userName,
              @Context SecurityContext secure){
        if(!secure.getUserPrincipal().getName().equals(userName))
            return null;
        return service.getAllComments(userName);
    }
    
    @GET
    @Path("/{userName}/articles")
    @Secured
//    @Produces(MediaType.APPLICATION_XML)
    public ShortArticleDetail[] getAllArticles(@PathParam("userName") String userName,
              @Context SecurityContext secure){
        if(!secure.getUserPrincipal().getName().equals(userName))
            return null;
        return service.getAllArticles(userName);
    }
    
    @GET
    @Path("/{userName}/likes")
    @Secured
//    @Produces(MediaType.APPLICATION_XML)
    public ShortArticleDetail[] getAllLikes(@PathParam("userName") String userName,
              @Context SecurityContext secure){
        if(!secure.getUserPrincipal().getName().equals(userName))
            return null;
        return service.getAllLikes(userName);
    }
    
    @POST
    @Path("/{userName}/like/{articleId}")
    @Secured
    public void addLike(@PathParam("userName") String userName,
                @PathParam("articleId") long articleId, @Context SecurityContext secure){
        if(!secure.getUserPrincipal().getName().equals(userName))
            return;
        service.addLike(userName, articleId);
    }
    
    @DELETE
    @Path("/{userName}/like/{articleId}")
    @Secured
    public void removeLike(@PathParam("userName") String userName,
                @PathParam("articleId") long articleId, @Context SecurityContext secure){
        if(!secure.getUserPrincipal().getName().equals(userName))
            return;
        service.removeLike(userName, articleId);
    }
    
}
