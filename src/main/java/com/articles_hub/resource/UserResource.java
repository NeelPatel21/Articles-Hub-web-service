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
import com.articles_hub.model.LinkMaker;
import com.articles_hub.model.ShortArticleDetail;
import com.articles_hub.model.TagDetail;
import com.articles_hub.model.UserDetail;
import com.articles_hub.providers.Secured;
import com.articles_hub.service.UserService;
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
@Path("/user")
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
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
    
    @Context
    private UriInfo urif;
    
//    @GET
//    public String getUserDetail(){
//        return "service :- ";
//    }
    
    @GET
    @Path("/{userName}")
//    @Produces(MediaType.APPLICATION_XML)
    public UserDetail getUserDetail(@PathParam("userName") String userName){
        UserDetail user=service.getUserDetail(userName);
        LinkMaker.popLinks(urif,user);
        return user;
    }
    
    @POST
    public Response createUserDetail(UserDetail user){
        if(service.addUser(user)){
            UserDetail newUser= service.getUserDetail(user.getUserName());
            LinkMaker.popLinks(urif, newUser);
            return Response.created(URI.create(newUser.getLinks().stream()
                      .filter(x->x.getName().equalsIgnoreCase("self"))
                      .findAny().get().getUrl())).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
//secure
    @PUT
    @Secured
    @Path("/{userName}")
    public Response updateUserDetail(@PathParam("userName") String userName,
              UserDetail user, @Context SecurityContext secure){
        if(!secure.getUserPrincipal().getName().equals(userName))
            return Response.status(Response.Status.UNAUTHORIZED).build();
        if(user.getUserName().equals(userName))
            if(service.updateUser(user))
                return Response.status(Response.Status.ACCEPTED).build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    
    }
    
    @GET
    @Path("/{userName}/comments")
    @Secured
//    @Produces(MediaType.APPLICATION_XML)
    public CommentDetail[] getAllComments(@PathParam("userName") String userName,
              @Context SecurityContext secure){
        if(!secure.getUserPrincipal().getName().equals(userName))
            return null;
        CommentDetail ar[]=service.getAllComments(userName);
        LinkMaker.popLinks(urif, ar);
        return ar;
    }
    
    @GET
    @Path("/{userName}/articles")
    @Secured
//    @Produces(MediaType.APPLICATION_XML)
    public ShortArticleDetail[] getAllArticles(@PathParam("userName") String userName,
              @Context SecurityContext secure){
        if(!secure.getUserPrincipal().getName().equals(userName))
            return null;
        ShortArticleDetail ar[]=service.getAllArticles(userName);
        LinkMaker.popLinks(urif, ar);
        return ar;
    }
    
    @GET
    @Path("/{userName}/likes")
    @Secured
//    @Produces(MediaType.APPLICATION_XML)
    public ShortArticleDetail[] getAllLikes(@PathParam("userName") String userName,
              @Context SecurityContext secure){
        if(!secure.getUserPrincipal().getName().equals(userName))
            return null;
        ShortArticleDetail ar[]=service.getAllLikes(userName);
        LinkMaker.popLinks(urif, ar);
        return ar;
    }
    
    @GET
    @Path("/{userName}/favorite-tags")
    @Secured
//    @Produces(MediaType.APPLICATION_XML)
    public TagDetail[] getFavoriteTags(@PathParam("userName") String userName,
              @Context SecurityContext secure){
        if(!secure.getUserPrincipal().getName().equals(userName))
            return null;
        TagDetail ar[]=service.getFavoriteTags(userName);
        LinkMaker.popLinks(urif, ar);
        return ar;
    }
    
    @POST
    @Path("/{userName}/like/{articleId}")
    @Secured
    public Response addLike(@PathParam("userName") String userName,
                @PathParam("articleId") long articleId, @Context SecurityContext secure){
        if(!secure.getUserPrincipal().getName().equals(userName))
            return Response.status(Response.Status.UNAUTHORIZED).build();
        if(service.addLike(userName, articleId))
            return Response.status(Response.Status.ACCEPTED).build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @DELETE
    @Path("/{userName}/like/{articleId}")
    @Secured
    public Response removeLike(@PathParam("userName") String userName,
                @PathParam("articleId") long articleId, @Context SecurityContext secure){
        if(!secure.getUserPrincipal().getName().equals(userName))
            return Response.status(Response.Status.UNAUTHORIZED).build();
        if(service.removeLike(userName, articleId))
            return Response.status(Response.Status.ACCEPTED).build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @POST
    @Path("/{userName}/favorite-tags")
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addFavoriteTags(@PathParam("userName") String userName,
                TagDetail[] tags,
                @Context SecurityContext secure){
        if(!secure.getUserPrincipal().getName().equals(userName))
            return Response.status(Response.Status.UNAUTHORIZED).build();
        if(service.setFavoriteTags(userName, tags))
            return Response.status(Response.Status.ACCEPTED).build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
}
