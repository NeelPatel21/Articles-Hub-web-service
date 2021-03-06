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


import com.articles_hub.api.model.CommentDetail;
import com.articles_hub.api.model.LinkMaker;
import com.articles_hub.api.providers.Secured;
import com.articles_hub.service.CommentService;
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
@Path("/comment")
@Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
public class CommentResource {
    
    private CommentService service;
    
    public CommentResource(){
        try{
            service=CommentService.getCommentService();
//            System.out.println(" dccewcahcajhcabcjabcajcbac "+service);
        }catch(Exception ex){
            ex.printStackTrace();
        }
//        System.out.println("comment service request");
    }
    
    @Context
    private UriInfo urif;
    
//    @GET
//    public String getCommentDetail(){
//        return "service :- ";
//    }
    
    @GET
    @Path("/{commentId}")
//    @Produces(MediaType.APPLICATION_XML)
    public CommentDetail getCommentDetail(@PathParam("commentId") long commentId){
        CommentDetail comment=service.getCommentDetail(commentId);
        LinkMaker.popLinks(urif, comment);
        return comment;
    }
    
//secure
    @PUT
    @Secured
    @Path("/{commentId}")
    public Response updateCommentDetail(@PathParam("commentId") long commentId,
              CommentDetail commentDetail, @Context SecurityContext secure){
        if(commentDetail.getCommentId() != commentId){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
//                  && service.getCommentDetail(commentId).getUserName()
//                            .equals(commentDetail.getUserName()))
        if(!secure.getUserPrincipal().getName()
                  .equals(service.getCommentDetail(commentId).getUserName()))
            return Response.status(Response.Status.UNAUTHORIZED).build();
        if(service.updateComment(commentDetail))
            return Response.status(Response.Status.ACCEPTED).build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @POST
    @Secured
    public Response createCommentDetail(CommentDetail comment, @Context SecurityContext secure){
        if(!secure.getUserPrincipal().getName().equals(comment.getUserName()))
            return Response.status(Response.Status.UNAUTHORIZED).build();
        long id=service.addComment(comment);
        if(id>=0){
            CommentDetail newComment = service.getCommentDetail(id);
            LinkMaker.popLinks(urif, newComment);
            return Response.created(URI.create(newComment.getLinks().stream()
                      .filter(x->x.getName().equalsIgnoreCase("self"))
                      .findAny().get().getUrl())).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @DELETE
    @Path("/{commentId}")
    @Secured
    public Response deleteCommentDetail(@PathParam("commentId") long commentId,
              @Context SecurityContext secure){
        if(!secure.getUserPrincipal().getName().equals(service
                  .getCommentDetail(commentId).getUserName()))
            return Response.status(Response.Status.UNAUTHORIZED).build();
        if(service.removeCommentDetail(commentId))
            return Response.accepted().build();
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
